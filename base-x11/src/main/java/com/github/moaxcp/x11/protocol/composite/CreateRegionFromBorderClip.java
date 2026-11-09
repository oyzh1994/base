package com.github.moaxcp.x11.protocol.composite;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CreateRegionFromBorderClip implements OneWayRequest {
  public static final String PLUGIN_NAME = "composite";
  public static final byte OPCODE = 5;
  private final int region;
  private final int window;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateRegionFromBorderClip readCreateRegionFromBorderClip(X11Input in) throws IOException {
    CreateRegionFromBorderClip.CreateRegionFromBorderClipBuilder javaBuilder = CreateRegionFromBorderClip.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int region = in.readCard32();
    int window = in.readCard32();
    javaBuilder.region(region);
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(region);
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateRegionFromBorderClipBuilder {
    @java.lang.SuppressWarnings("all")
    private int region;
    @java.lang.SuppressWarnings("all")
    private int window;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    CreateRegionFromBorderClipBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRegionFromBorderClip.CreateRegionFromBorderClipBuilder region(final int region) {
      this.region = region;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRegionFromBorderClip.CreateRegionFromBorderClipBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateRegionFromBorderClip build() {
      return new CreateRegionFromBorderClip(this.region, this.window);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateRegionFromBorderClip.CreateRegionFromBorderClipBuilder(region=" + this.region + ", window=" + this.window + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateRegionFromBorderClip(final int region, final int window) {
    this.region = region;
    this.window = window;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateRegionFromBorderClip.CreateRegionFromBorderClipBuilder builder() {
    return new CreateRegionFromBorderClip.CreateRegionFromBorderClipBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getRegion() {
    return this.region;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateRegionFromBorderClip)) return false;
    final CreateRegionFromBorderClip other = (CreateRegionFromBorderClip) o;
    if (this.getRegion() != other.getRegion()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getRegion();
    result = result * PRIME + this.getWindow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateRegionFromBorderClip(region=" + this.getRegion() + ", window=" + this.getWindow() + ")";
  }
}
