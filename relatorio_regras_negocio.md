# Relatório de Análise de Regras de Negócio na Camada Controller

Este relatório documenta locais no projeto onde lógicas e regras de negócio estão sendo executadas fora da camada `Service` (principalmente na camada `Controller`), violando o princípio de separação de responsabilidades.

Abaixo, os pontos sensíveis detectados por Controller:

## 1. ArquivosController
**Severidade: ALTA**
Este é o ponto mais crítico identificado na aplicação, pois a Controller está atuando como uma Service completa, ferindo gravemente as práticas de arquitetura limpa (MVC).
* **Acesso direto a repositórios**: A Controller injeta e acessa diretamente o `ArquivoRepository` ao invés de delegar o fluxo de acesso a dados para uma `Service`.
    * `ARQUIVO_REPOSITORY.existsByFkOrdemServicoAndTemplate(...)`
    * `ARQUIVO_REPOSITORY.save(...)`
    * `ARQUIVO_REPOSITORY.findByFkOrdemServicoAndTemplate(...)`
* **Lógica de criação e validação condicional de estado (Regra de Negócio)**:
    * Há lógicas explicitas de criação `if (!ARQUIVO_REPOSITORY.exists...) ARQUIVO_REPOSITORY.save(...)` dentro dos `endpoints`.
    * Há validação do estado do arquivo (domínio do negócio): `if (!arquivo.getStatus().equals(StatusArquivo.CONCLUIDO))` lançando exceptions do tipo `AcceptedException`.
* **Integração Externa (RabbitMQ / Gateway)**: A chamada da mensageria (fila) com `GATEWAY_EXPORT_DATA_RABBIT_MQ.solicitarArquivo(...)` está ocorrendo diretamente na Controller, algo que é de exclusiva responsabilidade de lógicas de negócio e integração de backend contidas numa Service.

## 2. OrdemDeServicoController
**Severidade: MÉDIA**
* **Validação de Enum / Status**: A Controller detém a lógica de formatação de string e checagem de existência de estado válido para a `StatusVeiculo`.
  ```java
  List<String> validStatus = Arrays.stream(StatusVeiculo.values()).map(Enum::name).toList();
  boolean isValid = validStatus.contains(status.toUpperCase());
  if (!isValid) throw new BadRequestException(...);
  ```
  Isso não é responsabilidade da Controller de acordo com a arquitetura do projeto. O correto é a validação ocorrer num `Service` (lançando exception) ou usando anotações personalizadas do `Spring Validation` no parâmetro do método.
* Validações de negação `ORDEM_SERVICO_SERVICE.existeOrdemServicoPorEntrada(...)` onde a `Controller` ativamente aciona um serviço booleano e então processa o lançamento da exception `ConflictException` (a exception deveria ser laçada por dentro do `Service`).

## 3. ProdutoController
**Severidade: MÉDIA**
* **Filtros manuais e processamento em memória**: 
  No endpoint `listarContains`, há a recuperação de TODOS os produtos via service `List<Produto> produtos = PRODUTO_SERVICE.listar();` e, em seguida, a **Controller** faz a filtragem em memória:
  `produtos = produtos.stream().filter(p -> p.getNome().contains(nome)).toList();`
  Isso causa graves problemas de performance na base de dados (que fará um `select *` na tabela desnecessariamente) e expõe a regra de busca para a controller. O correto é repassar a `String nome` para a `Service` e a camada repository efetuar uma query do tipo `WHERE nome LIKE %nome%`.

## 4. PainelControleController
**Severidade: BAIXA/MÉDIA**
* **Regra de Visibilidade**: Há múltiplos `if (ordem.getServicos().isEmpty() && ordem.getProdutos().isEmpty()) return ResponseEntity.status(204).build();`.
A determinação de que uma ordem sem produtos e serviços não deve ser exibida é uma validação atrelada aos requisitos de negócio. Ela não deveria estar contida nas actions da `PainelControleController`, mas abstraída por dentro das chamadas de métodos da respectiva `Service` e lidada com as exceções da aplicação ou retornada como Optional vazio.

## 5. Práticas Gerais e Tratamento (Várias Controllers)
**Severidade: BAIXA**
* **Condicionais de Retorno HTTP Espalhados**: É muito comum ao longo de todas as controllers (`ClienteController`, `EnderecoController`, `ItemProdutoController`, `VeiculoController`, etc.) ver condicionais avaliando o retorno dos Serviços:
    * `if (lista.isEmpty()) { return ResponseEntity.status(204).build(); }`
    * `if (entidade == null) { return ResponseEntity.status(404).build(); }`
* Embora retornar os devidos códigos HTTP (No Content / Not Found) seja o correto para Restful, ter que escrever essas lógicas "suja" e infla as Controllers. A abordagem ideal é a de adotar o que parte do seu projeto já faz: **Fazer as Services lançarem `DataNotFoundException`** e deixar um `@ControllerAdvice` (`GlobalExceptionHandler`) mapear as Exceptions para o código HTTP, deixando a Controller apenas com chamadas da forma `return ResponseEntity.ok(service.buscar());`.

---
### Conclusão e Próximos Passos
O foco de refatoração para corrigir a estrutura arquitetural focada em regras de negócio deve ser, em ordem de urgência:
1. Extrair toda a integração de mensageria, consulta a DB e controle condicional de estado de Arquivo da `ArquivosController` e transpor para uma nova (e inexistente) `ArquivoService`.
2. Remover os filtros de `stream()` em `ProdutoController` e delegá-los ao `ProdutoService / ProdutoRepository`.
3. Ajustar as lógicas da Controller de `OrdemDeServicoController` repassando a validação de Enums e Status para dentro do fluxo restrito do respectivo Serviço.