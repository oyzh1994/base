package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class CountedString16 implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte[] string;
  private final byte[] alignmentPad;

  public static CountedString16 readCountedString16(X11Input in) throws IOException {
    CountedString16.CountedString16Builder javaBuilder = CountedString16.builder();
    short length = in.readCard16();
    byte[] string = in.readChar(Short.toUnsignedInt(length));
    byte[] alignmentPad = in.readVoid((Short.toUnsignedInt(length) + 5) & (~(3)) - Short.toUnsignedInt(length) + 2);
    javaBuilder.string(string);
    javaBuilder.alignmentPad(alignmentPad);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    short length = (short) string.length;
    out.writeCard16(length);
    out.writeChar(string);
    out.writeVoid(alignmentPad);
  }

  @Override
  public int getSize() {
    return 2 + 1 * string.length + 1 * alignmentPad.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CountedString16Builder {
    @java.lang.SuppressWarnings("all")
    private byte[] string;
    @java.lang.SuppressWarnings("all")
    private byte[] alignmentPad;

    public int getSize() {
      return 2 + 1 * string.length + 1 * alignmentPad.length;
    }

    @java.lang.SuppressWarnings("all")
    CountedString16Builder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CountedString16.CountedString16Builder string(final byte[] string) {
      if (string == null) {
        throw new java.lang.NullPointerException("string is marked non-null but is null");
      }
      this.string = string;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CountedString16.CountedString16Builder alignmentPad(final byte[] alignmentPad) {
      if (alignmentPad == null) {
        throw new java.lang.NullPointerException("alignmentPad is marked non-null but is null");
      }
      this.alignmentPad = alignmentPad;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CountedString16 build() {
      return new CountedString16(this.string, this.alignmentPad);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CountedString16.CountedString16Builder(string=" + java.util.Arrays.toString(this.string) + ", alignmentPad=" + java.util.Arrays.toString(this.alignmentPad) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CountedString16(final byte[] string, final byte[] alignmentPad) {
    if (string == null) {
      throw new java.lang.NullPointerException("string is marked non-null but is null");
    }
    if (alignmentPad == null) {
      throw new java.lang.NullPointerException("alignmentPad is marked non-null but is null");
    }
    this.string = string;
    this.alignmentPad = alignmentPad;
  }

  @java.lang.SuppressWarnings("all")
  public static CountedString16.CountedString16Builder builder() {
    return new CountedString16.CountedString16Builder();
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getString() {
    return this.string;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getAlignmentPad() {
    return this.alignmentPad;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CountedString16)) return false;
    final CountedString16 other = (CountedString16) o;
    if (!java.util.Arrays.equals(this.getString(), other.getString())) return false;
    if (!java.util.Arrays.equals(this.getAlignmentPad(), other.getAlignmentPad())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + java.util.Arrays.hashCode(this.getString());
    result = result * PRIME + java.util.Arrays.hashCode(this.getAlignmentPad());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CountedString16(string=" + java.util.Arrays.toString(this.getString()) + ", alignmentPad=" + java.util.Arrays.toString(this.getAlignmentPad()) + ")";
  }
}
