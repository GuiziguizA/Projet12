@echo off

pushd C:\Program Files\Docker Toolbox
start.sh






timeout 30

pushd C:\Programmation\Environnement\Projet12

start docker-compose up -d service-config 
timeout 300
start docker-compose up -d service-register
start docker-compose up -d rabbitmq


timeout 30

start docker-compose up -d database_user_competence

start docker-compose up -d database_creneaux_requetes
 
start docker-compose up -d database_chat_batch
timeout 60
start docker-compose up -d service-proxy
 
start docker-compose up -d service-user_competence

start docker-compose up -d service-chats_batch

start docker-compose up -d service-creneaux_requetes

start docker-compose up -d service-front_end 