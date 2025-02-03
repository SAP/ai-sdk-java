package com.sap.ai.sdk.orchestration;

/**
 * Represents a text item in a {@link MessageContent} object.
 *
 * @param text the text of the item
 */
public record TextItem(String text) implements ContentItem {}
