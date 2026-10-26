# Anthropic Foundation Model Integration

Status: Decided.

## Background

SAP AI Core exposes Anthropic Claude models via a Bedrock-compatible HTTP API.
The SDK needs to provide Java users with access to these models.
Three design dimensions were evaluated independently: the overall integration approach, which sub-APIs to expose, and how to handle client configuration.

---

## Decision 1: Integration approach

How should the SDK expose Anthropic Claude models to users?

### Options considered

| Option | Description |
|---|---|
| **Convenience classes** | Hand-rolled request/response POJOs and a custom HTTP client, similar to the pre-existing custom implementation |
| **Raw Bedrock invoke API** | Thin wrapper around the SAP AI Core `/invoke` endpoint, leaving all serialization to the user |
| **Official Anthropic Java SDK (chosen)** | Use `com.anthropic:anthropic-java-core` as the typed API layer; replace its HTTP transport with SAP's authenticated Apache HTTP client |

### Arguments

| | Convenience classes | Raw Bedrock invoke | Official Anthropic SDK |
|---|---|---|---|
| Maintenance effort | High — every API change requires SDK updates | Near zero — no types owned | Low — SDK absorbs upstream changes |
| Code footprint | Large | Minimal | Small (bridge only: `AiCoreAnthropicClient`, `SapAnthropicHttpClient`) |
| User value | Moderate — familiar but limited API | Near zero — equivalent to handing the user an HTTP client | High — official, fully-typed, well-documented API |
| Stability | Fragile — SAP owns the contract | N/A | Stable — Anthropic owns and maintains the contract |
| Backend replaceability | Hard — POJOs are tightly coupled to Bedrock wire format | N/A | Easy — transport is swappable without breaking user code |

### Decision

**Use the official Anthropic Java SDK** (`com.anthropic:anthropic-java-core`) configured with a custom HTTP client that routes requests through SAP AI Core.
The `SapAnthropicHttpClient` bridges between the SDK's `com.anthropic.core.http.HttpClient` interface and SAP's `ApacheHttpClient5Accessor`, handling URL rewriting (Anthropic `/v1/messages` → SAP `/invoke`) and Bedrock-style SSE rewriting transparently.
Users interact exclusively with the official Anthropic SDK types (`AnthropicClient`, `MessageCreateParams`, `Message`, `StreamResponse`, etc.).

---

## Decision 2: Sub-APIs exposed

The official Anthropic Java SDK exposes multiple sub-APIs (messages, completions, batches, files, models, …).
SAP AI Core's router currently only supports two of these: **messages** and **completions**.

### Options considered

| Option | Description |
|---|---|
| **Expose everything** | Return the full `AnthropicClient` interface unchanged |
| **Expose only supported sub-APIs (chosen)** | Return the full `AnthropicClient` but document that only `messages()` and `completions()` are supported |

### Arguments

| | Expose everything | Expose only supported sub-APIs |
|---|---|---|
| Available functionality | Full SDK surface, most unsupported at runtime | Same actual functionality — unsupported sub-APIs would fail at runtime either way |
| User experience | May lead to confusion when unsupported calls fail | Clearly documented scope; failures are expected and documented |
| Maintenance effort | Zero additional code | Zero additional code; small documentation update needed when SAP router adds sub-APIs |
| Code footprint | None | None |

### Decision

**Expose only the supported sub-APIs** (`messages()` and `completions()`) by returning the standard `AnthropicClient` and documenting the supported scope.
When SAP AI Core's router extends support to additional sub-APIs, the SDK change is limited to a documentation update — no code modification required.

---

## Decision 3: Client URL and configuration

The official Anthropic SDK allows users to override the base URL, custom headers, timeouts, and other transport settings via `ClientOptions`.
The question is whether the SDK should restrict or validate these overrides.

### Options considered

| Option | Description |
|---|---|
| **Restrict / redefine the interface** | Override or wrap `messages()` and related services to prevent users from changing internal settings |
| **Do nothing (chosen)** | Expose the standard `AnthropicClient` as-is; users may override settings at their own risk |

### Arguments

| | Restrict interface | Do nothing |
|---|---|---|
| Maintenance effort | High — requires reimplementing or wrapping SDK interfaces; breaks on every upstream SDK update | Near zero |
| Code footprint | Large | None |
| User flexibility | Reduced — legitimate advanced use cases blocked | Full — power users can reconfigure if needed |
| Risk of misconfiguration | Eliminated | Accepted — consistent with SDK's general philosophy: bad input does not guarantee good output |
| Alignment with SDK principles | Against minimalistic design goal | Consistent |

### Decision

**Do nothing.** The `AnthropicClient` is returned as-is (supported sub-clients).
Users who override base URLs, headers, or other transport internals do so at their own risk.
This is consistent with the SDK's general design principle of minimal footprint and minimal maintenance overhead.
