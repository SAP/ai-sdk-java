## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

-

### ✨ New Functionality

- [RPT] Introducing `RptClient` for Tabular AI backed by SAP RPT models `SAP_RPT_1_SMALL` and `SAP_RPT_1_LARGE`.
    - Added support for Parquet file input with `RptClient#tableCompletion` for larger datasets.
- [Orchestration] Added new API `OrchestrationTemplateReference#withScope` to support prompt templates with resource-group scope.
- [Orchestration] Chat completion calls now can have multiple module configs to support [fallback modules](https://sap.github.io/ai-sdk/docs/java/orchestration/chat-completion).
- [Orchestration] Deprecated `ALEPHALPHA_PHARIA_1_7B_CONTROL` model from `OrchestrationAiModel` with replacement model `MISTRAL_SMALL_INSTRUCT`.
- [Orchestration] Deprecated `GPT_4O_MINI` model from `OrchestrationAiModel` with replacement model `GPT_5_MINI`.
- [Orchestration] Deprecated `GPT_4O_MINI` model from `OpenAiModel` with replacement model `GPT_5_MINI`.
- [Orchestration] Deprecated models `GEMINI_2_0_FLASH`, `GEMINI_2_0_FLASH_LITE` and `CLAUDE_3_7_SONNET` from `OrchestrationAiModel`.
- [Orchestration] Deprecated `DALL_E_3` model from `OpenAiModel`.
- [Orchestration] Added new models `CLAUDE_4_6_SONNET` and `CLAUDE_4_6_OPUS` in `OrchestrationAiModel`.

### 📈 Improvements

- [Orchestration] `AzureContentFilter.protectedMaterialCode()` is now supported as an output content filtering module .

### 🐛 Fixed Issues

-
