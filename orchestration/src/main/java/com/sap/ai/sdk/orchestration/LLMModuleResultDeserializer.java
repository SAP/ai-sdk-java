package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResult;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResultStreaming;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResultSynchronous;
import java.io.IOException;

public class LLMModuleResultDeserializer extends StdDeserializer<LLMModuleResult> {

  public LLMModuleResultDeserializer() {
    super(LLMModuleResult.class);
  }

  @Override
  public LLMModuleResult deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {

    // Check if the target type is a concrete class
    JavaType targetType = ctxt.getContextualType();

    if (targetType != null && !LLMModuleResult.class.equals(targetType.getRawClass())) {
      // If we're deserializing a concrete class, delegate to the default deserializer
      JsonDeserializer<Object> defaultDeserializer = ctxt.findRootValueDeserializer(targetType);
      return (LLMModuleResult) defaultDeserializer.deserialize(p, ctxt);
    }

    // Custom deserialization logic for LLMModuleResult interface
    ObjectMapper mapper = (ObjectMapper) p.getCodec();
    JsonNode root = mapper.readTree(p);

    // Inspect the "choices" field
    JsonNode choicesNode = root.get("choices");

    if (choicesNode != null && choicesNode.isArray()) {
      JsonNode firstChoice = choicesNode.get(0);
      if (firstChoice != null) {
        Class<? extends LLMModuleResult> concreteClass = null;
        if (firstChoice.has("delta")) {
          concreteClass = LLMModuleResultStreaming.class;
        } else if (firstChoice.has("message")) {
          concreteClass = LLMModuleResultSynchronous.class;
        }

        if (concreteClass != null) {
          // Deserialize into the determined concrete class
          // Create a new parser for the root node
          JsonParser rootParser = root.traverse(mapper);
          rootParser.nextToken(); // Advance to the first token

          // Use the default deserializer for the concrete class
          JsonDeserializer<?> deserializer =
              ctxt.findRootValueDeserializer(ctxt.constructType(concreteClass));

          return (LLMModuleResult) deserializer.deserialize(rootParser, ctxt);
        }
      }
    }

    // If unable to determine, throw an exception or handle default case
    throw new JsonMappingException(
        p, "Unable to determine the concrete implementation of LLMModuleResult");
  }
}
