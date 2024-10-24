package com.sap.ai.sdk.core;

import javax.annotation.Nonnull;

/** A simple class to demonstrate the use of the SAP AI SDK. */
public class FooBar {
  /**
   * The main method.
   *
   * @param args The command-line arguments.
   */
  public static void main(final @Nonnull String[] args) {
    System.out.println("Hello, world!");
  }

  /** A simple method to print "Foo". */
  public void foo() {
    System.out.println("Foo");
  }

  /** A simple method to print "Bar". */
  protected static void bar() {
    System.out.println("Bar");
  }
}
