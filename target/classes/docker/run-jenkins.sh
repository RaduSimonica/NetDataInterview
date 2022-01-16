#!/bin/bash

docker load < jenkins.tar
docker container run \
      -d \
      -p 8080:8080 \
      -v jenkins_vol1:/var/jenkins_home \
      -v /var/run/docker.sock:/var/run/docker.sock \
      -v $(which docker):/usr/bin/docker \
      --name jenkins \
      netdata/jenkins:1.0.3