package com.sap.ai.sdk.orchestration;

/** Represents an item in a {@link MessageContent} object. */
public sealed interface ContentItem permits TextItem, ImageItem {}
