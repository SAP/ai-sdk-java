package com.sap.ai.sdk.foundationmodels.openai.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatAssistantMessage;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatFunctionMessage;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatSystemMessage;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatToolMessage;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatMessage.OpenAiChatUserMessage;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/** OpenAI chat message types. */
@JsonTypeInfo(use = Id.NAME, property = "role", defaultImpl = OpenAiChatAssistantMessage.class)
// role is the field that determines the class type
// if role is missing we default to OpenAiChatAssistantMessage
@JsonSubTypes({
  @Type(value = OpenAiChatSystemMessage.class, name = "system"),
  @Type(value = OpenAiChatUserMessage.class, name = "user"),
  @Type(value = OpenAiChatAssistantMessage.class, name = "assistant"),
  @Type(value = OpenAiChatToolMessage.class, name = "tool"),
  @Type(value = OpenAiChatFunctionMessage.class, name = "function")
})
@Beta
public interface OpenAiChatMessage {
  /**
   * The role of the messages author.
   *
   * @return The role of the messages author.
   */
  @Nonnull
  String getRole();

  /**
   * The contents of the message.
   *
   * @return The contents of the message.
   */
  @Nullable
  Object getContent();

  /** OpenAI system message. */
  @Accessors(chain = true)
  @EqualsAndHashCode
  @ToString
  class OpenAiChatSystemMessage implements OpenAiChatMessage {
    /** The role of the messages author. */
    @Getter(onMethod_ = @Nonnull)
    private final String role = "system";

    /** The contents of the system message. */
    @JsonProperty("content")
    @Getter(onMethod_ = @Nullable)
    @Setter(onParam_ = @Nonnull)
    private String content;
  }

  /** OpenAI user message. */
  @Accessors(chain = true)
  @EqualsAndHashCode
  @ToString
  class OpenAiChatUserMessage implements OpenAiChatMessage {
    /** The role of the messages author. */
    @Getter(onMethod_ = @Nonnull)
    private final String role = "user";

    /** The contents of the user message. */
    @JsonProperty("content")
    @JsonFormat(with = Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @Getter(onMethod_ = @Nullable)
    private final List<ContentPart> content = new ArrayList<>();

    /**
     * Add text to the user message.
     *
     * @param content The text content.
     * @return The user message.
     */
    @Nonnull
    public OpenAiChatUserMessage addText(@Nonnull final String content) {
      this.content.add(new ContentPartText().setText(content));
      return this;
    }

    /**
     * Add an image to the user message.
     *
     * @param content The image URL.
     * @return The user message.
     */
    @Nonnull
    public OpenAiChatUserMessage addImage(@Nonnull final String content) {
      return addImage(content, null);
    }

    /**
     * Add an image to the user message.
     *
     * @param content The image URL.
     * @param detail The detail level of the image.
     * @return The user message.
     */
    @Nonnull
    public OpenAiChatUserMessage addImage(
        @Nonnull final String content, @Nullable final ImageDetailLevel detail) {
      this.content.add(new ContentPartImage().setUrl(content, detail));
      return this;
    }

    /**
     * Add images or text to the user message.
     *
     * @param content The content(s) to add.
     * @return The user message.
     */
    @Nonnull
    public OpenAiChatUserMessage addContent(@Nonnull final ContentPart... content) {
      this.content.addAll(List.of(content));
      return this;
    }

    /** Content part, can be of type `text` or `image_url` when passing in images. */
    @JsonTypeInfo(use = Id.NAME, property = "type", defaultImpl = ContentPartText.class)
    // This is the field that determines the class type
    @JsonSubTypes({
      @Type(value = ContentPartText.class, name = "text"),
      @Type(value = ContentPartImage.class, name = "image_url")
    })
    public interface ContentPart {
      /**
       * Get the type of the content part.
       *
       * @return The type of the content part as string.
       */
      @Nonnull
      String getType();
    }

    /** Content part text input. */
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class ContentPartText implements ContentPart {
      @Getter private final String type = "text";

      /** The text content. */
      @JsonProperty("text")
      @Setter(onParam_ = @Nonnull)
      String text;
    }

    /** Content part image input, only supported when using the `gpt-4-visual-preview` model. */
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class ContentPartImage implements ContentPart {
      @Getter private final String type = "image_url";

      /** Either a URL of the image or the base64 encoded image data. */
      // image_url": { "url": "website.com/picture.jpg" }
      @JsonProperty("image_url")
      @Nonnull
      private ImageUrl url;

      /**
       * Set the URL of the image.
       *
       * @param url The URL of the image.
       * @return The image URL.
       */
      @Nonnull
      public ContentPartImage setUrl(@Nonnull final String url) {
        return setUrl(url, null);
      }

      /**
       * Set the URL of the image.
       *
       * @param url The URL of the image.
       * @param detailLevel The detail level of the image.
       * @return The image URL.
       */
      @Nonnull
      public ContentPartImage setUrl(
          @Nonnull final String url, @Nullable final ImageDetailLevel detailLevel) {
        this.url = new ImageUrl(url, detailLevel);
        return this;
      }

      /** Image URL. */
      // { "url": "website.com/picture.jpg" }
      @JsonFormat(shape = Shape.OBJECT)
      @NoArgsConstructor
      @AllArgsConstructor
      @EqualsAndHashCode
      public static class ImageUrl {
        /** Specifies the url of the image. */
        @JsonProperty("url")
        String url;

        /** Specifies the detail level of the image. */
        @JsonProperty("detail")
        @Setter(onParam_ = @Nullable)
        ImageDetailLevel detail;
      }
    }

    /** Specifies the detail level of the image. */
    @RequiredArgsConstructor
    public enum ImageDetailLevel {
      /** The image detail level is auto. */
      AUTO("auto"),
      /** The image detail level is low. */
      LOW("low"),
      /** The image detail level is high. */
      HIGH("high");

      @Nonnull @JsonValue private final String value;
    }
  }

