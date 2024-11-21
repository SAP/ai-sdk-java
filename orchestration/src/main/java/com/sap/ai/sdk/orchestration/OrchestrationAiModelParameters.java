package com.sap.ai.sdk.orchestration;

import java.util.Map;
import javax.annotation.Nonnull;

/** Helper interface to define typed parameters. */
@FunctionalInterface
public interface OrchestrationAiModelParameters {
  /**
   * Get the parameters.
   *
   * @return the parameters.
   */
  @Nonnull
  Map<String, Object> getParams();

  /** GPT model parameters. */
  interface GPT extends OrchestrationAiModelParameters {
    /**
     * Create a new builder.
     *
     * @return the builder.
     */
    @Nonnull
    static GPT.Builder0 params() {
      return maxTokens ->
          temperature ->
              frequencyPenalty ->
                  presencePenalty ->
                      () ->
                          Map.of(
                              "max_tokens", maxTokens,
                              "temperature", temperature,
                              "frequency_penalty", frequencyPenalty,
                              "presence_penalty", presencePenalty);
    }

    /** Builder for GPT model parameters. */
    interface Builder0 {
      /**
       * Set the max tokens.
       *
       * @param maxTokens the max tokens.
       * @return the next builder.
       */
      @Nonnull
      GPT.Builder1 maxTokens(@Nonnull final Number maxTokens);
    }

    /** Builder for GPT model parameters. */
    interface Builder1 {
      /**
       * Set the temperature.
       *
       * @param temperature the temperature.
       * @return the next builder.
       */
      @Nonnull
      GPT.Builder2 temperature(@Nonnull final Number temperature);
    }

    /** Builder for GPT model parameters. */
    interface Builder2 {
      /**
       * Set the frequency penalty.
       *
       * @param frequencyPenalty the frequency penalty.
       * @return the next builder.
       */
      @Nonnull
      GPT.Builder3 frequencyPenalty(@Nonnull final Number frequencyPenalty);
    }

    /** Builder for GPT model parameters. */
    interface Builder3 {
      /**
       * Set the presence penalty.
       *
       * @param presencePenalty the presence penalty.
       * @return the final typed parameter object.
       */
      @Nonnull
      GPT presencePenalty(@Nonnull final Number presencePenalty);
    }
  }
}
