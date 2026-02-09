# Guia de Setup com Docker

## 📋 Pré-requisitos

- Docker instalado e rodando
- Docker Compose (opcional, mas recomendado)

## 🚀 Opção 1: Usando host network (Mais rápido)

### 1. Inicie o banco de dados
```bash
docker run -d \
  --name database-grotrack \
  -e MYSQL_ROOT_PASSWORD=123456 \
  -e MYSQL_DATABASE=grotrack \
  -p 3306:3306 \
  -v mysql-data:/var/lib/mysql \
  gabrielpacificooo/grotrack:database
```

### 2. Aguarde o MySQL inicializar (cerca de 10 segundos)
```bash
sleep 10
```

### 3. Inicie o back-end
```bash
docker run -d \
  -p 8080:8080 \
  --name back-end-grotrack \
  --network host \
  -e ACTIVE_PROFILE=mysql \
  -e JWT_SECRET="RXhpc3RlIHVtYSB0ZW9yaWEgcXVlIGRpeiBxdWUsIHNlIHVtIGRpYSBhbGd16W0gZGVzY29icmlyIGV4YXRhbWVudGUgcGFyYSBxdWUgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIHNlcnZlIG8gVW5pdmVyc28gZSBwb3IgcXVlIGVsZSBlc3TDoSBhcXVpLCBlbGUgZGVzYXBhcmVjZXJhIGluc3RhbnRhbmVhbWVudGUgZSBzZXJhIHN1YnN0aXR1w61kbyBwb3IgYWxnbyBhaW5kYSBtYWlzIGVzdHJhbmhvIGUgaW5leHBsaWNhdmVsLiBFeGlzdGUgdW1hIHNlZ3VuZGEgdGVvcmlhIHF1ZSBkaXogcXVlIGlzc28gauEgYWNvbnRlY2V1Li4u" \
  -e JWT_VALIDITY=3600000 \
  -e SERVER_PORT=8080 \
  -e SPRING_DATASOURCE_PASSWORD=123456 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/grotrack \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_JPA_HIBERNATE_DDL_AUTO=update \
  gabrielpacificooo/grotrack:back-end
```

### 4. Verifique se está rodando
```bash
docker logs back-end-grotrack
```

A aplicação estará disponível em: **http://localhost:8080**

---

## 🔗 Opção 2: Usando bridge network (Recomendado para produção)

### 1. Crie uma rede Docker
```bash
docker network create grotrack-network
```

### 2. Inicie o banco de dados
```bash
docker run -d \
  --name database-grotrack \
  --network grotrack-network \
  -e MYSQL_ROOT_PASSWORD=123456 \
  -e MYSQL_DATABASE=grotrack \
  -p 3306:3306 \
  -v mysql-data:/var/lib/mysql \
  gabrielpacificooo/grotrack:database
```

### 3. Aguarde o MySQL inicializar
```bash
sleep 10
```

### 4. Inicie o back-end
```bash
docker run -d \
  -p 8080:8080 \
  --name back-end-grotrack \
  --network grotrack-network \
  -e ACTIVE_PROFILE=mysql \
  -e JWT_SECRET="RXhpc3RlIHVtYSB0ZW9yaWEgcXVlIGRpeiBxdWUsIHNlIHVtIGRpYSBhbGd16W0gZGVzY29icmlyIGV4YXRhbWVudGUgcGFyYSBxdWUgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIHNlcnZlIG8gVW5pdmVyc28gZSBwb3IgcXVlIGVsZSBlc3TDoSBhcXVpLCBlbGUgZGVzYXBhcmVjZXJhIGluc3RhbnRhbmVhbWVudGUgZSBzZXJhIHN1YnN0aXR1w61kbyBwb3IgYWxnbyBhaW5kYSBtYWlzIGVzdHJhbmhvIGUgaW5leHBsaWNhdmVsLiBFeGlzdGUgdW1hIHNlZ3VuZGEgdGVvcmlhIHF1ZSBkaXogcXVlIGlzc28gauEgYWNvbnRlY2V1Li4u" \
  -e JWT_VALIDITY=3600000 \
  -e SERVER_PORT=8080 \
  -e SPRING_DATASOURCE_PASSWORD=123456 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://database-grotrack:3306/grotrack \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_JPA_HIBERNATE_DDL_AUTO=update \
  gabrielpacificooo/grotrack:back-end
```

---

## 🐳 Opção 3: Usando Docker Compose (Recomendado)

