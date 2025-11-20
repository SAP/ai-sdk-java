package com.sap.ai.sdk.core.common;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Spliterator.NONNULL;
import static java.util.Spliterator.ORDERED;

import io.vavr.control.Try;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ClassicHttpResponse;

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

  private static final String ERR_CONTENT = "Failed to read response content.";
  private static final String ERR_INTERRUPTED = "Parsing response content was interrupted.";
  private static final String ERR_CLOSE = "Could not close input stream with error: {} (ignored).";

  /** Read next entry for Stream or {@code null} when no further entry can be read. */
  private final Callable<T> readHandler;

  /** Close handler to be called when Stream terminated. */
  private final Runnable stopHandler;

  /** Error handler to be called when Stream is interrupted. */
  private final Function<Throwable, RuntimeException> errorHandler;

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
      log.debug("Reading next element failed with error {}.", e.getClass().getSimpleName());
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
   * java.io.InputStream} is closed, when the resulting Stream is closed (e.g. via
   * try-with-resources) or when an exception occurred.
   *
   * @param response The HTTP response object.
   * @param exceptionFactory The exception factory to use for creating exceptions.
   * @return A sequential Stream object.
   * @throws ClientException if the provided HTTP entity object is {@code null} or empty.
   */
  @SuppressWarnings("PMD.CloseResource") // Stream is closed automatically when consumed
  @Nonnull
  static <R extends ClientError> Stream<String> lines(
      @Nonnull final ClassicHttpResponse response,
      @Nonnull final ClientExceptionFactory<? extends ClientException, R> exceptionFactory)
      throws ClientException {
    if (response.getEntity() == null) {
      throw exceptionFactory.build("The HTTP Response is empty").setHttpResponse(response);
    }

    // access input stream
    final var inputStream =
        Try.of(() -> response.getEntity().getContent())
            .getOrElseThrow(e -> exceptionFactory.build(ERR_CONTENT, e).setHttpResponse(response));

    // initialize buffered reader
    final var reader = new BufferedReader(new InputStreamReader(inputStream, UTF_8), BUFFER_SIZE);

    // define close handler
    final Runnable closeHandler =
        () -> Try.run(reader::close).onFailure(e -> log.debug(ERR_CLOSE, e.getClass()));

    // define error handler
    final Function<Throwable, RuntimeException> errHandler =
        e -> exceptionFactory.build(ERR_INTERRUPTED, e).setHttpResponse(response);

    // initialize lazy stream iterator
    final var iterator = new IterableStreamConverter<>(reader::readLine, closeHandler, errHandler);

    // create lazy stream as output
    final var spliterator = Spliterators.spliteratorUnknownSize(iterator, ORDERED | NONNULL);
    return StreamSupport.stream(spliterator, /* NOT PARALLEL */ false).onClose(closeHandler);
  }
}
