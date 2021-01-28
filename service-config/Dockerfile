FROM maven:3.6.0-jdk-8-slim AS build


COPY src/ /home/app/src
COPY pom.xml /home/app
COPY mvnw.cmd /home/app

WORKDIR /home/app
RUN mvn clean install -DskipTests

#run the application
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY --from=build /home/app/target/service-config-0.0.1-SNAPSHOT.jar /home/app/service-config-0.0.1-SNAPSHOT.jar
WORKDIR /home/app
#Run the application
CMD java -jar service-config-0.0.1-SNAPSHOT.jar