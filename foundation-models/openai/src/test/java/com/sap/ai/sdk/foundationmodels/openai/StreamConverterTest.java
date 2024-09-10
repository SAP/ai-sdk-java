package com.sap.ai.sdk.foundationmodels.openai;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.SneakyThrows;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.InputStreamEntity;
import org.junit.jupiter.api.Test;

public class StreamConverterTest {
  @SneakyThrows
  @Test
  void testStreamLines() {
    final var TEMPLATE = "THIS\nIS\nA\nTEST\n";
    final var input = TEMPLATE.repeat(StreamConverter.BUFFER_SIZE);
    final var inputStream = spy(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    final var entity = new InputStreamEntity(inputStream, ContentType.TEXT_PLAIN);

    final var sut = StreamConverter.streamLines(entity);
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

    assertThat(streamCounter).hasValue(StreamConverter.BUFFER_SIZE * 4);
    verify(inputStream, times(TEMPLATE.length() + 1))
        .read(any(), anyInt(), eq(StreamConverter.BUFFER_SIZE));
  }
}
