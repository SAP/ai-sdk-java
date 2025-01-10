package com.sap.ai.sdk.app.controllers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sap.ai.sdk.core.client.ConfigurationApi;
import com.sap.ai.sdk.core.client.DeploymentApi;
import com.sap.ai.sdk.core.model.AiConfigurationBaseData;
import com.sap.ai.sdk.core.model.AiConfigurationCreationResponse;
import com.sap.ai.sdk.core.model.AiDeployment;
import com.sap.ai.sdk.core.model.AiDeploymentCreationRequest;
import com.sap.ai.sdk.core.model.AiDeploymentCreationResponse;
import com.sap.ai.sdk.core.model.AiDeploymentDeletionResponse;
import com.sap.ai.sdk.core.model.AiDeploymentList;
import com.sap.ai.sdk.core.model.AiDeploymentModificationRequest;
import com.sap.ai.sdk.core.model.AiDeploymentTargetStatus;
import com.sap.ai.sdk.core.model.AiParameterArgumentBinding;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints for AI Core AiDeployment operations */
@Slf4j
@RestController
@SuppressWarnings("unused")
@RequestMapping("/deployments")
class DeploymentController {

  private static final DeploymentApi CLIENT = new DeploymentApi();
  private static final String RESOURCE_GROUP = "default";
  private final ObjectMapper mapper =
      new ObjectMapper()
          .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
          .registerModule(new JavaTimeModule());

  /**
   * Create and delete a deployment with the Java specific configuration ID
   *
   * @param configId The configuration id.
   * @return the deployment deletion response
   */
  @Nullable
  AiDeploymentDeletionResponse createAndDeleteDeploymentByConfigId(final String configId) {
    final var deployment =
        CLIENT.create(
            RESOURCE_GROUP, AiDeploymentCreationRequest.create().configurationId(configId));

    // shortly after creation, the deployment will be status UNKNOWN.
    // We can directly DELETE it, without going through STOPPED
    return CLIENT.delete(RESOURCE_GROUP, deployment.getId());
  }

