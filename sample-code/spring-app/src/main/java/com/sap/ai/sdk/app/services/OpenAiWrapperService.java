package com.sap.ai.sdk.app.services;

import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import com.sap.ai.sdk.foundationmodels.openaiwrapper.OpenAiClientProvider;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class demonstrating the official OpenAI Java SDK wrapper for SAP AI Core.
 *
 * <p>Uses {@link OpenAiClientProvider} to get an {@link OpenAIClient} backed by SAP AI Core, then
 * interacts with the official SDK types directly.
 */
@Service
@Slf4j
public class OpenAiWrapperService {

  /**
   * Chat completion request using the official OpenAI Java SDK via SAP AI Core.
   *
   * @return the chat completion response
   */
  @Nonnull
  public ChatCompletion chatCompletion() {
    final OpenAIClient client = OpenAiClientProvider.forModel(OpenAiModel.GPT_5);

    final ChatCompletionCreateParams params =
        ChatCompletionCreateParams.builder()
            .model(ChatModel.GPT_5)
            .addSystemMessage("Be a helpful assistant")
            .addUserMessage("Hello World! Why is this phrase so famous?")
            .build();

    return client.chat().completions().create(params);
  }

  /**
   * Chat completion request with streaming disabled, using structured output.
   *
   * @param userMessage the user message to send
   * @return the chat completion response
   */
  @Nonnull
  public ChatCompletion chatCompletion(@Nonnull final String userMessage) {
    final OpenAIClient client = OpenAiClientProvider.forModel(OpenAiModel.GPT_4O);

    final ChatCompletionCreateParams params =
        ChatCompletionCreateParams.builder()
            .model(com.openai.models.ChatModel.GPT_4O)
            .addUserMessage(userMessage)
            .build();

    return client.chat().completions().create(params);
  }
}
