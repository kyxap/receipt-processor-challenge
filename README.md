# Receipt Processor

Spring Boot webservice that calculates receipt points.

## 🔧 Requirements

- Java 17
- Docker

## 🏃 Run Locally

Run unit Tests

```bash
./gradlew test 
```

Run Integration AKA E2E Tests (requires service deployed locally via `bootRun` or in Docker)

```bash
./gradlew testE2E
```

Build jar

```bash
./gradlew build
```

Run Service

```bash
./gradlew clean bootRun
```

## Deploy locally

Build jar

```bash
./gradlew build
```

Build docker image

```bash
docker build -t rkukharuk-to-hire .
```

Run service in docker

```bash
docker run -p 8080:8080 rkukharuk-to-hire .
```

Run e2e tests against local service, see service logs for output

```bsash
./gradlew testE2E
```
