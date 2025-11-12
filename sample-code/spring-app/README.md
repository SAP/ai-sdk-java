# Sample Code and E2E Test

![e2e-test](https://github.com/SAP/ai-sdk-java/actions/workflows/e2e-test.yaml/badge.svg)

Sample code to demonstrate the usage of the SAP AI SDK.
Also used as basis for running E2E tests.

## Prerequisites

Before running the application, ensure the following prerequisites are met:

- Java 17 or higher
- Maven 3.8 or higher
- Deployments of the following models in [SAP AI Core](https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/what-is-sap-ai-core):
  - `gpt-4o-mini`
  - `text-embedding-3-small`
- Credentials for [SAP AI Core](https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/what-is-sap-ai-core) service configured

### Setting Up Credentials for SAP AI Core

To set up credentials for the AI Core service:

- Follow the instruction at [Connecting to AI Core](https://sap.github.io/ai-sdk/docs/java/guides/connecting-to-ai-core) to create a service key for the AI Core service.
- Add the service key content either to a `.env` file in the sample app directory or directly to the `AI_CORE_SERVICE_KEY` environment variable.

### Deploying Generative AI Models in SAP AI Core

To deploy `gpt-4o-mini` and `text-embedding-3-small` models, follow the instructions at:
[Create a Deployment for a Generative AI Model](https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/create-deployment-for-generative-ai-model-in-sap-ai-core)

## Build and Run the Sample App

Before you can run the sample app, you need to install the AI SDK into your local Maven repository:

* Run `mvn install -DskipTests` from the root directory of the repository.

> [!NOTE]  
> The sample app uses the latest state of the SDK, so make sure to install the SDK after pulling a new version via Git.
> Alternatively, you check out one of the release tags of the repository, e.g. `git fetch --all --tags && git checkout rel/1.1.0`. 

Start the sample app:
 
* Run `mvn spring-boot:run` from the sample app directory.

Head to http://localhost:8080 in your browser to see all available endpoints.

To test the MCP integration, run with `mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=mcp"`:

## Run the E2E Test

Trigger the [GitHub Action](https://github.com/SAP/ai-sdk-java/actions/workflows/e2e-test.yaml).
