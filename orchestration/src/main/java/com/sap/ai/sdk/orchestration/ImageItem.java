package com.sap.ai.sdk.orchestration;

/** Represents an image item in a {@link MessageContent} object. */
public record ImageItem(String imageUrl, DetailLevel detailLevel) implements ContentItem {
  /**
   * Creates a new image item with the given image URL.
   *
   * @param imageUrl the URL of the image
   */
  public ImageItem(String imageUrl) {
    this(imageUrl, DetailLevel.auto);
  }

  /** The detail level of the image. */
  public enum DetailLevel {
    low,
    high,
    auto;

    /**
     * Converts a string to a detail level.
     *
     * @param str the string to convert
     * @return the detail level
     */
    static DetailLevel fromString(String str) {
      try {
        return DetailLevel.valueOf(str.toLowerCase());
      } catch (IllegalArgumentException e) {
        return DetailLevel.low;
      }
    }
  }
}
