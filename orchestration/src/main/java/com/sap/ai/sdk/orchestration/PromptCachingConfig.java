package com.sap.ai.sdk.orchestration;

import lombok.Getter;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Describes supported caching properties of a model
 */
@Getter
public final class PromptCachingConfig {

    private static final PromptCachingConfig NOT_SUPPORTED =
        new PromptCachingConfig(0, 0, "0m", "5m");

    private static final Map<String, PromptCachingConfig> PROMPT_CACHING_SUPPORT = Collections.unmodifiableMap(new HashMap<>(){{
        put(OrchestrationAiModel.CLAUDE_4_5_OPUS.getName(), new PromptCachingConfig(4096, 4, "5m|1h", "5m"));
        put(OrchestrationAiModel.CLAUDE_4_6_OPUS.getName(), new PromptCachingConfig(4096, 4, "5m|1h", "5m"));
        put(OrchestrationAiModel.CLAUDE_4_5_SONNET.getName(), new PromptCachingConfig(4096, 4, "5m|1h", "5m"));
        put(OrchestrationAiModel.CLAUDE_4_6_SONNET.getName(), new PromptCachingConfig(1024, 4, "5m|1h", "5m"));
        put(OrchestrationAiModel.CLAUDE_4_5_HAIKU.getName(), new PromptCachingConfig(4096, 4, "5m|1h", "5m"));
        put(OrchestrationAiModel.CLAUDE_4_OPUS.getName(), new PromptCachingConfig(4096, 4, "5m", "5m"));
        put(OrchestrationAiModel.CLAUDE_4_7_OPUS.getName(), new PromptCachingConfig(4096, 4, "5m|1h", "5m"));
        put(OrchestrationAiModel.CLAUDE_4_8_OPUS.getName(), new PromptCachingConfig(4096, 4, "5m|1h", "5m"));
    }});


    /**
     * Caching checkpoint can only be made for so few prompt input tokens and not fewer
     */
    private final int minTokensPerCheckpoint;

    /**
     * Only up to this number of caching points can be created per request
     */
    private final int maxCheckpointsPerRequest;

    /**
     * Pattern of supported TTL values, which can be passed
     */
    private final Pattern supportedTTLValues;

    /**
     * Caching TTL value to use if TTL has not been explicitly specified
     */
    private final String defaultTTLValue;

    private PromptCachingConfig(
            int minTokensPerCheckpoint,
            int maxCheckpointsPerRequest,
            String ttlPattern,
            String defaultTTLValue
    ){
        this.minTokensPerCheckpoint = minTokensPerCheckpoint;
        this.maxCheckpointsPerRequest = maxCheckpointsPerRequest;
        this.supportedTTLValues = Pattern.compile(ttlPattern);
        this.defaultTTLValue = defaultTTLValue;
    }

    @Nonnull
    public static PromptCachingConfig forModel(String modelName) {
        if (modelName == null || modelName.isEmpty()) {
            return NOT_SUPPORTED;
        }
        return PROMPT_CACHING_SUPPORT.getOrDefault(modelName, NOT_SUPPORTED);
    }

    public static PromptCachingConfig noCaching() {
        return NOT_SUPPORTED;
    }

}
