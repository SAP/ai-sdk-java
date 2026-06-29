## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- [Prompt Registry] In `OrchestrationConfigPostRequest` and similar classes to create prompt registry requests, use the following replacements:

  | Aspect | Before | After |
  |--------|--------|-------|
  | Orchestration config | `OrchestrationConfig` | `PromptRegistryOrchestrationConfig` |
  | Orchestration config modules | `OrchestrationConfigModules` | `PromptRegistryOrchestrationConfigModules` |
  | Module configs | `ModuleConfigs` | `PartialModuleConfigs` |
  | Prompt templating module config | `PromptTemplatingModuleConfig` | `PartialPromptTemplatingModuleConfig` |

### ✨ New Functionality

-

### 📈 Improvements

-

### 🐛 Fixed Issues

-
