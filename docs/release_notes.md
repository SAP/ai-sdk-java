## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

-

### ✨ New Functionality

- [Orchestration] Added `GEMINI_3_1_FLASH_LITE` to model list in `OrchestrationAiModel`.
- [Batch] Added a new `BatchesApi` client to access the [Batch Service API](https://github.tools.sap/AI/llm-batch-service).
- Support for OpenAI Responses API with the new `AiCoreOpenAiClient`.
  The following endpoints are currently supported:

  | API Endpoint                   | Description               | Supported |
      |--------------------------------|---------------------------|--|
  | `POST /responses`              | Create response           | ✅ |
  | `POST /responses` (streaming)  | Create streaming response | ✅ |
  | `GET /responses/{id}`          | Retrieve response         | ✅ |
  | `POST /responses/{id}/cancel`  | Cancel response           | ✅ |
  | `DELETE /responses/{id}`       | Delete response           | ✅ |
  | `POST /responses/{id}/compact` | Create response (compact) | ❌ |
  | `GET /responses/{id}/input_items` | List input items          | ❌ |
  | `POST /responses/input_tokens` | Count input tokens        | ❌ |

### 📈 Improvements

-

### 🐛 Fixed Issues

-
