[![REUSE status](https://api.reuse.software/badge/github.com/SAP/ai-sdk-java)](https://api.reuse.software/info/github.com/SAP/ai-sdk-java)

# AI SDK for Java

# ⚠️ This is a pre-alpha version of the AI SDK for Java. The APIs are subject to change ⚠️

## About this project

Integrate chat completion into your business applications with SAP Cloud SDK for GenAI Hub. Leverage the Generative AI Hub of SAP AI Core to make use of templating, grounding, data masking, content filtering and more. Set up your SAP AI Core instance with SAP Cloud SDK for AI Core.

## List of available and tested APIs

We maintain [a list of currently available and tested AI Core APIs](docs/list-of-tested-APIs.md)

# Documentation

## AI Core Deployment

### Prerequisites

- The AI Core service in BTP
  - [How to enable the AI Core service](https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/initial-setup)
- Created a configuration in AI Core
  - <details>
    <summary>An example configuration from the AI Core <code>/configuration</code> endpoint</summary>
    <pre>
    {
      "createdAt": "2024-07-03T12:44:08Z",
      "executableId": "azure-openai",
      "id": "12345-123-123-123-123456abcdefg",
      "inputArtifactBindings": [],
      "name": "gpt-35-turbo",
      "parameterBindings": [
        {
          "key": "modelName",
          "value": "gpt-35-turbo"
        },
        {
          "key": "modelVersion",
          "value": "latest"
        }
      ],
      "scenarioId": "foundation-models"
    }
    </pre>
    </details>
- A Java project with a Maven `pom.xml`
  - Java 17 or higher
  - Maven 3.9 or higher
  - if Spring Boot is used, then minimum version 3
- [Set the AI Core credentials as an environment variable for local testing](#set-ai-core-credentials-as-environment-variable)

### Maven dependencies

Add the following dependencies to your `pom.xml` file:

```xml
<dependencies>
  <dependency>
    <groupId>com.sap.ai.sdk</groupId>
    <artifactId>core</artifactId>
    <version>${ai-sdk.version}</version>
  </dependency>
</dependencies>
```

See [an example pom in our Spring Boot application](e2e-test-app/pom.xml)

### Create a Deployment

```java
public AiDeploymentCreationResponse createDeployment() {

  final AiDeploymentCreationResponse deployment =
      new DeploymentApi(getClient())
          .deploymentCreate(
              "default",
              new AiDeploymentCreationRequest().configurationId("12345-123-123-123-123456abcdefg"));

  Objects.requireNonNull(deployment, "Deployment creation failed");

  String id = deployment.getId();
  Status status = deployment.getStatus();

  return deployment;
}
```

See [an example in our Spring Boot application](e2e-test-app/src/main/java/com/sap/ai/sdk/app/controllers/DeploymentController.java)

### Delete a Deployment

```java
public AiDeploymentDeletionResponse deleteDeployment(AiDeploymentCreationResponse deployment) {

  DeploymentApi client = new DeploymentApi(getClient());

  if (deployment.getStatus() == Status.RUNNING) {
    // Only RUNNING deployments can be STOPPED
    client.deploymentModify(
        "default",
        deployment.getId(),
        new AiDeploymentModificationRequest().targetStatus(Status.STOPPED));
  }
  // Wait a few seconds for the deployment to stop
  // Only UNKNOWN and STOPPED deployments can be DELETED
  return client.deploymentDelete("default", deployment.getId());
}
```

See [an example in our Spring Boot application](e2e-test-app/src/main/java/com/sap/ai/sdk/app/controllers/DeploymentController.java)

## OpenAI chat completion

### Prerequisites

- A deployed OpenAI model in AI Core.
  - [How to deploy a model to AI Core](https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/create-deployment-for-generative-ai-model-in-sap-ai-core)
  - <details>
    <summary>An example deployed model from the AI Core <code>/deployments</code> endpoint</summary>
    <pre>
    {
      "id": "d123456abcdefg",
      "deploymentUrl": "https://api.ai.region.aws.ml.hana.ondemand.com/v2/inference/deployments/d123456abcdefg",
      "configurationId": "12345-123-123-123-123456abcdefg",
      "configurationName": "gpt-35-turbo",
      "scenarioId": "foundation-models",
      "status": "RUNNING",
      "statusMessage": null,
      "targetStatus": "RUNNING",
      "lastOperation": "CREATE",
      "latestRunningConfigurationId": "12345-123-123-123-123456abcdefg",
      "ttl": null,
      "details": {
        "scaling": {
          "backendDetails": null,
          "backend_details": {
          }
        },
        "resources": {
          "backendDetails": null,
          "backend_details": {
            "model": {
              "name": "gpt-35-turbo",
              "version": "latest"
            }
          }
        }
      },
      "createdAt": "2024-07-03T12:44:22Z",
      "modifiedAt": "2024-07-16T12:44:19Z",
      "submissionTime": "2024-07-03T12:44:51Z",
      "startTime": "2024-07-03T12:45:56Z",
      "completionTime": null
    }
    </pre>
    </details>
- A Java project with a Maven `pom.xml`
  - Java 17 or higher
  - Maven 3.9 or higher
  - if Spring Boot is used, then minimum version 3
- [Set the AI Core credentials as an environment variable for local testing](#set-ai-core-credentials-as-environment-variable)

### Maven dependencies

Add the following dependencies to your `pom.xml` file:

```xml
<dependencies>
  <dependency>
    <groupId>com.sap.ai.sdk.foundationmodels</groupId>
    <artifactId>openai</artifactId>
    <version>${ai-sdk.version}</version>
  </dependency>
</dependencies>
```

See [an example pom in our Spring Boot application](e2e-test-app/pom.xml)

### Simple chat completion

```java
final var userMessage =
    new OpenAiChatUserMessage().setContent("Hello World! Why is this phrase so famous?");
final var request =
    new OpenAiChatCompletionParameters().setMessages(List.of(systemMessage, userMessage));

final OpenAiChatCompletionOutput result = OpenAiClient.forModel(GPT_35_TURBO).chatCompletion(request);

final String resultMessage = result.getChoices().get(0).getMessage().getContent();
```

See [an example in our Spring Boot application](e2e-test-app/src/main/java/com/sap/ai/sdk/app/controllers/OpenAiController.java)

### Chat completion with a model not in defined `OpenAiModel`

```java
final OpenAiChatCompletionOutput result =
    OpenAiClient.forModel(new OpenAiModel(model)).chatCompletion(request);
```

## Orchestration chat completion

### Prerequisites

- A deployed Orchestration service in AI Core.
  - [Orchestration documentation](https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/orchestration)
  - <details>
    <summary>An example orchestration deployment from the AI Core <code>/deployments</code> endpoint</summary>
    <pre>
    {
      "id": "d123456abcdefg",
      "deploymentUrl": "https://api.ai.intprod-eu12.eu-central-1.aws.ml.hana.ondemand.com/v2/inference/deployments/d123456abcdefg",
      "configurationId": "12345-123-123-123-123456abcdefg",
      "configurationName": "orchestration",
      "scenarioId": "orchestration",
      "status": "RUNNING",
      "statusMessage": null,
      "targetStatus": "RUNNING",
      "lastOperation": "CREATE",
      "latestRunningConfigurationId": "12345-123-123-123-123456abcdefg",
      "ttl": null,
      "createdAt": "2024-08-05T16:17:29Z",
      "modifiedAt": "2024-08-06T06:32:50Z",
      "submissionTime": "2024-08-05T16:17:40Z",
      "startTime": "2024-08-05T16:18:41Z",
      "completionTime": null
    }
    </pre>
    </details>
- A Java project with a Maven `pom.xml`
  - Java 17 or higher
  - Maven 3.9 or higher
  - if Spring Boot is used, then minimum version 3
- [Set the AI Core credentials as an environment variable for local testing](#set-ai-core-credentials-as-environment-variable)

### Maven dependencies

Add the following dependencies to your `pom.xml` file:

```xml
<dependencies>
  <dependency>
    <groupId>com.sap.ai.sdk</groupId>
    <artifactId>orchestration</artifactId>
    <version>${ai-sdk.version}</version>
  </dependency>
</dependencies>
```

See [an example pom in our Spring Boot application](e2e-test-app/pom.xml)

### Chat completion template

```java
final var llmConfig = new LLMModuleConfig().modelName("gpt-35-turbo").modelParams(Map.of());

final var inputParams =
    Map.of("input", "Reply with 'Orchestration Service is working!' in German");
final var template = new ChatMessage().content("{{?input}}").role("user");
final var templatingConfig = new TemplatingModuleConfig().template(List.of(template));

final var config =
    new CompletionPostRequest()
        .orchestrationConfig(
            new OrchestrationConfig()
                .moduleConfigurations(
                    new ModuleConfigs()
                        .templatingModuleConfig(templatingConfig)
                        .llmModuleConfig(llmConfig)))
        .inputParams(inputParams);

final CompletionPostResponse result =
    new OrchestrationCompletionApi(getOrchestrationClient("default"))
        .orchestrationV1EndpointsCreate(config);

final String message =
    result.getOrchestrationResult().getChoices().get(0).getMessage().getContent();
```

See [an example in our Spring Boot application](e2e-test-app/src/main/java/com/sap/ai/sdk/app/controllers/OrchestrationController.java)

### Messages history

```java
final var llmConfig = new LLMModuleConfig().modelName("gpt-35-turbo").modelParams(Map.of());

List<ChatMessage> messagesHistory =
    List.of(
        new ChatMessage().content("What is the capital of France?").role("user"),
        new ChatMessage().content("The capital of France is Paris.").role("assistant"));
final var message = new ChatMessage().content("What is the typical food there?").role("user");

final var templatingConfig = new TemplatingModuleConfig().template(List.of(message));

final var config =
    new CompletionPostRequest()
        .orchestrationConfig(
            new OrchestrationConfig()
                .moduleConfigurations(
                    new ModuleConfigs()
                        .templatingModuleConfig(templatingConfig)
                        .llmModuleConfig(llmConfig)))
        .messagesHistory(messagesHistory);

final CompletionPostResponse result =
    new OrchestrationCompletionApi(getOrchestrationClient("default"))
        .orchestrationV1EndpointsCreate(config);

final String message =
    result.getOrchestrationResult().getChoices().get(0).getMessage().getContent();
```

See [an example in our Spring Boot application](e2e-test-app/src/main/java/com/sap/ai/sdk/app/controllers/OrchestrationController.java)

### Chat completion filter

```java
final var llmConfig = new LLMModuleConfig().modelName("gpt-35-turbo").modelParams(Map.of());

final var inputParams =
    Map.of(
        "disclaimer",
        "```DISCLAIMER: The area surrounding the apartment is known for prostitutes and gang violence including armed conflicts, gun violence is frequent.");
final var template =
    new ChatMessage()
        .content(
            "Create a rental posting for subletting my apartment in the downtown area. Keep it short. Make sure to add the following disclaimer to the end. Do not change it! {{?disclaimer}}")
        .role("user");
final var templatingConfig = new TemplatingModuleConfig().template(List.of(template));

final var filterStrict =
    new Filter()
        .type(ProviderType.AZURE_CONTENT_SAFETY)
        .config(
            new FilterConfig()
                .hate(NUMBER_0)
                .selfHarm(NUMBER_0)
                .sexual(NUMBER_0)
                .violence(NUMBER_0));
final var filterLoose =
    new Filter()
        .type(ProviderType.AZURE_CONTENT_SAFETY)
        .config(
            new FilterConfig()
                .hate(NUMBER_4)
                .selfHarm(NUMBER_4)
                .sexual(NUMBER_4)
                .violence(NUMBER_4));

final var filteringConfig =
    new FilteringModuleConfig()
        .input(new FilteringConfig().filters(List.of(filterStrict))) // changing the input to filterLoose will allow the message to pass
        .output(new FilteringConfig().filters(List.of(filterStrict)));

final var config =
    new CompletionPostRequest()
        .orchestrationConfig(
            new OrchestrationConfig()
                .moduleConfigurations(
                    new ModuleConfigs()
                        .templatingModuleConfig(templatingConfig)
                        .filteringModuleConfig(filteringConfig)
                        .llmModuleConfig(llmConfig)))
        .inputParams(inputParams);

final CompletionPostResponse result =
    new OrchestrationCompletionApi(getOrchestrationClient("default"))
        .orchestrationV1EndpointsCreate(config); // this fails with Bad Request because the strict filter prohibits the input message

final String message =
    result.getOrchestrationResult().getChoices().get(0).getMessage().getContent();
```

See [an example in our Spring Boot application](e2e-test-app/src/main/java/com/sap/ai/sdk/app/controllers/OrchestrationController.java)


### Set model parameters

Change your LLM module configuration to add model parameters:

```java
var llmModuleConfig =
    new LLMModuleConfig()
        .modelName("gpt-35-turbo")
        .modelParams(
            Map.of(
                "max_tokens", 50,
                "temperature", 0.1,
                "frequency_penalty", 0,
                "presence_penalty", 0));
```

See [an example in our unit test](orchestration/src/test/java/com/sap/ai/sdk/orchestration/client/OrchestrationUnitTest.java)

## Add a header to every request

To add a header to AI Core requests, use the following code:
```java
ApiClient client = Core.getClient().addDefaultHeader("header-key", "header-value");
DeploymentApi api = new DeploymentApi(client);
```

For more customization, creating a [HeaderProvider](https://sap.github.io/cloud-sdk/docs/java/features/connectivity/http-destinations#about-headerproviders) is also possible.

## Requirements and Setup

### Set AI Core credentials as environment variable

- Go into the BTP Cockpit
- Instances and Subscriptions -> Instances -> AI Core -> View Credentials -> Copy JSON
- Set it as an environment variable `AICORE_SERVICE_KEY` in your IDE

  Or in your terminal:
```shell
export AICORE_SERVICE_KEY='{   "serviceurls": {     "AI_API_URL": ...'
```

### Run the Spring Boot application

```shell
cd e2e-test-app
mvn spring-boot:run
```

### Deploy to Cloud Foundry

```shell
mvn clean package
cf push
```

# Contribute

### Set-up Formatting

- Install the Google Java Format plugin on Intellij and follow these [instructions](https://github.com/google/google-java-format?tab=readme-ov-file#intellij-android-studio-and-other-jetbrains-ides).

## Support, Feedback, Contributing

This project is open to feature requests/suggestions, bug reports etc. via [GitHub issues](https://github.com/SAP/ai-sdk-java/issues). Contribution and feedback are encouraged and always welcome. For more information about how to contribute, the project structure, as well as additional contribution information, see our [Contribution Guidelines](CONTRIBUTING.md).

## Security / Disclosure
If you find any bug that may be a security problem, please follow our instructions at [in our security policy](https://github.com/SAP/ai-sdk-java/security/policy) on how to report it. Please do not create GitHub issues for security-related doubts or problems.

## Code of Conduct

We as members, contributors, and leaders pledge to make participation in our community a harassment-free experience for everyone. By participating in this project, you agree to abide by its [Code of Conduct](https://github.com/SAP/.github/blob/main/CODE_OF_CONDUCT.md) at all times.

## Licensing

Copyright 2024 SAP SE or an SAP affiliate company and ai-sdk-java contributors. Please see our [LICENSE](LICENSE) for copyright and license information. Detailed information including third-party components and their licensing/copyright information is available [via the REUSE tool](https://api.reuse.software/info/github.com/SAP/ai-sdk-java).
