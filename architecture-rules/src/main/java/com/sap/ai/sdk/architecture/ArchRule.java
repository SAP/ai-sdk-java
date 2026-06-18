package com.sap.ai.sdk.architecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import lombok.Data;
import org.apache.maven.plugins.annotations.Parameter;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

/**
 * Helper class to orchestrate architecture rule execution.
 */
class ArchRule {

  /**
   * Executes the shared inheritance rule for the configured base package.
   *
   * @param basePackage The package prefix to import for analysis.
   * @param suppressions Fully qualified class names ignored for violations.
   */
  static void check(
      @Nonnull final String basePackage, @Nonnull final Suppressions suppressions) {
    final var importedClasses =
        new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(basePackage);

    ArchRuleInheritGeneratedModel.create(suppressions.getInheritModel()).check(importedClasses);
  }

  /** Maven configuration bean for ignored architecture-rule violations. */
  @Data
  public static final class Suppressions {
    @Parameter
    @Nonnull private Set<String> inheritModel = new HashSet<>();
  }
}
