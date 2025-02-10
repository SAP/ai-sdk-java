/*
 * Prompt Registry API
 * Prompt Storage service for Design time & Runtime prompt templates.
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.prompt.registry.model;

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

/** RegistryControllerPromptControllerHealthz503Response */
@Beta // CHECKSTYLE:OFF
public class RegistryControllerPromptControllerHealthz503Response
// CHECKSTYLE:ON
{
  @JsonProperty("status")
  private String status;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for RegistryControllerPromptControllerHealthz503Response. */
  protected RegistryControllerPromptControllerHealthz503Response() {}

  /**
   * Set the status of this {@link RegistryControllerPromptControllerHealthz503Response} instance
   * and return the same instance.
   *
   * @param status The status of this {@link RegistryControllerPromptControllerHealthz503Response}
   * @return The same instance of this {@link RegistryControllerPromptControllerHealthz503Response}
   *     class
   */
  @Nonnull
  public RegistryControllerPromptControllerHealthz503Response status(
      @Nullable final String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   *
   * @return status The status of this {@link RegistryControllerPromptControllerHealthz503Response}
   *     instance.
   */
  @Nonnull
  public String getStatus() {
    return status;
  }

  /**
   * Set the status of this {@link RegistryControllerPromptControllerHealthz503Response} instance.
   *
   * @param status The status of this {@link RegistryControllerPromptControllerHealthz503Response}
   */
  public void setStatus(@Nullable final String status) {
    this.status = status;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * RegistryControllerPromptControllerHealthz503Response}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link
   * RegistryControllerPromptControllerHealthz503Response} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "RegistryControllerPromptControllerHealthz503Response has no field with name '"
              + name
              + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link
   * RegistryControllerPromptControllerHealthz503Response} instance. If the map previously contained
   * a mapping for the key, the old value is replaced by the specified value.
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
    final RegistryControllerPromptControllerHealthz503Response
        registryControllerPromptControllerHealthz503Response =
            (RegistryControllerPromptControllerHealthz503Response) o;
    return Objects.equals(
            this.cloudSdkCustomFields,
            registryControllerPromptControllerHealthz503Response.cloudSdkCustomFields)
        && Objects.equals(this.status, registryControllerPromptControllerHealthz503Response.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class RegistryControllerPromptControllerHealthz503Response {\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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
   * Create a new {@link RegistryControllerPromptControllerHealthz503Response} instance. No
   * arguments are required.
   */
  public static RegistryControllerPromptControllerHealthz503Response create() {
    return new RegistryControllerPromptControllerHealthz503Response();
  }
}
