package com.sap.ai.sdk.foundationmodels.rpt;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.not;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.sap.ai.sdk.foundationmodels.rpt.generated.model.ColumnType.STRING;
import static com.sap.ai.sdk.foundationmodels.rpt.generated.model.TargetColumnConfig.TaskTypeEnum.CLASSIFICATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sap.ai.sdk.core.JacksonConfiguration;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.*;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Cache;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.conf.PlainParquetConfiguration;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.io.LocalOutputFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@WireMockTest
class RptClientTest {
  private static RptClient client;

  private static final Map<String, SchemaFieldConfig> DATA_SCHEMA =
      Map.of(
          "PRODUCT", SchemaFieldConfig.create().dtype(STRING),
          "PRICE", SchemaFieldConfig.create().dtype(STRING),
          "ORDERDATE", SchemaFieldConfig.create().dtype(ColumnType.DATE),
          "ID", SchemaFieldConfig.create().dtype(STRING),
          "COSTCENTER", SchemaFieldConfig.create().dtype(STRING));

  private static final List<TargetColumnConfig> TARGET_COLUMN =
      List.of(
          TargetColumnConfig.create()
              .name("COSTCENTER")
              .predictionPlaceholder(PredictionPlaceholder.create("[PREDICT]"))
              .taskType(CLASSIFICATION));

  @BeforeEach
  void setup(final WireMockRuntimeInfo server) {
    final DefaultHttpDestination destination =
        DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = RptClient.forDestination(destination, true);
    ApacheHttpClient5Accessor.setHttpClientCache(ApacheHttpClient5Cache.DISABLED);
  }

  @Test
  void testRptModels() {
    assertThat(RptModel.SAP_RPT_1_SMALL.name()).isEqualTo("sap-rpt-1-small");
    assertThat(RptModel.SAP_RPT_1_SMALL.version()).isNull();

    assertThat(RptModel.SAP_RPT_1_LARGE.name()).isEqualTo("sap-rpt-1-large");
    assertThat(RptModel.SAP_RPT_1_LARGE.version()).isNull();

    final var modelWithVersion = RptModel.SAP_RPT_1_SMALL.withVersion("v1.0");
    assertThat(modelWithVersion.name()).isEqualTo("sap-rpt-1-small");
    assertThat(modelWithVersion.version()).isEqualTo("v1.0");
  }

  @Test
  void testTableCompletionWithRowsFormat() {
    final List<Map<String, RowsInnerValue>> rows =
        List.of(
            Map.of(
                "PRODUCT", RowsInnerValue.create("Couch"),
                "PRICE", RowsInnerValue.create(BigDecimal.valueOf(999.99)),
                "ORDERDATE", RowsInnerValue.create("28-11-2025"),
                "ID", RowsInnerValue.create("35"),
                "COSTCENTER", RowsInnerValue.create("[PREDICT]")),
            Map.of(
                "PRODUCT", RowsInnerValue.create("Office Chair"),
                "PRICE", RowsInnerValue.create(BigDecimal.valueOf(150.8)),
                "ORDERDATE", RowsInnerValue.create("02-11-2025"),
                "ID", RowsInnerValue.create("44"),
                "COSTCENTER", RowsInnerValue.create("Office Furniture")),
            Map.of(
                "PRODUCT", RowsInnerValue.create("Server Rack"),
                "PRICE", RowsInnerValue.create(BigDecimal.valueOf(2200.00)),
                "ORDERDATE", RowsInnerValue.create("01-11-2025"),
                "ID", RowsInnerValue.create("104"),
                "COSTCENTER", RowsInnerValue.create("Data Infrastructure")));
    val request =
        PredictRequestPayloadOneOf.create()
            .predictionConfig(PredictionConfig.create().targetColumns(TARGET_COLUMN))
            .rows(rows)
            .indexColumn("ID")
            .dataSchema(DATA_SCHEMA)
            .parseDataTypes(true);

    final PredictResponsePayload response = client.tableCompletion(request);

    assertThat(response).isNotNull();
    assertThat(response.getId()).isEqualTo("0381c575-9ee5-46f1-9223-4f9caf039e48");
    assertThat(response.getMetadata().getNumColumns()).isEqualTo(5);
    assertThat(response.getMetadata().getNumPredictions()).isEqualTo(1);
    assertThat(response.getMetadata().getNumQueryRows()).isEqualTo(1);
    assertThat(response.getMetadata().getNumRows()).isEqualTo(2);

    assertThat(response.getStatus().getCode()).isEqualTo(0);
    assertThat(response.getStatus().getMessage()).isEqualTo("ok");
    assertThat(response.getPredictions()).hasSize(1);

    final Map<String, PredictionsInnerValue> prediction = response.getPredictions().get(0);
    assertThat(prediction)
        .containsEntry("ID", PredictionsInnerValue.create("35"))
        .containsEntry(
            "COSTCENTER",
            PredictionsInnerValue.createListOfPredictionResults(
                List.of(
                    PredictionResult.create()
                        .prediction(Prediction.create("Office Furniture"))
                        .confidence(BigDecimal.valueOf(0.97)))));
  }

