package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;

/**
 * Represents a text item in a {@link MessageContent} object.
 *
 * @param text the text of the item
 * @since 1.3.0
 */
public record TextItem(@Nonnull String text) implements ContentItem {}
