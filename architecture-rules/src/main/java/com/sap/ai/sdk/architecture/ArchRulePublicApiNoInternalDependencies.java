package com.sap.ai.sdk.architecture;

import static com.sap.ai.sdk.architecture.ArchRule.Suppressions;
import static com.tngtech.archunit.base.DescribedPredicate.describe;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchRule;
import javax.annotation.Nonnull;
import lombok.val;

/**
 * Shared ArchUnit rule that prevents public API types from depending on implementation packages.
 */
final class ArchRulePublicApiNoInternalDependencies {
  private static final String[] INTERNAL_PACKAGE_PATTERNS = {"..internal..", "..impl.."};

  private ArchRulePublicApiNoInternalDependencies() {}

  /**
   * Creates the rule for public API types exposing implementation details.
   *
   * @param suppressions Suppressions to that rule.
   * @return The configured ArchUnit rule.
   */
  @Nonnull
  static ArchRule create(@Nonnull final Suppressions suppressions) {
    val allowed = suppressions.getExposeInternal();
    val msg = "not configured in suppressions.exposeInternal";
    val notAllowlisted = describe(msg, (JavaClass cl) -> !allowed.contains(cl.getFullName()));

    return noClasses()
        .that()
        .arePublic()
        .and(notAllowlisted)
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage(INTERNAL_PACKAGE_PATTERNS);
  }
}
