package com.sap.ai.sdk.foundationmodels.openai.model;

import java.util.concurrent.Future;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@RequiredArgsConstructor(access = AccessLevel.NONE)
@NoArgsConstructor
@Setter
@Accessors(chain = true)
public class OpenAiChatCompletionStream<T> implements AutoCloseable {

  @Nonnull private Stream<T> deltaStream;
  @Nonnull private Future<T> total; // TODO: fill out "total" delta by delta

  @Override
  public void close() {
    deltaStream.close();
    // TODO: fill out "total" or cancel it
  }
}
