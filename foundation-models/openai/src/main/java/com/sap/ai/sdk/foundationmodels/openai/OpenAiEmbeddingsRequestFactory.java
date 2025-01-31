package com.sap.ai.sdk.foundationmodels.openai;

import com.sap.ai.sdk.foundationmodels.openai.model2.EmbeddingsCreateRequest;
import com.sap.ai.sdk.foundationmodels.openai.model2.EmbeddingsCreateRequestInput;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenAiEmbeddingsRequestFactory {

  public static EmbeddingsCreateRequest fromList(@Nonnull List<String> input) {
    return new EmbeddingsCreateRequest().input(EmbeddingsCreateRequestInput.create(input));
  }

  public static EmbeddingsCreateRequest fromStrings(
      @Nonnull String input, @Nonnull String... inputs) {
    var inputList = new ArrayList<String>();

    inputList.add(input);
    inputList.addAll(List.of(inputs));

    return fromList(inputList);
  }
}
