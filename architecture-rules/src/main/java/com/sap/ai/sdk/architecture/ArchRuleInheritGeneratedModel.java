package com.sap.ai.sdk.architecture;

import static com.sap.ai.sdk.architecture.ArchRule.Suppressions;
import static com.tngtech.archunit.base.DescribedPredicate.describe;
import static com.tngtech.archunit.lang.SimpleConditionEvent.violated;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import javax.annotation.Nonnull;
import lombok.val;

/**
 * Shared ArchUnit rule that restricts inheritance from SAP AI SDK packages ending with {@code
 * .model}.
 */
final class ArchRuleInheritGeneratedModel {
  private static final DescribedPredicate<JavaClass> MODEL_PACKAGE =
      JavaClass.Predicates.resideInAPackage("com.sap.ai.sdk..model");
  private static final String MSG_VIOLATION =
      "%s inherits from a type in a package matching 'com.sap.ai.sdk..model' while not residing in such a package itself. Configure the class name in allowedModelInheritanceClasses for approved exceptions.";

  private ArchRuleInheritGeneratedModel() {}

  /**
   * Creates the rule for non-model types inheriting from SDK model packages.
   *
   * @param suppressions Suppressions for the rule.
   * @return The configured ArchUnit rule.
   */
  @Nonnull
  static ArchRule create(@Nonnull final Suppressions suppressions) {
    val allowed = suppressions.getInheritModel();

    val msg1 = "not residing in a package matching com.sap.ai.sdk..model";
    val notInModelPackage = describe(msg1, (JavaClass cl) -> !MODEL_PACKAGE.test(cl));

    val msg2 = "not configured in `suppressions.inheritModel`";
    val notAllowlisted = describe(msg2, (JavaClass cl) -> !allowed.contains(cl.getFullName()));

    return classes()
        .that(notInModelPackage)
        .and(notAllowlisted)
        .should(notInheritFromModelTypes())
        .allowEmptyShould(true);
  }

  private static ArchCondition<JavaClass> notInheritFromModelTypes() {
    return new ArchCondition<>("not extend or implement com.sap.ai.sdk..model types") {
      @Override
      public void check(final JavaClass item, final ConditionEvents events) {
        final var superClass = item.getRawSuperclass();
        final var extendsModel = superClass.isPresent() && MODEL_PACKAGE.test(superClass.get());
        if (extendsModel || item.getRawInterfaces().stream().anyMatch(MODEL_PACKAGE)) {
          events.add(violated(item, MSG_VIOLATION.formatted(item.getFullName())));
        }
      }
    };
  }
}
