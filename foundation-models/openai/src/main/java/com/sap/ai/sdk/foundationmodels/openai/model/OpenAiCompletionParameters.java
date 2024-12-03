package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** OpenAI completion input parameters. */
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class OpenAiCompletionParameters {
  /**
   * The maximum number of [tokens](/tokenizer) that can be generated in the completion. The token
   * count of your prompt plus max_tokens can't exceed the model's context length. Most models have
   * a context length of 2048 tokens (except for the newest models, which support 4096).
   */
  @JsonProperty("max_tokens")
  @Setter(onParam_ = @Nullable)
  private Integer maxTokens;

  /**
   * What sampling temperature to use, between 0 and 2. Higher values means the model will take more
   * risks. Try 0.9 for more creative applications, and 0 ({@code argmax sampling}) for ones with a
   * well-defined answer. We generally recommend altering this or top_p but not both.
   */
  @JsonProperty("temperature")
  @Setter(onParam_ = @Nullable)
  private Double temperature;

  /**
   * An alternative to sampling with temperature, called nucleus sampling, where the model considers
   * the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising
   * the top 10% probability mass are considered. We generally recommend altering this or
   * temperature but not both.
   */
  @JsonProperty("top_p")
  @Setter(onParam_ = @Nullable)
  private Double topP;

  /**
   * Modify the likelihood of specified tokens appearing in the completion. Accepts a json object
   * that maps tokens (specified by their token ID in the GPT tokenizer) to an associated bias value
   * from -100 to 100. You can use this tokenizer tool (which works for both GPT-2 and GPT-3) to
   * convert text to token IDs. Mathematically, the bias is added to the logits generated by the
   * model prior to sampling. The exact effect will vary per model, but values between -1 and 1
   * should decrease or increase likelihood of selection; values like -100 or 100 should result in a
   * ban or exclusive selection of the relevant token. As an example, you can pass {"50256": -100}
   * to prevent the <|endoftext|> token from being generated.
   */
  @JsonProperty("logit_bias")
  @Setter(onParam_ = @Nullable)
  private Map<String, Object> logitBias;

  /**
   * A unique identifier representing your end-user, which can help monitoring and detecting abuse.
   */
  @JsonProperty("user")
  @Setter(onParam_ = @Nullable)
  private String user;

  /**
   * How many completions to generate for each prompt. Note: Because this parameter generates many
   * completions, it can quickly consume your token quota. Use carefully and ensure that you have
   * reasonable settings for max_tokens and stop.
   */
  @JsonProperty("n")
  @Setter(onParam_ = @Nullable)
  private Integer n;

  /**
   * Up to four sequences where the API will stop generating further tokens. The returned text won't
   * contain the stop sequence.
   */
  @JsonProperty("stop")
  @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
  @Nullable
  private List<String> stop;

  /**
   * Number between -2.0 and 2.0. Positive values penalize new tokens based on whether they appear
   * in the text so far, increasing the model's likelihood to talk about new topics.
   */
  @JsonProperty("presence_penalty")
  @Setter(onParam_ = @Nullable)
  private Double presencePenalty;

  /**
   * Number between -2.0 and 2.0. Positive values penalize new tokens based on their existing
   * frequency in the text so far, decreasing the model's likelihood to repeat the same line
   * verbatim.
   */
  @JsonProperty("frequency_penalty")
  @Setter(onParam_ = @Nullable)
  private Double frequencyPenalty;

  /**
   * If set, partial message deltas will be sent, like in ChatGPT. Tokens will be sent as data-only
   * server-sent events as they become available, with the stream terminated by a `data: [DONE]`
   * message. Default: false.
   */
  @JsonProperty("stream")
  private Boolean stream;

  /**
   * Whether to return log probabilities of the output tokens or not. If true, returns the log
   * probabilities of each output token returned in the `content` of `message`. This option is
   * currently not available on the `gpt-4-vision-preview` model. Default: false.
   */
  @JsonProperty("logprobs")
  @Setter(onParam_ = @Nullable)
  private Boolean logprobs;

  /**
   * An integer between 0 and 5 specifying the number of most likely tokens to return at each token
   * position, each with an associated log probability. `logprobs` must be set to `true` if this
   * parameter is used.
   */
  @JsonProperty("top_logprobs")
  @Setter(onParam_ = @Nullable)
  private Integer topLogprobs;

  /**
   * If set, partial message deltas will be sent, like in ChatGPT. Tokens will be sent as data-only
   * <a
   * href="https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events/Using_server-sent_events#event_stream_format">server-sent
   * events</a> as they become available, with the stream terminated by a {@code data: [DONE]}
   * message. Only set this when you set {@code stream: true}.
   */
  @JsonProperty("stream_options")
  private OpenAiStreamOptions streamOptions;

  /** "stream_options": { "include_usage": "true" } */
  @RequiredArgsConstructor
  @Setter
  @JsonFormat(shape = JsonFormat.Shape.OBJECT)
  @EqualsAndHashCode
  @ToString
  public static class OpenAiStreamOptions {
    /**
     * If set, an additional chunk will be streamed before the {@code data: [DONE]} message. The
     * usage field on this chunk shows the token usage statistics for the entire request, and the
     * choices field will always be an empty array. All other chunks will also include a {@code
     * usage} field, but with a null value.
     */
    @JsonProperty("include_usage")
    private Boolean include_usage;
  }

  /**
   * Please use {@link
   * com.sap.ai.sdk.foundationmodels.openai.OpenAiClient#streamChatCompletionDeltas(OpenAiChatCompletionParameters)}
   * instead.
   *
   * <p>Enable streaming of the completion. If enabled, partial message deltas will be sent.
   */
  public void enableStreaming() {
    this.stream = true;
    this.streamOptions = new OpenAiStreamOptions().setInclude_usage(true);
  }

  /**
   * Up to four sequences where the API will stop generating further tokens. The returned text won't
   * contain the stop sequence.
   *
   * @param values The stop sequences.
   * @return ${code this} instance for chaining.
   */
  @Nonnull
  public OpenAiCompletionParameters setStop(@Nullable final String... values) {
    this.stop = values == null || values.length == 0 ? null : List.of(values);
    return this;
  }
}
