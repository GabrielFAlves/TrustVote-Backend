FROM openjdk:17-jdk-slim

WORKDIR /app

# Ajustar caminhos para subpasta
COPY votacao-backend/mvnw votacao-backend/pom.xml ./
COPY votacao-backend/.mvn .mvn

RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline -B

COPY votacao-backend/src src

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/votacao-backend-0.0.1-SNAPSHOT.jar"]