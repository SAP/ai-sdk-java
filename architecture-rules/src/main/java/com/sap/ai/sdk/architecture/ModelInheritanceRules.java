package com.sap.ai.sdk.architecture;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

public final class ModelInheritanceRules {
  private ModelInheritanceRules() {}

  public static final DescribedPredicate<JavaClass> MODEL_PACKAGE =
      JavaClass.Predicates.resideInAPackage("..model");

  public static final DescribedPredicate<JavaClass> NOT_ALLOWLISTED =
      new DescribedPredicate<>("not annotated with @AllowModelInheritance") {
        @Override
        public boolean test(final JavaClass input) {
          return !input.isAnnotatedWith(AllowModelInheritance.class);
        }
      };

  public static boolean targetsModelPackage(final JavaClass source) {
    final var superClass = source.getRawSuperclass();
    if (superClass.isPresent() && MODEL_PACKAGE.test(superClass.get())) {
      return true;
    }
    return source.getRawInterfaces().stream().anyMatch(MODEL_PACKAGE);
  }
}
