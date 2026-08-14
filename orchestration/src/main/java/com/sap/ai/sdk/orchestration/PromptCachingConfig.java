package com.sap.ai.sdk.orchestration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Value;

/** Describes supported caching properties of a model */
@Value
public class PromptCachingConfig {

  private static final PromptCachingConfig NOT_SUPPORTED =
      new PromptCachingConfig(0, 0, "0m", "5m");

  private static final Map<String, PromptCachingConfig> PROMPT_CACHING_SUPPORT =
      Collections.unmodifiableMap(
          new HashMap<>() {
            {
              put(
                  OrchestrationAiModel.CLAUDE_4_5_OPUS.getName(),
                  new PromptCachingConfig(4096, 4, "5m|1h", "5m"));
              put(
                  OrchestrationAiModel.CLAUDE_4_6_OPUS.getName(),
                  new PromptCachingConfig(4096, 4, "5m|1h", "5m"));
              put(
                  OrchestrationAiModel.CLAUDE_4_5_SONNET.getName(),
                  new PromptCachingConfig(4096, 4, "5m|1h", "5m"));
              put(
                  OrchestrationAiModel.CLAUDE_4_6_SONNET.getName(),
                  new PromptCachingConfig(1024, 4, "5m|1h", "5m"));
              put(
                  OrchestrationAiModel.CLAUDE_4_5_HAIKU.getName(),
                  new PromptCachingConfig(4096, 4, "5m|1h", "5m"));
              put(
                  OrchestrationAiModel.CLAUDE_4_OPUS.getName(),
                  new PromptCachingConfig(4096, 4, "5m", "5m"));
              put(
                  OrchestrationAiModel.CLAUDE_4_7_OPUS.getName(),
                  new PromptCachingConfig(4096, 4, "5m|1h", "5m"));
              put(
                  OrchestrationAiModel.CLAUDE_4_8_OPUS.getName(),
                  new PromptCachingConfig(4096, 4, "5m|1h", "5m"));
            }
          });

  /** Caching checkpoint can only be made for so few prompt input tokens and not fewer */
  int minTokensPerCheckpoint;

  /** Only up to this number of caching points can be created per request */
  int maxCheckpointsPerRequest;

  /** Pattern of supported TTL values, which can be passed */
  Pattern supportedTTLValues;

  /** Caching TTL value to use if TTL has not been explicitly specified */
  String defaultTTLValue;

  private PromptCachingConfig(
      final int minTokensPerCheckpoint,
      final int maxCheckpointsPerRequest,
      final String ttlPattern,
      final String defaultTTLValue) {
    this.minTokensPerCheckpoint = minTokensPerCheckpoint;
    this.maxCheckpointsPerRequest = maxCheckpointsPerRequest;
    this.supportedTTLValues = Pattern.compile(ttlPattern);
    this.defaultTTLValue = defaultTTLValue;
  }

  /**
   * Factory method, returns instance of PromptCachingConfig with possible caching configurations.
   * If model does not support caching, a special instance will be returned
   *
   * @param modelName - model name to use
   * @return model prompt caching configuration
   */
  @Nonnull
  public static PromptCachingConfig forModel(@Nullable final String modelName) {
    if (modelName == null || modelName.isEmpty()) {
      return NOT_SUPPORTED;
    }
    return PROMPT_CACHING_SUPPORT.getOrDefault(modelName, NOT_SUPPORTED);
  }

  /**
   * Factory method, returns instance of PromptCachingConfig with possible caching configurations.
   * If model does not support caching, a special instance will be returned
   *
   * @param model - model to use
   * @return model prompt caching configuration
   */
  @Nonnull
  public static PromptCachingConfig forModel(@Nonnull final OrchestrationAiModel model) {
    return forModel(model.getName());
  }

  /**
   * Explicit "no caching" configuration
   *
   * @return "no caching" prompt caching configuration
   */
  @Nonnull
  public static PromptCachingConfig noCaching() {
    return NOT_SUPPORTED;
  }
}
