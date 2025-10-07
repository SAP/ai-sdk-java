## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- Breaking change:
  - `CompletionPostRequest` is now an interface instead of a class. 
    For all previous use-cases, it should be substitutable with the new class `CompletionRequestConfiguration`.
    - `OrchestrationClient.toCompletionPostRequest()` now returns `CompletionRequestConfiguration`.
    - `OrchestrationClient.streamChatCompletionDeltas()` takes `CompletionRequestConfiguration` as an input now.
  - Two fields in `OrchestrationModuleConfig` changed:
    - `inputTranslationConfig` is now of type `SAPDocumentTranslationInput`
    - `outputTranslationConfig` is now of type `SAPDocumentTranslationOutput`
  - When using `OrchestrationModuleConfig.withInputTranslationConfig()` and `OrchestrationModuleConfig.withOutputTranslationConfig()` consider the following diff:
    ```diff
    var config = new OrchestrationModuleConfig("some prompt");
    config
           .withInputTranslationConfig(
    -          SAPDocumentTranslation.create()
    -              .type(SAP_DOCUMENT_TRANSLATION)
    -              .config(SAPDocumentTranslationConfig.create().targetLanguage("en-US")))
    +          SAPDocumentTranslationInput.create()
    +              .type(SAPDocumentTranslationInput.TypeEnum.SAP_DOCUMENT_TRANSLATION)
    +              .config(SAPDocumentTranslationInputConfig.create().targetLanguage("en-US")))
            .withOutputTranslationConfig(
    -          SAPDocumentTranslation.create()
    -              .type(SAP_DOCUMENT_TRANSLATION)
    +          SAPDocumentTranslationOutput.create()
    +              .type(SAPDocumentTranslationOutput.TypeEnum.SAP_DOCUMENT_TRANSLATION)
                   .config(
    -                  SAPDocumentTranslationConfig.create()
    -                      .targetLanguage("de-DE")
    +                  SAPDocumentTranslationOutputConfig.create()
    +                      .targetLanguage(
    +                          SAPDocumentTranslationOutputTargetLanguage.create("de-DE"))
                           .sourceLanguage("en-US"))); 
    ```
- [Orchestration] Deprecated models `OrchestrationAiModel.CLAUDE_3_OPUS` and `OrchestrationAiModel.CLAUDE_3_5_SONNET`.
  - Replacement are respectively `OrchestrationAiModel.CLAUDE_4_OPUS` and `OrchestrationAiModel.CLAUDE_4_SONNET`.

### ✨ New Functionality

- [Orchestration] For streaming, add convenience configuration for output-filter-overlap, chunk-size, and delimiters via `OrchestrationModuleConfig#withStreamConfig`.
- [Orchestration] Added embedding generation support with new `OrchestrationClient#embed()` methods.
  - Added `OrchestrationEmbeddingModel` with `TEXT_EMBEDDING_3_SMALL`, `TEXT_EMBEDDING_3_LARGE`, `AMAZON_TITAN_EMBED_TEXT` and `NVIDIA_LLAMA_32_NV_EMBEDQA_1B` embedding models.
  - Introduced `OrchestrationEmbeddingRequest` for building requests fluently and `OrchestrationEmbeddingResponse#getEmbeddingVectors()` to retrieve embeddings.
- [Orchestration] Added new model `OrchestrationAiModel.MISTRAL_MEDIUM_INSTRUCT`.
  
### 📈 Improvements

-

### 🐛 Fixed Issues

- [Orchestration] Tool calling works on all models
