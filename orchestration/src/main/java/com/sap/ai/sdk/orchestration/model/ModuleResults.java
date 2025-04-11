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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Results of each module. */
// CHECKSTYLE:OFF
public class ModuleResults
// CHECKSTYLE:ON
{
  @JsonProperty("grounding")
  private GenericModuleResult grounding;

  @JsonProperty("templating")
  private List<ChatMessage> templating = new ArrayList<>();

  @JsonProperty("input_translation")
  private GenericModuleResult inputTranslation;

  @JsonProperty("input_masking")
  private GenericModuleResult inputMasking;

  @JsonProperty("input_filtering")
  private GenericModuleResult inputFiltering;

  @JsonProperty("llm")
  private LLMModuleResult llm;

  @JsonProperty("output_filtering")
  private GenericModuleResult outputFiltering;

  @JsonProperty("output_unmasking")
  private List<ModuleResultsOutputUnmaskingInner> outputUnmasking = new ArrayList<>();

  @JsonProperty("output_translation")
  private GenericModuleResult outputTranslation;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for ModuleResults. */
  protected ModuleResults() {}

  /**
   * Set the grounding of this {@link ModuleResults} instance and return the same instance.
   *
   * @param grounding The grounding of this {@link ModuleResults}
   * @return The same instance of this {@link ModuleResults} class
   */
  @Nonnull
  public ModuleResults grounding(@Nullable final GenericModuleResult grounding) {
    this.grounding = grounding;
    return this;
  }

  /**
   * Get grounding
   *
   * @return grounding The grounding of this {@link ModuleResults} instance.
   */
  @Nonnull
  public GenericModuleResult getGrounding() {
    return grounding;
  }

  /**
   * Set the grounding of this {@link ModuleResults} instance.
   *
   * @param grounding The grounding of this {@link ModuleResults}
   */
  public void setGrounding(@Nullable final GenericModuleResult grounding) {
    this.grounding = grounding;
  }

  /**
   * Set the templating of this {@link ModuleResults} instance and return the same instance.
   *
   * @param templating The templating of this {@link ModuleResults}
   * @return The same instance of this {@link ModuleResults} class
   */
  @Nonnull
  public ModuleResults templating(@Nullable final List<ChatMessage> templating) {
    this.templating = templating;
    return this;
  }

  /**
   * Add one templating instance to this {@link ModuleResults}.
   *
   * @param templatingItem The templating that should be added
   * @return The same instance of type {@link ModuleResults}
   */
  @Nonnull
  public ModuleResults addTemplatingItem(@Nonnull final ChatMessage templatingItem) {
    if (this.templating == null) {
      this.templating = new ArrayList<>();
    }
    this.templating.add(templatingItem);
    return this;
  }

  /**
   * Get templating
   *
   * @return templating The templating of this {@link ModuleResults} instance.
   */
  @Nonnull
  public List<ChatMessage> getTemplating() {
    return templating;
  }

  /**
   * Set the templating of this {@link ModuleResults} instance.
   *
   * @param templating The templating of this {@link ModuleResults}
   */
  public void setTemplating(@Nullable final List<ChatMessage> templating) {
    this.templating = templating;
  }

  /**
   * Set the inputTranslation of this {@link ModuleResults} instance and return the same instance.
   *
   * @param inputTranslation The inputTranslation of this {@link ModuleResults}
   * @return The same instance of this {@link ModuleResults} class
   */
  @Nonnull
  public ModuleResults inputTranslation(@Nullable final GenericModuleResult inputTranslation) {
    this.inputTranslation = inputTranslation;
    return this;
  }

  /**
   * Get inputTranslation
   *
   * @return inputTranslation The inputTranslation of this {@link ModuleResults} instance.
   */
  @Nonnull
  public GenericModuleResult getInputTranslation() {
    return inputTranslation;
  }

  /**
   * Set the inputTranslation of this {@link ModuleResults} instance.
   *
   * @param inputTranslation The inputTranslation of this {@link ModuleResults}
   */
  public void setInputTranslation(@Nullable final GenericModuleResult inputTranslation) {
    this.inputTranslation = inputTranslation;
  }

  /**
   * Set the inputMasking of this {@link ModuleResults} instance and return the same instance.
   *
   * @param inputMasking The inputMasking of this {@link ModuleResults}
   * @return The same instance of this {@link ModuleResults} class
   */
  @Nonnull
  public ModuleResults inputMasking(@Nullable final GenericModuleResult inputMasking) {
    this.inputMasking = inputMasking;
    return this;
  }

  /**
   * Get inputMasking
   *
   * @return inputMasking The inputMasking of this {@link ModuleResults} instance.
   */
  @Nonnull
  public GenericModuleResult getInputMasking() {
    return inputMasking;
  }

  /**
   * Set the inputMasking of this {@link ModuleResults} instance.
   *
   * @param inputMasking The inputMasking of this {@link ModuleResults}
   */
  public void setInputMasking(@Nullable final GenericModuleResult inputMasking) {
    this.inputMasking = inputMasking;
  }

  /**
   * Set the inputFiltering of this {@link ModuleResults} instance and return the same instance.
   *
   * @param inputFiltering The inputFiltering of this {@link ModuleResults}
   * @return The same instance of this {@link ModuleResults} class
   */
  @Nonnull
  public ModuleResults inputFiltering(@Nullable final GenericModuleResult inputFiltering) {
    this.inputFiltering = inputFiltering;
    return this;
  }

  /**
   * Get inputFiltering
   *
   * @return inputFiltering The inputFiltering of this {@link ModuleResults} instance.
   */
  @Nonnull
  public GenericModuleResult getInputFiltering() {
    return inputFiltering;
  }

  /**
   * Set the inputFiltering of this {@link ModuleResults} instance.
   *
   * @param inputFiltering The inputFiltering of this {@link ModuleResults}
   */
  public void setInputFiltering(@Nullable final GenericModuleResult inputFiltering) {
    this.inputFiltering = inputFiltering;
  }

  /**
   * Set the llm of this {@link ModuleResults} instance and return the same instance.
   *
   * @param llm The llm of this {@link ModuleResults}
   * @return The same instance of this {@link ModuleResults} class
   */
  @Nonnull
  public ModuleResults llm(@Nullable final LLMModuleResult llm) {
    this.llm = llm;
    return this;
  }

  /**
   * Get llm
   *
   * @return llm The llm of this {@link ModuleResults} instance.
   */
  @Nonnull
  public LLMModuleResult getLlm() {
    return llm;
  }

  /**
   * Set the llm of this {@link ModuleResults} instance.
   *
   * @param llm The llm of this {@link ModuleResults}
   */
  public void setLlm(@Nullable final LLMModuleResult llm) {
    this.llm = llm;
  }

  /**
   * Set the outputFiltering of this {@link ModuleResults} instance and return the same instance.
   *
   * @param outputFiltering The outputFiltering of this {@link ModuleResults}
   * @return The same instance of this {@link ModuleResults} class
   */
  @Nonnull
  public ModuleResults outputFiltering(@Nullable final GenericModuleResult outputFiltering) {
    this.outputFiltering = outputFiltering;
    return this;
  }

  /**
   * Get outputFiltering
   *
   * @return outputFiltering The outputFiltering of this {@link ModuleResults} instance.
   */
  @Nonnull
  public GenericModuleResult getOutputFiltering() {
    return outputFiltering;
  }

  /**
   * Set the outputFiltering of this {@link ModuleResults} instance.
   *
   * @param outputFiltering The outputFiltering of this {@link ModuleResults}
   */
  public void setOutputFiltering(@Nullable final GenericModuleResult outputFiltering) {
    this.outputFiltering = outputFiltering;
  }

  /**
   * Set the outputUnmasking of this {@link ModuleResults} instance and return the same instance.
   *
   * @param outputUnmasking The outputUnmasking of this {@link ModuleResults}
   * @return The same instance of this {@link ModuleResults} class
   */
  @Nonnull
  public ModuleResults outputUnmasking(
      @Nullable final List<ModuleResultsOutputUnmaskingInner> outputUnmasking) {
    this.outputUnmasking = outputUnmasking;
    return this;
  }

  /**
   * Add one outputUnmasking instance to this {@link ModuleResults}.
   *
   * @param outputUnmaskingItem The outputUnmasking that should be added
   * @return The same instance of type {@link ModuleResults}
   */
  @Nonnull
  public ModuleResults addOutputUnmaskingItem(
      @Nonnull final ModuleResultsOutputUnmaskingInner outputUnmaskingItem) {
    if (this.outputUnmasking == null) {
      this.outputUnmasking = new ArrayList<>();
    }
    this.outputUnmasking.add(outputUnmaskingItem);
    return this;
  }

  /**
   * Get outputUnmasking
   *
   * @return outputUnmasking The outputUnmasking of this {@link ModuleResults} instance.
   */
  @Nonnull
  public List<ModuleResultsOutputUnmaskingInner> getOutputUnmasking() {
    return outputUnmasking;
  }

  /**
   * Set the outputUnmasking of this {@link ModuleResults} instance.
   *
   * @param outputUnmasking The outputUnmasking of this {@link ModuleResults}
   */
  public void setOutputUnmasking(
      @Nullable final List<ModuleResultsOutputUnmaskingInner> outputUnmasking) {
    this.outputUnmasking = outputUnmasking;
  }

  /**
   * Set the outputTranslation of this {@link ModuleResults} instance and return the same instance.
   *
   * @param outputTranslation The outputTranslation of this {@link ModuleResults}
   * @return The same instance of this {@link ModuleResults} class
   */
  @Nonnull
  public ModuleResults outputTranslation(@Nullable final GenericModuleResult outputTranslation) {
    this.outputTranslation = outputTranslation;
    return this;
  }

  /**
   * Get outputTranslation
   *
   * @return outputTranslation The outputTranslation of this {@link ModuleResults} instance.
   */
  @Nonnull
  public GenericModuleResult getOutputTranslation() {
    return outputTranslation;
  }

  /**
   * Set the outputTranslation of this {@link ModuleResults} instance.
   *
   * @param outputTranslation The outputTranslation of this {@link ModuleResults}
   */
  public void setOutputTranslation(@Nullable final GenericModuleResult outputTranslation) {
    this.outputTranslation = outputTranslation;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link ModuleResults}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link ModuleResults} instance.
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
      throw new NoSuchElementException("ModuleResults has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link ModuleResults} instance including unrecognized
   * properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (grounding != null) declaredFields.put("grounding", grounding);
    if (templating != null) declaredFields.put("templating", templating);
    if (inputTranslation != null) declaredFields.put("inputTranslation", inputTranslation);
    if (inputMasking != null) declaredFields.put("inputMasking", inputMasking);
    if (inputFiltering != null) declaredFields.put("inputFiltering", inputFiltering);
    if (llm != null) declaredFields.put("llm", llm);
    if (outputFiltering != null) declaredFields.put("outputFiltering", outputFiltering);
    if (outputUnmasking != null) declaredFields.put("outputUnmasking", outputUnmasking);
    if (outputTranslation != null) declaredFields.put("outputTranslation", outputTranslation);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link ModuleResults} instance. If the map previously
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
    final ModuleResults moduleResults = (ModuleResults) o;
    return Objects.equals(this.cloudSdkCustomFields, moduleResults.cloudSdkCustomFields)
        && Objects.equals(this.grounding, moduleResults.grounding)
        && Objects.equals(this.templating, moduleResults.templating)
        && Objects.equals(this.inputTranslation, moduleResults.inputTranslation)
        && Objects.equals(this.inputMasking, moduleResults.inputMasking)
        && Objects.equals(this.inputFiltering, moduleResults.inputFiltering)
        && Objects.equals(this.llm, moduleResults.llm)
        && Objects.equals(this.outputFiltering, moduleResults.outputFiltering)
        && Objects.equals(this.outputUnmasking, moduleResults.outputUnmasking)
        && Objects.equals(this.outputTranslation, moduleResults.outputTranslation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        grounding,
        templating,
        inputTranslation,
        inputMasking,
        inputFiltering,
        llm,
        outputFiltering,
        outputUnmasking,
        outputTranslation,
        cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ModuleResults {\n");
    sb.append("    grounding: ").append(toIndentedString(grounding)).append("\n");
    sb.append("    templating: ").append(toIndentedString(templating)).append("\n");
    sb.append("    inputTranslation: ").append(toIndentedString(inputTranslation)).append("\n");
    sb.append("    inputMasking: ").append(toIndentedString(inputMasking)).append("\n");
    sb.append("    inputFiltering: ").append(toIndentedString(inputFiltering)).append("\n");
    sb.append("    llm: ").append(toIndentedString(llm)).append("\n");
    sb.append("    outputFiltering: ").append(toIndentedString(outputFiltering)).append("\n");
    sb.append("    outputUnmasking: ").append(toIndentedString(outputUnmasking)).append("\n");
    sb.append("    outputTranslation: ").append(toIndentedString(outputTranslation)).append("\n");
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

  /** Create a new {@link ModuleResults} instance. No arguments are required. */
  public static ModuleResults create() {
    return new ModuleResults();
  }
}
