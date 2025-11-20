package com.sap.ai.sdk.prompt.registry;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.Nonnull;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;

class FileHttpMessageConverterTest {

  @Test
  void testSupports() {
    final var converter = new FileHttpMessageConverter();

    assertThat(converter.supports(File.class)).isTrue();
    assertThat(converter.supports(String.class)).isFalse();
  }

  @Test
  @Description("Test conversion from HttpInputMessage to File")
  void testReadInternal() throws IOException {
    final var converter = new FileHttpMessageConverter();
    final var messageContent = "Hello, World!".getBytes();

    final var inputMessage =
        new HttpInputMessage() {
          @Nonnull
          @Override
          public InputStream getBody() {
            return new ByteArrayInputStream(messageContent);
          }

          @Nonnull
          @Override
          public HttpHeaders getHeaders() {
            return new HttpHeaders();
          }
        };

    final var generatedFile = converter.readInternal(File.class, inputMessage);
    try {
      assertThat(generatedFile).exists().isFile();
      assertThat(Files.readAllBytes(generatedFile.toPath())).isEqualTo(messageContent);
    } finally {
      Files.deleteIfExists(generatedFile.toPath());
    }
  }

  @Test
  @Description("Test conversion from File to HttpOutputMessage")
  void testWriteInternal(@TempDir final Path tempDir) throws IOException {
    final var converter = new FileHttpMessageConverter();
    final var fileContent = "Hello, World!".getBytes();
    final var tempFilePath = tempDir.resolve("testFile.txt");
    Files.write(tempFilePath, fileContent);

    final var outputStream = new ByteArrayOutputStream();
    final var outputMessage =
        new HttpOutputMessage() {

          @Nonnull
          @Override
          public HttpHeaders getHeaders() {
            return new HttpHeaders();
          }

          @Nonnull
          @Override
          public OutputStream getBody() {
            return outputStream;
          }
        };

    converter.writeInternal(tempFilePath.toFile(), outputMessage);
    assertThat(outputStream.toByteArray()).isEqualTo(fileContent);
  }
}
