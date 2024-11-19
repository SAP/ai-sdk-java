package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.FilterConfig;

/**
 * Interface representing convenience wrappers of serializable content filter that defines
 * moderation policies for different content categories.
 */
public interface ContentFilter {

  /**
   * A method that produces the serializable equivalent {@link FilterConfig} object from data
   * encapsulated in the {@link ContentFilter} object.
   *
   * @return the corresponding {@code FilterConfig} object.
   */
  FilterConfig toSerializable();
}
