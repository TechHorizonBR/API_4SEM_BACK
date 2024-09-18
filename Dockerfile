# Stage 1: Build da aplicação Java (Maven)
FROM maven:4.0.0-openjdk-17 AS builder

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo pom.xml e outras dependências necessárias
COPY pom.xml ./

# Baixa as dependências do Maven
RUN mvn dependency:go-offline -B

# Copia o código-fonte da aplicação
COPY src ./src

# Compila o projeto e cria o artefato JAR
RUN mvn clean package -DskipTests

# Stage 2: Executa a aplicação com Redis
FROM openjdk:17-jdk-slim

# Instala o Redis
RUN apt-get update && apt-get install -y redis-server

# Define o diretório de trabalho para a aplicação Java
WORKDIR /app

# Copia o JAR gerado do build anterior
COPY --from=builder /app/target/*.jar /app/app.jar

# Copia o arquivo de configuração do Redis
COPY redis.conf /etc/redis/redis.conf

# Define o comando para iniciar o Redis em background e a aplicação Java
CMD redis-server /etc/redis/redis.conf --daemonize yes && java -jar /app/app.jar
