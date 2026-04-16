# Relatorio de reorganizacao por dominios - GROTrack

## Objetivo deste documento
Este relatorio descreve a reorganizacao arquitetural proposta para o projeto, com foco em organizacao por dominio e aderencia a arquitetura hexagonal (Ports and Adapters), **sem alterar o dominio `jornada`**.

A ideia e facilitar manutencao, reduzir acoplamento e tornar o codigo mais intuitivo para novos mantenedores.

---

## O que foi feito
- Analisei a estrutura atual de pacotes em `src/main/java/geo/track`.
- Identifiquei pontos de mistura de responsabilidades (principalmente em `gestao`, `dto`, `controller`, `external` e `infraestructure`).
- Defini uma estrutura alvo por dominios/modulos para os segmentos fora de `jornada`.
- Mantive `jornada` explicitamente fora do escopo de reorganizacao.
- Documentei padroes de nomenclatura, separacao de camadas e plano de migracao incremental.

---

## Diagnostico da estrutura atual

### Pontos positivos
- Existe separacao inicial entre componentes de negocio (`gestao`), integracoes (`external`) e infraestrutura (`infraestructure`).
- Controllers, services, repositories e DTOs estao presentes para os principais fluxos.
- Ja existe `GlobalExceptionHandler`, configuracoes de seguranca e OpenAPI.

### Pontos que dificultam manutencao
- `gestao` concentra muitos subdominios diferentes (cliente, contato, veiculo, oficina, produto, itens, etc.).
- `dto` centralizado por tipo de dado gera acoplamento transversal e dificulta localizar contexto funcional.
- `controller` esta parcialmente fora dos modulos de negocio (ex.: `OrdemDeServicoController` separado), criando navegacao inconsistente.
- `external` mistura casos de uso de arquivo e reconhecimento de placa com conceitos de entidade local.
- `infraestructure` (grafia atual) concentra itens globais e itens de dominio, misturando responsabilidades.
- Inconsistencia de nomenclatura entre singular/plural e entre portugues/ingles.

---

## Escopo e restricoes desta reorganizacao
- **Nao mexer no dominio `jornada`** (codigo, estrutura e contratos).
- Reorganizar somente os demais segmentos:
  - autenticacao
  - cadastros (cliente, contato, endereco, funcionario, oficina, veiculo)
  - estoque/itens/produtos/servicos
  - registro de entrada
  - arquivos
  - integracoes externas (placa, via cep, gateway)
  - infraestrutura compartilhada

---

## Estrutura alvo proposta (por dominio)

```text
src/main/java/geo/track/
  bootstrap/
    StartApplication.java

  shared/
    api/
      exception/
        GlobalExceptionHandler.java
        ExceptionBody.java
        ...
    config/
      security/
      openapi/
      rabbitmq/
      beans/
    logging/
    annotation/
    util/

  autenticacao/
    api/
      controller/
      dto/
    application/
      service/
      usecase/
    domain/
      model/
      ports/
    infrastructure/
      persistence/
      security/

  cadastros/
    cliente/
      api/
      application/
      domain/
      infrastructure/
    contato/
      api/
      application/
      domain/
      infrastructure/
    endereco/
      api/
      application/
      domain/
      infrastructure/
    funcionario/
      api/
      application/
      domain/
      infrastructure/
    oficina/
      api/
      application/
      domain/
      infrastructure/
    veiculo/
      api/
      application/
      domain/
      infrastructure/

  operacao/
    registroentrada/
      api/
      application/
      domain/
      infrastructure/

  catalogo/
    produto/
      api/
      application/
      domain/
      infrastructure/
    itemproduto/
      api/
      application/
      domain/
      infrastructure/
    itemservico/
      api/
      application/
      domain/
      infrastructure/

  arquivos/
    api/
    application/
    domain/
    infrastructure/
      storage/
      persistence/
      template/

  integracoes/
    placa/
      application/
      infrastructure/
    viacep/
      application/
      infrastructure/
    gateway/
      application/
      infrastructure/

  jornada/  (inalterado)
    ...
```

---

## Mapeamento sugerido (estrutura atual -> estrutura alvo)

