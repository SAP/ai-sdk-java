package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.CompletionPostRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Delegate;

@Value
@AllArgsConstructor
public class OrchestrationPrompt implements OrchestrationConfig<OrchestrationPrompt> {
  @Nonnull List<Message> messages;
  @Nonnull Map<String, String> templateParameters;

  private interface IDelegate extends OrchestrationConfig<OrchestrationPrompt> {}

  @Getter(AccessLevel.NONE)
  @Delegate(types = IDelegate.class)
  @Nonnull
  DefaultOrchestrationConfig<OrchestrationPrompt> delegate =
      DefaultOrchestrationConfig.asDelegateFor(this);

  public OrchestrationPrompt(@Nonnull final String message) {
    this(List.of(new UserMessage(message)), Map.of());
  }

  public OrchestrationPrompt(@Nonnull final Message message, @Nullable final Message... messages) {
    final var allMessages = new ArrayList<Message>();
    allMessages.add(message);
    if (messages != null) {
      allMessages.addAll(Arrays.asList(messages));
    }
    this.messages = allMessages;
    this.templateParameters = Map.of();
  }

  public OrchestrationPrompt(@Nonnull final List<Message> messages) {
    this(messages, Map.of());
  }

  public OrchestrationPrompt(@Nonnull final Map<String, String> inputParams) {
    this(List.of(), inputParams);
  }

  @Nonnull
  CompletionPostRequest toCompletionPostRequestDTO(@Nonnull final OrchestrationConfig<?> defaults) {
    // duplicate the prompt config, then apply the defaults to the copy
    // that way this prompt remains unchanged and can be reused
    var config = DefaultOrchestrationConfig.standalone().copyFrom(this).copyFrom(defaults);
    var moduleConfigDTO = ModuleConfigFactory.toModuleConfigDTO(config, messages);
    return CompletionPostRequest.create()
        .orchestrationConfig(
            com.sap.ai.sdk.orchestration.client.model.OrchestrationConfig.create()
                .moduleConfigurations(moduleConfigDTO))
        .inputParams(templateParameters);
  }
}
