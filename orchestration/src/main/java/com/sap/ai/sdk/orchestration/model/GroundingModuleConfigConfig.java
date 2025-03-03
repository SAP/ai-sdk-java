/*
 * Internal Orchestration Service API
 * Orchestration is an inference service which provides common additional capabilities for business AI scenarios, such as content filtering and data masking. At the core of the service is the LLM module which allows for an easy, harmonized access to the language models of gen AI hub. The service is designed to be modular and extensible, allowing for the addition of new modules in the future. Each module can be configured independently and at runtime, allowing for a high degree of flexibility in the orchestration of AI services.
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.orchestration.model;

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

/** GroundingModuleConfigConfig */
@Beta // CHECKSTYLE:OFF
public class GroundingModuleConfigConfig
// CHECKSTYLE:ON
{
  @JsonProperty("filters")
  private List<GroundingModuleConfigConfigFiltersInner> filters = new ArrayList<>();

  @JsonProperty("input_params")
  private List<String> inputParams = new ArrayList<>();

  @JsonProperty("output_param")
  private String outputParam;

  @JsonProperty("metadata_params")
  private List<String> metadataParams = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for GroundingModuleConfigConfig. */
  protected GroundingModuleConfigConfig() {}

  /**
   * Set the filters of this {@link GroundingModuleConfigConfig} instance and return the same
   * instance.
   *
   * @param filters Document grounding service filters to be used
   * @return The same instance of this {@link GroundingModuleConfigConfig} class
   */
  @Nonnull
  public GroundingModuleConfigConfig filters(
      @Nullable final List<GroundingModuleConfigConfigFiltersInner> filters) {
    this.filters = filters;
    return this;
  }

  /**
   * Add one filters instance to this {@link GroundingModuleConfigConfig}.
   *
   * @param filtersItem The filters that should be added
   * @return The same instance of type {@link GroundingModuleConfigConfig}
   */
  @Nonnull
  public GroundingModuleConfigConfig addFiltersItem(
      @Nonnull final GroundingModuleConfigConfigFiltersInner filtersItem) {
    if (this.filters == null) {
      this.filters = new ArrayList<>();
    }
    this.filters.add(filtersItem);
    return this;
  }

  /**
   * Document grounding service filters to be used
   *
   * @return filters The filters of this {@link GroundingModuleConfigConfig} instance.
   */
  @Nonnull
  public List<GroundingModuleConfigConfigFiltersInner> getFilters() {
    return filters;
  }

  /**
   * Set the filters of this {@link GroundingModuleConfigConfig} instance.
   *
   * @param filters Document grounding service filters to be used
   */
  public void setFilters(@Nullable final List<GroundingModuleConfigConfigFiltersInner> filters) {
    this.filters = filters;
  }

  /**
   * Set the inputParams of this {@link GroundingModuleConfigConfig} instance and return the same
   * instance.
   *
   * @param inputParams Contains the input parameters used for grounding input questions
   * @return The same instance of this {@link GroundingModuleConfigConfig} class
   */
  @Nonnull
  public GroundingModuleConfigConfig inputParams(@Nonnull final List<String> inputParams) {
    this.inputParams = inputParams;
    return this;
  }

  /**
   * Add one inputParams instance to this {@link GroundingModuleConfigConfig}.
   *
   * @param inputParamsItem The inputParams that should be added
   * @return The same instance of type {@link GroundingModuleConfigConfig}
   */
  @Nonnull
  public GroundingModuleConfigConfig addInputParamsItem(@Nonnull final String inputParamsItem) {
    if (this.inputParams == null) {
      this.inputParams = new ArrayList<>();
    }
    this.inputParams.add(inputParamsItem);
    return this;
  }

  /**
   * Contains the input parameters used for grounding input questions
   *
   * @return inputParams The inputParams of this {@link GroundingModuleConfigConfig} instance.
   */
  @Nonnull
  public List<String> getInputParams() {
    return inputParams;
  }

  /**
   * Set the inputParams of this {@link GroundingModuleConfigConfig} instance.
   *
   * @param inputParams Contains the input parameters used for grounding input questions
   */
  public void setInputParams(@Nonnull final List<String> inputParams) {
    this.inputParams = inputParams;
  }

  /**
   * Set the outputParam of this {@link GroundingModuleConfigConfig} instance and return the same
   * instance.
   *
   * @param outputParam Parameter name used for grounding output
   * @return The same instance of this {@link GroundingModuleConfigConfig} class
   */
  @Nonnull
  public GroundingModuleConfigConfig outputParam(@Nonnull final String outputParam) {
    this.outputParam = outputParam;
    return this;
  }

  /**
   * Parameter name used for grounding output
   *
   * @return outputParam The outputParam of this {@link GroundingModuleConfigConfig} instance.
   */
  @Nonnull
  public String getOutputParam() {
    return outputParam;
  }

  /**
   * Set the outputParam of this {@link GroundingModuleConfigConfig} instance.
   *
   * @param outputParam Parameter name used for grounding output
   */
  public void setOutputParam(@Nonnull final String outputParam) {
    this.outputParam = outputParam;
  }

  /**
   * Set the metadataParams of this {@link GroundingModuleConfigConfig} instance and return the same
   * instance.
   *
   * @param metadataParams Parameter name used for specifying metadata parameters
   * @return The same instance of this {@link GroundingModuleConfigConfig} class
   */
  @Nonnull
  public GroundingModuleConfigConfig metadataParams(@Nullable final List<String> metadataParams) {
    this.metadataParams = metadataParams;
    return this;
  }

  /**
   * Add one metadataParams instance to this {@link GroundingModuleConfigConfig}.
   *
   * @param metadataParamsItem The metadataParams that should be added
   * @return The same instance of type {@link GroundingModuleConfigConfig}
   */
  @Nonnull
  public GroundingModuleConfigConfig addMetadataParamsItem(
      @Nonnull final String metadataParamsItem) {
    if (this.metadataParams == null) {
      this.metadataParams = new ArrayList<>();
    }
    this.metadataParams.add(metadataParamsItem);
    return this;
  }

  /**
   * Parameter name used for specifying metadata parameters
   *
   * @return metadataParams The metadataParams of this {@link GroundingModuleConfigConfig} instance.
   */
  @Nonnull
  public List<String> getMetadataParams() {
    return metadataParams;
  }

  /**
   * Set the metadataParams of this {@link GroundingModuleConfigConfig} instance.
   *
   * @param metadataParams Parameter name used for specifying metadata parameters
   */
  public void setMetadataParams(@Nullable final List<String> metadataParams) {
    this.metadataParams = metadataParams;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link GroundingModuleConfigConfig}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link GroundingModuleConfigConfig}
   * instance.
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
      throw new NoSuchElementException(
          "GroundingModuleConfigConfig has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link GroundingModuleConfigConfig} instance including
   * unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (filters != null) declaredFields.put("filters", filters);
    if (inputParams != null) declaredFields.put("inputParams", inputParams);
    if (outputParam != null) declaredFields.put("outputParam", outputParam);
    if (metadataParams != null) declaredFields.put("metadataParams", metadataParams);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link GroundingModuleConfigConfig} instance. If the map
   * previously contained a mapping for the key, the old value is replaced by the specified value.
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
    final GroundingModuleConfigConfig groundingModuleConfigConfig = (GroundingModuleConfigConfig) o;
    return Objects.equals(
            this.cloudSdkCustomFields, groundingModuleConfigConfig.cloudSdkCustomFields)
        && Objects.equals(this.filters, groundingModuleConfigConfig.filters)
        && Objects.equals(this.inputParams, groundingModuleConfigConfig.inputParams)
        && Objects.equals(this.outputParam, groundingModuleConfigConfig.outputParam)
        && Objects.equals(this.metadataParams, groundingModuleConfigConfig.metadataParams);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filters, inputParams, outputParam, metadataParams, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class GroundingModuleConfigConfig {\n");
    sb.append("    filters: ").append(toIndentedString(filters)).append("\n");
    sb.append("    inputParams: ").append(toIndentedString(inputParams)).append("\n");
    sb.append("    outputParam: ").append(toIndentedString(outputParam)).append("\n");
    sb.append("    metadataParams: ").append(toIndentedString(metadataParams)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link
   * GroundingModuleConfigConfig} instance with all required arguments.
   */
  public static Builder create() {
    return (inputParams) ->
        (outputParam) ->
            new GroundingModuleConfigConfig().inputParams(inputParams).outputParam(outputParam);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the inputParams of this {@link GroundingModuleConfigConfig} instance.
     *
     * @param inputParams Contains the input parameters used for grounding input questions
     * @return The GroundingModuleConfigConfig builder.
     */
    Builder1 inputParams(@Nonnull final List<String> inputParams);

    /**
     * Set the inputParams of this {@link GroundingModuleConfigConfig} instance.
     *
     * @param inputParams Contains the input parameters used for grounding input questions
     * @return The GroundingModuleConfigConfig builder.
     */
    default Builder1 inputParams(@Nonnull final String... inputParams) {
      return inputParams(Arrays.asList(inputParams));
    }
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the outputParam of this {@link GroundingModuleConfigConfig} instance.
     *
     * @param outputParam Parameter name used for grounding output
     * @return The GroundingModuleConfigConfig instance.
     */
    GroundingModuleConfigConfig outputParam(@Nonnull final String outputParam);
  }
}
