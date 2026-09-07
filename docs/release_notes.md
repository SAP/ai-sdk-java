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

```diff
-options.setInternalToolExecutionEnabled(true);
-chatModel.call(prompt);

+ChatClient.builder(chatModel).build().prompt(prompt).call().chatResponse();
```

To receive raw tool calls without execution (e.g. to forward them to a client), call the model directly as before — no change needed there.

**Immutable options — use `.mutate().build()` to configure per-request options**

`OrchestrationChatOptions` and `DefaultToolCallingChatOptions` are now immutable.
Use the builder to set per-request options such as tool callbacks:

```diff
-OrchestrationChatOptions options = new OrchestrationChatOptions(config);
-options.setToolCallbacks(...);  // no longer available

+OrchestrationChatOptions options = new OrchestrationChatOptions(config)
+    .mutate()
+    .toolCallbacks(ToolCallbacks.from(new MyTool()))
+    .build();
```

**Dependency updates**

*Optional*: If your project uses Spring AI MCP autoconfiguration (i.e. you had `spring-ai-autoconfigure-mcp-client` in your `pom.xml` to register MCP clients from `application.yml`), that artifact was split in Spring AI 2.0.1 — replace it:

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
```

If you use MCP clients manually in code (instantiating `SyncMcpToolCallbackProvider` directly), no change is needed — `spring-ai-mcp` already provides those classes.

### ✨ New Functionality

-

### 📈 Improvements

-

### 🐛 Fixed Issues

-
