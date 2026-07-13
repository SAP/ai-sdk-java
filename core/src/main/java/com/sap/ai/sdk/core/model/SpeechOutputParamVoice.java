package com.sap.ai.sdk.core.model;

import org.jspecify.annotations.NonNull;

import javax.annotation.Nonnull;
import java.util.Objects;

public final class SpeechOutputParamVoice implements SpeechOutputParam {

    public static final SpeechOutputParamVoice DEFAULT_MAN = new SpeechOutputParamVoice("DEFAULT_MAN");
    public static final SpeechOutputParamVoice DEFAULT_WOMAN = new SpeechOutputParamVoice("DEFAULT_WOMAN");

    private final String voice;

    private SpeechOutputParamVoice(@Nonnull String voice) {
        this.voice = voice;
    }

    @Override
    public @NonNull SpeechOutputParamName getParamName() {
        return SpeechOutputParamName.VOICE;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SpeechOutputParamVoice that = (SpeechOutputParamVoice) o;
        return Objects.equals(voice, that.voice);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(voice);
    }
}
