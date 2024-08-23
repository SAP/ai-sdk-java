package com.sap.ai.sdk.foundationmodels.openai;

import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.sap.ai.sdk.foundationmodels.openai.model.DeltaAggregatable;
import com.sap.ai.sdk.foundationmodels.openai.model.StreamedDelta;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/** Generic OpenAI stream output. */
@RequiredArgsConstructor(access = AccessLevel.NONE)
@NoArgsConstructor
@Setter // TODO: package private
@Accessors(chain = true)
// D extends StreamedDelta but Java generics, oddly enough, use extends for interfaces
public class OpenAiStream<D extends StreamedDelta, T extends DeltaAggregatable<D>>
    implements AutoCloseable {

  @Getter @Nonnull private Stream<D> deltaStream;
  @Nonnull private T totalOutput;

  void addDelta(D delta) {
    totalOutput.addDelta(delta);
  }

  /** Get the total aggregated output. */
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
