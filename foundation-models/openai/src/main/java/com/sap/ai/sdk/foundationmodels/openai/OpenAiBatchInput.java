package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.val;

/**
 * Represents a batch input for OpenAI chat completion requests.
 *
 * <p>This class converts a list of OpenAiChatCompletionRequest objects into JSONL format suitable
 * for batch processing.
 *
 * @since 1.20.0
 */
@Beta
@JsonSerialize(using = BatchInputSerializer.class)
public class OpenAiBatchInput {
  private static final ObjectMapper mapper = getDefaultObjectMapper();

  /** The list of individual batch requests */
  private final List<SingleRequest> requests = new ArrayList<>();

  /**
   * Constructs an OpenAiBatchInput from a list of OpenAiChatCompletionRequest objects.
   *
   * @param chatCompletionRequests the list of chat completion requests to include in the batch
   */
  public OpenAiBatchInput(@Nonnull final OpenAiChatCompletionRequest... chatCompletionRequests) {
    for (int i = 0; i < chatCompletionRequests.length; i++) {
      val request = chatCompletionRequests[i];
      val body = request.createCreateChatCompletionRequest();
      requests.add(new SingleRequest("request-" + (i + 1), "POST", "/v1/chat/completions", body));
    }
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder string = new StringBuilder();
    try {
      for (val request : requests) {
        string.append(mapper.writeValueAsString(request)).append("\n");
      }
      return string.toString();
    } catch (JsonProcessingException e) {
      return "Error serializing OpenAiBatchInput: " + e.getMessage();
    }
  }

  /**
   * Represents a single batch request in OpenAI batch format.
   *
   * @param customId a custom identifier for the request, used for tracking in batch processing
   * @param body the body of the request, which is a CreateChatCompletionRequest
   * @param method the HTTP method for the request, e.g., "POST"
   * @param url the endpoint URL for the request, e.g., "/v1/chat/completions"
   * @since 1.20.0
   */
  public record SingleRequest(
      @JsonProperty("custom_id") String customId,
      @JsonProperty("method") String method,
      @JsonProperty("url") String url,
      @JsonProperty("body") CreateChatCompletionRequest body) {}
}

/** Custom Jackson serializer for OpenAiBatchInput that outputs JSONL format. */
class BatchInputSerializer extends JsonSerializer<OpenAiBatchInput> {
  @Override
  public void serialize(
      final OpenAiBatchInput batchInput, final JsonGenerator gen, final SerializerProvider provider)
      throws IOException {
    // Write the JSONL content directly without string escaping
    gen.writeRaw(batchInput.toString());
  }
}
