package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Value;

@Beta
@Value
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
public class OpenAiFunctionCallItem implements OpenAiToolCallItem {
  @Nonnull String type = "function";

  @Nonnull String id;
  @Nonnull String name;
  @Nonnull String arguments;
}
