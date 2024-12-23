package com.sap.ai.sdk.core.commons;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
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

  @Test
  public void testParseErrorAndThrow() {
    var sut = new ClientResponseHandler<>(MyResponse.class, MyError.class, MyException::new);

    MyException cause = new MyException("Something wrong");

    assertThatThrownBy(() -> sut.parseErrorAndThrow("{\"message\":\"foobar\"}", cause))
        .isInstanceOf(MyException.class)
        .hasMessage("Something wrong and error message: 'foobar'")
        .hasCause(cause);

    assertThatThrownBy(() -> sut.parseErrorAndThrow("{\"foo\":\"bar\"}", cause))
        .isInstanceOf(MyException.class)
        .hasMessage("Something wrong and error message: ''")
        .hasCause(cause);

    assertThatThrownBy(() -> sut.parseErrorAndThrow("<message>foobar</message>", cause))
        .isInstanceOf(MyException.class)
        .hasMessage("Something wrong")
        .hasNoCause();
  }

  @SneakyThrows
  @Test
  public void testBuildExceptionAndThrow() {
    var sut = new ClientResponseHandler<>(MyResponse.class, MyError.class, MyException::new);

    HttpEntity entityWithNetworkIssues = spy(new StringEntity(""));
    doThrow(new IOException("Network issues")).when(entityWithNetworkIssues).writeTo(any());
    doThrow(new IOException("Network issues")).when(entityWithNetworkIssues).getContent();

    var response = spy(new BasicClassicHttpResponse(400, "Bad Request"));
    when(response.getEntity())
        .thenReturn(null)
        .thenReturn(entityWithNetworkIssues)
        .thenReturn(new StringEntity("", ContentType.APPLICATION_JSON))
        .thenReturn(new StringEntity("<html>oh", ContentType.TEXT_HTML))
        .thenReturn(new StringEntity("{\"message\":\"foobar\"}", ContentType.APPLICATION_JSON));

    assertThatThrownBy(() -> sut.buildExceptionAndThrow(response))
        .isInstanceOf(MyException.class)
        .hasMessage("Request failed with status 400 Bad Request")
        .hasNoCause();
    assertThatThrownBy(() -> sut.buildExceptionAndThrow(response))
        .isInstanceOf(MyException.class)
        .hasMessage("Request failed with status 400 Bad Request")
        .hasNoCause();
    assertThatThrownBy(() -> sut.buildExceptionAndThrow(response))
        .isInstanceOf(MyException.class)
        .hasMessage("Request failed with status 400 Bad Request")
        .hasNoCause();
    assertThatThrownBy(() -> sut.buildExceptionAndThrow(response))
        .isInstanceOf(MyException.class)
        .hasMessage("Request failed with status 400 Bad Request")
        .hasNoCause();
    assertThatThrownBy(() -> sut.buildExceptionAndThrow(response))
        .isInstanceOf(MyException.class)
        .hasMessage("Request failed with status 400 Bad Request and error message: 'foobar'")
        .hasCause(new MyException("Request failed with status 400 Bad Request"));
  }
}
