## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

-

### ✨ New Functionality

- [Orchestration] Added new models `GEMINI_3_8_FLASH`, `GPT_51`, `GPT_6_ASTRA`, `GPT_6_LUNA`, `GPT_61_SOL`, and `QWEN_3_8_MAX` to `OrchestrationModel`.
- [OpenAI] Added new models `GPT_51`, `GPT_6_ASTRA`, `GPT_6_LUNA`, `GPT_61_SOL` to `OpenAiModel`.
- [Spring AI] Chat completion calls via the Spring AI integration now can have multiple module configs to support fallback modules as well.

### 📈 Improvements

- [Core] Added `CoreApi.availableAiModels()` convenience methods to make model discovery more visible. Both the scenario ID (`"foundation-models"`) and resource group (`"default"`) are pre-filled by default. Use `availableAiModels(scenarioId, resourceGroup)` to specify custom values.

### 🐛 Fixed Issues

- [Orchestration] Token usage reported in `ChatResponse` metadata now correctly accumulates across all internal tool-call rounds when using `OrchestrationChatModel`.
