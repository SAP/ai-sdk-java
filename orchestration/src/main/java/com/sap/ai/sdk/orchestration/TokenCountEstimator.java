package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;

/**
 * Estimates the number of tokens a {@link Message} will consume in the LLM context window.
 *
 * <p>Accurate tokenization is model-specific and typically requires native tokenizer libraries.
 * Implement this interface to plug in a precise tokenizer for the model you are using.
 *
 * <p>A simple built-in approximation is provided via {@link #DEFAULT}: it counts all characters
 * across the message's text content items and divides by 4, which is a reasonable rule-of-thumb
 * heuristic for many models.
 *
 * @see ChatMemory#tokenWindow(int, TokenCountEstimator)
 */
@FunctionalInterface
public interface TokenCountEstimator {

  /**
   * A built-in default estimator that approximates token count as {@code totalCharacters / 4}.
   *
   * <p>This heuristic is suitable for prototyping. For production use, replace it with a
   * model-specific tokenizer.
   */
  TokenCountEstimator DEFAULT =
      message ->
          message.content().items().stream()
                  .filter(item -> item instanceof TextItem)
                  .mapToInt(item -> ((TextItem) item).text().length())
                  .sum()
              / 4;

  /**
   * Estimates the number of tokens the given message will consume.
   *
   * @param message the message to estimate tokens for.
   * @return estimated token count; must be non-negative.
   */
  int estimate(@Nonnull Message message);
}
