package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.Template.IdReference;
import com.sap.ai.sdk.orchestration.Template.Messages;
import com.sap.ai.sdk.orchestration.Template.NameReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.val;

/** A template configuration for orchestration requests. */
public sealed interface TemplateConfig permits Messages, IdReference, NameReference {

  /**
   * Creates a template from one or more messages. Use {@link TemplateVariable} to insert template
   * variables into your messages.
   *
   * <p>Note: Any occurrence of the template pattern {{?...}} in these messages will be interpreted
   * as a template and be filled with values from {@link OrchestrationPrompt}. This literal can not
   * be escaped.
   *
   * @param message the first message
   * @param messages (optional) further messages
   * @return a template configured with the given messages.
   * @see TemplateVariable
   * @see #fromMessages(List)
   */
  @Nonnull
  static TemplateConfig fromMessages(
      @Nonnull final Message message, @Nonnull final Message... messages) {
    val allMessages = new ArrayList<Message>();
    allMessages.add(message);
    allMessages.addAll(Arrays.asList(messages));

    return new Messages(allMessages);
  }

  /**
   * Convenience overload of {@link #fromMessages(Message, Message...)}.
   *
   * @param messages list of messages with at least one message.
   * @return a template configured with the given messages.
   * @see TemplateVariable
   * @see #fromMessages(Message, Message...)
   */
  @Nonnull
  static TemplateConfig fromMessages(@Nonnull final List<Message> messages) {
    return new Messages(new ArrayList<>(messages));
  }

  /**
   * Reference a template by its ID from the prompt repository.
   *
   * @param templateId the ID of the template
   * @return a template reference.
   * @see #referenceByName(String, String, String)
   */
  @Nonnull
  static TemplateConfig referenceById(@Nonnull final String templateId) {
    return new IdReference(templateId);
  }

  /**
   * Reference a template by its ID from the prompt repository.
   *
   * @param name the name of the template
   * @param version the version of the template
   * @param scenario the scenario of the template
   * @return a template reference.
   * @see #referenceById(String)
   */
  @Nonnull
  static TemplateConfig referenceByName(
      @Nonnull final String name, @Nonnull final String version, @Nonnull final String scenario) {
    return new NameReference(name, version, scenario);
  }
}
