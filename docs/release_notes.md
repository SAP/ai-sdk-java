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
- [Orchestration] The method `createConfig()` is removed from `ContentFilter`, `AzureContentFilter` and `LlamaGuardFilter` and is replaced by `createInputFilterConfig()` and `createOutputFilterConfig()`.

- [Prompt Registry] Resource group has been added as a optional parameter to all endpoints. Set it to `"default"` if it was not set before. Examples:
  - `client.importPromptTemplate(File)` --> `client.importPromptTemplate("default", File)`.
  - `client.parsePromptTemplateById(id, false, inputParams)` --> `client.parsePromptTemplateById(id, "default", false, inputParams)`.

### ✨ New Functionality

- [Orchestration] Added support for [transforming a JSON output into an entity](https://sap.github.io/ai-sdk/docs/java/orchestration/chat-completion#json_schema)
- [Orchestration] Added `AzureContentFilter#promptShield()` available for input filtering.

### 📈 Improvements

-

### 🐛 Fixed Issues

- [Orchestration] Resolved duplicate JSON property issue, enabling Anthropic Claude chat completions.
