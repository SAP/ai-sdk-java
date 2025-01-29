## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

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

### 🐛 Fixed Issues

- 
