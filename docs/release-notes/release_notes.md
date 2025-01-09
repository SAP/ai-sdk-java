## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- `ChatMessage`, as well as new `MultiChatMessage`, are now subtypes of new interface `ChatMessagesInner`. This may cause type inconsistencies in existing codebase. 

### ✨ New Functionality

- Orchestration supports images as input in newly introduced `MultiChatMessage`.
- `MultiChatMessage` also allows for multiple content items (text or image) in one object.
- Grounding input can be masked with `DPIConfig`.

### 📈 Improvements

- Update Orchestration client to version 2412a

### 🐛 Fixed Issues

- 
