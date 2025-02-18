package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.orchestration.OrchestrationAiModel.GPT_35_TURBO;

import com.sap.ai.sdk.orchestration.DpiMasking;
import com.sap.ai.sdk.orchestration.OrchestrationModuleConfig;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.spring.OrchestrationChatModel;
import com.sap.ai.sdk.orchestration.spring.OrchestrationChatOptions;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.val;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/** Service class for the Orchestration service */
@Service
public class SpringAiOrchestrationService {
  private final ChatModel client = new OrchestrationChatModel();
  private final OrchestrationModuleConfig config =
      new OrchestrationModuleConfig().withLlmConfig(GPT_35_TURBO);
  private final OrchestrationChatOptions defaultOptions = new OrchestrationChatOptions(config);

  /**
   * Chat request to OpenAI through the Orchestration service with a simple prompt.
   *
   * @return the assistant response object
   */
  @Nonnull
  public ChatResponse completion() {
    val prompt = new Prompt("What is the capital of France?", defaultOptions);

    return client.call(prompt);
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @return a stream of assistant message responses
   */
  @Nonnull
  public Flux<ChatResponse> streamChatCompletion() {
    val prompt =
        new Prompt(
            "Can you give me the first 100 numbers of the Fibonacci sequence?", defaultOptions);
    return client.stream(prompt);
  }

  /**
   * Chat request to OpenAI through the Orchestration service with a template.
   *
   * @return the assistant response object
   */
  @Nonnull
  public ChatResponse template() {
    val template = new PromptTemplate("{input}");
    val prompt = template.create(Map.of("input", "What is the capital of France?"), defaultOptions);

    return client.call(prompt);
  }

  /**
   * Let the orchestration service evaluate the feedback on the AI SDK provided by a hypothetical
   * user. Anonymize any names given as they are not relevant for judging the sentiment of the
   * feedback.
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/data-masking">SAP AI
   *     Core: Orchestration - Data Masking</a>
   * @return the assistant response object
   */
  @Nonnull
  public ChatResponse masking() {
    val masking =
        DpiMasking.anonymization()
            .withEntities(DPIEntities.EMAIL, DPIEntities.ADDRESS, DPIEntities.LOCATION);

    val opts = new OrchestrationChatOptions(config.withMaskingConfig(masking));
    val prompt =
        new Prompt(
            "Please write 'Hello World!' to me via email. My email address is foo.bar@baz.ai",
            opts);

    return client.call(prompt);
  }

  /**
   * Register a function that will be called when the user asks for the weather. <a
   * href="https://docs.spring.io/spring-ai/reference/api/chat/functions/openai-chat-functions.html#_registercall_functions_with_prompt_options">Spring
   * AI Function Calling</a>
   *
   * @return the assistant response object
   */
  @Nonnull
  public ChatResponse functionCalling() {
    final OrchestrationChatOptions options = new OrchestrationChatOptions(config);
    options.setToolCallbacks(
        List.of(
            FunctionToolCallback.builder("CurrentWeather", new MockWeatherService())
                .description("Get the weather in location") // (2) function description
                .inputType(MockWeatherService.Request.class) // (3) function input type
                .build()));
    val prompt = new Prompt("What is the weather in Potsdam and in Toulouse?", options);

    return client.call(prompt);
  }
}
