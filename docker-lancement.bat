@echo off

docker-compose
 start docker-compose up service-config 

timeout 10
 start docker-compose up service-register

timeout 20
start docker-compose up service-proxy

timeout 10
start docker-compose up service-user_competence
start docker-compose up service-chats_batch
start docker-compose up service-creneaux_requetes