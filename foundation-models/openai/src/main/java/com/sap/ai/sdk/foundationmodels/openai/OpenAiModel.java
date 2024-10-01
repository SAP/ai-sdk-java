package com.sap.ai.sdk.foundationmodels.openai;

import javax.annotation.Nonnull;

/**
 * Available OpenAI models.
 *
 * @param model a deployed OpenAI model
 */
@SuppressWarnings("unused")
public record OpenAiModel(@Nonnull String model) {

  /** Azure OpenAI dall-e-3 image generate model */
  public static final OpenAiModel DALL_E_3 = new OpenAiModel("dall-e-3");

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

  /** Azure OpenAI GPT-4o Mini chat completions model */
  public static final OpenAiModel GPT_4O_MINI = new OpenAiModel("gpt-4o-mini");

  /** Azure OpenAI Text Embedding 3 Large model */
  public static final OpenAiModel TEXT_EMBEDDING_3_LARGE =
      new OpenAiModel("text-embedding-3-large");

  /** Azure OpenAI Text Embedding 3 Small model */
  public static final OpenAiModel TEXT_EMBEDDING_3_SMALL =
      new OpenAiModel("text-embedding-3-small");

  /** Azure OpenAI Text Embedding ADA 002 model */
  public static final OpenAiModel TEXT_EMBEDDING_ADA_002 =
      new OpenAiModel("text-embedding-ada-002");
}
