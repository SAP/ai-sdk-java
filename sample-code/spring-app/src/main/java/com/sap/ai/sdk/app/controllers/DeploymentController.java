package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.core.model.AiDeploymentTargetStatus.STOPPED;

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
import com.sap.ai.sdk.core.model.AiParameterArgumentBinding;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiModel;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints for AI Core AiDeployment operations */
@Slf4j
@RestController
@SuppressWarnings("unused")
@RequestMapping("/deployments")
class DeploymentController {

  private static final DeploymentApi CLIENT = new DeploymentApi();
  private static final String RESOURCE_GROUP = "default";

  /** Create and delete a deployment with the Java specific configuration ID. */
  @Nullable
  AiDeploymentDeletionResponse createAndDeleteDeploymentByConfigId(final String configId) {
    final var deployment =
        CLIENT.create(
            RESOURCE_GROUP, AiDeploymentCreationRequest.create().configurationId(configId));

    // shortly after creation, the deployment will be status UNKNOWN.
    // We can directly DELETE it, without going through STOPPED
    return CLIENT.delete(RESOURCE_GROUP, deployment.getId());
  }

  /** Create and delete a deployment with the Java specific configuration ID. */
  @GetMapping("/by-config/{id}/createDelete")
  Object createAndDeleteDeploymentByConfigId(
      @Nonnull @PathVariable("id") final String configId,
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var response = createAndDeleteDeploymentByConfigId(configId);
    if ("json".equals(format)) {
      return response;
    }
    return ResponseEntity.ok("Deployment created and will be deleted.");
  }

  /**
   * Stop all deployments with the Java specific configuration ID.
   *
   * <p>Only RUNNING deployments can be STOPPED
   */
  @GetMapping("/by-config/{id}/stop")
  Object stopByConfigId(
      @Nonnull @PathVariable("id") final String configId,
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final List<AiDeployment> myDeployments = getAllByConfigId(configId);
    log.info("Found {} deployments to STOP", myDeployments.size());

    // STOP my deployments
    final var modificationRequest = AiDeploymentModificationRequest.create().targetStatus(STOPPED);
    final var stoppedDeployments =
        myDeployments.stream()
            .map(AiDeployment::getId)
            .map(id -> CLIENT.modify(RESOURCE_GROUP, id, modificationRequest))
            .toList();

    if ("json".equals(format)) {
      return stoppedDeployments;
    }
    return "Deployments under the given config ID stopped.";
  }

  /**
   * Delete all deployments with the Java specific configuration ID.
   *
   * <p>Only UNKNOWN and STOPPED deployments can be DELETED
   */
  @GetMapping("/by-config/{id}/delete")
  Object deleteByConfigId(
      @Nonnull @PathVariable("id") final String configId,
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final List<AiDeployment> myDeployments = getAllByConfigId(configId);
    log.info("Found {} deployments to DELETE", myDeployments.size());

    // DELETE my deployments
    final var responseList =
        myDeployments.stream()
            .map(deployment -> CLIENT.delete(RESOURCE_GROUP, deployment.getId()))
            .toList();
    if ("json".equals(format)) {
      return responseList;
    }
    return "Deployments under the given config ID deleted.";
  }

  /** Get all deployments with the Java specific configuration ID. */
  @GetMapping("/by-config/{id}/getAll")
  Object getAllByConfigId(
      @Nonnull @PathVariable("id") final String configId,
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var deployments = getAllByConfigId(configId);
    if ("json".equals(format)) {
      return deployments;
    }
    final var items =
        deployments.stream().map(AiDeployment::getId).collect(Collectors.joining(", "));
    return "The following Java-specific deployments are available: %s.".formatted(items);
  }

  /** Get all deployments with the Java specific configuration ID. */
  List<AiDeployment> getAllByConfigId(@Nonnull @PathVariable("id") final String configId) {
    final AiDeploymentList deploymentList = CLIENT.query(RESOURCE_GROUP);

    return deploymentList.getResources().stream()
        .filter(deployment -> configId.equals(deployment.getConfigurationId()))
        .toList();
  }

  /** Get all deployments, including non-Java specific deployments */
  @GetMapping("/getAll")
  Object getAll(@Nullable @RequestParam(value = "format", required = false) final String format) {
    final var deployments = getAll();
    if ("json".equals(format)) {
      return deployments;
    }
    final var items =
        deployments != null
            ? deployments.getResources().stream()
                .map(AiDeployment::getId)
                .collect(Collectors.joining(", "))
            : "";
    return "The following deployments are available: %s.".formatted(items);
  }

  /** Get all deployments */
  @Nullable
  AiDeploymentList getAll() {
    return CLIENT.query(RESOURCE_GROUP);
  }

  /**
   * Create a configuration, and deploy it.
   *
   * <p>This is to be invoked from a unit test.
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
