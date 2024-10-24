package com.sap.ai.sdk.core;

import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;

/** A simple class to demonstrate the use of the SAP AI SDK. */
@Slf4j
public class FooBar {
  /**
   * The main method.
   *
   * @param args The command-line arguments.
   */
  public static void main(final @Nonnull String[] args) {
    log.info("Hello, world!");
  }

  /** A simple method to print "Foo". */
  public void foo() {
    log.info("Foo");
  }

  /** A simple method to print "Bar". */
  protected static void bar() {
    log.info("Bar");
  }
}
