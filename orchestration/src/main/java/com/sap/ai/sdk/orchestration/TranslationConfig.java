package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationApplyToSelector;
import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationInput;
import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationInputConfig;
import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationOutput;
import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationOutputConfig;
import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationOutputTargetLanguage;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.With;
import lombok.val;

import java.util.List;

/**
 * Configuration helper for SAP Document Translation.
 *
 * @link <a
 *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/sap-document-translation">SAP
 *     AI Core: Orchestration - SAP Document Translation</a>
 */
public interface TranslationConfig {
  /** Input translation configuration. */
  @Value
  @With
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class Input implements TranslationConfig {
    @With(AccessLevel.NONE)
    String targetLanguage;

    String sourceLanguage;

    @With(AccessLevel.NONE)
    Object applyTo; // see backlog item SAP/ai-sdk-java-backlog#341

    @Nonnull
    SAPDocumentTranslationInput createSAPDocumentTranslationInput() {
      val translationType = SAPDocumentTranslationInput.TypeEnum.SAP_DOCUMENT_TRANSLATION;
      val conf =
          SAPDocumentTranslationInputConfig.create().targetLanguage(targetLanguage).applyTo(null);
      return SAPDocumentTranslationInput.create().type(translationType).config(conf);
    }
  }

  /** Output translation configuration. */
  @Value
  @With
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class Output implements TranslationConfig {
    @With(AccessLevel.NONE)
    String targetLanguage;

    String sourceLanguage;

    @Nonnull
    SAPDocumentTranslationOutput createSAPDocumentTranslationOutput() {
      val translationType = SAPDocumentTranslationOutput.TypeEnum.SAP_DOCUMENT_TRANSLATION;
      val tLang = SAPDocumentTranslationOutputTargetLanguage.create(targetLanguage);
      val conf =
          SAPDocumentTranslationOutputConfig.create()
              .targetLanguage(tLang)
              .sourceLanguage(sourceLanguage);
      return SAPDocumentTranslationOutput.create().type(translationType).config(conf);
    }
  }

  /**
   * Create a new input translation configuration.
   *
   * @param targetLanguage The target language code
   * @return A TranslationConfig configured for input translation
   */
  @Nonnull
  static Input createInput(@Nonnull final String targetLanguage) {
    return new TranslationConfig.Input(targetLanguage, null, null);
  }

  /**
   * Create a new output translation configuration.
   *
   * @param targetLanguage The target language code
   * @return A TranslationConfig configured for output translation
   */
  @Nonnull
  static TranslationConfig.Output createOutput(@Nonnull final String targetLanguage) {
    return new TranslationConfig.Output(targetLanguage, null);
  }
}
