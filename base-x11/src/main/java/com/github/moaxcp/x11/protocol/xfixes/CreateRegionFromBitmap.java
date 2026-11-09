package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CreateRegionFromBitmap implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 6;
  private final int region;
  private final int bitmap;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateRegionFromBitmap readCreateRegionFromBitmap(X11Input in) throws IOException {
    CreateRegionFromBitmap.CreateRegionFromBitmapBuilder javaBuilder = CreateRegionFromBitmap.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int region = in.readCard32();
    int bitmap = in.readCard32();
    javaBuilder.region(region);
    javaBuilder.bitmap(bitmap);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(region);
    out.writeCard32(bitmap);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateRegionFromBitmapBuilder {
    @java.lang.SuppressWarnings("all")
    private int region;
    @java.lang.SuppressWarnings("all")
    private int bitmap;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    CreateRegionFromBitmapBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRegionFromBitmap.CreateRegionFromBitmapBuilder region(final int region) {
      this.region = region;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRegionFromBitmap.CreateRegionFromBitmapBuilder bitmap(final int bitmap) {
      this.bitmap = bitmap;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateRegionFromBitmap build() {
      return new CreateRegionFromBitmap(this.region, this.bitmap);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateRegionFromBitmap.CreateRegionFromBitmapBuilder(region=" + this.region + ", bitmap=" + this.bitmap + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateRegionFromBitmap(final int region, final int bitmap) {
    this.region = region;
    this.bitmap = bitmap;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateRegionFromBitmap.CreateRegionFromBitmapBuilder builder() {
    return new CreateRegionFromBitmap.CreateRegionFromBitmapBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getRegion() {
    return this.region;
  }

  @java.lang.SuppressWarnings("all")
  public int getBitmap() {
    return this.bitmap;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateRegionFromBitmap)) return false;
    final CreateRegionFromBitmap other = (CreateRegionFromBitmap) o;
    if (this.getRegion() != other.getRegion()) return false;
    if (this.getBitmap() != other.getBitmap()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getRegion();
    result = result * PRIME + this.getBitmap();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateRegionFromBitmap(region=" + this.getRegion() + ", bitmap=" + this.getBitmap() + ")";
  }
}
