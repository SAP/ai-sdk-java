package com.sap.ai.sdk.foundationmodels.openai.model;

import static com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatUserMessage;
import static com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatUserMessage.ContentPartText;
import static com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatUserMessage.ImageDetailLevel;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class OpenAiChatMessageTest {
  private static final ObjectMapper OBJECT_MAPPER =
      new Jackson2ObjectMapperBuilder()
          .modules(new JavaTimeModule())
          .visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
          .visibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
          .serializationInclusion(JsonInclude.Include.NON_NULL) // THIS STOPS `null` serialization
          .build();

  @SneakyThrows
  @Test
  public void testEquality() {
    var stringBased = new OpenAiChatUserMessage().addText("foo");
    var listBased = new OpenAiChatUserMessage().addContent(new ContentPartText("foo"));

    // equality check
    assertThat(stringBased).isEqualTo(listBased);
    assertThat(stringBased.getContent()).hasSize(1);
    assertThat(stringBased.getContent().get(0))
        .extracting(o -> ((ContentPartText) o).text)
        .isEqualTo("foo");

    // deserialization check
    assertThat(
            OBJECT_MAPPER.readValue(
                "{\"role\":\"user\",\"content\":\"foo\"}", OpenAiChatMessage.class))
        .isEqualTo(listBased)
        .isEqualTo(stringBased);
  }

  @SneakyThrows
  @Test
  public void testImageContent() {
    // image content
    var message = new OpenAiChatUserMessage().addImage("https://my.image", ImageDetailLevel.LOW);

    // serialization check
    var serialized = OBJECT_MAPPER.writeValueAsString(message);
    assertThat(serialized)
        .isEqualTo(
            "{\"role\":\"user\",\"content\":[{\"type\":\"image_url\",\"image_url\":{\"url\":\"https://my.image\",\"detail\":\"low\"}}]}");

    // deserialization check
    assertThat(OBJECT_MAPPER.readValue(serialized, OpenAiChatMessage.class)).isEqualTo(message);
  }

  @SneakyThrows
  @Test
  public void testTextContent() {
    var message = new OpenAiChatUserMessage().addText("foo");

    // serialization check
    var serialized = OBJECT_MAPPER.writeValueAsString(message);
    assertThat(serialized)
        .isEqualTo("{\"role\":\"user\",\"content\":[{\"type\":\"text\",\"text\":\"foo\"}]}");

    // deserialization check
    var deserialized1 = OBJECT_MAPPER.readValue(serialized, OpenAiChatMessage.class);
    var deserialized2 = OBJECT_MAPPER.readValue(serialized, OpenAiChatUserMessage.class);
    assertThat(deserialized2).isEqualTo(deserialized1).isEqualTo(message);
  }
}
