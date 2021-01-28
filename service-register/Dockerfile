FROM maven:3.6.0-jdk-8-slim AS build

COPY src/ /home/app1/src
COPY pom.xml /home/app1
COPY mvnw.cmd /home/app1

WORKDIR /home/app1
RUN mvn clean install -DskipTests

#run the application
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY --from=build /home/app1/target/service-register-0.0.1-SNAPSHOT.jar /home/app1/service-register-0.0.1-SNAPSHOT.jar
WORKDIR /home/app1
#Run the application
CMD java -jar service-register-0.0.1-SNAPSHOT.jar