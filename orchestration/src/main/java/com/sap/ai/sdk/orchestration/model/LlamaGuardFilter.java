package com.sap.ai.sdk.orchestration.model;

import static com.sap.ai.sdk.orchestration.model.LlamaGuard38bFilterConfig.TypeEnum.LLAMA_GUARD_3_8B;

import com.sap.ai.sdk.orchestration.ContentFilter;
import javax.annotation.Nonnull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Setter
public class LlamaGuardFilter implements ContentFilter {

  private LlamaGuard38b config = LlamaGuard38b.create();

  @Nonnull
  @Override
  public LlamaGuard38bFilterConfig createConfig() {
    return LlamaGuard38bFilterConfig.create().type(LLAMA_GUARD_3_8B).config(config);
  }
}
