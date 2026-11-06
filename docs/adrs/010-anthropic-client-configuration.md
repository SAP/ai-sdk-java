#  Anthropic client configuration

##  Status

- Status: `proposed` <!-- [draft | proposed | rejected | accepted | deprecated | superseded by ...] -->
- Contributors: Vladimir Aseev
- Reviewers: Zhongpin Wang
- Next expected status change date: 06.11.2026

##  Context
Anthropic foundation models client will be implemented according to [008-anthropic-integration-approach.md](008-anthropic-foundation-models.md).
Exposed by the client configuration is to be decided.

### Business Requirements
This ADR is required in the context of the backlog items:
- https://github.com/SAP/ai-sdk-java-backlog/issues/449

### Problem Description
Anthropic client and its sub-clients (services) expose ways to configure them:
```java
    MessageService messagesClient = AiCoreAnthropicClient.forModel(AnthropicModel.CLAUDE_48_OPUS).messages();
    // messagesClient.withOptions(...) // client can be reconfigured here
```

Changing pre-configured client settings may lead to its inability to work with SAP gateway or issues in general.

Options on how and if at all we address this challenge are listed in this document. 

### Solution Options
#### Option A: Override implementation
For every sub-api we expose (e.g. `messages`) we expose not original Antropic implementation but a wrapped or
extended our implementation which delegates all necessary methods to the original client but does nothing or throws error
on attempt to call `.withOptions(...)`.

| Aspect                  | Value                                                                                                                                  |
|-------------------------|----------------------------------------------------------------------------------------------------------------------------------------|
| Implementation effort   | Maximum - we will need to implement relatively large codebase and tests                                                                |                             
| Maintenance effort      | Maximum - all changes in the Anthropics API will have to be manually implemented in the proxy classes                                  |
| Safety provided         | Maximum - we can configure and enforce any safety guarantees and configuration we need                                                 |
| Flexibility             | Minimum - we can cut off many non standard use cases we cannot foresee or verify now (e.g. usage with proxies and internet gateways)   | 

#### Option B: Override interface
For every sub-api we expose (e.g. `messages`) we expose not original Anthropic interface but our copy, exposing all
operations exactly as they are in the original interface but not exposing the `.withOptions(...)` method.

| Aspect                  | Value                                                                                                                                |
|-------------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| Implementation effort   | Medium - we will need to create one interface per sub-api                                                                            |                             
| Maintenance effort      | Medium - all changes in the Anthropics API will have to be reflected in the interfaces classes (one per sub-api)                     |
| Safety provided         | Maximum - we can configure and enforce any safety guarantees and configuration we need                                               |
| Flexibility             | Minimum - we can cut off many non standard use cases we cannot foresee or verify now (e.g. usage with proxies and internet gateways) | 

#### Option C: No override, document warning
We do nothing about configuration. We expose Anthropic sub-api (e.g. `messages`) with its original unaltered interface
and original implementation (just configured correctly to call SAP gateway).
We document clearly to the users which parts of the configuration they should not change.

| Aspect                  | Value                                                                                             |
|-------------------------|---------------------------------------------------------------------------------------------------|
| Implementation effort   | None (nothing to implement)                                                                       |                             
| Maintenance effort      | Minimum - nothing to maintain beyond the minimum necessary for system functioning                 |
| Safety provided         | Medium - we pre-configure all clients, but users can break things if they re-configure them       |
| Flexibility             | High - we still pre-configure all clients, but users can tweak configs for non-standard use cases | 

## Decision
No override (option C) is proposed.

We pre-configure all clients, but users can break things if they explicitly re-configure them

## Rationale

Options with override were discarded as they bloat codebase, increase maintenance effort not providing (or even
limiting) user value.

Potential use cases of AI SDK may include network configurations with DMZ and internet gateways or additional proxies.
For such cases API URL customization might be needed. Hiding this option in our SDK would make such use cases
virtually impossible.

Overriding does not provide full safety either. At least some of the configuration will have to be exposed. Even
with a limited configuration being exposed, there is always a possibility to misconfigure SDK client.

Given the above, override options were discarded in favor of "no override" (option C) and addition of warning
in documentation.

Additional supporting evidence with aspect analysis for every option is given below.

Every aspect value is scored between 1 and 5 points
Every option receives result as a sum of its aspect values.

| Aspect                | A: Override implementation | B: Override interface | __C: No override__ |
|-----------------------|----------------------------|-----------------------|--------------------|
| Implementation effort | Maximum (1)                | Medium (3)            | __None (5)__       |
| Maintenance effort    | Maximum (1)                | Medium (3)            | __Minimum (5)__    |
| Safety provided       | Maximum (5)                | Maximum (5)           | __Medium (3)__     |
| Flexibility           | Minimum (1)                | Minimum (1)           | __High (4)__       |
| Score                 | 8                          | 12                    | __17__             |

## Consequences, New Dependencies, Open Points, Next Steps
<!-- Describe anything else that needs to be done and lists dependencies to other services that need to be introduced. -->
