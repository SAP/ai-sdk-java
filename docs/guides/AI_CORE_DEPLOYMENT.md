# AI Core Deployment

## Table of Contents

- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Maven Dependencies](#maven-dependencies)
- [Usage](#usage)
  - [Create a Deployment](#create-a-deployment)
  - [Delete a Deployment](#delete-a-deployment)

## Introduction

This guide provides examples on how to create and manage deployments in SAP AI Core using the SAP AI
SDK for Java.

## Prerequisites

Before proceeding, ensure you have met the general requirements outlined in
the [README.md](../../README.md#general-requirements). 

Additionally, to carry out the examples in this guide, you need:

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

## Maven dependencies

Add the following dependencies to your `pom.xml` file:

```xml

<dependencies>
    <dependency>
        <groupId>com.sap.ai.sdk</groupId>
        <artifactId>core</artifactId>
        <version>${ai-sdk.version}</version>
    </dependency>
</dependencies>
```

See [an example pom in our Spring Boot application](../../sample-code/spring-app/pom.xml)

## Usage

## Create a Deployment

Use the following code snippet to create a deployment in SAP AI Core:

```java
public AiDeploymentCreationResponse createDeployment() {
  
  final AiDeploymentCreationResponse deployment =
      new DeploymentApi(getClient())
          .create(
              "default",
              AiDeploymentCreationRequest.create()
                  .configurationId("12345-123-123-123-123456abcdefg"));
  
  String id = deployment.getId();
  AiExecutionStatus status = deployment.getStatus();
  
  return deployment;
}
```

Refer to the [DeploymentController.java](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/controllers/DeploymentController.java) in our Spring Boot application for a complete example.

## Delete a Deployment

```java
public AiDeploymentDeletionResponse deleteDeployment(AiDeploymentCreationResponse deployment) {
  
  DeploymentApi client = new DeploymentApi(getClient());
  
  if (deployment.getStatus() == AiExecutionStatus.RUNNING) {
    // Only RUNNING deployments can be STOPPED
    client.modify(
        "default",
        deployment.getId(),
        AiDeploymentModificationRequest.create().targetStatus(AiDeploymentTargetStatus.STOPPED));
  }
  // Wait a few seconds for the deployment to stop
  // Only UNKNOWN and STOPPED deployments can be DELETED
  return client.delete("default", deployment.getId());
}
```

Refer to the [DeploymentController.java](../../sample-code/spring-app/src/main/java/com/sap/ai/sdk/app/controllers/DeploymentController.java) in our Spring Boot application for a complete example.
