package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;

/**
 * Represents an item in a {@link OpenAiMessageContent} object.
 *
 * @since 1.4.0
 */
@Beta
public sealed interface OpenAiContentItem permits OpenAiTextItem, OpenAiImageItem {}
