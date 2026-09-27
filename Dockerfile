FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /project
COPY . /project/
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /project/target/*-jar-with-dependencies.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]