package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.PromptTemplateReference.ScopeEnum.TENANT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.sap.ai.sdk.orchestration.model.PromptTemplatingModuleConfigPrompt;
import com.sap.ai.sdk.orchestration.model.TemplateRefByID;
import com.sap.ai.sdk.orchestration.model.TemplateRefByScenarioNameVersion;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.EqualsAndHashCode;

/**
 * Create a prompt template locally or reference an existing prompt template stored in context
 * registry. Upon request execution, this template will be enhanced with any messages and parameter
 * values. <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/templating">SAP
 * AI Core: Orchestration - Templating</a>
 *
 * @since 2.0.0
 */
@EqualsAndHashCode
public abstract class PromptTemplate {

  /**
   * Get the placeholder values supplied for this template.
   *
   * @return the placeholder values supplied for this template.
   */
  @Nonnull
  public abstract Map<String, String> getInputParameters();

  /**
   * Create a low-level representation of the template.
   *
   * @return the low-level representation of the template.
   */
  @Nonnull
  protected abstract PromptTemplatingModuleConfigPrompt toLowLevel();

  /**
   * Build a template containing a single user message.
   *
   * @param message the text of the user message.
   * @return a new template containing the user message.
   */
  @Nonnull
  public static LocalPromptTemplate create(@Nonnull final String message) {
    return create(Message.user(message));
  }

  /**
   * Build a template from the given messages.
   *
   * @param message the first message to use in the template.
   * @param moreMessages additional messages to use in the template.
   * @return a new template containing the given messages.
   */
  @Nonnull
  public static LocalPromptTemplate create(
      @Nonnull final Message message, @Nonnull final Message... moreMessages) {
    return new LocalPromptTemplate(
        Stream.concat(Stream.of(message), Stream.of(moreMessages))
            .map(Message::createChatMessage)
            .toList(),
        List.of(),
        new TreeMap<>(),
        null,
        null,
        null);
  }

  /**
   * Create an {@link LocalPromptTemplate} from a JSON provided as String.
   *
   * @param inputJson the provided JSON.
   * @return a template representing the provided JSON.
   * @throws IOException if the JSON cannot be deserialized.
   * @since 1.7.0
   */
  @Nullable
  public static LocalPromptTemplate fromJson(@Nonnull final String inputJson) throws IOException {
    final ObjectMapper objectMapper =
        OrchestrationJacksonConfiguration.getOrchestrationObjectMapper();
    final JsonNode rootNode = objectMapper.readTree(inputJson);
    return objectMapper.treeToValue(rootNode.get("spec"), LocalPromptTemplate.class);
  }

  /**
   * Create an {@link LocalPromptTemplate} from a YAML provided as String.
   *
   * @param inputYaml the provided YAML.
   * @return a template representing the provided YAML.
   * @throws IOException if the YAML cannot be parsed or deserialized.
   * @since 1.7.0
   */
  @Nullable
  public static LocalPromptTemplate fromYaml(@Nonnull final String inputYaml) throws IOException {
    final Object obj;
    try {
      final ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
      obj = yamlReader.readValue(inputYaml, Object.class);
    } catch (JsonProcessingException ex) {
      throw new IOException("Failed to parse the YAML input: " + ex.getMessage(), ex);
    }
    try {
      final ObjectMapper jsonWriter = new ObjectMapper();
      return fromJson(jsonWriter.writeValueAsString(obj));
    } catch (JsonProcessingException ex) {
      throw new IOException("Failed to deserialize the input: " + ex.getMessage(), ex);
    }
  }

  /**
   * Build a template reference with tenant level scope.
   *
   * @return an intermediate object to build the template reference.
   */
  @Nonnull
  public static ReferenceBuilder reference() {
    return scenario ->
        name ->
            version ->
                inputParameters ->
                    new PromptTemplateReference(
                        TemplateRefByScenarioNameVersion.create()
                            .scenario(scenario)
                            .name(name)
                            .version(version),
                        TENANT,
                        inputParameters);
  }

  /** Intermediate object to build a template reference. */
  @FunctionalInterface
  public interface ReferenceBuilder {
    /**
     * Build a template reference with the given id for tenant scope.
     *
     * @param id the id of the template.
     * @return a template reference with the given id.
     */
    @Nonnull
    default ReferenceBuilder3 byId(@Nonnull final String id) {
      return inputParameters ->
          new PromptTemplateReference(TemplateRefByID.create().id(id), TENANT, inputParameters);
    }

    /**
     * Build a template reference with the given scenario, name, and version.
     *
     * @param scenario the scenario of the template.
     * @return an intermediate object to build the template reference.
     */
    @Nonnull
    ReferenceBuilder1 byScenario(@Nonnull final String scenario);
  }

  /**
   * Intermediate object to build a template reference with the given scenario, name, and version.
   */
  @FunctionalInterface
  public interface ReferenceBuilder1 {

    /**
     * Build a template reference with the given scenario, name, and version.
     *
     * @param name the name of the template.
     * @return an intermediate object to build the template reference.
     */
    @Nonnull
    ReferenceBuilder2 name(@Nonnull final String name);
  }

  /**
   * Intermediate object to build a template reference with the given scenario, name, and version.
   */
  @FunctionalInterface
  public interface ReferenceBuilder2 {
    /**
     * Build a template reference with the given scenario, name, and version.
     *
     * @param version the version of the template.
     * @return a template reference with the given scenario, name, and version.
     */
    @Nonnull
    ReferenceBuilder3 version(@Nonnull final String version);
  }

  /**
   * Intermediate object to build a template reference with the given scenario, name, and version.
   */
  @FunctionalInterface
  public interface ReferenceBuilder3 {
    /**
     * Build a template reference with the given scenario, name, and version.
     *
     * @param inputParameters the placeholder values for the template.
     * @return a template reference with the given scenario, name, and version.
     */
    @Nonnull
    PromptTemplateReference andInputParameters(@Nonnull final Map<String, String> inputParameters);
  }
}
