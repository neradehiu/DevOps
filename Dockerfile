# =========================
# Stage 1: Build with Gradle
# =========================
FROM gradle:8.14.3-jdk21 AS builder

WORKDIR /app
COPY . .

# Build project (skip tests)
RUN gradle clean build -x test --no-daemon

# =========================
# Stage 2: Run the app
# =========================
FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
