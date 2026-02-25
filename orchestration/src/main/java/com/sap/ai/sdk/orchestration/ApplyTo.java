package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationApplyToSelector.CategoryEnum.PLACEHOLDERS;
import static com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationApplyToSelector.CategoryEnum.TEMPLATE_ROLES;

import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationApplyToSelector;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

/**
 * Convenience builder for {@link SAPDocumentTranslationApplyToSelector}.
 *
 * <p>This avoids passing raw strings for template roles and keeps sample-code readable.
 */
public final class ApplyTo {
  private ApplyTo() {}

  /**
   * Supported values for {@code items[]} when {@code category=template_roles}.
   *
   * <p>These map to the roles used in prompt templates.
   */
  public enum TemplateRole {
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

    TemplateRole(@Nonnull final String value) {
      this.value = value;
    }
  }

  /**
   * Start an {@code apply_to} selector for placeholder names in {@code placeholder_values}.
   *
   * @param names The placeholder keys to translate.
   * @return A selector with {@code category=placeholders} and the given items.
   */
  @Nonnull
  public static SAPDocumentTranslationApplyToSelector placeholders(@Nonnull final String... names) {
    return SAPDocumentTranslationApplyToSelector.create().category(PLACEHOLDERS).items(names);
  }

  /**
   * Start an {@code apply_to} selector for prompt template message roles.
   *
   * @param roles The template roles to translate.
   * @return A selector with {@code category=template_roles} and the given items.
   */
  @Nonnull
  public static SAPDocumentTranslationApplyToSelector templateRoles(
      @Nonnull final TemplateRole... roles) {
    final var roleStrings =
        Stream.of(roles).filter(Objects::nonNull).map(TemplateRole::getValue).toList();

    return SAPDocumentTranslationApplyToSelector.create()
        .category(TEMPLATE_ROLES)
        .items(roleStrings);
  }
}
