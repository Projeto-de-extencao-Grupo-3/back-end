# RELATÓRIO DE ANÁLISE DE SEGURANÇA - OWASP TOP 10 2021
## GeoTrack Back-End | Análise Completa do Projeto

**Data:** 9 de Fevereiro de 2026  
**Escopo:** Análise de frameworks, componentes e fluxo de segurança  
**Metodologia:** Revisão arquitetural com foco em OWASP Top 10 2021

---

## SUMÁRIO EXECUTIVO

Seu projeto utiliza **Spring Boot 3.4.1** com **Spring Security**, **JPA**, **JWT** e validação com **Jakarta Validation**. A análise examina como cada camada (frameworks, repositórios, serviços, controladores) protege contra vulnerabilidades OWASP.

**Vulnerabilidades CRÍTICAS encontradas:** 2  
**Vulnerabilidades ALTAS encontradas:** 3  
**Configurações FRACAS encontradas:** 4  

---

## 1. BROKEN ACCESS CONTROL (A01:2021)

### Status: ⚠️ **RISCO ALTO**

#### O que sua aplicação FAZ BEM:
✅ **Spring Security + @SecurityRequirement** - A maioria dos endpoints está protegida com `@SecurityRequirement(name = "Bearer")`, exigindo JWT válido.  
✅ **SessionCreationPolicy.STATELESS** - Você não usa sessão HTTP, apenas JWT (mais seguro).  
✅ **BCryptPasswordEncoder** - Senhas são criptografadas com bcrypt no cadastro (`OficinaService.cadastrar()`).  
✅ **@EnableMethodSecurity** - Permite controle de acesso em nível de método.

#### O que sua aplicação ESTÁ VULNERÁVEL:

❌ **CRÍTICO: Falta de Autorização de Recursos (Vertical e Horizontal)**
- Problema: Em `ClienteController`, `OrdemDeServicoController`, etc., qualquer usuário autenticado pode acessar **qualquer recurso**:
  ```java
  @GetMapping("/{id}")
  public ResponseEntity<ClienteResponse> getClienteById(@PathVariable Integer id) {
      Cliente cliente = clienteService.findClienteById(id);
      return ResponseEntity.status(200).body(ClientesMapper.toResponse(cliente));
  }
  ```
  - Qualquer oficina logada pode buscar clientes de OUTRA oficina apenas mudando o ID.
  - Não há validação se o usuário autenticado tem permissão para acessar aquele recurso específico.

❌ **CRÍTICO: Falta de Roles/Autoridades**
- Seu `UsuarioDetalhesDto.getAuthorities()` retorna `null`:
  ```java
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
      return null;  // ❌ SEM ROLES!
  }
  ```
- Sem roles (ROLE_ADMIN, ROLE_USER, etc.), você **não consegue controlar permissões granulares**.

❌ **ALTO: Endpoints públicos demais**
- Esses endpoints **NÃO requerem autenticação**:
  ```
  /swagger-ui/** - Documentação da API exposta
  /v3/api-docs/** - Schema JSON da API exposto
  /oficinas - Permitido sem JWT
  /oficinas/login - Correto, é público
  /actuator/* - Endpoints de monitoramento expostos
  /h2-console/** - Console do banco H2 exposto em produção!
  /error/** - Informações de erro expostas
  ```

❌ **ALTO: Token JWT não armazena informações de identificação do recurso**
- Em `GerenciadorTokenJwt`, o token contém apenas `subject` (CNPJ da oficina):
  ```java
  return Jwts.builder().setSubject(authentication.getName())
          .signWith(parseSecret()).setIssuedAt(new Date(...))
          .setExpiration(new Date(...)).compact();
  ```
- Quando busca um cliente por ID, não há verificação se esse cliente pertence à oficina autenticada.

---

## 2. CRYPTOGRAPHIC FAILURES (A02:2021)

### Status: ⚠️ **RISCO MÉDIO-ALTO**

#### O que sua aplicação FAZ BEM:
✅ **BCrypt para senhas** - Excelente, senhas não armazenadas em plain text.  
✅ **JWT com HMAC-SHA** - Token assinado com chave secreta:
  ```java
  private SecretKey parseSecret() {
      return Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));
  }
  ```

#### O que sua aplicação ESTÁ VULNERÁVEL:

❌ **CRÍTICO: JWT_SECRET armazenado em arquivo/variável de ambiente**
- Em `application.properties`:
  ```
  jwt.secret=${JWT_SECRET}
  ```
