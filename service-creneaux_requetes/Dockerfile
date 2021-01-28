FROM maven:3.6.0-jdk-8-slim AS build


COPY src/ /home/creneaux_requetes/src
COPY pom.xml /home/creneaux_requetes
COPY mvnw.cmd /home/creneaux_requetes

WORKDIR /home/creneaux_requetes
RUN mvn clean install -DskipTests

#run the application
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY --from=build /home/creneaux_requetes/target/service-creneaux_requetes-0.0.1-SNAPSHOT.jar /home/creneaux_requetes/service-creneaux_requetes-0.0.1-SNAPSHOT.jar
WORKDIR /home/creneaux_requetes
#Run the application
CMD java -jar service-creneaux_requetes-0.0.1-SNAPSHOT.jar