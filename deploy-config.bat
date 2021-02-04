@echo on





start java -jar service-config\target\service-config-0.0.1-SNAPSHOT.jar

timeout 20
start java -jar service-register\target\service-register-0.0.1-SNAPSHOT.jar

timeout 30
start java -jar service-proxy\target\service-proxy-0.0.1-SNAPSHOT.jar



 

