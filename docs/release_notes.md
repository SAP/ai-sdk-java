## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- Some changes to existing generated API in the _Prompt Registry_ module.
- Changes to existing generated API in the _Prompt Registry_ module.
  Generated methods now take additional (nullable) arguments.
  See full changelog for all details.
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

| Aspect | Before | After | Migration |
|--------|--------|-------|-----------|
| **API Class Base** | `[...]Api` extends `AbstractOpenApiService` | `[...]Api` extends `BaseApi` | Update inheritance in generated classes |
| **Response Object** | `com.sap.cloud.sdk.services.openapi.core.OpenApiResponse` | `com.sap.cloud.sdk.services.openapi.apache.core.OpenApiResponse` | Update import statements |
| **API Client** | `AiCoreService.getApiClient()` returns `com.sap.cloud.sdk.services.openapi.apiclient.ApiClient` | `AiCoreService.getApiClient()` returns `com.sap.cloud.sdk.services.openapi.apache.apiclient.ApiClient` | Update import statements |
| **importPromptTemplate() Input** | `Resource` parameter | `byte[]` parameter | Call `Resource.getContentAsByteArray()` |
| **Dependencies** | Includes `org.springframework` | Removed | May need to add to `dependencyManagement`: `spring-core`, `spring-web`, `spring-beans`, `spring-context` |

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
