package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;

/**
 * Represents a file item in a {@link MessageContent} object.
 *
 * @param fileData base64 encoded file content
 * @param filename name of the file
 * @since 1.18.0
 */
public record FileItem(@Nonnull String fileData, @Nonnull String filename) implements ContentItem {}
