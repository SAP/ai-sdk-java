package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.DataRepositoryType;
import com.sap.ai.sdk.orchestration.model.DocumentGroundingFilter;
import com.sap.ai.sdk.orchestration.model.GroundingModuleConfig;
import com.sap.ai.sdk.orchestration.model.GroundingModuleConfig.TypeEnum;
import com.sap.ai.sdk.orchestration.model.GroundingModuleConfigConfig;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.val;

/**
 * Grounding integrates external, contextually relevant, domain-specific, or real-time data into AI
 * processes. This data supplements the natural language processing capabilities of pre-trained
 * models, which are trained on general material.
 *
 * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/grounding">SAP AI
 *     Core: Orchestration - Grounding</a>
 */
@Beta
@Setter(onMethod_ = {@Nonnull})
@Accessors(fluent = true)
public class Grounding implements GroundingProvider {

  private String id = "someID";
  private DataRepositoryType dataRepositoryType = DataRepositoryType.VECTOR;
  private TypeEnum documentGroundingService = TypeEnum.DOCUMENT_GROUNDING_SERVICE;

  /**
   * Create a new default grounding provider.
   *
   * <p>It is by default a document grounding service with a vector data repository and id "someID".
   *
   * @return The grounding provider.
   */
  @Nonnull
  public static Grounding create() {
    return new Grounding();
  }

  /**
   * Create a prompt with grounding parameters included in the message.
   *
   * <p>It uses the inputParams {@code groundingInput} for the user message and {@code
   * groundingOutput} for the grounding context.
   *
   * @param message The user message.
   * @return The prompt with grounding.
   */
  @Nonnull
  public OrchestrationPrompt createGroundingPrompt(@Nonnull final String message) {
    return new OrchestrationPrompt(
        Map.of("groundingInput", message),
        Message.user(
            "{{?groundingInput}} Use the following information as additional context: {{?groundingOutput}}"));
  }

  @Nonnull
  @Override
  public GroundingModuleConfig createConfig() {
    val filterInner =
        DocumentGroundingFilter.create().id(id).dataRepositoryType(dataRepositoryType);
    val groundingConfigConfig =
        GroundingModuleConfigConfig.create()
            .inputParams(List.of("groundingInput"))
            .outputParam("groundingOutput")
            .addFiltersItem(filterInner);
    return GroundingModuleConfig.create()
        .type(documentGroundingService)
        .config(groundingConfigConfig);
  }
}
