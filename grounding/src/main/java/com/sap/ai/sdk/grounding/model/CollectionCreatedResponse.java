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
import com.google.common.annotations.Beta;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** CollectionCreatedResponse */
@Beta // CHECKSTYLE:OFF
public class CollectionCreatedResponse
    implements VectorV1VectorEndpointsGetCollectionCreationStatus200Response
// CHECKSTYLE:ON
{
  @JsonProperty("collectionURL")
  private String collectionURL;

  @JsonProperty("status")
  private String status;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for CollectionCreatedResponse. */
  protected CollectionCreatedResponse() {}

  /**
   * Set the collectionURL of this {@link CollectionCreatedResponse} instance and return the same
   * instance.
   *
   * @param collectionURL The collectionURL of this {@link CollectionCreatedResponse}
   * @return The same instance of this {@link CollectionCreatedResponse} class
   */
  @Nonnull
  public CollectionCreatedResponse collectionURL(@Nonnull final String collectionURL) {
    this.collectionURL = collectionURL;
    return this;
  }

  /**
   * Get collectionURL
   *
   * @return collectionURL The collectionURL of this {@link CollectionCreatedResponse} instance.
   */
  @Nonnull
  public String getCollectionURL() {
    return collectionURL;
  }

  /**
   * Set the collectionURL of this {@link CollectionCreatedResponse} instance.
   *
   * @param collectionURL The collectionURL of this {@link CollectionCreatedResponse}
   */
  public void setCollectionURL(@Nonnull final String collectionURL) {
    this.collectionURL = collectionURL;
  }

  /**
   * Set the status of this {@link CollectionCreatedResponse} instance and return the same instance.
   *
   * @param status The status of this {@link CollectionCreatedResponse}
   * @return The same instance of this {@link CollectionCreatedResponse} class
   */
  @Nonnull
  public CollectionCreatedResponse status(@Nonnull final String status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   *
   * @return status The status of this {@link CollectionCreatedResponse} instance.
   */
  @Nonnull
  public String getStatus() {
    return status;
  }

  /**
   * Set the status of this {@link CollectionCreatedResponse} instance.
   *
   * @param status The status of this {@link CollectionCreatedResponse}
   */
  public void setStatus(@Nonnull final String status) {
    this.status = status;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link CollectionCreatedResponse}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link CollectionCreatedResponse} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "CollectionCreatedResponse has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link CollectionCreatedResponse} instance. If the map
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
    final CollectionCreatedResponse collectionCreatedResponse = (CollectionCreatedResponse) o;
    return Objects.equals(this.cloudSdkCustomFields, collectionCreatedResponse.cloudSdkCustomFields)
        && Objects.equals(this.collectionURL, collectionCreatedResponse.collectionURL)
        && Objects.equals(this.status, collectionCreatedResponse.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(collectionURL, status, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class CollectionCreatedResponse {\n");
    sb.append("    collectionURL: ").append(toIndentedString(collectionURL)).append("\n");
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
   * CollectionCreatedResponse} instance with all required arguments.
   */
  public static Builder create() {
    return (collectionURL) ->
        (status) -> new CollectionCreatedResponse().collectionURL(collectionURL).status(status);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the collectionURL of this {@link CollectionCreatedResponse} instance.
     *
     * @param collectionURL The collectionURL of this {@link CollectionCreatedResponse}
     * @return The CollectionCreatedResponse builder.
     */
    Builder1 collectionURL(@Nonnull final String collectionURL);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the status of this {@link CollectionCreatedResponse} instance.
     *
     * @param status The status of this {@link CollectionCreatedResponse}
     * @return The CollectionCreatedResponse instance.
     */
    CollectionCreatedResponse status(@Nonnull final String status);
  }
}
