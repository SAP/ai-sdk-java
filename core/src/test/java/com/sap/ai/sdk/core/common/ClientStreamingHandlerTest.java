package com.sap.ai.sdk.core.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ClientStreamingHandler}. Inherits common test utilities and mock classes from
 * ClientResponseHandlerTest.
 */
class ClientStreamingHandlerTest extends ClientResponseHandlerTest {

  @Data
  static class MyStreamedDelta implements StreamedDelta {
    @JsonProperty("value")
    private String value; // Simulates the content

    @JsonProperty("finish_reason")
    private String finishReason;

    @Nonnull
    @Override
    public String getDeltaContent() {
      return value != null ? value : "";
    }

    @Nullable
    @Override
    public String getFinishReason() {
      return finishReason;
    }

    @Override
    public boolean isError() {
      return false;
    }
  }

  @SneakyThrows
  @Test
  void testHandleStreamingResponse() {
    var sut =
        new ClientStreamingHandler<>(
            MyStreamedDelta.class, MyError.class, new MyExceptionFactory());

    final String validStreamContent =
        """
        data: {"value":"delta1"}

        data: {"value":"delta2", "finish_reason": "length"}
        data: [DONE]
        """;

    final String emptyStreamContent =
        """
        data: [DONE]

        """;

    final String malformedLineContent =
        """
        data: {"value":"deltaA"}
        malformed line here
        data: {"value":"deltaB"}
        data: [DONE]
        """;

    final String invalidJsonContent =
        """
        data: {"value":"deltaX"}
        data: {"value"-"deltaY"}
        data: [DONE]
        """;

    var response = spy(new BasicClassicHttpResponse(200, "OK"));
    when(response.getEntity()).thenReturn(new StringEntity(validStreamContent));

    var stream1 = sut.handleStreamingResponse(response);
    var deltas1 = stream1.toList();
    assertThat(deltas1).hasSize(2);
    assertThat(deltas1.get(0).getDeltaContent()).isEqualTo("delta1");
    assertThat(deltas1.get(0).getFinishReason()).isNull();
    assertThat(deltas1.get(1).getDeltaContent()).isEqualTo("delta2");
    assertThat(deltas1.get(1).getFinishReason()).isEqualTo("length");

    when(response.getEntity()).thenReturn(new StringEntity(emptyStreamContent));
    var stream2 = sut.handleStreamingResponse(response);
    assertThat(stream2).isEmpty();

    when(response.getEntity()).thenReturn(new StringEntity(malformedLineContent));
    var stream3 = sut.handleStreamingResponse(response);
    assertThatThrownBy(stream3::toList)
        .isInstanceOf(MyException.class)
        .hasMessageContaining("Failed to parse response");

    when(response.getEntity()).thenReturn(new StringEntity(invalidJsonContent));
    var stream4 = sut.handleStreamingResponse(response);
    assertThatThrownBy(stream4::toList)
        .isInstanceOf(MyException.class)
        .hasMessageContaining("Failed to parse delta chunk")
        .hasCauseInstanceOf(JsonParseException.class);
  }
}
