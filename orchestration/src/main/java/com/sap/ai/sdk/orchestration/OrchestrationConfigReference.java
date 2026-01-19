package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

/**
 * Class representing a reference to an Orchestration config stored in prompt registry.
 *
 * @since 1.15.0
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter(AccessLevel.PACKAGE)
@Beta
public class OrchestrationConfigReference {
  String id;
  String scenario;
  String name;
  String version;

  //  @Getter(AccessLevel.PRIVATE)
  OrchestrationPrompt prompt = new OrchestrationPrompt(Map.of());

  /**
   * Set the chat history.
   *
   * @param messagesHistory The chat history to set.
   * @return A new instance of {@link OrchestrationConfigReference} with the specified chat history.
   */
  @Nonnull
  public OrchestrationConfigReference withMessageHistory(
      @Nonnull final List<Message> messagesHistory) {
    if (prompt.getMessagesHistory().equals(messagesHistory)) {
      return this;
    }
    final var reference = new OrchestrationConfigReference(id, scenario, name, version);
    reference
        .prompt
        .templateParameters(this.prompt.getTemplateParameters())
        .messageHistory(messagesHistory);
    return reference;
  }

  /**
   * Set the template parameters.
   *
   * @param templateParameters The template parameters to set.
   * @return A new instance of {@link OrchestrationConfigReference} with the specified chat history.
   */
  @Nonnull
  public OrchestrationConfigReference withTemplateParameters(
      @Nonnull final Map<String, String> templateParameters) {
    if (prompt.getTemplateParameters().equals(templateParameters)) {
      return this;
    }
    final var reference = new OrchestrationConfigReference(id, scenario, name, version);
    reference
        .prompt
        .templateParameters(templateParameters)
        .messageHistory(this.prompt.getMessagesHistory());
    return reference;
  }

  /**
   * Build a reference from an ID.
   *
   * @param id The id of the reference
   * @return A reference object with the specified id
   */
  @Nonnull
  public static OrchestrationConfigReference fromId(@Nonnull final String id) {
    return new OrchestrationConfigReference(id, null, null, null);
  }

  /**
   * Build a reference from a scenario, name, and version.
   *
   * @param scenario The scenario of the reference
   * @return A builder object with the specified scenario
   */
  @Nonnull
  public static Builder fromScenario(@Nonnull final String scenario) {
    return (name) -> (version) -> new OrchestrationConfigReference(null, scenario, name, version);
  }

  /**
   * Builder to create an Orchestration config reference from scenario, name, and version.
   *
   * @since 1.14.0
   */
  public interface Builder {

    /**
     * Build a reference from a scenario, name, and version.
     *
     * @param name The name of the reference
     * @return A builder object with the specified scenario and name
     */
    @Nonnull
    Builder1 name(@Nonnull final String name);
  }

  /**
   * Builder to create an Orchestration config reference from scenario, name, and version.
   *
   * @since 1.14.0
   */
  public interface Builder1 {

    /**
     * Build a reference from a scenario, name, and version.
     *
     * @param version The version of the reference
     * @return A reference object with the specified scenario, name, and version
     */
    @Nonnull
    OrchestrationConfigReference version(@Nonnull final String version);
  }
}
