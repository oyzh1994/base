package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class KeyAlias implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte[] real;
  private final byte[] alias;

  public static KeyAlias readKeyAlias(X11Input in) throws IOException {
    KeyAlias.KeyAliasBuilder javaBuilder = KeyAlias.builder();
    byte[] real = in.readChar(4);
    byte[] alias = in.readChar(4);
    javaBuilder.real(real);
    javaBuilder.alias(alias);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeChar(real);
    out.writeChar(alias);
  }

  @Override
  public int getSize() {
    return 0 + 1 * real.length + 1 * alias.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class KeyAliasBuilder {
    @java.lang.SuppressWarnings("all")
    private byte[] real;
    @java.lang.SuppressWarnings("all")
    private byte[] alias;

    public int getSize() {
      return 0 + 1 * real.length + 1 * alias.length;
    }

    @java.lang.SuppressWarnings("all")
    KeyAliasBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeyAlias.KeyAliasBuilder real(final byte[] real) {
      if (real == null) {
        throw new java.lang.NullPointerException("real is marked non-null but is null");
      }
      this.real = real;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeyAlias.KeyAliasBuilder alias(final byte[] alias) {
      if (alias == null) {
        throw new java.lang.NullPointerException("alias is marked non-null but is null");
      }
      this.alias = alias;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public KeyAlias build() {
      return new KeyAlias(this.real, this.alias);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "KeyAlias.KeyAliasBuilder(real=" + java.util.Arrays.toString(this.real) + ", alias=" + java.util.Arrays.toString(this.alias) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  KeyAlias(final byte[] real, final byte[] alias) {
    if (real == null) {
      throw new java.lang.NullPointerException("real is marked non-null but is null");
    }
    if (alias == null) {
      throw new java.lang.NullPointerException("alias is marked non-null but is null");
    }
    this.real = real;
    this.alias = alias;
  }

  @java.lang.SuppressWarnings("all")
  public static KeyAlias.KeyAliasBuilder builder() {
    return new KeyAlias.KeyAliasBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getReal() {
    return this.real;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getAlias() {
    return this.alias;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof KeyAlias)) return false;
    final KeyAlias other = (KeyAlias) o;
    if (!java.util.Arrays.equals(this.getReal(), other.getReal())) return false;
    if (!java.util.Arrays.equals(this.getAlias(), other.getAlias())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + java.util.Arrays.hashCode(this.getReal());
    result = result * PRIME + java.util.Arrays.hashCode(this.getAlias());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "KeyAlias(real=" + java.util.Arrays.toString(this.getReal()) + ", alias=" + java.util.Arrays.toString(this.getAlias()) + ")";
  }
}
