# =============== Stage 1: Build ===============
FROM gradle:8.5-jdk21-alpine AS builder
WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY src ./src

RUN gradle clean build -x test --no-daemon

# =============== Stage 2: Runtime ===============
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
