package com.sap.ai.sdk.foundationmodels.openai.model;

public interface Streamable<D> {

  void addDelta(D delta);
}
