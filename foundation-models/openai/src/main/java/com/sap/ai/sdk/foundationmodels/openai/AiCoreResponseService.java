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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AiCoreResponseService implements ResponseService {

  @Delegate(types = PassThroughMethods.class)
  private final ResponseService delegate;

  private final String deploymentModel;

  @Override
  @Nonnull
  public ResponseService withOptions(@Nonnull final Consumer<ClientOptions.Builder> modifier) {
    return new AiCoreResponseService(delegate.withOptions(modifier), deploymentModel);
  }

  @Override
  @Nonnull
  public ResponseService.WithRawResponse withRawResponse() {
    throw new UnsupportedOperationException(
        "withRawResponse() is not supported by AiCoreResponseService.");
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
    return delegate.create(useDeploymentModel(params), requestOptions);
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
    return delegate.create(useDeploymentModel(params), requestOptions);
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
    return delegate.createStreaming(useDeploymentModel(params), requestOptions);
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
    return delegate.createStreaming(useDeploymentModel(params), requestOptions);
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
    return delegate.compact(useDeploymentModel(params), requestOptions);
  }

  @Nonnull
  private <T> StructuredResponseCreateParams<T> useDeploymentModel(
      @Nonnull final StructuredResponseCreateParams<T> params) {
    final ResponseCreateParams validated = useDeploymentModel(params.rawParams());
    return new StructuredResponseCreateParams<>(params.responseType(), validated);
  }

  @Nonnull
  private ResponseCreateParams useDeploymentModel(@Nonnull final ResponseCreateParams params) {
    final var givenModel =
        params.model().map(ResponsesModel::asChat).map(ChatModel::asString).orElse(null);

    if (givenModel == null) {
      return params.toBuilder().model(deploymentModel).build();
    }

    throwOnModelMismatch(givenModel);
    return params;
  }

  @Nonnull
  private ResponseCompactParams useDeploymentModel(@Nonnull final ResponseCompactParams params) {
    final String givenModel =
        params.model().map(ResponseCompactParams.Model::asString).orElse(null);

    if (givenModel == null) {
      return params.toBuilder().model(deploymentModel).build();
    }

    throwOnModelMismatch(givenModel);
    return params;
  }

  private void throwOnModelMismatch(@Nonnull final String givenModel) {
    if (!deploymentModel.equals(givenModel)) {
      throw new IllegalArgumentException(
          """
        Model mismatch:
          Expected : '%s' (configured via forModel())
          Actual   : '%s' (set in request parameters)
        Fix: Either remove the model from the request parameters, \
        or use forModel("%s") when creating the client.\
        """
              .formatted(deploymentModel, givenModel, givenModel));
    }
  }

  private interface PassThroughMethods {
    InputItemService inputItems();

    InputTokenService inputTokens();

    Response retrieve(ResponseRetrieveParams params, RequestOptions requestOptions);

    Response retrieve(ResponseRetrieveParams params);

    StreamResponse<ResponseStreamEvent> retrieveStreaming(
        ResponseRetrieveParams params, RequestOptions requestOptions);

    StreamResponse<ResponseStreamEvent> retrieveStreaming(ResponseRetrieveParams params);

    void delete(ResponseDeleteParams params, RequestOptions requestOptions);

    void delete(ResponseDeleteParams params);

    Response cancel(ResponseCancelParams params, RequestOptions requestOptions);

    Response cancel(ResponseCancelParams params);
  }
}
