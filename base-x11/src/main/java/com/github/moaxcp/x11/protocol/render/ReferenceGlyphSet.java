package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ReferenceGlyphSet implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 18;
  private final int gsid;
  private final int existing;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ReferenceGlyphSet readReferenceGlyphSet(X11Input in) throws IOException {
    ReferenceGlyphSet.ReferenceGlyphSetBuilder javaBuilder = ReferenceGlyphSet.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int gsid = in.readCard32();
    int existing = in.readCard32();
    javaBuilder.gsid(gsid);
    javaBuilder.existing(existing);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(gsid);
    out.writeCard32(existing);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ReferenceGlyphSetBuilder {
    @java.lang.SuppressWarnings("all")
    private int gsid;
    @java.lang.SuppressWarnings("all")
    private int existing;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    ReferenceGlyphSetBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ReferenceGlyphSet.ReferenceGlyphSetBuilder gsid(final int gsid) {
      this.gsid = gsid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ReferenceGlyphSet.ReferenceGlyphSetBuilder existing(final int existing) {
      this.existing = existing;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ReferenceGlyphSet build() {
      return new ReferenceGlyphSet(this.gsid, this.existing);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ReferenceGlyphSet.ReferenceGlyphSetBuilder(gsid=" + this.gsid + ", existing=" + this.existing + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ReferenceGlyphSet(final int gsid, final int existing) {
    this.gsid = gsid;
    this.existing = existing;
  }

  @java.lang.SuppressWarnings("all")
  public static ReferenceGlyphSet.ReferenceGlyphSetBuilder builder() {
    return new ReferenceGlyphSet.ReferenceGlyphSetBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getGsid() {
    return this.gsid;
  }

  @java.lang.SuppressWarnings("all")
  public int getExisting() {
    return this.existing;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ReferenceGlyphSet)) return false;
    final ReferenceGlyphSet other = (ReferenceGlyphSet) o;
    if (this.getGsid() != other.getGsid()) return false;
    if (this.getExisting() != other.getExisting()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getGsid();
    result = result * PRIME + this.getExisting();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ReferenceGlyphSet(gsid=" + this.getGsid() + ", existing=" + this.getExisting() + ")";
  }
}
