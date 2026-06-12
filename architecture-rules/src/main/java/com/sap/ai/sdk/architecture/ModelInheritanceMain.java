package com.sap.ai.sdk.architecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;

public final class ModelInheritanceMain {
  private ModelInheritanceMain() {}

  public static void main(final String[] args) {
    final var targetPackage = System.getProperty("model.inheritance.basePackage", "");
    final var importedClasses =
        new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(targetPackage);

    ModelInheritanceCheck.TYPES_MUST_NOT_INHERIT_MODEL_TYPES.check(importedClasses);
  }
}
