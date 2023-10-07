FROM gradle:8.2.1-jdk17 AS builder
COPY . /usr/src
WORKDIR /usr/src
RUN gradle wrapper --gradle-version 8.2.1
RUN ./gradlew clean build

FROM openjdk:17-jdk-alpine
COPY --from=builder /usr/src/build/libs/dev-0.0.1-SNAPSHOT.jar /usr/app/app.jar
ENTRYPOINT ["java", "-jar", "/usr/app/app.jar"]