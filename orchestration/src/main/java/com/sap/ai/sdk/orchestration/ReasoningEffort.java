package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;
import lombok.Getter;

/**
 * Reasoning effort levels for the harmonized {@code reasoning_effort} model parameter.
 *
 * <p>Not all reasoning-capable models support the full range of values. Where a model does not
 * support a given effort, orchestration transparently maps it to the closest equivalent.
 *
 * @see <a href="https://help.sap.com/docs/sap-ai-core/generative-ai/reasoning">SAP AI Core:
 *     Orchestration - Reasoning</a>
 */
public enum ReasoningEffort {
  /** Minimal reasoning effort. */
  MINIMAL("minimal"),
  /** Low reasoning effort. */
  LOW("low"),
  /** Medium reasoning effort. */
  MEDIUM("medium"),
  /** High reasoning effort. */
  HIGH("high"),
  /** Disable reasoning when the model supports doing so. */
  NONE("none");

  /**
   * The string sent as the value of the {@code reasoning_effort} model parameter.
   */
  @Getter private final String value;

  ReasoningEffort(@Nonnull final String value) {
    this.value = value;
  }
}
