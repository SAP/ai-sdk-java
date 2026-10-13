package com.sap.ai.sdk.foundationmodels.anthropic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.ai.sdk.core.common.StreamedDelta;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A single streamed event from the Anthropic {@code invoke-with-response-stream} endpoint.
 *
 * <p>The stream returns server-sent events containing Claude streaming delta events. Each event
 * carries one of: {@code message_start}, {@code content_block_start}, {@code content_block_delta},
 * {@code content_block_stop}, {@code message_delta}, or {@code message_stop}.
 *
 * @since 1.26.0
 */
@Data
@Accessors(chain = true)
public class AnthropicChatCompletionDelta implements StreamedDelta {

  @Nullable
  @JsonProperty("type")
  private String type;

  @Nullable
  @JsonProperty("index")
  private Integer index;

  @Nullable
  @JsonProperty("delta")
  private Delta delta;

  @Nullable
  @JsonProperty("usage")
  private AnthropicChatCompletionResponse.AnthropicUsage usage;

  /**
   * Get the streamed text delta content from this event.
   *
   * @return the text delta, or an empty string if this event does not carry text content.
   */
  @Nonnull
  @Override
  public String getDeltaContent() {
    if (delta == null) {
      return "";
    }
    final var text = delta.getText();
    return text != null ? text : "";
  }

  /**
   * Get the stop reason from a {@code message_delta} event, mapped to the finish reason.
   *
   * @return the stop/finish reason, or {@code null} if not present.
   */
  @Nullable
  @Override
  public String getFinishReason() {
    return getStopReason();
  }

  /**
   * Get the stop reason from a {@code message_delta} event.
   *
   * @return the stop reason, or {@code null} if not present.
   */
  @Nullable
  public String getStopReason() {
    return delta != null ? delta.getStopReason() : null;
  }

  /** The delta payload in a {@code content_block_delta} or {@code message_delta} event. */
  @Data
  @Accessors(chain = true)
  public static class Delta {

    @Nullable
    @JsonProperty("type")
    private String type;

    @Nullable
    @JsonProperty("text")
    private String text;

    @Nullable
    @JsonProperty("stop_reason")
    private String stopReason;

    @Nullable
    @JsonProperty("stop_sequence")
    private String stopSequence;
  }
}
