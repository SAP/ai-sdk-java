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
  OrchestrationPrompt prompt = new OrchestrationPrompt(Map.of());

  /**
   * Set the chat history for this reference.
   *
   * @param messagesHistory The chat history to set.
   * @return The current instance of {@link OrchestrationConfigReference} with the changed chat
   *     history.
   */
  @Nonnull
  public OrchestrationConfigReference messageHistory(@Nonnull final List<Message> messagesHistory) {
    prompt.messageHistory(messagesHistory);
    return this;
  }

  /**
   * Set the template parameters for this reference.
   *
   * @param templateParameters The template parameters to set.
   * @return The current instance of {@link OrchestrationConfigReference} with the changed chat
   *     history.
   */
  @Nonnull
  public OrchestrationConfigReference templateParameters(
      @Nonnull final Map<String, String> templateParameters) {
    prompt.templateParameters(templateParameters);
    return this;
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
