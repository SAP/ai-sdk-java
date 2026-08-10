package com.sap.ai.sdk.foundationmodels.openai.realtime;

import com.openai.models.realtime.ConversationItem;
import com.openai.models.realtime.ConversationItemCreateEvent;
import com.openai.models.realtime.RealtimeAudioConfigInput;
import com.openai.models.realtime.RealtimeAudioFormats;
import com.openai.models.realtime.RealtimeConversationItemUserMessage;
import com.sap.ai.sdk.foundationmodels.openai.TextInputChannel;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class TextToSpeechRealtimeClient extends ToAudioRealtimeClient implements TextInputChannel {

  private static final String SYSTEM_PROMPT =
      "you are a speaker and your role is to read (produce audio) of the user input speech. voice user text input, "
          + "do not answer questions, just read them";

  public TextToSpeechRealtimeClient(
      @Nonnull final String url,
      @Nonnull final Map<String, String> httpHeaders,
      @Nonnull final AudioOutputChannel outputConsumer,
      @Nonnull final RealtimeParam... params) {
    super(
        url,
        httpHeaders,
        outputConsumer,
        true,
        Stream.concat(
                Stream.of((RealtimeParam) new RealtimeParamSystemPrompt(SYSTEM_PROMPT)),
                Stream.of(params))
            .toArray(RealtimeParam[]::new));
  }

  TextToSpeechRealtimeClient(
      @Nonnull final HttpClient httpClient,
      @Nonnull final CompletableFuture<WebSocket> ws,
      @Nonnull final Timer timer,
      @Nonnull final AudioOutputChannel outputConsumer,
      @Nonnull final RealtimeParam... params) {
    super(
        httpClient,
        ws,
        timer,
        outputConsumer,
        true,
        Stream.concat(
                Stream.of((RealtimeParam) new RealtimeParamSystemPrompt(SYSTEM_PROMPT)),
                Stream.of(params))
            .toArray(RealtimeParam[]::new));
  }

  @Override
  @Nonnull
  protected RealtimeAudioConfigInput inputConfig() {
    return RealtimeAudioConfigInput.builder()
        .turnDetection(Optional.empty())
        .format(
            RealtimeAudioFormats.AudioPcm.builder()
                .type(RealtimeAudioFormats.AudioPcm.Type.AUDIO_PCM)
                .rate(RealtimeAudioFormats.AudioPcm.Rate._24000)
                .build())
        .build();
  }

  public void sendText(@Nonnull final String text) {
    final var message =
        ConversationItemCreateEvent.builder()
            .item(
                ConversationItem.ofRealtimeConversationItemUserMessage(
                    RealtimeConversationItemUserMessage.builder()
                        .addContent(
                            RealtimeConversationItemUserMessage.Content.builder()
                                .text(text)
                                .type(RealtimeConversationItemUserMessage.Content.Type.INPUT_TEXT)
                                .build())
                        .build()))
            .build();

    super.sendMessage(message);
    if (eagerTurnDetection) {
      askForResponse();
    }
  }
}
