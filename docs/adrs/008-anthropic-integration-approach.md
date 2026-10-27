# Anthropic Integration: Approach

Status: Decided (2026-10-27).

## Background

SAP AI Core exposes Anthropic Claude models via a Bedrock-compatible HTTP API.
The SDK needs to provide Java users with access to these models.

This document covers the fundamental integration approach — how the SDK wraps or exposes the Anthropic API.
Downstream decisions about which sub-APIs to expose and how to handle client configuration are covered in
[009-anthropic-integration-client-design.md](009-anthropic-integration-client-design.md)
and depend on the outcome of this decision.

### Reference: how the OpenAI integration works

The existing `foundation-models/openai` module uses the **convenience classes** approach:
hand-rolled request/response POJOs (`OpenAiChatCompletionRequest`, `OpenAiChatCompletionResponse`, etc.)
and a custom `OpenAiClient` that wraps SAP's Apache HTTP client directly.

```java
// OpenAI — convenience classes approach (current implementation)
OpenAiChatCompletionResponse response =
    OpenAiClient.forModel(GPT_4O)
        .chatCompletion(new OpenAiChatCompletionRequest("What is the capital of France?"));

Stream<OpenAiChatCompletionDelta> stream =
    OpenAiClient.forModel(GPT_4O)
        .streamChatCompletionDeltas(new OpenAiChatCompletionRequest("Tell me a story"));
```

This gives full control over the API surface but requires ongoing maintenance as the OpenAI API evolves.
The Anthropic integration was an opportunity to evaluate whether this is the right pattern going forward.

---

## Options considered

### Option A: Convenience classes

Hand-rolled request/response POJOs and a custom HTTP client — the same pattern used by the OpenAI integration.

```java
// Hypothetical convenience classes approach
AnthropicChatCompletionResponse response =
    AnthropicClient.forModel(CLAUDE_45_HAIKU)
        .chatCompletion(new AnthropicChatCompletionRequest("What is the capital of France?"));

Stream<AnthropicChatCompletionDelta> stream =
    AnthropicClient.forModel(CLAUDE_45_HAIKU)
        .streamChatCompletionDeltas(new AnthropicChatCompletionRequest("Tell me a story"));
```

### Option B: Raw Bedrock invoke API

Expose the AWS Bedrock SDK `BedrockRuntimeClient` configured for SAP AI Core, with all serialization and response parsing left to the user.

```java
// Chat completion — user owns request serialization and response parsing
BedrockRuntimeClient bedrockClient = BedrockRuntimeClient.builder()
    .httpClient(SapBedrockHttpClient.forModel(CLAUDE_45_HAIKU))
    .build();

String requestBody = """
    {
      "anthropic_version": "bedrock-2023-05-31",
      "max_tokens": 1000,
      "messages": [{"role": "user", "content": "What is the capital of France?"}]
    }
    """;

InvokeModelResponse response = bedrockClient.invokeModel(
    InvokeModelRequest.builder()
        .modelId(CLAUDE_45_HAIKU.id())
        .body(SdkBytes.fromUtf8String(requestBody))
        .build());

String responseBody = response.body().asUtf8String();
// user parses responseBody JSON manually

// Streaming
bedrockClient.invokeModelWithResponseStream(
    InvokeModelWithResponseStreamRequest.builder()
        .modelId(CLAUDE_45_HAIKU.id())
        .body(SdkBytes.fromUtf8String(requestBody))
        .build(),
    InvokeModelWithResponseStreamResponseHandler.builder()
        .onEventStream(stream -> stream
            .filter(e -> e instanceof PayloadPart)
            .map(e -> ((PayloadPart) e).bytes().asUtf8String())
            .forEach(chunk -> { /* user parses each chunk JSON manually */ }))
        .build());
```

### Option C: Official Anthropic Java SDK (proposed)

Use `com.anthropic:anthropic-java-core` as the typed API layer.
Replace its HTTP transport (`com.anthropic.core.http.HttpClient`) with SAP's authenticated Apache HTTP client.
The bridge (`SapAnthropicHttpClient`) handles URL rewriting and Bedrock-style SSE format conversion transparently.

**Technical details:**
The SDK exposes a subset of the official `AnthropicClient` sub-clients (currently `messages()` and `completions()` — the only two supported by SAP AI Core's router).
Internally, `SapAnthropicHttpClient` intercepts every outgoing request, rewrites the Anthropic API URL (`/v1/messages`) to the SAP AI Core Bedrock invoke endpoint (`/invoke` or `/invoke-with-response-stream`), strips Anthropic-specific auth headers, and injects the `anthropic_version` field required by the Bedrock wire format.
Responses are rewritten in the opposite direction: Bedrock-style SSE (no `event:` field) is converted back to native Anthropic SSE before being handed to the SDK's event parser.
OAuth authentication is handled transparently by `ApacheHttpClient5Accessor` via SAP destination configuration — no Anthropic API key is needed.

**Reasons:**
- Simple on the user level API, allows for flexible and advanced configuration inside the SDK
- Utilizes available `invoke` API of the routing layer
- Works with available `AWS Bedrock` provider
- While official Anthropic API is exposed, backend can be flexibly replaced without breaking changes for AI SDK users

```java
// Chat completion
Message response =
    AiCoreAnthropicClient.forModel(CLAUDE_45_HAIKU)
        .client()
        .messages()
        .create(
            MessageCreateParams.builder()
                .model(CLAUDE_45_HAIKU.name())
                .maxTokens(1000)
                .addUserMessage("What is the capital of France?")
                .build());

String text = response.content().stream()
    .filter(ContentBlock::isText)
    .map(b -> b.asText().text())
    .collect(joining());

// Streaming
try (var stream =
    AiCoreAnthropicClient.forModel(CLAUDE_45_HAIKU)
        .client()
        .messages()
        .createStreaming(params)) {
  stream.stream()
      .filter(RawMessageStreamEvent::isContentBlockDelta)
      .map(e -> e.asContentBlockDelta().delta().asTextDelta().text())
      .forEach(System.out::print);
}
```

---

## Arguments

| | Option A: Convenience classes | Option B: Raw Bedrock invoke | Option C: Official Anthropic SDK |
|---|---|---|---|
| Maintenance effort | High — every upstream API change requires SDK updates (see OpenAI module history) | Near zero — no types owned | Low — SDK absorbs upstream changes |
| Code footprint | Large | Minimal | Small — bridge only: `AiCoreAnthropicClient`, `SapAnthropicHttpClient` |
| User value | Moderate — familiar but limited, SAP-specific API | Low — user owns JSON serialization, response parsing, and SSE chunk handling | High — official, fully-typed, well-documented Anthropic API |
| API stability | Fragile — SAP owns the contract | Stable at transport level; unstable at payload level — user owns the wire format | Stable — Anthropic owns and maintains the contract |
| Backend replaceability | Hard — POJOs tightly coupled to Bedrock wire format | Hard — user code is tightly coupled to Bedrock wire format | Easy — transport is swappable without breaking user code |

---

## Decision

**Option C — use the official Anthropic Java SDK.**

The `SapAnthropicHttpClient` bridges between `com.anthropic.core.http.HttpClient` and SAP's `ApacheHttpClient5Accessor`,
handling URL rewriting (`/v1/messages` → `/invoke`) and Bedrock SSE format conversion transparently.
Users interact exclusively with official Anthropic SDK types (`AnthropicClient`, `MessageCreateParams`, `Message`, `StreamResponse`, etc.).
