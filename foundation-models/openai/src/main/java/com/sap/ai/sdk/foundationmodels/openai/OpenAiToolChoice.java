package com.sap.ai.sdk.foundationmodels.openai;

import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionNamedToolChoice;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionNamedToolChoiceFunction;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionToolChoiceOption;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * OpenAi ToolChoice to specify whether to call which tool.
 *
 * @since 1.4.0
 */
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenAiToolChoice {
  @Nonnull final ChatCompletionToolChoiceOption toolChoice;

  /** Only message generation will be performed without calling any tool. */
  public static final OpenAiToolChoice NONE =
      new OpenAiToolChoice(ChatCompletionToolChoiceOption.create("none"));

  /** The model may decide whether to call a (one or more) tool. */
  public static final OpenAiToolChoice OPTIONAL =
      new OpenAiToolChoice(ChatCompletionToolChoiceOption.create("auto"));

  /** The model must call one or more tools as part of its processing. */
  public static final OpenAiToolChoice REQUIRED =
      new OpenAiToolChoice(ChatCompletionToolChoiceOption.create("required"));

  /**
   * The model must call the function specified by {@code functionName}.
   *
   * @param functionName the name of the function that must be called.
   * @return the OpenAI tool choice.
   */
  @Nonnull
  public static OpenAiToolChoice function(@Nonnull final String functionName) {
    return new OpenAiToolChoice(
        ChatCompletionToolChoiceOption.createInnerChatCompletionNamedToolChoice(
            new ChatCompletionNamedToolChoice()
                .type(ChatCompletionNamedToolChoice.TypeEnum.FUNCTION)
                .function(new ChatCompletionNamedToolChoiceFunction().name(functionName))));
  }
}
