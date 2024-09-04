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

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Error Response */
// CHECKSTYLE:OFF
public class DSetError
// CHECKSTYLE:ON
{
  @JsonProperty("code")
  private String code;

  @JsonProperty("message")
  private String message;

  @JsonProperty("target")
  private String target;

  @JsonProperty("requestId")
  private String requestId;

  @JsonProperty("details")
  private Set<DSetErrorDetailsInner> details = new LinkedHashSet<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  protected DSetError() {}

  /**
   * Set the code of this {@link DSetError} instance and return the same instance.
   *
   * @param code The code of this {@link DSetError}
   * @return The same instance of this {@link DSetError} class
   */
  @Nonnull
  public DSetError code(@Nonnull final String code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   *
   * @return code The code of this {@link DSetError} instance.
   */
  @Nonnull
  public String getCode() {
    return code;
  }

  /**
   * Set the code of this {@link DSetError} instance.
   *
   * @param code The code of this {@link DSetError}
   */
  public void setCode(@Nonnull final String code) {
    this.code = code;
  }

  /**
   * Set the message of this {@link DSetError} instance and return the same instance.
   *
   * @param message The message of this {@link DSetError}
   * @return The same instance of this {@link DSetError} class
   */
  @Nonnull
  public DSetError message(@Nonnull final String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   *
   * @return message The message of this {@link DSetError} instance.
   */
  @Nonnull
  public String getMessage() {
    return message;
  }

  /**
   * Set the message of this {@link DSetError} instance.
   *
   * @param message The message of this {@link DSetError}
   */
  public void setMessage(@Nonnull final String message) {
    this.message = message;
  }

  /**
   * Set the target of this {@link DSetError} instance and return the same instance.
   *
   * @param target The target of this {@link DSetError}
   * @return The same instance of this {@link DSetError} class
   */
  @Nonnull
  public DSetError target(@Nullable final String target) {
    this.target = target;
    return this;
  }

  /**
   * Get target
   *
   * @return target The target of this {@link DSetError} instance.
   */
  @Nonnull
  public String getTarget() {
    return target;
  }

  /**
   * Set the target of this {@link DSetError} instance.
   *
   * @param target The target of this {@link DSetError}
   */
  public void setTarget(@Nullable final String target) {
    this.target = target;
  }

  /**
   * Set the requestId of this {@link DSetError} instance and return the same instance.
   *
   * @param requestId The requestId of this {@link DSetError}
   * @return The same instance of this {@link DSetError} class
   */
  @Nonnull
  public DSetError requestId(@Nullable final String requestId) {
    this.requestId = requestId;
    return this;
  }

  /**
   * Get requestId
   *
   * @return requestId The requestId of this {@link DSetError} instance.
   */
  @Nonnull
  public String getRequestId() {
    return requestId;
  }

  /**
   * Set the requestId of this {@link DSetError} instance.
   *
   * @param requestId The requestId of this {@link DSetError}
   */
  public void setRequestId(@Nullable final String requestId) {
    this.requestId = requestId;
  }

  /**
   * Set the details of this {@link DSetError} instance and return the same instance.
   *
   * @param details The details of this {@link DSetError}
   * @return The same instance of this {@link DSetError} class
   */
  @Nonnull
  public DSetError details(@Nullable final Set<DSetErrorDetailsInner> details) {
    this.details = details;
    return this;
  }

  /**
   * Add one details instance to this {@link DSetError}.
   *
   * @param detailsItem The details that should be added
   * @return The same instance of type {@link DSetError}
   */
  @Nonnull
  public DSetError addDetailsItem(@Nonnull final DSetErrorDetailsInner detailsItem) {
    if (this.details == null) {
      this.details = new LinkedHashSet<>();
    }
    this.details.add(detailsItem);
    return this;
  }

  /**
   * Get details
   *
   * @return details The details of this {@link DSetError} instance.
   */
  @Nonnull
  public Set<DSetErrorDetailsInner> getDetails() {
    return details;
  }

  /**
   * Set the details of this {@link DSetError} instance.
   *
   * @param details The details of this {@link DSetError}
   */
  public void setDetails(@Nullable final Set<DSetErrorDetailsInner> details) {
    this.details = details;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link DSetError}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link DSetError} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException("DSetError has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link DSetError} instance. If the map previously
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
    final DSetError dsetError = (DSetError) o;
    return Objects.equals(this.cloudSdkCustomFields, dsetError.cloudSdkCustomFields)
        && Objects.equals(this.code, dsetError.code)
        && Objects.equals(this.message, dsetError.message)
        && Objects.equals(this.target, dsetError.target)
        && Objects.equals(this.requestId, dsetError.requestId)
        && Objects.equals(this.details, dsetError.details);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, message, target, requestId, details, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class DSetError {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    target: ").append(toIndentedString(target)).append("\n");
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    details: ").append(toIndentedString(details)).append("\n");
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
   * Create a type-safe, fluent-api builder object to construct a new {@link DSetError} instance
   * with all required arguments.
   */
  public static Builder create() {
    return (code) -> (message) -> new DSetError().code(code).message(message);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the code of this {@link DSetError} instance.
     *
     * @param code The code of this {@link DSetError}
     * @return The DSetError builder.
     */
    Builder1 code(@Nonnull final String code);
  }

  /** Builder helper class. */
  public interface Builder1 {
    /**
     * Set the message of this {@link DSetError} instance.
     *
     * @param message The message of this {@link DSetError}
     * @return The DSetError instance.
     */
    DSetError message(@Nonnull final String message);
  }
}
