package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XError;
import java.io.IOException;

public final class GlyphSetError implements XError {
  public static final String PLUGIN_NAME = "render";
  public static final byte CODE = 3;
  private final byte firstErrorOffset;
  private final short sequenceNumber;
  private final short minorOpcode;
  private final byte majorOpcode;

  @Override
  public byte getCode() {
    return (byte) (firstErrorOffset + CODE);
  }

  public static GlyphSetError readGlyphSetError(byte firstErrorOffset, X11Input in) throws IOException {
    GlyphSetError.GlyphSetErrorBuilder javaBuilder = GlyphSetError.builder();
    short sequenceNumber = in.readCard16();
    short minorOpcode = in.readCard16();
    byte majorOpcode = in.readCard8();
    byte[] pad5 = in.readPad(25);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.minorOpcode(minorOpcode);
    javaBuilder.majorOpcode(majorOpcode);
    javaBuilder.firstErrorOffset(firstErrorOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(getCode());
    out.writeCard16(sequenceNumber);
    out.writeCard16(minorOpcode);
    out.writeCard8(majorOpcode);
    out.writePad(25);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GlyphSetErrorBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstErrorOffset;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short minorOpcode;
    @java.lang.SuppressWarnings("all")
    private byte majorOpcode;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    GlyphSetErrorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GlyphSetError.GlyphSetErrorBuilder firstErrorOffset(final byte firstErrorOffset) {
      this.firstErrorOffset = firstErrorOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GlyphSetError.GlyphSetErrorBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GlyphSetError.GlyphSetErrorBuilder minorOpcode(final short minorOpcode) {
      this.minorOpcode = minorOpcode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GlyphSetError.GlyphSetErrorBuilder majorOpcode(final byte majorOpcode) {
      this.majorOpcode = majorOpcode;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GlyphSetError build() {
      return new GlyphSetError(this.firstErrorOffset, this.sequenceNumber, this.minorOpcode, this.majorOpcode);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GlyphSetError.GlyphSetErrorBuilder(firstErrorOffset=" + this.firstErrorOffset + ", sequenceNumber=" + this.sequenceNumber + ", minorOpcode=" + this.minorOpcode + ", majorOpcode=" + this.majorOpcode + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GlyphSetError(final byte firstErrorOffset, final short sequenceNumber, final short minorOpcode, final byte majorOpcode) {
    this.firstErrorOffset = firstErrorOffset;
    this.sequenceNumber = sequenceNumber;
    this.minorOpcode = minorOpcode;
    this.majorOpcode = majorOpcode;
  }

  @java.lang.SuppressWarnings("all")
  public static GlyphSetError.GlyphSetErrorBuilder builder() {
    return new GlyphSetError.GlyphSetErrorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstErrorOffset() {
    return this.firstErrorOffset;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public short getMinorOpcode() {
    return this.minorOpcode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMajorOpcode() {
    return this.majorOpcode;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GlyphSetError)) return false;
    final GlyphSetError other = (GlyphSetError) o;
    if (this.getFirstErrorOffset() != other.getFirstErrorOffset()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getMinorOpcode() != other.getMinorOpcode()) return false;
    if (this.getMajorOpcode() != other.getMajorOpcode()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstErrorOffset();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getMinorOpcode();
    result = result * PRIME + this.getMajorOpcode();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GlyphSetError(firstErrorOffset=" + this.getFirstErrorOffset() + ", sequenceNumber=" + this.getSequenceNumber() + ", minorOpcode=" + this.getMinorOpcode() + ", majorOpcode=" + this.getMajorOpcode() + ")";
  }
}
