# syntax=docker/dockerfile:1
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY build/libs/receipt-processor-challenge-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]


HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
  CMD curl --fail http://localhost:8080/health || exit 1
