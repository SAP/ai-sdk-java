package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.InputFilterConfig;
import com.sap.ai.sdk.orchestration.model.OutputFilterConfig;
import javax.annotation.Nonnull;

/**
 * For internal SDK usage only. Class representing convenience wrappers of serializable content
 * filter that defines thresholds for different content categories.
 *
 * <p><a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/input-filtering">SAP
 * AI Core: Orchestration - Input Filtering</a>
 *
 * <p><a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/output-filtering">SAP
 * AI Core: Orchestration - Output Filtering</a>
 */
public abstract class ContentFilter {

  /**
   * For internal SDK usage only. A method that produces the serializable equivalent {@link
   * InputFilterConfig} object from data encapsulated in the {@link ContentFilter} object.
   *
   * @return the corresponding {@link InputFilterConfig} object.
   */
  @Nonnull
  abstract InputFilterConfig createInputFilterConfig();

  /**
   * For internal SDK usage only. A method that produces the serializable equivalent {@link
   * OutputFilterConfig} object from data encapsulated in the {@link ContentFilter} object.
   *
   * @return the corresponding {@link OutputFilterConfig} object.
   */
  @Nonnull
  abstract OutputFilterConfig createOutputFilterConfig();
}
