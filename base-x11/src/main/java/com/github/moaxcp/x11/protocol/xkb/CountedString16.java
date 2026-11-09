package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class CountedString16 implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final ByteList string;
  private final ByteList alignmentPad;

  public static CountedString16 readCountedString16(X11Input in) throws IOException {
    CountedString16.CountedString16Builder javaBuilder = CountedString16.builder();
    short length = in.readCard16();
    ByteList string = in.readChar(Short.toUnsignedInt(length));
    ByteList alignmentPad = in.readVoid((Short.toUnsignedInt(length) + 5) & (~(3)) - Short.toUnsignedInt(length) + 2);
    javaBuilder.string(string.toImmutable());
    javaBuilder.alignmentPad(alignmentPad.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    short length = (short) string.size();
    out.writeCard16(length);
    out.writeChar(string);
    out.writeVoid(alignmentPad);
  }

  @Override
  public int getSize() {
    return 2 + 1 * string.size() + 1 * alignmentPad.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CountedString16Builder {
    @java.lang.SuppressWarnings("all")
    private ByteList string;
    @java.lang.SuppressWarnings("all")
    private ByteList alignmentPad;

    public int getSize() {
      return 2 + 1 * string.size() + 1 * alignmentPad.size();
    }

    @java.lang.SuppressWarnings("all")
    CountedString16Builder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CountedString16.CountedString16Builder string(final ByteList string) {
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
    public CountedString16.CountedString16Builder alignmentPad(final ByteList alignmentPad) {
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
      return "CountedString16.CountedString16Builder(string=" + this.string + ", alignmentPad=" + this.alignmentPad + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CountedString16(final ByteList string, final ByteList alignmentPad) {
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
  public ByteList getString() {
    return this.string;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getAlignmentPad() {
    return this.alignmentPad;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CountedString16)) return false;
    final CountedString16 other = (CountedString16) o;
    final java.lang.Object this$string = this.getString();
    final java.lang.Object other$string = other.getString();
    if (this$string == null ? other$string != null : !this$string.equals(other$string)) return false;
    final java.lang.Object this$alignmentPad = this.getAlignmentPad();
    final java.lang.Object other$alignmentPad = other.getAlignmentPad();
    if (this$alignmentPad == null ? other$alignmentPad != null : !this$alignmentPad.equals(other$alignmentPad)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $string = this.getString();
    result = result * PRIME + ($string == null ? 43 : $string.hashCode());
    final java.lang.Object $alignmentPad = this.getAlignmentPad();
    result = result * PRIME + ($alignmentPad == null ? 43 : $alignmentPad.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CountedString16(string=" + this.getString() + ", alignmentPad=" + this.getAlignmentPad() + ")";
  }
}
