# Introducing the SAP Cloud SDK for AI (Java) 🎉

SAP Managed Tags: Artificial Intelligence, Java, SAP AI Core, SAP AI Launchpad, SAP Cloud SDK, SAP Business Technology Platform

---

We’re excited to announce the initial release of the [SAP Cloud SDK for AI](https://github.com/SAP/ai-sdk-java#readme) for Java!
This SDK enables integration of generative AI capabilities within your SAP Business Technology Platform (BTP) applications and allows you to utilize the [Generative AI Hub](https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/generative-ai-hub-in-sap-ai-core) in [SAP AI Core](https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/what-is-sap-ai-core).

For SAP BTP developers, the AI SDK is crafted to simplify AI integration and enhance application impact. With features that optimize deployment, improve content safety, and facilitate model orchestration, the SDK lets you bring advanced AI functionality to your applications swiftly and with minimal setup. Whether you need adaptable workflows, secure data handling, or smooth generative model integration, the SDK equips you with robust tools to embed AI-powered features in your SAP BTP solutions.

This post introduces the main artifacts and their features.
## AI Orchestration

```xml
<dependency>
  <groupId>com.sap.ai.sdk</groupId>
  <artifactId>orchestration</artifactId>
  <version>1.0.0</version>
</dependency>
```

Use the generative AI Hub orchestration service with our `orchestration` module to set up templating, content filtering, and data masking for your applications.
The orchestration service streamlines AI interactions while ensuring adherence to content safety guidelines.

* Templating: Build dynamic prompts with placeholders to tailor AI interactions to user inputs.
* Content Filtering: Apply filters to maintain compliance with content safety guidelines.
* Data Masking: Anonymize and pseudonymize sensitive data.
* Grounding:  Add external data sources for contextually relevant information (planned for Q1 2025).

**Example:** Chat completion. ([more](https://github.com/SAP/ai-sdk-java/blob/main/docs/guides/ORCHESTRATION_CHAT_COMPLETION.md))
```java
var client = new OrchestrationClient();
var config = new OrchestrationModuleConfig().withLlmConfig(OrchestrationAiModel.GPT_4O);
var prompt = new OrchestrationPrompt("Hello world! Why is this phrase so famous?");
var result = client.chatCompletion(prompt, config);

String messageResult = result.getContent();
```

## AI Management

```xml
<dependency>
  <groupId>com.sap.ai.sdk</groupId>
  <artifactId>core</artifactId>
  <version>1.0.0</version>
</dependency>
```

Automate tasks such as creating artifacts, configurations, and deployments, executing batch inference jobs, as well as managing Docker registries and object storage for training data.
The `core` module provides tools for workflow and scenario management within SAP AI Core.
* Artifact Management: Register and organize datasets and model artifacts.
* Configuration Management: Set up configurations for various models and use cases.
* Deployment Management: Deploy AI models and manage their lifecycle within SAP AI Core.

**Example:** Create a deployment in SAP AI Core. ([more](https://github.com/SAP/ai-sdk-java/blob/main/docs/guides/AI_CORE_DEPLOYMENT.md))
```java
var api = new DeploymentApi();
var resourceGroupId = "default";
var request =
  AiDeploymentCreationRequest.create().configurationId("12345-123-123-123-123456abcdefg");

AiDeploymentCreationResponse deployment = api.create(resourceGroupId, request);
String id = deployment.getId();
AiExecutionStatus status = deployment.getStatus();
```

## Generative AI with Foundation Models and OpenAI

```xml
<dependency>
  <groupId>com.sap.ai.sdk.foundationmodels</groupId>
  <artifactId>openai</artifactId>
  <version>1.0.0</version>
</dependency>
```

The `openai` module, along with other artifacts in the `com.sap.ai.sdk.foundationmodels` group, enables streamlined access to specific generative AI models available through the Generative AI Hub.
The module provides a simplified interface focused on direct model interactions, ideal for developers who require direct access to foundation models for inference and embedding requests without additional orchestration features.

**Example:** Simple chat completion. ([more](https://github.com/SAP/ai-sdk-java/blob/main/docs/guides/OPENAI_CHAT_COMPLETION.md))
```java
var result = 
  OpenAiClient.forModel(GPT_35_TURBO)
    .withSystemPrompt("You are a helpful AI")
    .chatCompletion("Hello World! Why is this phrase so famous?");

String resultMessage = result.getContent();
```

## Getting Started

You will need _Java 17_ or higher.
_Spring Boot_ or _SAP CAP_ as a framework is recommended, but not required.
To explore these packages further, check out [our sample project](https://github.com/SAP/ai-sdk-java/tree/main/sample-code/spring-app), which shows the usage of the various SDK packages.

## Support and Feedback

We’d love your feedback on this first release! For support or to share your ideas, feel free to open an issue on [GitHub](https://github.com/SAP/ai-sdk-java/issues/new/choose).

## Latest News
* [SAP Cloud SDK for AI for JavaScript](https://community.sap.com/t5/technology-blogs-by-sap/introducing-the-sap-cloud-sdk-for-ai-javascript-typescript/ba-p/13892856) released in October 2024.
* Visit [sap.com/ai](https://www.sap.com/products/artificial-intelligence.html) and explore our portfolio
* Explore the available AI capabilities on [SAP Discovery Center](https://discovery-center.cloud.sap/serviceCatalog/sap-ai-core/?region=all).
* Discover the latest announcements in the [SAP TechEd Press Release](https://news.sap.com/?p=228310) and the [SAP TechEd News Guide](https://www.sap.com/events/teched/news-guide.html%22%20/t%20%22_blank).
* Review the [SAP Road Map Explorer](https://roadmaps.sap.com/board?range=FIRST-LAST&PRODUCT=73554900100800003641&PRODUCT=73555000100800003283%22%20\l%20%22Q3%202024%22%20\t%20%22_blank) for a detailed view of upcoming product innovations.
* Join the [SAP Community](https://pages.community.sap.com/topics/ai-core-artificial-intelligence%22%20/t%20%22_blank) page to connect with experts and share knowledge.