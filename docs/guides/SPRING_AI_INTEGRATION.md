# Spring AI Integration

## Table of Contents

- [Introduction](#introduction)
- [Orchestration Chat Completion](#orchestration-chat-completion)
- [Orchestration Masking](#orchestration-masking)

## Introduction

This guide provides examples of how to use our Spring AI integration with our clients in SAP AI Core
for chat completion tasks using the SAP AI SDK for Java.

## Orchestration Chat Completion

The Orchestration client is integrated in Spring AI classes:

```java
ChatModel client = new OrchestrationChatModel();
OrchestrationModuleConfig config = new OrchestrationModuleConfig().withLlmConfig(GPT_35_TURBO);
OrchestrationChatOptions opts = new OrchestrationChatOptions(config);

Prompt prompt = new Prompt("What is the capital of France?", opts);
ChatResponse response = client.call(prompt);
```

Please
find [an example in our Spring Boot application](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/controllers/SpringAiOrchestrationController.java).

## Orchestration Masking

Configure Orchestration modules withing Spring AI:

```java
ChatModel client = new OrchestrationChatModel();
OrchestrationModuleConfig config = new OrchestrationModuleConfig().withLlmConfig(GPT_35_TURBO);

val masking =
    DpiMasking.anonymization()
        .withEntities(DPIEntities.EMAIL, DPIEntities.ADDRESS, DPIEntities.LOCATION);

val opts = new OrchestrationChatOptions(config.withMaskingConfig(masking));
val prompt =
    new Prompt(
        "Please write 'Hello World!' to me via email. My email address is foo.bar@baz.ai",
        opts);

ChatResponse response = client.call(prompt);
```

Please
find [an example in our Spring Boot application](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/controllers/SpringAiOrchestrationController.java).
