package com.sap.ai.sdk.orchestration;

public record MultiMessageImageContent(String imageUrl, DetailLevel detailLevel)
    implements MultiMessageContent {
  private static final String type = "image_url";

  public String type() {
    return type;
  }

  public enum DetailLevel {
    low,
    high,
    auto;

    public static DetailLevel fromString(String str) {
      try {
        return DetailLevel.valueOf(str.toLowerCase());
      } catch (IllegalArgumentException e) {
        return DetailLevel.low;
      }
    }
  }
}
