package com.github.moaxcp.x11.protocol.render;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;

public final class AddGlyphs implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 20;
  private final int glyphset;
  private final int[] glyphids;
  private final List<Glyphinfo> glyphs;
  private final byte[] data;

  public byte getOpCode() {
    return OPCODE;
  }

  public static AddGlyphs readAddGlyphs(X11Input in) throws IOException {
    AddGlyphs.AddGlyphsBuilder javaBuilder = AddGlyphs.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int glyphset = in.readCard32();
    javaStart += 4;
    int glyphsLen = in.readCard32();
    javaStart += 4;
    int[] glyphids = in.readCard32((int) (Integer.toUnsignedLong(glyphsLen)));
    javaStart += 4 * glyphids.length;
    List<Glyphinfo> glyphs = new ArrayList<>((int) (Integer.toUnsignedLong(glyphsLen)));
    for (int i = 0; i < Integer.toUnsignedLong(glyphsLen); i++) {
      glyphs.add(Glyphinfo.readGlyphinfo(in));
    }
    javaStart += XObject.sizeOf(glyphs);
    byte[] data = in.readByte(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.glyphset(glyphset);
    javaBuilder.glyphids(glyphids);
    javaBuilder.glyphs(List.copyOf(glyphs));
    javaBuilder.data(data);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(glyphset);
    int glyphsLen = glyphs.size();
    out.writeCard32(glyphsLen);
    out.writeCard32(glyphids);
    for (Glyphinfo t : glyphs) {
      t.write(out);
    }
    out.writeByte(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 4 * glyphids.length + XObject.sizeOf(glyphs) + 1 * data.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class AddGlyphsBuilder {
    @java.lang.SuppressWarnings("all")
    private int glyphset;
    @java.lang.SuppressWarnings("all")
    private int[] glyphids;
    @java.lang.SuppressWarnings("all")
    private List<Glyphinfo> glyphs;
    @java.lang.SuppressWarnings("all")
    private byte[] data;

    public int getSize() {
      return 12 + 4 * glyphids.length + XObject.sizeOf(glyphs) + 1 * data.length;
    }

    @java.lang.SuppressWarnings("all")
    AddGlyphsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AddGlyphs.AddGlyphsBuilder glyphset(final int glyphset) {
      this.glyphset = glyphset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AddGlyphs.AddGlyphsBuilder glyphids(final int[] glyphids) {
      if (glyphids == null) {
        throw new java.lang.NullPointerException("glyphids is marked non-null but is null");
      }
      this.glyphids = glyphids;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AddGlyphs.AddGlyphsBuilder glyphs(final List<Glyphinfo> glyphs) {
      if (glyphs == null) {
        throw new java.lang.NullPointerException("glyphs is marked non-null but is null");
      }
      this.glyphs = glyphs;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AddGlyphs.AddGlyphsBuilder data(final byte[] data) {
      if (data == null) {
        throw new java.lang.NullPointerException("data is marked non-null but is null");
      }
      this.data = data;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public AddGlyphs build() {
      return new AddGlyphs(this.glyphset, this.glyphids, this.glyphs, this.data);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "AddGlyphs.AddGlyphsBuilder(glyphset=" + this.glyphset + ", glyphids=" + java.util.Arrays.toString(this.glyphids) + ", glyphs=" + this.glyphs + ", data=" + java.util.Arrays.toString(this.data) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  AddGlyphs(final int glyphset, final int[] glyphids, final List<Glyphinfo> glyphs, final byte[] data) {
    if (glyphids == null) {
      throw new java.lang.NullPointerException("glyphids is marked non-null but is null");
    }
    if (glyphs == null) {
      throw new java.lang.NullPointerException("glyphs is marked non-null but is null");
    }
    if (data == null) {
      throw new java.lang.NullPointerException("data is marked non-null but is null");
    }
    this.glyphset = glyphset;
    this.glyphids = glyphids;
    this.glyphs = glyphs;
    this.data = data;
  }

  @java.lang.SuppressWarnings("all")
  public static AddGlyphs.AddGlyphsBuilder builder() {
    return new AddGlyphs.AddGlyphsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getGlyphset() {
    return this.glyphset;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getGlyphids() {
    return this.glyphids;
  }

  @java.lang.SuppressWarnings("all")
  public List<Glyphinfo> getGlyphs() {
    return this.glyphs;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getData() {
    return this.data;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof AddGlyphs)) return false;
    final AddGlyphs other = (AddGlyphs) o;
    if (this.getGlyphset() != other.getGlyphset()) return false;
    if (!java.util.Arrays.equals(this.getGlyphids(), other.getGlyphids())) return false;
    final java.lang.Object this$glyphs = this.getGlyphs();
    final java.lang.Object other$glyphs = other.getGlyphs();
    if (this$glyphs == null ? other$glyphs != null : !this$glyphs.equals(other$glyphs)) return false;
    if (!java.util.Arrays.equals(this.getData(), other.getData())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getGlyphset();
    result = result * PRIME + java.util.Arrays.hashCode(this.getGlyphids());
    final java.lang.Object $glyphs = this.getGlyphs();
    result = result * PRIME + ($glyphs == null ? 43 : $glyphs.hashCode());
    result = result * PRIME + java.util.Arrays.hashCode(this.getData());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "AddGlyphs(glyphset=" + this.getGlyphset() + ", glyphids=" + java.util.Arrays.toString(this.getGlyphids()) + ", glyphs=" + this.getGlyphs() + ", data=" + java.util.Arrays.toString(this.getData()) + ")";
  }
}
