# Spring AI Integration

## Table of Contents

- [Introduction](#introduction)
- [Orchestration](#orchestration)
  - [Chat Completion](#chat-completion)
  - [Masking](#masking)
  - [Stream chat completion](#stream-chat-completion)
  - [Tool Calling](#tool-calling)
  - [Chat Memory](#chat-memory)
- [OpenAI](#openai)
  - [Embedding](#embedding)


## Introduction

This guide provides examples of how to use our Spring AI integration with our clients in SAP AI Core
for chat completion tasks using the SAP AI SDK for Java.

First, add the Spring AI dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-core</artifactId>
    <version>1.0.0-M5</version>
</dependency>
...
<repository>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
    <id>spring-milestones</id>
    <name>Spring Milestones</name>
    <url>https://repo.spring.io/milestone</url>
</repository>
```

> [!NOTE]
> Note that currently no stable version of Spring AI exists just yet.
> The AI SDK currently uses the [M6 milestone](https://spring.io/blog/2025/02/14/spring-ai-1-0-0-m6-released).
>
> Please be aware that future versions of the AI SDK may increase the Spring AI version.

## Orchestration

### Chat Completion

The Orchestration client is integrated in Spring AI classes:

```java
ChatModel client = new OrchestrationChatModel();
OrchestrationModuleConfig config = new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI);
OrchestrationChatOptions opts = new OrchestrationChatOptions(config);

Prompt prompt = new Prompt("What is the capital of France?", opts);
ChatResponse response = client.call(prompt);
```

Please find [an example in our Spring Boot application](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/services/SpringAiOrchestrationService.java).

### Masking

Configure Orchestration modules withing Spring AI:

```java
ChatModel client = new OrchestrationChatModel();
OrchestrationModuleConfig config = new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI);

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
find [an example in our Spring Boot application](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/services/SpringAiOrchestrationService.java).

### Stream chat completion

It's possible to pass a stream of chat completion delta elements, e.g. from the application backend
to the frontend in real-time.

```java
ChatModel client = new OrchestrationChatModel();
OrchestrationModuleConfig config = new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI);
OrchestrationChatOptions opts = new OrchestrationChatOptions(config);

Prompt prompt =
    new Prompt(
        "Can you give me the first 100 numbers of the Fibonacci sequence?", opts);
Flux<ChatResponse> flux = client.stream(prompt);

// also possible to keep only the chat completion text
Flux<String> responseFlux =
    flux.map(chatResponse -> chatResponse.getResult().getOutput().getContent());
```

_Note: A Spring endpoint can return `Flux` instead of `ResponseEntity`._

Please find [an example in our Spring Boot application](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/services/SpringAiOrchestrationService.java).

### Tool Calling

First define a function that will be called by the LLM:

```java
class WeatherMethod {
  enum Unit {C,F}
  record Request(String location, Unit unit) {}
  record Response(double temp, Unit unit) {}

  @Tool(description = "Get the weather in location")
  Response getCurrentWeather(@ToolParam Request request) {
    int temperature = request.location.hashCode() % 30;
    return new Response(temperature, request.unit);
  }
}
```

Then add your tool to the options:

```java
ChatModel client = new OrchestrationChatModel();
OrchestrationModuleConfig config = new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI);
OrchestrationChatOptions opts = new OrchestrationChatOptions(config);

options.setToolCallbacks(List.of(ToolCallbacks.from(new WeatherMethod())));

options.setInternalToolExecutionEnabled(false);// tool execution is not yet available in orchestration

Prompt prompt = new Prompt("What is the weather in Potsdam and in Toulouse?", options);

ChatResponse response = client.call(prompt);
```

Please find [an example in our Spring Boot application](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/services/SpringAiOrchestrationService.java).

## Chat Memory

Create a Spring AI `ChatClient` from our `OrchestrationChatModel` and add a chat memory advisor like so:

```java
ChatModel client = new OrchestrationChatModel();
OrchestrationModuleConfig config = new OrchestrationModuleConfig().withLlmConfig(GPT_4O_MINI);
OrchestrationChatOptions opts = new OrchestrationChatOptions(config);

val memory = new InMemoryChatMemory();
val advisor = new MessageChatMemoryAdvisor(memory);
val cl = ChatClient.builder(client).defaultAdvisors(advisor).build();

Prompt prompt1 = new Prompt("What is the capital of France?", defaultOptions);
String content1 = cl.prompt(prompt1).call().content();
// content1 is "Paris"

Prompt prompt2 = new Prompt("And what is the typical food there?", defaultOptions);
String content2 = cl.prompt(prompt2).call().content();
// chat memory will remember that the user is inquiring about France.
```

Please find [an example in our Spring Boot application](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/services/SpringAiOrchestrationService.java).

## OpenAI

### Introduction

Our OpenAI client is integrated in Spring AI classes:

### Embedding

Here is how to obtain embedding vectors for a list of strings:

You first initialize the OpenAI client for your model of choice and attach it  `OpenAiSpringEmbeddingModel` object.

```java
OpenAiClient client = OpenAiClient.forModel(OpenAiModel.TEXT_EMBEDDING_3_SMALL);
OpenAiSpringEmbeddingModel embeddingModel = new OpenAiSpringEmbeddingModel(client);
List<String> texts = List.of("Hello", "World");
float[] embeddings = embeddingModel.embed(texts);
```

Please find [an example in our Spring Boot application](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/services/SpringAiOpenAiService.java).