  /**
   * Create and delete a deployment with the Java specific configuration ID
   *
   * @param configId The configuration id.
   * @param accept The accept header.
   * @return a response entity with a string representation of the deployment creation response
   */
  @GetMapping("/by-config/{id}/createDelete")
  ResponseEntity<String> createAndDeleteDeploymentByConfigId(
      @Nonnull @PathVariable("id") final String configId,
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var response = createAndDeleteDeploymentByConfigId(configId);
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(response));
    }
    return ResponseEntity.ok("Deployment created and will be deleted.");
  }

  /**
   * Stop all deployments with the Java specific configuration ID
   *
   * <p>Only RUNNING deployments can be STOPPED
   *
   * @param configId The configuration id.
   * @param accept The accept header.
   * @return a response entity with a string representation of the deployment modification response
   */
  @GetMapping("/by-config/{id}/stop")
  @Nonnull
  ResponseEntity<String> stopByConfigId(
      @Nonnull @PathVariable("id") final String configId,
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final List<AiDeployment> myDeployments = getAllByConfigId(configId);
    log.info("Found {} deployments to STOP", myDeployments.size());

    // STOP my deployments
    final var stoppedDeployments =
        myDeployments.stream()
            .map(
                deployment ->
                    CLIENT.modify(
                        RESOURCE_GROUP,
                        deployment.getId(),
                        AiDeploymentModificationRequest.create()
                            .targetStatus(AiDeploymentTargetStatus.STOPPED)))
            .toList();

    if ("application/json".equals(accept)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(stoppedDeployments));
    }
    return ResponseEntity.ok("Deployments under config the given config ID stopped.");
  }

  /**
   * Delete all deployments with the Java specific configuration ID
   *
   * <p>Only UNKNOWN and STOPPED deployments can be DELETED
   *
   * @param configId The configuration id.
   * @param accept The accept header.
   * @return a response entity with a string representation of the deployment deletion response
   */
  @GetMapping("/by-config/{id}/delete")
  @Nonnull
  ResponseEntity<String> deleteByConfigId(
      @Nonnull @PathVariable("id") final String configId,
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final List<AiDeployment> myDeployments = getAllByConfigId(configId);
    log.info("Found {} deployments to DELETE", myDeployments.size());

    // DELETE my deployments
    final var responseList =
        myDeployments.stream()
            .map(deployment -> CLIENT.delete(RESOURCE_GROUP, deployment.getId()))
            .toList();
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(responseList));
    }
    return ResponseEntity.ok("Deployments under the given config ID deleted.");
  }

  /**
   * Get all deployments with the Java specific configuration ID
   *
   * @param configId The configuration id.
   * @param accept The accept header.
   * @return a response entity with a string representation of the Java specific deployments
   */
  @GetMapping("/by-config/{id}/getAll")
  ResponseEntity<String> getAllByConfigId(
      @Nonnull @PathVariable("id") final String configId,
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var deployments = getAllByConfigId(configId);
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(deployments));
    }
    return ResponseEntity.ok(buildMessage(deployments));
  }

  /**
   * Build a message from the deployment list.
   *
   * @param deployments The deployment list.
   * @return the message
   */
  private String buildMessage(final List<AiDeployment> deployments) {
    final var message = new StringBuilder("The following deployments are available: ");
    for (final var deployment : deployments) {
      message.append(deployment.getId()).append(", ");
    }
    message.setCharAt(message.length() - 2, '.');
    return message.toString();
  }

  /**
   * Get all deployments with the Java specific configuration ID
   *
   * @param configId The configuration id.
   * @return the Java specific deployments
   */
  @Nonnull
  List<AiDeployment> getAllByConfigId(@Nonnull @PathVariable("id") final String configId) {
    final AiDeploymentList deploymentList = CLIENT.query(RESOURCE_GROUP);

    return deploymentList.getResources().stream()
        .filter(deployment -> configId.equals(deployment.getConfigurationId()))
        .toList();
  }

  /**
   * Get all deployments, including non-Java specific deployments
   *
   * @param accept The accept header.
   * @return a response entity with a string representation of the Java specific deployments
   */
  @GetMapping("/getAll")
  @Nonnull
  ResponseEntity<String> getAll(
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var deployments = getAll();
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(deployments));
    }
    return ResponseEntity.ok(buildMessage(deployments.getResources().stream().toList()));
  }

  /**
   * Get all deployments
   *
   * @return all deployments
   */
  @Nullable
  AiDeploymentList getAll() {
    return CLIENT.query(RESOURCE_GROUP);
  }

  /**
   * Create a configuration, and deploy it.
   *
   * <p>This is to be invoked from a unit test.
   *
   * @param model The OpenAI model to deploy
   * @return the deployment creation response
   */
  @Nonnull
  @SuppressWarnings("unused") // debug method that doesn't need to be tested
  AiDeploymentCreationResponse createConfigAndDeploy(final OpenAiModel model) {

    // Create a configuration
    final var modelNameParameter =
        AiParameterArgumentBinding.create().key("model").value(model.name());
    final var modelVersion =
        AiParameterArgumentBinding.create().key("modelVersion").value("latest");
    final var configurationBaseData =
        AiConfigurationBaseData.create()
            .name(model.name())
            .executableId("azure-openai")
            .scenarioId("foundation-models")
            .addParameterBindingsItem(modelNameParameter)
            .addParameterBindingsItem(modelVersion);

    final AiConfigurationCreationResponse configuration =
        new ConfigurationApi().create(RESOURCE_GROUP, configurationBaseData);

    // Create a deployment from the configuration
    final var deploymentCreationRequest =
        AiDeploymentCreationRequest.create().configurationId(configuration.getId());

    return CLIENT.create(RESOURCE_GROUP, deploymentCreationRequest);
  }
}
