## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- [OpenAI] Removed the deprecated `OpenAiClient` methods `chatCompletion(String)`, `chatCompletion(OpenAiChatCompletionParameters)`, `streamChatCompletionDeltas(OpenAiChatCompletionParameters)` and `embedding(OpenAiEmbeddingParameters)`, along with the legacy `com.sap.ai.sdk.foundationmodels.openai.model` package. Use `chatCompletion(OpenAiChatCompletionRequest)`, `streamChatCompletionDeltas(OpenAiChatCompletionRequest)` and `embedding(OpenAiEmbeddingRequest)` instead.
- [OpenAI] Removed the deprecated `OpenAiClient.withApiVersion(String)` method. The API version is now set internally. There is no replacement as targeting a specific API version is no longer supported.
- [Orchestration] Removed the deprecated `OrchestrationTemplate.withTemplate(List<ChatMessage>)` method. Use `withMessages(Message...)` instead.

### ✨ New Functionality

- [Orchestration] Added new models `GPT_51`, `GPT_6_ASTRA`, `GPT_6_LUNA`, `GPT_61_SOL` to `OrchestrationModel`.
- [OpenAI] Added new models `GPT_51`, `GPT_6_ASTRA`, `GPT_6_LUNA`, `GPT_61_SOL` to `OpenAiModel`.

### 📈 Improvements

- [Core] Added `AiCoreService.queryModels()` convenience methods to make model discovery more visible. Both the scenario ID (`"foundation-models"`) and resource group (`"default"`) are pre-filled by default. Use `queryModels(scenarioId, resourceGroup)` to specify custom values.

### 🐛 Fixed Issues

-
