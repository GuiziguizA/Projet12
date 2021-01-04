@echo on





for %%x in (

service-config\target\service-config-0.0.1-SNAPSHOT.jar
service-register\target\service-register-0.0.1-SNAPSHOT.jar



  ) do (
start java -jar %%x
)

