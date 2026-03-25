## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- Minor changes to generated API of _SAP Grounding Service_.
  ```diff
    var chunkMeta;
    var docMeta;
    var chunk =
  -   TextOnlyBaseChunk.create().content("Luna is the Latin word for moon.").metadata(chunkMeta);
  +   TextOnlyBaseChunkCreate.create().content("Luna is the Latin word for moon.").addMetadataItem(chunkMeta);
  
  - BaseDocument.create().chunks(chunk).metadata(docMeta);
  + BaseDocument.create().chunks(chunk).addMetadataItem(docMeta);
  ```
- Migrated generated API clients from `Spring` to `Apache`:
  - `[...]Api`classes now extend `BaseApi` instead of `AbstractOpenApiService`.
  - `OpenApiResponse` is now `com.sap.cloud.sdk.services.openapi.apache.core.OpenApiResponse` instead of `com.sap.cloud.sdk.services.openapi.core.OpenApiResponse`.
  - `AiCoreService.getApiClient()` now returns `com.sap.cloud.sdk.services.openapi.apache.apiclient.ApiClient` instead of `com.sap.cloud.sdk.services.openapi.apiclient.ApiClient`.
  - `.PromptTemplatesApi.importPromptTemplate()` requires `byte[]` instead of `Resource` as input.
    - Migrate by calling `Resource.getContentAsByteArray()`.
  - Removed all `org.springframework` dependencies, you may need to add the following dependencies in your `dependencyManagement` to resolve convergence errors:
    - `spring-core`, `spring-web`, `spring-beans`, `spring-context`

### ✨ New Functionality

- [Grounding] Added `GroundingClient.withHeader()`.
- [Orchestration] Added `GPT_52` model for `OrchestrationAiModel`.
- [OpenAi] Added `GPT_52` model from `OpenAiModel`.
- [Orchestration] Added `GEMINI_EMBEDDING` model for `OrchestrationEmbeddingModel`.
- [Orchestration] Added citations for Perplexity `SONAR` model in `client.chatCompletion().getOriginalResponse().getFinalResult().getCitations()`

### 📈 Improvements

- [Orchestration] Added new API `TranslationConfig#applyToPlaceholders` and `TranslationConfig#applyToTemplateRoles` to support partial translation for a message.
- [RPT] `RptClient.tableCompletion()` GZIP compresses the request payload.

### 🐛 Fixed Issues

-
