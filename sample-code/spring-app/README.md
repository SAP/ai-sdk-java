# Sample Code and E2E Test

![e2e-test](https://github.com/SAP/ai-sdk-java/actions/workflows/e2e-test.yaml/badge.svg)

Sample code to demonstrate the usage of the SAP AI SDK.
Also used as basis for running E2E tests.

## Build and Run the Sample App

Before you can run the sample app, you need to install the AI SDK into your local Maven repository:

* Run `mvn install -DskipTests` from the root directory of the repository.

> [!NOTE]  
> The sample app uses the latest state of the SDK, so make sure to install the SDK after pulling a new version via Git.
> Alternatively, you check out one of the release tags of the repository, e.g. `git fetch --all --tags && git checkout rel/0.1.0`. 

Next, you'll need to set up credentials for the AI Core service: 

* Follow [these instructions](../../README.md#option-1-set-ai-core-credentials) to create a service key for the AI Core service.

  ⚠️ Put the `.env` file in the sample app directory.

Finally, you can start the sample app:
 
* Run `mvn spring-boot:run` from the sample app directory.

Head to http://localhost:8080 in your browser to see all available endpoints.

## Run the E2E Test

Trigger the [GitHub Action](https://github.com/SAP/ai-sdk-java/actions/workflows/e2e-test.yml).
