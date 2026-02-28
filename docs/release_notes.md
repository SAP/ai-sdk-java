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

### ✨ New Functionality

- [Grounding] Added `GroundingClient.withHeader()`.

### 📈 Improvements

- [Orchestration] Added new API `TranslationConfig#withApplyTo` to support partial translation for user's input.

### 🐛 Fixed Issues

-
