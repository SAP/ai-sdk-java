package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResult;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResultSynchronous;
import java.io.IOException;
import java.io.Serial;
import javax.annotation.Nonnull;
import lombok.val;

/**
 * A deserializer for {@link LLMModuleResult} that determines the concrete implementation based on
 * the structure of the JSON object.
 */
class LLMModuleResultDeserializer extends StdDeserializer<LLMModuleResult> {
  // checkstyle requires a serialVersionUid since StdDeserializer implements Serializable
  @Serial private static final long serialVersionUID = 1L;

  /** Default constructor. */
  LLMModuleResultDeserializer() {
    super(LLMModuleResult.class);
  }

  /**
   * Always deserialize into {@link LLMModuleResultSynchronous} since streaming isn't supported yet.
   *
   * @param parser The JSON parser.
   * @param context The deserialization context.
   * @return The deserialized object.
   * @throws IOException If an I/O error occurs.
   */
  @Nonnull
  @Override
  public LLMModuleResult deserialize(
      @Nonnull final JsonParser parser, @Nonnull final DeserializationContext context)
      throws IOException {
    val mapper = (ObjectMapper) parser.getCodec();
    val rootNode = mapper.readTree(parser);

    return mapper.readValue(rootNode.toString(), LLMModuleResultSynchronous.class);
  }
}
