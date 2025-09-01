package com.sap.ai.sdk.foundationmodels.openai;

import java.util.List;
import javax.annotation.Nonnull;

/**
 * Represents the content of a chat message.
 *
 * @param items a list of the content items
 * @since 1.4.0
 */
public record OpenAiMessageContent(@Nonnull List<OpenAiContentItem> items) {}
