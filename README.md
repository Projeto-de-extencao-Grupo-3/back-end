## Docker

## Criando imagem do Backend 
``` bash
docker build -t backend-grotrack .
```
## Colocando tag na imagem que criamos
``` bash
docker tag meuapp-java:latest <nome-usuario>/backend-grotrack:latest
```
## Enviando imagem Backend para o docker hub
``` bash
docker push <nome-usuario>/backend-grotrack:latest
```
