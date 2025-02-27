package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;

/**
 * Represents a text item in a {@link OpenAiMessageContent} object.
 *
 * @param text the text of the item
 * @since 1.4.0
 */
@Beta
public record OpenAiTextItem(@Nonnull String text) implements OpenAiContentItem {}
