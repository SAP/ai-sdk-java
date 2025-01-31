package com.sap.ai.sdk.foundationmodels.openai;

import com.sap.ai.sdk.foundationmodels.openai.model2.EmbeddingsCreateRequest;
import com.sap.ai.sdk.foundationmodels.openai.model2.EmbeddingsCreateRequestInput;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/** Factory class for creating OpenAI Embedding Requests. */
@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenAiEmbeddingsRequestFactory {

  /**
   * Creates an EmbeddingsCreateRequest from a list of input strings.
   *
   * @param input the list of input strings
   * @return an EmbeddingsCreateRequest with the provided input
   */
  public static EmbeddingsCreateRequest fromList(@Nonnull List<String> input) {
    return new EmbeddingsCreateRequest().input(EmbeddingsCreateRequestInput.create(input));
  }

  /**
   * Creates an EmbeddingsCreateRequest from a single input string and additional input strings.
   *
   * @param input the first input string
   * @param inputs additional input strings
   * @return an EmbeddingsCreateRequest with the combined input strings
   */
  public static EmbeddingsCreateRequest fromStrings(
      @Nonnull String input, @Nonnull String... inputs) {
    var inputList = new ArrayList<String>();

    inputList.add(input);
    inputList.addAll(List.of(inputs));

    return fromList(inputList);
  }
}
