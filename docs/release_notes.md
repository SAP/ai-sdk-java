## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- The **Spring AI** version has been increased from `1.0.0-M6` to the GA release `1.0.0`.
  - The `OrchestrationChatOptions` have been, replacing all references to `FunctionCallback` with `ToolCallback`.
  - Please follow the [official Spring AI upgrade guide](https://docs.spring.io/spring-ai/reference/upgrade-notes.html#upgrading-to-1-0-0-RC1) for further details.
  - The `@Beta` annotations on all classes related to Spring AI have been removed.
- [Orchestration] The `completion` api have been moved to the latest version `/v2/completions`
  - `LLMModuleConfig` is replaced by `LLMModelDetails` in `withLLmConfig` method of `OrchestrationModuleConfig` class.
  - `PromptTemplatingModuleConfigPrompt` replaces `TemplatingModuleConfig` in the `withTemplateConfig` method of `OrchestrationModuleConfig` class.
  - The generated model classes will reflect payload updates including restructuring of the module configurations and renaming of several fields.

### ✨ New Functionality


### 📈 Improvements

-

### 🐛 Fixed Issues

- OpenAi: Fix AssistantMessage Bug by now being able to send Assistant Messages using our API Client. 
