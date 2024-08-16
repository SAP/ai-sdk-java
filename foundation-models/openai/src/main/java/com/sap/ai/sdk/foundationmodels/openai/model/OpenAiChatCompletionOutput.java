package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatAssistantMessage;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** OpenAI chat completion output. */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString
public class OpenAiChatCompletionOutput extends OpenAiCompletionOutput {
  /** List of result candidates. */
  @JsonProperty("choices")
  @Getter(onMethod_ = @Nonnull)
  private List<OpenAiChatCompletionChoice> choices;

  /**
   * Can be used in conjunction with the seed request parameter to understand when backend changes
   * have been made that might impact determinism.
   */
  @JsonProperty("system_fingerprint")
  @Getter(onMethod_ = @Nonnull)
  private String systemFingerprint;

  public void addDelta(OpenAiChatCompletionOutput delta) {
    // TODO: Assign every field if not null on all parent and children classes.
    //       Right now we only assign content message.
    var deltaMessage = delta.getChoices().get(0).getMessage();
    if (deltaMessage == null) {
      return;
    }
    String deltaContent = deltaMessage.getContent();
    if (deltaContent == null) {
      return;
    }
    if (choices.isEmpty()) {
      var choice =
          new OpenAiChatCompletionChoice()
              .setMessage(new OpenAiChatAssistantMessage().setContent(deltaContent));
      choices.add(choice);
      return;
    }
    var message = choices.get(0).getMessage();
    message.setContent(message.getContent() + deltaContent);
  }
}
