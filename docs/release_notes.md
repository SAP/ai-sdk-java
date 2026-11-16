## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- [Orchestration] Structured output with the Spring AI integration is now configured via Spring AI's `useProviderStructuredOutput()` on the `entity(...)` call. See the [migration guide](https://sap.github.io/ai-sdk/docs/java/migration-guide) for the v1 → v2 change.

### ✨ New Functionality

- [Orchestration] Added new models `SAP_ABAP_2` and `QWEN_3_8_FLASH` to `OrchestrationAiModel`.

### 📈 Improvements

- [Core] Added `CoreApi.availableAiModels()` convenience methods to make model discovery more visible. Both the scenario ID (`"foundation-models"`) and resource group (`"default"`) are pre-filled by default. Use `availableAiModels(scenarioId, resourceGroup)` to specify custom values.
- [Orchestration] Added `ResponseJsonSchema.fromString(String jsonSchema, String name)` to build a response schema from a raw JSON schema string.
- [Orchestration] [OpenAI] [`OrchestrationClientException.getHttpRequest()`](https://sap.github.io/ai-sdk/docs/java/orchestration/chat-completion#exception-and-error-handling) and [`OpenAiClientException.getHttpRequest()`](https://sap.github.io/ai-sdk/docs/java/foundation-models/openai/chat-completion#exception-and-error-handling) now return the request that caused the exception, if available. This is useful for debugging and logging purposes.

### 🐛 Fixed Issues

- [Orchestration] Token usage reported in `ChatResponse` metadata now correctly accumulates across all internal tool-call rounds when using `OrchestrationChatModel`.
- [Orchestration] `OrchestrationClient.executeRequest()` is now `protected` instead of `package-private`. It was `public` in v1.
