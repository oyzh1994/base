package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XError;
import java.io.IOException;

public final class CounterError implements XError {
  public static final String PLUGIN_NAME = "sync";
  public static final byte CODE = 0;
  private final byte firstErrorOffset;
  private final short sequenceNumber;
  private final int badCounter;
  private final short minorOpcode;
  private final byte majorOpcode;

  @Override
  public byte getCode() {
    return (byte) (firstErrorOffset + CODE);
  }

  public static CounterError readCounterError(byte firstErrorOffset, X11Input in) throws IOException {
    CounterError.CounterErrorBuilder javaBuilder = CounterError.builder();
    short sequenceNumber = in.readCard16();
    int badCounter = in.readCard32();
    short minorOpcode = in.readCard16();
    byte majorOpcode = in.readCard8();
    byte[] pad6 = in.readPad(21);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.badCounter(badCounter);
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
    out.writeCard32(badCounter);
    out.writeCard16(minorOpcode);
    out.writeCard8(majorOpcode);
    out.writePad(21);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CounterErrorBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstErrorOffset;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int badCounter;
    @java.lang.SuppressWarnings("all")
    private short minorOpcode;
    @java.lang.SuppressWarnings("all")
    private byte majorOpcode;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    CounterErrorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CounterError.CounterErrorBuilder firstErrorOffset(final byte firstErrorOffset) {
      this.firstErrorOffset = firstErrorOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CounterError.CounterErrorBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CounterError.CounterErrorBuilder badCounter(final int badCounter) {
      this.badCounter = badCounter;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CounterError.CounterErrorBuilder minorOpcode(final short minorOpcode) {
      this.minorOpcode = minorOpcode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CounterError.CounterErrorBuilder majorOpcode(final byte majorOpcode) {
      this.majorOpcode = majorOpcode;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CounterError build() {
      return new CounterError(this.firstErrorOffset, this.sequenceNumber, this.badCounter, this.minorOpcode, this.majorOpcode);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CounterError.CounterErrorBuilder(firstErrorOffset=" + this.firstErrorOffset + ", sequenceNumber=" + this.sequenceNumber + ", badCounter=" + this.badCounter + ", minorOpcode=" + this.minorOpcode + ", majorOpcode=" + this.majorOpcode + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CounterError(final byte firstErrorOffset, final short sequenceNumber, final int badCounter, final short minorOpcode, final byte majorOpcode) {
    this.firstErrorOffset = firstErrorOffset;
    this.sequenceNumber = sequenceNumber;
    this.badCounter = badCounter;
    this.minorOpcode = minorOpcode;
    this.majorOpcode = majorOpcode;
  }

  @java.lang.SuppressWarnings("all")
  public static CounterError.CounterErrorBuilder builder() {
    return new CounterError.CounterErrorBuilder();
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
  public int getBadCounter() {
    return this.badCounter;
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
    if (!(o instanceof CounterError)) return false;
    final CounterError other = (CounterError) o;
    if (this.getFirstErrorOffset() != other.getFirstErrorOffset()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getBadCounter() != other.getBadCounter()) return false;
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
    result = result * PRIME + this.getBadCounter();
    result = result * PRIME + this.getMinorOpcode();
    result = result * PRIME + this.getMajorOpcode();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CounterError(firstErrorOffset=" + this.getFirstErrorOffset() + ", sequenceNumber=" + this.getSequenceNumber() + ", badCounter=" + this.getBadCounter() + ", minorOpcode=" + this.getMinorOpcode() + ", majorOpcode=" + this.getMajorOpcode() + ")";
  }
}
