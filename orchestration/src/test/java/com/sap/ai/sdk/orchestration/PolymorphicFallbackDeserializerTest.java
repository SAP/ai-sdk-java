package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

class PolymorphicFallbackDeserializerTest {

  @SneakyThrows
  @Test
  void testValueTypeResolutionFailure() {

    interface AbstractParent {}

    class KnownCandidate implements AbstractParent {
      String content;
    }

    class UnknownCandidate implements AbstractParent {
      List<String> content;
    }

    final String unknownCandidateJson =
        """
        {
          "content": [
            "Sentence one",
            "Sentence two"
          ]
        }
        """;

    final var module =
        new SimpleModule()
            .addDeserializer(
                AbstractParent.class,
                PolymorphicFallbackDeserializer.fromCandidates(
                    AbstractParent.class, List.of(KnownCandidate.class)));

    final var mapper = new JsonMapper().registerModule(module);

    assertThatThrownBy(() -> mapper.readValue(unknownCandidateJson, AbstractParent.class))
        .isInstanceOf(JsonMappingException.class)
        .hasMessageContaining(
            "PolymorphicFallbackDeserializer failed to deserialize "
                + AbstractParent.class.getName()
                + ". Attempted candidates: "
                + List.of(KnownCandidate.class.getName()));
  }

  @Test
  void testMissingSubtypeAnnotation() {
    interface NoJsonSubTypeAnnotationInterface {}

    assertThatThrownBy(
            () ->
                PolymorphicFallbackDeserializer.fromJsonSubTypes(
                    NoJsonSubTypeAnnotationInterface.class))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining(
            "No subtypes found for " + NoJsonSubTypeAnnotationInterface.class.getName());
  }
}
