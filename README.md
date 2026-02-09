## 🐳 Configuração com Docker

### Docker Compose

```bash
# Inicie tudo com um comando
docker-compose up -d

# Verifique o status
docker-compose ps

# Veja os logs
docker-compose logs -f

# Pare tudo
docker-compose down
```

---

### Documentação Completa

Para instruções detalhadas, troubleshooting e todas as opções, consulte **[DOCKER_SETUP.md](help/DOCKER_SETUP.md)**.

---

## Configuração Manual do Banco de Dados para Dev

### Pegar as imagens no docker hub
``` bash 
docker pull gabrielpacificooo/grotrack:database
docker pull gabrielpacificooo/grotrack:back-end
```

### Comando para rodar o container do banco de dados
``` bash 
docker run -d   --name database-grotrack   -e MYSQL_ROOT_PASSWORD=123456   -e MYSQL_DATABASE=grotrack   -p 3306:3306   -v mysql-data:/var/lib/mysql   gabrielpacificooo/grotrack:database
```

Após isto, garanta que o container esteja rodando e o utilize para desenvolver e seja feliz ;->)