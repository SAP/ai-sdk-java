package com.sap.ai.sdk.foundationmodels.openai;

import com.openai.core.ClientOptions;
import com.openai.core.RequestOptions;
import com.openai.core.http.QueryParams;
import com.openai.core.http.StreamResponse;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionChunk;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.ChatCompletionDeleteParams;
import com.openai.models.chat.completions.ChatCompletionDeleted;
import com.openai.models.chat.completions.ChatCompletionListPage;
import com.openai.models.chat.completions.ChatCompletionListParams;
import com.openai.models.chat.completions.ChatCompletionRetrieveParams;
import com.openai.models.chat.completions.ChatCompletionUpdateParams;
import com.openai.models.chat.completions.StructuredChatCompletion;
import com.openai.models.chat.completions.StructuredChatCompletionCreateParams;
import com.openai.services.blocking.chat.ChatCompletionService;
import com.openai.services.blocking.chat.completions.MessageService;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import lombok.experimental.Delegate;
import org.jspecify.annotations.NonNull;

class AiCoreChatCompletionService implements ChatCompletionService {

  @Delegate(types = PassThroughMethods.class)
  private final ChatCompletionService delegate;

  private final String deploymentModel;

  AiCoreChatCompletionService(
      @Nonnull final ChatCompletionService delegate, @Nonnull final String expectedModel) {
    final var apiVersionQuery = QueryParams.builder().put("api-version", "2024-02-01").build();
    this.delegate = delegate.withOptions(builder -> builder.queryParams(apiVersionQuery));
    this.deploymentModel = expectedModel;
  }

  @Override
  @NonNull
  public ChatCompletionService withOptions(
      @NonNull final Consumer<ClientOptions.Builder> consumer) {
    return new AiCoreChatCompletionService(delegate.withOptions(consumer), deploymentModel);
  }

  @Override
  @Nonnull
  public ChatCompletionService.WithRawResponse withRawResponse() {
    throw new UnsupportedOperationException(
        "withRawResponse() is not supported by AiCoreResponseService.");
  }

  @Override
  @Nonnull
  public @NonNull ChatCompletion create(@NonNull final ChatCompletionCreateParams params) {
    return create(params, RequestOptions.none());
  }

  @Override
  @NonNull
  public ChatCompletion create(
      @NonNull final ChatCompletionCreateParams params,
      @NonNull final RequestOptions requestOptions) {
    return delegate.create(params, requestOptions);
  }

  @Override
  @NonNull
  public <T> StructuredChatCompletion<T> create(
      @NonNull final StructuredChatCompletionCreateParams<T> params) {
    return create(params, RequestOptions.none());
  }

  @Override
  @NonNull
  public <T> StructuredChatCompletion<T> create(
      @NonNull final StructuredChatCompletionCreateParams<T> params,
      @NonNull final RequestOptions requestOptions) {
    return delegate.create(params, requestOptions);
  }

  @Override
  @NonNull
  public StreamResponse<ChatCompletionChunk> createStreaming(
      @NonNull final ChatCompletionCreateParams params) {
    return createStreaming(params, RequestOptions.none());
  }

  @Override
  @NonNull
  public StreamResponse<ChatCompletionChunk> createStreaming(
      @NonNull final ChatCompletionCreateParams params,
      @NonNull final RequestOptions requestOptions) {
    return delegate.createStreaming(params, requestOptions);
  }

  @Override
  @NonNull
  public StreamResponse<ChatCompletionChunk> createStreaming(
      @NonNull final StructuredChatCompletionCreateParams<?> params) {
    return createStreaming(params, RequestOptions.none());
  }

  @Override
  @NonNull
  public StreamResponse<ChatCompletionChunk> createStreaming(
      @NonNull final StructuredChatCompletionCreateParams<?> params,
      @NonNull final RequestOptions requestOptions) {
    return delegate.createStreaming(params, requestOptions);
  }

  @Nonnull
  private ChatCompletionCreateParams useDeploymentModel(
      @Nonnull final ChatCompletionCreateParams params) {
    final var givenModel = params.model().asString();

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
    ChatCompletionDeleted delete(
        ChatCompletionDeleteParams chatCompletionDeleteParams, RequestOptions requestOptions);

    ChatCompletionDeleted delete(String completionId);

    ChatCompletionDeleted delete(String completionId, ChatCompletionDeleteParams params);

    ChatCompletionDeleted delete(
        String completionId, ChatCompletionDeleteParams params, RequestOptions requestOptions);

    ChatCompletionDeleted delete(String completionId, RequestOptions requestOptions);

    ChatCompletionDeleted delete(ChatCompletionDeleteParams params);

    ChatCompletionListPage list();

    ChatCompletionListPage list(
        ChatCompletionListParams chatCompletionListParams, RequestOptions requestOptions);

    ChatCompletionListPage list(ChatCompletionListParams params);

    ChatCompletionListPage list(RequestOptions requestOptions);

    MessageService messages();

    ChatCompletion retrieve(
        ChatCompletionRetrieveParams chatCompletionRetrieveParams, RequestOptions requestOptions);

    ChatCompletion retrieve(String completionId);

    ChatCompletion retrieve(String completionId, ChatCompletionRetrieveParams params);

    ChatCompletion retrieve(
        String completionId, ChatCompletionRetrieveParams params, RequestOptions requestOptions);

    ChatCompletion retrieve(String completionId, RequestOptions requestOptions);

    ChatCompletion retrieve(ChatCompletionRetrieveParams params);

    ChatCompletion update(
        ChatCompletionUpdateParams chatCompletionUpdateParams, RequestOptions requestOptions);

    ChatCompletion update(String completionId, ChatCompletionUpdateParams params);

    ChatCompletion update(
        String completionId, ChatCompletionUpdateParams params, RequestOptions requestOptions);

    ChatCompletion update(ChatCompletionUpdateParams params);
  }
}
