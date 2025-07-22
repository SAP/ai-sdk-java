package com.sap.ai.sdk.core.common;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Spliterator.NONNULL;
import static java.util.Spliterator.ORDERED;

import io.vavr.control.Try;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpEntity;

/**
 * Internal utility class to convert from a reading handler to {@link Iterable} and {@link Stream}.
 *
 * <p><strong>Note:</strong> All operations are sequential in nature. Thread safety is not
 * guaranteed.
 *
 * @param <T> Iterated item type.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class IterableStreamConverter<T> implements Iterator<T> {
  /** see DEFAULT_CHAR_BUFFER_SIZE in {@link BufferedReader} * */
  static final int BUFFER_SIZE = 8192;

  /** Read next entry for Stream or {@code null} when no further entry can be read. */
  private final Callable<T> readHandler;

  /** Close handler to be called when Stream terminated. */
  private final Runnable stopHandler;

  /** Error handler to be called when Stream is interrupted. */
  private final Function<Exception, RuntimeException> errorHandler;

  private boolean isDone = false;
  private boolean isNextFetched = false;
  private T next = null;

  @SuppressWarnings("checkstyle:IllegalCatch")
  @Override
  public boolean hasNext() {
    if (isDone) {
      return false;
    }
    if (isNextFetched) {
      return true;
    }
    try {
      next = readHandler.call();
      isNextFetched = true;
      if (next == null) {
        isDone = true;
        stopHandler.run();
      }
    } catch (final Exception e) {
      isDone = true;
      stopHandler.run();
      log.debug(
          "Error while reading next element (fetched={}, done={}, exType={}).",
          isNextFetched,
          isDone,
          e.getClass().getSimpleName());
      throw errorHandler.apply(e);
    }
    return !isDone;
  }

  @Override
  public T next() {
    if (next == null && !hasNext()) {
      throw new NoSuchElementException(); // normally not reached with Stream API
    }
    isNextFetched = false;
    return next;
  }

  /**
   * Create a sequential Stream of lines from an HTTP response string (UTF-8). The underlying {@link
   * InputStream} is closed, when the resulting Stream is closed (e.g. via try-with-resources) or
   * when an exception occurred.
   *
   * @param entity The HTTP entity object.
   * @param exceptionType The type of the client exception to throw in case of an error.
   * @return A sequential Stream object.
   * @throws ClientException if the provided HTTP entity object is {@code null} or empty.
   */
  @SuppressWarnings("PMD.CloseResource") // Stream is closed automatically when consumed
  @Nonnull
  static Stream<String> lines(
      @Nullable final HttpEntity entity,
      @Nonnull final BiFunction<String, Throwable, ? extends ClientException> exceptionType)
      throws ClientException {
    if (entity == null) {
      throw exceptionType.apply("Orchestration service response was empty.", null);
    }

    final InputStream inputStream;
    try {
      inputStream = entity.getContent();
    } catch (final IOException e) {
      throw exceptionType.apply("Failed to read response content.", e);
    }

    final var reader = new BufferedReader(new InputStreamReader(inputStream, UTF_8), BUFFER_SIZE);
    final Runnable closeHandler =
        () ->
            Try.run(reader::close)
                .onFailure(
                    e ->
                        log.debug(
                            "Could not close input stream with error: {} (ignored)",
                            e.getClass().getSimpleName()));
    final Function<Exception, RuntimeException> errHandler =
        e -> exceptionType.apply("Parsing response content was interrupted.", e);

    final var iterator = new IterableStreamConverter<>(reader::readLine, closeHandler, errHandler);
    final var spliterator = Spliterators.spliteratorUnknownSize(iterator, ORDERED | NONNULL);
    return StreamSupport.stream(spliterator, /* NOT PARALLEL */ false).onClose(closeHandler);
  }
}
