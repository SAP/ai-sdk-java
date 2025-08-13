package com.sap.ai.sdk.core.common;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import java.io.IOException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.StandardException;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpResponse;
import org.junit.jupiter.api.Test;

class ClientResponseHandlerTest {

  static class MyResponse {}

  @Data
  static class MyError implements ClientError {
    @JsonProperty("message")
    private String message;
  }

  @StandardException
  static class MyException extends ClientException {}

  static class MyExceptionFactory implements ClientExceptionFactory<MyException, MyError> {
    @Nonnull
    @Override
    public MyException build(
        @Nonnull final String message,
        @Nullable final MyError clientError,
        @Nullable final Throwable cause) {
      return new MyException(message, cause).setClientError(clientError);
    }
  }

  @SneakyThrows
  @Test
  void testBuildExceptionAndThrow() {
    var sut =
        new ClientResponseHandler<>(MyResponse.class, MyError.class, new MyExceptionFactory());

    HttpEntity entityWithNetworkIssues = spy(new StringEntity(""));
    doThrow(new IOException("Network issues")).when(entityWithNetworkIssues).writeTo(any());
    doThrow(new IOException("Network issues")).when(entityWithNetworkIssues).getContent();

    var response = spy(new BasicClassicHttpResponse(400, "Bad Request"));
    when(response.getEntity())
        .thenReturn(null)
        .thenReturn(entityWithNetworkIssues)
        .thenReturn(new StringEntity("", ContentType.APPLICATION_JSON))
        .thenReturn(new StringEntity("<html>oh", ContentType.TEXT_HTML))
        .thenReturn(new StringEntity("{\"message\":\"foobar\"}", ContentType.APPLICATION_JSON))
        .thenReturn(new StringEntity("{\"message\"-\"foobar\"}", ContentType.APPLICATION_JSON))
        .thenReturn(new StringEntity("{\"foo\":\"bar\"}", ContentType.APPLICATION_JSON))
        .thenReturn(new StringEntity("<message>foobar</message>", ContentType.APPLICATION_JSON));

    assertThatThrownBy(() -> sut.handleResponse(response))
        .isInstanceOf(MyException.class)
        .hasMessage("Request failed with status 400 (Bad Request): The HTTP Response is empty")
        .hasNoCause()
        .extracting(e -> ((MyException) e).getClientError())
        .isNull();
    assertThatThrownBy(() -> sut.handleResponse(response))
        .isInstanceOf(MyException.class)
        .hasMessage(
            "Request failed with status 400 (Bad Request): Failed to read the response content")
        .extracting(e -> e.getSuppressed()[0])
        .isInstanceOf(IOException.class)
        .extracting(Throwable::getMessage)
        .isEqualTo("Network issues");
    assertThatThrownBy(() -> sut.handleResponse(response))
        .isInstanceOf(MyException.class)
        .hasMessage("Request failed with status 400 (Bad Request): Empty or blank response content")
        .hasNoCause()
        .extracting(e -> ((MyException) e).getClientError())
        .isNull();
    assertThatThrownBy(() -> sut.handleResponse(response))
        .isInstanceOf(MyException.class)
        .hasMessage(
            "Request failed with status 400 (Bad Request): The response Content-Type is not JSON")
        .hasNoCause()
        .extracting(e -> ((MyException) e).getClientError())
        .isNull();
    assertThatThrownBy(() -> sut.handleResponse(response))
        .isInstanceOf(MyException.class)
        .hasMessage("Request failed with status 400 (Bad Request): foobar")
        .hasNoCause()
        .extracting(e -> ((MyException) e).getClientError())
        .isNotNull();
    assertThatThrownBy(() -> sut.handleResponse(response))
        .isInstanceOf(MyException.class)
        .hasMessage(
            "Request failed with status 400 (Bad Request): Failed to parse the JSON error response")
        .hasNoCause()
        .extracting(e -> e.getSuppressed()[0])
        .isInstanceOf(JsonParseException.class);
    assertThatThrownBy(() -> sut.handleResponse(response))
        .isInstanceOf(MyException.class)
        .hasMessage("Request failed with status 400 (Bad Request)")
        .hasNoCause()
        .extracting(e -> ((MyException) e).getClientError())
        .isNotNull();
    assertThatThrownBy(() -> sut.handleResponse(response))
        .isInstanceOf(MyException.class)
        .hasMessage(
            "Request failed with status 400 (Bad Request): Failed to parse the JSON error response")
        .hasNoCause()
        .extracting(e -> e.getSuppressed()[0])
        .isInstanceOf(JsonParseException.class);
  }
}
