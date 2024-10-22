package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
final class Template {

  record Messages(@Nonnull List<ChatMessage> messages) implements TemplateConfig {}

  record IdReference(@Nonnull String templateId) implements TemplateConfig {}

  record NameReference(@Nonnull String name, @Nonnull String version, @Nonnull String scenario)
      implements TemplateConfig {}
}
