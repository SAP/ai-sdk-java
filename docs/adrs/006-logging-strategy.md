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

## Guidelines

### 1. Log Content and Security

- **Do not log sensitive information.**
  Never log full request or response bodies.
  Ensure that personally identifiable or confidential data — such as names, IDs, tokens, or payload content — is always excluded from logs.

- **Write concise and relevant logs.**
  Every log must convey meaningful information.
  Avoid verbose, repetitive, or purely cosmetic details.

- **Use descriptive, human-readable formats.**
  Logs must be clear enough for a developer to understand what happened without checking the code.
  Use the `metric=value` pattern to include structured details with extensibility in mind.

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
  Prefer **INFO level** only for one-time or startup/shutdown logs that occur once per application run.

* **Avoid unnecessary warnings.**
  Use the WARN level only for actionable or genuinely concerning conditions.
  Do not use it as a placeholder or for expected transient states.

* **Explicit request logging.**
  Always log at **request start** to provide immediate visibility that an operation has begun.
  This helps users understand that their request is being processed even before a result is available.
  Do not rely solely on response-time logging — requests may fail, hang, or take long durations.
  This approach also avoids the need for stack-trace investigation when surface error responses are ambiguous.

* **Performance-aware logging.**
  If a log statement requires expensive computation, guard it with a log-level check (e.g., `if (log.isDebugEnabled())`) to prevent performance degradation.

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
  Define them in a dedicated structure or utility (e.g., `RequestLogContext` class) to ensure discoverability and prevent errors during refactoring.

* **Responsibility and ownership.**
  The component or class that sets MDC context values is also responsible for clearing them.
  This maintains clarity and ensures proper lifecycle management.

---

### 4. Logging Boundaries and Generation

* **Deliberate logging boundaries.**
  Generated code (such as those in modules like *document-grounding* or *prompt-registry*) should log minimally or preferably be avoided entirely.
  Logging should be centralized in higher-level components to maintain consistency and reduce noise.
