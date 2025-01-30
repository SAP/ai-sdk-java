/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.core.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.Beta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** AiModelBaseData */
@Beta // CHECKSTYLE:OFF
public class AiModelBaseData
// CHECKSTYLE:ON
{
  @JsonProperty("model")
  private String model;

  @JsonProperty("executableId")
  private String executableId;

  @JsonProperty("description")
  private String description;

  @JsonProperty("versions")
  private List<AiModelVersion> versions = new ArrayList<>();

  @JsonProperty("displayName")
  private String displayName;

  @JsonProperty("accessType")
  private String accessType;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for AiModelBaseData. */
  protected AiModelBaseData() {}

  /**
   * Set the model of this {@link AiModelBaseData} instance and return the same instance.
   *
   * @param model Name of the model
   * @return The same instance of this {@link AiModelBaseData} class
   */
  @Nonnull
  public AiModelBaseData model(@Nonnull final String model) {
    this.model = model;
    return this;
  }

  /**
   * Name of the model
   *
   * @return model The model of this {@link AiModelBaseData} instance.
   */
  @Nonnull
  public String getModel() {
    return model;
  }

  /**
   * Set the model of this {@link AiModelBaseData} instance.
   *
   * @param model Name of the model
   */
  public void setModel(@Nonnull final String model) {
    this.model = model;
  }

  /**
   * Set the executableId of this {@link AiModelBaseData} instance and return the same instance.
   *
   * @param executableId ID of the executable
   * @return The same instance of this {@link AiModelBaseData} class
   */
  @Nonnull
  public AiModelBaseData executableId(@Nonnull final String executableId) {
    this.executableId = executableId;
    return this;
  }

  /**
   * ID of the executable
   *
   * @return executableId The executableId of this {@link AiModelBaseData} instance.
   */
  @Nonnull
  public String getExecutableId() {
    return executableId;
  }

  /**
   * Set the executableId of this {@link AiModelBaseData} instance.
   *
   * @param executableId ID of the executable
   */
  public void setExecutableId(@Nonnull final String executableId) {
    this.executableId = executableId;
  }

  /**
   * Set the description of this {@link AiModelBaseData} instance and return the same instance.
   *
   * @param description Description of the model and its capabilities
   * @return The same instance of this {@link AiModelBaseData} class
   */
  @Nonnull
  public AiModelBaseData description(@Nonnull final String description) {
    this.description = description;
    return this;
  }

  /**
   * Description of the model and its capabilities
   *
   * @return description The description of this {@link AiModelBaseData} instance.
   */
  @Nonnull
  public String getDescription() {
    return description;
  }

  /**
   * Set the description of this {@link AiModelBaseData} instance.
   *
   * @param description Description of the model and its capabilities
   */
  public void setDescription(@Nonnull final String description) {
    this.description = description;
  }

  /**
   * Set the versions of this {@link AiModelBaseData} instance and return the same instance.
   *
   * @param versions List of model versions that the model object has
   * @return The same instance of this {@link AiModelBaseData} class
   */
  @Nonnull
  public AiModelBaseData versions(@Nonnull final List<AiModelVersion> versions) {
    this.versions = versions;
    return this;
  }

  /**
   * Add one versions instance to this {@link AiModelBaseData}.
   *
   * @param versionsItem The versions that should be added
   * @return The same instance of type {@link AiModelBaseData}
   */
  @Nonnull
  public AiModelBaseData addVersionsItem(@Nonnull final AiModelVersion versionsItem) {
    if (this.versions == null) {
      this.versions = new ArrayList<>();
    }
    this.versions.add(versionsItem);
    return this;
  }

  /**
   * List of model versions that the model object has
   *
   * @return versions The versions of this {@link AiModelBaseData} instance.
   */
  @Nonnull
  public List<AiModelVersion> getVersions() {
    return versions;
  }

  /**
   * Set the versions of this {@link AiModelBaseData} instance.
   *
   * @param versions List of model versions that the model object has
   */
  public void setVersions(@Nonnull final List<AiModelVersion> versions) {
    this.versions = versions;
  }

  /**
   * Set the displayName of this {@link AiModelBaseData} instance and return the same instance.
   *
   * @param displayName Display name of the model
   * @return The same instance of this {@link AiModelBaseData} class
   */
  @Nonnull
  public AiModelBaseData displayName(@Nullable final String displayName) {
    this.displayName = displayName;
    return this;
  }

  /**
   * Display name of the model
   *
   * @return displayName The displayName of this {@link AiModelBaseData} instance.
   */
  @Nonnull
  public String getDisplayName() {
    return displayName;
  }

  /**
   * Set the displayName of this {@link AiModelBaseData} instance.
   *
   * @param displayName Display name of the model
   */
  public void setDisplayName(@Nullable final String displayName) {
    this.displayName = displayName;
  }

  /**
   * Set the accessType of this {@link AiModelBaseData} instance and return the same instance.
   *
   * @param accessType Access type of the model
   * @return The same instance of this {@link AiModelBaseData} class
   */
  @Nonnull
  public AiModelBaseData accessType(@Nullable final String accessType) {
    this.accessType = accessType;
    return this;
  }

  /**
   * Access type of the model
   *
   * @return accessType The accessType of this {@link AiModelBaseData} instance.
   */
  @Nonnull
  public String getAccessType() {
    return accessType;
  }

  /**
   * Set the accessType of this {@link AiModelBaseData} instance.
   *
   * @param accessType Access type of the model
   */
  public void setAccessType(@Nullable final String accessType) {
    this.accessType = accessType;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link AiModelBaseData}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AiModelBaseData} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("AiModelBaseData has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link AiModelBaseData} instance. If the map previously
   * contained a mapping for the key, the old value is replaced by the specified value.
   *
   * @param customFieldName The name of the property
   * @param customFieldValue The value of the property
   */
  @JsonIgnore
  public void setCustomField(@Nonnull String customFieldName, @Nullable Object customFieldValue) {
    cloudSdkCustomFields.put(customFieldName, customFieldValue);
  }

  @Override
  public boolean equals(@Nullable final java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final AiModelBaseData aiModelBaseData = (AiModelBaseData) o;
    return Objects.equals(this.cloudSdkCustomFields, aiModelBaseData.cloudSdkCustomFields)
        && Objects.equals(this.model, aiModelBaseData.model)
        && Objects.equals(this.executableId, aiModelBaseData.executableId)
        && Objects.equals(this.description, aiModelBaseData.description)
        && Objects.equals(this.versions, aiModelBaseData.versions)
        && Objects.equals(this.displayName, aiModelBaseData.displayName)
        && Objects.equals(this.accessType, aiModelBaseData.accessType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        model, executableId, description, versions, displayName, accessType, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class AiModelBaseData {\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    executableId: ").append(toIndentedString(executableId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    versions: ").append(toIndentedString(versions)).append("\n");
    sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    accessType: ").append(toIndentedString(accessType)).append("\n");
    cloudSdkCustomFields.forEach(
        (k, v) ->
            sb.append("    ").append(k).append(": ").append(toIndentedString(v)).append("\n"));
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(final java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  /**
   * Create a type-safe, fluent-api builder object to construct a new {@link AiModelBaseData}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (model) ->
        (executableId) ->
            (description) ->
                (versions) ->
                    new AiModelBaseData()
                        .model(model)
                        .executableId(executableId)
                        .description(description)
                        .versions(versions);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the model of this {@link AiModelBaseData} instance.
     *
     * @param model Name of the model
     * @return The AiModelBaseData builder.
     */
    Builder1 model(@Nonnull final String model);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the executableId of this {@link AiModelBaseData} instance.
     *
     * @param executableId ID of the executable
     * @return The AiModelBaseData builder.
     */
    Builder2 executableId(@Nonnull final String executableId);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the description of this {@link AiModelBaseData} instance.
     *
     * @param description Description of the model and its capabilities
     * @return The AiModelBaseData builder.
     */
    Builder3 description(@Nonnull final String description);
  }

  /** Builder helper class. */
  public interface Builder3 {
    /**
     * Set the versions of this {@link AiModelBaseData} instance.
     *
     * @param versions List of model versions that the model object has
     * @return The AiModelBaseData instance.
     */
    AiModelBaseData versions(@Nonnull final List<AiModelVersion> versions);

    /**
     * Set the versions of this {@link AiModelBaseData} instance.
     *
     * @param versions List of model versions that the model object has
     * @return The AiModelBaseData instance.
     */
    default AiModelBaseData versions(@Nonnull final AiModelVersion... versions) {
      return versions(Arrays.asList(versions));
    }
  }
}