  @Test
  void testTableCompletionWithColumnsFormat() {
    final Map<String, List<RowsInnerValue>> columns =
        Map.of(
            "PRODUCT",
                List.of(
                    RowsInnerValue.create("Couch"),
                    RowsInnerValue.create("Office Chair"),
                    RowsInnerValue.create("Server Rack")),
            "PRICE",
                List.of(
                    RowsInnerValue.create(BigDecimal.valueOf(999.99)),
                    RowsInnerValue.create(BigDecimal.valueOf(150.8)),
                    RowsInnerValue.create(BigDecimal.valueOf(2200.00))),
            "ORDERDATE",
                List.of(
                    RowsInnerValue.create("28-11-2025"),
                    RowsInnerValue.create("02-11-2025"),
                    RowsInnerValue.create("01-11-2025")),
            "ID",
                List.of(
                    RowsInnerValue.create("35"),
                    RowsInnerValue.create("44"),
                    RowsInnerValue.create("104")),
            "COSTCENTER",
                List.of(
                    RowsInnerValue.create("[PREDICT]"),
                    RowsInnerValue.create("Office Furniture"),
                    RowsInnerValue.create("Data Infrastructure")));
    val request =
        PredictRequestPayloadOneOf1.create()
            .predictionConfig(PredictionConfig.create().targetColumns(TARGET_COLUMN))
            .columns(columns)
            .indexColumn("ID")
            .dataSchema(DATA_SCHEMA)
            .parseDataTypes(true);

    final PredictResponsePayload response = client.tableCompletion(request);

    assertThat(response).isNotNull();
    assertThat(response.getId()).isEqualTo("f89fb682-8d6b-4ef5-97f4-bd3c9aab8c49");
    assertThat(response.getMetadata().getNumColumns()).isEqualTo(5);
    assertThat(response.getMetadata().getNumPredictions()).isEqualTo(1);
    assertThat(response.getMetadata().getNumQueryRows()).isEqualTo(1);
    assertThat(response.getMetadata().getNumRows()).isEqualTo(2);

    assertThat(response.getStatus().getCode()).isEqualTo(0);
    assertThat(response.getStatus().getMessage()).isEqualTo("ok");
    assertThat(response.getPredictions()).hasSize(1);

    final Map<String, PredictionsInnerValue> prediction = response.getPredictions().get(0);
    assertThat(prediction)
        .containsEntry("ID", PredictionsInnerValue.create("35"))
        .containsEntry(
            "COSTCENTER",
            PredictionsInnerValue.createListOfPredictionResults(
                List.of(
                    PredictionResult.create()
                        .prediction(Prediction.create("Office Furniture"))
                        .confidence(BigDecimal.valueOf(0.97)))));
  }

