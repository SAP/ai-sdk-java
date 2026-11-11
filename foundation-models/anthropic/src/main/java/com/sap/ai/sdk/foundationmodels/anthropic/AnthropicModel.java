package com.sap.ai.sdk.foundationmodels.anthropic;

import com.sap.ai.sdk.core.AiModel;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Anthropic Claude models available via SAP AI Core.
 *
 * <p>Please note that the list of models provided here might be outdated. Refer to the SAP AI Core
 * documentation for the latest model availability.
 *
 * @param name the name of the model.
 * @param version the version of the model (optional).
 * @since 1.26.0
 */
public record AnthropicModel(@Nonnull String name, @Nullable String version) implements AiModel {

  /** Anthropic Claude 3 Haiku. */
  public static final AnthropicModel CLAUDE_3_HAIKU =
      new AnthropicModel("anthropic--claude-3-haiku", null);

  /** Anthropic Claude 4 Sonnet. */
  public static final AnthropicModel CLAUDE_4_SONNET =
      new AnthropicModel("anthropic--claude-4-sonnet", null);

  /** Anthropic Claude 4.5 Haiku. */
  public static final AnthropicModel CLAUDE_45_HAIKU =
      new AnthropicModel("anthropic--claude-4.5-haiku", null);

  /** Anthropic Claude 4.5 Sonnet. */
  public static final AnthropicModel CLAUDE_45_SONNET =
      new AnthropicModel("anthropic--claude-4.5-sonnet", null);

  /** Anthropic Claude 4.5 Opus. */
  public static final AnthropicModel CLAUDE_45_OPUS =
      new AnthropicModel("anthropic--claude-4.5-opus", null);

  /** Anthropic Claude 4.6 Sonnet. */
  public static final AnthropicModel CLAUDE_46_SONNET =
      new AnthropicModel("anthropic--claude-4.6-sonnet", null);

  /** Anthropic Claude 4.6 Opus. */
  public static final AnthropicModel CLAUDE_46_OPUS =
      new AnthropicModel("anthropic--claude-4.6-opus", null);

  /** Anthropic Claude 4.8 Opus. */
  public static final AnthropicModel CLAUDE_48_OPUS =
      new AnthropicModel("anthropic--claude-4.8-opus", null);

  /**
   * Create a new instance of AnthropicModel with the provided version.
   *
   * @param version the version of the model.
   * @return the new instance of AnthropicModel.
   */
  @Nonnull
  public AnthropicModel withVersion(@Nonnull final String version) {
    return new AnthropicModel(name, version);
  }
}
