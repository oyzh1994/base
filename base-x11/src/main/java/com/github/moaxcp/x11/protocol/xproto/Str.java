package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class Str implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final ByteList name;

  public static Str readStr(X11Input in) throws IOException {
    Str.StrBuilder javaBuilder = Str.builder();
    byte nameLen = in.readCard8();
    ByteList name = in.readChar(Byte.toUnsignedInt(nameLen));
    javaBuilder.name(name.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    byte nameLen = (byte) name.size();
    out.writeCard8(nameLen);
    out.writeChar(name);
  }

  @Override
  public int getSize() {
    return 1 + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class StrBuilder {
    @java.lang.SuppressWarnings("all")
    private ByteList name;

    public int getSize() {
      return 1 + 1 * name.size();
    }

    @java.lang.SuppressWarnings("all")
    StrBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Str.StrBuilder name(final ByteList name) {
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
      return "Str.StrBuilder(name=" + this.name + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Str(final ByteList name) {
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
  public ByteList getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Str)) return false;
    final Str other = (Str) o;
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
    return "Str(name=" + this.getName() + ")";
  }
}
