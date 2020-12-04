#!/bin/bash

# ./build-metabase.sh

export ANSWERS_VERSION=0.0.1

docker login registry.cloud.bigconnect.io
docker build -t bigconnect-answers:$ANSWERS_VERSION -f Dockerfile .
docker tag bigconnect-answers:$ANSWERS_VERSION registry.cloud.bigconnect.io/bigconnect-answers:$ANSWERS_VERSION
docker push registry.cloud.bigconnect.io/bigconnect-answers:$ANSWERS_VERSION
