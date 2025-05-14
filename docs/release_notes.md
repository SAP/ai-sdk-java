## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- The constructor of the `AssistantMessage` class now takes `List<MessageToolCall>` as input instead of `List<ResponseMessageToolCall>` (the generated class got renamed).

### ✨ New Functionality

- [OpenAI] [Add convenience for tool definition, parsing function calls and tool execution](https://sap.github.io/ai-sdk/docs/java/foundation-models/openai/chat-completion#executing-tool-calls)
- [Orchestration] Added new model DeepSeek-R1: `OrchestrationAiModel.DEEPSEEK_R1`
- [Orchestration] [Tool execution fully enabled](https://sap.github.io/ai-sdk/docs/java/spring-ai/orchestration#tool-calling)

### 📈 Improvements

-

### 🐛 Fixed Issues

- [Orchestration] Fixed `OrchestrationAiModel.CLAUDE_3_7_SONNET`.
