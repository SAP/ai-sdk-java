# Grounding Services

## Table of Contents

- [Introduction](#introduction)
    - [Prerequisites](#prerequisites)
        - [Maven Dependencies](#maven-dependencies)
- [Usage](#usage)
    - [Data Ingestion](#data-ingestion)
        - [Pipeline API](#pipeline-api)
        - [Vector API](#vector-api)
    - [Data Retrieval](#retrieval-api)
        - [Retrieval API](#create-a-deployment)
        - [Grounding via Orchestration](#grounding-via-orchestration)

## Introduction

This guide provides examples on how to manage data in SAP Document Grounding.
It's divided into two main sections: Data Ingestion and Data Retrieval.

> [!WARNING]  
> The below examples rely on generated model classes.
> Please be aware of the [implications described here](/README.md#general-requirements).

## Prerequisites

Before using the Grounding module, ensure that you have met all the general requirements outlined in the [README.md](../../README.md#general-requirements).
Additionally, include the necessary Maven dependency in your project.

### Maven Dependencies

Add the following dependency to your `pom.xml` file:

```xml
<dependency>
    <groupId>com.sap.ai.sdk</groupId>
    <artifactId>grounding</artifactId>
    <version>${ai-sdk.version}</version>
</dependency>
```

See [an example pom in our Spring Boot application](../../sample-code/spring-app/pom.xml)

## Usage

In addition to the prerequisites above, we assume you have already set up the following to carry out the examples in this guide:

- A running instance of SAP AI Core with correctly setup credentials, including a resource group id.

## Data Ingestion

The following APIs are available for data ingestion: Pipeline and Vector.

### Pipeline API

Consider the following code sample to read pipelines, create a new one and get its status:

```java
var api = new GroundingClient().pipelines();
var resourceGroupId = "default";

// get all pipelines
Pipelines pipelines = api.getAllPipelines(resourceGroupId);

// create new pipeline
var type = "MSSharePoint"; // or "S3" or "SFTP"
var pipelineSecret = "my-secret-name";
var config = PipelinePostRequstConfiguration.create().destination(pipelineSecret);
var request = PipelinePostRequst.create().type(type)._configuration(config);
PipelineId pipeline = api.createPipeline(resourceGroupId, request);

// get pipeline status
PipelineStatus status = api.getPipelineStatus(resourceGroupId, pipeline.getPipelineId());
```

### Vector API

```java
var api = new GroundingClient().vector();
var resourceGroupId = "default";

// resolve collection id
var collectionId = UUID.fromString("12345-123-123-123-0123456abcdef");

var request = DocumentCreateRequest.create()
  .documents(BaseDocument.create()
      .chunks(TextOnlyBaseChunk.create()
          .content("The dog makes _woof_")
          .metadata(KeyValueListPair.create()
              .key("animal").value("dog")))
      .metadata(DocumentKeyValueListPair.create()
          .key("topic").value("sound")));
DocumentsListResponse response = api.createDocuments(resourceGroupId, collectionId, request);
```

Refer to the [DeploymentController.java](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/controllers/DeploymentController.java) in our Spring Boot application for a complete example.

## Data Retrieval

The following APIs are available for data retrieval: Retrieval and Orchestration.


### Retrieval API

Consider the following code sample to search for relevant grounding data based on a query:

```java
var api = new GroundingClient().retrieval();
var resourceGroupId = "default";

var filter =
    RetrievalSearchFilter.create()
        .id("question")
        .dataRepositoryType(DataRepositoryType.VECTOR)
        .dataRepositories(List.of("*"))
        .searchConfiguration(SearchConfiguration.create().maxChunkCount(10));
var search = RetrievalSearchInput.create().query("What is SAP Cloud SDK for AI?").filters(filter);
RetievalSearchResults results = api.search(resourceGroupId, search);
```

### Grounding via Orchestration

You can use the grounding service via orchestration.
Please find the [documentation on Orchestration client in the dedicated document](ORCHESTRATION.md).

```java
OrchestrationClient client;

var databaseFilter =
    DocumentGroundingFilter.create()
        .dataRepositoryType(DataRepositoryType.VECTOR)
        .searchConfig(GroundingFilterSearchConfiguration.create().maxChunkCount(3));
var groundingConfigConfig =
    GroundingModuleConfigConfig.create()
        .inputParams(List.of("query"))
        .outputParam("results")
        .addFiltersItem(databaseFilter);
var groundingConfig =
    GroundingModuleConfig.create()
        .type(GroundingModuleConfig.TypeEnum.DOCUMENT_GROUNDING_SERVICE)
        .config(groundingConfigConfig);
var configWithGrounding = config.withGroundingConfig(groundingConfig);

var inputParams = Map.of("query", "What is SAP Cloud SDK for AI?");

var prompt =
    new OrchestrationPrompt(
        inputParams,
        Message.system("Context message with embedded grounding results. {{?results}}"));

OrchestrationChatResponse response = client.chatCompletion(prompt, configWithGrounding);
```