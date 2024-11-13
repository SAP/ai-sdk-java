package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResult;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResultStreaming;
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
  @Nonnull
  @Override
  public LLMModuleResult deserialize(
      @Nonnull final JsonParser parser, @Nonnull final DeserializationContext context)
      throws IOException {

    // Check if the target type is a concrete class
    val targetType = context.getContextualType();
    if (targetType != null && !LLMModuleResult.class.equals(targetType.getRawClass())) {
      return delegateToDefaultDeserializer(parser, context, targetType);
    }

    // Custom deserialization logic for LLMModuleResult interface
    val mapper = (ObjectMapper) parser.getCodec();
    val rootNode = mapper.readTree(parser);
    Class<? extends LLMModuleResult> concreteClass = LLMModuleResultSynchronous.class;

    // Inspect the "choices" field
    val choicesNode = rootNode.get("choices");
    if (choicesNode != null && choicesNode.isArray()) {
      val firstChoice = (JsonNode) choicesNode.get(0);
      if (firstChoice != null && firstChoice.has("delta")) {
        concreteClass = LLMModuleResultStreaming.class;
      }
    }

    // Create a new parser for the root node
    val rootParser = rootNode.traverse(mapper);
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
      @Nonnull final JsonParser parser,
      @Nonnull final DeserializationContext context,
      @Nonnull final JavaType concreteType)
      throws IOException {
    val defaultDeserializer = context.findRootValueDeserializer(concreteType);
    return (LLMModuleResult) defaultDeserializer.deserialize(parser, context);
  }
}
