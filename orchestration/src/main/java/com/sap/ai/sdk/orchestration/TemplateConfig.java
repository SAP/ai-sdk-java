package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.Template.IdReference;
import com.sap.ai.sdk.orchestration.Template.Messages;
import com.sap.ai.sdk.orchestration.Template.NameReference;
import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public sealed interface TemplateConfig permits Messages, IdReference, NameReference {

  @Nonnull
  static TemplateConfig fromMessages(
      @Nonnull ChatMessage message, @Nullable ChatMessage... messages) {
    final var allMessages = new ArrayList<ChatMessage>();
    allMessages.add(message);
    if (messages != null) {
      allMessages.addAll(Arrays.asList(messages));
    }

    return new Messages(allMessages);
  }

  @Nonnull
  static TemplateConfig fromMessages(@Nonnull List<ChatMessage> messages) {
    return new Messages(new ArrayList<>(messages));
  }

  @Nonnull
  static TemplateConfig referenceById(@Nonnull String templateId) {
    return new IdReference(templateId);
  }

  @Nonnull
  static TemplateConfig referenceByName(
      @Nonnull String name, @Nonnull String version, @Nonnull String scenario) {
    return new NameReference(name, version, scenario);
  }
}
