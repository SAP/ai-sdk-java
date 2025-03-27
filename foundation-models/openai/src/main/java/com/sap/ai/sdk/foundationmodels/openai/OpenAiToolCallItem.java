package com.sap.ai.sdk.foundationmodels.openai;

/**
 * Represents a tool call suggested by an OpenAI model.
 *
 * @since 1.6.0
 */
public sealed interface OpenAiToolCallItem extends OpenAiContentItem
    permits OpenAiFunctionCallItem {}
