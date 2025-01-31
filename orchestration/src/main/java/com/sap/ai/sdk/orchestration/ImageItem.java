package com.sap.ai.sdk.orchestration;

public record ImageItem(String imageUrl, DetailLevel detailLevel) implements ContentItem {
  public ImageItem(String imageUrl) {
    this(imageUrl, DetailLevel.auto);
  }

  public enum DetailLevel {
    low,
    high,
    auto;

    static DetailLevel fromString(String str) {
      try {
        return DetailLevel.valueOf(str.toLowerCase());
      } catch (IllegalArgumentException e) {
        return DetailLevel.low;
      }
    }
  }
}
