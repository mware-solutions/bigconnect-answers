#!/bin/bash

rm -rf /tmp/answers-build
mkdir -p /tmp/answers-build

docker build -t bigconnect-answers-build:0.0.1 -f Dockerfile.build .
docker run -v /tmp/answers-build:/build -it bigconnect-answers-build:0.0.1 cp /app/source/target/uberjar/metabase.jar /build
docker image rm -f bigconnect-answers-build:0.0.1

