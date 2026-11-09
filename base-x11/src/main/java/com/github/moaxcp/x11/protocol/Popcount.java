package com.github.moaxcp.x11.protocol;

public final class Popcount {
  public static int popcount(int value) {
    return Integer.bitCount(value);
  }

  public static int popcount(long value) {
    return Long.bitCount(value);
  }

  @java.lang.SuppressWarnings("all")
  private Popcount() {
    throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }
}
