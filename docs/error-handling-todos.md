## Recommended test changes

1. Strengthen existing failure cases: assert all three getters, including expected nulls; verify response status, request method/path/body, and parsed error contents.
2. Add core regressions: valid SSE error events, streaming body-read failures, and successful-status responses with missing or malformed bodies.
3. Add client-level regressions: serialization failures, transport failures/timeouts, and errors raised during lazy stream consumption.
4. Extend filtering tests: preserve the specialized exception and filter details while checking the agreed HTTP-context contract.
5. Not every field should always be non-null: transport failures have no response; malformed or absent payloads have no parsed clientError. Core handlers currently receive no request, so request propagation needs client-level coverage.

## Other findings

6. Confirmed data loss: ClientStreamingHandler.throwErrorType() parses the structured error but does not pass it to the exception factory.
7. Missing request propagation: OpenAiClient attaches requests to serialization/I/O exceptions, but not handler-generated exceptions. OrchestrationHttpExecutor never attaches requests.
8. Lazy-stream caveat: catching exceptions only when creating a stream will miss failures during consumption.
9. Response lifetime: retaining httpResponse does not guarantee its consumed or closed body remains readable.
10. Minor documentation errors: setClientError() and setHttpResponse() describe setting the request.
