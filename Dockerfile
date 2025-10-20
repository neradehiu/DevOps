# =========================
# Stage 1: Build with Gradle
# =========================
FROM gradle:8.14.3-jdk21 AS builder

WORKDIR /app

# Chỉ copy các file cần build (tránh copy toàn bộ folder .git, docs...)
COPY build.gradle settings.gradle gradle.properties ./
COPY gradle ./gradle
COPY src ./src

# Build project, skip tests
RUN gradle clean build -x test --no-daemon

# =========================
# Stage 2: Run the app (slim image)
# =========================
FROM eclipse-temurin:21-jdk-focal AS runtime

WORKDIR /app

# Copy jar từ builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
