# AI Core Deployment

## Table of Contents

- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
    - [Maven Dependencies](#maven-dependencies)
- [Usage](#usage)
    - [Create a Deployment](#create-a-deployment)
    - [Delete a Deployment](#delete-a-deployment)

## Introduction

This guide provides examples on how to create and manage deployments in SAP AI Core using the SAP AI SDK for Java.

> [!WARNING]  
> The below examples rely on generated model classes.
> Please be aware of the [implications described here](/README.md#general-requirements).

## Prerequisites

Before using the AI Core module, ensure that you have met all the general requirements outlined in the [README.md](../../README.md#general-requirements).
Additionally, include the necessary Maven dependency in your project.

### Maven Dependencies

Add the following dependency to your `pom.xml` file:

```xml
<dependency>
    <groupId>com.sap.ai.sdk</groupId>
    <artifactId>core</artifactId>
    <version>${ai-sdk.version}</version>
</dependency>
```

See [an example pom in our Spring Boot application](../../sample-code/spring-app/pom.xml)

## Usage

In addition to the prerequisites above, we assume you have already set up the following to carry out the examples in this guide:

- **An AI Core Configuration** created in SAP AI Core.
    - <details>
      <summary>Example configuration from the AI Core <code>/configuration</code> endpoint</summary>

      ```json
      {
        "createdAt": "2024-07-03T12:44:08Z",
        "executableId": "azure-openai",
        "id": "12345-123-123-123-123456abcdefg",
        "inputArtifactBindings": [],
        "name": "gpt-35-turbo",
        "parameterBindings": [
          {
            "key": "modelName",
            "value": "gpt-35-turbo"
          },
          {
            "key": "modelVersion",
            "value": "latest"
          }
        ],
        "scenarioId": "foundation-models"
      }
      ```
      </details>

### Create a Deployment

Use the following code snippet to create a deployment in SAP AI Core:

```java
var api = new DeploymentApi();
var resourceGroupId = "default";
var configurationId = "12345-123-123-123-123456abcdefg";

var request = AiDeploymentCreationRequest.create().configurationId(configurationId);
var deployment = api.create(resourceGroupId, request);

String id = deployment.getId();
AiExecutionStatus status = deployment.getStatus();
```

Refer to the [DeploymentController.java](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/controllers/DeploymentController.java) in our Spring Boot application for a complete example.

### Delete a Deployment

```java
AiDeploymentCreationResponse deployment; // provided
String deploymentId = deployment.getId();
  
var api = new DeploymentApi();
var resourceGroupId = "default";
  
if (deployment.getStatus() == AiExecutionStatus.RUNNING) {
  // Only RUNNING deployments can be STOPPED
  api.modify(
    resourceGroupId,
    deploymentId,
    AiDeploymentModificationRequest.create().targetStatus(AiDeploymentTargetStatus.STOPPED));
}
// Wait a few seconds for the deployment to stop
// Only UNKNOWN and STOPPED deployments can be DELETED
api.delete(resourceGroupId, deployment.getId());
```

Refer to the [DeploymentController.java](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/controllers/DeploymentController.java) in our Spring Boot application for a complete example.