- `geo.track.gestao.ClienteController` -> `geo.track.cadastros.cliente.api.controller.ClienteController`
- `geo.track.gestao.service.ClienteService` -> `geo.track.cadastros.cliente.application.service.ClienteService`
- `geo.track.gestao.entity.Cliente` -> `geo.track.cadastros.cliente.infrastructure.persistence.entity.ClienteEntity`
- `geo.track.gestao.entity.repository.ClienteRepository` -> `geo.track.cadastros.cliente.infrastructure.persistence.repository.ClienteRepository`
- `geo.track.dto.clientes.*` -> `geo.track.cadastros.cliente.api.dto.*`

Repetir o mesmo padrao para:
- contato
- endereco
- funcionario
- oficina
- veiculo
- produto
- itemproduto
- itemservico
- registroentrada

Para integracoes/externos:
- `geo.track.external.ArquivosController` -> `geo.track.arquivos.api.controller.ArquivosController`
- `geo.track.external.service.ArquivoService` -> `geo.track.arquivos.application.service.ArquivoService`
- `geo.track.external.entity.*` -> `geo.track.arquivos.infrastructure.persistence.entity.*`
- `geo.track.external.PlateController` -> `geo.track.integracoes.placa.api.controller.PlateController`
- `geo.track.util.ViacepConnection` -> `geo.track.integracoes.viacep.infrastructure.client.ViacepClient`

Para infraestrutura transversal:
- `geo.track.infraestructure.config.*` -> `geo.track.shared.config.*`
- `geo.track.infraestructure.exception.*` -> `geo.track.shared.api.exception.*`
- `geo.track.infraestructure.log.*` -> `geo.track.shared.logging.*`
- Padronizar nome de pacote: `infraestructure` -> `infrastructure` (quando houver migracao)

---

## Convencoes de nomenclatura recomendadas
- Pacotes sempre em minusculo e sem acento.
- Preferir nomes consistentes em portugues de dominio (ex.: `cadastros`, `registroentrada`) e ingles tecnico para camadas (`api`, `application`, `domain`, `infrastructure`).
- DTOs junto do modulo (`api/dto`) e nao em pacote global.
- Entidades JPA sufixadas com `Entity` quando coexistirem com modelo de dominio puro.
- Mapeadores explicitos entre `domain` e `infrastructure.persistence.entity`.

---

## Regras de separacao de camadas (hexagonal)
- `domain`: regra de negocio pura, sem Spring/JPA.
- `application`: orquestracao de casos de uso e regras transacionais.
- `api`: controllers, requests/responses, validacoes de borda.
- `infrastructure`: JPA, mensageria, clientes HTTP, providers externos.
- `shared`: componentes transversais reutilizaveis entre dominios.

---

## Ordem recomendada de migracao (incremental e segura)
1. Criar estrutura de pacotes alvo sem remover a atual.
2. Migrar primeiro modulos de baixo risco: `cadastros` (cliente, contato, endereco).
3. Migrar `funcionario`, `oficina`, `veiculo`.
4. Migrar `catalogo` (`produto`, `itemproduto`, `itemservico`).
5. Migrar `operacao/registroentrada`.
6. Migrar `arquivos` e `integracoes`.
7. Consolidar `shared` (exceptions/config/logging) e corrigir grafia de `infrastructure`.
8. Validar testes e contratos de API a cada etapa.
9. Remover pacotes legados somente apos estabilizacao.

---

## Beneficios esperados
- Navegacao do codigo orientada ao negocio (dominio primeiro).
- Menos acoplamento entre modulos e menor risco de regressao em mudancas locais.
- Facil onboarding de novos desenvolvedores.
- Melhor alinhamento com arquitetura hexagonal e evolucao futura (inclusive mobile e integracoes).
- Preparacao para separar modulos de forma gradual sem ruptura do sistema.

---

## Observacoes finais para quem vai manter o projeto
- O dominio `jornada` permanece intencionalmente intacto nesta proposta.
- A reorganizacao deve ser feita em PRs pequenas por dominio, com testes e revisao de contrato OpenAPI.
- Sempre que possivel, usar adaptadores para manter compatibilidade temporaria durante a transicao.
- O objetivo nao e reescrever o sistema, e sim organizar por contexto para aumentar clareza e sustentabilidade tecnica.

