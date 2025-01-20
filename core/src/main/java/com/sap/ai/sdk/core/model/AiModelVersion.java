/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
 * The version of the OpenAPI document: 2.38.0
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Model version information including whether it is latest version, its deprecation status and
 * optional retirement date
 */
@Beta // CHECKSTYLE:OFF
public class AiModelVersion
// CHECKSTYLE:ON
{
  @JsonProperty("name")
  private String name;

  @JsonProperty("isLatest")
  private Boolean isLatest;

  @JsonProperty("deprecated")
  private Boolean deprecated;

  @JsonProperty("retirementDate")
  private String retirementDate;

  @JsonProperty("contextLength")
  private Integer contextLength;

  @JsonProperty("inputTypes")
  private List<String> inputTypes = new ArrayList<>();

  @JsonProperty("capabilities")
  private List<String> capabilities = new ArrayList<>();

  @JsonProperty("metadata")
  private List<Map<String, String>> metadata = new ArrayList<>();

  @JsonProperty("cost")
  private List<Map<String, String>> cost = new ArrayList<>();

  @JsonProperty("suggestedReplacements")
  private List<String> suggestedReplacements = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for AiModelVersion. */
  protected AiModelVersion() {}

  /**
   * Set the name of this {@link AiModelVersion} instance and return the same instance.
   *
   * @param name Name of model version
   * @return The same instance of this {@link AiModelVersion} class
   */
  @Nonnull
  public AiModelVersion name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of model version
   *
   * @return name The name of this {@link AiModelVersion} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link AiModelVersion} instance.
   *
   * @param name Name of model version
   */
  public void setName(@Nonnull final String name) {
    this.name = name;
  }

  /**
   * Set the isLatest of this {@link AiModelVersion} instance and return the same instance.
   *
   * @param isLatest Displays whether it is the latest version offered for the model
   * @return The same instance of this {@link AiModelVersion} class
   */
  @Nonnull
  public AiModelVersion isLatest(@Nonnull final Boolean isLatest) {
    this.isLatest = isLatest;
    return this;
  }

  /**
   * Displays whether it is the latest version offered for the model
   *
   * @return isLatest The isLatest of this {@link AiModelVersion} instance.
   */
  @Nonnull
  public Boolean isIsLatest() {
    return isLatest;
  }

  /**
   * Set the isLatest of this {@link AiModelVersion} instance.
   *
   * @param isLatest Displays whether it is the latest version offered for the model
   */
  public void setIsLatest(@Nonnull final Boolean isLatest) {
    this.isLatest = isLatest;
  }

  /**
   * Set the deprecated of this {@link AiModelVersion} instance and return the same instance.
   *
   * @param deprecated Deprecation status of model
   * @return The same instance of this {@link AiModelVersion} class
   */
  @Nonnull
  public AiModelVersion deprecated(@Nonnull final Boolean deprecated) {
    this.deprecated = deprecated;
    return this;
  }

  /**
   * Deprecation status of model
   *
   * @return deprecated The deprecated of this {@link AiModelVersion} instance.
   */
  @Nonnull
  public Boolean isDeprecated() {
    return deprecated;
  }

  /**
   * Set the deprecated of this {@link AiModelVersion} instance.
   *
   * @param deprecated Deprecation status of model
   */
  public void setDeprecated(@Nonnull final Boolean deprecated) {
    this.deprecated = deprecated;
  }

  /**
   * Set the retirementDate of this {@link AiModelVersion} instance and return the same instance.
   *
   * @param retirementDate Retirement date of model in ISO 8601 timestamp
   * @return The same instance of this {@link AiModelVersion} class
   */
  @Nonnull
  public AiModelVersion retirementDate(@Nullable final String retirementDate) {
    this.retirementDate = retirementDate;
    return this;
  }

  /**
   * Retirement date of model in ISO 8601 timestamp
   *
   * @return retirementDate The retirementDate of this {@link AiModelVersion} instance.
   */
  @Nonnull
  public String getRetirementDate() {
    return retirementDate;
  }

  /**
   * Set the retirementDate of this {@link AiModelVersion} instance.
   *
   * @param retirementDate Retirement date of model in ISO 8601 timestamp
   */
  public void setRetirementDate(@Nullable final String retirementDate) {
    this.retirementDate = retirementDate;
  }

  /**
   * Set the contextLength of this {@link AiModelVersion} instance and return the same instance.
   *
   * @param contextLength Context length of the model
   * @return The same instance of this {@link AiModelVersion} class
   */
  @Nonnull
  public AiModelVersion contextLength(@Nullable final Integer contextLength) {
    this.contextLength = contextLength;
    return this;
  }

  /**
   * Context length of the model
   *
   * @return contextLength The contextLength of this {@link AiModelVersion} instance.
   */
  @Nonnull
  public Integer getContextLength() {
    return contextLength;
  }

  /**
   * Set the contextLength of this {@link AiModelVersion} instance.
   *
   * @param contextLength Context length of the model
   */
  public void setContextLength(@Nullable final Integer contextLength) {
    this.contextLength = contextLength;
  }

  /**
   * Set the inputTypes of this {@link AiModelVersion} instance and return the same instance.
   *
   * @param inputTypes List of input types supported by the model
   * @return The same instance of this {@link AiModelVersion} class
   */
  @Nonnull
  public AiModelVersion inputTypes(@Nullable final List<String> inputTypes) {
    this.inputTypes = inputTypes;
    return this;
  }

  /**
   * Add one inputTypes instance to this {@link AiModelVersion}.
   *
   * @param inputTypesItem The inputTypes that should be added
   * @return The same instance of type {@link AiModelVersion}
   */
  @Nonnull
  public AiModelVersion addInputTypesItem(@Nonnull final String inputTypesItem) {
    if (this.inputTypes == null) {
      this.inputTypes = new ArrayList<>();
    }
    this.inputTypes.add(inputTypesItem);
    return this;
  }

  /**
   * List of input types supported by the model
   *
   * @return inputTypes The inputTypes of this {@link AiModelVersion} instance.
   */
  @Nonnull
  public List<String> getInputTypes() {
    return inputTypes;
  }

  /**
   * Set the inputTypes of this {@link AiModelVersion} instance.
   *
   * @param inputTypes List of input types supported by the model
   */
  public void setInputTypes(@Nullable final List<String> inputTypes) {
    this.inputTypes = inputTypes;
  }

  /**
   * Set the capabilities of this {@link AiModelVersion} instance and return the same instance.
   *
   * @param capabilities List of capabilities supported by the model
   * @return The same instance of this {@link AiModelVersion} class
   */
  @Nonnull
  public AiModelVersion capabilities(@Nullable final List<String> capabilities) {
    this.capabilities = capabilities;
    return this;
  }

  /**
   * Add one capabilities instance to this {@link AiModelVersion}.
   *
   * @param capabilitiesItem The capabilities that should be added
   * @return The same instance of type {@link AiModelVersion}
   */
  @Nonnull
  public AiModelVersion addCapabilitiesItem(@Nonnull final String capabilitiesItem) {
    if (this.capabilities == null) {
      this.capabilities = new ArrayList<>();
    }
    this.capabilities.add(capabilitiesItem);
    return this;
  }

  /**
   * List of capabilities supported by the model
   *
   * @return capabilities The capabilities of this {@link AiModelVersion} instance.
   */
  @Nonnull
  public List<String> getCapabilities() {
    return capabilities;
  }

  /**
   * Set the capabilities of this {@link AiModelVersion} instance.
   *
   * @param capabilities List of capabilities supported by the model
   */
  public void setCapabilities(@Nullable final List<String> capabilities) {
    this.capabilities = capabilities;
  }

  /**
   * Set the metadata of this {@link AiModelVersion} instance and return the same instance.
   *
   * @param metadata List of metadata supported by the model
   * @return The same instance of this {@link AiModelVersion} class
   */
  @Nonnull
  public AiModelVersion metadata(@Nullable final List<Map<String, String>> metadata) {
    this.metadata = metadata;
    return this;
  }

  /**
   * Add one metadata instance to this {@link AiModelVersion}.
   *
   * @param metadataItem The metadata that should be added
   * @return The same instance of type {@link AiModelVersion}
   */
  @Nonnull
  public AiModelVersion addMetadataItem(@Nonnull final Map<String, String> metadataItem) {
    if (this.metadata == null) {
      this.metadata = new ArrayList<>();
    }
    this.metadata.add(metadataItem);
    return this;
  }

  /**
   * List of metadata supported by the model
   *
   * @return metadata The metadata of this {@link AiModelVersion} instance.
   */
  @Nonnull
  public List<Map<String, String>> getMetadata() {
    return metadata;
  }

  /**
   * Set the metadata of this {@link AiModelVersion} instance.
   *
   * @param metadata List of metadata supported by the model
   */
  public void setMetadata(@Nullable final List<Map<String, String>> metadata) {
    this.metadata = metadata;
  }

  /**
   * Set the cost of this {@link AiModelVersion} instance and return the same instance.
   *
   * @param cost List of costs associated with the model
   * @return The same instance of this {@link AiModelVersion} class
   */
  @Nonnull
  public AiModelVersion cost(@Nullable final List<Map<String, String>> cost) {
    this.cost = cost;
    return this;
  }

  /**
   * Add one cost instance to this {@link AiModelVersion}.
   *
   * @param costItem The cost that should be added
   * @return The same instance of type {@link AiModelVersion}
   */
  @Nonnull
  public AiModelVersion addCostItem(@Nonnull final Map<String, String> costItem) {
    if (this.cost == null) {
      this.cost = new ArrayList<>();
    }
    this.cost.add(costItem);
    return this;
  }

  /**
   * List of costs associated with the model
   *
   * @return cost The cost of this {@link AiModelVersion} instance.
   */
  @Nonnull
  public List<Map<String, String>> getCost() {
    return cost;
  }

  /**
   * Set the cost of this {@link AiModelVersion} instance.
   *
   * @param cost List of costs associated with the model
   */
  public void setCost(@Nullable final List<Map<String, String>> cost) {
    this.cost = cost;
  }

  /**
   * Set the suggestedReplacements of this {@link AiModelVersion} instance and return the same
   * instance.
   *
   * @param suggestedReplacements List of suggested replacements for the model
   * @return The same instance of this {@link AiModelVersion} class
   */
  @Nonnull
  public AiModelVersion suggestedReplacements(@Nullable final List<String> suggestedReplacements) {
    this.suggestedReplacements = suggestedReplacements;
    return this;
  }

  /**
   * Add one suggestedReplacements instance to this {@link AiModelVersion}.
   *
   * @param suggestedReplacementsItem The suggestedReplacements that should be added
   * @return The same instance of type {@link AiModelVersion}
   */
  @Nonnull
  public AiModelVersion addSuggestedReplacementsItem(
      @Nonnull final String suggestedReplacementsItem) {
    if (this.suggestedReplacements == null) {
      this.suggestedReplacements = new ArrayList<>();
    }
    this.suggestedReplacements.add(suggestedReplacementsItem);
    return this;
  }

  /**
   * List of suggested replacements for the model
   *
   * @return suggestedReplacements The suggestedReplacements of this {@link AiModelVersion}
   *     instance.
   */
  @Nonnull
  public List<String> getSuggestedReplacements() {
    return suggestedReplacements;
  }

  /**
   * Set the suggestedReplacements of this {@link AiModelVersion} instance.
   *
   * @param suggestedReplacements List of suggested replacements for the model
   */
  public void setSuggestedReplacements(@Nullable final List<String> suggestedReplacements) {
    this.suggestedReplacements = suggestedReplacements;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link AiModelVersion}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link AiModelVersion} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("AiModelVersion has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link AiModelVersion} instance. If the map previously
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
    final AiModelVersion aiModelVersion = (AiModelVersion) o;
    return Objects.equals(this.cloudSdkCustomFields, aiModelVersion.cloudSdkCustomFields)
        && Objects.equals(this.name, aiModelVersion.name)
        && Objects.equals(this.isLatest, aiModelVersion.isLatest)
        && Objects.equals(this.deprecated, aiModelVersion.deprecated)
        && Objects.equals(this.retirementDate, aiModelVersion.retirementDate)
        && Objects.equals(this.contextLength, aiModelVersion.contextLength)
        && Objects.equals(this.inputTypes, aiModelVersion.inputTypes)
        && Objects.equals(this.capabilities, aiModelVersion.capabilities)
        && Objects.equals(this.metadata, aiModelVersion.metadata)
        && Objects.equals(this.cost, aiModelVersion.cost)
        && Objects.equals(this.suggestedReplacements, aiModelVersion.suggestedReplacements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        name,
        isLatest,
        deprecated,
        retirementDate,
        contextLength,
        inputTypes,
        capabilities,
        metadata,
        cost,
        suggestedReplacements,
        cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class AiModelVersion {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    isLatest: ").append(toIndentedString(isLatest)).append("\n");
    sb.append("    deprecated: ").append(toIndentedString(deprecated)).append("\n");
    sb.append("    retirementDate: ").append(toIndentedString(retirementDate)).append("\n");
    sb.append("    contextLength: ").append(toIndentedString(contextLength)).append("\n");
    sb.append("    inputTypes: ").append(toIndentedString(inputTypes)).append("\n");
    sb.append("    capabilities: ").append(toIndentedString(capabilities)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    cost: ").append(toIndentedString(cost)).append("\n");
    sb.append("    suggestedReplacements: ")
        .append(toIndentedString(suggestedReplacements))
        .append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link AiModelVersion}
   * instance with all required arguments.
   */
  public static Builder create() {
    return (name) ->
        (isLatest) ->
            (deprecated) ->
                new AiModelVersion().name(name).isLatest(isLatest).deprecated(deprecated);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the name of this {@link AiModelVersion} instance.
     *
     * @param name Name of model version
     * @return The AiModelVersion builder.
     */
    Builder1 name(@Nonnull final String name);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the isLatest of this {@link AiModelVersion} instance.
     *
     * @param isLatest Displays whether it is the latest version offered for the model
     * @return The AiModelVersion builder.
     */
    Builder2 isLatest(@Nonnull final Boolean isLatest);
  }

  /** Builder helper class. */
  public interface Builder2 {
    /**
     * Set the deprecated of this {@link AiModelVersion} instance.
     *
     * @param deprecated Deprecation status of model
     * @return The AiModelVersion instance.
     */
    AiModelVersion deprecated(@Nonnull final Boolean deprecated);
  }
}
