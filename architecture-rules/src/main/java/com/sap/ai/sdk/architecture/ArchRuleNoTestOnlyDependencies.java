package com.sap.ai.sdk.architecture;

import static com.sap.ai.sdk.architecture.ArchRule.Suppressions;
import static com.tngtech.archunit.base.DescribedPredicate.describe;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchRule;
import javax.annotation.Nonnull;
import lombok.val;

/** Shared ArchUnit rule that forbids test-only dependencies in production code. */
final class ArchRuleNoTestOnlyDependencies {
  private static final String[] TEST_ONLY_PACKAGE_PATTERNS = {
    "org.junit..", "org.mockito..", "org.assertj.."
  };

  private ArchRuleNoTestOnlyDependencies() {}

  /**
   * Creates the rule for production classes depending on test-only libraries.
   *
   * @param suppressions Suppressions to that rule.
   * @return The configured ArchUnit rule.
   */
  @Nonnull
  static ArchRule create(@Nonnull final Suppressions suppressions) {
    val allowed = suppressions.getTestOnlyDependencies();
    val msg = "not configured in suppressions.testOnlyDependencies";
    val notAllowlisted = describe(msg, (JavaClass cl) -> !allowed.contains(cl.getFullName()));

    return noClasses()
        .that(notAllowlisted)
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage(TEST_ONLY_PACKAGE_PATTERNS);
  }
}
