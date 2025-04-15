FROM eclipse-temurin:21-jdk
LABEL authors="ash"
WORKDIR /app
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]