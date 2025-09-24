## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- Breaking change:
  - two fields in `OrchestrationModuleConfig` changed:
    - `inputTranslationConfig` is now of type `SAPDocumentTranslationInput`
    - `outputTranslationConfig` is now of type `SAPDocumentTranslationOutput`
  - when using `OrchestrationModuleConfig.withInputTranslationConfig()` and `OrchestrationModuleConfig.withOutputTranslationConfig()` consider the following diff:
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

### ✨ New Functionality

-

### 📈 Improvements

-

### 🐛 Fixed Issues

- [Orchestration] Tool calling works on all models
