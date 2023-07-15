FROM maven:3.9.3 as builder

WORKDIR /app

ADD pom.xml .

# Cache dependencies in a layer
RUN mvn dependency:go-offline

ADD src src

RUN mvn clean install

FROM openjdk:17-ea-33-slim as release

COPY --from=builder /app/target/*.jar /app.jar

EXPOSE 8080

ENTRYPOINT java -jar /app.jar