package com.sap.ai.sdk.foundationmodels.rpt;

import com.sap.ai.sdk.core.AiModel;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents an SAP RPT foundation model.
 *
 * @param name The name of the model.
 * @param version The version of the model (optional).
 * @since 1.16.0
 */
public record RptModel(@Nonnull String name, @Nullable String version) implements AiModel {

  /** SAP Relational Pre-trained Transformer 1.5 model. */
  public static final RptModel SAP_RPT_15 = new RptModel("sap-rpt-1.5", null);

  /** SAP Relational Pre-trained Transformer Large 1.5 model. */
  public static final RptModel SAP_RPT_15_LARGE = new RptModel("sap-rpt-1.5-large", null);

  /**
   * Create a new instance of RptModel with the provided version.
   *
   * @param version The version of the model.
   * @return The new instance of RptModel.
   */
  @Nonnull
  public RptModel withVersion(@Nonnull final String version) {
    return new RptModel(name, version);
  }
}
