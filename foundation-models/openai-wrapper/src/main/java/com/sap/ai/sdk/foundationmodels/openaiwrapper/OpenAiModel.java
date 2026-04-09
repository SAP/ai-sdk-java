package com.sap.ai.sdk.foundationmodels.openaiwrapper;

import com.sap.ai.sdk.core.AiModel;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum of available OpenAI models for use with SAP AI Core.
 *
 * <p>These model names correspond to the model identifiers used when creating deployments in SAP AI
 * Core. They are used to resolve the correct deployment URL.
 */
@RequiredArgsConstructor
@Getter
public enum OpenAiModel implements AiModel {
  /** GPT-4o */
  GPT_4O("gpt-4o"),
  /** GPT-4o-mini */
  GPT_4O_MINI("gpt-4o-mini"),
  /** GPT-4.1 */
  GPT_41("gpt-4.1"),
  /** GPT-4.1-mini */
  GPT_41_MINI("gpt-4.1-mini"),
  /** GPT-4.1-nano */
  GPT_41_NANO("gpt-4.1-nano"),
  /** Text embedding ada 002 */
  TEXT_EMBEDDING_ADA_002("text-embedding-ada-002"),
  /** Text embedding 3 small */
  TEXT_EMBEDDING_3_SMALL("text-embedding-3-small"),
  /** Text embedding 3 large */
  TEXT_EMBEDDING_3_LARGE("text-embedding-3-large");

  @Nonnull private final String name;

  @Nullable
  @Override
  public String version() {
    return null;
  }
}
