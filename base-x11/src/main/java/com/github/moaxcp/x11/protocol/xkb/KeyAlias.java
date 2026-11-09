package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class KeyAlias implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final ByteList real;
  private final ByteList alias;

  public static KeyAlias readKeyAlias(X11Input in) throws IOException {
    KeyAlias.KeyAliasBuilder javaBuilder = KeyAlias.builder();
    ByteList real = in.readChar(4);
    ByteList alias = in.readChar(4);
    javaBuilder.real(real.toImmutable());
    javaBuilder.alias(alias.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeChar(real);
    out.writeChar(alias);
  }

  @Override
  public int getSize() {
    return 0 + 1 * real.size() + 1 * alias.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class KeyAliasBuilder {
    @java.lang.SuppressWarnings("all")
    private ByteList real;
    @java.lang.SuppressWarnings("all")
    private ByteList alias;

    public int getSize() {
      return 0 + 1 * real.size() + 1 * alias.size();
    }

    @java.lang.SuppressWarnings("all")
    KeyAliasBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeyAlias.KeyAliasBuilder real(final ByteList real) {
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
    public KeyAlias.KeyAliasBuilder alias(final ByteList alias) {
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
      return "KeyAlias.KeyAliasBuilder(real=" + this.real + ", alias=" + this.alias + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  KeyAlias(final ByteList real, final ByteList alias) {
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
  public ByteList getReal() {
    return this.real;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getAlias() {
    return this.alias;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof KeyAlias)) return false;
    final KeyAlias other = (KeyAlias) o;
    final java.lang.Object this$real = this.getReal();
    final java.lang.Object other$real = other.getReal();
    if (this$real == null ? other$real != null : !this$real.equals(other$real)) return false;
    final java.lang.Object this$alias = this.getAlias();
    final java.lang.Object other$alias = other.getAlias();
    if (this$alias == null ? other$alias != null : !this$alias.equals(other$alias)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $real = this.getReal();
    result = result * PRIME + ($real == null ? 43 : $real.hashCode());
    final java.lang.Object $alias = this.getAlias();
    result = result * PRIME + ($alias == null ? 43 : $alias.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "KeyAlias(real=" + this.getReal() + ", alias=" + this.getAlias() + ")";
  }
}
