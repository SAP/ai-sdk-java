# Orchestration Chat Completion

## Table of Contents

- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
  - [Maven Dependencies](#maven-dependencies)
- [Usage](#usage)
    - [Chat completion with Templates](#chat-completion-with-templates)
    - [Message history](#message-history)
    - [Chat Completion Filter](#chat-completion-filter)
    - [Data Masking](#data-masking)
    - [Set Model Parameters](#set-model-parameters)

## Introduction

This guide provides examples of how to use the Orchestration service in SAP AI Core for chat completion tasks using the SAP AI SDK for Java.

## Prerequisites

Before using the AI Core module, ensure that you have met all the general requirements outlined in the [README.md](../../README.md#general-requirements). 
Additionally, include the necessary Maven dependency in your project.

### Maven Dependencies

Add the following dependency to your `pom.xml` file:

```xml
<dependencies>
  <dependency>
    <groupId>com.sap.ai.sdk</groupId>
    <artifactId>orchestration</artifactId>
    <version>${ai-sdk.version}</version>
  </dependency>
</dependencies>
```

⚠️ Cloud SDK users should remove version tags from all Cloud SDK dependencies.

See [an example `pom.xml` in our Spring Boot application](../../sample-code/spring-app/pom.xml).

## Usage

In addition to the prerequisites above, we assume you have already set up the following to carry out the examples in this guide:

- **A Deployed Orchestration Service in SAP AI Core**
    - Refer to the [Orchestration Documentation](https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/orchestration) for setup instructions.
    - <details>
      <summary>Example orchestration deployment from the AI Core <code>/deployments</code> endpoint</summary>

      ```json
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
      ```

      </details>
    
### Chat completion with Templates

Use a chat completion template to generate a response in German:

```java
var llmConfig = LLMModuleConfig.create().modelName("gpt-35-turbo").modelParams(Map.of());

var inputParams =
    Map.of("input", "Reply with 'Orchestration Service is working!' in German");
var template = ChatMessage.create().role("user").content("{{?input}}");
var templatingConfig = TemplatingModuleConfig.create().template(template);

var config =
    CompletionPostRequest.create()
        .orchestrationConfig(
            OrchestrationConfig.create()
                .moduleConfigurations(
                    ModuleConfigs.create()
                        .llmModuleConfig(llmConfig)
                        .templatingModuleConfig(templatingConfig)))
        .inputParams(inputParams);

CompletionPostResponse result =
    new OrchestrationClient().chatCompletion(config);

String messageResult =
    result.getOrchestrationResult().getChoices().get(0).getMessage().getContent();
```

See [an example in our Spring Boot application](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/controllers/OrchestrationController.java)

### Message history

Include a message history to maintain context in the conversation:

```java
var llmConfig = LLMModuleConfig.create().modelName("gpt-35-turbo").modelParams(Map.of());

List<ChatMessage> messagesHistory =
    List.of(
        ChatMessage.create().role("user").content("What is the capital of France?"),
        ChatMessage.create().role("assistant").content("The capital of France is Paris."));

var message =
    ChatMessage.create().role("user").content("What is the typical food there?");
var templatingConfig = TemplatingModuleConfig.create().template(message);

var config =
    CompletionPostRequest.create()
        .orchestrationConfig(
            OrchestrationConfig.create()
                .moduleConfigurations(
                    ModuleConfigs.create()
                        .llmModuleConfig(llmConfig)
                        .templatingModuleConfig(templatingConfig)))
        .messagesHistory(messagesHistory);

CompletionPostResponse result =
    new OrchestrationClient().chatCompletion(config);

String messageResult =
    result.getOrchestrationResult().getChoices().get(0).getMessage().getContent();
```

See [an example in our Spring Boot application](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/controllers/OrchestrationController.java)

### Chat completion filter

Apply content filtering to the chat completion:

```java
var llmConfig = LLMModuleConfig.create().modelName("gpt-35-turbo").modelParams(Map.of());

var inputParams =
    Map.of(
        "disclaimer",
        "```DISCLAIMER: The area surrounding the apartment is known for prostitutes and gang violence including armed conflicts, gun violence is frequent.");
var template =
    ChatMessage.create()
        .role("user")
        .content(
            "Create a rental posting for subletting my apartment in the downtown area. Keep it short. Make sure to add the following disclaimer to the end. Do not change it! {{?disclaimer}}");
var templatingConfig = TemplatingModuleConfig.create().template(template);

var filterStrict = 
    FilterConfig.create()
        .type(FilterConfig.TypeEnum.AZURE_CONTENT_SAFETY)
        .config(
            AzureContentSafety.create()
                .hate(NUMBER_0)
                .selfHarm(NUMBER_0)
                .sexual(NUMBER_0)
                .violence(NUMBER_0));

var filterLoose =
    FilterConfig.create()
        .type(FilterConfig.TypeEnum.AZURE_CONTENT_SAFETY)
        .config(
            AzureContentSafety.create()
                .hate(NUMBER_4)
                .selfHarm(NUMBER_4)
                .sexual(NUMBER_4)
                .violence(NUMBER_4));

var filteringConfig =
    FilteringModuleConfig.create()
        // changing the input to filterLoose will allow the message to pass
        .input(FilteringConfig.create().filters(filterStrict))
        .output(FilteringConfig.create().filters(filterStrict));

var config =
    CompletionPostRequest.create()
        .orchestrationConfig(
            OrchestrationConfig.create()
                .moduleConfigurations(
                    ModuleConfigs.create()
                        .llmModuleConfig(llmConfig)
                        .templatingModuleConfig(templatingConfig)
                        .filteringModuleConfig(filteringConfig)))
        .inputParams(inputParams);

// this fails with Bad Request because the strict filter prohibits the input message
CompletionPostResponse result =
    new OrchestrationClient().chatCompletion(config);

String messageResult =
    result.getOrchestrationResult().getChoices().get(0).getMessage().getContent();
```

See [an example in our Spring Boot application](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/controllers/OrchestrationController.java)

### Data masking

Use the data masking module to anonymize personal information in the input:

```java
var inputParams = Map.of("privateInfo", "Patrick Morgan +49 (970) 333-3833");
var template =
    ChatMessage.create().role("user").content("What is the nationality of {{?privateInfo}}");
var templatingConfig = TemplatingModuleConfig.create().template(template);

var maskingProvider =
    MaskingProviderConfig.create()
        .type(MaskingProviderConfig.TypeEnum.SAP_DATA_PRIVACY_INTEGRATION)
        .method(MaskingProviderConfig.MethodEnum.ANONYMIZATION)
        .entities(
            DPIEntityConfig.create().type(DPIEntities.PHONE),
            DPIEntityConfig.create().type(DPIEntities.PERSON));
var maskingConfig = MaskingModuleConfig.create().maskingProviders(maskingProvider);

CompletionPostRequest config =
    CompletionPostRequest.create()
        .orchestrationConfig(
            OrchestrationConfig.create()
                .moduleConfigurations(
                    ModuleConfigs.create()
                        .llmModuleConfig(LLM_CONFIG)
                        .templatingModuleConfig(templatingConfig)
                        .maskingModuleConfig(maskingConfig)))
        .inputParams(inputParams);

CompletionPostResponse result =
    new OrchestrationClient().chatCompletion(config);

String messageResult =
    result.getOrchestrationResult().getChoices().get(0).getMessage().getContent();
```

In this example, the input will be masked before the call to the LLM. Note that data cannot be unmasked in the LLM output.

See [an example in our Spring Boot application](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/controllers/OrchestrationController.java)

### Set model parameters

Change your LLM module configuration to add model parameters:

```java
var llmConfig =
    LLMModuleConfig.create()
        .modelName("gpt-35-turbo")
        .modelParams(
            Map.of(
                "max_tokens", 50,
                "temperature", 0.1,
                "frequency_penalty", 0,
                "presence_penalty", 0));
```
