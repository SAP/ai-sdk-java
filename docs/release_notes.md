## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- Some changes to existing generated API in the _Prompt Registry_ module.
  Some methods now take additional (nullable) arguments.
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
