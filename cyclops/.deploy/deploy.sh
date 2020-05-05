#!/bin/bash

mkdir -p /var/log/cyclops-app

{
    source /etc/profile
    echo docker login $GITLAB_REGISTRY

    pushd /home/cyclops/cyclops
    echo "Git pull"
    git pull https://$GITLAB_USER:$GITLAB_TOKEN@gitlab.com/asolism/cyclops && echo Success

    echo "Docker login"
    echo docker login $GITLAB_REGISTRY
    docker login -u $GITLAB_USER -p $GITLAB_TOKEN $GITLAB_REGISTRY

    echo docker-compose up
    docker-compose pull
    docker-compose up

    popd
    echo
    echo
    echo
    echo
} 2>&1 | tee -a /var/log/cyclops-app/logfile



