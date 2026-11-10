package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.model.GraniteGuardianFilterConfig.TypeEnum.GRANITE_GUARDIAN_4_1;

import com.sap.ai.sdk.orchestration.model.GraniteGuardian41;
import com.sap.ai.sdk.orchestration.model.GraniteGuardian41Categories;
import com.sap.ai.sdk.orchestration.model.GraniteGuardianFilterConfig;
import javax.annotation.Nonnull;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A content filter wrapping Granite Guardian filter config.
 *
 * <p>This class allows setting filters for different content categories such as harm, violence,
 * jailbreak, social bias, profanity, sexual content, and unethical behavior.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // values not set are disabled by default
 * var categories =
 *     GraniteGuardian41Categories.create()
 *         .harm(true)
 *         .violence(true);
 * val filterConfig = new GraniteGuardianFilter().config(GraniteGuardian41.create().categories(categories));
 * }</pre>
 *
 * <p><a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/input-filtering">SAP
 * AI Core: Orchestration - Input Filtering</a>
 *
 * <p><a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/output-filtering">SAP
 * AI Core: Orchestration - Output Filtering</a>
 */
@Accessors(fluent = true)
@Setter
public class GraniteGuardianFilter extends ContentFilter {

  @SuppressWarnings("PMD.LombokGetterSetterExposesModelType")
  private GraniteGuardian41 config =
      GraniteGuardian41.create().categories(GraniteGuardian41Categories.create());

  @Nonnull
  @Override
  GraniteGuardianFilterConfig createInputFilterConfig() {
    return GraniteGuardianFilterConfig.create().type(GRANITE_GUARDIAN_4_1).config(config);
  }

  @Nonnull
  @Override
  GraniteGuardianFilterConfig createOutputFilterConfig() {
    return createInputFilterConfig();
  }
}
