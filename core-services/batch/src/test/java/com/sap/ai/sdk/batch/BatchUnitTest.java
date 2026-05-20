package com.sap.ai.sdk.batch;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.sap.ai.sdk.batch.generated.model.BatchCreateRequest.TypeEnum.LLM_NATIVE;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.batch.generated.client.BatchesApi;
import com.sap.ai.sdk.batch.generated.model.BatchCreateRequest;
import com.sap.ai.sdk.batch.generated.model.BatchCreateRequestInput;
import com.sap.ai.sdk.batch.generated.model.BatchCreateRequestOutput;
import com.sap.ai.sdk.batch.generated.model.BatchCreateRequestSpec;
import java.util.UUID;
import lombok.val;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;

/**
 * Test that queries are on the right URL, with the right headers. Also check that the received
 * response is parsed correctly in the generated client.
 */
class BatchUnitTest extends WireMockTestServerBatch {
  private static final String AI_RESOURCE_GROUP = "ai-sdk-java-e2e";
  private static final UUID BATCH_ID = UUID.fromString("89307094-2cc9-4ef1-9e1b-0fe0733aeb3e");
  private static final UUID NEW_BATCH_ID = UUID.fromString("27126ef7-d176-4f29-84db-2d64c001ad63");

  @Test
  void testList() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/v2/llm-batch-service/v1/batches"))
            .withHeader("AI-Resource-Group", equalTo(AI_RESOURCE_GROUP))
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
                              "id": "89307094-2cc9-4ef1-9e1b-0fe0733aeb3e",
                              "type": "llm-native",
                              "provider": "azure-openai",
                              "created_at": "2026-05-06T13:04:39.732861Z",
                              "status": "COMPLETED"
                            }
                          ]
                        }
                        """)));

    val batchList = new BatchesApi(aiCoreService).listBatches(AI_RESOURCE_GROUP);

    assertThat(batchList.getCount()).isEqualTo(1);
    assertThat(batchList.getResources()).hasSize(1);

    val batch = batchList.getResources().get(0);

    assertThat(batch.getId()).isEqualTo(BATCH_ID);
    assertThat(batch.getType()).isEqualTo("llm-native");
    assertThat(batch.getProvider()).isEqualTo("azure-openai");
    assertThat(batch.getCreatedAt()).isEqualTo("2026-05-06T13:04:39.732861Z");
    assertThat(batch.getStatus()).isEqualTo("COMPLETED");
  }

  @Test
  void testGet() {
    wireMockServer.stubFor(
        get(urlPathEqualTo("/v2/llm-batch-service/v1/batches/" + BATCH_ID))
            .withHeader("AI-Resource-Group", equalTo(AI_RESOURCE_GROUP))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_OK)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "27126ef7-d176-4f29-84db-2d64c001ad63",
                          "type": "llm-native",
                          "provider": "azure-openai",
                          "created_at": "2026-05-06T13:56:19.257546Z",
                          "input": {
                            "uri": "ai://s3secret/input-batch.jsonl"
                          },
                          "output": {
                            "uri": "ai://s3secret/"
                          },
                          "spec": {
                            "model": "gpt-4.1"
                          },
                          "status": {
                            "current_status": "PENDING",
                            "target_status": "COMPLETED",
                            "updated_at": "2026-05-06T13:56:19.257546Z",
                            "message": null
                          }
                        }
                        """)));

    val batch = new BatchesApi(aiCoreService).getBatchById(AI_RESOURCE_GROUP, BATCH_ID);

    assertThat(batch).isNotNull();
    assertThat(batch.getId()).isEqualTo(NEW_BATCH_ID);
    assertThat(batch.getType()).isEqualTo("llm-native");
    assertThat(batch.getProvider()).isEqualTo("azure-openai");
    assertThat(batch.getCreatedAt()).isEqualTo("2026-05-06T13:56:19.257546Z");
    assertThat(batch.getStatus().getCurrentStatus()).isEqualTo("PENDING");
    assertThat(batch.getInput().getUri()).isEqualTo("ai://s3secret/input-batch.jsonl");
    assertThat(batch.getOutput().getUri()).isEqualTo("ai://s3secret/");
    assertThat(batch.getSpec()).isNotNull();
  }

  @Test
  void testCreate() {
    wireMockServer.stubFor(
        post(urlPathEqualTo("/v2/llm-batch-service/v1/batches"))
            .withHeader("AI-Resource-Group", equalTo(AI_RESOURCE_GROUP))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_ACCEPTED)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "27126ef7-d176-4f29-84db-2d64c001ad63",
                          "created_at": "2026-05-06T13:56:19.257546+00:00",
                          "status": "PENDING",
                          "message": "Batch job scheduled"
                        }
                        """)));

    val input = BatchCreateRequestInput.create().uri("ai://s3secret/input-batch.jsonl");
    val output = BatchCreateRequestOutput.create().uri("ai://s3secret/");
    val spec = BatchCreateRequestSpec.create().provider("azure-openai").model("gpt-4.1");

    val batchCreateRequest =
        BatchCreateRequest.create().type(LLM_NATIVE).input(input).output(output).spec(spec);

    val createResponse =
        new BatchesApi(aiCoreService).createBatch(AI_RESOURCE_GROUP, batchCreateRequest);

    assertThat(createResponse.getId()).isEqualTo(NEW_BATCH_ID);
    assertThat(createResponse.getCreatedAt()).isEqualTo("2026-05-06T13:56:19.257546+00:00");
    assertThat(createResponse.getStatus()).isEqualTo("PENDING");
    assertThat(createResponse.getMessage()).isEqualTo("Batch job scheduled");
  }

  @Test
  void testDelete() {
    wireMockServer.stubFor(
        delete(urlPathEqualTo("/v2/llm-batch-service/v1/batches/" + BATCH_ID))
            .withHeader("AI-Resource-Group", equalTo(AI_RESOURCE_GROUP))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.SC_ACCEPTED)
                    .withHeader("content-type", "application/json")
                    .withBody(
                        """
                        {
                          "id": "89307094-2cc9-4ef1-9e1b-0fe0733aeb3e",
                          "created_at": "2026-05-06T13:04:39.732861Z",
                          "message": "Batch job deleted successfully"
                        }
                        """)));

    val deleteResponse = new BatchesApi(aiCoreService).deleteBatch(AI_RESOURCE_GROUP, BATCH_ID);

    assertThat(deleteResponse.getId()).isEqualTo(BATCH_ID);
    assertThat(deleteResponse.getCreatedAt()).isEqualTo("2026-05-06T13:04:39.732861Z");
    assertThat(deleteResponse.getMessage()).isEqualTo("Batch job deleted successfully");
  }
}
