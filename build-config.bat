@echo off
for %%x in (

service-config/pom.xml
service-register/pom.xml
service-proxy/pom.xml
service-user_competence/pom.xml
service-creneaux_requetes/pom.xml
service-chats_batch/pom.xml
service-front_end/pom.xml

 ) do (
start mvn clean install -DskipTests -f  %%x
)
