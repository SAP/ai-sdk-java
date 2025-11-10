# Logging Strategy

## Status

Proposed

## Context

The AI SDK identified some challenges with debugging and problem resolution that can be addressed with better logging.
Typically, users had to enable wire logs to access information necessary for troubleshooting which, along with helpful logs, surfaces a large volume of unrelated ones.
Additionally, we identified the need to improve visibility into what's happening behind the scenes about application progress to the user.

Key challenges that drove this decision:

- **Debugging difficulties**: Limited visibility into request flows and processing steps
- **Security concerns**: Risk of accidentally logging sensitive information
- **User experience**: Users needed better insight into long-running AI operations
- **Trace ownership**: Reliance on external parties for troubleshooting details

## Decision

Devise and follow comprehensive logging guidelines that prioritize **debugging capability** and **user visibility** while maintaining **security** and **performance**.
The approach emphasizes descriptive, human-readable logs with structured request tracking through MDC (Mapped Diagnostic Context).

## Guidelines

### 1. Content and Security

* **Avoid sensitive information.**
  As a general rule, avoid logging full request or response bodies.
  Do not log any personally identifiable or confidential data such as names, IDs, tokens, or payload content.

* **Keep logs concise and relevant.**
  Every log should convey meaningful information without redundancy.
  Avoid verbose, repetitive, or cosmetic details.

* **Use descriptive, human-readable formats.**
  Logs should read naturally and provide enough context for a developer to understand what happened without consulting the code.
  While descriptiveness is a priority, follow the `metric=value` pattern where practical to include additional details with extensibility in mind.
  Check out the example below.

  ```[reqId=e3eaa45c] OpenAI request completed successfully with duration=1628ms, size=1,2KB.```

* **Correlate logs.**
  Include a request identifier (e.g., `reqId`) in per-request logs to assist with correlation and debugging.

* **Exception logging.**
  When logging exceptions, use standard logging methods (e.g., `log.error("Operation failed", exception)`) rather than serializing exception objects.
  Exception objects may contain custom fields with sensitive data that could be exposed through JSON serialization or custom `toString()` implementations.

---

### 2. Log Levels and Scope

* **Per-request logs.**
  Keep per-request logs **below INFO level** (e.g., DEBUG or TRACE) to prevent cluttering normal application output.

* **Application runtime logs.**
  Prefer **INFO level** for one-time or startup/shutdown logs that occur once per application run.

* **Avoid unnecessary warnings.**
  Use the WARNING level only for actionable or genuinely concerning conditions.
  Do not use it as a placeholder or for expected transient states.

* **Explicit request logging.**
  Always log at **request start** to provide immediate visibility that an operation has begun.
  This helps users understand that their request is being processed even before a result is available.
  Do not rely solely on response-time logging — requests may fail, hang, or take long durations.
  This approach also avoids the need for stack-trace investigation when surface error responses are ambiguous.

* **Performance-aware logging.**
  If a log statement requires computation or inference to generate information, guard it with a log-level check (e.g., `if (log.isDebugEnabled())`) or other available context to avoid unnecessary overhead.

---

### 3. MDC (Mapped Diagnostic Context)

* **Purpose and usage.**
  MDC is used to carry contextual information (e.g., `reqId`, `endpoint`, `service`) across execution blocks within the same thread.

* **Setting and clearing context.**
  Set MDC values deliberately and close to their scope of relevance.
  Per-request MDC context must be cleared when the response completes.
  Avoid setting per-request values in long-lived objects that outlive the request lifecycle, as this can result in corrupted or incomplete log context.

* **Granular clearing only.**
  Never clear the entire MDC context.
  Instead, remove entries key-by-key to preserve unrelated context items that may remain valid for longer periods.

* **Centralized MDC management.**
  Avoid using magic strings for MDC keys or values.
  Define them in a dedicated structure or utility (e.g., `RequestlogContext` class) to ensure discoverability and prevent errors during refactoring.

* **Responsibility and ownership.**
  The component or class that sets MDC context values is also responsible for clearing them.
  This maintains clarity and ensures proper lifecycle management.

---

### 4. Logging Boundaries and Generation

* **Deliberate logging boundaries.**
  Generated code (such as those in modules like *document-grounding* or *prompt-registry*) should log minimally or preferably be avoided entirely.
  Logging should be centralized in higher-level components to maintain consistency and reduce noise.
