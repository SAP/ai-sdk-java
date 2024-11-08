package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;

public class GeneralizedFallbackDeserializer<T> extends StdDeserializer<T> {

  private final Class<T> baseType;

  public GeneralizedFallbackDeserializer(Class<T> baseClass) {
    super(baseClass);
    this.baseType = baseClass;
  }

  @Override
  public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    ObjectMapper mapper = (ObjectMapper) p.getCodec();
    JsonNode rootNode = mapper.readTree(p);

    // Get the possible subtypes from @JsonSubTypes
    JsonSubTypes subTypes = baseType.getAnnotation(JsonSubTypes.class);
    if (subTypes == null || subTypes.value().length == 0) {
      throw new JsonMappingException(p, "No subtypes found for " + baseType.getName());
    }

    Exception lastException = null;
    for (JsonSubTypes.Type subType : subTypes.value()) {
      Class<? extends T> candidateClass = (Class<? extends T>) subType.value();

      try {
        // Attempt to deserialize into the candidate class
        // If successful, return the result
        return mapper.treeToValue(rootNode, candidateClass);
      } catch (Exception e) {
        // Save the exception and try the next candidate
        lastException = e;
      }
    }

    // If none succeeded, throw an exception with the last caught exception as the cause
    throw JsonMappingException.from(
        p, "Unable to deserialize " + baseType.getName(), lastException);
  }
}
