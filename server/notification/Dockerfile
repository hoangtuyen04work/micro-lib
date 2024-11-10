# Build stage
FROM maven:3.9.9-amazoncorretto-21-alpine AS build

# Thiết lập thư mục làm việc cho giai đoạn build
WORKDIR /app

# Copy file pom.xml và thư mục src
COPY pom.xml .
COPY src ./src


# Run stage
FROM amazoncorretto:21.0.5-al2023

# Thiết lập thư mục làm việc cho container
WORKDIR /app

# Copy file JAR từ build stage
COPY --from=build /app/target/*.jar app.jar

# Mở cổng 8080
EXPOSE 8087

# Lệnh để khởi chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
