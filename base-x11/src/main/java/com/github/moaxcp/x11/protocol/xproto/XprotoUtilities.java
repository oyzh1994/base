package com.github.moaxcp.x11.protocol.xproto;

import java.util.List;
import java.util.stream.Collectors;

public final class XprotoUtilities {
  public static List<Char2b> toChar2bString(String string) {
    return string.chars().mapToObj(c -> Char2b.builder().byte1((byte) (c >> 8)).byte2((byte) c).build()).collect(Collectors.toUnmodifiableList());
  }

  @java.lang.SuppressWarnings("all")
  private XprotoUtilities() {
    throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }
}