Crie um arquivo `docker-compose.yml` na raiz do projeto:

```yaml
version: '3.9'

services:
  database:
    image: gabrielpacificooo/grotrack:database
    container_name: database-grotrack
    environment:
      MYSQL_ROOT_PASSWORD: 123456
      MYSQL_DATABASE: grotrack
    ports:
      - "3306:3306"
    volumes:
      - mysql-data:/var/lib/mysql
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      timeout: 20s
      retries: 10

  backend:
    image: gabrielpacificooo/grotrack:back-end
    container_name: back-end-grotrack
    depends_on:
      database:
        condition: service_healthy
    environment:
      ACTIVE_PROFILE: mysql
      JWT_SECRET: RXhpc3RlIHVtYSB0ZW9yaWEgcXVlIGRpeiBxdWUsIHNlIHVtIGRpYSBhbGd16W0gZGVzY29icmlyIGV4YXRhbWVudGUgcGFyYSBxdWUgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIHNlcnZlIG8gVW5pdmVyc28gZSBwb3IgcXVlIGVsZSBlc3TDoSBhcXVpLCBlbGUgZGVzYXBhcmVjZXJhIGluc3RhbnRhbmVhbWVudGUgZSBzZXJhIHN1YnN0aXR1w61kbyBwb3IgYWxnbyBhaW5kYSBtYWlzIGVzdHJhbmhvIGUgaW5leHBsaWNhdmVsLiBFeGlzdGUgdW1hIHNlZ3VuZGEgdGVvcmlhIHF1ZSBkaXogcXVlIGlzc28gauEgYWNvbnRlY2V1Li4u
      JWT_VALIDITY: 3600000
      SERVER_PORT: 8080
      SPRING_DATASOURCE_PASSWORD: 123456
      SPRING_DATASOURCE_URL: jdbc:mysql://database:3306/grotrack
      SPRING_DATASOURCE_USERNAME: root
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
    ports:
      - "8080:8080"
    networks:
      - grotrack-network

volumes:
  mysql-data:

networks:
  grotrack-network:
    driver: bridge
```

### Para iniciar com Docker Compose:
```bash
docker-compose up -d
```

### Para parar:
```bash
docker-compose down
```

---

## 🔧 Troubleshooting

### Erro: "Cannot resolve reference to bean 'entityManagerFactory'"
**Causa**: Variáveis de ambiente não foram passadas corretamente
**Solução**: Verifique se todas as variáveis `-e` estão presentes no comando docker run

### Erro: "Connection refused" ou "MySQL connection error"
**Causa**: O MySQL não está rodando ou a aplicação está tentando conectar em localhost
**Solução**: 
- Use `--network host` para rodar ambos na rede do host, OU
- Use bridge network e nome do container como hostname

### Erro: "Access denied for user 'root'@'localhost'"
**Causa**: Senha incorreta na variável `SPRING_DATASOURCE_PASSWORD`
**Solução**: Certifique-se de que a senha é `123456` (ou a senha que você configurou)

### Aplicação iniciando mas não conseguindo acessar
**Causa**: Porta bloqueada ou firewall
**Solução**: 
```bash
# Teste a conectividade
curl -v http://localhost:8080/swagger-ui.html
```

### Ver logs da aplicação
```bash
docker logs -f back-end-grotrack
```

### Ver logs do banco de dados
```bash
docker logs -f database-grotrack
```

---

## ✅ Verificar Saúde dos Containers

```bash
# Ver status de todos os containers
docker ps

# Ver recursos utilizados
docker stats

# Entrar no container da aplicação
docker exec -it back-end-grotrack /bin/bash

# Entrar no container do MySQL
docker exec -it database-grotrack mysql -u root -p123456 grotrack
```

---

## 🧹 Limpeza

### Parar containers
```bash
docker stop back-end-grotrack database-grotrack
```

### Remover containers
```bash
docker rm back-end-grotrack database-grotrack
```

### Remover volumes (atenção: deleta dados!)
```bash
docker volume rm mysql-data
```

### Remover rede
```bash
docker network rm grotrack-network
```

---

## 📝 Notas Importantes

1. **Sempre inicie o banco de dados antes da aplicação** - A aplicação precisa do MySQL rodando
2. **Use a mesma rede** - Se usar bridge network, database e backend devem estar na mesma rede
3. **Hostname correto** - Em host network use `localhost`, em bridge network use o nome do container
4. **Variáveis de ambiente** - Todas as variáveis prefixadas com `SPRING_` devem ser definidas
