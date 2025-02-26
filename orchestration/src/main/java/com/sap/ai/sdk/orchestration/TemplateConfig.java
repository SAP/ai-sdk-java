package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.TemplateRefByID;
import com.sap.ai.sdk.orchestration.model.TemplateRefByScenarioNameVersion;
import com.sap.ai.sdk.orchestration.model.TemplateRefTemplateRef;
import com.sap.ai.sdk.orchestration.model.TemplatingModuleConfig;

public abstract class TemplateConfig {
  protected abstract TemplatingModuleConfig toLowLevel();

  // factory method
  public static OrchestrationTemplate create() {
    return new OrchestrationTemplate();
  }

  // factory method
  public static ReferenceBuilder reference() {
    return new ReferenceBuilder();
  }

  public static class ReferenceBuilder {
    OrchestrationTemplateReference byId(String id) {
      return new OrchestrationTemplateReference(TemplateRefByID.create().id(id));
    }

    ReferenceBuilder1 byScenarioNameVersion(String scenario) {
      return new ReferenceBuilder1(scenario);
    }
  }

  public static class ReferenceBuilder1 {
    private final String scenario;

    ReferenceBuilder1(String scenario) {
      this.scenario = scenario;
    }

    ReferenceBuilder2 name(String name) {
      return new ReferenceBuilder2(scenario, name);
    }
  }

  public static class ReferenceBuilder2 {
    private final String scenario;
    private final String name;

    ReferenceBuilder2(String scenario, String name) {
      this.scenario = scenario;
      this.name = name;
    }

    OrchestrationTemplateReference version(String version) {
      return new OrchestrationTemplateReference(
          TemplateRefByScenarioNameVersion.create().scenario(scenario).name(name).version(version));
    }
  }
}
