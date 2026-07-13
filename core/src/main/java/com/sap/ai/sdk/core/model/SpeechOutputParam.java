package com.sap.ai.sdk.core.model;

import javax.annotation.Nonnull;

public interface SpeechOutputParam {
    enum SpeechOutputParamName {
        VOICE,
        TURN_DETECTION,
    }

    @Nonnull
    SpeechOutputParamName getParamName();

}
