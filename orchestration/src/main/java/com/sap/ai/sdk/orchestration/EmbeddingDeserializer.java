package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.Embedding;
import com.sap.ai.sdk.orchestration.model.EmbeddingMultiFormat;
import java.io.IOException;
import javax.annotation.Nonnull;

/**
 * Custom deserializer for the {@link Embedding} interface that resolves conflicts between multiple
 * {@code @JsonCreator} annotations by intelligently detecting the JSON structure and choosing the
 * appropriate implementation.
 */
@Beta
class EmbeddingDeserializer extends JsonDeserializer<Embedding> {

  /**
   * Deserializes JSON into the appropriate {@link Embedding} implementation based on the JSON
   * structure:
   *
   * <ul>
   *   <li>JSON array → {@link Embedding.ArrayOfFloats}
   *   <li>JSON string → {@link Embedding.InnerString}
   *   <li>JSON object → {@link Embedding.InnerEmbeddingMultiFormat}
   * </ul>
   *
   * @param jsonParser The parser providing the JSON.
   * @param deserializationContext The deserialization context.
   * @return The deserialized {@link Embedding} object.
   * @throws JsonMappingException If the JSON structure is not recognized or deserialization fails.
   * @throws IOException If JSON content cannot be consumed.
   */
  @Nonnull
  @Override
  public Embedding deserialize(
      @Nonnull final JsonParser jsonParser,
      @Nonnull final DeserializationContext deserializationContext)
      throws IOException {

    final var currentToken = jsonParser.getCurrentToken();

    try {
      return switch (currentToken) {
        case START_ARRAY -> {
          // JSON array - deserialize as float array
          final float[] floatArray = jsonParser.readValueAs(float[].class);
          yield new Embedding.ArrayOfFloats(floatArray);
        }
        case VALUE_STRING -> {
          // JSON string - deserialize as string
          final String stringValue = jsonParser.getValueAsString();
          yield new Embedding.InnerString(stringValue);
        }
        case START_OBJECT -> {
          // JSON object - deserialize as EmbeddingMultiFormat
          final EmbeddingMultiFormat multiFormat =
              jsonParser.readValueAs(EmbeddingMultiFormat.class);
          yield new Embedding.InnerEmbeddingMultiFormat(multiFormat);
        }
        default ->
            throw JsonMappingException.from(
                jsonParser,
                "EmbeddingDeserializer cannot deserialize JSON token type: "
                    + currentToken
                    + ". Expected START_ARRAY (for float[]), VALUE_STRING (for String), or START_OBJECT (for EmbeddingMultiFormat).");
      };
    } catch (IOException e) {
      throw JsonMappingException.from(
          jsonParser,
          "EmbeddingDeserializer failed to deserialize Embedding from JSON token: " + currentToken,
          e);
    }
  }
}
