## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

-

### ✨ New Functionality

- [Orchestration] Added embedding generation support with new `OrchestrationClient#embed()` methods.
  - Added `OrchestrationEmbeddingModel` with `TEXT_EMBEDDING_3_SMALL`, `TEXT_EMBEDDING_3_LARGE`, `AMAZON_TITAN_EMBED_TEXT` and `NVIDIA_LLAMA_32_NV_EMBEDQA_1B` embedding models.
  - Introduced `OrchestrationEmbeddingRequest` for building requests fluently and `OrchestrationEmbeddingResponse#getEmbeddingVectors()` to retrieve embeddings.
  
### 📈 Improvements

-

### 🐛 Fixed Issues

-
