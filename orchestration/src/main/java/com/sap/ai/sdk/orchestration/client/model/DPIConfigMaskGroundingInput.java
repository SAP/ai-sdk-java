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

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.Beta;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** DPIConfigMaskGroundingInput */
@Beta // CHECKSTYLE:OFF
public class DPIConfigMaskGroundingInput
// CHECKSTYLE:ON
{
  @JsonProperty("enabled")
  private Boolean enabled = false;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for DPIConfigMaskGroundingInput. */
  protected DPIConfigMaskGroundingInput() {}

  /**
   * Set the enabled of this {@link DPIConfigMaskGroundingInput} instance and return the same
   * instance.
   *
   * @param enabled controls whether the input to the grounding module will be masked with the
   *     configuration supplied in the masking module
   * @return The same instance of this {@link DPIConfigMaskGroundingInput} class
   */
  @Nonnull
  public DPIConfigMaskGroundingInput enabled(@Nullable final Boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  /**
   * controls whether the input to the grounding module will be masked with the configuration
   * supplied in the masking module
   *
   * @return enabled The enabled of this {@link DPIConfigMaskGroundingInput} instance.
   */
  @Nonnull
  public Boolean isEnabled() {
    return enabled;
  }

  /**
   * Set the enabled of this {@link DPIConfigMaskGroundingInput} instance.
   *
   * @param enabled controls whether the input to the grounding module will be masked with the
   *     configuration supplied in the masking module
   */
  public void setEnabled(@Nullable final Boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link DPIConfigMaskGroundingInput}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link DPIConfigMaskGroundingInput}
   * instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "DPIConfigMaskGroundingInput has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link DPIConfigMaskGroundingInput} instance. If the map
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
    final DPIConfigMaskGroundingInput dpIConfigMaskGroundingInput = (DPIConfigMaskGroundingInput) o;
    return Objects.equals(
            this.cloudSdkCustomFields, dpIConfigMaskGroundingInput.cloudSdkCustomFields)
        && Objects.equals(this.enabled, dpIConfigMaskGroundingInput.enabled);
  }

  @Override
  public int hashCode() {
    return Objects.hash(enabled, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class DPIConfigMaskGroundingInput {\n");
    sb.append("    enabled: ").append(toIndentedString(enabled)).append("\n");
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

  /** Create a new {@link DPIConfigMaskGroundingInput} instance. No arguments are required. */
  public static DPIConfigMaskGroundingInput create() {
    return new DPIConfigMaskGroundingInput();
  }
}
