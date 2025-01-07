package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles polymorphic deserialization for a base class or interface.
 *
 * <p>This deserializer attempts to deserialize JSON into a subtype of a base class or interface.
 * Subtypes are either discovered using the {@link JsonSubTypes} annotation or provided explicitly.
 * If deserialization fails for all candidates, a {@link JsonMappingException} is thrown with
 * suppressed exceptions.
 *
 * @param <T> The base type for deserialization.
 */
public class PolymorphicFallbackDeserializer<T> extends StdDeserializer<T> {

  private final List<Class<? extends T>> candidates;

  /**
   * Constructs the deserializer using the {@link JsonSubTypes} annotation.
   *
   * @param baseClass The base class or interface to be resolved.
   * @throws IllegalStateException If no subtypes are found.
   */
  protected PolymorphicFallbackDeserializer(Class<T> baseClass) {
    super(baseClass);

    final var subTypes = baseClass.getAnnotation(JsonSubTypes.class);
    if (subTypes == null || subTypes.value().length == 0) {
      throw new IllegalStateException("No subtypes found for " + baseClass.getName());
    }

    candidates = new ArrayList<>();
    for (final var subType : subTypes.value()) {
      candidates.add((Class<? extends T>) subType.value());
    }
  }

  /**
   * Constructs the deserializer with an explicit list of candidate types.
   *
   * @param baseClass The base class or interface to be resolved.
   * @param candidates A list of candidate classes to try deserialization.
   */
  protected PolymorphicFallbackDeserializer(
      Class<T> baseClass, List<Class<? extends T>> candidates) {
    super(baseClass);
    this.candidates = candidates;
  }

  /**
   * Deserializes the JSON into the first matching candidate type.
   *
   * @param jsonParser The parser providing the JSON.
   * @param deserializationContext The deserialization context.
   * @return The deserialized object of a matching candidate type.
   * @throws JsonMappingException If deserialization fails for all candidates.
   * @throws IOException If an I/O error occurs.
   */
  @Override
  public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {

    final var root = jsonParser.readValueAsTree();
    final var throwable =
        JsonMappingException.from(
            jsonParser,
            "PolymorphicFallbackDeserializer failed to deserialize " + handledType().getName());

    for (final var candidate : candidates) {
      try {
        return jsonParser.getCodec().treeToValue(root, candidate);
      } catch (JsonMappingException e) {
        throwable.addSuppressed(e);
      }
    }

    throw throwable;
  }
}
