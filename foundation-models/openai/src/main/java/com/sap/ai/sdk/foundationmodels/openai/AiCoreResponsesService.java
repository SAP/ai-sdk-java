package com.sap.ai.sdk.foundationmodels.openai;

import com.openai.core.RequestOptions;
import com.openai.core.http.StreamResponse;
import com.openai.models.ChatModel;
import com.openai.models.ResponsesModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseStreamEvent;
import com.openai.models.responses.StructuredResponse;
import com.openai.models.responses.StructuredResponseCreateParams;
import com.openai.services.blocking.ResponseService;
import javax.annotation.Nonnull;
import lombok.experimental.Delegate;

class AiCoreResponsesService implements ResponseService {

  @Delegate(excludes = Excludes.class)
  private final ResponseService delegate;

  private final ChatModel expectedModel;

  AiCoreResponsesService(
      @Nonnull final ResponseService delegate, @Nonnull final ChatModel expectedModel) {
    this.delegate = delegate;
    this.expectedModel = expectedModel;
  }

  @Override
  @Nonnull
  public Response create(@Nonnull final ResponseCreateParams params) {
    return create(params, RequestOptions.none());
  }

  @Override
  @Nonnull
  public Response create(
      @Nonnull final ResponseCreateParams params, @Nonnull final RequestOptions requestOptions) {
    return delegate.create(withValidatedModel(params), requestOptions);
  }

  @Override
  @Nonnull
  public <T> StructuredResponse<T> create(@Nonnull final StructuredResponseCreateParams<T> params) {
    return create(params, RequestOptions.none());
  }

  @Override
  @Nonnull
  public <T> StructuredResponse<T> create(
      @Nonnull final StructuredResponseCreateParams<T> params,
      @Nonnull final RequestOptions requestOptions) {
    return delegate.create(withValidatedModel(params), requestOptions);
  }

  @Override
  @Nonnull
  public StreamResponse<ResponseStreamEvent> createStreaming(
      @Nonnull final ResponseCreateParams params) {
    return createStreaming(params, RequestOptions.none());
  }

  @Override
  @Nonnull
  public StreamResponse<ResponseStreamEvent> createStreaming(
      @Nonnull final ResponseCreateParams params, @Nonnull final RequestOptions requestOptions) {
    return delegate.createStreaming(withValidatedModel(params), requestOptions);
  }

  @Override
  @Nonnull
  public StreamResponse<ResponseStreamEvent> createStreaming(
      @Nonnull final StructuredResponseCreateParams<?> params) {
    return createStreaming(params, RequestOptions.none());
  }

  @Override
  @Nonnull
  public StreamResponse<ResponseStreamEvent> createStreaming(
      @Nonnull final StructuredResponseCreateParams<?> params,
      @Nonnull final RequestOptions requestOptions) {
    return delegate.createStreaming(withValidatedModel(params), requestOptions);
  }

  @Nonnull
  private ResponseCreateParams withValidatedModel(@Nonnull final ResponseCreateParams params) {
    final ChatModel givenModel = params.model().map(ResponsesModel::asChat).orElse(null);

    if (givenModel == null) {
      return params.toBuilder().model(expectedModel).build();
    }

    if (!expectedModel.equals(givenModel)) {
      throw new IllegalArgumentException(
          """
          Model mismatch:
            Expected : '%s' (configured via forModel())
            Actual   : '%s' (set in request parameters)
          Fix: Either remove the model from the request parameters, \
          or use forModel("%s") when creating the client.\
          """
              .formatted(expectedModel, givenModel, givenModel));
    }

    return params;
  }

  @Nonnull
  private <T> StructuredResponseCreateParams<T> withValidatedModel(
      @Nonnull final StructuredResponseCreateParams<T> params) {
    final ResponseCreateParams validated = withValidatedModel(params.rawParams());
    return new StructuredResponseCreateParams<>(params.responseType(), validated);
  }

  private interface Excludes {
    Response create(ResponseCreateParams params);

    Response create(ResponseCreateParams params, RequestOptions requestOptions);

    <T> StructuredResponse<T> create(StructuredResponseCreateParams<T> params);

    <T> StructuredResponse<T> create(
        StructuredResponseCreateParams<T> params, RequestOptions requestOptions);

    StreamResponse<ResponseStreamEvent> createStreaming(ResponseCreateParams params);

    StreamResponse<ResponseStreamEvent> createStreaming(
        ResponseCreateParams params, RequestOptions requestOptions);

    StreamResponse<ResponseStreamEvent> createStreaming(StructuredResponseCreateParams<?> params);

    StreamResponse<ResponseStreamEvent> createStreaming(
        StructuredResponseCreateParams<?> params, RequestOptions requestOptions);
  }
}
