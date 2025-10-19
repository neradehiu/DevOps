# =============================
# Stage 1: Build JAR file
# =============================
FROM gradle:8.8-jdk21 AS builder

# Cài thêm OpenJDK 22 (Temurin)
USER root
RUN apt-get update && \
    apt-get install -y wget gnupg && \
    wget -O- https://packages.adoptium.net/artifactory/api/gpg/key/public | gpg --dearmor | tee /usr/share/keyrings/adoptium.gpg > /dev/null && \
    echo "deb [signed-by=/usr/share/keyrings/adoptium.gpg] https://packages.adoptium.net/artifactory/deb jammy main" | tee /etc/apt/sources.list.d/adoptium.list && \
    apt-get update && \
    apt-get install -y temurin-22-jdk && \
    apt-get clean

# Dùng JDK 22 làm mặc định
ENV JAVA_HOME=/usr/lib/jvm/temurin-22-jdk
ENV PATH="$JAVA_HOME/bin:$PATH"

WORKDIR /app
COPY . .
RUN gradle clean build -x test --no-daemon

# =============================
# Stage 2: Run the Spring Boot app
# =============================
FROM eclipse-temurin:22-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
