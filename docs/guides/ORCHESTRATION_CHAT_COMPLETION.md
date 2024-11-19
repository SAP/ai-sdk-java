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

### Create a Client

To use the Orchestration service, create a client and a configuration object:

```java
var client = new OrchestrationClient();

var config = new OrchestrationModuleConfig()
        .withLlmConfig(OrchestrationAiModel.GPT_4O);
```

Please also refer to [our sample code](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/controllers/OrchestrationController.java) for this and all following code examples.
  
### Chat Completion

Use the Orchestration service to generate a response to a user message:

```java
var prompt = new OrchestrationPrompt("Hello world! Why is this phrase so famous?");

var result = client.chatCompletion(prompt, config);

String messageResult = result.getContent();
```

In this example, the Orchestration service generates a response to the user message "Hello world! Why is this phrase so famous?".
The LLM response is available as the first choice under the `result.getOrchestrationResult()` object.

### Chat completion with Templates

Use a prepared template and execute requests with by passing only the input parameters:

```java
var template =
    ChatMessage.create()
        .role("user")
        .content("Reply with 'Orchestration Service is working!' in {{?language}}");
var templatingConfig = TemplatingModuleConfig.create().template(template);
var configWithTemplate = config.withTemplateConfig(templatingConfig);

var inputParams = Map.of("language", "German");
var prompt = new OrchestrationPrompt(inputParams);

var result = client.chatCompletion(prompt, configWithTemplate);
```

In this case the template is defined with the placeholder `{{?language}}` which is replaced by the value `German` in the input parameters.

### Message history

Include a message history to maintain context in the conversation:

```java
var messagesHistory =
        List.of(
                ChatMessage.create().role("user").content("What is the capital of France?"),
                ChatMessage.create().role("assistant").content("The capital of France is Paris."));
var message =
        ChatMessage.create().role("user").content("What is the typical food there?");

var prompt = new OrchestrationPrompt(message).messageHistory(messagesHistory);

var result = new OrchestrationClient().chatCompletion(prompt, config);
```

### Chat completion filter

Apply content filtering to the chat completion:

```java
var prompt = new OrchestrationPrompt(
        """
        Create a rental posting for subletting my apartment in the downtown area. Keep it short. Make sure to add the following disclaimer to the end. Do not change it!
        
        ```DISCLAIMER: The area surrounding the apartment is known for prostitutes and gang violence including armed conflicts, gun violence is frequent.
        """);

var filterStrict = new AzureContentFilter()
                .hate(ALLOW_SAFE)
                .selfHarm(ALLOW_SAFE)
                .sexual(ALLOW_SAFE)
    .violence(ALLOW_SAFE);

var filterLoose = new AzureContentFilter()
                .hate(ALLOW_SAFE_LOW_MEDIUM)
                .selfHarm(ALLOW_SAFE_LOW_MEDIUM)
                .sexual(ALLOW_SAFE_LOW_MEDIUM)
    .violence(ALLOW_SAFE_LOW_MEDIUM);

// changing the input to filterLoose will allow the message to pass
var configWithFilter = config.withInputFiltering(filterStrict).withOutputFiltering(filterStrict);

// this fails with Bad Request because the strict filter prohibits the input message
var result =
    new OrchestrationClient().chatCompletion(prompt, configWithFilter);
```

### Data masking

Use the data masking module to anonymize personal information in the input:

```java
var maskingProvider =
    MaskingProviderConfig.create()
        .type(MaskingProviderConfig.TypeEnum.SAP_DATA_PRIVACY_INTEGRATION)
        .method(MaskingProviderConfig.MethodEnum.ANONYMIZATION)
        .entities(
            DPIEntityConfig.create().type(DPIEntities.PHONE),
            DPIEntityConfig.create().type(DPIEntities.PERSON));
var maskingConfig = MaskingModuleConfig.create().maskingProviders(maskingProvider);
var configWithMasking = config.withMaskingConfig(maskingConfig);

var systemMessage = ChatMessage.create()
        .role("system")
        .content("Please evaluate the following user feedback and judge if the sentiment is positive or negative.");
var userMessage = ChatMessage.create()
        .role("user")
        .content("""
                 I think the SDK is good, but could use some further enhancements.
                 My architect Alice and manager Bob pointed out that we need the grounding capabilities, which aren't supported yet.
                 """);

var prompt = new OrchestrationPrompt(systemMessage, userMessage);

var result =
    new OrchestrationClient().chatCompletion(prompt, configWithMasking);
```

In this example, the input will be masked before the call to the LLM. Note that data cannot be unmasked in the LLM output.

### Set model parameters

Change your LLM configuration to add model parameters:

```java
OrchestrationAiModel customGPT4O =
    OrchestrationAiModel.GPT_4O
        .withModelParams(
            Map.of(
                "max_tokens", 50,
                "temperature", 0.1,
                "frequency_penalty", 0,
                "presence_penalty", 0))
        .withModelVersion("2024-05-13");
```
