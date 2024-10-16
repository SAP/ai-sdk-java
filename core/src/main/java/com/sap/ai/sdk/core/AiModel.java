package com.sap.ai.sdk.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Represents an AI model. */
public interface AiModel {

  /**
   * Returns the name of the model.
   *
   * @return The name of the model.
   */
  @Nonnull
  String name();

  /**
   * Returns the version of the model.
   *
   * @return The version of the model.
   */
  @Nullable
  String version();
}
