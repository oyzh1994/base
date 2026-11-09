package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class KeySymMap implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte[] ktIndex;
  private final byte groupInfo;
  private final byte width;
  private final int[] syms;

  public static KeySymMap readKeySymMap(X11Input in) throws IOException {
    KeySymMap.KeySymMapBuilder javaBuilder = KeySymMap.builder();
    byte[] ktIndex = in.readCard8(4);
    byte groupInfo = in.readCard8();
    byte width = in.readCard8();
    short nSyms = in.readCard16();
    int[] syms = in.readCard32(Short.toUnsignedInt(nSyms));
    javaBuilder.ktIndex(ktIndex);
    javaBuilder.groupInfo(groupInfo);
    javaBuilder.width(width);
    javaBuilder.syms(syms);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(ktIndex);
    out.writeCard8(groupInfo);
    out.writeCard8(width);
    short nSyms = (short) syms.length;
    out.writeCard16(nSyms);
    out.writeCard32(syms);
  }

  @Override
  public int getSize() {
    return 4 + 1 * ktIndex.length + 4 * syms.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class KeySymMapBuilder {
    @java.lang.SuppressWarnings("all")
    private byte[] ktIndex;
    @java.lang.SuppressWarnings("all")
    private byte groupInfo;
    @java.lang.SuppressWarnings("all")
    private byte width;
    @java.lang.SuppressWarnings("all")
    private int[] syms;

    public int getSize() {
      return 4 + 1 * ktIndex.length + 4 * syms.length;
    }

    @java.lang.SuppressWarnings("all")
    KeySymMapBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeySymMap.KeySymMapBuilder ktIndex(final byte[] ktIndex) {
      if (ktIndex == null) {
        throw new java.lang.NullPointerException("ktIndex is marked non-null but is null");
      }
      this.ktIndex = ktIndex;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeySymMap.KeySymMapBuilder groupInfo(final byte groupInfo) {
      this.groupInfo = groupInfo;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeySymMap.KeySymMapBuilder width(final byte width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeySymMap.KeySymMapBuilder syms(final int[] syms) {
      if (syms == null) {
        throw new java.lang.NullPointerException("syms is marked non-null but is null");
      }
      this.syms = syms;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public KeySymMap build() {
      return new KeySymMap(this.ktIndex, this.groupInfo, this.width, this.syms);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "KeySymMap.KeySymMapBuilder(ktIndex=" + java.util.Arrays.toString(this.ktIndex) + ", groupInfo=" + this.groupInfo + ", width=" + this.width + ", syms=" + java.util.Arrays.toString(this.syms) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  KeySymMap(final byte[] ktIndex, final byte groupInfo, final byte width, final int[] syms) {
    if (ktIndex == null) {
      throw new java.lang.NullPointerException("ktIndex is marked non-null but is null");
    }
    if (syms == null) {
      throw new java.lang.NullPointerException("syms is marked non-null but is null");
    }
    this.ktIndex = ktIndex;
    this.groupInfo = groupInfo;
    this.width = width;
    this.syms = syms;
  }

  @java.lang.SuppressWarnings("all")
  public static KeySymMap.KeySymMapBuilder builder() {
    return new KeySymMap.KeySymMapBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getKtIndex() {
    return this.ktIndex;
  }

  @java.lang.SuppressWarnings("all")
  public byte getGroupInfo() {
    return this.groupInfo;
  }

  @java.lang.SuppressWarnings("all")
  public byte getWidth() {
    return this.width;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getSyms() {
    return this.syms;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof KeySymMap)) return false;
    final KeySymMap other = (KeySymMap) o;
    if (this.getGroupInfo() != other.getGroupInfo()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (!java.util.Arrays.equals(this.getKtIndex(), other.getKtIndex())) return false;
    if (!java.util.Arrays.equals(this.getSyms(), other.getSyms())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getGroupInfo();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + java.util.Arrays.hashCode(this.getKtIndex());
    result = result * PRIME + java.util.Arrays.hashCode(this.getSyms());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "KeySymMap(ktIndex=" + java.util.Arrays.toString(this.getKtIndex()) + ", groupInfo=" + this.getGroupInfo() + ", width=" + this.getWidth() + ", syms=" + java.util.Arrays.toString(this.getSyms()) + ")";
  }
}
