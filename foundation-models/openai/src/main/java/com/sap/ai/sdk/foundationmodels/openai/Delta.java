package com.sap.ai.sdk.foundationmodels.openai;

import javax.annotation.Nullable;

public interface Delta {

  /**
   * Get the content from the delta.
   *
   * @return the content from the delta or null.
   */
  @Nullable
  public String getDeltaContent();
}
