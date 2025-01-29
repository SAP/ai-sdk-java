package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.model.LlamaGuard38bFilterConfig.TypeEnum.LLAMA_GUARD_3_8B;

import com.sap.ai.sdk.orchestration.model.LlamaGuard38b;
import com.sap.ai.sdk.orchestration.model.LlamaGuard38bFilterConfig;
import javax.annotation.Nonnull;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A content filter wrapping Llama Guard filter config.
 *
 * <p>This class allows setting filters for different content categories such as hate, self-harm,
 * sexual, and violence.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * // values not set are disabled by default
 * val config =
 *     LlamaGuard38b.create()
 *         .violentCrimes(true)
 *         .selfHarm(true);
 * val filterConfig = new LlamaGuardFilter().config(config);
 * }</pre>
 *
 * @link <a
 *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/input-filtering">SAP AI
 *     Core: Orchestration - Input Filtering</a>
 * @link <a
 *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/output-filtering">SAP
 *     AI Core: Orchestration - Output Filtering</a>
 */
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
