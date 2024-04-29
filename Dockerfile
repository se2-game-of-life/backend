FROM --platform=linux/amd64 openjdk:17-jdk-slim

WORKDIR /app

COPY target/grp-3-0.0.1.jar gameoflife-backend.jar

ENTRYPOINT ["java", "-jar", "gameoflife-backend.jar"]

EXPOSE  8080