package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents an image item in a {@link MessageContent} object.
 *
 * @param imageUrl the URL of the image
 * @param detailLevel the detail level of the image (optional
 */
public record ImageItem(@Nonnull String imageUrl, @Nullable DetailLevel detailLevel)
    implements ContentItem {

  /**
   * Creates a new image item with the given image URL.
   *
   * @param imageUrl the URL of the image
   */
  public ImageItem(@Nonnull String imageUrl) {
    this(imageUrl, DetailLevel.auto);
  }

  /** The detail level of the image. */
  public enum DetailLevel {
    /** Low detail level. */
    low,
    /** High detail level. */
    high,
    /** Automatic detail level. */
    auto;

    /**
     * Converts a string to a detail level.
     *
     * @param str the string to convert
     * @return the detail level
     */
    @Nonnull
    static DetailLevel fromString(@Nonnull String str) {
      try {
        return DetailLevel.valueOf(str.toLowerCase());
      } catch (IllegalArgumentException e) {
        return DetailLevel.auto;
      }
    }
  }
}
