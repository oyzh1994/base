package com.github.moaxcp.x11.protocol;

import org.eclipse.collections.api.list.primitive.ByteList;

final class ParametersCheck {
  /**
   * Checks that a variable is not blank and returns its value.
   * @param name of variable to check
   * @param value of variable to check
   * @return the value of the variable
   * @throws IllegalArgumentException if name or value is blank
   * @throws NullPointerException if name or value is null
   */
  public static String requireNonBlank(String name, String value) {
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    if (value == null) {
      throw new java.lang.NullPointerException("value is marked non-null but is null");
    }
    if (name.trim().isEmpty()) {
      throw new IllegalArgumentException("name must not be blank");
    }
    if (value.trim().isEmpty()) {
      throw new IllegalArgumentException(String.format("%s must not be blank", name));
    }
    return value;
  }

  /**
   * Checks that a variable is not empty and returns its value.
   * @param name of variable to check
   * @param value of variable to check
   * @return the value of the variable
   * @throws IllegalArgumentException if name or value is blank
   * @throws NullPointerException if name or value is null
   */
  public static byte[] requireNonEmpty(String name, byte[] value) {
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    if (value == null) {
      throw new java.lang.NullPointerException("value is marked non-null but is null");
    }
    if (name.trim().isEmpty()) {
      throw new IllegalArgumentException("name must not be blank");
    }
    if (value.length == 0) {
      throw new IllegalArgumentException(String.format("%s must not be empty", name));
    }
    return value;
  }

  /**
   * Checks that a variable is not empty and returns its value.
   * @param name of variable to check
   * @param value of variable to check
   * @return the value of the variable
   * @throws IllegalArgumentException if name is blank or if value is empty
   * @throws NullPointerException if name or value is null
   */
  public static ByteList requireNonEmpty(String name, ByteList value) {
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    if (value == null) {
      throw new java.lang.NullPointerException("value is marked non-null but is null");
    }
    if (name.trim().isEmpty()) {
      throw new IllegalArgumentException("name must not be blank");
    }
    if (value.isEmpty()) {
      throw new IllegalArgumentException(String.format("%s must not be empty", name));
    }
    return value;
  }

  @java.lang.SuppressWarnings("all")
  private ParametersCheck() {
    throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }
}
