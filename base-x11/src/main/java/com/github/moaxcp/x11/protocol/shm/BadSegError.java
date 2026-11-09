package com.github.moaxcp.x11.protocol.shm;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XError;
import java.io.IOException;

public final class BadSegError implements XError {
  public static final String PLUGIN_NAME = "shm";
  public static final byte CODE = 2;
  private final byte firstErrorOffset;
  private final short sequenceNumber;
  private final int badValue;
  private final short minorOpcode;
  private final byte majorOpcode;

  @Override
  public byte getCode() {
    return (byte) (firstErrorOffset + CODE);
  }

  public static BadSegError readBadSegError(byte firstErrorOffset, X11Input in) throws IOException {
    BadSegError.BadSegErrorBuilder javaBuilder = BadSegError.builder();
    short sequenceNumber = in.readCard16();
    int badValue = in.readCard32();
    short minorOpcode = in.readCard16();
    byte majorOpcode = in.readCard8();
    byte[] pad6 = in.readPad(1);
    byte[] pad7 = in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.badValue(badValue);
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
    out.writeCard32(badValue);
    out.writeCard16(minorOpcode);
    out.writeCard8(majorOpcode);
    out.writePad(1);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class BadSegErrorBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstErrorOffset;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int badValue;
    @java.lang.SuppressWarnings("all")
    private short minorOpcode;
    @java.lang.SuppressWarnings("all")
    private byte majorOpcode;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    BadSegErrorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public BadSegError.BadSegErrorBuilder firstErrorOffset(final byte firstErrorOffset) {
      this.firstErrorOffset = firstErrorOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public BadSegError.BadSegErrorBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public BadSegError.BadSegErrorBuilder badValue(final int badValue) {
      this.badValue = badValue;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public BadSegError.BadSegErrorBuilder minorOpcode(final short minorOpcode) {
      this.minorOpcode = minorOpcode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public BadSegError.BadSegErrorBuilder majorOpcode(final byte majorOpcode) {
      this.majorOpcode = majorOpcode;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public BadSegError build() {
      return new BadSegError(this.firstErrorOffset, this.sequenceNumber, this.badValue, this.minorOpcode, this.majorOpcode);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "BadSegError.BadSegErrorBuilder(firstErrorOffset=" + this.firstErrorOffset + ", sequenceNumber=" + this.sequenceNumber + ", badValue=" + this.badValue + ", minorOpcode=" + this.minorOpcode + ", majorOpcode=" + this.majorOpcode + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  BadSegError(final byte firstErrorOffset, final short sequenceNumber, final int badValue, final short minorOpcode, final byte majorOpcode) {
    this.firstErrorOffset = firstErrorOffset;
    this.sequenceNumber = sequenceNumber;
    this.badValue = badValue;
    this.minorOpcode = minorOpcode;
    this.majorOpcode = majorOpcode;
  }

  @java.lang.SuppressWarnings("all")
  public static BadSegError.BadSegErrorBuilder builder() {
    return new BadSegError.BadSegErrorBuilder();
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
  public int getBadValue() {
    return this.badValue;
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
    if (!(o instanceof BadSegError)) return false;
    final BadSegError other = (BadSegError) o;
    if (this.getFirstErrorOffset() != other.getFirstErrorOffset()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getBadValue() != other.getBadValue()) return false;
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
    result = result * PRIME + this.getBadValue();
    result = result * PRIME + this.getMinorOpcode();
    result = result * PRIME + this.getMajorOpcode();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "BadSegError(firstErrorOffset=" + this.getFirstErrorOffset() + ", sequenceNumber=" + this.getSequenceNumber() + ", badValue=" + this.getBadValue() + ", minorOpcode=" + this.getMinorOpcode() + ", majorOpcode=" + this.getMajorOpcode() + ")";
  }
}
