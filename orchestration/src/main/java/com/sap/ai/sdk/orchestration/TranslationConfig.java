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
import lombok.With;
import lombok.val;

/**
 * Configuration helper for SAP Document Translation.
 *
 * @link <a
 *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/sap-document-translation">SAP
 *     AI Core: Orchestration - SAP Document Translation</a>
 * @since 1.14.0
 */
@Value
@With
@Getter(AccessLevel.PACKAGE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TranslationConfig {
  SAPDocumentTranslationInput inputConfig;
  SAPDocumentTranslationOutput outputConfig;
  String sourceLanguage;

  /**
   * Create a new input translation configuration.
   *
   * @param targetLanguage The target language code
   * @return A TranslationConfig configured for input translation
   */
  @Nonnull
  public static TranslationConfig createInput(@Nonnull final String targetLanguage) {
    val translationType = SAPDocumentTranslationInput.TypeEnum.SAP_DOCUMENT_TRANSLATION;
    val inputConfig =
        SAPDocumentTranslationInput.create()
            .type(translationType)
            .config(
                SAPDocumentTranslationInputConfig.create()
                    .targetLanguage(targetLanguage)
                    .applyTo(null));
    return new TranslationConfig(inputConfig, null, null);
  }

  /**
   * Create a new output translation configuration.
   *
   * @param targetLanguage The target language code
   * @return A TranslationConfig configured for output translation
   */
  @Nonnull
  public static TranslationConfig createOutput(@Nonnull final String targetLanguage) {
    val translationType = SAPDocumentTranslationOutput.TypeEnum.SAP_DOCUMENT_TRANSLATION;
    val outputConfig =
        SAPDocumentTranslationOutput.create()
            .type(translationType)
            .config(
                SAPDocumentTranslationOutputConfig.create()
                    .targetLanguage(
                        SAPDocumentTranslationOutputTargetLanguage.create(targetLanguage)));
    return new TranslationConfig(null, outputConfig, null);
  }

  /**
   * Set the source language for translation
   *
   * @param language The source language code
   * @return A new TranslationConfig with the specified source language
   */
  @Nonnull
  public TranslationConfig sourceLanguage(@Nonnull final String language) {
    return this.withSourceLanguage(language);
  }
}
