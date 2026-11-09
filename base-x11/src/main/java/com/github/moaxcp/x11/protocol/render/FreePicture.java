package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class FreePicture implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 7;
  private final int picture;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FreePicture readFreePicture(X11Input in) throws IOException {
    FreePicture.FreePictureBuilder javaBuilder = FreePicture.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int picture = in.readCard32();
    javaBuilder.picture(picture);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(picture);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class FreePictureBuilder {
    @java.lang.SuppressWarnings("all")
    private int picture;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    FreePictureBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FreePicture.FreePictureBuilder picture(final int picture) {
      this.picture = picture;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public FreePicture build() {
      return new FreePicture(this.picture);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "FreePicture.FreePictureBuilder(picture=" + this.picture + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  FreePicture(final int picture) {
    this.picture = picture;
  }

  @java.lang.SuppressWarnings("all")
  public static FreePicture.FreePictureBuilder builder() {
    return new FreePicture.FreePictureBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getPicture() {
    return this.picture;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof FreePicture)) return false;
    final FreePicture other = (FreePicture) o;
    if (this.getPicture() != other.getPicture()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getPicture();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "FreePicture(picture=" + this.getPicture() + ")";
  }
}
