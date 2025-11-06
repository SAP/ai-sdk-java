package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationInput;
import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationInputConfig;
import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationOutput;
import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationOutputConfig;
import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationOutputTargetLanguage;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Configuration helper for SAP Document Translation.
 *
 * @link <a
 *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/sap-document-translation">SAP
 *     AI Core: Orchestration - SAP Document Translation</a>
 */
@Value
@Getter(AccessLevel.PACKAGE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TranslationConfig {
  @Nonnull SAPDocumentTranslationInput.TypeEnum translationType;

  /**
   * Create a builder for the SAP_DOCUMENT_TRANSLATION translation provider
   *
   * @return The Builder instance.
   */
  @Nonnull
  public static Builder inputTranslation() {
    return new TranslationConfig.Builder(
        SAPDocumentTranslationInput.TypeEnum.SAP_DOCUMENT_TRANSLATION);
  }

  /**
   * Create a builder for the SAP_DOCUMENT_TRANSLATION translation provider
   *
   * @return The Builder2 instance.
   */
  @Nonnull
  public static Builder2 outputTranslation() {
    return new TranslationConfig.Builder2(
        SAPDocumentTranslationOutput.TypeEnum.SAP_DOCUMENT_TRANSLATION);
  }

  /** Builder helper class. */
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Builder {

    private final SAPDocumentTranslationInput.TypeEnum translationType;

    /**
     * Get the input translation config with default values.
     *
     * @return The SAPDocumentTranslationInput instance.
     */
    @Nonnull
    public SAPDocumentTranslationInput getInputTranslationConfig() {
      return SAPDocumentTranslationInput.create()
          .type(translationType)
          .config(SAPDocumentTranslationInputConfig.create().targetLanguage("en-US").applyTo(null));
    }
  }

  /** Sample builder class */
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Builder2 {
    private final SAPDocumentTranslationOutput.TypeEnum translationType;

    /**
     * Get the output translation config with default values.
     *
     * @return The SAPDocumentTranslationOutput instance.
     */
    @Nonnull
    public SAPDocumentTranslationOutput getOutputTranslationConfig() {
      return SAPDocumentTranslationOutput.create()
          .type(translationType)
          .config(
              SAPDocumentTranslationOutputConfig.create()
                  .targetLanguage(SAPDocumentTranslationOutputTargetLanguage.create("de-DE"))
                  .sourceLanguage("en-US"));
    }
  }
}
