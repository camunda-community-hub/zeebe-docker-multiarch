package org.camunda.community.zeebe.docker.multiarch;

import org.testcontainers.utility.DockerImageName;

public class Constants {
  private Constants() {}

  public static final DockerImageName TEST_IMAGE =
      DockerImageName.parse("aivinog1/zeebe-multiarch:test-image");
}
