### 1.0.0 - December 03, 2024

[All Release Changes](https://github.com/SAP/ai-sdk-java/releases/tag/rel%2F1.0.0)

### Introduction

We’re excited to announce the initial release of the [SAP Cloud SDK for AI for Java](https://github.com/SAP/ai-sdk-java#readme)!
Only a few weeks ago we already [announced the release](https://community.sap.com/t5/technology-blogs-by-sap/introducing-the-sap-cloud-sdk-for-ai-javascript-typescript/ba-p/13892856) of the JavaScript/TypeScript variant.
Similarly, this SDK for Java enables convenient integration of generative AI capabilities within your SAP Business Technology Platform (BTP) applications and allows you to utilize the [Generative AI Hub](https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/generative-ai-hub-in-sap-ai-core) in [SAP AI Core](https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/what-is-sap-ai-core).

For [SAP BTP](https://help.sap.com/docs/btp?locale=en-US) developers, the AI SDK is crafted to simplify AI integration and enhance application impact. With features that optimize deployment, improve content safety, and facilitate model orchestration, the SDK lets you bring advanced AI functionality to your applications swiftly and with minimal setup. Whether you need adaptable workflows, secure data handling, or smooth generative model integration, the SDK equips you with robust tools to embed AI-powered features in your SAP BTP solutions.

This post introduces the main modules and their features.

### AI Core - Setup and Usage

```xml
<dependency>
  <groupId>com.sap.ai.sdk</groupId>
  <artifactId>core</artifactId>
  <version>1.0.0</version>
</dependency>
```

Automate tasks such as creating AI Core artifacts, configurations, and deployments, executing batch inference jobs, as well as managing Docker registries and object storage for training data.
The `core` module provides tools for workflow and scenario management within SAP AI Core.
* Artifact management: register and organize datasets and model artifacts.
* Configuration management: set up configurations for various models and use cases.
* Deployment management: deploy AI models and manage their lifecycle within SAP AI Core.

**Example SDK code:** Create a deployment in SAP AI Core.

```java
var api = new DeploymentApi();
var resourceGroupId = "default";
var request =
  AiDeploymentCreationRequest.create().configurationId("12345-123-123-123-123456abcdefg");

AiDeploymentCreationResponse deployment = api.create(resourceGroupId, request);
String id = deployment.getId();
AiExecutionStatus status = deployment.getStatus();
```

You can learn more about the SDK's capabilities for SAP AI Core [in the public repository guide](https://github.com/SAP/ai-sdk-java/blob/main/docs/guides/AI_CORE_DEPLOYMENT.md).

### AI Core - Orchestration

```xml
<dependency>
  <groupId>com.sap.ai.sdk</groupId>
  <artifactId>orchestration</artifactId>
  <version>1.0.0</version>
</dependency>
```

This `orchestration` module lets you use the [Generative AI Hub Orchestration Service](https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/orchestration) with templating, content filtering, and data masking from within your applications.
The Orchestration Service streamlines AI interactions while ensuring adherence to content safety guidelines.

* Templating: Build dynamic prompts with placeholders to tailor AI interactions to user inputs.
* Content Filtering: Apply filters to maintain compliance with content safety guidelines.
* Data Masking: Anonymize and pseudonymize sensitive data.
* Grounding:  Add external data sources for contextually relevant information (planned for Q1 2025).

**Example SDK code:** Write a simple chat completion.

```java
var client = new OrchestrationClient();
var config = new OrchestrationModuleConfig().withLlmConfig(OrchestrationAiModel.GPT_4O);
var prompt = new OrchestrationPrompt("Hello world! Why is this phrase so famous?");
var result = client.chatCompletion(prompt, config);

String messageResult = result.getContent();
```

You can learn more about the SDK's capabilities for Orchestration Service [in the public repository guide](https://github.com/SAP/ai-sdk-java/blob/main/docs/guides/ORCHESTRATION_CHAT_COMPLETION.md).

### AI Core - Foundation Models

```xml
<dependency>
  <groupId>com.sap.ai.sdk.foundationmodels</groupId>
  <artifactId>openai</artifactId>
  <version>1.0.0</version>
</dependency>
```

The `openai` module, along with other modules in the `com.sap.ai.sdk.foundationmodels` group, enables streamlined access to specific generative AI models available through the Generative AI Hub.
The module provides a simplified interface focused on direct model interactions, ideal for developers who require direct access to foundation models for inference and embedding requests without additional orchestration features.

Currently only `openai` is supported.
Please open a [feature request](https://github.com/SAP/ai-sdk-java/issues/new/choose), if you need direct LLM access for other foundation models.

**Example SDK code:** Write a simple chat completion.

```java
var result = 
  OpenAiClient.forModel(GPT_35_TURBO)
    .withSystemPrompt("You are a helpful AI")
    .chatCompletion("Hello World! Why is this phrase so famous?");

String resultMessage = result.getContent();
```

You can learn more about the SDK's capabilities for foundation models and OpenAI specific features [in the public repository guide](https://github.com/SAP/ai-sdk-java/blob/main/docs/guides/OPENAI_CHAT_COMPLETION.md).

### Getting Started

You will need _Java 17_ or higher.
_Spring Boot_ or [_SAP Cloud Application Programming Model (CAP)_](https://cap.cloud.sap/docs/) as a framework is recommended, but not required.
To explore these packages further, check out [our sample project](https://github.com/SAP/ai-sdk-java/tree/main/sample-code/spring-app), which shows the usage of the various SDK packages.

### Support and Feedback

We’d love your feedback on this first release! For support or to share your ideas, feel free to open an issue on [GitHub](https://github.com/SAP/ai-sdk-java/issues/new/choose).

### Latest News
* [SAP Cloud SDK for AI for JavaScript](https://community.sap.com/t5/technology-blogs-by-sap/introducing-the-sap-cloud-sdk-for-ai-javascript-typescript/ba-p/13892856) released in October 2024.
* Visit [sap.com/ai](https://www.sap.com/products/artificial-intelligence.html) and explore our portfolio
* Explore the available AI capabilities on [SAP Discovery Center](https://discovery-center.cloud.sap/serviceCatalog/sap-ai-core/?region=all).
* Discover the latest announcements in the [SAP TechEd Press Release](https://news.sap.com/?p=228310) and the [SAP TechEd News Guide](https://www.sap.com/events/teched/news-guide.html).
* Review the [SAP Road Map Explorer](https://roadmaps.sap.com/board?PRODUCT=73554900100800003641&PRODUCT=73555000100800003283&range=FIRST-LAST) for a detailed view of upcoming product innovations.
* Join the [SAP Community](https://pages.community.sap.com/topics/ai-core-artificial-intelligence) page to connect with experts and share knowledge.
