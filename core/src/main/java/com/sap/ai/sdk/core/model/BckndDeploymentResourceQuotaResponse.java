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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.Beta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** BckndDeploymentResourceQuotaResponse */
@Beta // CHECKSTYLE:OFF
public class BckndDeploymentResourceQuotaResponse
// CHECKSTYLE:ON
{
  @JsonProperty("usage")
  private BckndDeploymentUsage usage;

  @JsonProperty("quotas")
  private List<BckndDeploymentQuotaItem> quotas = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for BckndDeploymentResourceQuotaResponse. */
  protected BckndDeploymentResourceQuotaResponse() {}

  /**
   * Set the usage of this {@link BckndDeploymentResourceQuotaResponse} instance and return the same
   * instance.
   *
   * @param usage The usage of this {@link BckndDeploymentResourceQuotaResponse}
   * @return The same instance of this {@link BckndDeploymentResourceQuotaResponse} class
   */
  @Nonnull
  public BckndDeploymentResourceQuotaResponse usage(@Nullable final BckndDeploymentUsage usage) {
    this.usage = usage;
    return this;
  }

  /**
   * Get usage
   *
   * @return usage The usage of this {@link BckndDeploymentResourceQuotaResponse} instance.
   */
  @Nonnull
  public BckndDeploymentUsage getUsage() {
    return usage;
  }

  /**
   * Set the usage of this {@link BckndDeploymentResourceQuotaResponse} instance.
   *
   * @param usage The usage of this {@link BckndDeploymentResourceQuotaResponse}
   */
  public void setUsage(@Nullable final BckndDeploymentUsage usage) {
    this.usage = usage;
  }

  /**
   * Set the quotas of this {@link BckndDeploymentResourceQuotaResponse} instance and return the
   * same instance.
   *
   * @param quotas The quotas of this {@link BckndDeploymentResourceQuotaResponse}
   * @return The same instance of this {@link BckndDeploymentResourceQuotaResponse} class
   */
  @Nonnull
  public BckndDeploymentResourceQuotaResponse quotas(
      @Nonnull final List<BckndDeploymentQuotaItem> quotas) {
    this.quotas = quotas;
    return this;
  }

  /**
   * Add one quotas instance to this {@link BckndDeploymentResourceQuotaResponse}.
   *
   * @param quotasItem The quotas that should be added
   * @return The same instance of type {@link BckndDeploymentResourceQuotaResponse}
   */
  @Nonnull
  public BckndDeploymentResourceQuotaResponse addQuotasItem(
      @Nonnull final BckndDeploymentQuotaItem quotasItem) {
    if (this.quotas == null) {
      this.quotas = new ArrayList<>();
    }
    this.quotas.add(quotasItem);
    return this;
  }

  /**
   * Get quotas
   *
   * @return quotas The quotas of this {@link BckndDeploymentResourceQuotaResponse} instance.
   */
  @Nonnull
  public List<BckndDeploymentQuotaItem> getQuotas() {
    return quotas;
  }

  /**
   * Set the quotas of this {@link BckndDeploymentResourceQuotaResponse} instance.
   *
   * @param quotas The quotas of this {@link BckndDeploymentResourceQuotaResponse}
   */
  public void setQuotas(@Nonnull final List<BckndDeploymentQuotaItem> quotas) {
    this.quotas = quotas;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * BckndDeploymentResourceQuotaResponse}.
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
   * BckndDeploymentResourceQuotaResponse} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "BckndDeploymentResourceQuotaResponse has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndDeploymentResourceQuotaResponse} instance.
   * If the map previously contained a mapping for the key, the old value is replaced by the
   * specified value.
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
    final BckndDeploymentResourceQuotaResponse bckndDeploymentResourceQuotaResponse =
        (BckndDeploymentResourceQuotaResponse) o;
    return Objects.equals(
            this.cloudSdkCustomFields, bckndDeploymentResourceQuotaResponse.cloudSdkCustomFields)
        && Objects.equals(this.usage, bckndDeploymentResourceQuotaResponse.usage)
        && Objects.equals(this.quotas, bckndDeploymentResourceQuotaResponse.quotas);
  }

  @Override
  public int hashCode() {
    return Objects.hash(usage, quotas, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndDeploymentResourceQuotaResponse {\n");
    sb.append("    usage: ").append(toIndentedString(usage)).append("\n");
    sb.append("    quotas: ").append(toIndentedString(quotas)).append("\n");
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
   * BckndDeploymentResourceQuotaResponse} instance with all required arguments.
   */
  public static Builder create() {
    return (quotas) -> new BckndDeploymentResourceQuotaResponse().quotas(quotas);
  }

  /** Builder helper class. */
  public interface Builder {
    /**
     * Set the quotas of this {@link BckndDeploymentResourceQuotaResponse} instance.
     *
     * @param quotas The quotas of this {@link BckndDeploymentResourceQuotaResponse}
     * @return The BckndDeploymentResourceQuotaResponse instance.
     */
    BckndDeploymentResourceQuotaResponse quotas(
        @Nonnull final List<BckndDeploymentQuotaItem> quotas);

    /**
     * Set the quotas of this {@link BckndDeploymentResourceQuotaResponse} instance.
     *
     * @param quotas The quotas of this {@link BckndDeploymentResourceQuotaResponse}
     * @return The BckndDeploymentResourceQuotaResponse instance.
     */
    default BckndDeploymentResourceQuotaResponse quotas(
        @Nonnull final BckndDeploymentQuotaItem... quotas) {
      return quotas(Arrays.asList(quotas));
    }
  }
}
