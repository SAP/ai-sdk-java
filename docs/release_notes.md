## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

-

### ✨ New Functionality

- [Orchestration] Added new models for `OrchestrationAiModel`: `SONAR`,`SONAR_PRO`, `GEMINI_2_5_FLASH_LITE`, `CLAUDE_4_5_HAIKU`.
- [Orchestration] Convenience for adding the `metadata_params` option to grounding calls.

### 📈 Improvements

- [Orchestration] Added new API `TranslationConfig#createInput` to extract input config.
- [Orchestration] Added new API `TranslationConfig#createOutput` to extract output config.

### 🐛 Fixed Issues

- [PromptRegistry] Fix deserialization of `response_format` in retrieved prompt templates.
