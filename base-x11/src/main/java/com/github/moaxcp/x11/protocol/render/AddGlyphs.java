package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ByteList;
import org.eclipse.collections.api.list.primitive.IntList;

public final class AddGlyphs implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 20;
  private final int glyphset;
  private final IntList glyphids;
  private final ImmutableList<Glyphinfo> glyphs;
  private final ByteList data;

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
    IntList glyphids = in.readCard32((int) (Integer.toUnsignedLong(glyphsLen)));
    javaStart += 4 * glyphids.size();
    MutableList<Glyphinfo> glyphs = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(glyphsLen)));
    for (int i = 0; i < Integer.toUnsignedLong(glyphsLen); i++) {
      glyphs.add(Glyphinfo.readGlyphinfo(in));
    }
    javaStart += XObject.sizeOf(glyphs);
    ByteList data = in.readByte(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.glyphset(glyphset);
    javaBuilder.glyphids(glyphids.toImmutable());
    javaBuilder.glyphs(glyphs.toImmutable());
    javaBuilder.data(data.toImmutable());
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
    return 12 + 4 * glyphids.size() + XObject.sizeOf(glyphs) + 1 * data.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class AddGlyphsBuilder {
    @java.lang.SuppressWarnings("all")
    private int glyphset;
    @java.lang.SuppressWarnings("all")
    private IntList glyphids;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Glyphinfo> glyphs;
    @java.lang.SuppressWarnings("all")
    private ByteList data;

    public int getSize() {
      return 12 + 4 * glyphids.size() + XObject.sizeOf(glyphs) + 1 * data.size();
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
    public AddGlyphs.AddGlyphsBuilder glyphids(final IntList glyphids) {
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
    public AddGlyphs.AddGlyphsBuilder glyphs(final ImmutableList<Glyphinfo> glyphs) {
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
    public AddGlyphs.AddGlyphsBuilder data(final ByteList data) {
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
      return "AddGlyphs.AddGlyphsBuilder(glyphset=" + this.glyphset + ", glyphids=" + this.glyphids + ", glyphs=" + this.glyphs + ", data=" + this.data + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  AddGlyphs(final int glyphset, final IntList glyphids, final ImmutableList<Glyphinfo> glyphs, final ByteList data) {
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
  public IntList getGlyphids() {
    return this.glyphids;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Glyphinfo> getGlyphs() {
    return this.glyphs;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getData() {
    return this.data;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof AddGlyphs)) return false;
    final AddGlyphs other = (AddGlyphs) o;
    if (this.getGlyphset() != other.getGlyphset()) return false;
    final java.lang.Object this$glyphids = this.getGlyphids();
    final java.lang.Object other$glyphids = other.getGlyphids();
    if (this$glyphids == null ? other$glyphids != null : !this$glyphids.equals(other$glyphids)) return false;
    final java.lang.Object this$glyphs = this.getGlyphs();
    final java.lang.Object other$glyphs = other.getGlyphs();
    if (this$glyphs == null ? other$glyphs != null : !this$glyphs.equals(other$glyphs)) return false;
    final java.lang.Object this$data = this.getData();
    final java.lang.Object other$data = other.getData();
    if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getGlyphset();
    final java.lang.Object $glyphids = this.getGlyphids();
    result = result * PRIME + ($glyphids == null ? 43 : $glyphids.hashCode());
    final java.lang.Object $glyphs = this.getGlyphs();
    result = result * PRIME + ($glyphs == null ? 43 : $glyphs.hashCode());
    final java.lang.Object $data = this.getData();
    result = result * PRIME + ($data == null ? 43 : $data.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "AddGlyphs(glyphset=" + this.getGlyphset() + ", glyphids=" + this.getGlyphids() + ", glyphs=" + this.getGlyphs() + ", data=" + this.getData() + ")";
  }
}
