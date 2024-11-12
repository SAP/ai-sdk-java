package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResult;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResultStreaming;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResultSynchronous;
import java.io.IOException;
import javax.annotation.Nonnull;

/**
 * A deserializer for {@link LLMModuleResult} that determines the concrete implementation based on
 * the structure of the JSON object.
 */
public class LLMModuleResultDeserializer extends StdDeserializer<LLMModuleResult> {

  public LLMModuleResultDeserializer() {
    super(LLMModuleResult.class);
  }

  /**
   * Deserialize the JSON object into one of the subtypes of the base type.
   *
   * <ul>
   *   <li>If elements of "choices" array contains "delta", deserialize into {@link
   *       LLMModuleResultStreaming}.
   *   <li>Otherwise, deserialize into {@link LLMModuleResultSynchronous}.
   * </ul>
   *
   * @param parser The JSON parser.
   * @param context The deserialization context.
   * @return The deserialized object.
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public LLMModuleResult deserialize(JsonParser parser, @Nonnull DeserializationContext context)
      throws IOException {

    // Check if the target type is a concrete class
    JavaType targetType = context.getContextualType();
    if (targetType != null && !LLMModuleResult.class.equals(targetType.getRawClass())) {
      return delegateToDefaultDeserializer(parser, context, targetType);
    }

    // Custom deserialization logic for LLMModuleResult interface
    var mapper = (ObjectMapper) parser.getCodec();
    var rootNode = mapper.readTree(parser);
    Class<? extends LLMModuleResult> concreteClass = LLMModuleResultSynchronous.class;

    // Inspect the "choices" field
    var choicesNode = rootNode.get("choices");
    if (choicesNode != null && choicesNode.isArray()) {
      var firstChoice = (JsonNode) choicesNode.get(0);
      if (firstChoice != null && firstChoice.has("delta")) {
        concreteClass = LLMModuleResultStreaming.class;
      }
    }

    // Create a new parser for the root node
    var rootParser = rootNode.traverse(mapper);
    rootParser.nextToken(); // Advance to the first token

    // Use the default deserializer for the concrete class
    return delegateToDefaultDeserializer(rootParser, context, mapper.constructType(concreteClass));
  }

  /**
   * Delegate deserialization to the default deserializer for the given concrete type.
   *
   * @param parser The JSON parser.
   * @param context The deserialization context.
   * @param concreteType The concrete type to deserialize into.
   * @return The deserialized object.
   * @throws IOException If an I/O error occurs.
   */
  private LLMModuleResult delegateToDefaultDeserializer(
      JsonParser parser, DeserializationContext context, JavaType concreteType) throws IOException {
    var defaultDeserializer = context.findRootValueDeserializer(concreteType);
    return (LLMModuleResult) defaultDeserializer.deserialize(parser, context);
  }
}
