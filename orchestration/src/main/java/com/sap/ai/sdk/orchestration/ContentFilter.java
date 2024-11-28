package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.generated.FilterConfig;
import javax.annotation.Nonnull;

/**
 * Interface representing convenience wrappers of serializable content filter that defines
 * thresholds for different content categories.
 */
public interface ContentFilter {

  /**
   * A method that produces the serializable equivalent {@link FilterConfig} object from data
   * encapsulated in the {@link ContentFilter} object.
   *
   * @return the corresponding {@code FilterConfig} object.
   */
  @Nonnull
  FilterConfig createConfig();
}
