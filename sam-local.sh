#!/bin/sh
docker build . -t micronaut-person
mkdir -p build
docker run --rm --entrypoint cat micronaut-person  /home/application/function.zip > build/function.zip

sam local start-api -t sam.yaml -p 3000

