package com.sap.ai.sdk.foundationmodels.openai;

import javax.annotation.Nonnull;

/**
 * Available OpenAI models.
 *
 * @param model a deployed OpenAI model
 */
public record OpenAiModel(@Nonnull String model) {

  /** Azure OpenAI GPT-3.5 Turbo chat completions model */
  public static final OpenAiModel GPT_35_TURBO = new OpenAiModel("gpt-35-turbo");

  /** Azure OpenAI GPT-3.5 Turbo chat completions model */
  public static final OpenAiModel GPT_35_TURBO_1025 = new OpenAiModel("gpt-35-turbo-0125");

  /** Azure OpenAI GPT-3.5 Turbo chat completions model */
  public static final OpenAiModel GPT_35_TURBO_16K = new OpenAiModel("gpt-35-turbo-16k");

  /** Azure OpenAI GPT-4 chat completions model */
  public static final OpenAiModel GPT_4 = new OpenAiModel("gpt-4");

  /** Azure OpenAI GPT-4-32k chat completions model */
  public static final OpenAiModel GPT_4_32K = new OpenAiModel("gpt-4-32k");

  /** Azure OpenAI GPT-4o chat completions model */
  public static final OpenAiModel GPT_4O = new OpenAiModel("gpt-4o");

  /** Azure OpenAI Text Embedding ADA 002 model */
  public static final OpenAiModel TEXT_EMBEDDING_ADA_002 =
      new OpenAiModel("text-embedding-ada-002");
}
