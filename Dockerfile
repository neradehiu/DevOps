# =========================
# Stage 1: Build with Gradle
# =========================
FROM gradle:8.14.3-jdk21 AS builder

# Set working directory
WORKDIR /app

# Copy Gradle wrapper + build files first for caching
COPY build.gradle settings.gradle gradle.properties ./
COPY gradle ./gradle

# Copy source code
COPY src ./src

# Build project (skip tests)
RUN gradle clean build -x test --no-daemon

# =========================
# Stage 2: Run the app
# =========================
# Use lightweight runtime image
FROM eclipse-temurin:21-jdk-alpine AS runtime

WORKDIR /app

# Copy built jar from builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
