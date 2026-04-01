package com.sap.ai.sdk.foundationmodels.openai;

import com.openai.client.OpenAIClient;
import com.openai.client.OpenAIClientAsync;
import com.openai.client.OpenAIClientImpl;
import com.openai.core.ClientOptions;
import com.openai.services.blocking.AudioService;
import com.openai.services.blocking.BatchService;
import com.openai.services.blocking.BetaService;
import com.openai.services.blocking.ChatService;
import com.openai.services.blocking.CompletionService;
import com.openai.services.blocking.ContainerService;
import com.openai.services.blocking.ConversationService;
import com.openai.services.blocking.EmbeddingService;
import com.openai.services.blocking.EvalService;
import com.openai.services.blocking.FileService;
import com.openai.services.blocking.FineTuningService;
import com.openai.services.blocking.GraderService;
import com.openai.services.blocking.ImageService;
import com.openai.services.blocking.ModelService;
import com.openai.services.blocking.ModerationService;
import com.openai.services.blocking.RealtimeService;
import com.openai.services.blocking.ResponseService;
import com.openai.services.blocking.SkillService;
import com.openai.services.blocking.UploadService;
import com.openai.services.blocking.VectorStoreService;
import com.openai.services.blocking.VideoService;
import com.openai.services.blocking.WebhookService;
import com.sap.ai.sdk.foundationmodels.openai.AiCoreOpenAiClient.AiCoreHttpClientImpl;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
class OpenAIClientImplWrapper implements OpenAIClient {

  @Delegate(types = OtherMethods.class)
  @Nonnull
  private final OpenAIClientImpl delegate;

  @Nonnull private final String deploymentModel;

  @Nonnull
  @Override
  public OpenAIClient withOptions(@Nonnull final Consumer<ClientOptions.Builder> consumer) {
    return new OpenAIClientImplWrapper(
        (OpenAIClientImpl) delegate.withOptions(consumer), deploymentModel);
  }

  @Nonnull
  @Override
  public ChatService chat() {
    return new AiCoreChatService(delegate.chat(), deploymentModel);
  }

  @Override
  @Nonnull
  public ResponseService responses() {
    return new AiCoreResponseService(delegate.responses(), deploymentModel);
  }

  /**
   * Methods that are delegated to the underlying OpenAI client.
   *
   * <p>Note: Most of these methods will throw {@link UnsupportedOperationException} at runtime due
   * to endpoint constraints enforced in {@code AiCoreHttpClientImpl}.
   *
   * @see AiCoreHttpClientImpl#validateAllowedEndpoint
   */
  private interface OtherMethods {
    OpenAIClientAsync async();

    OpenAIClient.WithRawResponse withRawResponse();

    OpenAIClient withOptions(Consumer<ClientOptions.Builder> modifier);

    CompletionService completions();

    EmbeddingService embeddings();

    FileService files();

    ImageService images();

    AudioService audio();

    ModerationService moderations();

    ModelService models();

    FineTuningService fineTuning();

    GraderService graders();

    VectorStoreService vectorStores();

    WebhookService webhooks();

    BetaService beta();

    BatchService batches();

    UploadService uploads();

    RealtimeService realtime();

    ConversationService conversations();

    EvalService evals();

    ContainerService containers();

    SkillService skills();

    VideoService videos();

    void close();
  }
}
