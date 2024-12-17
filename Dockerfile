FROM openjdk:21
COPY .env /app/.env
COPY /target/history-service-0.0.1-SNAPSHOT.jar /app/history-service.jar
WORKDIR /app
EXPOSE 8083
CMD ["java", "-jar", "history-service.jar"]
