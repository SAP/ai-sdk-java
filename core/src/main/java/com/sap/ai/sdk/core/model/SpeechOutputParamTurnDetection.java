package com.sap.ai.sdk.core.model;

import org.jspecify.annotations.NonNull;

import java.util.Objects;

public final class SpeechOutputParamTurnDetection implements SpeechOutputParam {

    public static final SpeechOutputParamTurnDetection BY_MODEL_AUTO = new SpeechOutputParamTurnDetection("BY_MODEL_AUTO");
    public static final SpeechOutputParamTurnDetection EACH_CALL_IS_A_TURN =
            new SpeechOutputParamTurnDetection("EACH_CALL_IS_A_TURN");

    private final String value;

    private SpeechOutputParamTurnDetection(String value) {
        this.value = value;
    }

    @Override
    public @NonNull SpeechOutputParamName getParamName() {
        return SpeechOutputParamName.TURN_DETECTION;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SpeechOutputParamTurnDetection that = (SpeechOutputParamTurnDetection) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
