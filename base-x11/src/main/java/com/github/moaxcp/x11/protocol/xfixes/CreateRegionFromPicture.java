package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CreateRegionFromPicture implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 9;
  private final int region;
  private final int picture;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateRegionFromPicture readCreateRegionFromPicture(X11Input in) throws IOException {
    CreateRegionFromPicture.CreateRegionFromPictureBuilder javaBuilder = CreateRegionFromPicture.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int region = in.readCard32();
    int picture = in.readCard32();
    javaBuilder.region(region);
    javaBuilder.picture(picture);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(region);
    out.writeCard32(picture);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateRegionFromPictureBuilder {
    @java.lang.SuppressWarnings("all")
    private int region;
    @java.lang.SuppressWarnings("all")
    private int picture;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    CreateRegionFromPictureBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRegionFromPicture.CreateRegionFromPictureBuilder region(final int region) {
      this.region = region;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRegionFromPicture.CreateRegionFromPictureBuilder picture(final int picture) {
      this.picture = picture;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateRegionFromPicture build() {
      return new CreateRegionFromPicture(this.region, this.picture);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateRegionFromPicture.CreateRegionFromPictureBuilder(region=" + this.region + ", picture=" + this.picture + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateRegionFromPicture(final int region, final int picture) {
    this.region = region;
    this.picture = picture;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateRegionFromPicture.CreateRegionFromPictureBuilder builder() {
    return new CreateRegionFromPicture.CreateRegionFromPictureBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getRegion() {
    return this.region;
  }

  @java.lang.SuppressWarnings("all")
  public int getPicture() {
    return this.picture;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateRegionFromPicture)) return false;
    final CreateRegionFromPicture other = (CreateRegionFromPicture) o;
    if (this.getRegion() != other.getRegion()) return false;
    if (this.getPicture() != other.getPicture()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getRegion();
    result = result * PRIME + this.getPicture();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateRegionFromPicture(region=" + this.getRegion() + ", picture=" + this.getPicture() + ")";
  }
}
