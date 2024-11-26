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

/** TemplateRef */
@Beta // CHECKSTYLE:OFF
public class TemplateRef implements TemplatingModuleConfig
// CHECKSTYLE:ON
{
  @JsonProperty("template_ref")
  private TemplateRefTemplateRef templateRef;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for TemplateRef. */
  protected TemplateRef() {}

  /**
   * Set the templateRef of this {@link TemplateRef} instance and return the same instance.
   *
   * @param templateRef The templateRef of this {@link TemplateRef}
   * @return The same instance of this {@link TemplateRef} class
   */
  @Nonnull
  public TemplateRef templateRef(@Nonnull final TemplateRefTemplateRef templateRef) {
    this.templateRef = templateRef;
    return this;
  }

  /**
   * Get templateRef
   *
   * @return templateRef The templateRef of this {@link TemplateRef} instance.
   */
  @Nonnull
  public TemplateRefTemplateRef getTemplateRef() {
    return templateRef;
  }

  /**
   * Set the templateRef of this {@link TemplateRef} instance.
   *
   * @param templateRef The templateRef of this {@link TemplateRef}
   */
  public void setTemplateRef(@Nonnull final TemplateRefTemplateRef templateRef) {
    this.templateRef = templateRef;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link TemplateRef}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link TemplateRef} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("TemplateRef has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link TemplateRef} instance. If the map previously
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
    final TemplateRef templateRef = (TemplateRef) o;
    return Objects.equals(this.cloudSdkCustomFields, templateRef.cloudSdkCustomFields)
        && Objects.equals(this.templateRef, templateRef.templateRef);
  }

  @Override
  public int hashCode() {
    return Objects.hash(templateRef, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class TemplateRef {\n");
    sb.append("    templateRef: ").append(toIndentedString(templateRef)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link TemplateRef} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (templateRef) -> new TemplateRef().templateRef(templateRef);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the templateRef of this {@link TemplateRef} instance.
     *
     * @param templateRef The templateRef of this {@link TemplateRef}
     * @return The TemplateRef instance.
     */
    TemplateRef templateRef(@Nonnull final TemplateRefTemplateRef templateRef);
  }
}