- E em `.github/workflows/ci.yaml` (VCS):
  ```yaml
  JWT_SECRET: "RXhpc3RlIHVtYSB0ZW9yaWEgcXVlIGRpeiBxdWU..."
  ```
- **Risco:** Se o repositório for exposto, a chave secreta está comprometida.
- **Impacto:** Qualquer pessoa com a chave pode gerar tokens JWT válidos e acessar a aplicação como qualquer usuário.

❌ **ALTO: Sem criptografia de dados em repouso (banco de dados)**
- Você armazena dados sensíveis em banco MySQL em plain text:
  - Nomes, CPF/CNPJ, telefones, emails de clientes
  - Endereços completos
- **Recomendação:** Implementar criptografia de dados sensíveis (field-level encryption com JPA).

❌ **MÉDIO: Banco de dados MySQL sem credenciais fortes em produção**
- Em `.github/workflows/ci.yaml`:
  ```yaml
  DB_PASSWORD: 123456  # ❌ Muito fraca!
  ```
- Mesmo que seja apenas CI/CD, isso expõe o padrão de credenciais fracas.

---

## 3. INJECTION (A03:2021) - SQL, HQL, etc.

### Status: ✅ **PROTEGIDO** 

#### O que sua aplicação FAZ BEM:

✅ **JPA Query Methods - Imune a SQL Injection**
- Você usa `JpaRepository` com métodos pré-compilados:
  ```java
  List<Cliente> findByNomeContainingIgnoreCase(String nome);
  Optional<Cliente> findByCpfCnpj(String cpfCnpj);
  Boolean existsByCpfCnpj(String cpfCnpj);
  ```
- Esses métodos são **automaticamente parametrizados** pelo Spring Data JPA.
- **Impacto:** SQL injection é prevenido na raiz.

✅ **Sem @Query com concatenação**
- Não encontrei nenhuma query customizada com concatenação de strings.
- Todos os repositórios usam derivação de nome ou métodos nativos.

✅ **Validation Framework (Jakarta)**
- Você valida entrada com:
  ```java
  @NotBlank
  @CPF
  @Size(min = 10, max = 11)
  ```
- Isso previne entrada malformada antes de chegar à query.

---

## 4. INSECURE DESIGN (A04:2021)

### Status: ⚠️ **RISCO ALTO**

#### O que sua aplicação ESTÁ VULNERÁVEL:

❌ **ALTO: Sem validação de autorização no design da API**
- A API foi desenhada **sem levar em conta** que um usuário autenticado não deve acessar dados de outro usuário.
- Exemplo: Um cliente pode ser vinculado a múltiplas oficinas, mas não há validação disso.

❌ **ALTO: Sem tratamento de tentativas de ataque de força bruta**
- Não há limite de tentativas de login (rate limiting).
- Um atacante pode tentar múltiplos CNPJ/senhas indefinidamente.

❌ **MÉDIO: Sem auditoria de ações críticas**
- Não há logging de:
  - Quem fez login e quando
  - Quem deletou um registro
  - Quem alterou dados sensíveis (CPF, email)
- Apenas logs em `AutenticacaoFilter` com nível `TRACE`.

---

## 5. BROKEN OBJECT LEVEL AUTHORIZATION (A05:2021)

### Status: ❌ **CRÍTICO**

#### O que sua aplicação ESTÁ VULNERÁVEL:

❌ **CRÍTICO: Nenhuma validação de propriedade de recurso**
- Cenário de ataque:
  1. Oficina A faz login e recebe token JWT
  2. Oficina A tenta GET `/clientes/999` (cliente de outra oficina)
  3. **Resultado:** Sucesso! Retorna dados do cliente de Oficina B

- Código vulnerable em `ClienteService`:
  ```java
  public Cliente findClienteById(Integer id) {
      Optional<Cliente> cliente = clientesRepository.findById(id);
      if (cliente.isEmpty()) {
          throw new DataNotFoundException("O ID %d não foi encontrado".formatted(id), "Clientes");
      }
      return cliente.get();  // ❌ Sem validação se cliente pertence à oficina autenticada
  }
  ```

❌ **CRÍTICO: Em OrdemDeServico também**
  ```java
  public OrdemDeServico findOrdemById(Integer idOrdem) {
      return ORDEM_REPOSITORY.findById(idOrdem)
              .orElseThrow(() -> new DataNotFoundException(...));
      // ❌ Qualquer usuário pode acessar qualquer ordem de qualquer oficina
  }
  ```

