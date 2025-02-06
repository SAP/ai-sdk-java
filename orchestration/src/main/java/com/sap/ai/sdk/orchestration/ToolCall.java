package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.val;

/**
 * Represents a tool call.
 *
 * @since 1.3.0
 */
@NoArgsConstructor(onConstructor_ = @Beta)
@Accessors(fluent = true)
@Getter
@Setter
public class ToolCall {
  /** call id */
  private String id;

  /** "function" or "tool" */
  private String type;

  /** the name of the function to call */
  private String name;

  /** the arguments to pass to the function */
  private String arguments;

  /**
   * Creates a Map representation of the tool call.
   *
   * @return a Map representation of the tool call.
   */
  @Nonnull
  public Map<String, Object> map() {
    val function = Map.of("name", name, "arguments", arguments);
    return Map.of("id", id, "type", type, type, function);
  }
}
