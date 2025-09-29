FROM openjdk:17-jdk-slim

# Метаинформация
LABEL maintainer="K.V.Afanasyev@gmail.com"
LABEL version="1.0"
LABEL description="Device Management"

WORKDIR /app

# Копируем JAR
COPY target/devicemanagement-3.5.6.jar app.jar

# Настройки

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]