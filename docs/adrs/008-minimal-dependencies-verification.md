# Minimal Dependencies Verification

Status: Proposed.

## Background

The SDK declares a set of consumer-facing dependencies (e.g. SAP Cloud SDK, Jackson, Apache HttpComponents) and communicates a _minimum supported version_ for each of them to our users.

Nothing, however, prevented us from accidentally relying on an API that only exists in a _newer_ release of such a dependency.
Because our own builds always resolve recent versions, such a mistake compiles and tests green on our side, but breaks for any user who pins an older — but officially still supported — version.

We want a mechanism that detects this class of regression automatically, before a release reaches users.

## Goal

Continuously verify that the SDK still compiles against the lowest version we claim to support for each relevant dependency.
The check must fail the build the moment our code starts depending on a newer-version API.

This is deliberately a _compile-time_ check. It does not run tests against the floor versions; it only answers the single question "does our source still build against the declared minima?".

## The Portable Recipe

The concrete implementation below is Maven-specific, but the underlying approach is language-agnostic and is intended to be adoptable by the JavaScript and Python SDKs.
It consists of three steps:

1. **Define the floor versions in one place, as data.**
   The minimum supported version of each dependency lives in a single, well-named location, separate from the logic that applies it.
2. **Force the minima in an isolated build configuration.**
   A dedicated, opt-in configuration overrides the normally-resolved versions with the floors. It is never active in a normal build, so it changes nothing for consumers and nothing in regular CI unless explicitly requested.
3. **Add a CI step that builds against the minima and fails if it breaks.**
   The pipeline activates the isolated configuration and compiles. A compilation failure means we started relying on a newer-version API — the build goes red.

Each SDK maps these three steps onto its own tooling. Only the mechanism is shared; the floor _values_ and the build tool differ per language and per release line.

## Java Implementation

1. **Floor versions as data** — `floor.*` properties in the root `pom.xml`, e.g. `floor.cloud-sdk.version`, `floor.jackson.version`, `floor.spring-ai.version`.
2. **Isolated build configuration** — a Maven profile `floor-deps` whose `dependencyManagement` pins each dependency (and relevant BOM imports) to its `floor.*` property. Because it is a profile, it only takes effect with `-P floor-deps`; the normal build and published POM are unaffected.
3. **CI step** — `mvn test-compile -P floor-deps` (with CI plugins skipped, as we only care about compilation) in the `continuous-integration` workflow. It runs on every pull request and goes red if the SDK no longer compiles against the floors.

## Choosing the Floor Values

The floor values are a team decision — the versions we commit to supporting — not an objectively derivable fact.
They were determined empirically via a binary search: for each dependency, the floor is the lowest version that still compiles, verified by confirming that one version below it breaks.

The declared minima are mirrored in the release notes and the documentation portal so users have a single, consistent source of truth.

Because the v1 and v2 (main) release lines differ in some minima (notably Spring AI), each line carries its own floor values.

## Decision

> 1. We maintain, per release line, a set of minimum supported versions for consumer-facing dependencies, defined as data in one place.
> 2. An opt-in, isolated build configuration forces these minima without affecting normal builds or consumers.
> 3. A CI step compiles against the minima on every pull request and fails the build if the SDK relies on a newer-version API.
> 4. The approach is intended as a portable, cross-SDK pattern: the three steps above are shared, while the tooling and floor values are language- and release-line-specific.

## Further Remarks

- The check is intentionally limited to compilation. Verifying _runtime_ behavior against the floor versions is out of scope and would require running the full test suite against pinned minima.
- When a floor value is raised (dropping support for an older version), this is a user-facing compatibility change and must be reflected in the release notes and documentation.
