# Stage 1: Build JAR file
FROM gradle:8.7-jdk22 AS builder
WORKDIR /app
COPY . .
RUN gradle build -x test

# Stage 2: Run the Spring Boot app
FROM eclipse-temurin:22-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
