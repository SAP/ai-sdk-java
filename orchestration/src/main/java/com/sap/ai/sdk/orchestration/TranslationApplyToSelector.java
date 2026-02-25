package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationApplyToSelector.CategoryEnum.PLACEHOLDERS;
import static com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationApplyToSelector.CategoryEnum.TEMPLATE_ROLES;

import com.sap.ai.sdk.orchestration.model.SAPDocumentTranslationApplyToSelector;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;

/**
 * Convenience builder for {@link SAPDocumentTranslationApplyToSelector}.
 *
 * <p>This avoids passing raw strings for template roles and keeps sample-code readable.
 */
public final class TranslationApplyToSelector {
  private TranslationApplyToSelector() {}

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

    private final String value;

    TemplateRole(@Nonnull final String value) {
      this.value = value;
    }

    /**
     * Get the string representation used in the API payload.
     *
     * @return The role value used in {@code items[]}.
     */
    @Nonnull
    public String getValue() {
      return value;
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
    Objects.requireNonNull(names, "names must not be null");
    return placeholders(List.of(names));
  }

  /**
   * Start an {@code apply_to} selector for placeholder names in {@code placeholder_values}.
   *
   * @param names The placeholder keys to translate.
   * @return A selector with {@code category=placeholders} and the given items.
   */
  @Nonnull
  public static SAPDocumentTranslationApplyToSelector placeholders(
      @Nonnull final List<String> names) {
    Objects.requireNonNull(names, "names must not be null");
    return SAPDocumentTranslationApplyToSelector.create()
        .category(PLACEHOLDERS)
        .items(List.copyOf(names));
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
    Objects.requireNonNull(roles, "roles must not be null");
    return templateRoles(List.of(roles));
  }

  /**
   * Start an {@code apply_to} selector for prompt template message roles.
   *
   * @param roles The template roles to translate.
   * @return A selector with {@code category=template_roles} and the given items.
   */
  @Nonnull
  public static SAPDocumentTranslationApplyToSelector templateRoles(
      @Nonnull final Collection<TemplateRole> roles) {
    Objects.requireNonNull(roles, "roles must not be null");
    final var roleStrings =
        roles.stream().filter(Objects::nonNull).map(TemplateRole::getValue).toList();

    return SAPDocumentTranslationApplyToSelector.create()
        .category(TEMPLATE_ROLES)
        .items(roleStrings);
  }
}
