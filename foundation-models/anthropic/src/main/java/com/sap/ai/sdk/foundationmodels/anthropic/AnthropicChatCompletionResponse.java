package com.sap.ai.sdk.foundationmodels.anthropic;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A response from the Anthropic Messages API.
 *
 * @since 1.26.0
 */
@Data
@Accessors(chain = true)
public class AnthropicChatCompletionResponse {

  @Nullable
  @JsonProperty("id")
  private String id;

  @Nullable
  @JsonProperty("type")
  private String type;

  @Nullable
  @JsonProperty("role")
  private String role;

  @Nullable
  @JsonProperty("content")
  private List<AnthropicContentBlock> content;

  @Nullable
  @JsonProperty("model")
  private String model;

  @Nullable
  @JsonProperty("stop_reason")
  private String stopReason;

  @Nullable
  @JsonProperty("stop_sequence")
  private String stopSequence;

  @Nullable
  @JsonProperty("usage")
  private AnthropicUsage usage;

  /**
   * Get the first text content from the response.
   *
   * @return the text content, or an empty string if not present.
   */
  @Nonnull
  public String getContent() {
    if (content == null || content.isEmpty()) {
      return "";
    }
    final var text = content.get(0).getText();
    return text != null ? text : "";
  }

  /**
   * Token usage information from the Anthropic Messages API response.
   *
   * @since 1.26.0
   */
  @Data
  @Accessors(chain = true)
  public static class AnthropicUsage {

    @Nullable
    @JsonProperty("input_tokens")
    private Integer inputTokens;

    @Nullable
    @JsonProperty("output_tokens")
    private Integer outputTokens;
  }
}
