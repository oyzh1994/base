package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class StoreNamedColor implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 90;
  private final byte flags;
  private final int cmap;
  private final int pixel;
  private final ByteList name;

  public byte getOpCode() {
    return OPCODE;
  }

  public static StoreNamedColor readStoreNamedColor(X11Input in) throws IOException {
    StoreNamedColor.StoreNamedColorBuilder javaBuilder = StoreNamedColor.builder();
    byte flags = in.readCard8();
    short length = in.readCard16();
    int cmap = in.readCard32();
    int pixel = in.readCard32();
    short nameLen = in.readCard16();
    byte[] pad6 = in.readPad(2);
    ByteList name = in.readChar(Short.toUnsignedInt(nameLen));
    javaBuilder.flags(flags);
    javaBuilder.cmap(cmap);
    javaBuilder.pixel(pixel);
    javaBuilder.name(name.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard8(flags);
    out.writeCard16((short) getLength());
    out.writeCard32(cmap);
    out.writeCard32(pixel);
    short nameLen = (short) name.size();
    out.writeCard16(nameLen);
    out.writePad(2);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  public boolean isFlagsEnabled(ColorFlag... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ColorFlag m : maskEnums) {
      if (!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 16 + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class StoreNamedColorBuilder {
    @java.lang.SuppressWarnings("all")
    private byte flags;
    @java.lang.SuppressWarnings("all")
    private int cmap;
    @java.lang.SuppressWarnings("all")
    private int pixel;
    @java.lang.SuppressWarnings("all")
    private ByteList name;

    public boolean isFlagsEnabled(ColorFlag... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ColorFlag m : maskEnums) {
        if (!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public StoreNamedColor.StoreNamedColorBuilder flagsEnable(ColorFlag... maskEnums) {
      for (ColorFlag m : maskEnums) {
        flags((byte) m.enableFor(flags));
      }
      return this;
    }

    public StoreNamedColor.StoreNamedColorBuilder flagsDisable(ColorFlag... maskEnums) {
      for (ColorFlag m : maskEnums) {
        flags((byte) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 16 + 1 * name.size();
    }

    @java.lang.SuppressWarnings("all")
    StoreNamedColorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StoreNamedColor.StoreNamedColorBuilder flags(final byte flags) {
      this.flags = flags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StoreNamedColor.StoreNamedColorBuilder cmap(final int cmap) {
      this.cmap = cmap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StoreNamedColor.StoreNamedColorBuilder pixel(final int pixel) {
      this.pixel = pixel;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StoreNamedColor.StoreNamedColorBuilder name(final ByteList name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public StoreNamedColor build() {
      return new StoreNamedColor(this.flags, this.cmap, this.pixel, this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "StoreNamedColor.StoreNamedColorBuilder(flags=" + this.flags + ", cmap=" + this.cmap + ", pixel=" + this.pixel + ", name=" + this.name + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  StoreNamedColor(final byte flags, final int cmap, final int pixel, final ByteList name) {
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.flags = flags;
    this.cmap = cmap;
    this.pixel = pixel;
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static StoreNamedColor.StoreNamedColorBuilder builder() {
    return new StoreNamedColor.StoreNamedColorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getFlags() {
    return this.flags;
  }

  @java.lang.SuppressWarnings("all")
  public int getCmap() {
    return this.cmap;
  }

  @java.lang.SuppressWarnings("all")
  public int getPixel() {
    return this.pixel;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof StoreNamedColor)) return false;
    final StoreNamedColor other = (StoreNamedColor) o;
    if (this.getFlags() != other.getFlags()) return false;
    if (this.getCmap() != other.getCmap()) return false;
    if (this.getPixel() != other.getPixel()) return false;
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
    result = result * PRIME + this.getFlags();
    result = result * PRIME + this.getCmap();
    result = result * PRIME + this.getPixel();
    final java.lang.Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "StoreNamedColor(flags=" + this.getFlags() + ", cmap=" + this.getCmap() + ", pixel=" + this.getPixel() + ", name=" + this.getName() + ")";
  }
}
