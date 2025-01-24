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

  private DocumentGroundingFilter filter =
      DocumentGroundingFilter.create().id("").dataRepositoryType(DataRepositoryType.VECTOR);
  private TypeEnum documentGroundingService = TypeEnum.DOCUMENT_GROUNDING_SERVICE;

  /**
   * Create a new default grounding provider.
   *
   * <p>It is by default a document grounding service with a vector data repository.
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
   * <p>It uses the inputParams {@code userMessage} for the user message and {@code
   * groundingContext} for the grounding context.
   *
   * @param message The user message.
   * @return The prompt with grounding.
   */
  @Nonnull
  public OrchestrationPrompt createGroundingPrompt(@Nonnull final String message) {
    return new OrchestrationPrompt(
        Map.of("userMessage", message),
        Message.user(
            "{{?userMessage}} Use the following information as additional context: {{?groundingContext}}"));
  }

  @Nonnull
  @Override
  public GroundingModuleConfig createConfig() {
    val groundingConfigConfig =
        GroundingModuleConfigConfig.create()
            .inputParams(List.of("userMessage"))
            .outputParam("groundingContext")
            .addFiltersItem(filter);
    return GroundingModuleConfig.create()
        .type(documentGroundingService)
        .config(groundingConfigConfig);
  }
}
