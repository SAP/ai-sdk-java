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
- [Orchestration] Deprecated : `LLAMA3_1_70B_INSTRUCT`, `CLAUDE_3_SONNET`, `TITAN_TEXT_LITE`, `TITAN_TEXT_EXPRESS`, `GPT_4`, `GPT_4_0613`.
  -`GPT_4` and `GPT_4_0613` are replaced by : `GPT_40`or `GPT_41`.
  - `CLAUDE_3_SONNET` is replaced by `CLAUDE_4_SONNET`.

### ✨ New Functionality

- [Orchestration] Added support for [transforming a JSON output into an entity](https://sap.github.io/ai-sdk/docs/java/orchestration/chat-completion#json_schema)
- [Orchestration] Added new models for `OrchestrationAiModel`: `GEMINI_2_5_FLASH`, `GEMINI_2_5_PRO`, `ALEPHALPHA_PHARIA_1_7B_CONTROL`, `OPENAI_O4_MINI`, `CLAUDE_4_OPUS`, `CLAUDE_4_SONNET`, `OPENAI_O3`.

### 📈 Improvements

-

### 🐛 Fixed Issues

- [Orchestration] Resolved duplicate JSON property issue, enabling Anthropic Claude chat completions.
