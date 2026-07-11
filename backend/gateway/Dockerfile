# ==============================================================================
# Stage 1: 編譯階段 (Build Stage)
# 使用 Maven 官方映像檔進行原始碼編譯，不打包測試程式碼
# ==============================================================================
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
# 先下載依賴（利用 Docker layer cache 加速後續建置）
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests -B

# ==============================================================================
# Stage 2: 執行階段 (Runtime Stage)
# 使用輕量 JRE，僅複製編譯完的 JAR，縮小映像檔大小
# ==============================================================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
