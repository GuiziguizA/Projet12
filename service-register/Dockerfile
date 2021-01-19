FROM maven:3.6.0-jdk-8-slim AS build

COPY src/ /home/app1/src
COPY pom.xml /home/app1
COPY mvnw.cmd /home/app1

WORKDIR /home/app1
RUN mvn clean install -DskipTests

#run the application
FROM openjdk:8-jdk-alpine
VOLUME /tmp
CMD ["java", "-jar", "target/service-config-0.0.1-SNAPSHOT.jar"]


