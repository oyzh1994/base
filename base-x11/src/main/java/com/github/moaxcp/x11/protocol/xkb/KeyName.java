package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class KeyName implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte[] name;

  public static KeyName readKeyName(X11Input in) throws IOException {
    KeyName.KeyNameBuilder javaBuilder = KeyName.builder();
    byte[] name = in.readChar(4);
    javaBuilder.name(name);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeChar(name);
  }

  @Override
  public int getSize() {
    return 0 + 1 * name.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class KeyNameBuilder {
    @java.lang.SuppressWarnings("all")
    private byte[] name;

    public int getSize() {
      return 0 + 1 * name.length;
    }

    @java.lang.SuppressWarnings("all")
    KeyNameBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeyName.KeyNameBuilder name(final byte[] name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public KeyName build() {
      return new KeyName(this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "KeyName.KeyNameBuilder(name=" + java.util.Arrays.toString(this.name) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  KeyName(final byte[] name) {
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static KeyName.KeyNameBuilder builder() {
    return new KeyName.KeyNameBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof KeyName)) return false;
    final KeyName other = (KeyName) o;
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
    return "KeyName(name=" + java.util.Arrays.toString(this.getName()) + ")";
  }
}
