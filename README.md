[![Java CI with Gradle](https://github.com/alinasadness-cpu/bank-api-tests/actions/workflows/gradle.yml/badge.svg)](https://github.com/alinasadness-cpu/bank-api-tests/actions/workflows/gradle.yml)

# Автотесты для тестового режима интернет-банка

## Технологии
- Java 11
- JUnit 5
- RestAssured (API)
- Selenide (UI)
- JavaFaker
- Lombok
- Gson
- Gradle

## Запуск

1. Запустите сервис в тестовом режиме:
```bash
java -jar artifacts/app-ibank.jar -P:profile=test
