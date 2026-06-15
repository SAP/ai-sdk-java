package com.sap.ai.sdk.architecture;

import javax.annotation.Nonnull;
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

  @Override
  public void execute() throws MojoFailureException {
    try {
      ArchRule.check(basePackage);
    } catch (final AssertionError error) {
      throw new MojoFailureException(error.getMessage(), error);
    }
  }

  /**
   * Returns the configured base package for architecture checks.
   *
   * @return The package prefix to analyze.
   */
  @Nonnull
  String getBasePackage() {
    return basePackage;
  }
}
