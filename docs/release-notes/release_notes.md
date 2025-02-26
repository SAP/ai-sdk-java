## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- The constructors `UserMessage(MessageContent)` and `SystemMessage(MessageContent)` are removed. Use `Message.user(String)`, `Message.user(ImageItem)`, or `Message.system(String)` instead.
- Deprecate `getCustomField(String)` in favor of `toMap()` on generated model classes.
  - `com.sap.ai.sdk.core.model.*`
  - `com.sap.ai.sdk.orchestration.model.*`

### ✨ New Functionality

- [Add Spring AI tool calling](../guides/SPRING_AI_INTEGRATION.md#tool-calling).
- [Add Document Grounding Client](https://github.com/SAP/ai-sdk-java/tree/main/docs/guides/GROUNDING.md)
  - `com.sap.ai.sdk:document-grounding:1.4.0` 

### 📈 Improvements

-

### 🐛 Fixed Issues

- 
