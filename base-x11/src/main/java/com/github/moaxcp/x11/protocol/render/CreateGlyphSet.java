package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CreateGlyphSet implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 17;
  private final int gsid;
  private final int format;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateGlyphSet readCreateGlyphSet(X11Input in) throws IOException {
    CreateGlyphSet.CreateGlyphSetBuilder javaBuilder = CreateGlyphSet.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int gsid = in.readCard32();
    int format = in.readCard32();
    javaBuilder.gsid(gsid);
    javaBuilder.format(format);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(gsid);
    out.writeCard32(format);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateGlyphSetBuilder {
    @java.lang.SuppressWarnings("all")
    private int gsid;
    @java.lang.SuppressWarnings("all")
    private int format;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    CreateGlyphSetBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateGlyphSet.CreateGlyphSetBuilder gsid(final int gsid) {
      this.gsid = gsid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateGlyphSet.CreateGlyphSetBuilder format(final int format) {
      this.format = format;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateGlyphSet build() {
      return new CreateGlyphSet(this.gsid, this.format);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateGlyphSet.CreateGlyphSetBuilder(gsid=" + this.gsid + ", format=" + this.format + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateGlyphSet(final int gsid, final int format) {
    this.gsid = gsid;
    this.format = format;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateGlyphSet.CreateGlyphSetBuilder builder() {
    return new CreateGlyphSet.CreateGlyphSetBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getGsid() {
    return this.gsid;
  }

  @java.lang.SuppressWarnings("all")
  public int getFormat() {
    return this.format;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateGlyphSet)) return false;
    final CreateGlyphSet other = (CreateGlyphSet) o;
    if (this.getGsid() != other.getGsid()) return false;
    if (this.getFormat() != other.getFormat()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getGsid();
    result = result * PRIME + this.getFormat();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateGlyphSet(gsid=" + this.getGsid() + ", format=" + this.getFormat() + ")";
  }
}
