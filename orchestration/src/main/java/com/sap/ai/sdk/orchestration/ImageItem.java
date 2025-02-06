package com.sap.ai.sdk.orchestration;

import java.util.Locale;
import javax.annotation.Nonnull;

/**
 * Represents an image item in a {@link MessageContent} object.
 *
 * @param imageUrl the URL of the image
 * @param detailLevel the detail level of the image (optional
 */
public record ImageItem(@Nonnull String imageUrl, @Nonnull DetailLevel detailLevel)
    implements ContentItem {

  /**
   * Creates a new image item with the given image URL.
   *
   * @param imageUrl the URL of the image
   */
  public ImageItem(@Nonnull final String imageUrl) {
    this(imageUrl, DetailLevel.AUTO);
  }

  /** The detail level of the image. */
  public enum DetailLevel {
    /** Low detail level. */
    LOW("low"),
    /** High detail level. */
    HIGH("high"),
    /** Automatic detail level. */
    AUTO("auto");

    private final String level;

    /**
     * Converts a string to a detail level.
     *
     * @param str the string to convert
     * @return the detail level
     */
    @Nonnull
    static DetailLevel fromString(@Nonnull final String str) {
      return DetailLevel.valueOf(str.toUpperCase(Locale.ENGLISH));
    }

    /**
     * Get the string representation of the DetailLevel
     *
     * @return the DetailLevel as string
     */
    @Nonnull
    public String toString() {
      return level;
    }

    DetailLevel(@Nonnull final String level) {
      this.level = level;
    }
  }
}
