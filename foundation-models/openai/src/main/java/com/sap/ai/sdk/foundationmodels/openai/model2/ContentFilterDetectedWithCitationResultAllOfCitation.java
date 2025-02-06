/*
 * Copyright (c) 2025 SAP SE or an SAP affiliate company. All rights reserved.
 */

/*
 * Azure OpenAI Service API
 * Azure OpenAI APIs for completions and search
 *
 * The version of the OpenAPI document: 2024-10-21
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.foundationmodels.openai.model2;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** ContentFilterDetectedWithCitationResultAllOfCitation */
// CHECKSTYLE:OFF
@com.google.common.annotations.Beta
public class ContentFilterDetectedWithCitationResultAllOfCitation
// CHECKSTYLE:ON
{
  @JsonProperty("URL")
  private String URL;

  @JsonProperty("license")
  private String license;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /**
   * Set the URL of this {@link ContentFilterDetectedWithCitationResultAllOfCitation} instance and
   * return the same instance.
   *
   * @param URL The URL of this {@link ContentFilterDetectedWithCitationResultAllOfCitation}
   * @return The same instance of this {@link ContentFilterDetectedWithCitationResultAllOfCitation}
   *     class
   */
  @Nonnull
  public ContentFilterDetectedWithCitationResultAllOfCitation URL(@Nullable final String URL) {
    this.URL = URL;
    return this;
  }

  /**
   * Get URL
   *
   * @return URL The URL of this {@link ContentFilterDetectedWithCitationResultAllOfCitation}
   *     instance.
   */
  @Nonnull
  public String getURL() {
    return URL;
  }

  /**
   * Set the URL of this {@link ContentFilterDetectedWithCitationResultAllOfCitation} instance.
   *
   * @param URL The URL of this {@link ContentFilterDetectedWithCitationResultAllOfCitation}
   */
  public void setURL(@Nullable final String URL) {
    this.URL = URL;
  }

  /**
   * Set the license of this {@link ContentFilterDetectedWithCitationResultAllOfCitation} instance
   * and return the same instance.
   *
   * @param license The license of this {@link ContentFilterDetectedWithCitationResultAllOfCitation}
   * @return The same instance of this {@link ContentFilterDetectedWithCitationResultAllOfCitation}
   *     class
   */
  @Nonnull
  public ContentFilterDetectedWithCitationResultAllOfCitation license(
      @Nullable final String license) {
    this.license = license;
    return this;
  }

  /**
   * Get license
   *
   * @return license The license of this {@link
   *     ContentFilterDetectedWithCitationResultAllOfCitation} instance.
   */
  @Nonnull
  public String getLicense() {
    return license;
  }

  /**
   * Set the license of this {@link ContentFilterDetectedWithCitationResultAllOfCitation} instance.
   *
   * @param license The license of this {@link ContentFilterDetectedWithCitationResultAllOfCitation}
   */
  public void setLicense(@Nullable final String license) {
    this.license = license;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * ContentFilterDetectedWithCitationResultAllOfCitation}.
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
   * ContentFilterDetectedWithCitationResultAllOfCitation} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "ContentFilterDetectedWithCitationResultAllOfCitation has no field with name '"
              + name
              + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link
   * ContentFilterDetectedWithCitationResultAllOfCitation} instance. If the map previously contained
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
  public boolean equals(@Nullable final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ContentFilterDetectedWithCitationResultAllOfCitation
        contentFilterDetectedWithCitationResultAllOfCitation =
            (ContentFilterDetectedWithCitationResultAllOfCitation) o;
    return Objects.equals(
            this.cloudSdkCustomFields,
            contentFilterDetectedWithCitationResultAllOfCitation.cloudSdkCustomFields)
        && Objects.equals(this.URL, contentFilterDetectedWithCitationResultAllOfCitation.URL)
        && Objects.equals(
            this.license, contentFilterDetectedWithCitationResultAllOfCitation.license);
  }

  @Override
  public int hashCode() {
    return Objects.hash(URL, license, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class ContentFilterDetectedWithCitationResultAllOfCitation {\n");
    sb.append("    URL: ").append(toIndentedString(URL)).append("\n");
    sb.append("    license: ").append(toIndentedString(license)).append("\n");
    cloudSdkCustomFields.forEach(
        (k, v) ->
            sb.append("    ").append(k).append(": ").append(toIndentedString(v)).append("\n"));
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(final Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
