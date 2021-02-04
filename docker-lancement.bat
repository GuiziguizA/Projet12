@echo off





 start docker-compose up service-config 
 timeout 20
 
 



start docker-compose up service-register

timeout 30
start docker-compose up database
 start docker-compose up database1
 start docker-compose up database2
timeout 30

start docker-compose up service-proxy
 
start docker-compose up service-user_competence

start docker-compose up service-chats_batch

start docker-compose up service-creneaux_requetes