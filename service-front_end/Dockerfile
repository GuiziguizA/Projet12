FROM maven:3.6.0-jdk-8-slim AS build


COPY src/ /home/front_end/src
COPY pom.xml /home/front_end
COPY mvnw.cmd /home/front_end

WORKDIR /home/front_end
RUN mvn clean install -DskipTests

#run the application
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY --from=build /home/front_end/target/service-front_end-0.0.1-SNAPSHOT.jar /home/front_end/service-front_end-0.0.1-SNAPSHOT.jar
WORKDIR /home/front_end
#Run the application
CMD java -jar service-front_end-0.0.1-SNAPSHOT.jar