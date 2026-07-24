## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- [Orchestration] Removed `OrchestrationAiModel COHERE_RERANKER`. This model was supported neither by Orchestration nor by AI SDK.
- [Prompt Registry] Added optional arguments `$top` and `$skip` to the following methods:
  - `OrchestrationConfigsApi.listOrchestrationConfigHistory`
  - `OrchestrationConfigsApi.listOrchestrationConfigs`
  - `PromptTemplatesApi.listPromptTemplateHistory`
  - `PromptTemplatesApi.listPromptTemplates`

### ✨ New Functionality

- [Orchestration] Added `QWEN_3_7_MAX`, `QWEN_3_7_PLUS` and `ALIBABA_TEXT_EMBEDDING_4` to model list in `OrchestrationAiModel`.
- [Orchestration] Marked as deprecated `QWEN_3_MAX` model (retired, use newer models instead, e.g. `QWEN_3_7_MAX`).
- [OpenAI] You can now add multiple custom headers to an `OpenAiClient` at once via `.withHeaders()`.
- [Orchestration] Added `GEMINI_3_1_PRO_PREVIEW_EA` to model list in `OrchestrationAiModel`.

### 📈 Improvements

-

### 🐛 Fixed Issues

- [Orchestration] Some `OrchestrationClientException` were reported as `OrchestrationFilterException.Input`.
