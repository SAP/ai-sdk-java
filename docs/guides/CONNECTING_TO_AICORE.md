# Connecting to AI Core

## Table of Contents

- [Using a Service Binding](#using-a-service-binding)
    - [Providing a Service Binding Locally](#providing-a-service-binding-locally)
    - [Using the `AICORE_SERVICE_KEY` Environment Variable](#using-the-aicore_service_key-environment-variable)
- [Using a Destination from the BTP Destination Service](#using-a-destination-from-the-btp-destination-service)
 

The AI SDK uses the [`Destination` concept of the SAP Cloud SDK](https://sap.github.io/cloud-sdk/docs/java/features/connectivity/destination-service) to connect to AI Core.
This allows for a seamless integration in BTP and offers a wide variety of managing the credentials for the connection.

## Using a Service Binding

By default, the AI SDK will look for an `aicore` service binding in the environment of the application.

When running on SAP BTP, [create an instance of the AI Core service](https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/create-service-instance) and bind it to your application.
The AI SDK will automatically detect the service binding and use it to connect to AI Core.

If no service binding could be found (and no other destination has been given), the AI SDK will throw an exception:

> Could not find any matching service bindings for service identifier 'aicore'

To run your code outside SAP BTP (e.g. locally), you will have to provide the credentials manually.

### Providing a Service Binding Locally

For local development, you can provide the service credentials locally in various ways.

#### Using CAP Hybrid Testing

When developing a [CAP application](https://cap.cloud.sap/docs/), you can use the [hybrid testing](https://cap.cloud.sap/docs/advanced/hybrid-testing#services-on-cloud-foundry) approach.
Assuming your AI Core service instance is called `aicore`, the following command runs a Maven command with the credentials of the service instance:

```bash
cds bind --to aicore --exec mvn spring-boot:run
```

#### Using the `AICORE_SERVICE_KEY` Environment Variable

You can also provide the service credentials in the `AICORE_SERVICE_KEY` environment variable.
First, [create a service key for your AI Core service instance](https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/create-service-key).
Copy the resulting JSON object.

Then set the environment variable `AICORE_SERVICE_KEY` to be defined for however you want to run your code.
How exactly to set this depends on how you run your code (e.g. via IDE, command line etc.) and potentially on your operating system.

Here are three examples of how this can be done:

1. <details>
    <summary>Using a `.env` file</summary>
   
    Create a `.env` file in the **working directory** from which you run your code.
    Add the following line:
   
    ```txt
    AICORE_SERVICE_KEY={ "clientid": "...", "clientsecret": "...", "url": "...", "serviceurls": { "AI_API_URL": "..." } }
    ```

    > [!IMPORTANT]
    > The value of `AICORE_SERVICE_KEY` must be a single line, so remove any line breaks from the service key JSON.

   </details>

2. <details> 
   <summary>Using the command line on Mac OS</summary>
   
    Run your code with the following command:
    
    ```bash
    export AICORE_SERVICE_KEY='{ "clientid": "...", "clientsecret": "...", "url": "...", "serviceurls": { "AI_API_URL": "..." } }'
    mvn ...
    ```

    </details>

3. <details> 
   <summary>Using PowerShell on Windows</summary>

   Run your code with the following command:

    ```shell
    $env:AICORE_SERVICE_KEY='{ "clientid": "...", "clientsecret": "...", "url": "...", "serviceurls": { "AI_API_URL": "..." } }'
    mvn ...
    ```

    </details>

4. <details>
    <summary>Using an IntelliJ run configuration</summary>
   
    In IntelliJ, go to `Run` > `Edit Configurations...` > `Environment variables`.
    Add a new environment variable with the name `AICORE_SERVICE_KEY` and paste the service key as value.
   
    For more information, see the [official IntelliJ documentation](https://www.jetbrains.com/help/idea/run-debug-configuration-application.html#configure-environment-variables). 
    
   </details>

## Using a Destination from the BTP Destination Service

You can define a destination in the BTP Destination Service and use that to connect to AI Core. 

<details>
<summary>How to create a Destination in the SAP BTP Cockpit</summary>

1. [Create a service key for your AI Core service instance](https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/create-service-key).

2. Create a new Destination in the SAP BTP Cockpit with the following properties:

  - **Name**: `my-aicore`
  - **Type**: `HTTP`
  - **URL**: `[serviceurls.AI_API_URL]`
  - **Proxy Type**: `Internet`
  - **Authentication**: `OAuth2ClientCredentials`
  - **Client ID**: `[clientid]`
  - **Client Secret**: `[clientsecret]`
  - **Token Service URL Type**: `Dedicated`
  - **Token Service URL**: `[url]`
  
    Fill in the values for URL, client ID, client secret, and token service URL from the service key JSON.

</details>

To use the destination, ensure you have created an instance of the BTP Destination Service and bound it to your application.

```java
Destination destination = DestinationAccessor.getDestination("my-aicore").asHttp();
AiCoreService aiCoreService = new AiCoreService().withBaseDestination(destination);
```

Now you can use the `AiCoreService` as follows:

```java
// To create an OpenAiClient:
var openAiDestination = aiCoreService.getInferenceDestination().forModel(OpenAiModel.TEXT_EMBEDDING_3_LARGE);
var client = OpenAiClient.withCustomDestination(openAiDestination);

// To create an OrchestrationClient:
var orchestrationDestination = aiCoreService.getInferenceDestination().forScenario("orchestration");
var client = new OrchestrationClient(orchestrationDestination);

// To create any of the com.sap.ai.sdk.core.client.* classes (e.g. DeploymentApi):
new DeploymentApi(aiCoreService);
```

> [!IMPORTANT]
> The `destination` obtained from BTP destination service will expire once the contained OAuth2 token expires.
> Please run the above code for each request to ensure the destination is up-to-date.
> Destinations are cached by default, so this does not come with a performance penalty.
> To learn more, see the [SAP Cloud SDK documentation](https://sap.github.io/cloud-sdk/docs/java/features/connectivity/destination-service).