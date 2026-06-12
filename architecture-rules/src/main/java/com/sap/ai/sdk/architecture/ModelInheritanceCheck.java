package com.sap.ai.sdk.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

public final class ModelInheritanceCheck {
  private ModelInheritanceCheck() {}

  public static final ArchRule TYPES_MUST_NOT_INHERIT_MODEL_TYPES =
      classes()
          .that(ModelInheritanceRules.NOT_ALLOWLISTED)
          .should(
              new ArchCondition<JavaClass>("not extend or implement .model types") {
                @Override
                public void check(final JavaClass item, final ConditionEvents events) {
                  if (ModelInheritanceRules.targetsModelPackage(item)) {
                    events.add(SimpleConditionEvent.violated(item, message(item)));
                  }
                }
              });

  private static String message(final JavaClass item) {
    return item.getFullName()
        + " inherits from a type in a package ending with '.model'. "
        + "Use @AllowModelInheritance(reason = \"...\") for approved exceptions.";
  }
}
