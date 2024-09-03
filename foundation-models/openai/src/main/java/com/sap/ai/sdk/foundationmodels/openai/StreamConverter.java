package com.sap.ai.sdk.foundationmodels.openai;

import static java.nio.charset.StandardCharsets.UTF_8;

import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpEntity;

@Slf4j
class StreamConverter {

  @RequiredArgsConstructor
  private static class HandledIterator<T> implements Iterator<T> {
    private final CheckedFunction0<T> producer;
    private final Runnable stopper;
    private boolean done = false;
    private T next = null;

    @Override
    public boolean hasNext() {
      if (done) {
        return false;
      }
      try {
        next = producer.apply();
        if (next == null) {
          done = true;
          stopper.run();
        }
      } catch (Throwable e) {
        done = true;
        stopper.run();
        throw new RuntimeException(e);
      }
      return !done;
    }

    @Override
    public T next() {
      if (next == null && !hasNext()) {
        throw new IllegalStateException();
      }
      return next;
    }
  }

  @Nonnull
  static Stream<String> streamLines(@Nullable HttpEntity entity) throws OpenAiClientException {
    if (entity == null) {
      throw new OpenAiClientException("OpenAI response was empty.");
    }

    final InputStream inputStream;
    try {
      inputStream = entity.getContent();
    } catch (IOException e) {
      throw new OpenAiClientException("Failed to read response content.", e);
    }

    final var reader = new BufferedReader(new InputStreamReader(inputStream, UTF_8));
    final Runnable closeHandler =
        () ->
            Try.run(reader::close)
                .onFailure(e -> log.error("Could not close HTTP input stream", e));

    final var iterator = new HandledIterator<>(reader::readLine, closeHandler);
    final var spliterator = Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED);
    return StreamSupport.stream(spliterator, false).onClose(closeHandler);
  }
}
