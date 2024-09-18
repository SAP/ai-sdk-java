package com.sap.ai.sdk.core.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.sap.ai.sdk.core.Core.getClient;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.core.WireMockTestServer;
import com.sap.ai.sdk.core.client.model.AiArtifact;
import com.sap.ai.sdk.core.client.model.AiArtifactCreationResponse;
import com.sap.ai.sdk.core.client.model.AiArtifactList;
import com.sap.ai.sdk.core.client.model.AiArtifactPostData;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;

/**
 * Test that queries are on the right URL, with the right headers. Also check that the received
 * response is parsed correctly in the generated client.
 */
public class ArtifactUnitTest extends WireMockTestServer {
  @Test
  void testGetArtifact() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/lm/artifacts"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_OK)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                          "count": 1,
                          "resources": [
                            {
                              "createdAt": "2024-05-22T07:40:30Z",
                              "description": "dataset for aicore training",
                              "id": "744b0136-ed4b-49b1-bd10-08c236ed5ce7",
                              "kind": "dataset",
                              "modifiedAt": "2024-05-22T07:40:30Z",
                              "name": "default",
                              "scenarioId": "foundation-models",
                              "url": "ai://default/spam/data"
                            }
                          ]
                        }
                        """)));

    final AiArtifactList artifactList =
        new ArtifactApi(getClient(destination)).artifactQuery("default");
    assertThat(artifactList).isNotNull();
    assertThat(artifactList.getCount()).isEqualTo(1);
    assertThat(artifactList.getResources().size()).isEqualTo(1);
    AiArtifact artifact = artifactList.getResources().get(0);
    assertThat(artifact.getCreatedAt()).isEqualTo("2024-05-22T07:40:30Z");
    assertThat(artifact.getDescription()).isEqualTo("dataset for aicore training");
    assertThat(artifact.getId()).isEqualTo("744b0136-ed4b-49b1-bd10-08c236ed5ce7");
    assertThat(artifact.getKind()).isEqualTo(AiArtifact.KindEnum.DATASET);
    assertThat(artifact.getModifiedAt()).isEqualTo("2024-05-22T07:40:30Z");
    assertThat(artifact.getName()).isEqualTo("default");
    assertThat(artifact.getScenarioId()).isEqualTo("foundation-models");
    assertThat(artifact.getUrl()).isEqualTo("ai://default/spam/data");
  }

  @Test
  void testPostArtifact() {
    wireMockServer.stubFor(
        post(urlPathEqualTo("/lm/artifacts"))
            .withHeader("AI-Resource-Group", equalTo("default"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_OK)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "1a84bb38-4a84-4d12-a5aa-300ae7d33fb4",
                          "message": "AiArtifact acknowledged",
                          "url": "ai://default/spam/data"
                        }
                        """)));

    AiArtifactPostData artifactPostData =
        AiArtifactPostData.create()
            .name("default")
            .kind(AiArtifactPostData.KindEnum.DATASET)
            .url("ai://default/spam/data")
            .scenarioId("foundation-models")
            .description("dataset for aicore training");
    final AiArtifactCreationResponse artifact =
        new ArtifactApi(getClient(destination)).artifactCreate("default", artifactPostData);
    assertThat(artifact).isNotNull();
    assertThat(artifact.getId()).isEqualTo("1a84bb38-4a84-4d12-a5aa-300ae7d33fb4");
    assertThat(artifact.getMessage()).isEqualTo("AiArtifact acknowledged");
    assertThat(artifact.getUrl()).isEqualTo("ai://default/spam/data");
  }
}
