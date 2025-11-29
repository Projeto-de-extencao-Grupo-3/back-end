## Configuração do Banco de Dados para Dev

## Pegar a imagem no docker hub
``` bash 
docker pull henriquedandrade/bd-grotrack-v2:bd
```

## Comando para rodar o container
``` bash 
docker run -d   --name grotrack   -e MYSQL_ROOT_PASSWORD=123456   -e MYSQL_DATABASE=grotrack   -p 3306:3306   -v mysql-data:/var/lib/mysql   henriquedandrade/bd-grotrack-v2:bd
```

Após isto, garanta que o container esteja rodando e o utilize para desenvolver