
FROM maven:3.8.4-openjdk-17 AS build

COPY . /app

WORKDIR /app

RUN mvn clean package -DskipTests

RUN ls -la /app/target

FROM openjdk:17

COPY --from=build /app/target/*.jar /app.jar

RUN ls -la /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
