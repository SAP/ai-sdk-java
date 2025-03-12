## 1.4.0 - February 28, 2025

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/tag/rel%2F1.4.0)

### 🔧 Compatibility Notes

- [Orchestration] The constructors `UserMessage(MessageContent)` and `SystemMessage(MessageContent)` are removed. Use `Message.user(String)`, `Message.user(ImageItem)`, or `Message.system(String)` instead.
- Deprecate `getCustomField(String)` in favor of `toMap()` on generated model classes.
    - `com.sap.ai.sdk.core.model.*`
    - `com.sap.ai.sdk.orchestration.model.*`

### ✨ New Functionality

- [Orchestration] [Add Spring AI tool calling](https://github.com/SAP/ai-sdk-java/tree/main/docs/guides/SPRING_AI_INTEGRATION.md#tool-calling).
- [Orchestration] [Add new convenient methods to set the response format for Orchestration.](https://github.com/SAP/ai-sdk-java/tree/main/docs/guides/ORCHESTRATION_CHAT_COMPLETION.md#set-a-response-format)
- [Document Grounding] [Add Document Grounding Client](https://github.com/SAP/ai-sdk-java/tree/main/docs/guides/GROUNDING.md)
    - `com.sap.ai.sdk:document-grounding:1.4.0`
- [OpenAI] New generated model classes introduced for _AzureOpenAI_ specification dated 2024-10-21.
- [OpenAI] Introducing [new user interface](https://github.com/SAP/ai-sdk-java/tree/main/docs/guides/OPENAI_CHAT_COMPLETION.md/#new-user-interface-v140) for chat completion wrapping the generated model classes.
    - `OpenAiChatCompletionRequest` and `OpenAiChatCompletionResponse`' for high level request and response handling.
    - `OpenAiUserMessage`, `OpenAiSystemMessage`, `OpenAiAssistantMessage` and `OpenAiToolMessage` for message creation for different content types.
    - `OpenAiToolChoice` for configuring chat completion requests with tool selection strategy.
- [OpenAI] Introducing new user interface for embedding calls using `OpenAiEmbeddingRequest` and `OpenAiEmbeddingResponse`.


## 1.3.0 - February 13, 2025

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/tag/rel%2F1.3.0)

### 🔧 Compatibility Notes

- `Message.content()` returns a `ContentItem` now instead of a `String`. Use `((TextItem) Message.content().items().get(0)).text()` if the corresponding `ContentItem` is a `TextItem` and the string representation is needed.

### ✨ New Functionality

- Upgrade to release 2502a of AI Core.
- Orchestration:
  - [Add `LlamaGuardFilter`](https://github.com/SAP/ai-sdk-java/tree/main/docs/guides/ORCHESTRATION_CHAT_COMPLETION.md#chat-completion-filter).
  - [Convenient methods to create messages containing images and multiple text inputs](https://github.com/SAP/ai-sdk-java/tree/main/docs/guides/ORCHESTRATION_CHAT_COMPLETION.md#add-images-and-multiple-text-inputs-to-a-message)
  - [Enable setting the response format](https://github.com/SAP/ai-sdk-java/tree/main/docs/guides/ORCHESTRATION_CHAT_COMPLETION.md#set-a-response-format)


## 1.2.0 - January 30, 2025

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/tag/rel%2F1.2.0)

### 🔧 Compatibility Notes

- `SingleChatMessage`, as well as new `MultiChatMessage`, are now subtypes of new interface `ChatMessage`.
  Most variables or methods previously typed as `ChatMessage` in `model` package are now typed as `SingleChatMessage`. 
- Add missing `@Beta` annotations to all `com.sap.ai.sdk.core.client` and `com.sap.ai.sdk.core.model` classes.

### ✨ New Functionality

- New Orchestration features:
  - [Spring AI integration](https://github.com/SAP/ai-sdk-java/tree/main/docs/guides/SPRING_AI_INTEGRATION.md)
  - [Add Grounding configuration convenience](https://github.com/SAP/ai-sdk-java/tree/main/docs/guides/ORCHESTRATION_CHAT_COMPLETION.md#grounding)
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
