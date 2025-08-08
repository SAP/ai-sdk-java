## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- The **Spring AI** version has been increased from `1.0.0-M6` to the GA release `1.0.0`.
    - The `OrchestrationChatOptions` have been, replacing all references to `FunctionCallback` with `ToolCallback`.
    - Please follow the [official Spring AI upgrade guide](https://docs.spring.io/spring-ai/reference/upgrade-notes.html#upgrading-to-1-0-0-RC1) for further details.
    - The `@Beta` annotations on all classes related to Spring AI have been removed.
- [Orchestration] The `completion` api have been moved to the latest version `/v2/completions`
    - `LLMModuleConfig` is replaced by `LLMModelDetails` in `withLLmConfig` method of `OrchestrationModuleConfig` class.
    - `PromptTemplatingModuleConfigPrompt` replaces `TemplatingModuleConfig` in the `withTemplateConfig` method of `OrchestrationModuleConfig` class.
    - The generated model classes will reflect payload updates including restructuring of the module configurations and renaming of several fields.
- [Document Grounding] Breaking: The `message` field in `RetrievalPerFilterSearchResultWithError` is replaced by `error` and `filterId` with more specific error details.
- [Document Grounding] Extensive generated model class renaming for better specificity due to API spec changes.
    - `SearchResults` → `VectorSearchResults`
    - `KeyValueListPair` split into context-specific classes:
        - `VectorKeyValueListPair` for vector operations
        - `RetrievalKeyValueListPair` for retrieval operations
        - `VectorDocumentKeyValueListPair` for vector document operations
        - `RetrievalDocumentKeyValueListPair` for retrieval document operations
    - `Chunk` → `VectorChunk` and `RetrievalChunk` for different contexts
    - `SearchFilter` → `VectorSearchFilter` and `RetrievalSearchFilter`
    - `SearchConfiguration` → `VectorSearchConfiguration` and `RetrievalSearchConfiguration`

### ✨ New Functionality

- [Core] Added `ClientExceptionFactory` interface to provide custom exception mapping logic for different service clients.
- Extend `OpenAiClientException` and `OrchestrationClientException` to retrieve error diagnostics information received from remote service using `getErrorResponse`.
- [Orchestration] Introduced filtering related exceptions along with convenience methods to obtain additional contextual information.
    - `OrchestrationInputFilterException` for prompt filtering and `OrchestrationOutputFilterException` for response filtering.
        - `getFilterDetails()`: Returns a map of all filter details.
        - `getAzureContentSafetyInput()` and `getAzureContentSafetyInput()` : Returns Azure Content Safety filter scores
        - `getLlamaGuard38b()`: Returns LlamaGuard filter scores
- [Document Grounding] Extend pipline operations, notably:
    - Added new pipeline creation requests for SDM and WorkZone with `SDMPipelineCreateRequest` and `WorkZonePipelineCreateRequest`.
    - S3 and SFTP pipeline configurations extended with `S3Configuration` and `SFTPConfiguration` for including data sources.
    - Support `/pipelines/trigger` endpoint to trigger pipelines on-demand via `PipelinesApi#manualTriggerPipeline()`.

### 📈 Improvements

- Update AI Core client to 2.40.1
- [Document Grounding] Enhanced pipeline status reporting with additional metadata such as `createdAt`, `modifiedAt`, `lastCompletedAt`.

### 🐛 Fixed Issues

- OpenAi: Fix AssistantMessage Bug by now being able to send Assistant Messages using our API Client. 
