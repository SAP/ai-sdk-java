# Anthropic Integration: Client Design

Status: Proposed (2026-10-27).

## Background

SAP AI Core exposes Anthropic Claude models via a Bedrock-compatible HTTP API.
This document covers two design decisions about how `AiCoreAnthropicClient` exposes the official Anthropic Java SDK to users.

Both decisions assume **Option C (official Anthropic Java SDK)** from
[008-anthropic-integration-approach.md](008-anthropic-integration-approach.md).

---

## Decision 1: Sub-APIs exposed

The official Anthropic Java SDK exposes multiple sub-APIs (`messages()`, `completions()`, `batches()`, `files()`, `models()`, …).
SAP AI Core's router currently only supports two of these: **messages** and **completions**.

### Options considered

| Option | Description |
|---|---|
| **Expose everything** | Return the full `AnthropicClient` interface with no restrictions or documentation caveats |
| **Expose only supported sub-APIs (chosen)** | Return the full `AnthropicClient` but document that only `messages()` and `completions()` are supported by SAP AI Core |

### Arguments

| | Expose everything | Expose only supported sub-APIs |
|---|---|---|
| Available functionality | Same — unsupported sub-APIs fail at runtime either way | Same |
| User experience | May cause confusion when unsupported calls fail with opaque errors | Clearly scoped; failures on unsupported APIs are expected and documented |
| Maintenance when SAP router adds sub-APIs | None | Documentation update only — no code change needed |
| Code footprint | None | None |

### Decision

**Expose only the supported sub-APIs** (`messages()` and `completions()`) by documenting the supported scope on `AiCoreAnthropicClient.client()`.
When SAP AI Core's router adds support for further sub-APIs, the SDK change is a documentation update only.

---

## Decision 2: Client URL and configuration

The official Anthropic SDK allows users to further configure the returned `AnthropicClient` — overriding the base URL, adding custom headers, changing timeouts, etc.
The question is whether the SDK should prevent or validate such overrides.

### Options considered

| Option | Description |
|---|---|
| **Restrict / redefine the interface** | Wrap or re-implement `messages()` and related services to prevent users from altering internal transport settings |
| **Do nothing (chosen)** | Return the standard `AnthropicClient` as-is; document that overriding internals is unsupported |

### Arguments

| | Restrict interface | Do nothing |
|---|---|---|
| Maintenance effort | High — requires re-implementing SDK interfaces; breaks on every upstream SDK update | Near zero |
| Code footprint | Large | None |
| User flexibility | Reduced — legitimate advanced use cases blocked | Full — power users can reconfigure if needed |
| Risk of misconfiguration | Accepted — consistent with SDK philosophy: bad input does not guarantee good output | Eliminated |
| Alignment with SDK principles | Against minimalistic design goal | Consistent |

### Decision

**Do nothing.** The `AnthropicClient` is returned as-is.
Users who override base URLs, headers, or other transport internals do so at their own risk.
This is consistent with the SDK's general design principle of minimal footprint and minimal maintenance overhead.
