package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.AzureFilterThreshold.ALLOW_SAFE;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.orchestration.model.DPIEntities;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OrchestrationModuleConfigWithRefTest {

  @Test
  void withTemplateConfigReturnsWrapperWithRef() {
    var ref = TemplateConfig.reference().byId("abc");
    OrchestrationModuleConfigWithRef withRef =
        new OrchestrationModuleConfig()
            .withLlmConfig(OrchestrationAiModel.GPT_4O)
            .withTemplateConfig(ref);

    assertThat(withRef.getTemplateRef()).isSameAs(ref);
    assertThat(withRef.getInner()).isNotNull();
  }

  @Test
  void templateRefCarriesHistoryAndParams() {
    var ref =
        TemplateConfig.reference()
            .byId("abc")
            .withMessageHistory(List.of(new UserMessage("hi")))
            .withTemplateParameters(Map.of("k", "v"));

    assertThat(ref.getMessagesHistory()).hasSize(1);
    assertThat(ref.getTemplateParameters()).containsEntry("k", "v");
  }

  @Test
  void delegateMethodsPreserveTemplateRef() {
    var ref = TemplateConfig.reference().byId("abc");
    var withRef =
        new OrchestrationModuleConfig()
            .withLlmConfig(OrchestrationAiModel.GPT_4O)
            .withTemplateConfig(ref);

    assertThat(withRef.withLlmConfig(OrchestrationAiModel.GPT_4O).getTemplateRef()).isSameAs(ref);
    assertThat(withRef.withLlmConfig(OrchestrationAiModel.GPT_4O.createConfig()).getTemplateRef())
        .isSameAs(ref);

    var filter = new AzureContentFilter().hate(ALLOW_SAFE);
    assertThat(withRef.withInputFiltering(filter).getTemplateRef()).isSameAs(ref);
    assertThat(withRef.withOutputFiltering(filter).getTemplateRef()).isSameAs(ref);

    var masking = DpiMasking.anonymization().withEntities(DPIEntities.PERSON);
    assertThat(withRef.withMaskingConfig(masking).getTemplateRef()).isSameAs(ref);

    assertThat(withRef.withGrounding(Grounding.create()).getTemplateRef()).isSameAs(ref);

    assertThat(
            withRef
                .withInputTranslationConfig(TranslationConfig.translateInputTo("en-US"))
                .getTemplateRef())
        .isSameAs(ref);
    assertThat(
            withRef
                .withOutputTranslationConfig(TranslationConfig.translateOutputTo("de-DE"))
                .getTemplateRef())
        .isSameAs(ref);

    assertThat(withRef.withStreamConfig(new OrchestrationStreamConfig()).getTemplateRef())
        .isSameAs(ref);
  }
}
