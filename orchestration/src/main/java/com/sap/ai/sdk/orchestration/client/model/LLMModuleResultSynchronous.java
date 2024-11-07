/*
 * Orchestration
 * Orchestration is an inference service which provides common additional capabilities for business AI scenarios, such as content filtering and data masking. At the core of the service is the LLM module which allows for an easy, harmonized access to the language models of gen AI hub. The service is designed to be modular and extensible, allowing for the addition of new modules in the future. Each module can be configured independently and at runtime, allowing for a high degree of flexibility in the orchestration of AI services.
 *
 * The version of the OpenAPI document: 0.29.3
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.orchestration.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Output of LLM module. Follows the OpenAI spec. */
@JsonPropertyOrder({
  LLMModuleResultSynchronous.JSON_PROPERTY_ID,
  LLMModuleResultSynchronous.JSON_PROPERTY_OBJECT,
  LLMModuleResultSynchronous.JSON_PROPERTY_CREATED,
  LLMModuleResultSynchronous.JSON_PROPERTY_MODEL,
  LLMModuleResultSynchronous.JSON_PROPERTY_SYSTEM_FINGERPRINT,
  LLMModuleResultSynchronous.JSON_PROPERTY_CHOICES,
  LLMModuleResultSynchronous.JSON_PROPERTY_USAGE
})
@javax.annotation.Generated(
    value = "org.openapitools.codegen.languages.JavaClientCodegen",
    comments = "Generator version: 7.9.0")
public class LLMModuleResultSynchronous implements LLMModuleResult {
  public static final String JSON_PROPERTY_ID = "id";
  private String id;

  public static final String JSON_PROPERTY_OBJECT = "object";
  private String _object;

  public static final String JSON_PROPERTY_CREATED = "created";
  private Integer created;

  public static final String JSON_PROPERTY_MODEL = "model";
  private String model;

  public static final String JSON_PROPERTY_SYSTEM_FINGERPRINT = "system_fingerprint";
  private String systemFingerprint;

  public static final String JSON_PROPERTY_CHOICES = "choices";
  private List<LLMChoice> choices = new ArrayList<>();

  public static final String JSON_PROPERTY_USAGE = "usage";
  private TokenUsage usage;

  public LLMModuleResultSynchronous() {}

  public LLMModuleResultSynchronous id(String id) {

    this.id = id;
    return this;
  }

  /**
   * ID of the response
   *
   * @return id
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getId() {
    return id;
  }

  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setId(String id) {
    this.id = id;
  }

  public LLMModuleResultSynchronous _object(String _object) {

    this._object = _object;
    return this;
  }

  /**
   * Object type
   *
   * @return _object
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_OBJECT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getObject() {
    return _object;
  }

  @JsonProperty(JSON_PROPERTY_OBJECT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setObject(String _object) {
    this._object = _object;
  }

  public LLMModuleResultSynchronous created(Integer created) {

    this.created = created;
    return this;
  }

  /**
   * Unix timestamp
   *
   * @return created
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_CREATED)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public Integer getCreated() {
    return created;
  }

  @JsonProperty(JSON_PROPERTY_CREATED)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setCreated(Integer created) {
    this.created = created;
  }

  public LLMModuleResultSynchronous model(String model) {

    this.model = model;
    return this;
  }

  /**
   * Model name
   *
   * @return model
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MODEL)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public String getModel() {
    return model;
  }

  @JsonProperty(JSON_PROPERTY_MODEL)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setModel(String model) {
    this.model = model;
  }

  public LLMModuleResultSynchronous systemFingerprint(String systemFingerprint) {

    this.systemFingerprint = systemFingerprint;
    return this;
  }

  /**
   * System fingerprint
   *
   * @return systemFingerprint
   */
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SYSTEM_FINGERPRINT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public String getSystemFingerprint() {
    return systemFingerprint;
  }

