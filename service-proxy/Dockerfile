FROM maven:3.6.0-jdk-8-slim AS build


COPY src/ /home/proxy/src
COPY pom.xml /home/proxy
COPY mvnw.cmd /home/proxy

WORKDIR /home/proxy
RUN mvn clean install -DskipTests

#run the application
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY --from=build /home/proxy/target/service-proxy-0.0.1-SNAPSHOT.jar /home/proxy/service-proxy-0.0.1-SNAPSHOT.jar
WORKDIR /home/proxy
#Run the application
CMD java -jar service-proxy-0.0.1-SNAPSHOT.jar