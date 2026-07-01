## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- [Prompt Registry] In `OrchestrationConfigPostRequest` and similar classes to create prompt registry requests, use the following replacements:

  | Aspect | Before | After |
  |--------|--------|-------|
  | Orchestration config | `OrchestrationConfig` | `PromptRegistryOrchestrationConfig` |
  | Orchestration config modules | `OrchestrationConfigModules` | `PromptRegistryOrchestrationConfigModules` |
  | Module configs | `ModuleConfigs` | `PartialModuleConfigs` |
  | Prompt templating module config | `PromptTemplatingModuleConfig` | `PartialPromptTemplatingModuleConfig` |

### ✨ New Functionality

- [Batch] Added a new `BatchesApi` client to access the [Batch Service API](https://github.tools.sap/AI/llm-batch-service).
- Support for OpenAI Responses API with the new `AiCoreOpenAiClient`.
  The following endpoints are currently supported:

- create()
- createStreaming()
- retrieve()
- delete()
- [Orchestration] Added `CLAUDE_4_8_OPUS`, `QWEN_3_MAX`, `QWEN_3_6_PLUS` and `QWEN_3_6_FLASH` to the model list in `OrchestrationAiModel`.

### 📈 Improvements

-

### 🐛 Fixed Issues

-
