# OpenAI Chat Completion

## Table of Contents

- [Introduction](#introduction)
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

This guide demonstrates how to use the SAP AI SDK for Java to perform chat completion tasks using OpenAI models deployed
on SAP AI Core.

## Prerequisites

Before using the AI Core module, ensure that you have met all the general requirements outlined in
the [README.md](../../README.md#general-requirements). 

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

In addition to the prerequisites above, we assume you have already set up the following to carry out the examples in
this guide:

- **A Deployed OpenAI Model in SAP AI Core**
    - Refer
      to [How to deploy a model to AI Core](https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/create-deployment-for-generative-ai-model-in-sap-ai-core)
      for setup instructions.
    - <details>
      <summary>Example deployed model from the AI Core <code>/deployments</code> endpoint</summary>

      ```json
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
            "backend_details": {}
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
      ```

      </details>

### Simple chat completion

```java
final OpenAiChatCompletionOutput result =
    OpenAiClient.forModel(GPT_35_TURBO)
        .withSystemPrompt("You are a helpful AI")
        .chatCompletion("Hello World! Why is this phrase so famous?");

final String resultMessage = result.getContent();
```

### Message history

```java
final var systemMessage =
    new OpenAiChatSystemMessage().setContent("You are a helpful assistant");
final var userMessage =
    new OpenAiChatUserMessage().addText("Hello World! Why is this phrase so famous?");
final var request =
    new OpenAiChatCompletionParameters().addMessages(systemMessage, userMessage);

final OpenAiChatCompletionOutput result =
    OpenAiClient.forModel(GPT_35_TURBO).chatCompletion(request);

final String resultMessage = result.getContent();
```

See [an example in our Spring Boot application](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/controllers/OpenAiController.java)

### Chat Completion with Custom Model

```java
final OpenAiChatCompletionOutput result =
    OpenAiClient.forModel(new OpenAiModel("model")).chatCompletion(request);
```

### Stream chat completion

It's possible to pass a stream of chat completion delta elements, e.g. from the application backend to the frontend in
real-time.

#### Asynchronous Streaming

This is a blocking example for streaming and printing directly to the console:

```java
String msg = "Can you give me the first 100 numbers of the Fibonacci sequence?";

OpenAiClient client = OpenAiClient.forModel(GPT_35_TURBO);

// try-with-resources on stream ensures the connection will be closed
try (Stream<String> stream = client.streamChatCompletion(msg)) {
    stream.forEach(
        deltaString -> {
            System.out.print(deltaString);
            System.out.flush();
        });
}
```

#### Aggregating Total Output

The following example is non-blocking and demonstrates how to aggregate the complete response. Any asynchronous library
can be used, such as the classic Thread API.

```java
final var message = "Can you give me the first 100 numbers of the Fibonacci sequence?";

final OpenAiChatMessage.OpenAiChatUserMessage userMessage =
    new OpenAiChatMessage.OpenAiChatUserMessage().addText(message);
final OpenAiChatCompletionParameters requestParameters =
    new OpenAiChatCompletionParameters().addMessages(userMessage);

final OpenAiClient client = OpenAiClient.forModel(GPT_35_TURBO);
final var totalOutput = new OpenAiChatCompletionOutput();

// Prepare the stream before starting the thread to handle any initialization exceptions
final Stream<OpenAiChatCompletionDelta> stream =
    client.streamChatCompletionDeltas(requestParameters);

final var streamProcessor =
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
final Integer tokensUsed = totalOutput.getUsage().getCompletionTokens();
System.out.println("Tokens used: " + tokensUsed);
```

#### Spring Boot example

Please
find [an example in our Spring Boot application](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/controllers/OpenAiController.java).
It shows the usage of Spring Boot's `ResponseBodyEmitter` to stream the chat completion delta messages to the frontend
in real-time.
