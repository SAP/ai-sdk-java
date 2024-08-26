

/*
 * Internal Orchestration Service API
 * SAP AI Core - Orchestration Service API
 *
 * The version of the OpenAPI document: 0.0.1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.orchestration.client.model;

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
import com.sap.ai.sdk.orchestration.client.model.ChatMessage;
import com.sap.ai.sdk.orchestration.client.model.GenericModuleResult;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResult;
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
 * Results of each module.
 */
// CHECKSTYLE:OFF
public class ModuleResults 
// CHECKSTYLE:ON
{
  @JsonProperty("grounding")
  private GenericModuleResult grounding;

  @JsonProperty("templating")
  private List<ChatMessage> templating = new ArrayList<>();

  @JsonProperty("input_masking")
  private GenericModuleResult inputMasking;

  @JsonProperty("input_filtering")
  private GenericModuleResult inputFiltering;

  @JsonProperty("llm")
  private LLMModuleResult llm;

  @JsonProperty("output_filtering")
  private GenericModuleResult outputFiltering;

  @JsonProperty("output_unmasking")
  private GenericModuleResult outputUnmasking;

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();
  protected ModuleResults() {  }

  /**
   * Set the grounding of this {@link ModuleResults} instance and return the same instance.
   *
   * @param grounding  The grounding of this {@link ModuleResults}
   * @return The same instance of this {@link ModuleResults} class
   */
  @Nonnull public ModuleResults grounding( @Nullable final GenericModuleResult grounding) {
    this.grounding = grounding;
    return this;
  }

  /**
   * Get grounding
   * @return grounding  The grounding of this {@link ModuleResults} instance.
   */
  @Nonnull public GenericModuleResult getGrounding() {
    return grounding;
  }

  /**
   * Set the grounding of this {@link ModuleResults} instance.
   *
   * @param grounding  The grounding of this {@link ModuleResults}
   */
  public void setGrounding( @Nullable final GenericModuleResult grounding) {
    this.grounding = grounding;
  }

  /**
   * Set the templating of this {@link ModuleResults} instance and return the same instance.
   *
   * @param templating  The templating of this {@link ModuleResults}
   * @return The same instance of this {@link ModuleResults} class
   */
  @Nonnull public ModuleResults templating( @Nullable final List<ChatMessage> templating) {
    this.templating = templating;
    return this;
  }
  /**
   * Add one templating instance to this {@link ModuleResults}.
   * @param templatingItem The templating that should be added
   * @return The same instance of type {@link ModuleResults}
   */
  @Nonnull public ModuleResults addTemplatingItem( @Nonnull final ChatMessage templatingItem) {
    if (this.templating == null) {
      this.templating = new ArrayList<>();
    }
    this.templating.add(templatingItem);
    return this;
  }

  /**
   * Get templating
   * @return templating  The templating of this {@link ModuleResults} instance.
   */
  @Nonnull public List<ChatMessage> getTemplating() {
    return templating;
  }

  /**
   * Set the templating of this {@link ModuleResults} instance.
   *
   * @param templating  The templating of this {@link ModuleResults}
   */
  public void setTemplating( @Nullable final List<ChatMessage> templating) {
    this.templating = templating;
  }

  /**
   * Set the inputMasking of this {@link ModuleResults} instance and return the same instance.
   *
   * @param inputMasking  The inputMasking of this {@link ModuleResults}
   * @return The same instance of this {@link ModuleResults} class
   */
  @Nonnull public ModuleResults inputMasking( @Nullable final GenericModuleResult inputMasking) {
    this.inputMasking = inputMasking;
    return this;
  }

  /**
   * Get inputMasking
   * @return inputMasking  The inputMasking of this {@link ModuleResults} instance.
   */
  @Nonnull public GenericModuleResult getInputMasking() {
    return inputMasking;
  }

  /**
   * Set the inputMasking of this {@link ModuleResults} instance.
   *
   * @param inputMasking  The inputMasking of this {@link ModuleResults}
   */
  public void setInputMasking( @Nullable final GenericModuleResult inputMasking) {
    this.inputMasking = inputMasking;
  }

  /**
   * Set the inputFiltering of this {@link ModuleResults} instance and return the same instance.
   *
   * @param inputFiltering  The inputFiltering of this {@link ModuleResults}
   * @return The same instance of this {@link ModuleResults} class
   */
  @Nonnull public ModuleResults inputFiltering( @Nullable final GenericModuleResult inputFiltering) {
    this.inputFiltering = inputFiltering;
    return this;
  }

  /**
   * Get inputFiltering
   * @return inputFiltering  The inputFiltering of this {@link ModuleResults} instance.
   */
  @Nonnull public GenericModuleResult getInputFiltering() {
    return inputFiltering;
  }

  /**
   * Set the inputFiltering of this {@link ModuleResults} instance.
   *
   * @param inputFiltering  The inputFiltering of this {@link ModuleResults}
   */
  public void setInputFiltering( @Nullable final GenericModuleResult inputFiltering) {
    this.inputFiltering = inputFiltering;
  }

  /**
   * Set the llm of this {@link ModuleResults} instance and return the same instance.
   *
   * @param llm  The llm of this {@link ModuleResults}
   * @return The same instance of this {@link ModuleResults} class
   */
  @Nonnull public ModuleResults llm( @Nullable final LLMModuleResult llm) {
    this.llm = llm;
    return this;
  }

  /**
   * Get llm
   * @return llm  The llm of this {@link ModuleResults} instance.
   */
  @Nonnull public LLMModuleResult getLlm() {
    return llm;
  }

  /**
   * Set the llm of this {@link ModuleResults} instance.
   *
   * @param llm  The llm of this {@link ModuleResults}
   */
  public void setLlm( @Nullable final LLMModuleResult llm) {
    this.llm = llm;
  }

  /**
   * Set the outputFiltering of this {@link ModuleResults} instance and return the same instance.
   *
   * @param outputFiltering  The outputFiltering of this {@link ModuleResults}
   * @return The same instance of this {@link ModuleResults} class
   */
  @Nonnull public ModuleResults outputFiltering( @Nullable final GenericModuleResult outputFiltering) {
    this.outputFiltering = outputFiltering;
    return this;
  }

  /**
   * Get outputFiltering
   * @return outputFiltering  The outputFiltering of this {@link ModuleResults} instance.
   */
  @Nonnull public GenericModuleResult getOutputFiltering() {
    return outputFiltering;
  }

  /**
   * Set the outputFiltering of this {@link ModuleResults} instance.
   *
   * @param outputFiltering  The outputFiltering of this {@link ModuleResults}
   */
  public void setOutputFiltering( @Nullable final GenericModuleResult outputFiltering) {
    this.outputFiltering = outputFiltering;
  }

  /**
   * Set the outputUnmasking of this {@link ModuleResults} instance and return the same instance.
   *
   * @param outputUnmasking  The outputUnmasking of this {@link ModuleResults}
   * @return The same instance of this {@link ModuleResults} class
   */
  @Nonnull public ModuleResults outputUnmasking( @Nullable final GenericModuleResult outputUnmasking) {
    this.outputUnmasking = outputUnmasking;
    return this;
  }

  /**
   * Get outputUnmasking
   * @return outputUnmasking  The outputUnmasking of this {@link ModuleResults} instance.
   */
  @Nonnull public GenericModuleResult getOutputUnmasking() {
    return outputUnmasking;
  }

  /**
   * Set the outputUnmasking of this {@link ModuleResults} instance.
   *
   * @param outputUnmasking  The outputUnmasking of this {@link ModuleResults}
   */
  public void setOutputUnmasking( @Nullable final GenericModuleResult outputUnmasking) {
    this.outputUnmasking = outputUnmasking;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ModuleResults}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ModuleResults} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField( @Nonnull final String name ) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("ModuleResults has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link ModuleResults} instance. If the map previously contained a mapping
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
    final ModuleResults moduleResults = (ModuleResults) o;
    return Objects.equals(this.cloudSdkCustomFields, moduleResults.cloudSdkCustomFields) &&
        Objects.equals(this.grounding, moduleResults.grounding) &&
        Objects.equals(this.templating, moduleResults.templating) &&
        Objects.equals(this.inputMasking, moduleResults.inputMasking) &&
        Objects.equals(this.inputFiltering, moduleResults.inputFiltering) &&
        Objects.equals(this.llm, moduleResults.llm) &&
        Objects.equals(this.outputFiltering, moduleResults.outputFiltering) &&
        Objects.equals(this.outputUnmasking, moduleResults.outputUnmasking);
  }

  @Override
  public int hashCode() {
    return Objects.hash(grounding, templating, inputMasking, inputFiltering, llm, outputFiltering, outputUnmasking, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ModuleResults {\n");
    sb.append("    grounding: ").append(toIndentedString(grounding)).append("\n");
    sb.append("    templating: ").append(toIndentedString(templating)).append("\n");
    sb.append("    inputMasking: ").append(toIndentedString(inputMasking)).append("\n");
    sb.append("    inputFiltering: ").append(toIndentedString(inputFiltering)).append("\n");
    sb.append("    llm: ").append(toIndentedString(llm)).append("\n");
    sb.append("    outputFiltering: ").append(toIndentedString(outputFiltering)).append("\n");
    sb.append("    outputUnmasking: ").append(toIndentedString(outputUnmasking)).append("\n");
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

    /**
    * Create a new {@link ModuleResults} instance. No arguments are required.
    */
    public static ModuleResults create() {
        return new ModuleResults();
    }

}

