#Build environment
FROM openjdk:21-jdk-slim
COPY /target/hw17-spring-docker-1.0.jar /app/app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

