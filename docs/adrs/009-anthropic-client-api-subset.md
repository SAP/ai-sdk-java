#  Anthropic client API subset

##  Status

- Status: `proposed` <!-- [draft | proposed | rejected | accepted | deprecated | superseded by ...] -->
- Contributors: Vladimir Aseev
- Reviewers: Zhongpin Wang
- Next expected status change date: 06.11.2026

##  Context
Anthropic foundation models client will be implemented according to [008-anthropic-integration-approach.md](008-anthropic-foundation-models.md).
Exposed by the client subset of API offered by Anthropic is to be decided.

### Business Requirements
This ADR is required in the context of the backlog items:
- https://github.com/SAP/ai-sdk-java-backlog/issues/449

### Problem Description
Anthropic offers a broad range of APIs. Some functions might be already deprecated, some might be not supported by
SAP gateway or low demand and not worth supporting for set of reasons.

This document compares different options with their tradeoffs, attempts to identify and propose the optimal solution. 

### Solution Options
### Option A: Expose entire Anthropic API client

We expose official Anthropics client (configured to use SAP gateway) "as is":

```java
AnthropicClient client = AiCoreAnthropicClient.forModel(CLAUDE_45_HAIKU);
CompletionService completionsApi = client.completions();
MessagesService messagesApi = client.messages();
ModelService modelsApi = client.models();
FileService filesApi = client.files();
SkillService skillsApi = client.skills();
OrganizationService organizationApi = client.organization();
AnthropicClientAsync asyncClient = client.async();
// ...
```

| Aspect                      | Value                                                                                                                                                  |
|-----------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| Anthropic API completeness  | Maximum - high (potentially everything that is supported by Anthropics and SAP gateway)                                                                |
| Maintenance effort          | Maximum, given our committment to track API availability and defend our users from breaking changes many tests are to be maintained.                   |
| Implementation effort       | Maximum - many tests are to be written                                                                                                                 |
| User value                  | Maximum - users can use virtually any relevant Anthropics APIs available via SAP gateway                                                               |
| Flexibility                 | Minimum - if we expose entire official Anthropics client "as is" we barely have any control on the operations level unless we reimplement it ourselves |

### Option B: Expose `messages` sub-api, proceed with other APIs on demand

We implement a small wrapper, which only exposes (delegates) sub-APIs of official Anthropic client; We start with `messages`:

```java
AiCoreAnthropicClient client = AiCoreAnthropicClient.forModel(CLAUDE_45_HAIKU);
MessagesService messagesApi = client.messages();
// ...
```

| Aspect                      | Value                                                                                                                                                             |
|-----------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Anthropic API completeness  | Minimum - only `messages` are initially exposed, other sub-apis can be supported on demand relatively easily                                                      |
| Maintenance effort          | Minimum, we maintain tests and API only explicitly exposed (monitored API avaiability, absence of breaking changes)                                               |
| Implementation effort       | Minimum - few tests are to be written, only to cover explicitly exposed APIs                                                                                      |
| User value                  | Medium - we can prioritise features/ sub-APIs according to ROI/ROTI and/or Pareto principles. Low demand sub-apis can be postponed, while high demand prioritized |
| Flexibility                 | Maximum - if we expose entire official Anthropics client "as is" we barely have any control on the operations level unless we reimplement it ourselves            |

## Decision
Expose `messages` sub-api, proceed with other APIs on demand (Option B) is proposed.

## Rationale
Every aspect value is scored between 1 and 5 points
Every option receives result as a sum of its aspect values.

| Aspect                     | A: Full client | __B: Sub-APIs__ |
|----------------------------|----------------|-----------------|
| Anthropic API completeness | Maximum (5)    | __Minimum (1)__ |
| Maintenance effort         | Maximum (1)    | __Minimum (5)__ |
| Implementation effort      | Maximum (1)    | __Minimum (5)__ |
| User value                 | Maximum (5)    | __Medium (3)__  |
| Flexibility                | Minimum (1)    | __Maximum (5)__ |
| Score                      | 13             | __19__          |

## Consequences, New Dependencies, Open Points, Next Steps
<!-- Describe anything else that needs to be done and lists dependencies to other services that need to be introduced. -->