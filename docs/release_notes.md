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
- [OpenAI] Added new models for `OpenAiModel`: `GPT_5`, `GPT_5_MINI` and `GPT_5_NANO`.
- [Orchestration] Added new models for `OrchestrationAiModel`: `GPT_5`, `GPT_5_MINI` and
  `GPT_5_NANO`.
- [Orchestration] Deprecated models for `OrchestrationAiModel`: `GEMINI_1_5_PRO` and
  `OrchestrationAiModel.GEMINI_1_5_FLASH`
  - Replacement are `GEMINI_2_5_PRO` and `GEMINI_2_5_FLASH`.
- [Orchestration] Deprecated `OrchestrationAiModel.IBM_GRANITE_13B_CHAT` with no replacement.
- [OpenAI] [Introduced SpringAI integration with our OpenAI client.](https://sap.github.io/ai-sdk/docs/java/spring-ai/openai)
  - Added `OpenAiChatModel`

### 📈 Improvements

-

### 🐛 Fixed Issues

-
