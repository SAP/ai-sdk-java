# Testing Strategy

Status: Accepted, except for the open TBD on the automated AI Core API [wiremock tests](#wiremock-unit-tests).

## Background

Like any product, the AI SDK needs to be tested.

## General Strategy

We classify our tests into regular unit, wiremock unit, integration and e2e tests.
Overall, we aim for at least 80% test coverage.
Excluded from this is the AI Core API module, which is completely generated and only selected functionality can be tested.

## Regular Unit Tests

Anything that can be covered by unit tests, should be, following the [testing pyramid principle](https://martinfowler.com/articles/practical-test-pyramid.html).

## Wiremock Unit Tests

We use wiremock tests to verify (de-) serialization, HTTP headers and other behavior on the HTTP layer.

We construct these tests only with data that has been obtained from real systems.
This guarantees that, at least at the time of creating the test, a real system behaved in the exact same way the wiremock test stub does.

TBD: How much of the generated code for the AI Core API do we want to test

## Integration Tests

We use the integration tests to continuously test against real systems.
Integration tests are in a separate module and are run only nightly / on-demand.

These tests are more expensive and are less reliable, so we limit how much we cover with them:

- Reading configurations and deployments
- Performing chat completions and embeddings (and potentially other features) for at least one foundation model per vendor
- Performing requests via orchestration service, covering every orchestration module available
- Query all available models and verify all are known to the SDK

Note that orchestration and foundation model tests should be tuned to produce small input/output sizes to reduce costs and keep test execution fast.

## E2E Tests

Finally, E2E tests cover full application scenarios, e.g. using the SDK in a Spring and/or CAP based application.
They ensure that the SDK works in a real-world scenario, including typical application dependencies and configuration.

E2E tests are comprised of two parts: The test/sample application code and pipeline code.

The application code serves as both test code but also sample code for end-users, showcasing how they could include the SDK in their applications.
Thus, we want to keep the application code as meaningful as possible.

The actual testing is done via GitHub Actions, which builds and starts the applications and then invokes endpoints via `curl` or similar.
The E2E test actions are run nightly / on-demand, similarly to integration tests.

## Performance Tests

Currently not planned.

## Test Landscapes

For integration and E2E tests we test against 2 different systems:

- Staging - pre-release system
- Canary - released system

Implementing new features or adapting to changes happens on branches and is tested first against staging.