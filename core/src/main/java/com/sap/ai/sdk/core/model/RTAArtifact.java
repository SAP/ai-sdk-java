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
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.annotations.Beta;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Entity having labels */
@Beta // CHECKSTYLE:OFF
public class RTAArtifact
// CHECKSTYLE:ON
{
  @JsonProperty("name")
  private String name;

  @JsonProperty("executionId")
  private String executionId;

  @JsonProperty("url")
  private String url;

  @JsonProperty("signature")
  private String signature;

  /** Kind of the artifact, i.e. model or dataset */
  public enum KindEnum {
    /** The MODEL option of this RTAArtifact */
    MODEL("model"),

    /** The DATASET option of this RTAArtifact */
    DATASET("dataset"),

    /** The RESULTSET option of this RTAArtifact */
    RESULTSET("resultset"),

    /** The OTHER option of this RTAArtifact */
    OTHER("other"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this RTAArtifact */
    UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

    private String value;

    KindEnum(String value) {
      this.value = value;
    }

    /**
     * Get the value of the enum
     *
     * @return The enum value
     */
    @JsonValue
    @Nonnull
    public String getValue() {
      return value;
    }

    /**
     * Get the String value of the enum value.
     *
     * @return The enum value as String
     */
    @Override
    @Nonnull
    public String toString() {
      return String.valueOf(value);
    }

    /**
     * Get the enum value from a String value
     *
     * @param value The String value
     * @return The enum value of type RTAArtifact
     */
    @JsonCreator
    @Nonnull
    public static KindEnum fromValue(@Nonnull final String value) {
      for (KindEnum b : KindEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return UNKNOWN_DEFAULT_OPEN_API;
    }
  }

  @JsonProperty("kind")
  private KindEnum kind;

  @JsonProperty("labels")
  private List<RTALabel> labels = new ArrayList<>();

  @JsonProperty("createdAt")
  private OffsetDateTime createdAt;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for RTAArtifact. */
  protected RTAArtifact() {}

  /**
   * Set the name of this {@link RTAArtifact} instance and return the same instance.
   *
   * @param name Name of the artifact; this is used for dependent pipelines to resolve an artifact
   * @return The same instance of this {@link RTAArtifact} class
   */
  @Nonnull
  public RTAArtifact name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the artifact; this is used for dependent pipelines to resolve an artifact
   *
   * @return name The name of this {@link RTAArtifact} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link RTAArtifact} instance.
   *
   * @param name Name of the artifact; this is used for dependent pipelines to resolve an artifact
   */
  public void setName(@Nonnull final String name) {
    this.name = name;
  }

  /**
   * Set the executionId of this {@link RTAArtifact} instance and return the same instance.
   *
   * @param executionId ID of the execution
   * @return The same instance of this {@link RTAArtifact} class
   */
  @Nonnull
  public RTAArtifact executionId(@Nonnull final String executionId) {
    this.executionId = executionId;
    return this;
  }

  /**
   * ID of the execution
   *
   * @return executionId The executionId of this {@link RTAArtifact} instance.
   */
  @Nonnull
  public String getExecutionId() {
    return executionId;
  }

  /**
   * Set the executionId of this {@link RTAArtifact} instance.
   *
   * @param executionId ID of the execution
   */
  public void setExecutionId(@Nonnull final String executionId) {
    this.executionId = executionId;
  }

  /**
   * Set the url of this {@link RTAArtifact} instance and return the same instance.
   *
   * @param url Reference to the location of the artifact. Note, the credentials will be found in a
   *     secret called &#39;some_bucket-object_store_secret&#39;. If not provided, a default will be
   *     assumed.
   * @return The same instance of this {@link RTAArtifact} class
   */
  @Nonnull
  public RTAArtifact url(@Nonnull final String url) {
    this.url = url;
    return this;
  }

  /**
   * Reference to the location of the artifact. Note, the credentials will be found in a secret
   * called &#39;some_bucket-object_store_secret&#39;. If not provided, a default will be assumed.
   *
   * @return url The url of this {@link RTAArtifact} instance.
   */
  @Nonnull
  public String getUrl() {
    return url;
  }

  /**
   * Set the url of this {@link RTAArtifact} instance.
   *
   * @param url Reference to the location of the artifact. Note, the credentials will be found in a
   *     secret called &#39;some_bucket-object_store_secret&#39;. If not provided, a default will be
   *     assumed.
   */
  public void setUrl(@Nonnull final String url) {
    this.url = url;
  }

  /**
   * Set the signature of this {@link RTAArtifact} instance and return the same instance.
   *
   * @param signature The signature of this {@link RTAArtifact}
   * @return The same instance of this {@link RTAArtifact} class
   */
  @Nonnull
  public RTAArtifact signature(@Nullable final String signature) {
    this.signature = signature;
    return this;
  }

  /**
   * Get signature
   *
   * @return signature The signature of this {@link RTAArtifact} instance.
   */
  @Nonnull
  public String getSignature() {
    return signature;
  }

  /**
   * Set the signature of this {@link RTAArtifact} instance.
   *
   * @param signature The signature of this {@link RTAArtifact}
   */
  public void setSignature(@Nullable final String signature) {
    this.signature = signature;
  }

  /**
   * Set the kind of this {@link RTAArtifact} instance and return the same instance.
   *
   * @param kind Kind of the artifact, i.e. model or dataset
   * @return The same instance of this {@link RTAArtifact} class
   */
  @Nonnull
  public RTAArtifact kind(@Nonnull final KindEnum kind) {
    this.kind = kind;
    return this;
  }

  /**
   * Kind of the artifact, i.e. model or dataset
   *
   * @return kind The kind of this {@link RTAArtifact} instance.
   */
  @Nonnull
  public KindEnum getKind() {
    return kind;
  }

  /**
   * Set the kind of this {@link RTAArtifact} instance.
   *
   * @param kind Kind of the artifact, i.e. model or dataset
   */
  public void setKind(@Nonnull final KindEnum kind) {
    this.kind = kind;
  }

  /**
   * Set the labels of this {@link RTAArtifact} instance and return the same instance.
   *
   * @param labels Arbitrary labels as meta information
   * @return The same instance of this {@link RTAArtifact} class
   */
  @Nonnull
  public RTAArtifact labels(@Nullable final List<RTALabel> labels) {
    this.labels = labels;
    return this;
  }

  /**
   * Add one labels instance to this {@link RTAArtifact}.
   *
   * @param labelsItem The labels that should be added
   * @return The same instance of type {@link RTAArtifact}
   */
  @Nonnull
  public RTAArtifact addLabelsItem(@Nonnull final RTALabel labelsItem) {
    if (this.labels == null) {
      this.labels = new ArrayList<>();
    }
    this.labels.add(labelsItem);
    return this;
  }

  /**
   * Arbitrary labels as meta information
   *
   * @return labels The labels of this {@link RTAArtifact} instance.
   */
  @Nonnull
  public List<RTALabel> getLabels() {
    return labels;
  }

  /**
   * Set the labels of this {@link RTAArtifact} instance.
   *
   * @param labels Arbitrary labels as meta information
   */
  public void setLabels(@Nullable final List<RTALabel> labels) {
    this.labels = labels;
  }

  /**
   * Set the createdAt of this {@link RTAArtifact} instance and return the same instance.
   *
   * @param createdAt Timestamp of resource creation
   * @return The same instance of this {@link RTAArtifact} class
   */
  @Nonnull
  public RTAArtifact createdAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Timestamp of resource creation
   *
   * @return createdAt The createdAt of this {@link RTAArtifact} instance.
   */
  @Nonnull
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Set the createdAt of this {@link RTAArtifact} instance.
   *
   * @param createdAt Timestamp of resource creation
   */
  public void setCreatedAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link RTAArtifact}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link RTAArtifact} instance.
   *
   * @deprecated Use {@link #toMap()} instead.
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  @Deprecated
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("RTAArtifact has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link RTAArtifact} instance including unrecognized
   * properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (name != null) declaredFields.put("name", name);
    if (executionId != null) declaredFields.put("executionId", executionId);
    if (url != null) declaredFields.put("url", url);
    if (signature != null) declaredFields.put("signature", signature);
    if (kind != null) declaredFields.put("kind", kind);
    if (labels != null) declaredFields.put("labels", labels);
    if (createdAt != null) declaredFields.put("createdAt", createdAt);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link RTAArtifact} instance. If the map previously
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
    final RTAArtifact rtAArtifact = (RTAArtifact) o;
    return Objects.equals(this.cloudSdkCustomFields, rtAArtifact.cloudSdkCustomFields)
        && Objects.equals(this.name, rtAArtifact.name)
        && Objects.equals(this.executionId, rtAArtifact.executionId)
        && Objects.equals(this.url, rtAArtifact.url)
        && Objects.equals(this.signature, rtAArtifact.signature)
        && Objects.equals(this.kind, rtAArtifact.kind)
        && Objects.equals(this.labels, rtAArtifact.labels)
        && Objects.equals(this.createdAt, rtAArtifact.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        name, executionId, url, signature, kind, labels, createdAt, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class RTAArtifact {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    executionId: ").append(toIndentedString(executionId)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link RTAArtifact} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (name) ->
        (executionId) ->
            (url) ->
                (kind) ->
                    (createdAt) ->
                        new RTAArtifact()
                            .name(name)
                            .executionId(executionId)
                            .url(url)
                            .kind(kind)
                            .createdAt(createdAt);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the name of this {@link RTAArtifact} instance.
     *
     * @param name Name of the artifact; this is used for dependent pipelines to resolve an artifact
     * @return The RTAArtifact builder.
     */
    Builder1 name(@Nonnull final String name);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the executionId of this {@link RTAArtifact} instance.
     *
     * @param executionId ID of the execution
     * @return The RTAArtifact builder.
     */
    Builder2 executionId(@Nonnull final String executionId);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the url of this {@link RTAArtifact} instance.
     *
     * @param url Reference to the location of the artifact. Note, the credentials will be found in
     *     a secret called &#39;some_bucket-object_store_secret&#39;. If not provided, a default
     *     will be assumed.
     * @return The RTAArtifact builder.
     */
    Builder3 url(@Nonnull final String url);
  }

  /** Builder helper class. */
  public interface Builder3 {
    /**
     * Set the kind of this {@link RTAArtifact} instance.
     *
     * @param kind Kind of the artifact, i.e. model or dataset
     * @return The RTAArtifact builder.
     */
    Builder4 kind(@Nonnull final KindEnum kind);
  }

  /** Builder helper class. */
  public interface Builder4 {
    /**
     * Set the createdAt of this {@link RTAArtifact} instance.
     *
     * @param createdAt Timestamp of resource creation
     * @return The RTAArtifact instance.
     */
    RTAArtifact createdAt(@Nonnull final OffsetDateTime createdAt);
  }
}
