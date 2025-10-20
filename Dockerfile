# Stage 1: Build with Gradle
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app

# Copy chỉ những file cần thiết
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY src ./src

RUN gradle clean build -x test --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:21-jdk-alpine AS runtime
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
