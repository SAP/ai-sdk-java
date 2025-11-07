# Logging Strategy

## Status: Proposed

## Context

We would like to log useful information that in future helps us and the user debug and resolve problems quickly. 

Currently, the user would have to enable wire logs to show information that enables us to assist them. 
We also identified than there is benefit to show a story of what is happening behind the scenes to the user which informs them on application progress.
We want to rely on application level logs from ai sdk for the same.

## Guidelines

- Don't log any sensitive information eg: names, ids etc. The general rule to follow is to avoid logging request or response payloads.
- Logs must contain useful and non-redundant information to keep them concise and to the point.
- Keep per-request log below INFO level to avoid littering the output and further making it difficult to identify related logs
- Prefer to keep one-time logs in a single application run at INFO level.
- Make use of MDC as appropriate and deliberate about clearing the context.
- MDC must not be cleared as a whole but only by key basis to avoid unintentionally clearing context items that merit a longer lifespan
- To be deliberate about logging, we limit logging out of generated classes, consequently limiting logs produced from modules like document-grounding, prompt-registry.
- Limit unnecessary warning logs to also avoid littering the console logging
- MDC is currently leveraged to contain and transport information when logging is performed on a different block down the flow of control chain. We need special care on where the context is set. Any per-request context set in MDC is clearing up at response completion. Consequently, setting per-request context outside of blocks/objects that are not invoked/consumed per-request can lead to incomplete MDC states which will in turn corrupt logs produced. 
- We are generally inclined towards descriptive and human-readable logs and ideally contain, service invoked, endpoint etc and request logging. We do not expect machine readability of logs but still following "metric=value" patterns for the sake of extensibility and readability of them.
- We may also correlate request level logs with request identifier reqId to assist debugging.
- In the case for logging information not simply read but involve computation or inference cost, we must deliberately deflect the effort based on log level or other available context.
- Centralize MDC handling. Using magic string as keys or values for the context may be lost in maintenance and add towards collaborative effort. We may wrap the access in a format that explicitly declares context keys and assist discoverability of all expected context items, so they are not lost in refactoring effort. 
- The responsibility of MDC context clearing lies with the class that sets the context for the sake of readability. 
- Explicitly log at request start and don't purely rely on logging at response time. A request may take long or even fail. Additionally, inferring response metrics even for a success case may be non-trivial eg: duration for streaming. The most critical benefit is immediate visibility for the user about the effect of their action for even long-running requests. Additionally, this avoids the the scenario where the surface error is ambiguous and needs investigation of stack-trace. 

### Unattended
- Unit test logging
- Logback
- logging pattern
- Separate logging into per request and per application runtime.
- A separate section for MDC Handling