package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.app.services.TabularService.ArtifactService;
import com.sap.ai.sdk.app.services.TabularService.DataDestinationService;
import com.sap.ai.sdk.app.services.TabularService.PredictionService;
import com.sap.ai.sdk.app.services.TabularService.ScenarioConfigurationService;
import com.sap.ai.sdk.tabular.generated.orchestration.model.AutoDefinition;
import com.sap.ai.sdk.tabular.generated.orchestration.model.CSNDefinition;
import com.sap.ai.sdk.tabular.generated.orchestration.model.DeltaSharingDataDestinationGetResponse;
import com.sap.ai.sdk.tabular.generated.orchestration.model.DocumentDefinition;
import com.sap.ai.sdk.tabular.generated.orchestration.model.GetDataDestination;
import com.sap.ai.sdk.tabular.generated.orchestration.model.HDLDataDestinationGetResponse;
import com.sap.ai.sdk.tabular.generated.orchestration.model.ObjectStoreDataDestinationGetResponse;
import com.sap.ai.sdk.tabular.generated.orchestration.model.ReferenceDefinition;
import com.sap.ai.sdk.tabular.generated.orchestration.model.ScenarioConfigurationObject;
import com.sap.ai.sdk.tabular.generated.orchestration.model.TabularArtifactDetails;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoints for Tabular Orchestration operations.
 *
 * <p>Note: This code does not return the SDK model objects as JSON. Instead, it converts them to
 * plain {@link Map}s with each model's {@code toMap()} method. Reason: {@code GetDataDestination}
 * and {@code CSNDefinition} are interfaces. Jackson cannot serialize these interfaces to JSON. This
 * is a known issue in the generated code, not a bug in Spring or Jackson.
 */
@Slf4j
@RestController
@SuppressWarnings("unused")
@RequestMapping("/tabular")
class TabularController {

  private static final DataDestinationService DATA_DESTINATION_SERVICE =
      new DataDestinationService();
  private static final ArtifactService ARTIFACT_SERVICE = new ArtifactService();
  private static final ScenarioConfigurationService SCENARIO_CONFIGURATION_SERVICE =
      new ScenarioConfigurationService();
  private static final PredictionService PREDICTION_SERVICE = new PredictionService();

  /** List all data destinations for the default resource group. */
  @GetMapping("/data-destinations/list")
  Object getAllDataDestinations(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = DATA_DESTINATION_SERVICE.getAllDataDestinations();
    if ("json".equals(format)) {
      val resources = response.getResources().stream().map(TabularController::toMap).toList();
      return Map.of("count", response.getCount(), "resources", resources);
    }
    val types = response.getResources().stream().map(GetDataDestination::getType).toList();
    return "Found data destinations with types: " + types;
  }

  /** Create a new Hana Data Lake data destination. */
  @GetMapping("/data-destinations/create")
  Object createDataDestination(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = DATA_DESTINATION_SERVICE.createHanaDataLakeDataDestination();
    if ("json".equals(format)) {
      return response;
    }
    return "Created data destination: " + response.getName();
  }

  /** List all tabular artifacts for the default resource group. */
  @GetMapping("/artifacts/list")
  Object getAllArtifacts(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = ARTIFACT_SERVICE.getAllArtifacts();
    if ("json".equals(format)) {
      val resources = response.getResources().stream().map(TabularController::toMap).toList();
      return Map.of("count", response.getCount(), "resources", resources);
    }
    val names = response.getResources().stream().map(TabularArtifactDetails::getName).toList();
    return "Found tabular artifacts: " + names;
  }

  /** Create a new tabular artifact from a Parquet file. */
  @GetMapping("/artifacts/create")
  Object createArtifact(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = ARTIFACT_SERVICE.createArtifact();
    if ("json".equals(format)) {
      return response;
    }
    return "Created tabular artifact: " + response.getName();
  }

  /** List all scenario configurations for the default resource group. */
  @GetMapping("/scenario-configurations/list")
  Object getAllScenarioConfigurations(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = SCENARIO_CONFIGURATION_SERVICE.getAllScenarioConfigurations();
    if ("json".equals(format)) {
      return response;
    }
    val names = response.getResources().stream().map(ScenarioConfigurationObject::getName).toList();
    return "Found scenario configurations: " + names;
  }

  /** Create a new scenario configuration for product prediction. */
  @GetMapping("/scenario-configurations/create")
  Object createScenarioConfiguration(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = SCENARIO_CONFIGURATION_SERVICE.createScenarioConfiguration();
    if ("json".equals(format)) {
      return response;
    }
    return "Created scenario configuration: " + response.getName();
  }

  /** Run a prediction using a deployed Tabular Foundation Model. */
  @GetMapping("/predict")
  Object predict(@Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = PREDICTION_SERVICE.predict();
    if ("json".equals(format)) {
      return response.toMap();
    }
    val predictions =
        response.getPredictions().stream()
            .map(TabularController::extractSalesGroupPredictions)
            .toList();
    return "Predictions: " + predictions;
  }

  /**
   * Get the predicted values for "salesgroup" from one prediction row.
   *
   * @param row One prediction row.
   * @return The list of predicted values for "salesgroup".
   */
  @Nonnull
  @SuppressWarnings("unchecked")
  private static List<Object> extractSalesGroupPredictions(@Nonnull final Map<String, Object> row) {
    val salesgroup = (List<Map<String, Object>>) row.get("salesgroup");
    if (salesgroup == null) {
      return List.of();
    }
    return salesgroup.stream().map(candidate -> candidate.get("prediction")).toList();
  }

  private static Map<String, Object> toMap(@Nonnull final GetDataDestination destination) {
    if (destination instanceof HDLDataDestinationGetResponse hdl) {
      return hdl.toMap();
    } else if (destination instanceof ObjectStoreDataDestinationGetResponse objectStore) {
      return objectStore.toMap();
    } else if (destination instanceof DeltaSharingDataDestinationGetResponse deltaSharing) {
      return deltaSharing.toMap();
    }
    return Map.of("type", destination.getType());
  }

  private static Map<String, Object> toMap(@Nonnull final TabularArtifactDetails artifact) {
    val map = new LinkedHashMap<>(artifact.toMap());
    val csnMetadata = artifact.getCsnMetadata();
    if (csnMetadata != null) {
      val csnMetadataMap = new LinkedHashMap<>(csnMetadata.toMap());
      val definition = csnMetadata.getDefinition();
      csnMetadataMap.put("definition", toMap(definition));
      map.put("csnMetadata", csnMetadataMap);
    }
    return map;
  }

  private static Map<String, Object> toMap(@Nonnull final CSNDefinition definition) {
    if (definition instanceof DocumentDefinition document) {
      return document.toMap();
    } else if (definition instanceof ReferenceDefinition reference) {
      return reference.toMap();
    } else if (definition instanceof AutoDefinition auto) {
      return auto.toMap();
    }
    return Map.of("definitionType", definition.getDefinitionType());
  }
}
