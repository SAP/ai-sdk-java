package com.sap.ai.sdk.core.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.SneakyThrows;
import lombok.experimental.StandardException;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IterableStreamConverterTest {
  @SneakyThrows
  @Test
  @DisplayName("Stream is fully consumed")
  void testLines() {
    final var TEMPLATE = "THIS\nIS\nA\nTEST\n";
    final var input = TEMPLATE.repeat(IterableStreamConverter.BUFFER_SIZE);
    final var inputStream = spy(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    final var entity = new InputStreamEntity(inputStream, ContentType.TEXT_PLAIN);

    final var sut = IterableStreamConverter.lines(entity, TestClientException::new);
    verify(inputStream, never()).read();
    verify(inputStream, never()).read(any());
    verify(inputStream, never()).read(any(), anyInt(), anyInt());

    final var streamCounter = new AtomicInteger(0);
    sut.peek(s -> streamCounter.incrementAndGet())
        .forEach(
            s ->
                assertThat(s)
                    .containsAnyOf("THIS", "IS", "A", "TEST")
                    .doesNotContainAnyWhitespaces());

    assertThat(streamCounter).hasValue(IterableStreamConverter.BUFFER_SIZE * 4);
    verify(inputStream, times(TEMPLATE.length() + 1))
        .read(any(), anyInt(), eq(IterableStreamConverter.BUFFER_SIZE));
    verify(inputStream, times(1)).close();
  }

  @SneakyThrows
  @Test
  @DisplayName("Stream may only read first entry without closing")
  void testLinesFindFirst() {
    final var TEMPLATE = "Foo Bar\n";
    final var inputStream = mock(InputStream.class);
    when(inputStream.read(any(), anyInt(), anyInt()))
        .thenAnswer(
            arg -> {
              byte[] ar = arg.getArgument(0, byte[].class);
              byte[] bytes = TEMPLATE.getBytes(StandardCharsets.UTF_8);
              for (int i = 0; i < ar.length; i++) ar[i] = bytes[i % bytes.length];
              return ar.length;
            });

    final var entity = new InputStreamEntity(inputStream, ContentType.TEXT_PLAIN);

    final var sut = IterableStreamConverter.lines(entity, TestClientException::new);
    assertThat(sut.findFirst()).contains("Foo Bar");
    verify(inputStream, times(1)).read(any(), anyInt(), anyInt());
    verify(inputStream, never()).close();
  }

  @SneakyThrows
  @Test
  @DisplayName("Stream may close unexpectedly")
  void testLinesThrows() {
    final var TEMPLATE = "Foo Bar\n";
    final var inputStream = mock(InputStream.class);
    when(inputStream.read(any(), anyInt(), anyInt()))
        .thenAnswer(
            arg -> {
              byte[] ar = arg.getArgument(0, byte[].class);
              byte[] bytes = TEMPLATE.getBytes(StandardCharsets.UTF_8);
              for (int i = 0; i < ar.length; i++) ar[i] = bytes[i % bytes.length];
              return ar.length;
            })
        .thenThrow(new IOException("Ups!"));

    final var entity = new InputStreamEntity(inputStream, ContentType.TEXT_PLAIN);

    final var sut = IterableStreamConverter.lines(entity, TestClientException::new);
    assertThatThrownBy(sut::count)
        .isInstanceOf(TestClientException.class)
        .hasMessage("Parsing response content was interrupted.")
        .cause()
        .isInstanceOf(IOException.class)
        .hasMessage("Ups!");
    verify(inputStream, times(2)).read(any(), anyInt(), anyInt());
    verify(inputStream, times(1)).close();
  }

  @StandardException
  public static class TestClientException extends ClientException {}
}
