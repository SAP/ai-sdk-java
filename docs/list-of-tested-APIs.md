# List of available and tested APIs

last updated: 15.07.2024

✅ Available and tested

✔️ Available

❌ Not available

## AI Core
Maven dependency `com.sap.ai.sdk:core`

- ApplicationAPI ✔️
- ArtifactApi ✅
- ConfigurationApi ✅
- DeploymentApi ✅
- DockerRegistrySecretApi ✔️
- ExecutableApi ✔️
- ExecutionApi ✅
- ExecutionScheduleApi ✔️
- FileApi ✔️
- KpiApi ✔️
- MetaApi ✔️
- MetricsApi ✔️
- ObjectStoreSecretApi ✔️
- RepositoryApi ✔️
- ResourceApi ✔️
- ResourceGroupApi ✔️
- ResourceQuotaApi ✔️
- ScenarioApi ✅
- SecretApi ✔️
- ServiceApi ✔️

## OpenAI
Maven dependency `com.sap.ai.sdk.foundationmodels:openai`

- OpenAiClient
  - /completions ❌
  - /embeddings ✅
  - /chat/completions
    - Text ✅
    - Image ✅
    - Tools ✔️
  - /audio/transcriptions ❌
  - /audio/translations ❌
  - /images/generation ❌
