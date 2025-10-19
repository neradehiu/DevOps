# ==========================================
# Stage 1: Build JAR file bằng Gradle
# ==========================================
FROM gradle:8.6-jdk22 AS builder

# Tạo thư mục làm việc
WORKDIR /app

# Copy toàn bộ mã nguồn vào container
COPY . .

# Build project (bỏ qua test để build nhanh hơn)
RUN gradle clean build -x test


# ==========================================
# Stage 2: Run ứng dụng Spring Boot
# ==========================================
FROM eclipse-temurin:22-jdk

# Thư mục làm việc trong container
WORKDIR /app

# Copy file jar từ stage builder sang
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose port mà Spring Boot chạy
EXPOSE 8080

# Lệnh khởi chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
