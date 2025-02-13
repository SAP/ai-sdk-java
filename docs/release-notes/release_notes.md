## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- `Message.content()` returns a `ContentItem` now instead of a `String`. Use `((TextItem) Message.content().items().get(0)).text()` if the corresponding `ContentItem` is a `TextItem` and the string representation is needed.

### ✨ New Functionality

- Upgrade to release 2502a of AI Core.
- Orchestration:
  - [Add `LlamaGuardFilter`](https://github.com/SAP/ai-sdk-java/tree/main/docs/guides/ORCHESTRATION_CHAT_COMPLETION.md#chat-completion-filter).
  - [Convenient methods to create messages containing images and multiple text inputs](https://github.com/SAP/ai-sdk-java/tree/main/docs/guides/ORCHESTRATION_CHAT_COMPLETION.md#add-images-and-multiple-text-inputs-to-a-message)
  - [Enable setting the response format](https://github.com/SAP/ai-sdk-java/tree/main/docs/guides/ORCHESTRATION_CHAT_COMPLETION.md#set-a-response-format)

### 📈 Improvements

-

### 🐛 Fixed Issues

- 
