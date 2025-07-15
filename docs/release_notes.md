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
  - document grounding

- [Orchestration] Interfaces with only one implementation were reduced.
  - As a result, the accessors for fields `OrchestrationModuleConfig.inputTranslationConfig` and `OrchestrationModuleConfig.outputTranslationConfig` now handle the implementing class explicitly.
  - The same applies to helper methods `DpiMasking#createConfig()` and `MaskingProvider#createConfig()`.
- [Orchestration] `OrchestrationTemplate.withTemplate()` has been deprecated. Please use `OrchestrationTemplate.withTemplateMessages()` instead.
- [Orchestration] The method `createConfig()` is removed from `ContentFilter`, `AzureContentFilter` and `LlamaGuardFilter` and is replaced by `createInputFilterConfig()` and `createOutputFilterConfig()`.
- [Orchestration] Deprecated : `LLAMA3_1_70B_INSTRUCT`, `CLAUDE_3_SONNET`, `TITAN_TEXT_LITE`, `TITAN_TEXT_EXPRESS`, `GPT_4`, `GPT_4_0613`, `MIXTRAL_8X7B_INSTRUCT_V01`.
    - `GPT_4` and `GPT_4_0613` are replaced by : `GPT_40`or `GPT_41`.
    - `CLAUDE_3_SONNET` is replaced by `CLAUDE_4_SONNET`.
    - `MIXTRAL_8X7B_INSTRUCT_V01` is replaced by `MISTRAL_SMALL_INSTRUCT`.
- [OpenAI] Deprecated : `GPT_4`, `GPT_4_0613`.
  - `GPT_4` and `GPT_4_0613` are replaced by : `GPT_40`or `GPT_41`.

- [Prompt Registry] Resource group has been added as a optional parameter to all endpoints. Set it to `"default"` if it was not set before. Examples:
  - `client.importPromptTemplate(File)` --> `client.importPromptTemplate("default", File)`.
  - `client.parsePromptTemplateById(id, false, inputParams)` --> `client.parsePromptTemplateById(id, "default", false, inputParams)`.

- [Document Grounding] All classes with `Retrieval` have been renamed to fix the typo
  - for example: `RetievalSearchResults` has been renamed to `RetrievalSearchResults`
- [Document Grounding] `PipelinesApi#getAllPipelines()` and `PipelinesApi#getPipelineById()` now any of these 3 classes implementing the `GetPipeline` interface:
  - `MSSharePointPipelineGetResponse`, `S3PipelineGetResponse` and `SFTPPipelineGetResponse`

### ✨ New Functionality

- [Orchestration] Added support for [transforming a JSON output into an entity](https://sap.github.io/ai-sdk/docs/java/orchestration/chat-completion#json_schema)
- [Orchestration] Added `AzureContentFilter#promptShield()` available for input filtering.
- [Orchestration] Added new models for `OrchestrationAiModel`: `GEMINI_2_5_FLASH`, `GEMINI_2_5_PRO`, `ALEPHALPHA_PHARIA_1_7B_CONTROL`, `OPENAI_O4_MINI`, `CLAUDE_4_OPUS`, `CLAUDE_4_SONNET`, `OPENAI_O3`.

### 📈 Improvements

-

### 🐛 Fixed Issues

- [Orchestration] Resolved duplicate JSON property issue, enabling Anthropic Claude chat completions.
