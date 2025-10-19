# =========================
# Stage 1: Build with Gradle
# =========================
FROM gradle:8.10.2-jdk-22 AS builder

WORKDIR /app
COPY . .

# Build project (skip tests)
RUN gradle clean build -x test --no-daemon

# =========================
# Stage 2: Run the app
# =========================
FROM eclipse-temurin:22-jdk

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
