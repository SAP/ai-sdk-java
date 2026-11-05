## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- The SDK is verified to build against these minimum supported dependency versions: SAP Cloud SDK `5.28.0`, Jackson `2.18.0`, Apache HttpComponents `5.2.1`, SLF4J `2.0.0`, Spring AI `2.0.0` (optional).

### ✨ New Functionality

- [Orchestration] Added new model `SAP_ABAP_2` to `OrchestrationAiModel`.

### 📈 Improvements

- [Core] Added `CoreApi.availableAiModels()` convenience methods to make model discovery more visible. Both the scenario ID (`"foundation-models"`) and resource group (`"default"`) are pre-filled by default. Use `availableAiModels(scenarioId, resourceGroup)` to specify custom values.

### 🐛 Fixed Issues

- [Orchestration] Token usage reported in `ChatResponse` metadata now correctly accumulates across all internal tool-call rounds when using `OrchestrationChatModel`.
- [Orchestration] `OrchestrationClient.executeRequest()` is now `protected` instead of `package-private`. It was `public` in v1.
