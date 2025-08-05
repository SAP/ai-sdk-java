package com.sap.ai.sdk.foundationmodels.openai;

import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionStreamOptions;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionRequestAllOfResponseFormat;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import lombok.*;

@With
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OpenAiChatCompletionConfig {

  /** Upto 4 Stop sequences to interrupts token generation and returns a response without them. */
  @Nullable List<String> stop;

  /**
   * Controls the randomness of the completion.
   *
   * <p>Lower values (e.g. 0.0) make the model more deterministic and repetitive, while higher
   * values (e.g. 1.0) make the model more random and creative.
   */
  @Nullable BigDecimal temperature;

  /**
   * Controls the cumulative probability threshold used for nucleus sampling. Alternative to {@link
   * #temperature}.
   *
   * <p>Lower values (e.g. 0.1) limit the model to consider only the smallest set of tokens whose
   * combined probabilities add up to at least 10% of the total.
   */
  @Nullable BigDecimal topP;

  /** Maximum number of tokens that can be generated for the completion. */
  @Nullable Integer maxTokens;

  /**
   * Maximum number of tokens that can be generated for the completion, including consumed reasoning
   * tokens. This field supersedes {@link #maxTokens} and should be used with newer models.
   */
  @Nullable Integer maxCompletionTokens;

  /**
   * Encourage new topic by penalising token based on their presence in the completion.
   *
   * <p>Value should be in range [-2, 2].
   */
  @Nullable BigDecimal presencePenalty;

  /**
   * Encourage new topic by penalising tokens based on their frequency in the completion.
   *
   * <p>Value should be in range [-2, 2].
   */
  @Nullable BigDecimal frequencyPenalty;

  /**
   * A map that adjusts the likelihood of specified tokens by adding a bias value (between -100 and
   * 100) to the logits before sampling. Extreme values can effectively ban or enforce the selection
   * of tokens.
   */
  @Nullable Map<String, Integer> logitBias;

  /**
   * Unique identifier for the end-user making the request. This can help with monitoring and abuse
   * detection.
   */
  @Nullable String user;

  /** Whether to include log probabilities in the response. */
  @Nullable Boolean logprobs;

  /**
   * Number of top log probabilities to return for each token. An integer between 0 and 20. This is
   * only relevant if {@code logprobs} is enabled.
   */
  @Nullable Integer topLogprobs;

  /** Number of completions to generate. */
  @Nullable Integer n;

  /** Whether to allow parallel tool calls. */
  @Nullable Boolean parallelToolCalls;

  /** Seed for random number generation. */
  @Nullable Integer seed;

  /** Options for streaming the completion response. */
  @Nullable ChatCompletionStreamOptions streamOptions;

  /** Response format for the completion. */
  @Nullable CreateChatCompletionRequestAllOfResponseFormat responseFormat;

  /**
   * Tools the model may invoke during chat completion (metadata only).
   *
   * <p>Use {@link #withToolsExecutable} for registering executable tools.
   */
  @Nullable List<ChatCompletionTool> tools;

  /**
   * Tools the model may invoke during chat completion that are also executable at application
   * runtime.
   *
   * @since 1.8.0
   */
  @Getter(value = AccessLevel.PACKAGE)
  @Nullable
  List<OpenAiTool> toolsExecutable;

  /** Option to control which tool is invoked by the model. */
  @Nullable OpenAiToolChoice toolChoice;
}
