/*
 * Document Grounding Pipeline API
 * SAP AI Core - API Specification AI Data Management api's
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.grounding.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** CollectionPendingResponse */
// CHECKSTYLE:OFF
public class CollectionPendingResponse
    implements VectorV1VectorEndpointsGetCollectionCreationStatus200Response,
        VectorV1VectorEndpointsGetCollectionDeletionStatus200Response
// CHECKSTYLE:ON
{
  @JsonProperty("Location")
  private URI location;

  @JsonProperty("status")
  private String status;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for CollectionPendingResponse. */
  protected CollectionPendingResponse() {}

  /**
   * Set the location of this {@link CollectionPendingResponse} instance and return the same
   * instance.
   *
   * @param location The location of this {@link CollectionPendingResponse}
   * @return The same instance of this {@link CollectionPendingResponse} class
   */
  @Nonnull
  public CollectionPendingResponse location(@Nonnull final URI location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   *
   * @return location The location of this {@link CollectionPendingResponse} instance.
   */
  @Nonnull
  public URI getLocation() {
    return location;
  }

  /**
   * Set the location of this {@link CollectionPendingResponse} instance.
   *
   * @param location The location of this {@link CollectionPendingResponse}
   */
  public void setLocation(@Nonnull final URI location) {
    this.location = location;
  }

  /**
   * Set the status of this {@link CollectionPendingResponse} instance and return the same instance.
   *
   * @param status The status of this {@link CollectionPendingResponse}
   * @return The same instance of this {@link CollectionPendingResponse} class
   */
  @Nonnull
  public CollectionPendingResponse status(@Nonnull final String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   *
   * @return status The status of this {@link CollectionPendingResponse} instance.
   */
  @Nonnull
  public String getStatus() {
    return status;
  }

  /**
   * Set the status of this {@link CollectionPendingResponse} instance.
   *
   * @param status The status of this {@link CollectionPendingResponse}
   */
  public void setStatus(@Nonnull final String status) {
    this.status = status;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link CollectionPendingResponse}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link CollectionPendingResponse} instance.
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
          "CollectionPendingResponse has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Get the value of all properties of this {@link CollectionPendingResponse} instance including
   * unrecognized properties.
   *
   * @return The map of all properties
   */
  @JsonIgnore
  @Nonnull
  public Map<String, Object> toMap() {
    final Map<String, Object> declaredFields = new LinkedHashMap<>(cloudSdkCustomFields);
    if (location != null) declaredFields.put("location", location);
    if (status != null) declaredFields.put("status", status);
    return declaredFields;
  }

  /**
   * Set an unrecognizable property of this {@link CollectionPendingResponse} instance. If the map
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
    final CollectionPendingResponse collectionPendingResponse = (CollectionPendingResponse) o;
    return Objects.equals(this.cloudSdkCustomFields, collectionPendingResponse.cloudSdkCustomFields)
        && Objects.equals(this.location, collectionPendingResponse.location)
        && Objects.equals(this.status, collectionPendingResponse.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(location, status, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class CollectionPendingResponse {\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link
   * CollectionPendingResponse} instance with all required arguments.
   */
  public static Builder create() {
    return (location) ->
        (status) -> new CollectionPendingResponse().location(location).status(status);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the location of this {@link CollectionPendingResponse} instance.
     *
     * @param location The location of this {@link CollectionPendingResponse}
     * @return The CollectionPendingResponse builder.
     */
    Builder1 location(@Nonnull final URI location);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the status of this {@link CollectionPendingResponse} instance.
     *
     * @param status The status of this {@link CollectionPendingResponse}
     * @return The CollectionPendingResponse instance.
     */
    CollectionPendingResponse status(@Nonnull final String status);
  }
}
