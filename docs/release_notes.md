## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- (optional) Spring AI requires version `1.1.0` (or higher).

### ✨ New Functionality

- [Orchestration] Added new models for `OrchestrationAiModel`: `SAP_ABAP_1`, `SONAR`,`SONAR_PRO`, `GEMINI_2_5_FLASH_LITE`, `CLAUDE_4_5_HAIKU`, `GPT_REALTIME`.
- [Orchestration] Convenience for adding the `metadata_params` option to grounding calls.
- [Orchestration] Added new models for `OrchestrationAiModel`: `COHERE_COMMAND_A_REASONING`, `NOVA_PREMIER`, `COHERE_RERANKER`.
- [Orchestration] Deprecated `DEEPSEEK_R1` model from `OrchestrationAiModel` with no replacement.

### 📈 Improvements

- [Orchestration] Added new API `TranslationConfig#translateInputTo` to extract input config.
- [Orchestration] Added new API `TranslationConfig#translateOutputTo` to extract output config.

### 🐛 Fixed Issues

- [PromptRegistry] Fix deserialization of `response_format` in retrieved prompt templates.
