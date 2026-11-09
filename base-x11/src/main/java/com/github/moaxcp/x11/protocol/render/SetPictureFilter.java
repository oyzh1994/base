package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;
import org.eclipse.collections.api.list.primitive.IntList;

public final class SetPictureFilter implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 30;
  private final int picture;
  private final ByteList filter;
  private final IntList values;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetPictureFilter readSetPictureFilter(X11Input in) throws IOException {
    SetPictureFilter.SetPictureFilterBuilder javaBuilder = SetPictureFilter.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int picture = in.readCard32();
    javaStart += 4;
    short filterLen = in.readCard16();
    javaStart += 2;
    byte[] pad5 = in.readPad(2);
    javaStart += 2;
    ByteList filter = in.readChar(Short.toUnsignedInt(filterLen));
    javaStart += 1 * filter.size();
    in.readPadAlign(Short.toUnsignedInt(filterLen));
    javaStart += XObject.getSizeForPadAlign(4, 1 * filter.size());
    IntList values = in.readInt32(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.picture(picture);
    javaBuilder.filter(filter.toImmutable());
    javaBuilder.values(values.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(picture);
    short filterLen = (short) filter.size();
    out.writeCard16(filterLen);
    out.writePad(2);
    out.writeChar(filter);
    out.writePadAlign(Short.toUnsignedInt(filterLen));
    out.writeInt32(values);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 1 * filter.size() + XObject.getSizeForPadAlign(4, 1 * filter.size()) + 4 * values.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetPictureFilterBuilder {
    @java.lang.SuppressWarnings("all")
    private int picture;
    @java.lang.SuppressWarnings("all")
    private ByteList filter;
    @java.lang.SuppressWarnings("all")
    private IntList values;

    public int getSize() {
      return 12 + 1 * filter.size() + XObject.getSizeForPadAlign(4, 1 * filter.size()) + 4 * values.size();
    }

    @java.lang.SuppressWarnings("all")
    SetPictureFilterBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPictureFilter.SetPictureFilterBuilder picture(final int picture) {
      this.picture = picture;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPictureFilter.SetPictureFilterBuilder filter(final ByteList filter) {
      if (filter == null) {
        throw new java.lang.NullPointerException("filter is marked non-null but is null");
      }
      this.filter = filter;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPictureFilter.SetPictureFilterBuilder values(final IntList values) {
      if (values == null) {
        throw new java.lang.NullPointerException("values is marked non-null but is null");
      }
      this.values = values;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetPictureFilter build() {
      return new SetPictureFilter(this.picture, this.filter, this.values);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetPictureFilter.SetPictureFilterBuilder(picture=" + this.picture + ", filter=" + this.filter + ", values=" + this.values + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetPictureFilter(final int picture, final ByteList filter, final IntList values) {
    if (filter == null) {
      throw new java.lang.NullPointerException("filter is marked non-null but is null");
    }
    if (values == null) {
      throw new java.lang.NullPointerException("values is marked non-null but is null");
    }
    this.picture = picture;
    this.filter = filter;
    this.values = values;
  }

  @java.lang.SuppressWarnings("all")
  public static SetPictureFilter.SetPictureFilterBuilder builder() {
    return new SetPictureFilter.SetPictureFilterBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getPicture() {
    return this.picture;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getFilter() {
    return this.filter;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getValues() {
    return this.values;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetPictureFilter)) return false;
    final SetPictureFilter other = (SetPictureFilter) o;
    if (this.getPicture() != other.getPicture()) return false;
    final java.lang.Object this$filter = this.getFilter();
    final java.lang.Object other$filter = other.getFilter();
    if (this$filter == null ? other$filter != null : !this$filter.equals(other$filter)) return false;
    final java.lang.Object this$values = this.getValues();
    final java.lang.Object other$values = other.getValues();
    if (this$values == null ? other$values != null : !this$values.equals(other$values)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getPicture();
    final java.lang.Object $filter = this.getFilter();
    result = result * PRIME + ($filter == null ? 43 : $filter.hashCode());
    final java.lang.Object $values = this.getValues();
    result = result * PRIME + ($values == null ? 43 : $values.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetPictureFilter(picture=" + this.getPicture() + ", filter=" + this.getFilter() + ", values=" + this.getValues() + ")";
  }
}
