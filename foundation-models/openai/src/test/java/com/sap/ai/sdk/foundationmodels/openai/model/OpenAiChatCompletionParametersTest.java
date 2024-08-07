package com.sap.ai.sdk.foundationmodels.openai.model;

import static com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionParameters.ResponseFormat.JSON_OBJECT;
import static com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionTool.ToolType.FUNCTION;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class OpenAiChatCompletionParametersTest {
  private static final ObjectMapper OBJECT_MAPPER =
      new Jackson2ObjectMapperBuilder()
          .modules(new JavaTimeModule())
          .visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
          .visibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
          .serializationInclusion(JsonInclude.Include.NON_NULL) // THIS STOPS `null` serialization
          .build();

  @SneakyThrows
  @Test
  public void testCompletionParametersStop() {
    var pNone = new OpenAiChatCompletionParameters().setStop();
    var pNull = new OpenAiChatCompletionParameters().setStop((String[]) null);
    var p1 = new OpenAiChatCompletionParameters().setStop("<|endoftext|>");
    var p2 = new OpenAiChatCompletionParameters().setStop("<|endoftext|>", "<|BYE|>");

    // equality check
    assertThat(pNone).isEqualTo(pNull);
    assertThat(pNone).isNotEqualTo(p1);
    assertThat(p1).isNotEqualTo(p2);

    // serialization check
    assertThat(OBJECT_MAPPER.writeValueAsString(pNone)).isEqualTo("{}");
    assertThat(OBJECT_MAPPER.writeValueAsString(pNull)).isEqualTo("{}");
    assertThat(OBJECT_MAPPER.writeValueAsString(p1)).isEqualTo("{\"stop\":[\"<|endoftext|>\"]}");
    assertThat(OBJECT_MAPPER.writeValueAsString(p2))
        .isEqualTo("{\"stop\":[\"<|endoftext|>\",\"<|BYE|>\"]}");

    // deserialization check
    var js = "{}";
    assertThat(OBJECT_MAPPER.readValue(js, OpenAiChatCompletionParameters.class)).isEqualTo(pNone);

    js = "{\"stop\":\"<|endoftext|>\"}";
    assertThat(OBJECT_MAPPER.readValue(js, OpenAiChatCompletionParameters.class)).isEqualTo(p1);

    js = "{\"stop\":[\"<|endoftext|>\"]}";
    assertThat(OBJECT_MAPPER.readValue(js, OpenAiChatCompletionParameters.class)).isEqualTo(p1);

    js = "{\"stop\":[\"<|endoftext|>\",\"<|BYE|>\"]}";
    assertThat(OBJECT_MAPPER.readValue(js, OpenAiChatCompletionParameters.class)).isEqualTo(p2);
  }

  @SneakyThrows
  @Test
  public void testCompletionParametersToolChoice() {
    var c = new OpenAiChatCompletionParameters();
    var cAuto = new OpenAiChatCompletionParameters().setToolChoiceAuto();
    var cNone = new OpenAiChatCompletionParameters().setToolChoiceNone();
    var cFib = new OpenAiChatCompletionParameters().setToolChoiceFunction("fibonacci");

    // equality check
    assertThat(c).isNotEqualTo(cAuto);
    assertThat(cAuto).isNotEqualTo(cNone);
    assertThat(cNone).isNotEqualTo(cFib);

    // serialization check
    assertThat(OBJECT_MAPPER.writeValueAsString(c)).isEqualTo("{}");
    assertThat(OBJECT_MAPPER.writeValueAsString(cAuto)).isEqualTo("{\"tool_choice\":\"auto\"}");
    assertThat(OBJECT_MAPPER.writeValueAsString(cNone)).isEqualTo("{\"tool_choice\":\"none\"}");
    assertThat(OBJECT_MAPPER.writeValueAsString(cFib))
        .isEqualTo(
            "{\"tool_choice\":{\"function\":{\"name\":\"fibonacci\"},\"type\":\"function\"}}");

    // deserialization check
    var js = "{}";
    assertThat(OBJECT_MAPPER.readValue(js, OpenAiChatCompletionParameters.class)).isEqualTo(c);

    js = "{\"tool_choice\":\"auto\"}";
    assertThat(OBJECT_MAPPER.readValue(js, OpenAiChatCompletionParameters.class)).isEqualTo(cAuto);

    js = "{\"tool_choice\":\"none\"}";
    assertThat(OBJECT_MAPPER.readValue(js, OpenAiChatCompletionParameters.class)).isEqualTo(cNone);

    js = "{\"tool_choice\":{\"function\":{\"name\":\"fibonacci\"},\"type\":\"function\"}}";
    assertThat(OBJECT_MAPPER.readValue(js, OpenAiChatCompletionParameters.class)).isEqualTo(cFib);
  }

  @SneakyThrows
  @Test
  public void testCompletionParametersTools() {
    var funcParams = Map.of("type", "object", "properties", Map.of("N", Map.of("type", "integer")));
    var func = new OpenAiChatCompletionFunction().setName("fibonacci").setParameters(funcParams);

    var noTools = new OpenAiChatCompletionParameters();
    var withTools =
        new OpenAiChatCompletionParameters()
            .setTools(List.of(new OpenAiChatCompletionTool().setType(FUNCTION).setFunction(func)));

    // equality check
    assertThat(noTools).isNotEqualTo(withTools);

    // serialization check
    String serializedActual = OBJECT_MAPPER.writeValueAsString(withTools);
    String serializedExpected =
        "{\"tools\":[{\"type\":\"function\",\"function\":{\"name\":\"fibonacci\",\"parameters\":{\"properties\":{\"N\":{\"type\":\"integer\"}},\"type\":\"object\"}}}]}";

    assertThat(OBJECT_MAPPER.writeValueAsString(noTools)).isEqualTo("{}");
    assertThat(OBJECT_MAPPER.readTree(serializedActual))
        .isEqualTo(
            OBJECT_MAPPER.readTree(
                serializedExpected)); // not directly comparing strings, due to nondeterministic
    // order of keys

    // deserialization check
    assertThat(OBJECT_MAPPER.readValue("{}", OpenAiChatCompletionParameters.class))
        .isEqualTo(noTools);
    assertThat(OBJECT_MAPPER.readValue(serializedExpected, OpenAiChatCompletionParameters.class))
        .isEqualTo(withTools);
  }

  @SneakyThrows
  @Test
  public void testCompletionParameters() {
    var params =
        new OpenAiChatCompletionParameters()
            .setMessages(List.of(new OpenAiChatMessage.OpenAiChatUserMessage().addText("foo")))
            .setResponseFormat(JSON_OBJECT);

    // serialization check
    var serializedActual = OBJECT_MAPPER.writeValueAsString(params);
    var serializedExpected =
        """
                        {"messages":[{"role":"user","content":[{"type":"text","text":"foo"}]}],"response_format":{"type":"json_object"}}""";

    assertThat(serializedActual).isEqualTo(serializedExpected);

    // deserialization check
    assertThat(OBJECT_MAPPER.readValue(serializedExpected, OpenAiChatCompletionParameters.class))
        .isEqualTo(params);
  }
}
