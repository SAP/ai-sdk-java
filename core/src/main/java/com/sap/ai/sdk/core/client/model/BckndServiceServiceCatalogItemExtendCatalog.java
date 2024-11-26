/*
 * AI Core
 * Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a batch job, for example to pre-process or train your models, or perform batch inference.  Serve inference requests of trained models. Deploy а trained machine learning model as a web service to serve inference requests with high performance.  Register your own Docker registry, synchronize your AI content from your own git repository, and register your own object store for training data and trained models.
 *
 * The version of the OpenAPI document: 2.36.0
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** BckndServiceServiceCatalogItemExtendCatalog */
// CHECKSTYLE:OFF
public class BckndServiceServiceCatalogItemExtendCatalog
// CHECKSTYLE:ON
{
  @JsonProperty("bindable")
  private Boolean bindable;

  @JsonProperty("description")
  private String description;

  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("plans")
  private List<BckndServiceServicePlanItem> plans = new ArrayList<>();

  @JsonAnySetter @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  /** Default constructor for BckndServiceServiceCatalogItemExtendCatalog. */
  protected BckndServiceServiceCatalogItemExtendCatalog() {}

  /**
   * Set the bindable of this {@link BckndServiceServiceCatalogItemExtendCatalog} instance and
   * return the same instance.
   *
   * @param bindable if the service is bindable
   * @return The same instance of this {@link BckndServiceServiceCatalogItemExtendCatalog} class
   */
  @Nonnull
  public BckndServiceServiceCatalogItemExtendCatalog bindable(@Nullable final Boolean bindable) {
    this.bindable = bindable;
    return this;
  }

  /**
   * if the service is bindable
   *
   * @return bindable The bindable of this {@link BckndServiceServiceCatalogItemExtendCatalog}
   *     instance.
   */
  @Nonnull
  public Boolean isBindable() {
    return bindable;
  }

  /**
   * Set the bindable of this {@link BckndServiceServiceCatalogItemExtendCatalog} instance.
   *
   * @param bindable if the service is bindable
   */
  public void setBindable(@Nullable final Boolean bindable) {
    this.bindable = bindable;
  }

  /**
   * Set the description of this {@link BckndServiceServiceCatalogItemExtendCatalog} instance and
   * return the same instance.
   *
   * @param description description of the service
   * @return The same instance of this {@link BckndServiceServiceCatalogItemExtendCatalog} class
   */
  @Nonnull
  public BckndServiceServiceCatalogItemExtendCatalog description(
      @Nullable final String description) {
    this.description = description;
    return this;
  }

  /**
   * description of the service
   *
   * @return description The description of this {@link BckndServiceServiceCatalogItemExtendCatalog}
   *     instance.
   */
  @Nonnull
  public String getDescription() {
    return description;
  }

  /**
   * Set the description of this {@link BckndServiceServiceCatalogItemExtendCatalog} instance.
   *
   * @param description description of the service
   */
  public void setDescription(@Nullable final String description) {
    this.description = description;
  }

  /**
   * Set the id of this {@link BckndServiceServiceCatalogItemExtendCatalog} instance and return the
   * same instance.
   *
   * @param id id of the service
   * @return The same instance of this {@link BckndServiceServiceCatalogItemExtendCatalog} class
   */
  @Nonnull
  public BckndServiceServiceCatalogItemExtendCatalog id(@Nullable final String id) {
    this.id = id;
    return this;
  }

  /**
   * id of the service
   *
   * @return id The id of this {@link BckndServiceServiceCatalogItemExtendCatalog} instance.
   */
  @Nonnull
  public String getId() {
    return id;
  }

  /**
   * Set the id of this {@link BckndServiceServiceCatalogItemExtendCatalog} instance.
   *
   * @param id id of the service
   */
  public void setId(@Nullable final String id) {
    this.id = id;
  }

  /**
   * Set the name of this {@link BckndServiceServiceCatalogItemExtendCatalog} instance and return
   * the same instance.
   *
   * @param name name of the service
   * @return The same instance of this {@link BckndServiceServiceCatalogItemExtendCatalog} class
   */
  @Nonnull
  public BckndServiceServiceCatalogItemExtendCatalog name(@Nullable final String name) {
    this.name = name;
    return this;
  }

  /**
   * name of the service
   *
   * @return name The name of this {@link BckndServiceServiceCatalogItemExtendCatalog} instance.
   */
  @Nonnull
  public String getName() {
    return name;
  }

  /**
   * Set the name of this {@link BckndServiceServiceCatalogItemExtendCatalog} instance.
   *
   * @param name name of the service
   */
  public void setName(@Nullable final String name) {
    this.name = name;
  }

  /**
   * Set the plans of this {@link BckndServiceServiceCatalogItemExtendCatalog} instance and return
   * the same instance.
   *
   * @param plans The plans of this {@link BckndServiceServiceCatalogItemExtendCatalog}
   * @return The same instance of this {@link BckndServiceServiceCatalogItemExtendCatalog} class
   */
  @Nonnull
  public BckndServiceServiceCatalogItemExtendCatalog plans(
      @Nullable final List<BckndServiceServicePlanItem> plans) {
    this.plans = plans;
    return this;
  }

  /**
   * Add one plans instance to this {@link BckndServiceServiceCatalogItemExtendCatalog}.
   *
   * @param plansItem The plans that should be added
   * @return The same instance of type {@link BckndServiceServiceCatalogItemExtendCatalog}
   */
  @Nonnull
  public BckndServiceServiceCatalogItemExtendCatalog addPlansItem(
      @Nonnull final BckndServiceServicePlanItem plansItem) {
    if (this.plans == null) {
      this.plans = new ArrayList<>();
    }
    this.plans.add(plansItem);
    return this;
  }

  /**
   * Get plans
   *
   * @return plans The plans of this {@link BckndServiceServiceCatalogItemExtendCatalog} instance.
   */
  @Nonnull
  public List<BckndServiceServicePlanItem> getPlans() {
    return plans;
  }

  /**
   * Set the plans of this {@link BckndServiceServiceCatalogItemExtendCatalog} instance.
   *
   * @param plans The plans of this {@link BckndServiceServiceCatalogItemExtendCatalog}
   */
  public void setPlans(@Nullable final List<BckndServiceServicePlanItem> plans) {
    this.plans = plans;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link
   * BckndServiceServiceCatalogItemExtendCatalog}.
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
   * BckndServiceServiceCatalogItemExtendCatalog} instance.
   *
   * @param name The name of the property
   * @return The value of the property
   * @throws NoSuchElementException If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if (!cloudSdkCustomFields.containsKey(name)) {
      throw new NoSuchElementException(
          "BckndServiceServiceCatalogItemExtendCatalog has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndServiceServiceCatalogItemExtendCatalog}
   * instance. If the map previously contained a mapping for the key, the old value is replaced by
   * the specified value.
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
    final BckndServiceServiceCatalogItemExtendCatalog bckndServiceServiceCatalogItemExtendCatalog =
        (BckndServiceServiceCatalogItemExtendCatalog) o;
    return Objects.equals(
            this.cloudSdkCustomFields,
            bckndServiceServiceCatalogItemExtendCatalog.cloudSdkCustomFields)
        && Objects.equals(this.bindable, bckndServiceServiceCatalogItemExtendCatalog.bindable)
        && Objects.equals(this.description, bckndServiceServiceCatalogItemExtendCatalog.description)
        && Objects.equals(this.id, bckndServiceServiceCatalogItemExtendCatalog.id)
        && Objects.equals(this.name, bckndServiceServiceCatalogItemExtendCatalog.name)
        && Objects.equals(this.plans, bckndServiceServiceCatalogItemExtendCatalog.plans);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bindable, description, id, name, plans, cloudSdkCustomFields);
  }

  @Override
  @Nonnull
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndServiceServiceCatalogItemExtendCatalog {\n");
    sb.append("    bindable: ").append(toIndentedString(bindable)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    plans: ").append(toIndentedString(plans)).append("\n");
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
   * Create a new {@link BckndServiceServiceCatalogItemExtendCatalog} instance. No arguments are
   * required.
   */
  public static BckndServiceServiceCatalogItemExtendCatalog create() {
    return new BckndServiceServiceCatalogItemExtendCatalog();
  }
}
