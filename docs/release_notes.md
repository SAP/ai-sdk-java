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

- [OpenAI] Support for OpenAI Realtime API with the new OpenAiRealtimeClient. The following cases are currently supported:
  - `textToSpeech`
  - `speechToSpeech`
- [OpenAI] You can now add multiple custom headers to an `OpenAiClient` at once via `.withHeaders()`.
- [OpenAI] Added `GPT_56_LUNA`, `GPT_56_SOL` and `GPT_56_TERRA` to model list in `OpenAiModel`.
- [Orchestration] Added `QWEN_3_7_MAX`, `QWEN_3_7_PLUS`, `ALIBABA_TEXT_EMBEDDING_4`, `GEMINI_3_1_PRO_PREVIEW_EA`, `MISTRAL_MEDIUM`, `GPT_56_LUNA`, `GPT_56_SOL` and `GPT_56_TERRA` to model list in `OrchestrationAiModel`.
- [Orchestration] Reasoning content is now supported for users through `OrchestrationChatResponse.getReasoningText()` and `OrchestrationChatCompletionDelta.getDeltaReasoningText()` to see the thinking processes when using the reasoning models.
- [Orchestration] Added support for prompt caching for new Claude models. The following messages are cacheable:
  - `SystemMessage`,
  - `UserMessage`,
  - `ToolMessage`.
  - Refer to documentation for the models supporting caching.

### 📈 Improvements

-

### 🐛 Fixed Issues

- [Orchestration] Some `OrchestrationClientException` were reported as `OrchestrationFilterException.Input`.
