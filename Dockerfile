# Sử dụng image JDK chính thức
FROM eclipse-temurin:17-jdk-alpine

# Tạo thư mục làm việc
WORKDIR /app

# Copy file jar vào container
COPY target/*.jar app.jar

# Expose port Spring Boot chạy
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
