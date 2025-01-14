## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- `ChatMessage`, as well as new `MultiChatMessage`, are now subtypes of new interface `ChatMessagesInner`.
  Most variables or methods previously typed as `ChatMessage` in `model` package are now typed as `ChatMessagesInner`. 
- Add missing `@Beta` annotations to all `com.sap.ai.sdk.core.client` and `com.sap.ai.sdk.core.model` classes.

### ✨ New Functionality

- Orchestration supports images as input in newly introduced `MultiChatMessage`.
- `MultiChatMessage` also allows for multiple content items (text or image) in one object.
- Grounding input can be masked with `DPIConfig`.

### 📈 Improvements

- Update Orchestration client to version 0.43.0 (2412a)

### 🐛 Fixed Issues

- 
