package com.sap.ai.sdk.orchestration;

/**
 * Represents an item in a {@link MessageContent} object.
 *
 * @since 1.3.0
 */
public sealed interface ContentItem permits TextItem, ImageItem {}
