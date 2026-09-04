## 0.X.0-SNAPSHOT

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/)

### 🚧 Known Issues

-

### 🔧 Compatibility Notes

- [RPT] SAP-RPT was updated to the newer 1.6.0 API
- [Orchestration] Spring AI support was upgraded to version `2.0.1`

#### Spring AI 2.0.1 Migration Guide

If you use the Spring AI integration (`OrchestrationChatModel`, `OpenAiChatModel`) together with tool calling, the following changes are required:

**Tool execution — use `ChatClient` instead of `isInternalToolExecutionEnabled`**

The `isInternalToolExecutionEnabled` flag has been removed in Spring AI 2.0.1.
To execute tools automatically, route the call through `ChatClient` (which wires in `ToolCallingAdvisor`):

```java
// Before
options.setInternalToolExecutionEnabled(true);
chatModel.call(prompt);

// After
ChatClient.builder(chatModel).build().prompt(prompt).call().chatResponse();
```

To receive raw tool calls without execution (e.g. to forward them to a client), call the model directly as before — no change needed there.

**Immutable options — use `.mutate().build()` to configure per-request options**

`OrchestrationChatOptions` and `DefaultToolCallingChatOptions` are now immutable.
Use the builder to set per-request options such as tool callbacks:

```java
// Before
OrchestrationChatOptions options = new OrchestrationChatOptions(config);
options.setToolCallbacks(...);  // no longer available

// After
OrchestrationChatOptions options = new OrchestrationChatOptions(config)
    .mutate()
    .toolCallbacks(ToolCallbacks.from(new MyTool()))
    .build();
```

**Dependency updates**

The `spring-ai-autoconfigure-mcp-client` artifact was split in Spring AI 2.0.1.
If you include MCP client autoconfiguration, replace:

```xml
<!-- Remove -->
<dependency>
  <groupId>org.springframework.ai</groupId>
  <artifactId>spring-ai-autoconfigure-mcp-client</artifactId>
</dependency>

<!-- Add -->
<dependency>
  <groupId>org.springframework.ai</groupId>
  <artifactId>spring-ai-autoconfigure-mcp-client-common</artifactId>
  <version>2.0.1</version>
</dependency>
<dependency>
  <groupId>org.springframework.ai</groupId>
  <artifactId>spring-ai-mcp-annotations</artifactId>
  <version>2.0.1</version>
</dependency>
```

### ✨ New Functionality

- [Grounding] Enabled presigned URL for Pipeline Documents.
- [RPT] Added `SAP_RPT_1_6` and `SAP_RPT_1_6_LARGE` to the model list in `RptModel`.

### 📈 Improvements

-

### 🐛 Fixed Issues

- [Realtime] Fixed possible race condition in Realtime API implementation
