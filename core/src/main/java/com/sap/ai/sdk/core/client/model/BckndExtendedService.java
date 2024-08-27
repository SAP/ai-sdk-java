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

/** BckndExtendedService */
// CHECKSTYLE:OFF
public class BckndExtendedService
// CHECKSTYLE:ON
{
  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("url")
  private String url;

  @JsonProperty("brokerSecret")
  private BckndServiceBrokerSecret brokerSecret;

  @JsonProperty("capabilities")
  private BckndServiceCapabilities capabilities;

  @JsonProperty("serviceCatalog")
  private List<BckndServiceServiceCatalogItem> serviceCatalog = new ArrayList<>();

  /** aggregated status of the service */
  public enum StatusEnum {
    /** The PROVISIONED option of this BckndExtendedService */
    PROVISIONED("PROVISIONED"),

    /** The ERROR option of this BckndExtendedService */
    ERROR("ERROR"),

    /** The PROVISIONING option of this BckndExtendedService */
    PROVISIONING("PROVISIONING"),

    /** The DEPROVISIONING option of this BckndExtendedService */
    DEPROVISIONING("DEPROVISIONING");

    private String value;

    StatusEnum(String value) {
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
     * @return The enum value of type BckndExtendedService
     */
    @JsonCreator
    @Nonnull
    public static StatusEnum fromValue(@Nonnull final String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("status")
  private StatusEnum status;

  @JsonProperty("statusMessage")
  private String statusMessage;

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  protected BckndExtendedService() {}

  /**
   * Set the name of this {@link BckndExtendedService} instance and return the same instance.
   *
   * @param name service name
   * @return The same instance of this {@link BckndExtendedService} class
   */
  @Nonnull
  public BckndExtendedService name(@Nonnull final String name) {
    this.name = name;
    return this;
  }

  /**
   * service name
   *
   * @return name The name of this {@link BckndExtendedService} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link BckndExtendedService} instance.
   *
   * @param name service name
   */
  public void setName(@Nonnull final String name) {
    this.name = name;
  }

  /**
   * Set the description of this {@link BckndExtendedService} instance and return the same instance.
   *
   * @param description service description
   * @return The same instance of this {@link BckndExtendedService} class
   */
  @Nonnull
  public BckndExtendedService description(@Nonnull final String description) {
    this.description = description;
    return this;
  }

  /**
   * service description
   *
   * @return description The description of this {@link BckndExtendedService} instance.
   */
  @Nonnull
  public String getDescription() {
    return description;
  }

  /**
   * Set the description of this {@link BckndExtendedService} instance.
   *
   * @param description service description
   */
  public void setDescription(@Nonnull final String description) {
    this.description = description;
  }

  /**
   * Set the url of this {@link BckndExtendedService} instance and return the same instance.
   *
   * @param url service broker url
   * @return The same instance of this {@link BckndExtendedService} class
   */
  @Nonnull
  public BckndExtendedService url(@Nonnull final String url) {
    this.url = url;
    return this;
  }

  /**
   * service broker url
   *
   * @return url The url of this {@link BckndExtendedService} instance.
   */
  @Nonnull
  public String getUrl() {
    return url;
  }

  /**
   * Set the url of this {@link BckndExtendedService} instance.
   *
   * @param url service broker url
   */
  public void setUrl(@Nonnull final String url) {
    this.url = url;
  }

  /**
   * Set the brokerSecret of this {@link BckndExtendedService} instance and return the same
   * instance.
   *
   * @param brokerSecret The brokerSecret of this {@link BckndExtendedService}
   * @return The same instance of this {@link BckndExtendedService} class
   */
  @Nonnull
  public BckndExtendedService brokerSecret(@Nonnull final BckndServiceBrokerSecret brokerSecret) {
    this.brokerSecret = brokerSecret;
    return this;
  }

  /**
   * Get brokerSecret
   *
   * @return brokerSecret The brokerSecret of this {@link BckndExtendedService} instance.
   */
  @Nonnull
  public BckndServiceBrokerSecret getBrokerSecret() {
    return brokerSecret;
  }

  /**
   * Set the brokerSecret of this {@link BckndExtendedService} instance.
   *
   * @param brokerSecret The brokerSecret of this {@link BckndExtendedService}
   */
  public void setBrokerSecret(@Nonnull final BckndServiceBrokerSecret brokerSecret) {
    this.brokerSecret = brokerSecret;
  }

  /**
   * Set the capabilities of this {@link BckndExtendedService} instance and return the same
   * instance.
   *
   * @param capabilities The capabilities of this {@link BckndExtendedService}
   * @return The same instance of this {@link BckndExtendedService} class
   */
  @Nonnull
  public BckndExtendedService capabilities(@Nonnull final BckndServiceCapabilities capabilities) {
    this.capabilities = capabilities;
    return this;
  }

  /**
   * Get capabilities
   *
   * @return capabilities The capabilities of this {@link BckndExtendedService} instance.
   */
  @Nonnull
  public BckndServiceCapabilities getCapabilities() {
    return capabilities;
  }

  /**
   * Set the capabilities of this {@link BckndExtendedService} instance.
   *
   * @param capabilities The capabilities of this {@link BckndExtendedService}
   */
  public void setCapabilities(@Nonnull final BckndServiceCapabilities capabilities) {
    this.capabilities = capabilities;
  }

  /**
   * Set the serviceCatalog of this {@link BckndExtendedService} instance and return the same
   * instance.
   *
   * @param serviceCatalog The serviceCatalog of this {@link BckndExtendedService}
   * @return The same instance of this {@link BckndExtendedService} class
   */
  @Nonnull
  public BckndExtendedService serviceCatalog(
      @Nonnull final List<BckndServiceServiceCatalogItem> serviceCatalog) {
    this.serviceCatalog = serviceCatalog;
    return this;
  }

  /**
   * Add one serviceCatalog instance to this {@link BckndExtendedService}.
   *
   * @param serviceCatalogItem The serviceCatalog that should be added
   * @return The same instance of type {@link BckndExtendedService}
   */
  @Nonnull
  public BckndExtendedService addServiceCatalogItem(
      @Nonnull final BckndServiceServiceCatalogItem serviceCatalogItem) {
    if (this.serviceCatalog == null) {
      this.serviceCatalog = new ArrayList<>();
    }
    this.serviceCatalog.add(serviceCatalogItem);
    return this;
  }

  /**
   * Get serviceCatalog
   *
   * @return serviceCatalog The serviceCatalog of this {@link BckndExtendedService} instance.
   */
  @Nonnull
  public List<BckndServiceServiceCatalogItem> getServiceCatalog() {
    return serviceCatalog;
  }

  /**
   * Set the serviceCatalog of this {@link BckndExtendedService} instance.
   *
   * @param serviceCatalog The serviceCatalog of this {@link BckndExtendedService}
   */
  public void setServiceCatalog(
      @Nonnull final List<BckndServiceServiceCatalogItem> serviceCatalog) {
    this.serviceCatalog = serviceCatalog;
  }

  /**
   * Set the status of this {@link BckndExtendedService} instance and return the same instance.
   *
   * @param status aggregated status of the service
   * @return The same instance of this {@link BckndExtendedService} class
   */
  @Nonnull
  public BckndExtendedService status(@Nonnull final StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * aggregated status of the service
   *
   * @return status The status of this {@link BckndExtendedService} instance.
   */
  @Nonnull
  public StatusEnum getStatus() {
    return status;
  }

  /**
   * Set the status of this {@link BckndExtendedService} instance.
   *
   * @param status aggregated status of the service
   */
  public void setStatus(@Nonnull final StatusEnum status) {
    this.status = status;
  }

  /**
   * Set the statusMessage of this {@link BckndExtendedService} instance and return the same
   * instance.
   *
   * @param statusMessage status message
   * @return The same instance of this {@link BckndExtendedService} class
   */
  @Nonnull
  public BckndExtendedService statusMessage(@Nonnull final String statusMessage) {
    this.statusMessage = statusMessage;
    return this;
  }

  /**
   * status message
   *
   * @return statusMessage The statusMessage of this {@link BckndExtendedService} instance.
   */
  @Nonnull
  public String getStatusMessage() {
    return statusMessage;
  }

  /**
   * Set the statusMessage of this {@link BckndExtendedService} instance.
   *
   * @param statusMessage status message
   */
  public void setStatusMessage(@Nonnull final String statusMessage) {
    this.statusMessage = statusMessage;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndExtendedService}.
   *
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndExtendedService} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "BckndExtendedService has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndExtendedService} instance. If the map
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
    final BckndExtendedService bckndExtendedService = (BckndExtendedService) o;
    return Objects.equals(this.cloudSdkCustomFields, bckndExtendedService.cloudSdkCustomFields)
        && Objects.equals(this.name, bckndExtendedService.name)
        && Objects.equals(this.description, bckndExtendedService.description)
        && Objects.equals(this.url, bckndExtendedService.url)
        && Objects.equals(this.brokerSecret, bckndExtendedService.brokerSecret)
        && Objects.equals(this.capabilities, bckndExtendedService.capabilities)
        && Objects.equals(this.serviceCatalog, bckndExtendedService.serviceCatalog)
        && Objects.equals(this.status, bckndExtendedService.status)
        && Objects.equals(this.statusMessage, bckndExtendedService.statusMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        name,
        description,
        url,
        brokerSecret,
        capabilities,
        serviceCatalog,
        status,
        statusMessage,
        cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndExtendedService {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    brokerSecret: ").append(toIndentedString(brokerSecret)).append("\n");
    sb.append("    capabilities: ").append(toIndentedString(capabilities)).append("\n");
    sb.append("    serviceCatalog: ").append(toIndentedString(serviceCatalog)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    statusMessage: ").append(toIndentedString(statusMessage)).append("\n");
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

  /** Create a new {@link BckndExtendedService} instance. No arguments are required. */
  public static BckndExtendedService create() {
    return new BckndExtendedService();
  }
}