---

## 6. SECURITY MISCONFIGURATION (A06:2021)

### Status: ⚠️ **RISCO ALTO**

#### O que sua aplicação ESTÁ VULNERÁVEL:

❌ **CRÍTICO: Headers de segurança DESABILITADOS**
```java
http.headers(headers -> headers
    .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)  // ❌ Clickjacking desabilitado!
    .cacheControl(HeadersConfigurer.CacheControlConfig::disable) // ❌ Cache headers desabilitados!
    .contentTypeOptions(HeadersConfigurer.ContentTypeOptionsConfig::disable) // ❌
    .xssProtection(HeadersConfigurer.XXssConfig::disable) // ❌ XSS protection desabilitada!
)
```

❌ **CRÍTICO: CORS permitido para QUALQUER origem**
```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuracao = new CorsConfiguration();
    configuracao.applyPermitDefaultValues();  // ❌ Permite qualquer origin!
    // ...
    origem.registerCorsConfiguration("/**", configuracao);  // ❌ Em TODOS os endpoints!
}
```
- Qualquer site (http://attacker.com) pode fazer requisições para sua API.

❌ **CRÍTICO: H2-Console exposto em produção**
```java
new AntPathRequestMatcher("/h2-console/**"),
new AntPathRequestMatcher("/h2-console/**/**"),
```
- H2 é apenas para desenvolvimento/testes.
- Em produção com MySQL, isso não deveria estar acessível.

❌ **ALTO: Swagger/OpenAPI exposto sem autenticação**
```java
new AntPathRequestMatcher("/swagger-ui/**"),
new AntPathRequestMatcher("/v3/api-docs/**"),
```
- Qualquer pessoa pode ver todos os endpoints, parâmetros e estrutura de dados.

❌ **ALTO: /actuator exposto sem autenticação**
```java
new AntPathRequestMatcher("/actuator/*"),
```
- Expõe informações do sistema (memory, threads, etc).

❌ **MÉDIO: server.error.include-message=always**
```properties
server.error.include-message=always
server.error.include-binding-errors=always
```
- Expõe mensagens de erro detalhadas que podem revelar informações sobre a estrutura da aplicação.

---

## 7. CROSS-SITE SCRIPTING (XSS) - A07:2021

### Status: ✅ **PROTEGIDO** (parcialmente)

#### O que sua aplicação FAZ BEM:

✅ **Spring Security desabilita XSS Protection por configuração explícita**
- Enquanto isso é uma escolha deliberada, você usa JSON e não renderiza HTML no servidor.

✅ **Sem template rendering no servidor**
- Você retorna apenas DTOs JSON via `@RestController`.
- Não há template HTML (Thymeleaf, JSP) que pudesse ter XSS.

✅ **Jackson com SNAKE_CASE**
- Seu JSON é sempre estruturado, não tem entrada de usuário interpolada em HTML.

#### Potencial vulnerabilidade:

⚠️ **MÉDIO: Se implementar Web UI com template**
- Se você criar uma Web UI (Thymeleaf, etc) que consome a API, precisará ativar:
  ```java
  .xssProtection(HeadersConfigurer.XXssConfig::enable)
  ```

---

## 8. IDENTIFICATION AND AUTHENTICATION FAILURES (A08:2021)

### Status: ⚠️ **RISCO MÉDIO-ALTO**

#### O que sua aplicação FAZ BEM:

✅ **JWT com expiração**
  ```java
  jwt.validity=${JWT_VALIDITY}  // Padrão: 3600000ms = 1 hora
  ```

✅ **Autenticação baseada em CNPJ + Senha**
  ```java
  if (this.passwordEncoder.matches(password, userDetails.getPassword())) {
      return new UsernamePasswordAuthenticationToken(...);
  }
  ```

#### O que sua aplicação ESTÁ VULNERÁVEL:

❌ **ALTO: Sem proteção contra brute force no login**
- Não há implementação de:
  - Rate limiting (limitar tentativas por IP)
  - Lockout temporário após N falhas
  - Captcha
- Um atacante pode fazer `POST /oficinas/login` infinitamente.

❌ **MÉDIO: Token JWT nunca é invalidado (logout fraco)**
- Não há mecanismo de blacklist ou logout.
- Um token roubado continua válido até expirar (1 hora).
- Sem forma de fazer logout imediato.

❌ **MÉDIO: Senha padrão em CI/CD**
```yaml
JWT_SECRET: "RXhpc3RlIHVtYSB0ZW9yaWEgcXVlIGRpeiBxdWU..."
```
- Secret é uma base64 de um texto conhecido (está visível em GitHub).

---

## 9. SOFTWARE AND DATA INTEGRITY FAILURES (A09:2021)

### Status: ✅ **PROTEGIDO**

#### O que sua aplicação FAZ BEM:

✅ **Dependências gerenciadas via Maven**
- Arquivo `pom.xml` centraliza todas as dependências.
- Maven automaticamente baixa versões compatíveis.

✅ **Spring Boot 3.4.1 (versão recente)**
- Você está em uma versão recente que recebe patches de segurança.

✅ **Sem código externo/scripts desconhecidos**
- Tudo é controle via Maven.

#### Potencial melhoria:

⚠️ **MÉDIO: Verificação de vulnerabilidades de dependências**
- Você NÃO usa ferramentas como:
  - OWASP Dependency-Check
  - Snyk
  - GitHub Security Advisories
- Recomendação: Integrar no CI/CD.

---

## 10. SECURITY LOGGING AND MONITORING FAILURES (A10:2021)

### Status: ⚠️ **RISCO MÉDIO**

#### O que sua aplicação FAZ BEM:

✅ **Logging básico em AutenticacaoFilter**
```java
LOGGER.info("[FALHA AUTENTICACAO] - Token expirado, usuario: {} - {}",
        exception.getClaims().getSubject(), exception.getMessage());
```

#### O que sua aplicação ESTÁ VULNERÁVEL:

❌ **MÉDIO: Sem logging de eventos de negócio críticos**
- Não há logs de:
  - Criação de nova ordem de serviço
  - Alteração de dados sensíveis (CPF, email, telefone)
  - Deleção de registros
  - Mudanças de status

❌ **MÉDIO: Sem alertas em tempo real**
- Não há sistema de alertas para:
  - Múltiplas falhas de autenticação
  - Acesso a recursos sensíveis
  - Mudanças em dados críticos

❌ **MÉDIO: Logs não centralizados**
- Logs provavelmente estão apenas em arquivo local.
- Sem consolidação em ELK, Splunk, ou CloudWatch.

---

## 11. VULNERABILIDADES ADICIONAIS ENCONTRADAS

### A. Denial of Service (DoS) Implícito

❌ **Sem limit de paginação**
- Endpoints como `/clientes` retornam **TODOS os clientes** sem paginação:
```java
public List<Cliente> findClientes(){
    return clientesRepository.findAll();  // ❌ Sem limit!
}
```
- Um banco com 1 milhão de registros causaria OOM (Out of Memory).

### B. Exposição de Informações Sensíveis

❌ **CPF retornado em respostas**
```java
@Schema(description = "CPF (11 dígitos) ou CNPJ (14 dígitos) do cliente", ...)
private String cpfCnpj;
```
- CPF é dato sensível e nunca deveria ser retornado em listas públicas.

❌ **Logs com informações sensíveis**
```
LOGGER.trace("[FALHA AUTENTICACAO] - stack trace: %s", exception);
```
- Stack traces podem expor informações internas.

---

## MATRIZ DE VULNERABILIDADES - RESUMO EXECUTIVO

| # | Vulnerabilidade OWASP | Seu Status | Severidade | Recomendação |
|---|---|---|---|---|
| 1 | Broken Access Control | ❌ Vulnerável | **CRÍTICA** | Implementar autorização por recurso |
| 2 | Cryptographic Failures | ⚠️ Parcial | **ALTA** | Proteger JWT_SECRET, criptografar dados sensíveis |
| 3 | Injection (SQL) | ✅ Protegido | N/A | Manter JPA, não usar @Query com concat |
| 4 | Insecure Design | ❌ Vulnerável | **ALTA** | Redesenhar com segurança em mente |
| 5 | Broken Object Level Authorization | ❌ Vulnerável | **CRÍTICA** | Validar propriedade de recurso em CADA endpoint |
| 6 | Security Misconfiguration | ❌ Vulnerável | **ALTA** | Ativar headers, restringir CORS |
| 7 | XSS | ✅ Protegido | N/A | Manter como está (REST API) |
| 8 | Authentication Failures | ⚠️ Parcial | **ALTA** | Rate limiting, logout imediato |
| 9 | Software Integrity | ✅ Protegido | N/A | Adicionar Dependency-Check no CI/CD |
| 10 | Logging & Monitoring | ⚠️ Fraco | **MÉDIA** | Implementar auditoria completa |

---

## IMPACTO CRÍTICO IDENTIFICADO

### Cenário de Ataque Realista - CRÍTICO

```
1. Atacante descobre a oficina A (CNPJ: 11.111.111/0001-11) via CNPJ público
2. Porta força bruta na API /oficinas/login até conseguir acesso
   (Sem rate limiting, tenta 100.000 combinações)
3. Consegue login com sucesso
4. Obtém token JWT válido por 1 hora
5. Acessa GET /clientes/1, /clientes/2, ... /clientes/9999
   (Sem validação, obtém TODOS os clientes de TODAS as oficinas)
6. Extrai CPF/CNPJ/emails/telefones de 10.000 clientes
7. Acessa GET /ordens/1, /ordens/2, ... /ordens/50000
   (Obtém informações de serviços, valores, veículos de outras oficinas)
8. Acessa PUT /clientes/{id} de cliente de outra oficina
   (Altera email/telefone de cliente alheia)
```

**Resultado:** Breach completo de dados de múltiplas oficinas.

---

## RECOMENDAÇÕES PRIORITÁRIAS

### 🔴 CRÍTICA - Implementar em SEMANAS

1. **[P1] Autorização por Recurso**
   - Adicionar validação em CADA endpoint que retorna dados
   - Verificar se recurso pertence à oficina autenticada
   
2. **[P1] Disabled Security Headers**
   - Ativar X-Frame-Options, X-Content-Type-Options, etc
   - Restringir CORS apenas para domínios conhecidos

3. **[P1] Proteger JWT_SECRET**
   - Remover de GitHub, usar AWS Secrets Manager / Vault
   - Usar chave forte (256 bits)

### 🟠 ALTA - Implementar em MESES

4. **[P2] Rate Limiting no Login**
   - Implementar Spring Cloud Gateway com rate limiter
   
5. **[P2] Roles e Autorização Granular**
   - Adicionar ROLE_ADMIN, ROLE_EMPLOYEE, etc
   - Usar @PreAuthorize em endpoints

6. **[P2] Logout e Token Blacklist**
   - Implementar Redis para invalidar tokens
   - Endpoint POST /logout

7. **[P2] Auditoria Completa**
   - Logar TODAS as operações (Create, Update, Delete)
   - Armazenar quem, o quê, quando, onde

### 🟡 MÉDIA - Implementar em SEMESTRES

8. **[P3] Criptografia de Dados em Repouso**
   - Criptografar CPF, email, telefone no banco
   
9. **[P3] Paginação em Endpoints**
   - Adicionar @PageableDefault em findAll()
   
10. **[P3] Swagger/Actuator com Autenticação**
    - Proteger /swagger-ui, /v3/api-docs, /actuator

---

## CONCLUSÃO

Sua aplicação **tem uma base sólida** com Spring Security, JPA e JWT, mas **sofre de vulnerabilidades críticas de autorização** que a tornam vulnerável a ataques imediatos. 

O maior risco é:
- ✋ **Qualquer usuário autenticado pode acessar/modificar dados de qualquer outro usuário**

Isso invalida toda a proteção de autenticação que você implementou.

**Prioridade 1:** Implementar verificação de autorização por recurso em TODOS os endpoints que retornam dados do usuário.

---

## ARQUIVOS ANALISADOS

- ✅ `pom.xml` - Dependências
- ✅ `SecurityConfiguracao.java` - Configuração Spring Security
- ✅ `AutenticacaoFilter.java` - Filtro de autenticação JWT
- ✅ `GerenciadorTokenJwt.java` - Geração/validação de tokens
- ✅ `ClienteController.java`, `OrdemDeServicoController.java` - Endpoints
- ✅ `ClienteService.java`, `OficinaService.java` - Lógica de negócio
- ✅ `ClienteRepository.java`, etc - Acesso a dados (JPA)
- ✅ `UsuarioDetalhesDto.java` - Autenticação
- ✅ `GlobalExceptionHandler.java` - Tratamento de erros
- ✅ `application.properties` - Configuração

---

## Próximos Passos

1. **Revisar este relatório** com seu time
2. **Validar se análise faz sentido** com a arquitetura real
3. **Priorizar correções** conforme severidade
4. **Solicitar análise de remediação** para vulnerabilidades críticas

---

*Relatório gerado automaticamente. Para dúvidas técnicas, consultar documentação OWASP: https://owasp.org/Top10/*
