package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessagesInner;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

class PolymorphicFallbackDeserializerTest {

  @SneakyThrows
  @Test
  void testValueTypeResolutionFailure() {

    final String multiChatMessageJson =
        """
        {
          "role": "user",
          "content": [
            {
              "type": "text",
              "text": "What’s in this image?"
            },
            {
              "type": "image_url",
              "image_url": {
                "url": "https://sample.page.org/an-image.jpg"
              }
            }
          ]
        }
        """;

    final var module =
        new SimpleModule()
            .addDeserializer(
                ChatMessagesInner.class,
                new PolymorphicFallbackDeserializer<>(
                    ChatMessagesInner.class, List.of(ChatMessage.class)))
            .setMixInAnnotation(ChatMessagesInner.class, JacksonMixins.NoneTypeInfoMixin.class);

    final var mapper = new JsonMapper().registerModule(module);

    assertThatThrownBy(() -> mapper.readValue(multiChatMessageJson, ChatMessagesInner.class))
        .isInstanceOf(JsonMappingException.class)
        .hasMessageContaining(
            "PolymorphicFallbackDeserializer failed to deserialize "
                + ChatMessagesInner.class.getName());
  }

  @Test
  void testMissingSubtypeAnnotation() {
    interface NoSubTypesInterface {}

    assertThatThrownBy(() -> new PolymorphicFallbackDeserializer<>(NoSubTypesInterface.class))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("No subtypes found for " + NoSubTypesInterface.class.getName());
  }
}
