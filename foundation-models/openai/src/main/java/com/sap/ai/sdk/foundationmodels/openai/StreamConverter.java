package com.sap.ai.sdk.foundationmodels.openai;

import static java.nio.charset.StandardCharsets.UTF_8;

import io.vavr.control.Try;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
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

  @FunctionalInterface
  private interface ReadHandler<T> {
    /**
     * Read next entry for Stream.
     *
     * @return The next entry, or {@code null} when no further entry can be read.
     * @throws IOException if no entry can be read anymore, unexpected.
     */
    @Nullable
    T readEntry() throws IOException;
  }

  @FunctionalInterface
  private interface CloseHandler {
    /** Close handler to be called when Stream terminated. */
    void close();
  }

  @RequiredArgsConstructor
  private static class HandledIterator<T> implements Iterator<T> {
    private final ReadHandler<T> readHandler;
    private final CloseHandler stopHandler;
    private boolean done = false;
    private T next = null;

    @Override
    public boolean hasNext() {
      if (done) {
        return false;
      }
      try {
        next = readHandler.readEntry();
        if (next == null) {
          done = true;
          stopHandler.close();
        }
      } catch (IOException e) {
        done = true;
        stopHandler.close();
        throw new UncheckedIOException("Iterator stopped unexpectedly.", e);
      }
      return !done;
    }

    @Override
    public T next() {
      if (next == null && !hasNext()) {
        throw new NoSuchElementException();
      }
      return next;
    }
  }

  @SuppressWarnings("PMD.CloseResource")
  @Nonnull
  static Stream<String> streamLines(@Nullable final HttpEntity entity)
      throws OpenAiClientException {
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
    final CloseHandler closeHandler =
        () ->
            Try.run(reader::close)
                .onFailure(e -> log.error("Could not close HTTP input stream", e));

    final var iterator = new HandledIterator<>(reader::readLine, closeHandler);
    final var spliterator = Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED);
    return StreamSupport.stream(spliterator, false).onClose(closeHandler::close);
  }
}
