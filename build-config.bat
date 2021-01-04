@echo off
for %%x in (

service-config/pom.xml
service-register/pom.xml

 ) do (
start mvn clean install -f %%x
)
