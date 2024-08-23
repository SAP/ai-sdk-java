package com.sap.ai.sdk.foundationmodels.openai.model;

import com.sap.ai.sdk.foundationmodels.openai.Delta;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/** OpenAI chat completion stream output. */
@RequiredArgsConstructor(access = AccessLevel.NONE)
@NoArgsConstructor
@Setter // TODO: package private
@Accessors(chain = true)
// T implements Streamable but Java generics, oddly enough, use extends for interfaces 😣
public class OpenAiStream<D extends Delta, T extends Streamable<D>> implements AutoCloseable {

  @Getter @Nonnull private Stream<D> deltaStream;
  @Nonnull private T total;

  // TODO: package private
  public void addDelta(D delta) {
    total.addDelta(delta);
  }

  public Future<T> getTotalOutput() {
    // wait for deltaStream to be closed
    return new FutureTask<>(
        () -> {
          // wait for deltaStream to be closed
          deltaStream.onClose(() -> {});
          return total;
        });
  }

  @Override
  public void close() {
    deltaStream.close();
  }
}
