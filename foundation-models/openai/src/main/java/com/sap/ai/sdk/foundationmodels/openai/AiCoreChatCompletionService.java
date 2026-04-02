package com.sap.ai.sdk.foundationmodels.openai;

import com.openai.core.ClientOptions;
import com.openai.core.RequestOptions;
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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AiCoreChatCompletionService implements ChatCompletionService {

  @Delegate(types = PassThroughMethods.class)
  private final ChatCompletionService delegate;

  private final String deploymentModel;

  @Override
  @Nonnull
  public ChatCompletionService withOptions(
      @Nonnull final Consumer<ClientOptions.Builder> consumer) {
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
  public ChatCompletion create(@Nonnull final ChatCompletionCreateParams params) {
    return create(params, RequestOptions.none());
  }

  @Override
  @Nonnull
  public ChatCompletion create(
      @Nonnull final ChatCompletionCreateParams params,
      @Nonnull final RequestOptions requestOptions) {
    throwOnModelMismatch(params.model().asString());
    return delegate.create(params, requestOptions);
  }

  @Override
  @Nonnull
  public <T> StructuredChatCompletion<T> create(
      @Nonnull final StructuredChatCompletionCreateParams<T> params) {
    return create(params, RequestOptions.none());
  }

  @Override
  @Nonnull
  public <T> StructuredChatCompletion<T> create(
      @Nonnull final StructuredChatCompletionCreateParams<T> params,
      @Nonnull final RequestOptions requestOptions) {
    throwOnModelMismatch(params.rawParams().model().asString());
    return delegate.create(params, requestOptions);
  }

  @Override
  @Nonnull
  public StreamResponse<ChatCompletionChunk> createStreaming(
      @Nonnull final ChatCompletionCreateParams params) {
    return createStreaming(params, RequestOptions.none());
  }

  @Override
  @Nonnull
  public StreamResponse<ChatCompletionChunk> createStreaming(
      @Nonnull final ChatCompletionCreateParams params,
      @Nonnull final RequestOptions requestOptions) {
    throwOnModelMismatch(params.model().asString());
    return delegate.createStreaming(params, requestOptions);
  }

  @Override
  @Nonnull
  public StreamResponse<ChatCompletionChunk> createStreaming(
      @Nonnull final StructuredChatCompletionCreateParams<?> params) {
    return createStreaming(params, RequestOptions.none());
  }

  @Override
  @Nonnull
  public StreamResponse<ChatCompletionChunk> createStreaming(
      @Nonnull final StructuredChatCompletionCreateParams<?> params,
      @Nonnull final RequestOptions requestOptions) {
    throwOnModelMismatch(params.rawParams().model().asString());
    return delegate.createStreaming(params, requestOptions);
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
