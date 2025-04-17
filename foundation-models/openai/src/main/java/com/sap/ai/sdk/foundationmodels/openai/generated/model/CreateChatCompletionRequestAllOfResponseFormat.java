/*
 * Azure OpenAI Service API
 * Azure OpenAI APIs for completions and search
 *
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.sap.ai.sdk.foundationmodels.openai.generated.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.annotations.Beta;

/**
 * An object specifying the format that the model must output. Compatible with
 * [GPT-4o](https://learn.microsoft.com/en-us/azure/ai-services/openai/concepts/models#gpt-4-and-gpt-4-turbo-models),
 * [GPT-4o
 * mini](https://learn.microsoft.com/en-us/azure/ai-services/openai/concepts/models#gpt-4-and-gpt-4-turbo-models),
 * [GPT-4
 * Turbo](https://learn.microsoft.com/en-us/azure/ai-services/openai/concepts/models#gpt-4-and-gpt-4-turbo-models)
 * and all
 * [GPT-3.5](https://learn.microsoft.com/en-us/azure/ai-services/openai/concepts/models#gpt-35)
 * Turbo models newer than &#x60;gpt-3.5-turbo-1106&#x60;. Setting to &#x60;{ \&quot;type\&quot;:
 * \&quot;json_schema\&quot;, \&quot;json_schema\&quot;: {...} }&#x60; enables Structured Outputs
 * which guarantees the model will match your supplied JSON schema. Setting to &#x60;{
 * \&quot;type\&quot;: \&quot;json_object\&quot; }&#x60; enables JSON mode, which guarantees the
 * message the model generates is valid JSON. **Important:** when using JSON mode, you **must** also
 * instruct the model to produce JSON yourself via a system or user message. Without this, the model
 * may generate an unending stream of whitespace until the generation reaches the token limit,
 * resulting in a long-running and seemingly \&quot;stuck\&quot; request. Also note that the message
 * content may be partially cut off if &#x60;finish_reason&#x3D;\&quot;length\&quot;&#x60;, which
 * indicates the generation exceeded &#x60;max_tokens&#x60; or the conversation exceeded the max
 * context length.
 */
@Beta
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
  @JsonSubTypes.Type(value = ResponseFormatJsonObject.class),
  @JsonSubTypes.Type(value = ResponseFormatJsonSchema.class),
  @JsonSubTypes.Type(value = ResponseFormatText.class),
})
public interface CreateChatCompletionRequestAllOfResponseFormat {}
