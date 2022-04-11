package org.camunda.community.zeebe.docker.multiarch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.community.zeebe.docker.multiarch.Constants.TEST_IMAGE;

import io.camunda.zeebe.client.api.response.Topology;
import io.zeebe.containers.cluster.ZeebeCluster;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class TopologyTest {

  @ParameterizedTest
  @ArgumentsSource(TopologyArguments.class)
  void shouldCreateSuccessfulTopology(final ZeebeCluster zeebeCluster) {
    zeebeCluster.start();
    try (var client = zeebeCluster.newClientBuilder().usePlaintext().build()) {
      final Topology topology = client.newTopologyRequest().send().join();
      assertThat(topology.getBrokers().size()).isEqualTo(zeebeCluster.getBrokers().size());
      assertThat(topology.getPartitionsCount()).isEqualTo(zeebeCluster.getPartitionsCount());
      assertThat(topology.getReplicationFactor()).isEqualTo(zeebeCluster.getReplicationFactor());
    } finally {
      zeebeCluster.stop();
    }
  }

  static class TopologyArguments implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
      return Stream.of(
          Arguments.of(
              ZeebeCluster.builder()
                  .withBrokersCount(1)
                  .withImage(TEST_IMAGE)
                  .withEmbeddedGateway(true)
                  .build()),
          Arguments.of(
              ZeebeCluster.builder()
                  .withBrokersCount(3)
                  .withGatewaysCount(1)
                  .withReplicationFactor(3)
                  .withImage(TEST_IMAGE)
                  .withEmbeddedGateway(false)
                  .build()));
    }
  }
}
