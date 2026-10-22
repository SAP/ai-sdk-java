package com.sap.ai.sdk.orchestration;

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
public class OrchestrationConfigReference {
  String id;
  String scenario;
  String name;
  String version;
  Map<String, String> inputParameters;
  List<Message> messageHistory;

  /**
   * Set the chat history.
   *
   * @param messagesHistory the chat history to set.
   * @return a new instance of {@link OrchestrationConfigReference} with the specified chat history.
   */
  @Nonnull
  public OrchestrationConfigReference withMessageHistory(
      @Nonnull final List<Message> messagesHistory) {
    if (this.messageHistory.equals(messagesHistory)) {
      return this;
    }
    return new OrchestrationConfigReference(
        id, scenario, name, version, inputParameters, messagesHistory);
  }

  /**
   * Set the template parameters.
   *
   * @param inputParameters the template parameters to set.
   * @return a new instance of {@link OrchestrationConfigReference} with the specified chat history.
   */
  @Nonnull
  public OrchestrationConfigReference withInputParameters(
      @Nonnull final Map<String, String> inputParameters) {
    if (this.inputParameters.equals(inputParameters)) {
      return this;
    }
    return new OrchestrationConfigReference(
        id, scenario, name, version, inputParameters, messageHistory);
  }

  /**
   * Build a reference from an ID.
   *
   * @param id the id of the reference.
   * @return a reference object with the specified id.
   */
  @Nonnull
  public static OrchestrationConfigReference fromId(@Nonnull final String id) {
    return new OrchestrationConfigReference(id, null, null, null, Map.of(), List.of());
  }

  /**
   * Build a reference from a scenario, name, and version.
   *
   * @param scenario the scenario of the reference.
   * @return a builder object with the specified scenario.
   */
  @Nonnull
  public static Builder fromScenario(@Nonnull final String scenario) {
    return name ->
        version ->
            inputParameters ->
                new OrchestrationConfigReference(
                    null, scenario, name, version, inputParameters, List.of());
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
     * @param name the name of the reference.
     * @return a builder object with the specified scenario and name.
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
     * Build a config reference from a scenario, name, and version.
     *
     * @param version the version of the reference.
     * @return a reference object with the specified scenario, name, and version.
     */
    @Nonnull
    Builder2 version(@Nonnull final String version);
  }

  /** Intermediate object to build a config reference with the given scenario, name, and version. */
  @FunctionalInterface
  public interface Builder2 {
    /**
     * Build a config reference with the given scenario, name, and version.
     *
     * @param inputParameters the placeholder values for the config.
     * @return a config reference with the given scenario, name, and version.
     */
    @Nonnull
    OrchestrationConfigReference andInputParameters(
        @Nonnull final Map<String, String> inputParameters);
  }
}
