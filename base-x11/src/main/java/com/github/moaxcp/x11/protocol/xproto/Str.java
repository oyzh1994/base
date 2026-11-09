package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Str implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final byte[] name;

  public static Str readStr(X11Input in) throws IOException {
    Str.StrBuilder javaBuilder = Str.builder();
    byte nameLen = in.readCard8();
    byte[] name = in.readChar(Byte.toUnsignedInt(nameLen));
    javaBuilder.name(name);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    byte nameLen = (byte) name.length;
    out.writeCard8(nameLen);
    out.writeChar(name);
  }

  @Override
  public int getSize() {
    return 1 + 1 * name.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class StrBuilder {
    @java.lang.SuppressWarnings("all")
    private byte[] name;

    public int getSize() {
      return 1 + 1 * name.length;
    }

    @java.lang.SuppressWarnings("all")
    StrBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Str.StrBuilder name(final byte[] name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Str build() {
      return new Str(this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Str.StrBuilder(name=" + java.util.Arrays.toString(this.name) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Str(final byte[] name) {
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static Str.StrBuilder builder() {
    return new Str.StrBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Str)) return false;
    final Str other = (Str) o;
    if (!java.util.Arrays.equals(this.getName(), other.getName())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + java.util.Arrays.hashCode(this.getName());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Str(name=" + java.util.Arrays.toString(this.getName()) + ")";
  }
}
