package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;

/**
 * Represents a PDF file item in a {@link MessageContent} object.
 *
 * @param fileData base64 encoded PDF file content
 * @param filename name of the PDF file
 * @since 1.18.0
 */
public record PdfItem(@Nonnull String fileData, @Nonnull String filename) implements ContentItem {}
