[![](https://img.shields.io/badge/Lifecycle-Proof%20of%20Concept-blueviolet)](https://github.com/Camunda-Community-Hub/community/blob/main/extension-lifecycle.md#proof-of-concept-)
[![Community extension badge](https://img.shields.io/badge/Community%20Extension-An%20open%20source%20community%20maintained%20project-FF4700)](https://github.com/camunda-community-hub/community)

# Zeebe Docker Multiarch

## Motivation
Since the Zeebe project [doesn't provide Docker images for ARM architectures](https://github.com/camunda/zeebe/issues/6155) I've decided to build it myself.

## Usage

To use it you just need to replace the `camunda/zeebe` docker image name with `aivinog1/zeebe-multiarch` with the proper(for the most cases - same) version.

## Version matrix
This is still in progress. On one hand: I want that version mapping between Zeebe and this project should be as easy as possible (this project version should be mapped to the same version in the Zeebe project), but I'm considering the cases when I need to add some functionality, or patch version, for example.
Also, I don't want to shift from the SemVer. So I've decided to stick with the same version, with an optional `-x` suffix, where `x` - positive number, describing the current patch version.

| Zeebe Version | Project version |
|---------------|-----------------|
| 1.2.11        | 1.2.11          |

## How to build
1. You have to have:
   1. JDK 11
   2. Docker
   3. [Buildx](https://docs.docker.com/buildx/working-with-buildx/) with prepared environment for multiarch builds (i.e. `docker buildx create --use`)
2. Build the project (note: you can't run tests until you build a multiplatform image) `./mvnw clean verify -DskipTests`
3. Build image for tests: `docker buildx build --no-cache --load --build-arg DISTBALL=target/dependency/camunda-cloud-zeebe-*.tar.gz -t aivinog1/zeebe-multiarch:test-image --target app .`
4. Run tests: `./mvnw verify`
