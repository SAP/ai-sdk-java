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
- [Orchestration] The method `createConfig()` is removed from all `ContentFilter` types and replaced by `createInputFilterConfig()` and `createOutputFilterConfig()`.

### ✨ New Functionality

- [Orchestration] `promptShield` available in `AzureContentFilter` for input filtering.

### 📈 Improvements

-

### 🐛 Fixed Issues

-
