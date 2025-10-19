# ==========================================
# Stage 1: Build JAR file bằng Gradle
# ==========================================
FROM gradle:8.6-jdk21 AS builder

WORKDIR /app
COPY . .
RUN gradle clean build -x test


# ==========================================
# Stage 2: Run ứng dụng Spring Boot
# ==========================================
FROM eclipse-temurin:22-jdk

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
