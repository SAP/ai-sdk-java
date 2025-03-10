package com.sap.ai.sdk.orchestration.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.javaparser.JavaParser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.api.Test;

class ImportCheckTest {

  private static final JavaParser PARSER = new JavaParser();

  @Test
  void testSpringAIOptional() throws IOException {
    String springAI = "org.springframework.ai";
    checkImports("com/sap/ai/sdk/orchestration", springAI, false);
    checkImports("com/sap/ai/sdk/orchestration/spring", springAI, true);
  }

  private void checkImports(String packagePath, String imports, boolean shouldContain)
      throws IOException {
    List<File> javaFiles = getJavaFiles(packagePath);

    boolean hasImport = false;
    for (File file : javaFiles) {
      val result = PARSER.parse(file).getResult();
      assertThat(result).isPresent();

      hasImport =
          hasImport
              || result.get().getImports().stream()
                  .anyMatch(importDecl -> importDecl.getNameAsString().startsWith(imports));

      if (!shouldContain) {
        assertFalse(
            hasImport, "File " + file.getName() + " contains a prohibited import: " + imports);
      }
    }

    if (shouldContain) {
      assertTrue(hasImport, "Orchestration Spring should contain Spring AI imports");
    }
  }

  private List<File> getJavaFiles(String packagePath) throws IOException {
    Path orchestrationPackage = Paths.get("src/main/java", packagePath);
    try (Stream<Path> files = Files.list(orchestrationPackage)) {
      return files
          .filter(Files::isRegularFile)
          .map(Path::toFile)
          .filter(file -> file.getName().endsWith(".java"))
          .collect(Collectors.toList());
    }
  }
}
