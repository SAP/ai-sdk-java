package com.sap.ai.sdk.architecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import java.util.Set;
import javax.annotation.Nonnull;

/**
 * Helper class to orchestrate architecture rule execution.
 */
class ArchRule {

  /**
   * Executes the shared inheritance rule for the configured base package.
   *
   * @param basePackage The package prefix to import for analysis.
   * @param allowedClassNames Fully qualified class names ignored for inherit-model violations.
   */
  static void check(
      @Nonnull final String basePackage, @Nonnull final Set<String> allowedClassNames) {
    final var importedClasses =
        new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(basePackage);

    ArchRuleInheritGeneratedModel.create(allowedClassNames).check(importedClasses);
  }
}