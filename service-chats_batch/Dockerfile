FROM maven:3.6.0-jdk-8-slim AS build


COPY src/ /home/chats_batch/src
COPY pom.xml /home/chats_batch
COPY mvnw.cmd /home/chats_batch

WORKDIR /home/chats_batch
RUN mvn clean install -DskipTests

#run the application
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY --from=build /home/chats_batch/target/service-chats_batch-0.0.1-SNAPSHOT.jar /home/chats_batch/service-chats_batch-0.0.1-SNAPSHOT.jar
WORKDIR /home/chats_batch
#Run the application
CMD java -jar service-chats_batch-0.0.1-SNAPSHOT.jar