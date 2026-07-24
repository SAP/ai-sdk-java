package com.sap.ai.sdk.foundationmodels.openai.realtime;

import com.openai.models.realtime.ConversationItem;
import com.openai.models.realtime.ConversationItemCreateEvent;
import com.openai.models.realtime.RealtimeAudioConfigInput;
import com.openai.models.realtime.RealtimeAudioFormats;
import com.openai.models.realtime.RealtimeConversationItemUserMessage;
import com.sap.ai.sdk.core.RealtimeParam;
import com.sap.ai.sdk.core.RealtimeParamTurnDetection;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;

import com.sap.ai.sdk.foundationmodels.openai.AudioOutputChannel;
import com.sap.ai.sdk.foundationmodels.openai.TextInputChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class TextToSpeechRealtimeClient extends ToAudioRealtimeClient implements TextInputChannel {

  private static final String TASK =
      "you are a speaker and your role is to read (produce audio) of the user input speech. voice user text input, "
          + "do not answer questions, just read them";

  private final boolean eagerTurnDetection;

  public TextToSpeechRealtimeClient(
      @Nonnull final String url,
      @Nonnull final Map<String, String> httpHeaders,
      @Nonnull final AudioOutputChannel outputConsumer,
      @Nonnull final RealtimeParam... params) {
    super(url, httpHeaders, outputConsumer, params);
    var turnDetectionEager = true;
    for (RealtimeParam param : params) {
      if (param.getParamName() == RealtimeParam.SpeechOutputParamName.TURN_DETECTION) {
        if (RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN.equals(param)) {
          turnDetectionEager = true;
        } else if (RealtimeParamTurnDetection.BY_MODEL_AUTO.equals(param)) {
          turnDetectionEager = false;
        }
      }
    }
    this.eagerTurnDetection = turnDetectionEager;
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

  @Override
  @Nonnull
  protected String getSystemPrompt() {
    return TASK;
  }
}
