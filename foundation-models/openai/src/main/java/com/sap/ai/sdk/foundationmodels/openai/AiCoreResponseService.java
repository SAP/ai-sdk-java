package com.sap.ai.sdk.foundationmodels.openai;

import com.openai.core.ClientOptions;
import com.openai.core.RequestOptions;
import com.openai.core.http.StreamResponse;
import com.openai.models.ChatModel;
import com.openai.models.ResponsesModel;
import com.openai.models.responses.CompactedResponse;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCancelParams;
import com.openai.models.responses.ResponseCompactParams;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseDeleteParams;
import com.openai.models.responses.ResponseRetrieveParams;
import com.openai.models.responses.ResponseStreamEvent;
import com.openai.models.responses.StructuredResponse;
import com.openai.models.responses.StructuredResponseCreateParams;
import com.openai.services.blocking.ResponseService;
import com.openai.services.blocking.responses.InputItemService;
import com.openai.services.blocking.responses.InputTokenService;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import lombok.experimental.Delegate;

class AiCoreResponseService implements ResponseService {

  @Delegate(types = SafeMethods.class)
  private final ResponseService delegate;

  private final ChatModel expectedModel;

  @Override
  @Nonnull
  public ResponseService withOptions(@Nonnull final Consumer<ClientOptions.Builder> modifier) {
    return new AiCoreResponseService(delegate.withOptions(modifier), expectedModel);
  }

  AiCoreResponseService(
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

  @Override
  @Nonnull
  public CompactedResponse compact(@Nonnull final ResponseCompactParams params) {
    return compact(params, RequestOptions.none());
  }

  @Override
  @Nonnull
  public CompactedResponse compact(
      @Nonnull final ResponseCompactParams params, @Nonnull final RequestOptions requestOptions) {
    return delegate.compact(withValidatedModel(params), requestOptions);
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

  @Nonnull
  private ResponseCompactParams withValidatedModel(@Nonnull final ResponseCompactParams params) {
    final String givenModel =
        params.model().map(ResponseCompactParams.Model::asString).orElse(null);

    if (givenModel == null) {
      return params.toBuilder().model(expectedModel.asString()).build();
    }

    if (!expectedModel.asString().equals(givenModel)) {
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

  private interface SafeMethods {
    InputItemService inputItems();

    InputTokenService inputTokens();

    ResponseService.WithRawResponse withRawResponse();

    Response retrieve(ResponseRetrieveParams params, RequestOptions requestOptions);

    Response retrieve(ResponseRetrieveParams params);

    StreamResponse<ResponseStreamEvent> retrieveStreaming(
        ResponseRetrieveParams params, RequestOptions requestOptions);

    StreamResponse<ResponseStreamEvent> retrieveStreaming(ResponseRetrieveParams params);

    // Delete operation - DELETE by ID, no model parameter
    void delete(ResponseDeleteParams params, RequestOptions requestOptions);

    void delete(ResponseDeleteParams params);

    // Cancel operation - no model parameter
    Response cancel(ResponseCancelParams params, RequestOptions requestOptions);

    Response cancel(ResponseCancelParams params);
  }
}
