![build](https://github.com/SAP/ai-sdk-java/actions/workflows/continuous-integration.yaml/badge.svg?branch=main)
![CodeQL](https://github.com/SAP/ai-sdk-java/actions/workflows/github-code-scanning/codeql/badge.svg)
[![REUSE status](https://api.reuse.software/badge/git.fsfe.org/reuse/api)](https://api.reuse.software/info/git.fsfe.org/reuse/api)
[![Fosstars security rating](https://github.com/SAP/cloud-sdk-java/blob/fosstars-report/fosstars_badge.svg)](https://github.com/SAP/cloud-sdk-java/blob/fosstars-report/fosstars_report.md)

# <img src="https://sap.github.io/cloud-sdk/img/logo.svg" alt="SAP Cloud SDK" width="30"/> SAP Cloud SDK for AI (for Java)

> ⚠️ **This is a pre-alpha version of the AI SDK for Java. The APIs are subject to change.** ⚠️

## Table of Contents

- [Introduction](#introduction)
- [General Requirements](#general-requirements)
- [Connecting to SAP AI Core](#connecting-to-sap-ai-core)
    - [Option 1: Set Credentials as Environment Variable](#option-1-set-ai-core-credentials)
    - [Option 2: Regular Service Binding in SAP BTP Cloud Foundry](#option-2-regular-service-binding-in-sap-btp-cloud-foundry)
    - [Option 3: Define and Use a Destination](#option-3-define-and-use-a-destination)
- [Getting Started](#getting-started)
    - [What You'll Build](#what-youll-build)
    - [Prerequisites](#prerequisites)
    - [Write the Code](#write-the-code)
    - [Run and Test the Application Locally](#run-and-test-the-application-locally)
    - [Deploying to Cloud Foundry (Optional)](#deploying-to-cloud-foundry-optional)
- [Documentation](#documentation)
- [FAQs](#faqs)
- [Contribute, Support and Feedback](#contribute-support-and-feedback)
- [Security / Disclosure](#security--disclosure)
- [Code of Conduct](#code-of-conduct)
- [Licensing](#licensing)

## Introduction

Welcome to the **SAP Cloud SDK for AI (for Java)**.
This SDK enables developers to seamlessly integrate AI capabilities, such as chat completion, into their Java-based business applications using SAP's Generative AI Hub.
Leverage powerful features like templating, grounding, data masking, and content filtering to build intelligent applications.
The SDK simplifies the setup and interaction with SAP AI Core, allowing you to focus on delivering value through AI integration.

## General Requirements

To use the SAP AI SDK for Java, the following general prerequisites must be met:

- **Java Development Kit (JDK) 17** or higher installed.
- **Apache Maven 3.9** or higher installed.
- **SAP AI Core Service** enabled in your SAP BTP account.
    - [How to enable the AI Core service](https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/initial-setup)
- **SAP AI Core Credentials** to access the AI Core service.
    - [Connecting to SAP AI Core](#connecting-to-sap-ai-core)
- **(Optional) Spring Boot** version 3 or higher.
- **(Optional) SAP Cloud SDK** version `5.6.0` or higher.
- **(Optional) SAP CAP Framework** version `3.0.0` or higher.

See [an example `pom.xml` in our Spring Boot application](sample-code/spring-app/pom.xml).

## Connecting to SAP AI Core

To interact with SAP AI Core services, the SAP AI SDK requires credentials available at application runtime.
By default, the SDK automatically extracts these credentials from a service instance of  type `aicore` bound to your application.

If running the application locally without this service binding, you may encounter an exception:

```
Could not find any matching service bindings for service identifier 'aicore'
```

There are multiple ways to provide these credentials:

| Option | Description                                                                                              |
|--------|----------------------------------------------------------------------------------------------------------|
| **1**  | Create an `.env` file containing an `AICORE_SERVICE_KEY={...}`                                           |
| **2**  | Regular service binding in SAP BTP Cloud Foundry (results in `VCAP_SERVICES` environment variable entry) |
| **3**  | Define and use a _Destination_ in the SAP BTP Destination Service                                        |

Additional methods (not recommended for production):

- Use the [hybrid testing](https://cap.cloud.sap/docs/advanced/hybrid-testing#services-on-cloud-foundry) approach for
  CAP applications (e.g., `cds bind --to aicore --exec mvn spring-boot:run`)
- Leverage a "user-provided" service binding
- Define and use a custom `ServiceBinding` or `ServiceBindingAccessor` in your application

### Option 1: Set AI Core Credentials

<details>
<summary>Click to view detailed steps</summary>


**1. Obtain Service Credentials:**

- Log into the **SAP BTP Cockpit**
- Navigate to **Services** -> **Instances and Subscriptions** -> **Instances** -> **AI Core**
- Click **View Credentials** and copy the JSON content

**2. Create `.env` file:**

- Create an `.env` file in the root directory of your application
- Add a **one line** entry `AICORE_SERVICE_KEY={...}` with the copied JSON

<details>
<summary>Set an environment variable instead of .env</summary>

**2. Set an Environment Variable: (alternative)**

- In your IDE or terminal, set the environment variable `AICORE_SERVICE_KEY` with the copied JSON content

  ℹ️ The environment variable has priority over the `.env` file.

Example Linux/MacOS:

```shell
export AICORE_SERVICE_KEY='{ "clientid": "...", "clientsecret": "...", "url": "...", "serviceurls": { "AI_API_URL": "..." } }'
```

Example Windows:

```shell
$env:AICORE_SERVICE_KEY='{ "clientid": "...", "clientsecret": "...", "url": "...", "serviceurls": { "AI_API_URL": "..." } }'
```

</details>
</details>

### Option 2: Regular Service Binding in SAP BTP Cloud Foundry

<details>
<summary>Click to view detailed steps</summary>


**1. Bind an existing `aicore` service instance to your application**

SAP BTP provides multiple ways to do this:

- Using the web interface
- Using the CLI
- Using MTA or manifest files

[Learn more about binding service instances to applications](https://help.sap.com/docs/btp/sap-business-technology-platform/binding-service-instances-to-applications)

After restarting your application, you should see an "aicore" entry in the `VCAP_SERVICES` environment variable:

```json
{
  "aicore": [
    {
      "clientid": "...",
      "clientsecret": "...",
      "url": "...",
      "serviceurls": {
        "AI_API_URL": "..."
      }
    }
  ]
}
```

</details>

### Option 3: Define and Use a Destination

<details>
<summary>Click to view detailed steps</summary>

**1. Obtain Service Credentials:** (Same as in Option 1)

**2. Create a Destination:**

- In the **SAP BTP Cockpit**, go to **Connectivity** -> **Destinations**
- Click **New Destination** and configure:

    - **Name**: `my-aicore`
    - **Type**: `HTTP`
    - **URL**: `[serviceurls.AI_API_URL]/v2` (append `/v2` to the URL)
    - **Proxy Type**: `Internet`
    - **Authentication**: `OAuth2ClientCredentials`
    - **Client ID**: `[clientid]`
    - **Client Secret**: `[clientsecret]`
    - **Token Service URL Type**: `Dedicated`
    - **Token Service URL**: `[url]`

**3. Use the Destination in Your Application:**

```java
Destination destination = DestinationAccessor.getDestination("my-aicore");
AiCoreService aiCoreService = new AiCoreService().withDestination(destination);
DeploymentApi client = new DeploymentApi(aiCoreService);
```

</details>

## Getting Started

### What You'll Build

In this quickstart, you'll build a simple Spring Boot application that uses the OpenAI GPT-3.5 Turbo model for chat completion.
The application will send a prompt to the AI model and display the generated response.

### Prerequisites

Before you begin, ensure you have:

- Met the [General Requirements](#general-requirements).
- Met the OpenAI Chat Completion module specific requirements
    - Refer to [Prerequisites for OpenAI Chat Completion](docs/guides/OPENAI_CHAT_COMPLETION.md#prerequisites)
- Set up the AI Core credentials
  using [(1) Environment variable or env-file](#option-1-set-ai-core-credentials)
  or [(2) Regular Service Binding](#option-2-regular-service-binding-in-sap-btp-cloud-foundry).
- Deployed the OpenAI GPT-3.5 Turbo model in SAP AI Core.
    - Refer to [Deploying the OpenAI GPT-3.5 Turbo Model](docs/guides/OPENAI_CHAT_COMPLETION.md#usage)

### Write the Code

Add the following code to the controller or service class in your Spring Boot application:

```java
// Initialize the client for GPT-3.5 Turbo model
OpenAiClient client = OpenAiClient.forModel(OpenAiModel.GPT_35_TURBO);

// Perform chat completion
OpenAiChatCompletionOutput result =
    client
        .withSystemPrompt("You are a helpful assistant.")
        .chatCompletion("Hello World! Why is this phrase so famous?");
```

### Run and Test the Application Locally

Then, compile and run your application:

```shell
cd your-spring-app/

mvn compile
mvn spring-boot:run
```

### Deploying to Cloud Foundry (Optional)

When deploying your productive application to Cloud Foundry, it is recommended to use **service binding** to provide the AI Core credentials as per [Option 2](#option-2-regular-service-binding-in-sap-btp-cloud-foundry).

Build your application using Maven and deploy it to Cloud Foundry:

 ```shell
 cf push
 ```

That's it!
Your application should now be running on Cloud Foundry, and the AI Core credentials are provided securely via service binding.

## Documentation

For more detailed information and advanced usage, please refer to the following:

- [OpenAI Chat Completion](docs/guides/OPENAI_CHAT_COMPLETION.md)
- [Orchestration Chat Completion](docs/guides/ORCHESTRATION_CHAT_COMPLETION.md)
- [AI Core Deployment](docs/guides/AI_CORE_DEPLOYMENT.md)

## FAQs

### _"How to add a custom header to AI Core requests?"_

Create a [HeaderProvider](https://sap.github.io/cloud-sdk/docs/java/features/connectivity/http-destinations#about-headerproviders).


### _"There's a vulnerability warning `CVE-2021-41251`?"_

This is a known false-positive finding.
Depending on the tooling any product called "SAP Cloud SDK" or similar with a low version number may be marked as vulnerable, incorrectly.
Please consider suppressing the warning, [as we do](https://github.com/SAP/ai-sdk-java/blob/main/.pipeline/dependency-check-suppression.xml).


### _"Are there any example projects?"_

Explore example applications and code snippets:

- [Spring Boot Application Example](sample-code/spring-app)

## Contribute, Support and Feedback

This project is open to feature requests/suggestions, bug reports etc. via [GitHub issues](https://github.com/SAP/ai-sdk-java/issues).
Contribution and feedback are encouraged and always welcome. 
For more information about how to contribute, the project structure, as well as additional contribution information, see our [Contribution Guidelines](CONTRIBUTING.md).

## Security / Disclosure

If you find any bug that may be a security problem, please follow our instructions at [in our security policy](https://github.com/SAP/ai-sdk-java/security/policy) on how to report it. 
Please do not create GitHub issues for security-related doubts or problems.

## Code of Conduct

We as members, contributors, and leaders pledge to make participation in our community a harassment-free experience for everyone. 
By participating in this project, you agree to abide by its [Code of Conduct](https://github.com/SAP/.github/blob/main/CODE_OF_CONDUCT.md) at all times.

## Licensing

Copyright 2024 SAP SE or an SAP affiliate company and ai-sdk-java contributors. Please see our [LICENSE](LICENSE) for copyright and license information. 
Detailed information including third-party components and their licensing/copyright information is available [via the REUSE tool](https://api.reuse.software/info/github.com/SAP/ai-sdk-java).
