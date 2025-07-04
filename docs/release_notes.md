## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- The old OpenAI client (v1.0.0) is being deprecated in favor of the new OpenAI client (v1.4.0).
  [See the documentation for more details](https://sap.github.io/ai-sdk/docs/java/foundation-models/openai/chat-completion)
- Generated classes for the following service specifications are subject to change:
  - core
  - openai
  - orchestration
  
  Interfaces with only one implementation were reduced.
  As a result, the accessors for fields `OrchestrationModuleConfig.inputTranslationConfig` and `OrchestrationModuleConfig.outputTranslationConfig` now handle the implementing class explicitly.
  The same applies to helper methods `DpiMasking#createConfig()` and `MaskingProvider#createConfig()`.
- [Orchestration] `OrchestrationTemplate.withTemplate()` has been deprecated. Please use `OrchestrationTemplate.withTemplateMessages()` instead.

### ✨ New Functionality

- [Orchestration] Added support for [transforming a JSON output into an entity](https://sap.github.io/ai-sdk/docs/java/orchestration/chat-completion#json_schema)
- [Orchestration] Added new models for `OrchestrationAiModel`: `MISTRAL_SMALL_INSTRUCT`, `GPT_41_MINI`, `GPT_41`, `GPT_41_NANO`

### 📈 Improvements

-

### 🐛 Fixed Issues

- [Orchestration] Resolved duplicate JSON property issue, enabling Anthropic Claude chat completions.
