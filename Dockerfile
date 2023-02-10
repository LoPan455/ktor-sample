# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY ./build/libs/ktor-sample-0.0.7-cve-2021-29425.jar .

CMD ["java", "--jar ./app/ktor-sample-0.0.7-cve-2021-29425.jar"]