  /** OpenAI assistant message. */
  @Accessors(chain = true)
  @EqualsAndHashCode
  @ToString
  class OpenAiChatAssistantMessage implements OpenAiChatMessage {
    /** The role of the messages author. */
    @Getter(onMethod_ = @Nonnull)
    private final String role = "assistant";

    /** Message content. */
    @JsonProperty("content")
    @Getter(onMethod_ = @Nullable)
    @Setter(onParam_ = @Nullable, value = AccessLevel.PACKAGE)
    private String content;

    /** The tool calls generated by the model, such as function calls. */
    @JsonProperty("tool_calls")
    @Getter(onMethod_ = @Nullable)
    private List<OpenAiChatToolCall> toolCalls;

    // TODO: add context
    // https://github.com/Azure/azure-rest-api-specs/blob/07d286359f828bbc7901e86288a5d62b48ae2052/specification/cognitiveservices/data-plane/AzureOpenAI/inference/stable/2024-02-01/inference.json#L1599

    void addDelta(@Nonnull final OpenAiChatAssistantMessage delta) {

      if (delta.getContent() != null) {
        if (content == null) {
          content = "";
        }
        content += delta.getContent();
      }

      if (delta.getToolCalls() != null) {
        if (toolCalls == null) {
          toolCalls = new ArrayList<>();
        }
        toolCalls.addAll(delta.getToolCalls());
      }
    }
  }

  /** OpenAI tool message. */
  @Accessors(chain = true)
  @EqualsAndHashCode
  @ToString
  class OpenAiChatToolMessage implements OpenAiChatMessage {
    /** The role of the messages author. */
    @Getter(onMethod_ = @Nonnull)
    private final String role = "tool";

    /** The contents of the tool message. */
    @JsonProperty("content")
    @Getter(onMethod_ = @Nullable)
    @Setter(onParam_ = @Nonnull)
    private String content; // must be String or null

    /** Tool call that this message is responding to. */
    @JsonProperty("tool_call_id")
    @Setter(onParam_ = @Nonnull)
    private String toolCallId;
  }

  /** OpenAI function message. */
  @Accessors(chain = true)
  @EqualsAndHashCode
  @ToString
  class OpenAiChatFunctionMessage implements OpenAiChatMessage {
    /** The role of the messages author. */
    @Getter(onMethod_ = @Nonnull)
    private final String role = "function";

    /** The contents of the function message. */
    @JsonProperty("content")
    @Getter(onMethod_ = @Nullable)
    @Setter(onParam_ = @Nonnull)
    private String content; // must be String or null

    /** The name of the function to call. */
    @JsonProperty("name")
    @Getter(onMethod_ = @Nullable)
    @Setter(onParam_ = @Nullable)
    private String name;
  }
}
