package com.sap.ai.sdk.foundationmodels.anthropic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A content block in an Anthropic message.
 *
 * @since 1.26.0
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnthropicContentBlock {

  @Nullable
  @JsonProperty("type")
  private String type;

  @Nullable
  @JsonProperty("text")
  private String text;

  /**
   * Create a text content block.
   *
   * @param text the text.
   * @return a text content block.
   */
  @Nonnull
  public static AnthropicContentBlock ofText(@Nonnull final String text) {
    return new AnthropicContentBlock().setType("text").setText(text);
  }
}
