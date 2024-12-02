package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.FilterConfig;
import javax.annotation.Nonnull;

/**
 * Interface representing convenience wrappers of serializable content filter that defines
 * thresholds for different content categories.
 *
 * @link <a
 *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/input-filtering">SAP AI
 *     Core: Orchestration - Input Filtering</a>
 * @link <a
 *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/output-filtering">SAP
 *     AI Core: Orchestration - Output Filtering</a>
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
