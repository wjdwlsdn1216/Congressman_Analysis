#!/bin/bash

sudo xhost +local:root
#sudo docker pull nvcr.io/nvidia/pytorch:21.08-py3

container_name="roadking"
docker_image="wjdwlsdn1216/roadking:latest"

# xhost +

echo ${container_name} | cat > ./container_name.txt

sudo docker run -dit \
--name=${container_name} \
--net=host \
--privileged \
--ipc=host \
--gpus all \
-w /works \
-v /home/roadking/works:/works \
-e DISPLAY=$DISPLAY \
-v /tmp/.X11-unix:/tmp/.X11-unix \
-v /home/$USER/.Xauthority:/root/.Xauthority:rw \
${docker_image} bash && \
sudo docker cp ./container_name.txt ${container_name}:/ && \
rm ./container_name.txt && \
sudo docker exec -it ${container_name} bash
