package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PolymorphicFallbackDeserializerTest {

  private static String candidateAJson;
  private static String candidateBJson;

  @BeforeEach
  void setUp() {
    candidateAJson =
        """
        {
          "content": "I am candidate A"
        }
        """;

    candidateBJson =
        """
        {
          "content": [
            "I am candidate B"
          ]
        }
        """;
  }

  @SneakyThrows
  @Test
  void testValueTypesResolutionSuccess() {

    final var fullCandidateList = List.of(CandidateA.class, CandidateB.class);
    final var module =
        new SimpleModule()
            .addDeserializer(
                AbstractParent.class,
                PolymorphicFallbackDeserializer.fromCandidates(
                    AbstractParent.class, fullCandidateList));

    final var mapper = new JsonMapper().registerModule(module);

    final var candidateA = mapper.readValue(candidateAJson, AbstractParent.class);
    assertThat(candidateA).isInstanceOf(CandidateA.class);

    final var candidateB = mapper.readValue(candidateBJson, AbstractParent.class);
    assertThat(candidateB).isInstanceOf(CandidateB.class);
  }

  @SneakyThrows
  @Test
  void testValueTypeResolutionFailure() {

    final List<Class<? extends AbstractParent>> incompleteCandidateList = List.of(CandidateA.class);

    final var moduleWithoutCandidateB =
        new SimpleModule()
            .addDeserializer(
                AbstractParent.class,
                PolymorphicFallbackDeserializer.fromCandidates(
                    AbstractParent.class, incompleteCandidateList));

    final var mapper = new JsonMapper().registerModule(moduleWithoutCandidateB);

    assertThatThrownBy(() -> mapper.readValue(candidateBJson, AbstractParent.class))
        .isInstanceOf(JsonMappingException.class)
        .hasMessageContaining(
            "PolymorphicFallbackDeserializer failed to deserialize "
                + AbstractParent.class.getName()
                + ". Attempted candidates: "
                + List.of(CandidateA.class.getName()));
  }

  @SneakyThrows
  @Test
  void testSubtypeAnnotationSuccess() {

    final var module =
        new SimpleModule()
            .addDeserializer(
                AbstractParent.class,
                PolymorphicFallbackDeserializer.fromJsonSubTypes(AbstractParent.class));

    ObjectMapper mapper = new ObjectMapper().registerModule(module);

    final var candidate = mapper.readValue(candidateAJson, AbstractParent.class);
    assertThat(candidate).isInstanceOf(CandidateA.class);
  }

  @JsonSubTypes({
    @JsonSubTypes.Type(value = CandidateA.class),
    @JsonSubTypes.Type(value = CandidateB.class)
  })
  interface AbstractParent {}

  static class CandidateA implements AbstractParent {
    @JsonProperty("content")
    String content;
  }

  static class CandidateB implements AbstractParent {
    @JsonProperty("content")
    List<String> content;
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
