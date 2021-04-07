Prerequis:

- installer docker-machine version 0.16.1
- installer Docker version 18.09.3
windows:

-installer boot2Docker

Docker-machine 

- ouvrir boot2Docker : 

- creation d'une machine virtuel permettant d'accueillir les containers Dockers:

docker-machine create --driver "virtualbox" --virtualbox-memory=8192 --virtualbox-disk-size=80000 node1 

- connection a node1 (Machine créée)

eval $(docker-machine env manager)

-lancer le fichier docker-lancement.bat


