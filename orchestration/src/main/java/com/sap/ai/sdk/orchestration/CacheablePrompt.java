package com.sap.ai.sdk.orchestration;

import javax.annotation.Nullable;

/** Prompt objects supporting caching may implement this interface */
public sealed interface CacheablePrompt permits TextItem {

  /**
   * Returns cache control for a given cacheable prompt (if set)
   *
   * @return cacheControl, nullable
   */
  @Nullable
  CacheControl getCacheControl();
}
