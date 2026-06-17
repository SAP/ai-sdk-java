package com.sap.ai.sdk.architecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;

import javax.annotation.Nonnull;

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
      @Nonnull final String basePackage, @Nonnull final ArchitectureRulesMojo.Suppressions suppressions) {
    final var importedClasses =
        new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(basePackage);

    ArchRuleInheritGeneratedModel.create(suppressions.getInheritModel()).check(importedClasses);
  }
}
