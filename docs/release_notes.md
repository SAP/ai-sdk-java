## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

-

### ✨ New Functionality

- Extend `OpenAiClientException` and `OrchestrationClientException` to retrieve error diagnostics information received
  from remote service.
  New available accessors for troubleshooting: `getErrorResponse()`, `getHttpResponse()` and, `getHttpRequest()`.
  Please note: depending on the error response, these methods may return `null` if the information is not available.
- [OpenAI] Introduced `completion`, `streamChatCompletion`, `toolCalling` and `chatMemory` for OpenAI using SpringAI
  making SpringAI fully integrated with our OpenAI client.

### 📈 Improvements

-

### 🐛 Fixed Issues

-
