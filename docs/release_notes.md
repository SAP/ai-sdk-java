## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- Remove Spring dependency, by migrating generated API clients from `RestTemplate` (Spring) to `Apache`:

| Aspect | Before | After | Migration |
|--------|--------|-------|-----------|
| **API Class Base** | `[...]Api` extends `AbstractOpenApiService` | `[...]Api` extends `BaseApi` | Update inheritance in generated classes |
| **Response Object** | `com.sap.cloud.sdk.services.openapi.core.OpenApiResponse` | `com.sap.cloud.sdk.services.openapi.apache.core.OpenApiResponse` | Update import statements |
| **API Client** | `AiCoreService.getApiClient()` returns `com.sap.cloud.sdk.services.openapi.apiclient.ApiClient` | `AiCoreService.getApiClient()` returns `com.sap.cloud.sdk.services.openapi.apache.apiclient.ApiClient` | Update import statements |
| **importPromptTemplate() Input** | `Resource` parameter | `byte[]` parameter | Call `Resource.getContentAsByteArray()` |
| **Dependencies** | Includes `org.springframework` | Removed | May need to add to `dependencyManagement`: `spring-core`, `spring-web`, `spring-beans`, `spring-context` |
- [PromptRegistry] (Breaking) Removed `includeSpec` parameter from `listPromptTemplateHistory` method in `PromptTemplatesApi`
- [Grounding] (Breaking) `GoogleDriveConfig` now has fields `resourceType` and `resourceId` instead of `folder`.
  `GoogleDriveFolderDetail` has been renamed to `GoogleDriveResourceDetail` and can now represent both folders and drives using `resourceType` and `resourceId`.
  `GoogleDrivePipelineCreateRequest` now requires `GoogleDriveConfigurationStruct` to be created
- Deprecated multiple Orchestration and OpenAI models: `MISTRAL_LARGE_INSTRUCT`, `MISTRAL_SMALL_INSTRUCT`, `OPENAI_O1`, and `OPENAI_O3_MINI`.

### ✨ New Functionality

- [Grounding] Added values `CREATING`, `CREATED`, `CREATION_FAILED`, `DELETION_INPROGRESS` and `DELETION_FAILED` for `PipelineExecutionStatus`.
- [Grounding] New error type `GenericError` added. `ValidationError` now includes additional fields `input` and `ctx` for better debugging and error handling.
- [Orchestration] Supported (PDF) file uploading feature by local file path via `UserMessage.withFile`, by remote file URL via `UserMessage.withFileUrl` , and by base64 encoded string file via `UserMessage.withFileBase64`.
- [Orchestration] Added new models `GPT_53_CODEX`, `GPT_54`, and `GPT_54_NANO` for `OrchestrationAiModel`.
- [OpenAI] Added new models `GPT_53_CODEX`, `GPT_54`, and `GPT_54_NANO` for `OpenAiModel`.

### 📈 Improvements

-

### 🐛 Fixed Issues

-
