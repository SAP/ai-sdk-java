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
- [OpenAI] [Introduced SpringAI integration with our OpenAI client.](https://sap.github.io/ai-sdk/docs/java/spring-ai/openai)
  - Added `OpenAiChatModel`

### 📈 Improvements

-

### 🐛 Fixed Issues

-
