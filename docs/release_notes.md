## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- [Document Grounding] `getAllPipelines()` has additional nullable parameter `metadataConfigId`.

### ✨ New Functionality

- Support for OpenAI Responses API with the new `AiCoreOpenAiClient`.
  The following endpoints are currently supported:

  | API Endpoint                         | Description               | Supported |
    |--------------------------------------|---------------------------|--|
  | `POST /v1/responses`                 | Create response           | ✅ |
  | `POST /v1/responses` (streaming)     | Create streaming response | ✅ |
  | `GET /v1/responses/{id}`             | Retrieve response         | ❌ |
  | `DELETE /v1/responses/{id}`          | Delete response           | ❌ |
  | `POST /v1/responses/{id}/compact`    | Create response (compact) | ✅ |
  | `POST /v1/responses/{id}/cancel`     | Cancel response           | ❌ |
  | `GET /v1/responses/{id}/input_items` | List input items          | ❌ |
  | `POST /v1/responses/input_tokens`    | Count input tokens        | ❌ |

### 📈 Improvements

-

### 🐛 Fixed Issues

-
