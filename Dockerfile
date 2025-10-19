# Stage 1: Build JAR file
FROM gradle:8.6-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle build -x test

# Stage 2: Run the Spring Boot app
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
