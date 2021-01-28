FROM maven:3.6.0-jdk-8-slim AS build


COPY src/ /home/user_competence/src
COPY pom.xml /home/user_competence
COPY mvnw.cmd /home/user_competence

WORKDIR /home/user_competence
RUN mvn clean install -DskipTests

#run the application
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY --from=build /home/user_competence/target/service-user_competence-0.0.1-SNAPSHOT.jar /home/user_competence/service-user_competence-0.0.1-SNAPSHOT.jar
WORKDIR /home/user_competence
#Run the application
CMD java -jar service-user_competence-0.0.1-SNAPSHOT.jar