package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.Template.IdReference;
import com.sap.ai.sdk.orchestration.Template.Messages;
import com.sap.ai.sdk.orchestration.Template.NameReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public sealed interface TemplateConfig permits Messages, IdReference, NameReference {

  @Nonnull
  static TemplateConfig fromMessages(@Nonnull Message message, @Nullable Message... messages) {
    final var allMessages = new ArrayList<Message>();
    allMessages.add(message);
    if (messages != null) {
      allMessages.addAll(Arrays.asList(messages));
    }

    return new Messages(allMessages);
  }

  @Nonnull
  static TemplateConfig fromMessages(@Nonnull List<Message> messages) {
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
