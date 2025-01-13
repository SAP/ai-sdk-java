# Development Lifecycle

Status: In-Progress.

## Background

The AI SDKs largely depend on the AI Core service, which currently is released roughly every 2 weeks.
The release process itself (from the first integration landscape to the last production landscape) also takes around 2 weeks.

This raises the question when to release new versions of the SDKs.

## Development

For any AI Core functionality not yet publicly released, the following options can be considered for the AI SDKs:

- Allow no code
- Allow test code
- Allow (released) internal code
- Allow (released) public API

Note that for JavaScript and Python, code can be marked as internal and thus be considered _non-public_.
For Java, the options are more limited: Manually written code can be marked package-private, but generated code can not.
Since most new features will rely on generated code, the options for Java are limited.

## Testing

Testing can be performed against multiple landscapes and can vary based on the type of tests.

- Unit tests may use test data from any landscape (including development landscapes) manually tested with e.g. Bruno.
- E2E tests may use integration, canary or production landscapes.
  In case of multiple landscapes:
   - GitHub matrix builds can be used to easily testing against multiple landscapes
   - For any differences between landscapes, test toggles need to be considered (e.g. `@EnabledIfSystemProperty`)
   - Such toggles come with a bit of additional maintenance cost, as they need to be removed once the feature is released to all landscapes
   - Alternatively, E2E tests may be executed manually during development against any landscape

## Release

For releasing new AI SDK versions, several points during the AI Core release process can be considered:

- Release to any canary landscape
- Release to all canary landscapes
- Release to any production landscape
- Release to all production landscapes

Furthermore, the following boundary conditions need to be considered:

- Releases to canary are usually deployed to production after 1-2 weeks
- Releases to canary are not guaranteed to reach production
- Different landscapes (both canary and prod) may host different versions of AI Core or different feature-toggle configurations
- Historically, production EU10 is the most feature-complete landscape

Finally, please note that in case of daily delivery the term _release_ still holds, typically referring to the enablement of a feature toggle rather than a physical deployment.
However, here there would be more freedom in when a feature toggle is enabled for which landscape, and the timelines can be much shorter than weeks.

## Decision

We decide as follows:

> 1. New AI SDK versions are released roughly every 2 weeks, shortly after new AI Core versions have been released to **all** landscapes.
> 2. Any _publicly available release_ of the AI SDKs must only contain _public API_ for AI Core features available in **any** landscape under the service plan _extended_.
> 3. E2E tests run automatically against canary EU12 only. Production EU10 can be used for manual test runs. 

Further explanations and notes:

- Any features released exclusively under the `sap-internal` plan are not supported.
- There will be no stable releases of the SDKs to internal artifactory.
  For Java, we deliver SNAPSHOT versions to the interhal artifactories, for JS we deliver canary releases to NPM.
- Please note that the following is allowed:
  - Public API in an unreleased SDK version for unreleased AI Core features.
    Notably, this will **block** the release of the SDK until the AI Core feature is released publicly.
  - Internal code in a released SDK version for unreleased AI Core features.

### An Example Development Lifecycle Iteration

The following depicts a development flow where the AI SDK development steps are performed as soon as possible. 

1. A new AI Core feature is being developed.
2. A PR is raised on the AI SDK with a corresponding implementation, but so far not E2E-tested.
   - Potentially aided by unit tests based on test data manually copied from e.g. Bruno.
   - Generated code is created from a development version of the relevant spec file.
3. (+2 weeks later) The feature is released to EU12 canary landscape.
4. The AI SDK PR is enhanced:
   - With an updated spec file.
   - With an E2E test against canary.
5. The AI SDK PR is merged.
6. (+1 week later) The feature is released to EU10 production landscape.
7. The E2E test is updated to now also run against production.
8. The AI SDK is released publicly.

In case of delays in the release process of AI Core:

- If step (3) is delayed, the open PR may be closed and re-opened later
- If step (5) is delayed, we consider 3 options:
  1. The PR is reverted, together with potentially other related PRs
  2. The AI SDK release is delayed equally
  3. The AI SDK is released anyway with exceptional PO approval

## Further Links

- The single source of truth for all landscapes is in [mlf-gitops](https://github.tools.sap/MLF-prod/mlf-gitops-prod)
  - In particular, we care about the [version of orchestration](https://github.tools.sap/MLF-prod/mlf-gitops-prod/blob/aws.eu-central-1.prod-eu/current/services/llm-orchestration/source/Chart.yaml)
- [This JIRA ticket](https://jira.tools.sap/browse/AI-44024) tracks releases
