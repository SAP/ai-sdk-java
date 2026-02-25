package com.sap.ai.sdk.foundationmodels.rpt;

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
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
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

  @BeforeEach
  void setup(final WireMockRuntimeInfo server) {
    final DefaultHttpDestination destination =
        DefaultHttpDestination.builder(server.getHttpBaseUrl()).build();
    client = RptClient.forDestination(destination);
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

  static PredictRequestPayload createBaseRequest() {
    final var dataSchema =
        Map.of(
            "PRODUCT", SchemaFieldConfig.create().dtype(ColumnType.STRING),
            "PRICE", SchemaFieldConfig.create().dtype(ColumnType.STRING),
            "ORDERDATE", SchemaFieldConfig.create().dtype(ColumnType.DATE),
            "ID", SchemaFieldConfig.create().dtype(ColumnType.STRING),
            "COSTCENTER", SchemaFieldConfig.create().dtype(ColumnType.STRING));
    final var targetColumns =
        List.of(
            TargetColumnConfig.create()
                .name("COSTCENTER")
                .predictionPlaceholder(PredictionPlaceholder.create("[PREDICT]"))
                .taskType(TargetColumnConfig.TaskTypeEnum.CLASSIFICATION));

    return PredictRequestPayload.create()
        .predictionConfig(PredictionConfig.create().targetColumns(targetColumns))
        .indexColumn("ID")
        .dataSchema(dataSchema)
        .parseDataTypes(true);
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
    final var request = createBaseRequest().rows(rows);

    final var response = client.tableCompletion(request);
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
    final var request = createBaseRequest().columns(columns);

    final var response = client.tableCompletion(request);

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
  void generateTestParquetFile() throws IOException {
    final var schemaString =
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
    final Path outputPath =
        Path.of(getClass().getResource("/").toURI()).resolve("test-data.parquet");

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

  @SneakyThrows
  @Test
  void testTableCompletionWithParquetFile() throws IOException {
    final var resourceStream =
        Path.of(getClass().getResource("/test-data.parquet").toURI()).toFile();
    assertThat(resourceStream).isNotNull();

    final var targetConfig =
        TargetColumnConfig.create()
            .name("COSTCENTER")
            .predictionPlaceholder(PredictionPlaceholder.create("[PREDICT]"))
            .taskType(TargetColumnConfig.TaskTypeEnum.CLASSIFICATION);
    final var predictionConfig = PredictionConfig.create().targetColumns(List.of(targetConfig));

    final var response = client.tableCompletion(resourceStream, predictionConfig);

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
  void testTableCompletionWithParquetThrowsIllegalArgumentException() throws IOException {
    final var predictionConfig = mock(PredictionConfig.class);
    final var objectMapper = mock(JsonMapper.class);

    try (final var mockedStatic = mockStatic(JacksonConfiguration.class)) {
      mockedStatic.when(JacksonConfiguration::getDefaultObjectMapper).thenReturn(objectMapper);

      when(objectMapper.writeValueAsString(any()))
          .thenThrow(new JsonProcessingException("Test") {});
      assertThatThrownBy(() -> client.tableCompletion(mock(File.class), predictionConfig))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Failed to serialize PredictionConfig");
    }
  }
}