  @SneakyThrows
  @Disabled("Used to generate test-data.parquet file")
  @Test
  void generateTestParquetFile() {
    val schemaString =
        """
        {
          "type": "record",
          "name": "TableData",
          "fields": [
            {"name": "PRODUCT", "type": "string"},
            {"name": "PRICE", "type": "double"},
            {"name": "ORDERDATE", "type": "string"},
            {"name": "ID", "type": "string"},
            {"name": "COSTCENTER", "type": "string"}
          ]
        }
        """;

    final Schema schema = new Schema.Parser().parse(schemaString);
    val outputPath = Path.of("src/test/resources/rpt/test-data.parquet");

    try (ParquetWriter<GenericRecord> writer =
        AvroParquetWriter.<GenericRecord>builder(new LocalOutputFile(outputPath))
            .withSchema(schema)
            .withConf(new PlainParquetConfiguration())
            .withWriteMode(ParquetFileWriter.Mode.OVERWRITE)
            .build()) {

      writer.write(recordFor(schema, "Couch", 999.99, "28-11-2025", "35", "[PREDICT]"));
      writer.write(
          recordFor(schema, "Office Chair", 150.8, "02-11-2025", "44", "Office Furniture"));
      writer.write(
          recordFor(schema, "Server Rack", 2200.00, "01-11-2025", "104", "Data Infrastructure"));
    }

    assertThat(outputPath).exists();
    assertThat(outputPath.toFile()).isFile();
    assertThat(outputPath.toFile().length()).isGreaterThan(0);
  }

  private static GenericRecord recordFor(
      final Schema schema,
      final String product,
      final double price,
      final String orderDate,
      final String id,
      final String costCenter) {
    final GenericRecord record = new GenericData.Record(schema);
    record.put("PRODUCT", product);
    record.put("PRICE", price);
    record.put("ORDERDATE", orderDate);
    record.put("ID", id);
    record.put("COSTCENTER", costCenter);
    return record;
  }

  @Test
  void testTableCompletionWithParquetFile() {
    val parquetFile = Path.of("src/test/resources/rpt/test-data.parquet").toFile();

    val targetConfig =
        TargetColumnConfig.create()
            .name("COSTCENTER")
            .predictionPlaceholder(PredictionPlaceholder.create("[PREDICT]"))
            .taskType(CLASSIFICATION);
    val predictionConfig = PredictionConfig.create().targetColumns(List.of(targetConfig));

    val response = client.tableCompletion(parquetFile, predictionConfig);

    assertThat(response).isNotNull();
    assertThat(response.getId()).isEqualTo("3a4ed523-b834-4685-8538-92f94b3cfa1d");
    assertThat(response.getMetadata().getNumColumns()).isEqualTo(5);
    assertThat(response.getMetadata().getNumPredictions()).isEqualTo(1);
    assertThat(response.getMetadata().getNumQueryRows()).isEqualTo(1);
    assertThat(response.getMetadata().getNumRows()).isEqualTo(2);

    assertThat(response.getStatus().getCode()).isEqualTo(0);
    assertThat(response.getStatus().getMessage()).isEqualTo("ok");
    assertThat(response.getPredictions()).hasSize(1);

    final Map<String, PredictionsInnerValue> prediction = response.getPredictions().get(0);
    assertThat(prediction)
        .containsEntry(
            "COSTCENTER",
            PredictionsInnerValue.createListOfPredictionResults(
                List.of(
                    PredictionResult.create()
                        .prediction(Prediction.create("Office Furniture"))
                        .confidence(BigDecimal.valueOf(0.95)))));
  }

  @SneakyThrows
  @Test
  void testTableCompletionWithParquetThrowsIllegalArgumentException() {
    val predictionConfig = mock(PredictionConfig.class);
    val objectMapper = mock(JsonMapper.class);

    try (val mockedStatic = mockStatic(JacksonConfiguration.class)) {
      mockedStatic.when(JacksonConfiguration::getDefaultObjectMapper).thenReturn(objectMapper);

      when(objectMapper.writeValueAsString(any()))
          .thenThrow(new JsonProcessingException("Test") {});
      assertThatThrownBy(() -> client.tableCompletion(mock(File.class), predictionConfig))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Failed to serialize PredictionConfig");
    }
  }

