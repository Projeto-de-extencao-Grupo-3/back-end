# Grotrack Back-end

API REST em Spring Boot organizada por dominios funcionais. Este documento descreve a estrutura tecnica do projeto para facilitar onboarding, manutencao e evolucao de codigo.

## Visao tecnica geral

- Linguagem e runtime: Java 21
- Framework principal: Spring Boot 3
- Persistencia: Spring Data JPA (MySQL/H2)
- Documentacao de API: Springdoc OpenAPI (Swagger UI)
- Integracoes: OpenFeign e RabbitMQ
- Seguranca: Spring Security + JWT

Ponto de entrada da aplicacao:

- `src/main/java/geo/track/StartApplication.java`

## Organizacao por dominio

A base de codigo em `src/main/java/geo/track` e separada por dominios:

- `jornada`
- `gestao`
- `catalogo`
- `externo`

Cada dominio (ou subdominio) segue majoritariamente o padrao de camadas:

- `application`: contratos e orquestracao de casos de uso
- `domain`: implementacoes de regra tecnica e servicos de dominio
- `infraestructure`: adaptadores de entrada/saida (web, persistencia, mapeamento, DTOs)

Observacao: o nome de pasta `infraestructure` foi mantido como esta no projeto.

## Dominio Jornada

Pacote base:

- `src/main/java/geo/track/jornada`

Estrutura principal:

- `application`: casos de uso de cadastro, controle e listagem
- `domain`: servicos e implementacoes usadas pelos use cases
- `infraestructure/web`: controladores HTTP
- `infraestructure/persistence`: repositorios JPA e entidades
- `infraestructure/request` e `infraestructure/response`: modelos de entrada/saida
- `infraestructure/mapper`: transformacao entre entidades e DTOs

Entradas HTTP:

- `JornadaController` (`/jornada`)
- `OrdemDeServicoController` (`/ordens`)

## Dominio Gestao

Pacote base:

- `src/main/java/geo/track/gestao`

Subdominios tecnicos:

- `cliente` (Embedded com `contato` e `endereco`)
- `veiculo`
- `funcionario`
- `oficina`

Padrao por subdominio:

- `application`: use cases para escrita (CUD) e consulta (R)
- `domain`: servicos e classes de comportamento tecnico
- `infraestructure/web`: controllers REST
- `infraestructure/persistence`: repositories e entities
- `infraestructure/request` e `infraestructure/response`: DTOs
- `infraestructure/*Mapper`: mapeadores de conversao

Entradas HTTP (principais):

- `ClienteController` (`/clientes`)
- `ContatoController` (`/clientes/{idCliente}/contatos`)
- `EnderecoController` (`/enderecos`)
- `VeiculoController` (`/veiculos`)
- `FuncionarioController` (`/funcionarios`)
- `OficinaController` (`/oficinas`)

## Dominio Catalogo

Pacote base:

- `src/main/java/geo/track/catalogo`

Subdominios tecnicos:

- `produto`
- `item_produto`
- `item_servico`

Padrao de camadas:

- `application`: casos de uso
- `domain`: servicos e implementacoes
- `infraestructure/web`: controladores REST
- `infraestructure/persistence`: repositories e entities
- `infraestructure/request` e `infraestructure/response`: contratos de API

Entradas HTTP:

- `ProdutoController` (`/produtos`)
- `ItemProdutoController` (`/itens-produtos`)
- `ItemServicoController` (`/itens-servicos`)

## Dominio Externo

Pacote base:

- `src/main/java/geo/track/externo`

Subdominios tecnicos:

- `arquivo`
- `placa`
- `viacep`

Responsabilidades tecnicas:

- Integracoes com servicos externos
- Endpoints para operacoes de apoio
- Persistencia de metadados/artefatos quando aplicavel

Entradas HTTP:

- `ArquivosController` (`/arquivos`)
- `PlateController` (`/plates`)

Componente de integracao dedicado:

- `ViacepConnection` em `externo/viacep`

## Componentes transversais

Pacote base:

- `src/main/java/geo/track/infraestructure`

Conteudo tecnico compartilhado:

- `auth`: servicos de autenticacao e modelos de usuario
- `config`: seguranca, beans, JWT, swagger e RabbitMQ
- `exception`: excecoes de API, mensagens e handlers globais
- `log`: contrato e implementacao de log

## Convencoes tecnicas de implementacao

- Controllers em `infraestructure/web` expostos via `@RestController`
- Operacoes de escrita (CUD) via contratos/use cases em `application`
- Operacoes de leitura (R) em comunicacao direta quando necessario
- Implementacoes com estereotipos Spring (`@Component`, `@Service`, `@Repository`)
- Persistencia com repositories JPA em `infraestructure/persistence`

## Configuracao e execucao

Arquivos de configuracao:

- `src/main/resources/application.properties`
- `src/main/resources/application-mysql.properties`
- `src/main/resources/application-h2.properties`

Build local:

```bash
mvn clean package
```

Execucao local (perfil padrao):

```bash
mvn spring-boot:run
```

## Docker (base)

Pull das imagens:

```bash
docker pull gabrielpacificooo/grotrack:database
docker pull gabrielpacificooo/grotrack:back-end
```

Subir banco MySQL local:

```bash
docker run -d \
  --name database-grotrack \
  -e MYSQL_ROOT_PASSWORD=123456 \
  -e MYSQL_DATABASE=grotrack \
  -p 3306:3306 \
  -v mysql-data:/var/lib/mysql \
  gabrielpacificooo/grotrack:database
```

## Objetivo deste README

Este README cobre apenas contexto tecnico e estrutural. A descricao de regra de negocio fica separada para ser apresentada pelo time de produto/dominio.
