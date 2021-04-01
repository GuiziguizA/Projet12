@echo off

pushd C:\Programmation\Environnement\Projet12



start docker-compose up service-config 
timeout 300
start docker-compose up service-register
start docker-compose up rabbitmq

timeout 30
start docker-compose up database_user_competence

start docker-compose up database_creneaux_requetes
 
start docker-compose up database_chat_batch
timeout 60
start docker-compose up service-proxy
 
start docker-compose up service-user_competence

start docker-compose up service-chats_batch

start docker-compose up service-creneaux_requetes

start docker-compose up service-front_end 