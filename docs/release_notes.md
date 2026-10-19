## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- [OpenAI] Removed the deprecated `OpenAiClient` methods `chatCompletion(String)`, `chatCompletion(OpenAiChatCompletionParameters)`, `streamChatCompletionDeltas(OpenAiChatCompletionParameters)` and `embedding(OpenAiEmbeddingParameters)`, along with the legacy `com.sap.ai.sdk.foundationmodels.openai.model` package.
  Use `chatCompletion(OpenAiChatCompletionRequest)`, `streamChatCompletionDeltas(OpenAiChatCompletionRequest)` and `embedding(OpenAiEmbeddingRequest)` instead.
- [OpenAI] Removed the deprecated `OpenAiClient.withApiVersion(String)` method. The API version is now set internally. There is no replacement as targeting a specific API version is no longer supported.
- [Orchestration] Removed the deprecated `OrchestrationTemplate.withTemplate(List<ChatMessage>)` method. Use `withMessages(Message...)` instead.

#### Orchestration Migration Guide

| Previous API                                                                  | Replacement                                                                     |
|-------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| `TemplateConfig` / `OrchestrationTemplate` / `OrchestrationTemplateReference` | `PromptTemplate` / `LocalPromptTemplate` / `PromptTemplateReference`            |
| `OrchestrationPrompt`                                                         | `PromptTemplate.create(String)` or `PromptTemplate.create(Message, Message...)` |
| `OrchestrationTemplate.withTemplate(List<ChatMessage>)`                       | `LocalPromptTemplate.withMessages(Message...)`                                  |
| `OrchestrationPrompt.messageHistory(...)`                                     | `LocalPromptTemplate.withMessageHistory(...)`                                   |
| `OrchestrationConfigReference.withTemplateParameters(...)`                    | `withInputParameters(...)` on a completed reference                             |
| `OrchestrationChatOptions.getConfigWithCallbacks()`                           | `withCallbacks(LocalPromptTemplate)`, returning an enriched template            |
| `client.executeRequest(request)`                                            | `client.chatCompletion().lowLevel().request(request).execute()`                 |
| `client.streamChatCompletionDeltas(request)`                                 | `client.chatCompletion().lowLevel().request(request).executeStreamDeltas()`     |
| `client.embed(orchestrationEmbeddingRequest)`                                | `client.embeddings().request(orchestrationEmbeddingRequest).execute()`          |
| `client.embed(embeddingsPostRequest)`                                        | `client.embeddings().lowLevel().request(embeddingsPostRequest).execute()`        |

**Templates and execution**

Templates are no longer stored in `OrchestrationModuleConfig`. Combine the former template and prompt messages in order, then pass the result separately:

```diff
-var template = TemplateConfig.create()
-    .withMessages(Message.system("Translate into {{?language}}"));
-var prompt = new OrchestrationPrompt(parameters, Message.user("Hello"))
-    .messageHistory(history);
-var response = client.chatCompletion(prompt, config.withTemplateConfig(template));
+var template = PromptTemplate.create(
+    Message.system("Translate into {{?language}}"), Message.user("Hello"))
+    .withInputParameters(parameters)
+    .withMessageHistory(history);
+var response = client.chatCompletion()
+    .config(config)
+    .promptTemplate(template)
+    .execute();
```

- Replace `streamChatCompletion(prompt, config)` with the same builder ending in `executeStream()` (`Stream<String>`). Use `executeStreamDeltas()` for full deltas, including reasoning, tool calls, usage, and intermediate results. Close both kinds of streams with try-with-resources.
- Set defaults, tools, and response formats on the local template using `withDefaults(...)`, `withTools(...)`, `withJsonResponse()`, or `withJsonSchemaResponse(...)`. All `with...` methods return updated instances; retain their results.
- Supply at least one template message. Import templates with `PromptTemplate.fromJson(...)` or `fromYaml(...)`, handling `IOException` and nullable results.

**Registry references**

Complete prompt-template references with `andInputParameters(...)` and pass them to the same `.promptTemplate(template)` step:

```java
var template = PromptTemplate.reference().byId("template-id")
	.andInputParameters(parameters);
// Alternatively: reference().byScenario("translation").name("translator")
//     .version("0.0.1").andInputParameters(parameters)
```

Use `Map.of()` when no parameters are needed. Tenant scope is the default; set `.withScope(PromptTemplateReference.ScopeEnum.RESOURCE_GROUP)` after completing the reference if required. Prompt-template references currently do not support message history.

For a stored orchestration configuration, replace `chatCompletionUsingReference(...)` with `configReference(...)`; no separate config or template is needed:

```diff
 var reference = OrchestrationConfigReference.fromScenario("translation")
	 .name("translator-config").version("0.0.1")
-    .withTemplateParameters(parameters);
+    .andInputParameters(parameters);
-var response = client.chatCompletionUsingReference(reference);
+var response = client.chatCompletion().configReference(reference).execute();
```

For ID-based configs, use `OrchestrationConfigReference.fromId(id).withInputParameters(parameters)`. Both config-reference forms support `withMessageHistory(...)`, `executeStream()`, and `executeStreamDeltas()`.

**Spring AI callbacks**

`OrchestrationChatModel` applies registered tool callbacks automatically. Native SDK callers using the helper must pass `options.withCallbacks(template)` to `.promptTemplate(...)` and use `options.getConfig()` for the config. The helper adds tool definitions; it does not execute callbacks.

### ✨ New Functionality

- [Orchestration] Added new models `GPT_51`, `GPT_6_ASTRA`, `GPT_6_LUNA`, `GPT_61_SOL` to `OrchestrationModel`.
- [OpenAI] Added new models `GPT_51`, `GPT_6_ASTRA`, `GPT_6_LUNA`, `GPT_61_SOL` to `OpenAiModel`.

### 📈 Improvements

-

### 🐛 Fixed Issues

-
