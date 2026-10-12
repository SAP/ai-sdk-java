## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- [OpenAI] Removed the deprecated `OpenAiClient` methods `chatCompletion(String)`, `chatCompletion(OpenAiChatCompletionParameters)`, `streamChatCompletionDeltas(OpenAiChatCompletionParameters)` and `embedding(OpenAiEmbeddingParameters)`, along with the legacy `com.sap.ai.sdk.foundationmodels.openai.model` package. Use `chatCompletion(OpenAiChatCompletionRequest)`, `streamChatCompletionDeltas(OpenAiChatCompletionRequest)` and `embedding(OpenAiEmbeddingRequest)` instead.
- [OpenAI] Removed the deprecated `OpenAiClient.withApiVersion(String)` method. The API version is now set internally. There is no replacement as targeting a specific API version is no longer supported.

### ✨ New Functionality

- [OpenAI] Added new models `GPT_6_ASTRA`, `GPT_6_LUNA`, `GPT_61_SOL` to `OpenAiModel`.
- [Orchestration] Added new models `GPT_6_ASTRA`, `GPT_6_LUNA`, `GPT_61_SOL` and `NOVA_CORE` to `OrchestrationModel`.
- [RPT] Added new model `SAP_RPT_TS` for Time Series for forecasting to `RptModel`.

### 📈 Improvements

-

### 🐛 Fixed Issues

-
