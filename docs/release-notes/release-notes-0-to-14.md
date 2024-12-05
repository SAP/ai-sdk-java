### 1.0.0 - December 03, 2024

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/tag/rel%2F1.0.0)

### ✨ New Functionality

- Introduce AI Core client to consume the [AI Core Rest APIs](https://api.sap.com/api/AI_CORE_API/overview).
  Here are a few features:
  - Artifact management: register and organize datasets and model artifacts.
  - Configuration management: set up configurations for various models and use cases.
  - Deployment management: deploy AI models and manage their lifecycle within SAP AI Core.
- Introduce Orchestration client for consuming the following features of the orchestration service:
  - Harmonized LLM access via orchestration
  - Prompt templates
  - Content filtering
  - Masking
- Introduce the OpenAI client to consume the following features:
  - Chat completion and streaming chat completion
    - Text
    - Images
    - Tools

> [!WARNING]  
> All model classes are generated or depend on changing specifications.
> This means that model classes are not stable and may change in the future.
