# Sample Code and E2E Test

![e2e-test](https://github.com/SAP/ai-sdk-java/actions/workflows/e2e-test.yaml/badge.svg)

Sample code to demonstrate the usage of the SAP AI SDK.
Also used as basis for running E2E tests.

## Build, Run, Deploy Locally

Build the project with:

1. `mvn compile`
2. [Download a service key for your AI Core service instance](../../README.md#set-credentials-as-dedicated-environment-variable)
3. Create the environment variable `AICORE_SERVICE_KEY`
4. Run the application with `mvn spring-boot:run`
5. [See all available endpoints](localhost:8080)

## Run the E2E Test

Trigger the [GitHub Action](https://github.com/SAP/ai-sdk-java/actions/workflows/e2e-test.yml).
