

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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * BckndEvent
 */

// CHECKSTYLE:OFF
public class BckndEvent 
// CHECKSTYLE:ON
{
  @JsonProperty("tenantId")
  private String tenantId;

  /**
   * Gets or Sets action
   */
  public enum ActionEnum {
    /**
    * The PROVISION option of this BckndEvent
    */
    PROVISION("PROVISION"),
    
    /**
    * The DEPROVISION option of this BckndEvent
    */
    DEPROVISION("DEPROVISION");

    private String value;

    ActionEnum(String value) {
      this.value = value;
    }

    /**
    * Get the value of the enum
    * @return The enum value
    */
    @JsonValue
    @Nonnull public String getValue() {
      return value;
    }

    /**
    * Get the String value of the enum value.
    * @return The enum value as String
    */
    @Override
    @Nonnull public String toString() {
      return String.valueOf(value);
    }

    /**
    * Get the enum value from a String value
    * @param value The String value
    * @return The enum value of type BckndEvent
    */
    @JsonCreator
    @Nonnull public static ActionEnum fromValue(@Nonnull final String value) {
      for (ActionEnum b : ActionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("action")
  private ActionEnum action;

  /**
   * Gets or Sets state
   */
  public enum StateEnum {
    /**
    * The SUCCESSFUL option of this BckndEvent
    */
    SUCCESSFUL("SUCCESSFUL"),
    
    /**
    * The FAILED option of this BckndEvent
    */
    FAILED("FAILED"),
    
    /**
    * The PENDING option of this BckndEvent
    */
    PENDING("PENDING");

    private String value;

    StateEnum(String value) {
      this.value = value;
    }

    /**
    * Get the value of the enum
    * @return The enum value
    */
    @JsonValue
    @Nonnull public String getValue() {
      return value;
    }

    /**
    * Get the String value of the enum value.
    * @return The enum value as String
    */
    @Override
    @Nonnull public String toString() {
      return String.valueOf(value);
    }

    /**
    * Get the enum value from a String value
    * @param value The String value
    * @return The enum value of type BckndEvent
    */
    @JsonCreator
    @Nonnull public static StateEnum fromValue(@Nonnull final String value) {
      for (StateEnum b : StateEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("state")
  private StateEnum state;

  @JsonProperty("description")
  private String description;

  @JsonProperty("createdAt")
  private OffsetDateTime createdAt;

  @JsonAnySetter
  @JsonAnyGetter
  private final Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

   /**
   * Set the tenantId of this {@link BckndEvent} instance and return the same instance.
   *
   * @param tenantId  tenant id
   * @return The same instance of this {@link BckndEvent} class
   */
   @Nonnull public BckndEvent tenantId(@Nonnull final String tenantId) {
    this.tenantId = tenantId;
    return this;
  }

   /**
   * tenant id
   * @return tenantId  The tenantId of this {@link BckndEvent} instance.
  **/
  @Nonnull public String getTenantId() {
    return tenantId;
  }

  /**
  * Set the tenantId of this {@link BckndEvent} instance.
  *
  * @param tenantId  tenant id
  */
  public void setTenantId( @Nonnull final String tenantId) {
    this.tenantId = tenantId;
  }

   /**
   * Set the action of this {@link BckndEvent} instance and return the same instance.
   *
   * @param action  The action of this {@link BckndEvent}
   * @return The same instance of this {@link BckndEvent} class
   */
   @Nonnull public BckndEvent action(@Nonnull final ActionEnum action) {
    this.action = action;
    return this;
  }

   /**
   * Get action
   * @return action  The action of this {@link BckndEvent} instance.
  **/
  @Nonnull public ActionEnum getAction() {
    return action;
  }

  /**
  * Set the action of this {@link BckndEvent} instance.
  *
  * @param action  The action of this {@link BckndEvent}
  */
  public void setAction( @Nonnull final ActionEnum action) {
    this.action = action;
  }

   /**
   * Set the state of this {@link BckndEvent} instance and return the same instance.
   *
   * @param state  The state of this {@link BckndEvent}
   * @return The same instance of this {@link BckndEvent} class
   */
   @Nonnull public BckndEvent state(@Nonnull final StateEnum state) {
    this.state = state;
    return this;
  }

   /**
   * Get state
   * @return state  The state of this {@link BckndEvent} instance.
  **/
  @Nonnull public StateEnum getState() {
    return state;
  }

  /**
  * Set the state of this {@link BckndEvent} instance.
  *
  * @param state  The state of this {@link BckndEvent}
  */
  public void setState( @Nonnull final StateEnum state) {
    this.state = state;
  }

   /**
   * Set the description of this {@link BckndEvent} instance and return the same instance.
   *
   * @param description  describes the event state
   * @return The same instance of this {@link BckndEvent} class
   */
   @Nonnull public BckndEvent description(@Nonnull final String description) {
    this.description = description;
    return this;
  }

   /**
   * describes the event state
   * @return description  The description of this {@link BckndEvent} instance.
  **/
  @Nonnull public String getDescription() {
    return description;
  }

  /**
  * Set the description of this {@link BckndEvent} instance.
  *
  * @param description  describes the event state
  */
  public void setDescription( @Nonnull final String description) {
    this.description = description;
  }

   /**
   * Set the createdAt of this {@link BckndEvent} instance and return the same instance.
   *
   * @param createdAt  The createdAt of this {@link BckndEvent}
   * @return The same instance of this {@link BckndEvent} class
   */
   @Nonnull public BckndEvent createdAt(@Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Get createdAt
   * @return createdAt  The createdAt of this {@link BckndEvent} instance.
  **/
  @Nonnull public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  /**
  * Set the createdAt of this {@link BckndEvent} instance.
  *
  * @param createdAt  The createdAt of this {@link BckndEvent}
  */
  public void setCreatedAt( @Nonnull final OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Get the names of the unrecognizable properties of the {@link BckndEvent}.
   * @return The set of properties names
   */
  @JsonIgnore
  @Nonnull
  public Set<String> getCustomFieldNames() {
    return cloudSdkCustomFields.keySet();
  }

  /**
   * Get the value of an unrecognizable property of this {@link BckndEvent} instance.
   * @param name  The name of the property
   * @return The value of the property
   * @throws NoSuchElementException  If no property with the given name could be found.
   */
  @Nullable
  public Object getCustomField(@Nonnull final String name) throws NoSuchElementException {
    if( !cloudSdkCustomFields.containsKey(name) ) {
        throw new NoSuchElementException("BckndEvent has no field with name '" + name + "'.");
    }
    return cloudSdkCustomFields.get(name);
  }

  /**
   * Set an unrecognizable property of this {@link BckndEvent} instance. If the map previously contained a mapping
   * for the key, the old value is replaced by the specified value.
   * @param customFieldName The name of the property
   * @param customFieldValue The value of the property
   */
  @JsonIgnore
  public void setCustomField( @Nonnull String customFieldName, @Nullable Object customFieldValue )
  {
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
    final BckndEvent bckndEvent = (BckndEvent) o;
    return Objects.equals(this.cloudSdkCustomFields, bckndEvent.cloudSdkCustomFields) &&
        Objects.equals(this.tenantId, bckndEvent.tenantId) &&
        Objects.equals(this.action, bckndEvent.action) &&
        Objects.equals(this.state, bckndEvent.state) &&
        Objects.equals(this.description, bckndEvent.description) &&
        Objects.equals(this.createdAt, bckndEvent.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tenantId, action, state, description, createdAt, cloudSdkCustomFields);
  }

  @Override
  @Nonnull public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class BckndEvent {\n");
    sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    cloudSdkCustomFields.forEach((k,v) -> sb.append("    ").append(k).append(": ").append(toIndentedString(v)).append("\n"));
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(final java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

