# Usa uma imagem do Java 17 leve
FROM eclipse-temurin:17-jdk-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o seu arquivo JAR gerado para dentro do container
# O nome deve ser igual ao que vimos na sua pasta target
COPY target/usuarios-api-1.0.0.jar app.jar

# Expõe a porta que o Spring usa
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]