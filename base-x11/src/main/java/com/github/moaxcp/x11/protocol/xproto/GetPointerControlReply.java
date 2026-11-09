package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetPointerControlReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final short accelerationNumerator;
  private final short accelerationDenominator;
  private final short threshold;

  public static GetPointerControlReply readGetPointerControlReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetPointerControlReply.GetPointerControlReplyBuilder javaBuilder = GetPointerControlReply.builder();
    int length = in.readCard32();
    short accelerationNumerator = in.readCard16();
    short accelerationDenominator = in.readCard16();
    short threshold = in.readCard16();
    byte[] pad7 = in.readPad(18);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.accelerationNumerator(accelerationNumerator);
    javaBuilder.accelerationDenominator(accelerationDenominator);
    javaBuilder.threshold(threshold);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(accelerationNumerator);
    out.writeCard16(accelerationDenominator);
    out.writeCard16(threshold);
    out.writePad(18);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetPointerControlReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short accelerationNumerator;
    @java.lang.SuppressWarnings("all")
    private short accelerationDenominator;
    @java.lang.SuppressWarnings("all")
    private short threshold;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    GetPointerControlReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetPointerControlReply.GetPointerControlReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetPointerControlReply.GetPointerControlReplyBuilder accelerationNumerator(final short accelerationNumerator) {
      this.accelerationNumerator = accelerationNumerator;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetPointerControlReply.GetPointerControlReplyBuilder accelerationDenominator(final short accelerationDenominator) {
      this.accelerationDenominator = accelerationDenominator;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetPointerControlReply.GetPointerControlReplyBuilder threshold(final short threshold) {
      this.threshold = threshold;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetPointerControlReply build() {
      return new GetPointerControlReply(this.sequenceNumber, this.accelerationNumerator, this.accelerationDenominator, this.threshold);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetPointerControlReply.GetPointerControlReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", accelerationNumerator=" + this.accelerationNumerator + ", accelerationDenominator=" + this.accelerationDenominator + ", threshold=" + this.threshold + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetPointerControlReply(final short sequenceNumber, final short accelerationNumerator, final short accelerationDenominator, final short threshold) {
    this.sequenceNumber = sequenceNumber;
    this.accelerationNumerator = accelerationNumerator;
    this.accelerationDenominator = accelerationDenominator;
    this.threshold = threshold;
  }

  @java.lang.SuppressWarnings("all")
  public static GetPointerControlReply.GetPointerControlReplyBuilder builder() {
    return new GetPointerControlReply.GetPointerControlReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public short getAccelerationNumerator() {
    return this.accelerationNumerator;
  }

  @java.lang.SuppressWarnings("all")
  public short getAccelerationDenominator() {
    return this.accelerationDenominator;
  }

  @java.lang.SuppressWarnings("all")
  public short getThreshold() {
    return this.threshold;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetPointerControlReply)) return false;
    final GetPointerControlReply other = (GetPointerControlReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getAccelerationNumerator() != other.getAccelerationNumerator()) return false;
    if (this.getAccelerationDenominator() != other.getAccelerationDenominator()) return false;
    if (this.getThreshold() != other.getThreshold()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getAccelerationNumerator();
    result = result * PRIME + this.getAccelerationDenominator();
    result = result * PRIME + this.getThreshold();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetPointerControlReply(sequenceNumber=" + this.getSequenceNumber() + ", accelerationNumerator=" + this.getAccelerationNumerator() + ", accelerationDenominator=" + this.getAccelerationDenominator() + ", threshold=" + this.getThreshold() + ")";
  }
}
