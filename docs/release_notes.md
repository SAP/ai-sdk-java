## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

-

### ✨ New Functionality

- [Grounding] Added `GroundingClient.withHeader()`.
- [Orchestration] Added `GPT_52` model for `OrchestrationAiModel`.
- [OpenAi] Added `GPT_52` model from `OpenAiModel`.
- [Orchestration] Added `GEMINI_EMBEDDING` model for `OrchestrationEmbeddingModel`.

### 📈 Improvements

- [Orchestration] Added new API `TranslationConfig#applyToPlaceholders` and `TranslationConfig#applyToTemplateRoles` to support partial translation for a message.
- [RPT] `RptClient.tableCompletion()` GZIP compresses the request payload.

### 🐛 Fixed Issues

-
