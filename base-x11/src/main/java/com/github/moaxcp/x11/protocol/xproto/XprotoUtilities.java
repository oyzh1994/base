package com.github.moaxcp.x11.protocol.xproto;

import org.eclipse.collections.api.list.ImmutableList;
import static org.eclipse.collections.impl.collector.Collectors2.toImmutableList;

public final class XprotoUtilities {
  public static ImmutableList<Char2b> toChar2bString(String string) {
    return string.chars().mapToObj(c -> Char2b.builder().byte1((byte) (c >> 8)).byte2((byte) c).build()).collect(toImmutableList());
  }

  @java.lang.SuppressWarnings("all")
  private XprotoUtilities() {
    throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }
}
