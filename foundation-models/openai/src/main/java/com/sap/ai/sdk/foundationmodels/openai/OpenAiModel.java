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

  /** internal [Azure OpenAI dall-e-3 model] */
  public static final OpenAiModel DALL_E_3 = new OpenAiModel("dall-e-3", null);

  /**
   * Azure OpenAI GPT-3.5 Turbo model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2025-02-13. The
   *     suggested replacement model is {@link OpenAiModel#GPT_4O_MINI}.
   */
  @Deprecated public static final OpenAiModel GPT_35_TURBO = new OpenAiModel("gpt-35-turbo", null);

  /**
   * Azure OpenAI GPT-3.5 Turbo model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2025-02-22. The
   *     suggested replacement model is {@link OpenAiModel#GPT_4O_MINI}.
   */
  @Deprecated
  public static final OpenAiModel GPT_35_TURBO_1025 = new OpenAiModel("gpt-35-turbo-0125", null);

  /**
   * Azure OpenAI GPT-3.5 Turbo model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2025-02-13. The
   *     suggested replacement model is {@link OpenAiModel#GPT_4O_MINI}.
   */
  @Deprecated
  public static final OpenAiModel GPT_35_TURBO_16K = new OpenAiModel("gpt-35-turbo-16k", null);

  /**
   * Azure OpenAI GPT-4 model
   *
   * <p>Note: This model is supposed to be deprecated on AI Core with a planned retirement on
   * 2025-09-01. The suggested replacement model is {@link OpenAiModel#GPT_4O} or {@link
   * OpenAiModel#GPT_41}.
   */
  public static final OpenAiModel GPT_4 = new OpenAiModel("gpt-4", null);

  /**
   * Azure OpenAI GPT-4 model
   *
   * @deprecated This model is deprecated on AI Core.
   */
  @Deprecated public static final OpenAiModel GPT_4_32K = new OpenAiModel("gpt-4-32k", null);

  /** Azure OpenAI GPT-4o model */
  public static final OpenAiModel GPT_4O = new OpenAiModel("gpt-4o", null);

  /** Azure OpenAI GPT-4o Mini model */
  public static final OpenAiModel GPT_4O_MINI = new OpenAiModel("gpt-4o-mini", null);

  /** Azure OpenAI GPT-o3 Mini model */
  public static final OpenAiModel O3_MINI = new OpenAiModel("o3-mini", null);

  /** Azure OpenAI GPT-o1 model */
  public static final OpenAiModel O1 = new OpenAiModel("o1", null);

  /** Azure OpenAI Text Embedding 3 Large model */
  public static final OpenAiModel TEXT_EMBEDDING_3_LARGE =
      new OpenAiModel("text-embedding-3-large", null);

  /** Azure OpenAI Text Embedding 3 Small model */
  public static final OpenAiModel TEXT_EMBEDDING_3_SMALL =
      new OpenAiModel("text-embedding-3-small", null);

  /** Azure OpenAI GPT-o4 Mini model */
  public static final OpenAiModel O4_MINI = new OpenAiModel("o4-mini", null);

  /** Azure OpenAI GPT-o3 model */
  public static final OpenAiModel O3 = new OpenAiModel("o3", null);

  /** Azure OpenAI GPT-4.1 model */
  public static final OpenAiModel GPT_41 = new OpenAiModel("gpt-4.1", null);

  /** Azure OpenAI GPT-4.1-nano model */
  public static final OpenAiModel GPT_41_NANO = new OpenAiModel("gpt-4.1-nano", null);

  /** Azure OpenAI GPT-4.1-mini model */
  public static final OpenAiModel GPT_41_MINI = new OpenAiModel("gpt-4.1-mini", null);

  /**
   * Azure OpenAI Text Embedding ADA 002 model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2025-10-03. The
   *     suggested replacement models are {@link OpenAiModel#TEXT_EMBEDDING_3_SMALL} and {@link
   *     OpenAiModel#TEXT_EMBEDDING_3_LARGE}.
   */
  @Deprecated
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
