package com.sap.ai.sdk.foundationmodels.openai;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Spliterator.NONNULL;
import static java.util.Spliterator.ORDERED;

import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpEntity;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class IterableStreamConverter<T> implements Iterator<T> {
  /** see {@link BufferedReader#DEFAULT_CHAR_BUFFER_SIZE} * */
  static final int BUFFER_SIZE = 8192;

  /** Read next entry for Stream or {@code null} when no further entry can be read. */
  private final CheckedFunction0<T> readHandler;

  /** Close handler to be called when Stream terminated. */
  private final Runnable stopHandler;

  private boolean done = false;
  private T next = null;

  @Override
  public boolean hasNext() {
    if (done) {
      return false;
    }
    try {
      next = readHandler.apply();
      if (next == null) {
        done = true;
        stopHandler.run();
      }
    } catch (final Throwable t) {
      done = true;
      stopHandler.run();
      final var e = t instanceof IOException e1 ? e1 : new IOException(t.getMessage(), t);
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

  @SuppressWarnings("PMD.CloseResource")
  @Nonnull
  static Stream<String> lines(@Nullable final HttpEntity entity) throws OpenAiClientException {
    if (entity == null) {
      throw new OpenAiClientException("OpenAI response was empty.");
    }

    final InputStream inputStream;
    try {
      inputStream = entity.getContent();
    } catch (final IOException e) {
      throw new OpenAiClientException("Failed to read response content.", e);
    }

    final var reader = new BufferedReader(new InputStreamReader(inputStream, UTF_8), BUFFER_SIZE);
    final Runnable closeHandler =
        () -> Try.run(reader::close).onFailure(e -> log.error("Could not close input stream", e));

    final var iterator = new IterableStreamConverter<>(reader::readLine, closeHandler);
    final var spliterator = Spliterators.spliteratorUnknownSize(iterator, ORDERED | NONNULL);
    return StreamSupport.stream(spliterator, /* NOT PARALLEL */ false).onClose(closeHandler);
  }
}