  @JsonProperty(JSON_PROPERTY_SYSTEM_FINGERPRINT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSystemFingerprint(String systemFingerprint) {
    this.systemFingerprint = systemFingerprint;
  }

  public LLMModuleResultSynchronous choices(List<LLMChoice> choices) {

    this.choices = choices;
    return this;
  }

  public LLMModuleResultSynchronous addChoicesItem(LLMChoice choicesItem) {
    if (this.choices == null) {
      this.choices = new ArrayList<>();
    }
    this.choices.add(choicesItem);
    return this;
  }

  /**
   * Choices
   *
   * @return choices
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_CHOICES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public List<LLMChoice> getChoices() {
    return choices;
  }

  @JsonProperty(JSON_PROPERTY_CHOICES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setChoices(List<LLMChoice> choices) {
    this.choices = choices;
  }

  public LLMModuleResultSynchronous usage(TokenUsage usage) {

    this.usage = usage;
    return this;
  }

  /**
   * Get usage
   *
   * @return usage
   */
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_USAGE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public TokenUsage getUsage() {
    return usage;
  }

  @JsonProperty(JSON_PROPERTY_USAGE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setUsage(TokenUsage usage) {
    this.usage = usage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LLMModuleResultSynchronous llMModuleResultSynchronous = (LLMModuleResultSynchronous) o;
    return Objects.equals(this.id, llMModuleResultSynchronous.id)
        && Objects.equals(this._object, llMModuleResultSynchronous._object)
        && Objects.equals(this.created, llMModuleResultSynchronous.created)
        && Objects.equals(this.model, llMModuleResultSynchronous.model)
        && Objects.equals(this.systemFingerprint, llMModuleResultSynchronous.systemFingerprint)
        && Objects.equals(this.choices, llMModuleResultSynchronous.choices)
        && Objects.equals(this.usage, llMModuleResultSynchronous.usage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, _object, created, model, systemFingerprint, choices, usage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LLMModuleResultSynchronous {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    _object: ").append(toIndentedString(_object)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    systemFingerprint: ").append(toIndentedString(systemFingerprint)).append("\n");
    sb.append("    choices: ").append(toIndentedString(choices)).append("\n");
    sb.append("    usage: ").append(toIndentedString(usage)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  public static class Builder {

    private LLMModuleResultSynchronous instance;

    public Builder() {
      this(new LLMModuleResultSynchronous());
    }

    protected Builder(LLMModuleResultSynchronous instance) {
      this.instance = instance;
    }

    public LLMModuleResultSynchronous.Builder id(String id) {
      this.instance.id = id;
      return this;
    }

    public LLMModuleResultSynchronous.Builder _object(String _object) {
      this.instance._object = _object;
      return this;
    }

    public LLMModuleResultSynchronous.Builder created(Integer created) {
      this.instance.created = created;
      return this;
    }

    public LLMModuleResultSynchronous.Builder model(String model) {
      this.instance.model = model;
      return this;
    }

    public LLMModuleResultSynchronous.Builder systemFingerprint(String systemFingerprint) {
      this.instance.systemFingerprint = systemFingerprint;
      return this;
    }

    public LLMModuleResultSynchronous.Builder choices(List<LLMChoice> choices) {
      this.instance.choices = choices;
      return this;
    }

    public LLMModuleResultSynchronous.Builder usage(TokenUsage usage) {
      this.instance.usage = usage;
      return this;
    }

    /**
     * returns a built LLMModuleResultSynchronous instance.
     *
     * <p>The builder is not reusable.
     */
    public LLMModuleResultSynchronous build() {
      try {
        return this.instance;
      } finally {
        // ensure that this.instance is not reused
        this.instance = null;
      }
    }

    @Override
    public String toString() {
      return getClass() + "=(" + instance + ")";
    }
  }

  /** Create a builder with no initialized field. */
  public static LLMModuleResultSynchronous.Builder builder() {
    return new LLMModuleResultSynchronous.Builder();
  }

  /** Create a builder with a shallow copy of this instance. */
  public LLMModuleResultSynchronous.Builder toBuilder() {
    return new LLMModuleResultSynchronous.Builder()
        .id(getId())
        ._object(getObject())
        .created(getCreated())
        .model(getModel())
        .systemFingerprint(getSystemFingerprint())
        .choices(getChoices())
        .usage(getUsage());
  }
}
