

/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models. 
 *
 * The version of the OpenAPI document: 2.33.0
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
import com.sap.ai.sdk.core.client.model.RTAArtifactLabel;
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
 * Input or output artifact
 */
// CHECKSTYLE:OFF
public class RTAExecutableArtifact 
// CHECKSTYLE:ON
{
  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("kind")
  private String kind;

  @JsonProperty("labels")
  private List<RTAArtifactLabel> labels = new ArrayList<>();

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

   /**
    * Set the name of this {@link RTAExecutableArtifact} instance and return the same instance.
    *
    * @param name  Name of the signature argument
    * @return The same instance of this {@link RTAExecutableArtifact} class
    */
   @Nonnull public RTAExecutableArtifact name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

   /**
    * Name of the signature argument
    * @return name  The name of this {@link RTAExecutableArtifact} instance.
    */
  @Nonnull public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link RTAExecutableArtifact} instance.
   *
   * @param name  Name of the signature argument
   */
  public void setName( @Nonnull final String name) {
    this.name = name;
  }

   /**
    * Set the description of this {@link RTAExecutableArtifact} instance and return the same instance.
    *
    * @param description  Description of the signature argument
    * @return The same instance of this {@link RTAExecutableArtifact} class
    */
   @Nonnull public RTAExecutableArtifact description(@Nonnull final String description) {
    this.description = description;
    return this;
  }

   /**
    * Description of the signature argument
    * @return description  The description of this {@link RTAExecutableArtifact} instance.
    */
  @Nonnull public String getDescription() {
    return description;
  }

  /**
   * Set the description of this {@link RTAExecutableArtifact} instance.
   *
   * @param description  Description of the signature argument
   */
  public void setDescription( @Nonnull final String description) {
    this.description = description;
  }

   /**
    * Set the kind of this {@link RTAExecutableArtifact} instance and return the same instance.
    *
    * @param kind  Kind of the artifact, i.e. model or dataset
    * @return The same instance of this {@link RTAExecutableArtifact} class
    */
   @Nonnull public RTAExecutableArtifact kind(@Nonnull final String kind) {
    this.kind = kind;
    return this;
  }

   /**
    * Kind of the artifact, i.e. model or dataset
    * @return kind  The kind of this {@link RTAExecutableArtifact} instance.
    */
  @Nonnull public String getKind() {
    return kind;
  }

  /**
   * Set the kind of this {@link RTAExecutableArtifact} instance.
   *
   * @param kind  Kind of the artifact, i.e. model or dataset
   */
  public void setKind( @Nonnull final String kind) {
    this.kind = kind;
  }

   /**
    * Set the labels of this {@link RTAExecutableArtifact} instance and return the same instance.
    *
    * @param labels  Arbitrary labels as meta information
    * @return The same instance of this {@link RTAExecutableArtifact} class
    */
   @Nonnull public RTAExecutableArtifact labels(@Nonnull final List<RTAArtifactLabel> labels) {
    this.labels = labels;
    return this;
  }
  /**
   * Add one labels instance to this {@link RTAExecutableArtifact}.
   * @param labelsItem The labels that should be added
   * @return The same instance of type {@link RTAExecutableArtifact}
   */
  @Nonnull public RTAExecutableArtifact addLabelsItem( @Nonnull final RTAArtifactLabel labelsItem) {
    if (this.labels == null) {
      this.labels = new ArrayList<>();
    }
    this.labels.add(labelsItem);
    return this;
  }

   /**
    * Arbitrary labels as meta information
    * @return labels  The labels of this {@link RTAExecutableArtifact} instance.
    */
  @Nonnull public List<RTAArtifactLabel> getLabels() {
    return labels;
  }

  /**
   * Set the labels of this {@link RTAExecutableArtifact} instance.
   *
   * @param labels  Arbitrary labels as meta information
   */
  public void setLabels( @Nonnull final List<RTAArtifactLabel> labels) {
    this.labels = labels;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link RTAExecutableArtifact}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link RTAExecutableArtifact} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("RTAExecutableArtifact has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link RTAExecutableArtifact} instance. If the map previously contained a mapping
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
    final RTAExecutableArtifact rtAExecutableArtifact = (RTAExecutableArtifact) o;
    return Objects.equals(this.cloudSdkCustomFields, rtAExecutableArtifact.cloudSdkCustomFields) &&
        Objects.equals(this.name, rtAExecutableArtifact.name) &&
        Objects.equals(this.description, rtAExecutableArtifact.description) &&
        Objects.equals(this.kind, rtAExecutableArtifact.kind) &&
        Objects.equals(this.labels, rtAExecutableArtifact.labels);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, kind, labels, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class RTAExecutableArtifact {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    labels: ").append(toIndentedString(labels)).append("\n");
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

