# OpenAI Chat Completion

## Table of Contents

- [Introduction](#introduction)
    - [New User Interface (v1.4.0)](#new-user-interface-v140)
- [Prerequisites](#prerequisites)
- [Maven Dependencies](#maven-dependencies)
- [Usage](#usage)
    - [Simple Chat Completion](#simple-chat-completion)
    - [Message History](#message-history)
    - [Chat Completion with Custom Model](#chat-completion-with-custom-model)
    - [Stream Chat Completion](#stream-chat-completion)
        - [Asynchronous Streaming](#asynchronous-streaming)
        - [Aggregating Total Output](#aggregating-total-output)
        - [Spring Boot Example](#spring-boot-example)

## Introduction

This guide demonstrates how to use the SAP AI SDK for Java to perform chat completion tasks using OpenAI models deployed on SAP AI Core.

### New User Interface (v1.4.0)

We're excited to introduce a new user interface for OpenAI chat completions starting with **version 1.4.0**. This update is designed to improve the SDK by:

- **Decoupling Layers:** Separating the convenience layer from the model classes to deliver a more stable and maintainable experience.
- **Staying Current:** Making it easier for the SDK to adapt to the latest changes in the OpenAI API specification.
- **Consistent Design:** Aligning with the Orchestrator convenience API for a smoother transition and easier adoption.

**Please Note:**

- The new interface is gradually being rolled out across the SDK.
- We welcome your feedback to help us refine this interface.
- The existing interface (v1.0.0) remains available for compatibility.

## Prerequisites

Before using the AI Core module, ensure that you have met all the general requirements outlined in the [README.md](../../README.md#general-requirements).
Additionally, include the necessary Maven dependency in your project.

### Maven Dependencies

Add the following dependency to your `pom.xml` file:

```xml
<dependencies>
    <dependency>
        <groupId>com.sap.ai.sdk.foundationmodels</groupId>
        <artifactId>openai</artifactId>
        <version>${ai-sdk.version}</version>
    </dependency>
</dependencies>
```

See [an example pom in our Spring Boot application](../../sample-code/spring-app/pom.xml)

## Usage

In addition to the prerequisites above, we assume you have already set up the following to carry out the examples in this guide:

- **A Deployed OpenAI Model in SAP AI Core**
    - Refer
      to [How to deploy a model to AI Core](https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/create-deployment-for-generative-ai-model-in-sap-ai-core)
      for setup instructions.
    - In case the model is deployed in a custom resource group, refer to [this section](#using-a-custom-resource-group).
    - <details>
      <summary>Example deployed model from the AI Core <code>/deployments</code> endpoint</summary>

      ```json
      {
        "id": "d123456abcdefg",
        "deploymentUrl": "https://api.ai.region.aws.ml.hana.ondemand.com/v2/inference/deployments/d123456abcdefg",
        "configurationId": "12345-123-123-123-123456abcdefg",
        "configurationName": "gpt-4o-mini",
        "scenarioId": "foundation-models",
        "status": "RUNNING",
        "statusMessage": null,
        "targetStatus": "RUNNING",
        "lastOperation": "CREATE",
        "latestRunningConfigurationId": "12345-123-123-123-123456abcdefg",
        "ttl": null,
        "details": {
          "scaling": {
            "backendDetails": {}
          },
          "resources": {
            "backendDetails": {
              "model": {
                "name": "gpt-4o-mini",
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
      ```

      </details>

## Simple chat completion

```java
var result =
    OpenAiClient.forModel(GPT_4O_MINI)
        .withSystemPrompt("You are a helpful AI")
        .chatCompletion("Hello World! Why is this phrase so famous?");

String resultMessage = result.getContent();
```

## Using a Custom Resource Group

```java
var destination = new AiCoreService()
    .getInferenceDestination("custom-rg")
    .forModel(GPT_4O);
OpenAiClient.withCustomDestination(destination);
```

## Message history

**Since v1.4.0**

```java
var request =
    new OpenAiChatCompletionRequest(
        OpenAiMessage.system("You are a helpful assistant"),
        OpenAiMessage.user("Hello World! Why is this phrase so famous?"));

var response = OpenAiClient.forModel(GPT_4O).chatCompletion(request).getContent();
```

<details>
<summary><b>Since v1.0.0</b></summary>

```java
var systemMessage =
    new OpenAiChatSystemMessage().setContent("You are a helpful assistant");
var userMessage =
    new OpenAiChatUserMessage().addText("Hello World! Why is this phrase so famous?");
var request =
    new OpenAiChatCompletionParameters().addMessages(systemMessage, userMessage);

var result = OpenAiClient.forModel(GPT_4O_MINI).chatCompletion(request);

String resultMessage = result.getContent();
```

See [an example in our Spring Boot application](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/services/OpenAiService.java)

</details>

## Chat Completion with Specific Model Version

By default, when no version is specified, the system selects one of the available deployments of the specified model, regardless of its version.
To target a specific version, you can specify the model version along with the model.

```java
OpenAiChatCompletionOutput result =
    OpenAiClient.forModel(GPT_4O_MINI.withVersion("1106")).chatCompletion(request);
```

## Chat completion with Custom Model

You can also use a custom OpenAI model for chat completion by creating an `OpenAiModel` object. 

```java
OpenAiChatCompletionOutput result =
    OpenAiClient.forModel(new OpenAiModel("custom-model", "v1")).chatCompletion(request);
```

Ensure that the custom model is deployed in SAP AI Core.

## Stream chat completion

It's possible to pass a stream of chat completion delta elements, e.g. from the application backend to the frontend in real-time.

### Asynchronous Streaming - Blocking

This is a blocking example for streaming and printing directly to the console:

```java
String msg = "Can you give me the first 100 numbers of the Fibonacci sequence?";

OpenAiClient client = OpenAiClient.forModel(GPT_4O_MINI);

// try-with-resources on stream ensures the connection will be closed
try (Stream<String> stream = client.streamChatCompletion(msg)) {
    stream.forEach(
        deltaString -> {
            System.out.print(deltaString);
            System.out.flush();
        });
}
```

### Asynchronous Streaming - Non-blocking

**Since v1.4.0**

The following example demonstrate how you can leverage a concurrency-safe container (like an AtomicReference) to "listen" for usage information in any incoming delta.

```java
String question = "Can you give me the first 100 numbers of the Fibonacci sequence?";
var userMessage = OpenAiMessage.user(question);
var request = new OpenAiChatCompletionRequest(userMessage);

OpenAiClient client = OpenAiClient.forModel(GPT_4O);
var usageRef = new AtomicReference<CompletionUsage>();

// Prepare the stream before starting the thread to handle any initialization exceptions
Stream<OpenAiChatCompletionDelta> stream = client.streamChatCompletionDeltas(request);

// Create a new thread for asynchronous, non-blocking processing
Thread streamProcessor =
    new Thread(
        () -> {
            // try-with-resources ensures the stream is closed after processing
            try (stream) {
                stream.forEach(
                    delta -> {
                        usageRef.compareAndExchange(null, delta.getCompletionUsage());
                        System.out.println("Content: " + delta.getDeltaContent());
                    });
            }
        });

// Start the processing thread; the main thread remains free (non-blocking)
streamProcessor.start();
// Wait for the thread to finish (blocking)
streamProcessor.join();

// Access information caught from completion usage
Integer tokensUsed = usageRef.get().getCompletionTokens();
System.out.println("Tokens used: " + tokensUsed);
```

<details>
<summary><b>Since v1.0.0</b></summary>

The following example is non-blocking and demonstrates how to aggregate the complete response.
Any asynchronous library can be used, such as the classic Thread API.

```java
var question = "Can you give me the first 100 numbers of the Fibonacci sequence?";

var userMessage =
    new OpenAiChatMessage.OpenAiChatUserMessage().addText(question);
var requestParameters =
    new OpenAiChatCompletionParameters().addMessages(userMessage);

var client = OpenAiClient.forModel(GPT_4O_MINI);
var totalOutput = new OpenAiChatCompletionOutput();

// Prepare the stream before starting the thread to handle any initialization exceptions
Stream<OpenAiChatCompletionDelta> stream =
    client.streamChatCompletionDeltas(requestParameters);

var streamProcessor =
    new Thread(
        () -> {
          // try-with-resources ensures the stream is closed after processing
          try (stream) {
            stream.peek(totalOutput::addDelta).forEach(System.out::println);
          }
        });

streamProcessor.start(); // Start processing in a separate thread (non-blocking)
streamProcessor.join(); // Wait for the thread to finish (blocking)

// Access aggregated information from total output
Integer tokensUsed = totalOutput.getUsage().getCompletionTokens();
System.out.println("Tokens used: " + tokensUsed);
```

Please find [an example in our Spring Boot application](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/services/OpenAiService.java). It shows the usage of Spring
Boot's `ResponseBodyEmitter` to stream the chat completion delta messages to the frontend in real-time.

</details>

## Embedding

**Since v1.4.0**

Get the embeddings of a text input in list of float values:

```java
var request = new OpenAiEmbeddingRequest(List.of("Hello World"));

OpenAiEmbeddingResponse response = OpenAiClient.forModel(TEXT_EMBEDDING_3_SMALL).embedding(request);
float[] embedding = embedding.getEmbeddings().get(0);
```

<details>
<summary><b>Since v1.0.0</b></summary>

```java
var request = new OpenAiEmbeddingParameters().setInput("Hello World");

OpenAiEmbeddingOutput embedding = OpenAiClient.forModel(TEXT_EMBEDDING_3_SMALL).embedding(request);

float[] embedding = embedding.getData().get(0).getEmbedding();
```

See [an example in our Spring Boot application](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/services/OpenAiService.java)

</details>
