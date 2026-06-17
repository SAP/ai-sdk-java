package com.sap.ai.sdk.architecture;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import lombok.Data;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/** Generic Maven plugin adapter that delegates to shared architecture rule classes. */
@Mojo(
    name = "check-model-inheritance",
    defaultPhase = LifecyclePhase.PROCESS_CLASSES,
    threadSafe = true)
public final class ArchitectureRulesMojo extends AbstractMojo {

  @Parameter(
      property = "model.inheritance.basePackage",
      defaultValue = "${project.groupId}",
      required = true)
  private String basePackage;

  @Parameter @Nonnull private final Suppressions suppressions = new Suppressions();

  @Override
  public void execute() throws MojoFailureException {
    try {
      ArchRule.check(basePackage, suppressions);
    } catch (final AssertionError error) {
      throw new MojoFailureException(error.getMessage(), error);
    }
  }

  /** Maven configuration bean for ignored architecture-rule violations. */
  @Data
  public static final class Suppressions {
    @Parameter @Nonnull private Set<String> inheritModel = new HashSet<>();
  }
}
