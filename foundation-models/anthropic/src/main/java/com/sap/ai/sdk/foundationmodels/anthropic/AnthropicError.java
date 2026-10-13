package com.sap.ai.sdk.foundationmodels.anthropic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.ai.sdk.core.common.ClientError;
import javax.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Represents an error response from the Anthropic Messages API.
 *
 * @since 1.26.0
 */
@Data
@Accessors(chain = true)
public class AnthropicError implements ClientError {

  @Nullable
  @JsonProperty("message")
  private String message;

  @Nullable
  @JsonProperty("type")
  private String type;
}
