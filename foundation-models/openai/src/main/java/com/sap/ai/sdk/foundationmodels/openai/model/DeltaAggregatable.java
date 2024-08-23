package com.sap.ai.sdk.foundationmodels.openai.model;

/**
 * Interface for model classes that can be created from aggregated streamed deltas.
 *
 * <p>For example aggregating chat completions deltas into a single chat completion output.
 *
 * @param <D> the delta type.
 */
public interface DeltaAggregatable<D> {

  void addDelta(D delta);
}
