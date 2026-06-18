package com.sap.ai.sdk.architecture;

import static com.tngtech.archunit.core.importer.ImportOption.Predefined.DO_NOT_INCLUDE_TESTS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME;
import static com.tngtech.archunit.library.GeneralCodingRules.OLD_DATE_AND_TIME_CLASSES_SHOULD_NOT_BE_USED;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import lombok.Data;
import org.apache.maven.plugins.annotations.Parameter;

/** Helper class to orchestrate architecture rule execution. */
class ArchRule {

  /**
   * Executes the shared architecture rules for the configured base package.
   *
   * @param basePackage The package prefix to import for analysis.
   * @param suppressions Fully qualified class names ignored for violations.
   */
  static void check(@Nonnull final String basePackage, @Nonnull final Suppressions suppressions) {
    final var classes =
        new ClassFileImporter().withImportOption(DO_NOT_INCLUDE_TESTS).importPackages(basePackage);

    ArchRuleInheritGeneratedModel.create(suppressions).check(classes);
    ArchRulePublicApiNoInternalDependencies.create(suppressions).check(classes);
    ArchRuleNoTestOnlyDependencies.create(suppressions).check(classes);

    NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS.check(classes);
    NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS.check(classes);
    NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING.check(classes);
    NO_CLASSES_SHOULD_USE_JODATIME.check(classes);
    OLD_DATE_AND_TIME_CLASSES_SHOULD_NOT_BE_USED.check(classes);
  }

  /** Maven configuration bean for ignored architecture-rule violations. */
  @Data
  public static final class Suppressions {
    @Parameter @Nonnull private Set<String> inheritModel = new HashSet<>();
    @Parameter @Nonnull private Set<String> exposeInternal = new HashSet<>();
    @Parameter @Nonnull private Set<String> testOnlyDependencies = new HashSet<>();
  }
}
