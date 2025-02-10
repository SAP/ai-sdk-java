package com.sap.ai.sdk.foundationmodels.openai;

/**
 * Represents an item in a {@link OpenAiMessageContent} object.
 *
 * @since 1.3.0
 */
public sealed interface OpenAiContentItem permits OpenAiTextItem, OpenAiImageItem {}
