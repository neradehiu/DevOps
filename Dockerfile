# =============================
# Stage 1: Build JAR file
# =============================
FROM gradle:8.8-jdk21 AS builder
# Cài thêm JDK 22 để Gradle có thể build với toolchain 22
USER root
RUN apt-get update && apt-get install -y wget unzip && \
    wget https://download.oracle.com/java/22/latest/jdk-22_linux-x64_bin.deb && \
    apt install -y ./jdk-22_linux-x64_bin.deb && \
    rm jdk-22_linux-x64_bin.deb

# Set JAVA_HOME cho Gradle nhận JDK 22
ENV JAVA_HOME=/usr/lib/jvm/jdk-22
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
