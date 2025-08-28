package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.model.EmbeddingsInput.TypeEnum.DOCUMENT;
import static com.sap.ai.sdk.orchestration.model.EmbeddingsInput.TypeEnum.QUERY;
import static com.sap.ai.sdk.orchestration.model.EmbeddingsInput.TypeEnum.TEXT;
import static lombok.AccessLevel.PRIVATE;

import com.google.common.annotations.Beta;
import com.google.common.collect.Lists;
import com.sap.ai.sdk.orchestration.model.EmbeddingsInput;
import com.sap.ai.sdk.orchestration.model.EmbeddingsInputText;
import com.sap.ai.sdk.orchestration.model.EmbeddingsModelConfig;
import com.sap.ai.sdk.orchestration.model.EmbeddingsModuleConfigs;
import com.sap.ai.sdk.orchestration.model.EmbeddingsOrchestrationConfig;
import com.sap.ai.sdk.orchestration.model.EmbeddingsPostRequest;
import com.sap.ai.sdk.orchestration.model.MaskingModuleConfigProviders;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;
import lombok.experimental.Tolerate;

// Do we need staged input builder here?
// Do we need an enum for tokenType?
@Beta
@Value
@AllArgsConstructor(access = PRIVATE)
public class OrchestrationEmbeddingRequest {

  @Nonnull OrchestrationEmbeddingModel model;
  @Nonnull List<String> tokens;

  @With(value = PRIVATE)
  @Nullable
  List<MaskingProvider> masking;

  @With(value = PRIVATE)
  @Nullable
  EmbeddingsInput.TypeEnum tokenType;

  public static OrchestrationEmbeddingRequest create(
      OrchestrationEmbeddingModel model, List<String> tokens) {
    return new OrchestrationEmbeddingRequest(model, tokens, null, null);
  }

  @Tolerate
  @Nonnull
  public OrchestrationEmbeddingRequest withMasking(
      @Nonnull final MaskingProvider maskingProvider,
      @Nonnull final MaskingProvider... maskingProviders) {
    return withMasking(Lists.asList(maskingProvider, maskingProviders));
  }

  @Nonnull
  public OrchestrationEmbeddingRequest asDocument() {
    return withTokenType(DOCUMENT);
  }

  @Nonnull
  public OrchestrationEmbeddingRequest asText() {
    return withTokenType(TEXT);
  }

  @Nonnull
  public OrchestrationEmbeddingRequest asQuery() {
    return withTokenType(QUERY);
  }

  EmbeddingsPostRequest createEmbeddingsPostRequest() {

    final var input =
        EmbeddingsInput.create().text(EmbeddingsInputText.create(tokens)).type(tokenType);
    final var embeddingsModelConfig =
        EmbeddingsModelConfig.create().model(this.model.createEmbeddingsModelDetails());
    final var modules =
        EmbeddingsOrchestrationConfig.create()
            .modules(EmbeddingsModuleConfigs.create().embeddings(embeddingsModelConfig));

    if (masking != null) {
      final var dpiConfigs = this.masking.stream().map(MaskingProvider::createConfig).toList();
      modules.getModules().setMasking(MaskingModuleConfigProviders.create().providers(dpiConfigs));
    }

    return EmbeddingsPostRequest.create().config(modules).input(input);
  }
}
