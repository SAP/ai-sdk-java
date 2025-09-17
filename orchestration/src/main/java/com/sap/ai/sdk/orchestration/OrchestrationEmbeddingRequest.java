package com.sap.ai.sdk.orchestration;

import static lombok.AccessLevel.NONE;
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
import lombok.Getter;
import lombok.Value;
import lombok.With;
import lombok.experimental.Tolerate;

/**
 * Represents a request for generating embeddings through the SAP AI Core Orchestration service.
 *
 * @since 1.12.0
 */
@Beta
@Value
@AllArgsConstructor(access = PRIVATE)
public class OrchestrationEmbeddingRequest {

  /** The embedding model to use for generating vector representations. */
  @Nonnull OrchestrationEmbeddingModel model;

  /** The list of text inputs to be converted into embeddings. */
  @Nonnull List<String> tokens;

  /** Optional masking providers for data privacy and security. */
  @With(value = PRIVATE)
  @Nullable
  List<MaskingProvider> masking;

  /** Optional token type classification to optimize embedding generation. */
  @With(value = PRIVATE)
  @Getter(NONE)
  @Nullable
  EmbeddingsInput.TypeEnum tokenType;

  /**
   * Create an embedding request using fluent API starting with model selection.
   *
   * <pre>{@code
   * OrchestrationEmbeddingRequest.forModel(myModel).forInputs("text to embed");
   * }</pre>
   *
   * @param model the embedding model to use
   * @return a step for specifying inputs
   */
  @Nonnull
  public static InputStep forModel(@Nonnull final OrchestrationEmbeddingModel model) {
    return tokens -> new OrchestrationEmbeddingRequest(model, List.copyOf(tokens), null, null);
  }

  /** Builder step for specifying text inputs to embed. */
  @FunctionalInterface
  public interface InputStep {

    /**
     * Specifies text inputs to be embedded.
     *
     * @param tokens the text strings to embed
     * @return a new embedding request instance
     */
    @Nonnull
    OrchestrationEmbeddingRequest forInputs(@Nonnull final List<String> tokens);

    /**
     * Specifies multiple text inputs using variable arguments.
     *
     * @param tokens one or more strings to embed
     * @return a new embedding request instance
     */
    @Nonnull
    default OrchestrationEmbeddingRequest forInputs(@Nonnull final String... tokens) {
      return forInputs(List.of(tokens));
    }
  }

  /**
   * Adds data masking providers to enable detection and masking of sensitive information.
   *
   * @param maskingProvider the primary masking provider
   * @param maskingProviders additional masking providers
   * @return a new request instance with the specified masking providers
   * @see MaskingProvider
   */
  @Tolerate
  @Nonnull
  public OrchestrationEmbeddingRequest withMasking(
      @Nonnull final MaskingProvider maskingProvider,
      @Nonnull final MaskingProvider... maskingProviders) {
    return withMasking(Lists.asList(maskingProvider, maskingProviders));
  }

  /**
   * Configures this request to optimize embeddings for document content.
   *
   * @return a new request instance configured for document embedding
   */
  @Nonnull
  public OrchestrationEmbeddingRequest asDocument() {
    return withTokenType(EmbeddingsInput.TypeEnum.DOCUMENT);
  }

  /**
   * Configures this request to optimize embeddings for general text content.
   *
   * @return a new request instance configured for text embedding
   */
  @Nonnull
  public OrchestrationEmbeddingRequest asText() {
    return withTokenType(EmbeddingsInput.TypeEnum.TEXT);
  }

  /**
   * Configures this request to optimize embeddings for query content.
   *
   * @return a new request instance configured for query embedding
   */
  @Nonnull
  public OrchestrationEmbeddingRequest asQuery() {
    return withTokenType(EmbeddingsInput.TypeEnum.QUERY);
  }

  @Nonnull
  EmbeddingsPostRequest createEmbeddingsPostRequest() {

    final var input =
        EmbeddingsInput.create().text(EmbeddingsInputText.create(tokens)).type(tokenType);
    final var embeddingsModelConfig =
        EmbeddingsModelConfig.create().model(model.createEmbeddingsModelDetails());
    final var modules =
        EmbeddingsOrchestrationConfig.create()
            .modules(EmbeddingsModuleConfigs.create().embeddings(embeddingsModelConfig));

    if (masking != null) {
      final var dpiConfigs = masking.stream().map(MaskingProvider::createConfig).toList();
      modules.getModules().setMasking(MaskingModuleConfigProviders.create().providers(dpiConfigs));
    }
    return EmbeddingsPostRequest.create().config(modules).input(input);
  }
}
