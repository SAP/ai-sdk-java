package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.model.UserChatMessage.RoleEnum.USER;
import static com.sap.ai.sdk.orchestration.model.UserChatMessageContentItem.TypeEnum.FILE;
import static com.sap.ai.sdk.orchestration.model.UserChatMessageContentItem.TypeEnum.IMAGE_URL;
import static com.sap.ai.sdk.orchestration.model.UserChatMessageContentItem.TypeEnum.TEXT;

import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.FileContent;
import com.sap.ai.sdk.orchestration.model.ImageContentUrl;
import com.sap.ai.sdk.orchestration.model.UserChatMessage;
import com.sap.ai.sdk.orchestration.model.UserChatMessageContent;
import com.sap.ai.sdk.orchestration.model.UserChatMessageContentItem;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;
import lombok.extern.slf4j.Slf4j;

/** Represents a chat message as 'user' to the orchestration service. */
@Slf4j
@Value
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserMessage implements Message {
  private static final String PDF_DATA_URI_PREFIX = "data:application/pdf;base64,";

  /** The role of the assistant. */
  @Nonnull String role = "user";

  /** The content of the message. */
  @Nonnull @Getter MessageContent content;

  /**
   * Creates a new user message from a string.
   *
   * @param message the first message.
   */
  @Tolerate
  public UserMessage(@Nonnull final String message) {
    this(new MessageContent(List.of(new TextItem(message))));
  }

  /**
   * Add text to the message.
   *
   * @param message the text to add.
   * @return the new message.
   * @since 1.3.0
   */
  @Nonnull
  public UserMessage withText(@Nonnull final String message) {
    final var contentItems = new LinkedList<>(content.items());
    contentItems.add(new TextItem(message));
    return new UserMessage(new MessageContent(contentItems));
  }

  /**
   * Add an image to the message with the given image URL and detail level.
   *
   * @param imageUrl the URL of the image.
   * @param detailLevel the detail level of the image.
   * @return the new message.
   * @since 1.3.0
   */
  @Nonnull
  public UserMessage withImage(
      @Nonnull final String imageUrl, @Nonnull final ImageItem.DetailLevel detailLevel) {
    final var contentItems = new LinkedList<>(content.items());
    contentItems.add(new ImageItem(imageUrl, detailLevel));
    return new UserMessage(new MessageContent(contentItems));
  }

  /**
   * Add an image to the message with the given image URL.
   *
   * @param imageUrl the URL of the image.
   * @return the new message.
   * @since 1.3.0
   */
  @Nonnull
  public UserMessage withImage(@Nonnull final String imageUrl) {
    final var contentItems = new LinkedList<>(content.items());
    contentItems.add(new ImageItem(imageUrl));
    return new UserMessage(new MessageContent(contentItems));
  }

  /**
   * Add a file to the message by reading it from disk.
   *
   * @param filePath the path to a local file.
   * @return the new message.
   * @since 1.18.0
   */
  @Nonnull
  public UserMessage withFile(@Nonnull final Path filePath) {
    final String filename = filePath.getFileName().toString();
    warnNotPdfFile(filename);
    try {
      final String base64Data = Base64.getEncoder().encodeToString(Files.readAllBytes(filePath));
      return appendFileItem(PDF_DATA_URI_PREFIX + base64Data, filename);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to read the file: " + filePath, e);
    }
  }

  /**
   * Add a file to the message by passing its URL.
   *
   * @param fileUrl the URL of the file.
   * @param filename optional name of the file.
   * @return the new message.
   * @since 1.18.0
   */
  @Nonnull
  public UserMessage withFileUrl(@Nonnull final String fileUrl, @Nullable final String filename) {
    warnNotPdfFile(filename);
    return appendFileItem(fileUrl, filename);
  }

  /**
   * Add a file to the message from a base64-encoded payload.
   *
   * @param base64Data base64-encoded payload.
   * @param filename optional name of the file.
   * @return the new message.
   * @since 1.18.0
   */
  @Nonnull
  public UserMessage withFileBase64(
      @Nonnull final String base64Data, @Nullable final String filename) {
    warnNotPdfFile(filename);
    final String payload =
        base64Data.startsWith(PDF_DATA_URI_PREFIX) ? base64Data : PDF_DATA_URI_PREFIX + base64Data;
    try {
      Base64.getDecoder().decode(payload.substring(PDF_DATA_URI_PREFIX.length()));
      return appendFileItem(payload, filename);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid base64 payload for the file.", e);
    }
  }

  @Nonnull
  private UserMessage appendFileItem(
      @Nonnull final String fileData, @Nullable final String filename) {
    final var contentItems = new LinkedList<>(content.items());
    contentItems.add(new FileItem(fileData, filename));
    return new UserMessage(new MessageContent(contentItems));
  }

  private static void warnNotPdfFile(@Nullable final String filename) {
    if (filename != null && !filename.toLowerCase(Locale.ROOT).endsWith(".pdf")) {
      log.warn("Only .pdf files are supported.");
    }
  }

  @Override
  @Nonnull
  public ChatMessage createChatMessage() {
    final var contentList = new LinkedList<UserChatMessageContentItem>();

    if (content.items().size() == 1 && content.items().get(0) instanceof TextItem textItem) {
      return UserChatMessage.create()
          .content(UserChatMessageContent.create(textItem.text()))
          .role(USER);
    }
    for (final ContentItem item : content.items()) {
      if (item instanceof TextItem textItem) {
        contentList.add(UserChatMessageContentItem.create().type(TEXT).text(textItem.text()));
      } else if (item instanceof ImageItem imageItem) {
        final var detail = imageItem.detailLevel().toString();
        final var img = ImageContentUrl.create().url(imageItem.imageUrl()).detail(detail);
        contentList.add(UserChatMessageContentItem.create().type(IMAGE_URL).imageUrl(img));
      } else if (item instanceof FileItem fileItem) {
        final var file =
            FileContent.create().fileData(fileItem.fileData()).filename(fileItem.filename());
        contentList.add(UserChatMessageContentItem.create().type(FILE)._file(file));
      }
    }
    return UserChatMessage.create()
        .content(UserChatMessageContent.createListOfUserChatMessageContentItems(contentList))
        .role(USER);
  }
}
