package com.sap.ai.sdk.foundationmodels.openai;

import com.sap.ai.sdk.foundationmodels.openai.model.DeltaAggregatable;
import com.sap.ai.sdk.foundationmodels.openai.model.StreamedDelta;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Generic OpenAI stream output.
 *
 * @param <D> the type of the streamed delta
 * @param <T> the type of the total output
 */
@RequiredArgsConstructor(access = AccessLevel.NONE)
@NoArgsConstructor
@Setter(AccessLevel.PACKAGE)
@Accessors(chain = true)
// D extends StreamedDelta but Java generics, oddly enough, use extends for interfaces
public class OpenAiStream<D extends StreamedDelta, T extends DeltaAggregatable<D>>
    implements AutoCloseable {

  @Getter(onMethod_ = @Nonnull)
  private Stream<D> deltaStream;

  @Nonnull private T totalOutput;

  void addDelta(D delta) {
    totalOutput.addDelta(delta);
  }

  /**
   * Get the total aggregated output from all deltas. Closes the delta stream.
   *
   * @return the total output until now.
   */
  @Nonnull
  public T getTotalOutput() {
    deltaStream.close();
    return totalOutput;
  }

  /** Close the delta stream. */
  @Override
  public void close() {
    deltaStream.close();
  }
}
