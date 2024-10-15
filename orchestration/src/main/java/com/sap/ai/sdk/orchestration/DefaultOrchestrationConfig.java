package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import io.vavr.control.Option;

import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.PRIVATE)
public class DefaultOrchestrationConfig<T extends OrchestrationConfig<T>>
    implements OrchestrationConfig<T> {

  @Nonnull private Option<LLMModuleConfig> llmConfig = Option.none();
  @Nonnull private Option<TemplatingModuleConfig> template = Option.none();
  @Nonnull private Option<MaskingConfig> maskingConfig = Option.none();
  @Nonnull private Option<ContentFilter> inputContentFilter = Option.none();
  @Nonnull private Option<ContentFilter> outputContentFilter = Option.none();

  @Nonnull
  @Override
  @SuppressWarnings("unchecked")
  public T instance() {
    return (T) this;
  }

  @Nonnull
  @Override
  public T withLlmConfig(@Nonnull final LLMModuleConfig llm) {
    this.llmConfig = Option.some(llm);
    return instance();
  }

  @Nonnull
  @Override
  public T withTemplate(@Nonnull final TemplatingModuleConfig template) {
    this.template = Option.some(template);
    return instance();
  }

  @Nonnull
  @Override
  public T withMaskingConfig(@Nonnull final MaskingConfig maskingConfig) {
    this.maskingConfig = Option.some(maskingConfig);
    return instance();
  }

  @Nonnull
  @Override
  public T withInputContentFilter(@Nonnull final ContentFilter filter) {
    this.inputContentFilter = Option.some(filter);
    return instance();
  }

  @Nonnull
  @Override
  public T withOutputContentFilter(@Nonnull final ContentFilter filter) {
    this.outputContentFilter = Option.some(filter);
    return instance();
  }
}
