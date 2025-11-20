# Logging Strategy

## Status

Accepted

## Context

The AI SDK identified some challenges with debugging and problem resolution that can be addressed with better logging.
Typically, users have to enable wire logs for troubleshooting which, along with helpful logs, surfaces a large volume of unrelated ones.
Additionally, we identified the need to improve visibility into the application progress to the user.

Key drivers for this decision include:

- **Debugging difficulties**: Limited visibility into request flows.
- **Security risks**: Accidental logging of sensitive data.
- **Poor user experience**: Lack of insight into application progress.
- **Troubleshooting dependency**: Reliance on external parties for critical details.

## Decision

We will implement and enforce comprehensive logging guidelines that prioritize **debugging capability** and **user visibility**.
This approach mandates descriptive, human-readable logs with structured request tracking through Mapped Diagnostic Context (MDC).
The SDK uses SLF4J API for all logging statements.

## Guidelines

### 1. Log Content and Security

* **Do not log sensitive information.**
  Never log full request or response bodies.
  Ensure that personally identifiable or confidential data — such as names, IDs, tokens, or payload content — is always excluded from logs.

* **Write concise and relevant logs.**
  Every log must convey meaningful information.
  Avoid verbose, repetitive, or purely cosmetic details.

* **Use descriptive, human-readable and standard formats.**
  - Logs must be clear enough for a developer to understand what happened without checking the code.
  - Start log messages with a capital letter (exceptions include case-sensitive identifiers) and end with a period (`.`).
  - Avoid newlines (`\n`) and emojis within log messages to prevent parsing and encoding concerns.
  - Use the `metric=value` pattern to include structured details with extensibility in mind.

    ```
    [callId=e3eaa45c] OpenAI request completed successfully with duration=1628ms, responseSize=1.2KB.
    ```

* **Correlate logs.**
  Include a call identifier (e.g., `callId`) in outgoing per-request logs to assist with correlation and debugging.
  Throughout this document, the term 'request' refers to a single SDK operation that calls an AI service (e.g., `OrchestrationClient.chatCompletion()`, `OpenAiClient.embed()`), distinct from HTTP requests to the user's application.

* **Exception logging.**
  When logging exceptions, use standard logging methods (e.g., `log.debug("Connection lost.", exception)`) rather than serializing exception objects.
  Exception objects may contain custom fields with sensitive data that could be exposed through JSON serialization or custom `toString()` implementations.

* **Logging framework**
  Write all logging statements against the SLF4J API.
  The AI SDK relies on `logback-classic` as the provided runtime implementation.

---

### 2. Log Levels and Scope

* **Per-request logs.**
  Keep per-request logs **below INFO level** (e.g., DEBUG or TRACE) to prevent cluttering normal application output.

* **Application runtime logs.**
  Prefer **INFO level** only for one-time or startup/shutdown logs that occur once per application run.

* **Avoid unnecessary warnings.**
  Use the WARN level only for actionable or genuinely concerning conditions.
  Expected transient states (retries, fallbacks, cache misses) should not generate a warning.

* **Explicit request logging.**
  Always log at **request start** to provide immediate visibility that an operation has begun.
  This helps users understand that their request is being processed even before a result is available.
  Do not rely solely on response-time logging — requests may fail, hang, or take long durations.
  This approach also avoids the need for stack-trace investigation when surface error responses are ambiguous.

  ```
  [callId=e3eaa45c] Starting OpenAI synchronous request to /v1/chat/completions, destination=<some-uri>.
  ```

