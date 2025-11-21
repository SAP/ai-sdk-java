package com.sap.ai.sdk.prompt.registry;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import javax.annotation.Nonnull;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

/**
 * A custom {@link HttpMessageConverter} that enables Spring's RestTemplate to read and write {@link
 * java.io.File} objects for {@code application/octet-stream} payloads.
 *
 * @see org.springframework.http.converter.AbstractHttpMessageConverter
 */
class FileHttpMessageConverter extends AbstractHttpMessageConverter<File> {

  FileHttpMessageConverter() {
    super(MediaType.APPLICATION_OCTET_STREAM);
  }

  /**
   * Indicates whether this converter supports the given class.
   *
   * <p>This implementation supports only {@link File}.
   *
   * @param clazz the target class to check
   * @return {@code true} if and only if {@code clazz} is {@link File}
   */
  @Override
  protected boolean supports(@Nonnull final Class clazz) {
    return File.class == clazz;
  }

  /**
   * Reads the {@link HttpInputMessage} body into a new temporary file and returns it.
   *
   * <p>A temporary file is created in the system temp directory and the response body is streamed
   * directly into this file without buffering the entire content in memory.
   *
   * <p>The caller is responsible for deleting the returned file.
   *
   * @param clazz the expected target class (always {@link File}
   * @param inputMessage the HTTP message containing the response body
   * @return a {@link File} containing the streamed response data
   * @throws IOException if file creation or streaming fails
   * @throws HttpMessageNotReadableException if the message cannot be read
   */
  @Nonnull
  @Override
  protected File readInternal(
      @Nonnull final Class<? extends File> clazz, @Nonnull final HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException {
    final var tempFile = File.createTempFile("tmp", ".bin");
    try (OutputStream out = Files.newOutputStream(tempFile.toPath())) {
      StreamUtils.copy(inputMessage.getBody(), out);
    }
    return tempFile;
  }

  /**
   * Writes the contents of a {@link File} into the HTTP request body.
   *
   * <p>The file is streamed directly into the output message, avoiding unnecessary buffering.
   *
   * @param file the file whose contents should be written
   * @param outputMessage the HTTP message whose body should be written to
   * @throws IOException if reading the file or writing the body fails
   * @throws HttpMessageNotWritableException if the message cannot be written
   */
  @Override
  protected void writeInternal(
      @Nonnull final File file, @Nonnull final HttpOutputMessage outputMessage)
      throws IOException, HttpMessageNotWritableException {
    try (InputStream in = Files.newInputStream(file.toPath())) {
      StreamUtils.copy(in, outputMessage.getBody());
    }
  }
}
