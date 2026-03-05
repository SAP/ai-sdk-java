package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationApplyToSelector.CategoryEnum.PLACEHOLDERS;
import static com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationApplyToSelector.CategoryEnum.TEMPLATE_ROLES;

import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationApplyToSelector;
import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationInput;
import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationInputConfig;
import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationOutput;
import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationOutputConfig;
import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationOutputTargetLanguage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
public interface TranslationConfig {
  /**
   * Supported values for {@code items[]} when {@code category=template_roles}.
   *
   * <p>These map to the roles used in prompt templates.
   */
  @RequiredArgsConstructor
  enum TemplateRole {
    /** Template role for user messages. */
    USER("user"),

    /** Template role for system messages. */
    SYSTEM("system"),

    /** Template role for assistant messages. */
    ASSISTANT("assistant"),

    /** Template role for developer messages. */
    DEVELOPER("developer"),

    /** Template role for tool messages. */
    TOOL("tool");

    @Getter private final String value;
  }

  /** Input configuration for translation. */
  @Value
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class Input implements TranslationConfig {
    String targetLanguage;

    @Getter String sourceLanguage;

    /**
     * Optional selection(s) to translate. If empty or null, translation is applied to the whole
     * message. If used, source language will be applied per selector.
     */
    @Nullable List<SAPDocumentTranslationApplyToSelector> applyTo;

    @Nonnull
    SAPDocumentTranslationInput createSAPDocumentTranslationInput() {
      val translationType = SAPDocumentTranslationInput.TypeEnum.SAP_DOCUMENT_TRANSLATION;
      final var conf =
          SAPDocumentTranslationInputConfig.create()
              .targetLanguage(targetLanguage)
              .applyTo(applyTo);

      if (applyTo == null || applyTo.isEmpty()) {
        conf.sourceLanguage(sourceLanguage);
      }

      return SAPDocumentTranslationInput.create().type(translationType).config(conf);
    }

    /**
     * Start an {@code apply_to} selector for placeholder names in {@code placeholder_values}.
     *
     * @param name The first placeholder name to translate.
     * @param additionalNames Additional placeholder names to translate.
     * @return A selector with {@code category=placeholders} and the given items.
     */
    @Nonnull
    public Input applyToPlaceholders(
        @Nonnull final String name, @Nonnull final String... additionalNames) {
      final var selector =
          SAPDocumentTranslationApplyToSelector.create()
              .category(PLACEHOLDERS)
              .items(Stream.concat(Stream.of(name), Stream.of(additionalNames)).toList());
      return addApplyToSelector(selector);
    }

    /**
     * Start an {@code apply_to} selector for prompt template message roles.
     *
     * @param role The first template role to translate.
     * @param roles The template roles to translate.
     * @return A selector with {@code category=template_roles} and the given items.
     */
    @Nonnull
    public Input applyToTemplateRoles(
        @Nonnull final TranslationConfig.TemplateRole role,
        @Nonnull final TranslationConfig.TemplateRole... roles) {
      final var roleStrings = new ArrayList<String>(1 + roles.length);
      roleStrings.add(role.getValue());
      for (final var r : roles) {
        if (r != null) {
          roleStrings.add(r.getValue());
        }
      }

      final var selector =
          SAPDocumentTranslationApplyToSelector.create()
              .category(TEMPLATE_ROLES)
              .items(roleStrings);
      return addApplyToSelector(selector);
    }

    /**
     * Set the source language for this translation. 
     * <br>
     * <strong>Important Note:</strong> If no selectors are used, this applies to the
     * whole message. If selectors are used, this applies to the most recently added selector.
     *
     * @param sourceLanguage The source language code
     * @return A new Input with the given source language applied.
     */
    @Nonnull
    public Input withSourceLanguage(@Nonnull final String sourceLanguage) {
      if (applyTo != null) {
        applyTo.get(applyTo.size() - 1).sourceLanguage(sourceLanguage);
      }
      return new Input(targetLanguage, sourceLanguage, applyTo);
    }

    private Input addApplyToSelector(
        @Nonnull final SAPDocumentTranslationApplyToSelector selector) {
      final var appended = new ArrayList<SAPDocumentTranslationApplyToSelector>();
      if (applyTo != null && !applyTo.isEmpty()) {
        appended.addAll(applyTo);
      }
      appended.add(selector);

      return new TranslationConfig.Input(targetLanguage, sourceLanguage, appended);
    }
  }

  /** Output configuration for translation. */
  @Value
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  class Output implements TranslationConfig {
    String targetLanguage;

    @Getter @With String sourceLanguage;

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
   * @link <a
   *     href="https://help.sap.com/docs/translation-hub/sap-translation-hub/supported-languages">SAP
   *     AI Core: Orchestration - SAP Translation Hub Table with official languages</a>
   * @return A TranslationConfig configured for input translation
   */
  @Nonnull
  static TranslationConfig.Input translateInputTo(@Nonnull final String targetLanguage) {
    return new TranslationConfig.Input(targetLanguage, null, null);
  }

  /**
   * Create a new output translation configuration.
   *
   * @param targetLanguage The target language code
   * @link <a
   *     href="https://help.sap.com/docs/translation-hub/sap-translation-hub/supported-languages">
   *     SAP AI Core: Orchestration - SAP Translation Hub Table with official languages</a>
   * @return A TranslationConfig configured for output translation
   */
  @Nonnull
  static TranslationConfig.Output translateOutputTo(@Nonnull final String targetLanguage) {
    return new TranslationConfig.Output(targetLanguage, null);
  }
}
