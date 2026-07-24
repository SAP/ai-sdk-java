package com.sap.ai.sdk.orchestration;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a text item in a {@link MessageContent} object.
 *
 * @since 1.3.0
 */
@Getter
@ToString
public final class TextItem implements ContentItem, CacheablePrompt {

  private final String text;
  private final CacheControl cacheControl;

  public TextItem(@Nonnull final String text, @Nullable final CacheControl cacheControl) {
    this.text = text;
    this.cacheControl = cacheControl;
  }

  /**
   * Compatibility constructor conforming with the previous API to avoid breaking changes
   *
   * @param text value of the item
   */
  public TextItem(@Nonnull final String text) {
    this(text, null);
  }

  /**
   * Compatibility method to support conversion from record to class without breaking changes, the
   * same as {@link #getText()}
   *
   * @return text
   */
  public String text() {
    return text;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    TextItem textItem = (TextItem) o;
    return Objects.equals(text, textItem.text)
        && Objects.equals(cacheControl, textItem.cacheControl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, cacheControl);
  }
}
