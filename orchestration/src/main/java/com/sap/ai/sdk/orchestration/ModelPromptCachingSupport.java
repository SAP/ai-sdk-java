package com.sap.ai.sdk.orchestration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;

/** Describes supported caching properties of a model */
class ModelPromptCachingSupport {

  private static final ModelPromptCachingSupport NOT_SUPPORTED =
      new ModelPromptCachingSupport(0, 0, "0m", "5m");

  private static final Map<String, ModelPromptCachingSupport> PROMPT_CACHING_SUPPORT =
      Collections.unmodifiableMap(
          new HashMap<>() {
            {
              put(
                  OrchestrationAiModel.CLAUDE_4_5_OPUS.getName(),
                  new ModelPromptCachingSupport(4096, 4, "5m|1h", "5m"));
              put(
                  OrchestrationAiModel.CLAUDE_4_6_OPUS.getName(),
                  new ModelPromptCachingSupport(4096, 4, "5m|1h", "5m"));
              put(
                  OrchestrationAiModel.CLAUDE_4_5_SONNET.getName(),
                  new ModelPromptCachingSupport(4096, 4, "5m|1h", "5m"));
              put(
                  OrchestrationAiModel.CLAUDE_4_6_SONNET.getName(),
                  new ModelPromptCachingSupport(1024, 4, "5m|1h", "5m"));
              put(
                  OrchestrationAiModel.CLAUDE_4_5_HAIKU.getName(),
                  new ModelPromptCachingSupport(4096, 4, "5m|1h", "5m"));
              put(
                  OrchestrationAiModel.CLAUDE_4_OPUS.getName(),
                  new ModelPromptCachingSupport(4096, 4, "5m", "5m"));
              put(
                  OrchestrationAiModel.CLAUDE_4_7_OPUS.getName(),
                  new ModelPromptCachingSupport(4096, 4, "5m|1h", "5m"));
              put(
                  OrchestrationAiModel.CLAUDE_4_8_OPUS.getName(),
                  new ModelPromptCachingSupport(4096, 4, "5m|1h", "5m"));
            }
          });

  /** Caching checkpoint can only be made for so few prompt input tokens and not fewer */
  @Getter private final int minTokensPerCheckpoint;

  /** Only up to this number of caching points can be created per request */
  @Getter private final int maxCheckpointsPerRequest;

  /** Pattern of supported TTL values, which can be passed */
  private final Pattern supportedTTLValues;

  /** Caching TTL value to use if TTL has not been explicitly specified */
  @Getter private final String defaultTTLValue;

  private ModelPromptCachingSupport(
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
  static ModelPromptCachingSupport forModel(@Nullable final String modelName) {
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
  static ModelPromptCachingSupport forModel(@Nonnull final OrchestrationAiModel model) {
    return forModel(model.getName());
  }

  /**
   * Explicit "no caching" configuration
   *
   * @return "no caching" prompt caching configuration
   */
  @Nonnull
  static ModelPromptCachingSupport noCaching() {
    return NOT_SUPPORTED;
  }

  /**
   * Checks if passed ttl value correct and supported
   *
   * @param ttlValue ttl value to check
   * @return true if ttlValue is supported, else false
   */
  boolean supportsTTLValue(final String ttlValue) {
    return supportedTTLValues.matcher(ttlValue).matches();
  }
}
