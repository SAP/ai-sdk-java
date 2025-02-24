package com.sap.ai.sdk.foundationmodels.openai;

import com.sap.ai.sdk.core.AiModel;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * OpenAI models that are available in AI Core.
 *
 * <p>Please note that the template of models provided in this class might be outdated. To check the
 * latest availability of OpenAI models in AI Core, please refer to <a
 * href="https://me.sap.com/notes/3437766">SAP Availability of Generative AI Models </a>.
 *
 * @param name The name of the model.
 * @param version The version of the model (optional).
 */
public record OpenAiModel(@Nonnull String name, @Nullable String version) implements AiModel {

  /** Azure OpenAI dall-e-3 image generate model */
  public static final OpenAiModel DALL_E_3 = new OpenAiModel("dall-e-3", null);

  /** Azure OpenAI GPT-3.5 Turbo chat completions model */
  public static final OpenAiModel GPT_35_TURBO = new OpenAiModel("gpt-35-turbo", null);

  /** Azure OpenAI GPT-3.5 Turbo chat completions model */
  public static final OpenAiModel GPT_35_TURBO_1025 = new OpenAiModel("gpt-35-turbo-0125", null);

  /** Azure OpenAI GPT-3.5 Turbo chat completions model */
  @Deprecated
  public static final OpenAiModel GPT_35_TURBO_16K = new OpenAiModel("gpt-35-turbo-16k", null);

  /** Azure OpenAI GPT-4 chat completions model */
  public static final OpenAiModel GPT_4 = new OpenAiModel("gpt-4", null);

  /** Azure OpenAI GPT-4-32k chat completions model */
  public static final OpenAiModel GPT_4_32K = new OpenAiModel("gpt-4-32k", null);

  /** Azure OpenAI GPT-4o chat completions model */
  public static final OpenAiModel GPT_4O = new OpenAiModel("gpt-4o", null);

  /** Azure OpenAI GPT-4o Mini chat completions model */
  public static final OpenAiModel GPT_4O_MINI = new OpenAiModel("gpt-4o-mini", null);

  /** Azure OpenAI o1 chat completions model */
  public static final OpenAiModel O1 = new OpenAiModel("o1", null);

  /** Azure OpenAI o3-mini chat completions model */
  public static final OpenAiModel O3_MINI = new OpenAiModel("o3-mini", null);

  /** Azure OpenAI Text Embedding 3 Large model */
  public static final OpenAiModel TEXT_EMBEDDING_3_LARGE =
      new OpenAiModel("text-embedding-3-large", null);

  /** Azure OpenAI Text Embedding 3 Small model */
  public static final OpenAiModel TEXT_EMBEDDING_3_SMALL =
      new OpenAiModel("text-embedding-3-small", null);

  /** Azure OpenAI Text Embedding ADA 002 model */
  public static final OpenAiModel TEXT_EMBEDDING_ADA_002 =
      new OpenAiModel("text-embedding-ada-002", null);

  /**
   * Create a new instance of OpenAiModel with the provided version.
   *
   * @param version The version of the model.
   * @return The new instance of OpenAiModel.
   */
  @Nonnull
  public OpenAiModel withVersion(@Nonnull final String version) {
    return new OpenAiModel(name, version);
  }
}
