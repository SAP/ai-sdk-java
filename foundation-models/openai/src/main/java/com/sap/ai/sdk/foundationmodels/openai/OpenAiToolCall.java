package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;

/**
 * Represents a tool call suggested by an OpenAI model.
 *
 * @since 1.6.0
 */
@Beta
public sealed interface OpenAiToolCall permits OpenAiFunctionCall {}
