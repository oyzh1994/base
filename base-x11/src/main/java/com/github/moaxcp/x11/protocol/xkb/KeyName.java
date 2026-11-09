package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class KeyName implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final ByteList name;

  public static KeyName readKeyName(X11Input in) throws IOException {
    KeyName.KeyNameBuilder javaBuilder = KeyName.builder();
    ByteList name = in.readChar(4);
    javaBuilder.name(name.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeChar(name);
  }

  @Override
  public int getSize() {
    return 0 + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class KeyNameBuilder {
    @java.lang.SuppressWarnings("all")
    private ByteList name;

    public int getSize() {
      return 0 + 1 * name.size();
    }

    @java.lang.SuppressWarnings("all")
    KeyNameBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeyName.KeyNameBuilder name(final ByteList name) {
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
      return "KeyName.KeyNameBuilder(name=" + this.name + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  KeyName(final ByteList name) {
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
  public ByteList getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof KeyName)) return false;
    final KeyName other = (KeyName) o;
    final java.lang.Object this$name = this.getName();
    final java.lang.Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "KeyName(name=" + this.getName() + ")";
  }
}
