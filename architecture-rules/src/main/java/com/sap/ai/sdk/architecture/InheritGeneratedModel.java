package com.sap.ai.sdk.architecture;

import static com.tngtech.archunit.lang.SimpleConditionEvent.violated;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.annotation.Nonnull;

/**
 * Runs the global architecture rule that restricts inheritance from packages ending with {@code
 * .model}.
 */
public final class InheritGeneratedModel {
  private static final DescribedPredicate<JavaClass> MODEL_PACKAGE =
      JavaClass.Predicates.resideInAPackage("com.sap.ai.sdk..model");
  private static final String MSG_VIOLATION =
      "%s inherits from a type in a package matching 'com.sap.ai.sdk..model' while not residing in such a package itself. Use @AllowModelInheritance(reason = \"...\") for approved exceptions.";

  private static final DescribedPredicate<JavaClass> NOT_IN_MODEL_PACKAGE =
      DescribedPredicate.describe(
          "not residing in a package matching com.sap.ai.sdk..model",
          input -> !MODEL_PACKAGE.test(input));

  private static final DescribedPredicate<JavaClass> NOT_ALLOWLISTED =
      DescribedPredicate.describe(
          "not annotated with @AllowModelInheritance",
          input -> !input.isAnnotatedWith(AllowModelInheritance.class));

  private static final ArchCondition<JavaClass> NOT_INHERIT_FROM_MODEL_TYPES =
      new ArchCondition<>("not extend or implement com.sap.ai.sdk..model types") {
        @Override
        public void check(final JavaClass item, final ConditionEvents events) {
          final var superClass = item.getRawSuperclass();
          final var extendsModel = superClass.isPresent() && MODEL_PACKAGE.test(superClass.get());
          if (extendsModel || item.getRawInterfaces().stream().anyMatch(MODEL_PACKAGE)) {
            events.add(violated(item, MSG_VIOLATION.formatted(item.getFullName())));
          }
        }
      };

  private InheritGeneratedModel() {}

  /**
   * Executes the inheritance rule for the package configured via {@code
   * model.inheritance.basePackage}.
   *
   * @param args Unused command line arguments.
   */
  public static void main(final String[] args) {
    final var targetPackage = System.getProperty("model.inheritance.basePackage", "");
    final var importedClasses =
        new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(targetPackage);

    classes()
        .that(NOT_IN_MODEL_PACKAGE)
        .and(NOT_ALLOWLISTED)
        .should(NOT_INHERIT_FROM_MODEL_TYPES)
        .check(importedClasses);
  }

  /**
   * Marks approved exceptions to the model inheritance rule.
   *
   * <p>Types annotated with this are excluded from the ArchUnit check.
   */
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  public @interface AllowModelInheritance {
    /**
     * Explains why the exception is needed.
     *
     * @return The reason for the exception.
     */
    @Nonnull
    String reason() default "";
  }
}
