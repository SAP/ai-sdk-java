## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- The **Spring AI** version has been increased from `1.0.0-M6` to the GA release `1.0.0`.
  - The `OrchestrationChatOptions` have been, replacing all references to `FunctionCallback` with `ToolCallback`.
  - Please follow the [official Spring AI upgrade guide](https://docs.spring.io/spring-ai/reference/upgrade-notes.html#upgrading-to-1-0-0-RC1) for further details.
  - The `@Beta` annotations on all classes related to Spring AI have been removed.


### ✨ New Functionality

- [Core] Added `ClientExceptionFactory` interface to provide custom exception mapping logic for different service clients.
- Extend `OpenAiClientException` and `OrchestrationClientException` to  retrieve error diagnostics information received from remote service.
  New available accessors for troubleshooting: `getErrorResponse()`, `getHttpResponse()` and , `getHttpRequest()`.
  Please note: depending on the error response, these methods may return `null` if the information is not available.
- [Orchestration] Introduced filtering related exceptions along with convenience methods to obtain additional contextual information.
  - `OrchestrationInputFilterException` for prompt filtering and `OrchestrationOutputFilterException` for response filtering.
    - `getFilterDetails()`: Returns a map of all filter details.
    - `getAzureContentSafetyInput()` and `getAzureContentSafetyInput()` : Returns Azure Content Safety filter scores
    - `getLlamaGuard38b()`: Returns LlamaGuard filter scores

### 📈 Improvements

- Update AI Core client to 2.40.1


### 🐛 Fixed Issues

- OpenAi: Fix AssistantMessage Bug by now being able to send Assistant Messages using our API Client. 
