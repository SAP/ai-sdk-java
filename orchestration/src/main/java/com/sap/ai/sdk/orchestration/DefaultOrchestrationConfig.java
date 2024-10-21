package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.core.AiModel;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import io.vavr.control.Option;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter(AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultOrchestrationConfig<T extends OrchestrationConfig<T>>
    implements OrchestrationConfig<T> {

  @Nonnull private Option<AiModel> llmConfig = Option.none();
  @Nonnull private Option<TemplatingModuleConfig> template = Option.none();
  @Nonnull private Option<MaskingConfig> maskingConfig = Option.none();
  @Nonnull private Option<ContentFilter> inputContentFilter = Option.none();
  @Nonnull private Option<ContentFilter> outputContentFilter = Option.none();

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @Getter(AccessLevel.NONE)
  @Nonnull
  private final T wrapper;

  @SuppressWarnings("unchecked")
  private DefaultOrchestrationConfig() {
    wrapper = (T) this;
  }

  /**
   * Create a new instance of {@link DefaultOrchestrationConfig} to delegate to. This is useful when
   * exposing the {@link OrchestrationConfig} in other objects, without re-implementing it. To
   * maintain fluent API usage, the given wrapper object will be returned by the fluent methods,
   * instead of this instance.
   *
   * @param wrapper The wrapper that delegates to this object.
   * @param <T> The type of the wrapper object.
   * @return The new instance.
   * @see #standalone()
   */
  @Nonnull
  public static <T extends OrchestrationConfig<T>> DefaultOrchestrationConfig<T> asDelegateFor(
      @Nonnull final T wrapper) {
    return new DefaultOrchestrationConfig<>(wrapper);
  }

  /**
   * Create an implementation without any object delegating to it. The fluent API will return this
   * object itself.
   *
   * @return The new instance.
   * @see #asDelegateFor(OrchestrationConfig)
   */
  @Nonnull
  public static DefaultOrchestrationConfig<?> standalone() {
    return new DefaultOrchestrationConfig<>();
  }

  @Nonnull
  @Override
  public T withLlmConfig(@Nonnull final AiModel llm) {
    this.llmConfig = Option.some(llm);
    return wrapper;
  }

  @Nonnull
  @Override
  public T withTemplate(@Nonnull final TemplatingModuleConfig template) {
    this.template = Option.some(template);
    return wrapper;
  }

  @Nonnull
  @Override
  public T withMaskingConfig(@Nonnull final MaskingConfig maskingConfig) {
    this.maskingConfig = Option.some(maskingConfig);
    return wrapper;
  }

  @Nonnull
  @Override
  public T withInputContentFilter(@Nonnull final ContentFilter filter) {
    this.inputContentFilter = Option.some(filter);
    return wrapper;
  }

  @Nonnull
  @Override
  public T withOutputContentFilter(@Nonnull final ContentFilter filter) {
    this.outputContentFilter = Option.some(filter);
    return wrapper;
  }

  /**
   * Copy the configuration into the given target configuration. The copy is
   * <strong>shallow</strong> and does <strong>not override</strong> any existing configuration.
   *
   * <p>This has two main use cases:
   *
   * <ol>
   *   <li>Duplicating a config
   *   <li>Applying defaults to a config
   * </ol>
   *
   * @param source The source configuration to copy from.
   * @return This (delegate) object.
   */
  @Nonnull
  public DefaultOrchestrationConfig<T> copyFrom(@Nonnull final OrchestrationConfig<?> source) {
    llmConfig.orElse(source::getLlmConfig).forEach(this::withLlmConfig);
    template.orElse(source::getTemplate).forEach(this::withTemplate);
    maskingConfig.orElse(source::getMaskingConfig).forEach(this::withMaskingConfig);
    inputContentFilter.orElse(source::getInputContentFilter).forEach(this::withInputContentFilter);
    outputContentFilter
        .orElse(source::getOutputContentFilter)
        .forEach(this::withOutputContentFilter);
    return this;
  }
}
