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

- [OpenAI] You can now add multiple custom headers to an `OpenAiClient` at once via `.withHeaders()`.
- [Orchestration] Added `GEMINI_3_1_PRO_PREVIEW_EA` to model list in `OrchestrationAiModel`.
- [Orchestration] Reasoning content is now supported for users through `OrchestrationChatResponse.getReasoningText()` and `OrchestrationChatCompletionDelta.getDeltaReasoningText()` to see the thinking processes when using the reasoning models.

### 📈 Improvements

-

### 🐛 Fixed Issues

- [Orchestration] Some `OrchestrationClientException` were reported as `OrchestrationFilterException.Input`.
