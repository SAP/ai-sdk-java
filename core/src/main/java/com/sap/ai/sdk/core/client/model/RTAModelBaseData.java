/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
 * The version of the OpenAPI document: 2.35.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.core.client.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

/** RTAModelBaseData */
// CHECKSTYLE:OFF
public class RTAModelBaseData
// CHECKSTYLE:ON
{
  @JsonProperty("model")
  private String model;

  @JsonProperty("executableId")
  private String executableId;

  @JsonProperty("description")
  private String description;

  @JsonProperty("versions")
  private List<RTAModelVersion> versions = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for RTAModelBaseData. */
  protected RTAModelBaseData() {}

  /**
   * Set the model of this {@link RTAModelBaseData} instance and return the same instance.
   *
   * @param model Name of the model
   * @return The same instance of this {@link RTAModelBaseData} class
   */
  @Nonnull
  public RTAModelBaseData model(@Nonnull final String model) {
    this.model = model;
    return this;
  }

  /**
   * Name of the model
   *
   * @return model The model of this {@link RTAModelBaseData} instance.
   */
  @Nonnull
  public String getModel() {
    return model;
  }

  /**
   * Set the model of this {@link RTAModelBaseData} instance.
   *
   * @param model Name of the model
   */
  public void setModel(@Nonnull final String model) {
    this.model = model;
  }

  /**
   * Set the executableId of this {@link RTAModelBaseData} instance and return the same instance.
   *
   * @param executableId ID of the executable
   * @return The same instance of this {@link RTAModelBaseData} class
   */
  @Nonnull
  public RTAModelBaseData executableId(@Nonnull final String executableId) {
    this.executableId = executableId;
    return this;
  }

  /**
   * ID of the executable
   *
   * @return executableId The executableId of this {@link RTAModelBaseData} instance.
   */
  @Nonnull
  public String getExecutableId() {
    return executableId;
  }

  /**
   * Set the executableId of this {@link RTAModelBaseData} instance.
   *
   * @param executableId ID of the executable
   */
  public void setExecutableId(@Nonnull final String executableId) {
    this.executableId = executableId;
  }

  /**
   * Set the description of this {@link RTAModelBaseData} instance and return the same instance.
   *
   * @param description Description of the model and its capabilities
   * @return The same instance of this {@link RTAModelBaseData} class
   */
  @Nonnull
  public RTAModelBaseData description(@Nonnull final String description) {
    this.description = description;
    return this;
  }

  /**
   * Description of the model and its capabilities
   *
   * @return description The description of this {@link RTAModelBaseData} instance.
   */
  @Nonnull
  public String getDescription() {
    return description;
  }

  /**
   * Set the description of this {@link RTAModelBaseData} instance.
   *
   * @param description Description of the model and its capabilities
   */
  public void setDescription(@Nonnull final String description) {
    this.description = description;
  }

  /**
   * Set the versions of this {@link RTAModelBaseData} instance and return the same instance.
   *
   * @param versions List of model versions that the model object has
   * @return The same instance of this {@link RTAModelBaseData} class
   */
  @Nonnull
  public RTAModelBaseData versions(@Nonnull final List<RTAModelVersion> versions) {
    this.versions = versions;
    return this;
  }

  /**
   * Add one versions instance to this {@link RTAModelBaseData}.
   *
   * @param versionsItem The versions that should be added
   * @return The same instance of type {@link RTAModelBaseData}
   */
  @Nonnull
  public RTAModelBaseData addVersionsItem(@Nonnull final RTAModelVersion versionsItem) {
    if (this.versions == null) {
      this.versions = new ArrayList<>();
    }
    this.versions.add(versionsItem);
    return this;
  }

  /**
   * List of model versions that the model object has
   *
   * @return versions The versions of this {@link RTAModelBaseData} instance.
   */
  @Nonnull
  public List<RTAModelVersion> getVersions() {
    return versions;
  }

  /**
   * Set the versions of this {@link RTAModelBaseData} instance.
   *
   * @param versions List of model versions that the model object has
   */
  public void setVersions(@Nonnull final List<RTAModelVersion> versions) {
    this.versions = versions;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link RTAModelBaseData}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link RTAModelBaseData} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("RTAModelBaseData has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link RTAModelBaseData} instance. If the map previously
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
    final RTAModelBaseData rtAModelBaseData = (RTAModelBaseData) o;
    return Objects.equals(this.cloudSdkCustomFields, rtAModelBaseData.cloudSdkCustomFields)
        && Objects.equals(this.model, rtAModelBaseData.model)
        && Objects.equals(this.executableId, rtAModelBaseData.executableId)
        && Objects.equals(this.description, rtAModelBaseData.description)
        && Objects.equals(this.versions, rtAModelBaseData.versions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(model, executableId, description, versions, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class RTAModelBaseData {\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    executableId: ").append(toIndentedString(executableId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    versions: ").append(toIndentedString(versions)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link RTAModelBaseData}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (model) ->
        (executableId) ->
            (description) ->
                (versions) ->
                    new RTAModelBaseData()
                        .model(model)
                        .executableId(executableId)
                        .description(description)
                        .versions(versions);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the model of this {@link RTAModelBaseData} instance.
     *
     * @param model Name of the model
     * @return The RTAModelBaseData builder.
     */
    Builder1 model(@Nonnull final String model);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the executableId of this {@link RTAModelBaseData} instance.
     *
     * @param executableId ID of the executable
     * @return The RTAModelBaseData builder.
     */
    Builder2 executableId(@Nonnull final String executableId);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the description of this {@link RTAModelBaseData} instance.
     *
     * @param description Description of the model and its capabilities
     * @return The RTAModelBaseData builder.
     */
    Builder3 description(@Nonnull final String description);
  }

  /** Builder helper class. */
  public interface Builder3 {
    /**
     * Set the versions of this {@link RTAModelBaseData} instance.
     *
     * @param versions List of model versions that the model object has
     * @return The RTAModelBaseData instance.
     */
    RTAModelBaseData versions(@Nonnull final List<RTAModelVersion> versions);

    /**
     * Set the versions of this {@link RTAModelBaseData} instance.
     *
     * @param versions List of model versions that the model object has
     * @return The RTAModelBaseData instance.
     */
    default RTAModelBaseData versions(@Nonnull final RTAModelVersion... versions) {
      return versions(Arrays.asList(versions));
    }
  }
}
