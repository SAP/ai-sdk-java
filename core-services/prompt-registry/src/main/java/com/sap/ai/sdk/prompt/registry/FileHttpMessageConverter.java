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
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

class FileHttpMessageConverter extends AbstractHttpMessageConverter<File> {

  FileHttpMessageConverter() {
    super(MediaType.APPLICATION_OCTET_STREAM);
  }

  @Override
  protected boolean supports(@Nonnull final Class clazz) {
    return File.class == clazz;
  }

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

  @Override
  protected void writeInternal(
      @Nonnull final File file, @Nonnull final HttpOutputMessage outputMessage)
      throws IOException, HttpMessageNotWritableException {
    try (InputStream in = Files.newInputStream(file.toPath())) {
      StreamUtils.copy(in, outputMessage.getBody());
    }
  }
}
