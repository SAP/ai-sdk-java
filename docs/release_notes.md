## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- [RPT] Updated SAP RPT tabular data prediction model client to version 1.5

  | Aspect            | Before                                                              | After                                                                     |
  |-------------------|---------------------------------------------------------------------|---------------------------------------------------------------------------|
  | `RptModel`        | `SAP_RPT_1_SMALL`                                                   | `SAP_RPT_15`                                                              |
  | `RptModel`        | `SAP_RPT_1_LARGE`                                                   | `SAP_RPT_15_LARGE`                                                        |
  | Row prediction    | `PredictRequestPayload.create().predictionConfig().xxx().rows()`    | `PredictRequestPayloadOneOf.create().predictionConfig().rows().xxx()`     |
  | Column prediction | `PredictRequestPayload.create().predictionConfig().xxx().columns()` | `PredictRequestPayloadOneOf1.create().predictionConfig().columns().xxx()` |

### ✨ New Functionality

-[Orchestration] Added `GEMINI_3_5_FLASH_LITE` and `GEMINI_3_6_FLASH` to model list in `OrchestrationAiModel`.

### 📈 Improvements

- [RPT] Added explanation parameter to `PredictionConfig`.

### 🐛 Fixed Issues

-
