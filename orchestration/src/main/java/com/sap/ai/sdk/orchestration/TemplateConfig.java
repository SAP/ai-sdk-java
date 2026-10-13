package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationTemplateReference.ScopeEnum.TENANT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
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
 * Template configuration for the {@link OrchestrationModuleConfig}.
 *
 * @since 1.4.0
 */
@EqualsAndHashCode
public abstract class TemplateConfig {

  /**
   * Create a low-level representation of the template.
   *
   * @return the low-level representation of the template.
   */
  @Nonnull
  protected abstract PromptTemplatingModuleConfigPrompt toLowLevel();

  public static OrchestrationPrompt prompt(@Nonnull final Message message) {
    return new OrchestrationPrompt(message);
  }

  public static OrchestrationTemplate template(String message) {
    return template(Message.user(message));
  }

  /**
   * Build a template from the given messages.
   *
   * @param messages the messages to use in the template.
   * @return a new template containing the given messages.
   */
  @Nonnull
  public static OrchestrationTemplate template(@Nonnull final Message... messages) {
    return new OrchestrationTemplate(
        Stream.of(messages).map(Message::createChatMessage).toList(),
        List.of(),
        new TreeMap<>(),
        null,
        null,
        null);
  }

  /**
   * Create an {@link OrchestrationTemplate} from a JSON provided as String.
   *
   * @param inputJson the provided JSON.
   * @return a template representing the provided JSON.
   * @throws IOException if the JSON cannot be deserialized.
   * @since 1.7.0
   */
  @Nullable
  public static OrchestrationTemplate fromJson(@Nonnull final String inputJson) throws IOException {
    final ObjectMapper objectMapper =
        OrchestrationJacksonConfiguration.getOrchestrationObjectMapper();
    final JsonNode rootNode = objectMapper.readTree(inputJson);
    return objectMapper.treeToValue(rootNode.get("spec"), OrchestrationTemplate.class);
  }

  /**
   * Create an {@link OrchestrationTemplate} from a YAML provided as String.
   *
   * @param inputYaml the provided YAML.
   * @return a template representing the provided YAML.
   * @throws IOException if the YAML cannot be parsed or deserialized.
   * @since 1.7.0
   */
  @Nullable
  public static OrchestrationTemplate fromYaml(@Nonnull final String inputYaml) throws IOException {
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
    final var templ = TemplateRefByScenarioNameVersion.create();

    return scenario ->
        name ->
            version ->
                new OrchestrationTemplateReference(
                    templ.scenario(scenario).name(name).version(version), TENANT, new TreeMap<>());
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
    default OrchestrationTemplateReference byId(@Nonnull final String id) {
      return new OrchestrationTemplateReference(TemplateRefByID.create().id(id), TENANT, new TreeMap<>());
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
    OrchestrationTemplateReference version(@Nonnull final String version);
  }
}
