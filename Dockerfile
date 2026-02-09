# Etapa 1: usar imagem base do Maven + JDK para construir o projeto
FROM maven:4.0.0-rc-4-amazoncorretto-21 AS build

# Define o diretório de trabalho
WORKDIR /back-end

# Copia o pom.xml e baixa as dependências (cache de build)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o restante do código
COPY src ./src

# Empacota o projeto (gera o jar)
RUN mvn clean package

# Etapa 2: imagem final, leve, apenas para rodar o jar
FROM eclipse-temurin:21-jre

WORKDIR /back-end

# Copia o jar da etapa anterior
COPY --from=build /back-end/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar o app
ENTRYPOINT ["java", "-jar", "app.jar"]