* **Performance-aware logging.**
  If a log statement requires computation (such as object creation or method invocation), guard it with a log-level check to prevent performance degradation.

  ``` java
  Optional<Destination> maybeDestination;
  Destination fallbackDestination = /* existing instance */;
  
  // Bad: Creates objects or invokes methods even when logging is disabled
  log.debug("Destination: {}", maybeDestination.toString());
  log.debug("Destination: {}", maybeDestination.orElseGet(() -> new Destination()));
  log.debug("Destination: {}", maybeDestination.orElseGet(this::getFallback));
  log.debug("Destination: {}", maybeDestination.orElse(getFallback()));
  
  // Good: No additional computation
  log.debug("Destination: {}", maybeDestination);
  log.debug("Destination: {}", maybeDestination.orElse(fallbackDestination));
  
  // Good: Guard object creation
  if (log.isDebugEnabled()) {
    log.debug("Destination: {}", maybeDestination.orElse(getFallback()));
  }
  
  // Exception: Singletons require no guarding (no object creation)
  Optional<List<Destination>> maybeDestinations;
  log.debug("Destinations: {}", maybeDestinations.orElse(Collections.emptyList()));
  ```

---

### 3. MDC (Mapped Diagnostic Context)

* **Purpose and usage.**
  MDC is used to carry contextual information (e.g., `callId`, `endpoint`, `service`) across execution blocks within the same thread.

* **Setting and clearing context.**
  Set MDC values deliberately and close to their scope of relevance.
  Per-request MDC context must be cleared appropriately when the response completes.
  Avoid setting per-request values in long-lived objects that outlive the request lifecycle, as this can result in corrupted or incomplete log context.

* **Centralized MDC management.**
  Avoid using magic strings for MDC keys or values.
  Define them in a dedicated structure or utility (e.g., `RequestLogContext` class) to ensure discoverability and prevent errors during refactoring.
  ```java
  // Bad: Magic strings scattered in code
  MDC.put("service", "OpenAI");
  log.debug("Service {}", MDC.get("service"));
  
  // Good: Centralized in utility class
  RequestLogContext.setService(Service.OPENAI);
  log.debug("Service {}", RequestLogContext.get(MdcKeys.SERVICE));
  ```

* **Granular clearing only.**
  Never clear the entire MDC context, as this will remove entries set by the application developer or other libraries.
  Instead, remove entries key-by-key to preserve unrelated context items that may remain valid for longer periods.
  ```java
  // Bad: Risk clearing useful context entries from other components
  MDC.clear();

  // Good: Clear only your own entries, centralized in utility class
  class RequestLogContext { 
     //...
     static void clear(){
      MDC.remove(MdcKeys.CALL_ID);
      MDC.remove(MdcKeys.SERVICE);
    }
  }
  ```

* **Responsibility and ownership.**
  The component or class that sets MDC context values is also responsible for clearing them.
  This maintains clarity and ensures proper lifecycle management.

* **Safe consumption.**
  Since MDC uses `ThreadLocal` storage, any new thread (created implicitly or explicitly) will not have access to the parent thread's MDC context.
  Always audit for thread switches through async operations, resilience patterns etc., as these may lead to corrupted logs due to invalid MDC.

  ```java
  // Thread A
  RequestLogContext.setCallId("abc123");
  log.debug("[callId={}] Starting request", RequestLogContext.get(MdcKeys.CALL_ID)); 
  
  // Problem: Async callback runs in Thread B without original MDC context
  client.executeAsync(() -> {
      // Thread B: RequestLogContext.get(MdcKeys.CALL_ID) returns null
      log.debug("[callId={}] Processing", RequestLogContext.get(MdcKeys.CALL_ID)); 
  });
  ```

  To maintain logging context across thread boundaries, manually propagate the MDC context:

  ```java
  // Capture parent thread's MDC context
  Map<String, String> context = MDC.getCopyOfContextMap();
  
  client.executeAsync(() -> {
    // Restore the captured context in new thread
    MDC.setContextMap(context);
    // Thread B: RequestLogContext.get(MdcKeys.CALL_ID) returns abc123
    log.debug("[callId={}] Processing", RequestLogContext.get(MdcKeys.CALL_ID));
  });
  ```
---

### 4. Logging Boundaries and Generation

* **Deliberate logging boundaries.**
  Generated code (such as those in modules like *document-grounding* or *prompt-registry*) should log minimally or preferably be avoided entirely.
  Logging should be centralized in higher-level components to maintain consistency and reduce noise.
