#!/bin/bash
docker build . -t micronaut-person
mkdir -p build
docker run --rm --entrypoint cat micronaut-person  /home/application/function.zip > build/function.zip
