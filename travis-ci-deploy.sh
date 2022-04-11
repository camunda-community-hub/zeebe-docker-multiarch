#!/usr/bin/env bash

./mvnw package -DskipTests=true -Dmaven.javadoc.skip=true -B -V
# TODO: Deploy to the Maven Central
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker buildx build --no-cache --platform linux/amd64,linux/arm64 --push --build-arg DISTBALL=target/dependency/camunda-cloud-zeebe-*.tar.gz -t "aivinog1/zeebe-multiarch:$TRAVIS_TAG" --target app .

