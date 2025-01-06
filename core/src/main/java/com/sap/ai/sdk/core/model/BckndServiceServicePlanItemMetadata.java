/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
 * The version of the OpenAPI document: 2.37.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.core.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** BckndServiceServicePlanItemMetadata */
// CHECKSTYLE:OFF
public class BckndServiceServicePlanItemMetadata
// CHECKSTYLE:ON
{
  /** Gets or Sets supportedPlatforms */
  public enum SupportedPlatformsEnum {
    /** The CLOUDFOUNDRY option of this BckndServiceServicePlanItemMetadata */
    CLOUDFOUNDRY("cloudfoundry"),

    /** The KUBERNETES option of this BckndServiceServicePlanItemMetadata */
    KUBERNETES("kubernetes"),

    /** The SAPBTP option of this BckndServiceServicePlanItemMetadata */
    SAPBTP("sapbtp"),

    /** The UNKNOWN_DEFAULT_OPEN_API option of this BckndServiceServicePlanItemMetadata */
    UNKNOWN_DEFAULT_OPEN_API("unknown_default_open_api");

    private String value;

    SupportedPlatformsEnum(String value) {
      this.value = value;
    }

    /**
     * Get the value of the enum
     *
     * @return The enum value
     */
    @JsonValue
    @Nonnull
    public String getValue() {
      return value;
    }

    /**
     * Get the String value of the enum value.
     *
     * @return The enum value as String
     */
    @Override
    @Nonnull
    public String toString() {
      return String.valueOf(value);
    }

    /**
     * Get the enum value from a String value
     *
     * @param value The String value
     * @return The enum value of type BckndServiceServicePlanItemMetadata
     */
    @JsonCreator
    @Nonnull
    public static SupportedPlatformsEnum fromValue(@Nonnull final String value) {
      for (SupportedPlatformsEnum b : SupportedPlatformsEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return UNKNOWN_DEFAULT_OPEN_API;
    }
  }

  @JsonProperty("supportedPlatforms")
  private List<SupportedPlatformsEnum> supportedPlatforms = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for BckndServiceServicePlanItemMetadata. */
  protected BckndServiceServicePlanItemMetadata() {}

  /**
   * Set the supportedPlatforms of this {@link BckndServiceServicePlanItemMetadata} instance and
   * return the same instance.
   *
   * @param supportedPlatforms supported platforms of the service plan
   * @return The same instance of this {@link BckndServiceServicePlanItemMetadata} class
   */
  @Nonnull
  public BckndServiceServicePlanItemMetadata supportedPlatforms(
      @Nullable final List<SupportedPlatformsEnum> supportedPlatforms) {
    this.supportedPlatforms = supportedPlatforms;
    return this;
  }

  /**
   * Add one supportedPlatforms instance to this {@link BckndServiceServicePlanItemMetadata}.
   *
   * @param supportedPlatformsItem The supportedPlatforms that should be added
   * @return The same instance of type {@link BckndServiceServicePlanItemMetadata}
   */
  @Nonnull
  public BckndServiceServicePlanItemMetadata addSupportedPlatformsItem(
      @Nonnull final SupportedPlatformsEnum supportedPlatformsItem) {
    if (this.supportedPlatforms == null) {
      this.supportedPlatforms = new ArrayList<>();
    }
    this.supportedPlatforms.add(supportedPlatformsItem);
    return this;
  }

  /**
   * supported platforms of the service plan
   *
   * @return supportedPlatforms The supportedPlatforms of this {@link
   *     BckndServiceServicePlanItemMetadata} instance.
   */
  @Nonnull
  public List<SupportedPlatformsEnum> getSupportedPlatforms() {
    return supportedPlatforms;
  }

  /**
   * Set the supportedPlatforms of this {@link BckndServiceServicePlanItemMetadata} instance.
   *
   * @param supportedPlatforms supported platforms of the service plan
   */
  public void setSupportedPlatforms(
      @Nullable final List<SupportedPlatformsEnum> supportedPlatforms) {
    this.supportedPlatforms = supportedPlatforms;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * BckndServiceServicePlanItemMetadata}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndServiceServicePlanItemMetadata}
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
          "BckndServiceServicePlanItemMetadata has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndServiceServicePlanItemMetadata} instance. If
   * the map previously contained a mapping for the key, the old value is replaced by the specified
   * value.
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
    final BckndServiceServicePlanItemMetadata bckndServiceServicePlanItemMetadata =
        (BckndServiceServicePlanItemMetadata) o;
    return Objects.equals(
            this.cloudSdkCustomFields, bckndServiceServicePlanItemMetadata.cloudSdkCustomFields)
        && Objects.equals(
            this.supportedPlatforms, bckndServiceServicePlanItemMetadata.supportedPlatforms);
  }

  @Override
  public int hashCode() {
    return Objects.hash(supportedPlatforms, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndServiceServicePlanItemMetadata {\n");
    sb.append("    supportedPlatforms: ").append(toIndentedString(supportedPlatforms)).append("\n");
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
   * Create a new {@link BckndServiceServicePlanItemMetadata} instance. No arguments are required.
   */
  public static BckndServiceServicePlanItemMetadata create() {
    return new BckndServiceServicePlanItemMetadata();
  }
}