  @Test
  void testOldModelThrowsOnUnknownPayloadType() {
    final var oldModelClient =
        RptClient.forDestination(DefaultHttpDestination.builder("http://localhost").build(), false);
    final var unknownPayload = mock(PredictRequestPayload.class);

    assertThatThrownBy(() -> oldModelClient.tableCompletion(unknownPayload))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Unsupported PredictRequestPayload type");
  }

  @Test
  void testContextModeStrippedFromRowWiseRequest(final WireMockRuntimeInfo server) {
    stubFor(post(urlEqualTo("/predict")).willReturn(aResponse().withStatus(200).withBody("{}")));
    final var oldModelClient =
        RptClient.forDestination(
            DefaultHttpDestination.builder(server.getHttpBaseUrl()).build(), false);

    val config =
        PredictionConfig.create()
            .targetColumns(TARGET_COLUMN)
            .contextMode(PredictionConfig.ContextModeEnum.DEEP);
    val request =
        PredictRequestPayloadOneOf.create()
            .predictionConfig(config)
            .rows(List.of())
            .indexColumn("ID")
            .dataSchema(DATA_SCHEMA)
            .parseDataTypes(true);

    assertThat(config.getContextMode()).isEqualTo(PredictionConfig.ContextModeEnum.DEEP);
    oldModelClient.tableCompletion(request);
    assertThat(config.getContextMode()).isNull();
    verify(
        postRequestedFor(urlEqualTo("/predict")).withRequestBody(not(containing("context_mode"))));
  }

  @Test
  void testContextModeStrippedFromColumnWiseRequest(final WireMockRuntimeInfo server) {
    stubFor(post(urlEqualTo("/predict")).willReturn(aResponse().withStatus(200).withBody("{}")));
    final var oldModelClient =
        RptClient.forDestination(
            DefaultHttpDestination.builder(server.getHttpBaseUrl()).build(), false);

    val config =
        PredictionConfig.create()
            .targetColumns(TARGET_COLUMN)
            .contextMode(PredictionConfig.ContextModeEnum.DEFAULT);
    val request =
        PredictRequestPayloadOneOf1.create()
            .predictionConfig(config)
            .columns(Map.of())
            .indexColumn("ID")
            .dataSchema(DATA_SCHEMA)
            .parseDataTypes(true);

    assertThat(config.getContextMode()).isEqualTo(PredictionConfig.ContextModeEnum.DEFAULT);
    oldModelClient.tableCompletion(request);
    assertThat(config.getContextMode()).isNull();
    verify(
        postRequestedFor(urlEqualTo("/predict")).withRequestBody(not(containing("context_mode"))));
  }

  @Test
  void testOldModelStripsContextModeFromParquetRequest(final WireMockRuntimeInfo server) {
    stubFor(
        post(urlEqualTo("/predict_parquet"))
            .willReturn(aResponse().withStatus(200).withBody("{}")));
    final var oldModelClient =
        RptClient.forDestination(
            DefaultHttpDestination.builder(server.getHttpBaseUrl()).build(), false);

    val parquetFile = Path.of("src/test/resources/rpt/test-data.parquet").toFile();
    val predictionConfig =
        PredictionConfig.create()
            .targetColumns(TARGET_COLUMN)
            .contextMode(PredictionConfig.ContextModeEnum.DEEP);

    assertThat(predictionConfig.getContextMode()).isEqualTo(PredictionConfig.ContextModeEnum.DEEP);
    oldModelClient.tableCompletion(parquetFile, predictionConfig);
    assertThat(predictionConfig.getContextMode()).isNull();
    verify(
        postRequestedFor(urlEqualTo("/predict_parquet"))
            .withRequestBody(not(containing("context_mode"))));
  }
}
