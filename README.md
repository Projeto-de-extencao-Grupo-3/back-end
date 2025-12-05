## Configuração do Banco de Dados para Dev

## Pegar a imagem no docker hub
``` bash 
docker pull henriquedandrade/bd-grotrack-v2:bd
```

## Comando para rodar o container
``` bash 
docker run -d   --name grotrack   -e MYSQL_ROOT_PASSWORD=123456   -e MYSQL_DATABASE=grotrack   -p 3306:3306   -v mysql-data:/var/lib/mysql   henriquedandrade/bd-grotrack-v2:bd
```

## Comando para rodar container da aplicação (Opcional)
```bash
docker run -d \
  -p 8080:8080 \
  --name back-end-app \
  -e DB_HOST=127.0.0.1 \
  -e DB_PORT=3306 \
  -e DB_NAME=grotrack \
  -e DB_USERNAME=root \
  -e DB_PASSWORD=123456 \
  -e JWT_VALIDITY=3600000 \
  -e JWT_SECRET="RXhpc3RlIHVtYSB0ZW9yaWEgcXVlIGRpeiBxdWUsIHNlIHVtIGRpYSBhbGd16W0gZGVzY29icmlyIGV4YXRhbWVudGUgcGFyYSBxdWUgc2VydmUgbyBVbml2ZXJzbyBlIHBvciBxdWUgZWxlIGVzdOEgYXF1aSwgZWxlIGRlc2FwYXJlY2Vy4SBpbnN0YW50YW5lYW1lbnRlIGUgc2Vy4SBzdWJzdGl0de1kbyBwb3IgYWxnbyBhaW5kYSBtYWlzIGVzdHJhbmhvIGUgaW5leHBsaWPhdmVsLiBFeGlzdGUgdW1hIHNlZ3VuZGEgdGVvcmlhIHF1ZSBkaXogcXVlIGlzc28gauEgYWNvbnRlY2V1Li4u" \
  gabrielpacificooo/grotrack:back-end
  ```

Após isto, garanta que o container esteja rodando e o utilize para desenvolver e seja feliz ;->)