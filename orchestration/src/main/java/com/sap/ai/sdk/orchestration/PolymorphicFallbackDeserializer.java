package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.annotations.Beta;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * Handles polymorphic deserialization for a base class or interface.
 *
 * <p>This deserializer uses a fallback strategy by attempting to deserialize the JSON into known
 * subtypes. Subtypes are either discovered using the {@link JsonSubTypes} annotation or provided
 * explicitly. If deserialization fails for all candidates, a {@link JsonMappingException} is thrown
 * with suppressed exceptions.
 *
 * @since 1.2.0
 * @param <T> The base type for deserialization.
 */
@Beta
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PolymorphicFallbackDeserializer<T> extends JsonDeserializer<T> {

  @Nonnull private final Class<T> baseClass;
  @Nonnull private final List<Class<? extends T>> candidates;

  /**
   * Constructs the deserializer using candidates inferred from the {@link JsonSubTypes} annotation.
   *
   * @param baseClass The base class or interface to be resolved.
   * @throws IllegalStateException If no subtypes are found.
   */
  @Nonnull
  protected static <T> PolymorphicFallbackDeserializer<T> fromJsonSubTypes(
      @Nonnull final Class<T> baseClass) {
    final var subTypes = baseClass.getAnnotation(JsonSubTypes.class);
    if (subTypes == null || subTypes.value().length == 0) {
      throw new IllegalStateException("No subtypes found for " + baseClass.getName());
    }

    final var candidates = new ArrayList<Class<? extends T>>();
    for (final var subType : subTypes.value()) {
      candidates.add((Class<? extends T>) subType.value());
    }

    return PolymorphicFallbackDeserializer.fromCandidates(baseClass, candidates);
  }

  /**
   * Constructs the deserializer with an explicit given list of candidate types.
   *
   * @param baseClass The base class or interface to be resolved.
   * @param candidates A list of candidate classes to try deserialization.
   */
  @Nonnull
  protected static <T> PolymorphicFallbackDeserializer<T> fromCandidates(
      @Nonnull final Class<T> baseClass, @Nonnull final List<Class<? extends T>> candidates) {
    return new PolymorphicFallbackDeserializer<>(baseClass, candidates);
  }

  /**
   * Deserializes the JSON into the first matching candidate type.
   *
   * @param jsonParser The parser providing the JSON.
   * @param deserializationContext The deserialization context.
   * @return The deserialized object of a matching candidate type.
   * @throws JsonMappingException If deserialization fails for all candidates.
   * @throws IOException If json content cannot be consumed.
   */
  @Nonnull
  @Override
  public T deserialize(
      @Nonnull final JsonParser jsonParser,
      @Nonnull final DeserializationContext deserializationContext)
      throws IOException {

    final var root = jsonParser.readValueAsTree();
    final var suppressed = new ArrayList<JsonMappingException>();

    for (final var candidate : candidates) {
      try {
        return jsonParser.getCodec().treeToValue(root, candidate);
      } catch (JsonMappingException e) {
        suppressed.add(e);
      }
    }

    final var aggregateException =
        JsonMappingException.from(
            jsonParser,
            "PolymorphicFallbackDeserializer failed to deserialize "
                + this.baseClass.getName()
                + ". Attempted candidates: "
                + candidates.stream().map(Class::getName).toList());

    suppressed.forEach(aggregateException::addSuppressed);
    throw aggregateException;
  }
}
