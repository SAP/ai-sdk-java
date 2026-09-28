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
 * @param name the name of the model.
 * @param version the version of the model (optional).
 */
public record OpenAiModel(@Nonnull String name, @Nullable String version) implements AiModel {

  /** Azure OpenAI GPT-4o model. */
  public static final OpenAiModel GPT_4O = new OpenAiModel("gpt-4o", null);

  /** Azure OpenAI Text Embedding 3 Large model. */
  public static final OpenAiModel TEXT_EMBEDDING_3_LARGE =
      new OpenAiModel("text-embedding-3-large", null);

  /** Azure OpenAI Text Embedding 3 Small model. */
  public static final OpenAiModel TEXT_EMBEDDING_3_SMALL =
      new OpenAiModel("text-embedding-3-small", null);

  /** Azure OpenAI GPT-o4 Mini model. */
  public static final OpenAiModel O4_MINI = new OpenAiModel("o4-mini", null);

  /** Azure OpenAI GPT-o3 model. */
  public static final OpenAiModel O3 = new OpenAiModel("o3", null);

  /** Azure OpenAI GPT-4.1 model. */
  public static final OpenAiModel GPT_41 = new OpenAiModel("gpt-4.1", null);

  /** Azure OpenAI GPT-4.1-nano model. */
  public static final OpenAiModel GPT_41_NANO = new OpenAiModel("gpt-4.1-nano", null);

  /** Azure OpenAI GPT-4.1-mini model. */
  public static final OpenAiModel GPT_41_MINI = new OpenAiModel("gpt-4.1-mini", null);

  /** Azure OpenAI GPT-5 model. */
  public static final OpenAiModel GPT_5 = new OpenAiModel("gpt-5", null);

  /** Azure OpenAI GPT-5-mini model. */
  public static final OpenAiModel GPT_5_MINI = new OpenAiModel("gpt-5-mini", null);

  /** Azure OpenAI GPT-5-nano model. */
  public static final OpenAiModel GPT_5_NANO = new OpenAiModel("gpt-5-nano", null);

  /** Azure OpenAI GPT-5.1 model. */
  public static final OpenAiModel GPT_51 = new OpenAiModel("gpt-5.1", null);

  /** Azure OpenAI GPT-realtime model. */
  public static final OpenAiModel GPT_REALTIME = new OpenAiModel("gpt-realtime", null);

  /** Azure OpenAI GPT-5.2 model. */
  public static final OpenAiModel GPT_52 = new OpenAiModel("gpt-5.2", null);

  /** Azure OpenAI GPT-5.3-codex model. */
  public static final OpenAiModel GPT_53_CODEX = new OpenAiModel("gpt-5.3-codex", null);

  /** Azure OpenAI GPT-5.4 model. */
  public static final OpenAiModel GPT_54 = new OpenAiModel("gpt-5.4", null);

  /** Azure OpenAI GPT-5.4-nano model. */
  public static final OpenAiModel GPT_54_NANO = new OpenAiModel("gpt-5.4-nano", null);

  /** Azure OpenAI GPT-5.5 model. */
  public static final OpenAiModel GPT_55 = new OpenAiModel("gpt-5.5", null);

  /** Azure OpenAI GPT-5.6-luna model. */
  public static final OpenAiModel GPT_56_LUNA = new OpenAiModel("gpt-5.6-luna", null);

  /** Azure OpenAI GPT-5.6-sol model. */
  public static final OpenAiModel GPT_56_SOL = new OpenAiModel("gpt-5.6-sol", null);

  /** Azure OpenAI GPT-5.6-terra model. */
  public static final OpenAiModel GPT_56_TERRA = new OpenAiModel("gpt-5.6-terra", null);

  /**
   * Create a new instance of OpenAiModel with the provided version.
   *
   * @param version the version of the model.
   * @return the new instance of OpenAiModel.
   */
  @Nonnull
  public OpenAiModel withVersion(@Nonnull final String version) {
    return new OpenAiModel(name, version);
  }
}
