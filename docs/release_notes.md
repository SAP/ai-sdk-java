## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- [RPT] Updated SAP RPT tabular data prediction model client to version 1.5:

  | Aspect            | Before                                                              | After                                                                     |
  |-------------------|---------------------------------------------------------------------|---------------------------------------------------------------------------|
  | Row prediction    | `PredictRequestPayload.create().predictionConfig().xxx().rows()`    | `PredictRequestPayloadOneOf.create().predictionConfig().rows().xxx()`     |
  | Column prediction | `PredictRequestPayload.create().predictionConfig().xxx().columns()` | `PredictRequestPayloadOneOf1.create().predictionConfig().columns().xxx()` |
 
### ✨ New Functionality

-[Orchestration] Added `GEMINI_3_5_FLASH_LITE` and `GEMINI_3_6_FLASH` to model list in `OrchestrationAiModel`.

### 📈 Improvements

- [RPT] Updated SAP RPT tabular data prediction model client to version 1.5:
  - Added new `RptModel`, `SAP_RPT_1_5` and `SAP_RPT_1_5_LARGE`
  - Added `explanation` parameter to `PredictionConfig`.

### 🐛 Fixed Issues

- Fixed `kotlin-stdlib` dependency issues by removing the version management from the parent POM.
