package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationEmbeddingModel.TEXT_EMBEDDING_3_SMALL;
import static com.sap.ai.sdk.orchestration.model.EmbeddingsInput.TypeEnum.DOCUMENT;
import static com.sap.ai.sdk.orchestration.model.EmbeddingsModelParams.EncodingFormatEnum.BASE64;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.orchestration.model.DPIConfig;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.model.DPIStandardEntity;
import com.sap.ai.sdk.orchestration.model.EmbeddingsInputText;
import com.sap.ai.sdk.orchestration.model.MaskingModuleConfigProviders;
import java.util.List;
import org.junit.jupiter.api.Test;

class OrchestrationEmbeddingTest {

  @Test
  void embeddingModelTest() {
    final var model = TEXT_EMBEDDING_3_SMALL;
    assertThat(model.name().equals("text-embedding-3-small"));
    assertThat(model.version()).isNull();
    assertThat(model.dimensions()).isNull();
    assertThat(model.encodingFormat()).isNull();
    assertThat(model.normalize()).isNull();

    final var model2 =
        TEXT_EMBEDDING_3_SMALL
            .withVersion("some-version")
            .withDimensions(1536)
            .withNormalize(true)
            .withEncodingFormat(BASE64);
    assertThat(model2.name().equals("text-embedding-3-large"));
    assertThat(model2.version().equals("some-version"));
    assertThat(model2.dimensions().equals(1536));
    assertThat(model2.normalize().equals(true));
    assertThat(model2.encodingFormat().equals(BASE64));

    final var custom = new OrchestrationEmbeddingModel("custom-model");
    assertThat(custom.name()).isEqualTo("custom-model");
  }

  @Test
  void embeddingRequestTest() {
    final var request =
        OrchestrationEmbeddingRequest.create(TEXT_EMBEDDING_3_SMALL, List.of("token1", "token2"))
            .asDocument()
            .withMasking(
                DpiMasking.anonymization()
                    .withEntities(DPIEntities.ADDRESS)
                    .withAllowList(List.of("Alice")));

    final var postRequest = request.createEmbeddingsPostRequest();
    assertThat(postRequest.getInput().getText())
        .isEqualTo(EmbeddingsInputText.create(List.of("token1", "token2")));
    assertThat(postRequest.getInput().getType()).isEqualTo(DOCUMENT);
    final var embeddingsModelConfig = postRequest.getConfig().getModules().getEmbeddings();
    assertThat(embeddingsModelConfig.getModel().getName()).isEqualTo("text-embedding-3-small");
    assertThat(embeddingsModelConfig.getModel().getVersion()).isNull();
    assertThat(embeddingsModelConfig.getModel().getParams().getDimensions()).isNull();
    assertThat(embeddingsModelConfig.getModel().getParams().getEncodingFormat()).isNull();
    assertThat(embeddingsModelConfig.getModel().getParams().isNormalize()).isNull();

    final var maskingConfig = postRequest.getConfig().getModules().getMasking();
    assertThat(maskingConfig)
        .isInstanceOfSatisfying(
            MaskingModuleConfigProviders.class,
            cfg -> {
              assertThat(cfg.getProviders()).hasSize(1);
              assertThat(cfg.getProviders().get(0).getType())
                  .isEqualTo(DPIConfig.TypeEnum.SAP_DATA_PRIVACY_INTEGRATION);
              assertThat(cfg.getProviders().get(0).getEntities())
                  .isEqualTo(List.of(DPIStandardEntity.create().type(DPIEntities.ADDRESS)));
              assertThat(cfg.getProviders().get(0).getAllowlist()).isEqualTo(List.of("Alice"));
            });
  }
}
