

/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models. 
 *
 * The version of the OpenAPI document: 2.32.1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.core.client.model;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sap.ai.sdk.core.client.model.AiLabel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Base data of the artifact; this is the data that can be provided when the artifact is created; &#x60;name&#x60; and &#x60;kind&#x60; are required because they constitute important semantic filtering criteria for use in training / inference executables (&#x60;name&#x60; is a semantic handle of the artifact within a scenario and &#x60;kind&#x60; specifies the type of usage, e.g. you would only want to allow models in the model operator). 
 */

// CHECKSTYLE:OFF
public class AiArtifactPostData 
// CHECKSTYLE:ON
{
  @JsonProperty("labels")
  private List<AiLabel> labels = new ArrayList<>();

  @JsonProperty("name")
  private String name;

  /**
   * Kind of the artifact, i.e. model or dataset
   */
  public enum KindEnum {
    /**
    * The MODEL option of this AiArtifactPostData
    */
    MODEL("model"),
    
    /**
    * The DATASET option of this AiArtifactPostData
    */
    DATASET("dataset"),
    
    /**
    * The RESULTSET option of this AiArtifactPostData
    */
    RESULTSET("resultset"),
    
    /**
    * The OTHER option of this AiArtifactPostData
    */
    OTHER("other");

    private String value;

    KindEnum(String value) {
      this.value = value;
    }

    /**
    * Get the value of the enum
    * @return The enum value
    */
    @JsonValue
    @Nonnull public String getValue() {
      return value;
    }

    /**
    * Get the String value of the enum value.
    * @return The enum value as String
    */
    @Override
    @Nonnull public String toString() {
      return String.valueOf(value);
    }

    /**
    * Get the enum value from a String value
    * @param value The String value
    * @return The enum value of type AiArtifactPostData
    */
    @JsonCreator
    @Nonnull public static KindEnum fromValue(@Nonnull final String value) {
      for (KindEnum b : KindEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("kind")
  private KindEnum kind;

  @JsonProperty("url")
  private String url;

  @JsonProperty("description")
  private String description;

  @JsonProperty("scenarioId")
  private String scenarioId;

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

   /**
   * Set the labels of this {@link AiArtifactPostData} instance and return the same instance.
   *
   * @param labels  Arbitrary labels as meta information
   * @return The same instance of this {@link AiArtifactPostData} class
   */
   @Nonnull public AiArtifactPostData labels(@Nonnull final List<AiLabel> labels) {
    this.labels = labels;
    return this;
  }
  /**
  * Add one labels instance to this {@link AiArtifactPostData}.
  * @param labelsItem The labels that should be added
  * @return The same instance of type {@link AiArtifactPostData}
  */
  @Nonnull public AiArtifactPostData addlabelsItem( @Nonnull final AiLabel labelsItem) {
    if (this.labels == null) {
      this.labels = new ArrayList<>();
    }
    this.labels.add(labelsItem);
    return this;
  }

   /**
   * Arbitrary labels as meta information
   * @return labels  The labels of this {@link AiArtifactPostData} instance.
  **/
  @Nonnull public List<AiLabel> getLabels() {
    return labels;
  }

  /**
  * Set the labels of this {@link AiArtifactPostData} instance.
  *
  * @param labels  Arbitrary labels as meta information
  */
  public void setLabels( @Nonnull final List<AiLabel> labels) {
    this.labels = labels;
  }

   /**
   * Set the name of this {@link AiArtifactPostData} instance and return the same instance.
   *
   * @param name  Name of the artifact
   * @return The same instance of this {@link AiArtifactPostData} class
   */
   @Nonnull public AiArtifactPostData name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

   /**
   * Name of the artifact
   * @return name  The name of this {@link AiArtifactPostData} instance.
  **/
  @Nonnull public String getName() {
    return name;
  }

  /**
  * Set the name of this {@link AiArtifactPostData} instance.
  *
  * @param name  Name of the artifact
  */
  public void setName( @Nonnull final String name) {
    this.name = name;
  }

   /**
   * Set the kind of this {@link AiArtifactPostData} instance and return the same instance.
   *
   * @param kind  Kind of the artifact, i.e. model or dataset
   * @return The same instance of this {@link AiArtifactPostData} class
   */
   @Nonnull public AiArtifactPostData kind(@Nonnull final KindEnum kind) {
    this.kind = kind;
    return this;
  }

   /**
   * Kind of the artifact, i.e. model or dataset
   * @return kind  The kind of this {@link AiArtifactPostData} instance.
  **/
  @Nonnull public KindEnum getKind() {
    return kind;
  }

  /**
  * Set the kind of this {@link AiArtifactPostData} instance.
  *
  * @param kind  Kind of the artifact, i.e. model or dataset
  */
  public void setKind( @Nonnull final KindEnum kind) {
    this.kind = kind;
  }

   /**
   * Set the url of this {@link AiArtifactPostData} instance and return the same instance.
   *
   * @param url  Reference to the location of the artifact. 
   * @return The same instance of this {@link AiArtifactPostData} class
   */
   @Nonnull public AiArtifactPostData url(@Nonnull final String url) {
    this.url = url;
    return this;
  }

   /**
   * Reference to the location of the artifact. 
   * @return url  The url of this {@link AiArtifactPostData} instance.
  **/
  @Nonnull public String getUrl() {
    return url;
  }

  /**
  * Set the url of this {@link AiArtifactPostData} instance.
  *
  * @param url  Reference to the location of the artifact. 
  */
  public void setUrl( @Nonnull final String url) {
    this.url = url;
  }

   /**
   * Set the description of this {@link AiArtifactPostData} instance and return the same instance.
   *
   * @param description  Description of the artifact
   * @return The same instance of this {@link AiArtifactPostData} class
   */
   @Nonnull public AiArtifactPostData description(@Nonnull final String description) {
    this.description = description;
    return this;
  }

   /**
   * Description of the artifact
   * @return description  The description of this {@link AiArtifactPostData} instance.
  **/
  @Nonnull public String getDescription() {
    return description;
  }

  /**
  * Set the description of this {@link AiArtifactPostData} instance.
  *
  * @param description  Description of the artifact
  */
  public void setDescription( @Nonnull final String description) {
    this.description = description;
  }

   /**
   * Set the scenarioId of this {@link AiArtifactPostData} instance and return the same instance.
   *
   * @param scenarioId  ID of the scenario
   * @return The same instance of this {@link AiArtifactPostData} class
   */
   @Nonnull public AiArtifactPostData scenarioId(@Nonnull final String scenarioId) {
    this.scenarioId = scenarioId;
    return this;
  }

   /**
   * ID of the scenario
   * @return scenarioId  The scenarioId of this {@link AiArtifactPostData} instance.
  **/
  @Nonnull public String getScenarioId() {
    return scenarioId;
  }

  /**
  * Set the scenarioId of this {@link AiArtifactPostData} instance.
  *
  * @param scenarioId  ID of the scenario
  */
  public void setScenarioId( @Nonnull final String scenarioId) {
    this.scenarioId = scenarioId;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link AiArtifactPostData}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AiArtifactPostData} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("AiArtifactPostData has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link AiArtifactPostData} instance. If the map previously contained a mapping
   * for the key, the old value is replaced by the specified value.
   * @param customFieldName The name of the property
   * @param customFieldValue The value of the property
   */
  @JsonIgnore
  public void setCustomField( @Nonnull String customFieldName, @Nullable Object customFieldValue )
  {
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
    final AiArtifactPostData aiArtifactPostData = (AiArtifactPostData) o;
    return Objects.equals(this.cloudSdkCustomFields, aiArtifactPostData.cloudSdkCustomFields) &&
        Objects.equals(this.labels, aiArtifactPostData.labels) &&
        Objects.equals(this.name, aiArtifactPostData.name) &&
        Objects.equals(this.kind, aiArtifactPostData.kind) &&
        Objects.equals(this.url, aiArtifactPostData.url) &&
        Objects.equals(this.description, aiArtifactPostData.description) &&
        Objects.equals(this.scenarioId, aiArtifactPostData.scenarioId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(labels, name, kind, url, description, scenarioId, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class AiArtifactPostData {\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    scenarioId: ").append(toIndentedString(scenarioId)).append("\n");
    cloudSdkCustomFields.forEach((k,v) -> sb.append("    ").append(k).append(": ").append(toIndentedString(v)).append("\n"));
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(final java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

