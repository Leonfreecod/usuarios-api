# Estágio 1: Build (Usando JDK 21 para compilar)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
# Boa prática: Copiar apenas o pom.xml primeiro para baixar as dependências e usar o cache do Docker
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Runtime (Imagem final leve)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# A Render exige que a aplicação ouça na porta que ela definir
EXPOSE 8080

# Adicionando flags de otimização de memória para ambientes Cloud
ENTRYPOINT ["java", "-Xmx512m", "-Dserver.port=${PORT:8080}", "-jar", "app.jar"]
