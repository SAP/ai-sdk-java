package com.sap.ai.sdk.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** An interface defining essential attributes of an AI model. */
public interface AiModel {

  /**
   * Get the model's name.
   *
   * @return The name of the model.
   */
  @Nonnull
  String name();

  /**
   * Get the model's version.
   *
   * @return The version of the model, or null if not specified.
   */
  @Nullable
  String version();
}
