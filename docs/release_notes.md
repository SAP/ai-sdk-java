## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- [Orchestration] Removed `OrchestrationAiModel COHERE_RERANKER`. This model was supported neither by Orchestration nor by AI SDK.

### ✨ New Functionality

- [OpenAI] You can now add multiple custom headers to an `OpenAiClient` at once via `.withHeaders()`.
- [Orchestration] Added `OrchestrationAiModel.GEMINI_3_1_PRO_PREVIEW_EA`

### 📈 Improvements

-

### 🐛 Fixed Issues

- [Orchestration] Some `OrchestrationClientException` were reported as `OrchestrationFilterException.Input`.
