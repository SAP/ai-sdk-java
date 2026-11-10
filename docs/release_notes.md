## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- [Orchestration] `LlamaGuardFilter` is deprecated. Use `GraniteGuardianFilter` instead. Configure categories (`harm`, `violence`, `jailbreak`, `socialBias`, `profanity`, `sexualContent`, `unethicalBehavior`) via `GraniteGuardian41Categories` and retrieve filter details from caught exceptions via `OrchestrationFilterException.getGraniteGuardian41()`.

  | Aspect | Before | After |
  | --- | --- | --- |
  | Filter class | `LlamaGuardFilter` | `GraniteGuardianFilter` |
  | Filter config model | `LlamaGuard38b` | `GraniteGuardian41` with `GraniteGuardian41Categories` |
  | Exception accessor | `e.getLlamaGuard38b()` | `e.getGraniteGuardian41()` |

### ✨ New Functionality

- [Orchestration] Added new model `SAP_ABAP_2` to `OrchestrationAiModel`.
- [Orchestration] Added `GraniteGuardianFilter` for input and output content filtering using IBM Granite Guardian 4.1.

### 📈 Improvements

- [Core] Added `CoreApi.availableAiModels()` convenience methods to make model discovery more visible. Both the scenario ID (`"foundation-models"`) and resource group (`"default"`) are pre-filled by default. Use `availableAiModels(scenarioId, resourceGroup)` to specify custom values.

### 🐛 Fixed Issues

- [Orchestration] Token usage reported in `ChatResponse` metadata now correctly accumulates across all internal tool-call rounds when using `OrchestrationChatModel`.
- [Orchestration] `OrchestrationClient.executeRequest()` is now `protected` instead of `package-private`. It was `public` in v1.
