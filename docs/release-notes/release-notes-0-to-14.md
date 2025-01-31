## 1.2.0 - January 30, 2025

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/tag/rel%2F1.2.0)

### 🔧 Compatibility Notes

- `SingleChatMessage`, as well as new `MultiChatMessage`, are now subtypes of new interface `ChatMessage`.
  Most variables or methods previously typed as `ChatMessage` in `model` package are now typed as `SingleChatMessage`. 
- Add missing `@Beta` annotations to all `com.sap.ai.sdk.core.client` and `com.sap.ai.sdk.core.model` classes.

### ✨ New Functionality

- New Orchestration features:
  - [Spring AI integration](../guides/SPRING_AI_INTEGRATION.md)
  - [Add Grounding configuration convenience](../guides/ORCHESTRATION_CHAT_COMPLETION.md#grounding)
  - Images are now supported as input in newly introduced `MultiChatMessage`.
  - `MultiChatMessage` also allows for multiple content items (text or image) in one object.
  - Grounding input can be masked with `DPIConfig`.
  - LLama Guard can now be used for content filtering.
  - Support for tool calling and response format
  - Updated the list for supported models (e.g., added amazon nova models).

### 📈 Improvements

- Update Orchestration client to version 0.48.2 (2501a)


## 1.1.0 - January 07, 2025

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/tag/rel%2F1.1.0)

### 🔧 Compatibility Notes

- Changed return type of `List<Double> getEmbedding()` from experimental API `OpenAiEmbeddingData` to `float[]` to match recent Spring AI change.

### ✨ New Functionality

- Added `streamChatCompletion()` and `streamChatCompletionDeltas()` to the `OrchestrationClient`.

### 📈 Improvements

- Update AI Core client to 2.37.0


## 1.0.0 - December 03, 2024

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/tag/rel%2F1.0.0)

### ✨ New Functionality

- Introduce AI Core client to consume the [AI Core Rest APIs](https://api.sap.com/api/AI_CORE_API/overview).
  Here are a few features:
  - Artifact management: register and organize datasets and model artifacts.
  - Configuration management: set up configurations for various models and use cases.
  - Deployment management: deploy AI models and manage their lifecycle within SAP AI Core.
- Introduce Orchestration client for consuming the following features of the orchestration service:
  - Harmonized LLM access via orchestration
  - Prompt templates
  - Content filtering
  - Masking
- Introduce the OpenAI client to consume the following features:
  - Chat completion and streaming chat completion
    - Text
    - Images
    - Tools
  - Generate embeddings for input text.

> [!WARNING]  
> All model classes are generated or depend on changing specifications.
> This means that model classes are not stable and may change in the future.
