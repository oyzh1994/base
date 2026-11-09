package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ChangePointerControl implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 105;
  private final short accelerationNumerator;
  private final short accelerationDenominator;
  private final short threshold;
  private final boolean doAcceleration;
  private final boolean doThreshold;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangePointerControl readChangePointerControl(X11Input in) throws IOException {
    ChangePointerControl.ChangePointerControlBuilder javaBuilder = ChangePointerControl.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short accelerationNumerator = in.readInt16();
    short accelerationDenominator = in.readInt16();
    short threshold = in.readInt16();
    boolean doAcceleration = in.readBool();
    boolean doThreshold = in.readBool();
    javaBuilder.accelerationNumerator(accelerationNumerator);
    javaBuilder.accelerationDenominator(accelerationDenominator);
    javaBuilder.threshold(threshold);
    javaBuilder.doAcceleration(doAcceleration);
    javaBuilder.doThreshold(doThreshold);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeInt16(accelerationNumerator);
    out.writeInt16(accelerationDenominator);
    out.writeInt16(threshold);
    out.writeBool(doAcceleration);
    out.writeBool(doThreshold);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ChangePointerControlBuilder {
    @java.lang.SuppressWarnings("all")
    private short accelerationNumerator;
    @java.lang.SuppressWarnings("all")
    private short accelerationDenominator;
    @java.lang.SuppressWarnings("all")
    private short threshold;
    @java.lang.SuppressWarnings("all")
    private boolean doAcceleration;
    @java.lang.SuppressWarnings("all")
    private boolean doThreshold;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    ChangePointerControlBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangePointerControl.ChangePointerControlBuilder accelerationNumerator(final short accelerationNumerator) {
      this.accelerationNumerator = accelerationNumerator;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangePointerControl.ChangePointerControlBuilder accelerationDenominator(final short accelerationDenominator) {
      this.accelerationDenominator = accelerationDenominator;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangePointerControl.ChangePointerControlBuilder threshold(final short threshold) {
      this.threshold = threshold;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangePointerControl.ChangePointerControlBuilder doAcceleration(final boolean doAcceleration) {
      this.doAcceleration = doAcceleration;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangePointerControl.ChangePointerControlBuilder doThreshold(final boolean doThreshold) {
      this.doThreshold = doThreshold;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ChangePointerControl build() {
      return new ChangePointerControl(this.accelerationNumerator, this.accelerationDenominator, this.threshold, this.doAcceleration, this.doThreshold);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ChangePointerControl.ChangePointerControlBuilder(accelerationNumerator=" + this.accelerationNumerator + ", accelerationDenominator=" + this.accelerationDenominator + ", threshold=" + this.threshold + ", doAcceleration=" + this.doAcceleration + ", doThreshold=" + this.doThreshold + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ChangePointerControl(final short accelerationNumerator, final short accelerationDenominator, final short threshold, final boolean doAcceleration, final boolean doThreshold) {
    this.accelerationNumerator = accelerationNumerator;
    this.accelerationDenominator = accelerationDenominator;
    this.threshold = threshold;
    this.doAcceleration = doAcceleration;
    this.doThreshold = doThreshold;
  }

  @java.lang.SuppressWarnings("all")
  public static ChangePointerControl.ChangePointerControlBuilder builder() {
    return new ChangePointerControl.ChangePointerControlBuilder();
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

  @java.lang.SuppressWarnings("all")
  public boolean isDoAcceleration() {
    return this.doAcceleration;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isDoThreshold() {
    return this.doThreshold;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ChangePointerControl)) return false;
    final ChangePointerControl other = (ChangePointerControl) o;
    if (this.getAccelerationNumerator() != other.getAccelerationNumerator()) return false;
    if (this.getAccelerationDenominator() != other.getAccelerationDenominator()) return false;
    if (this.getThreshold() != other.getThreshold()) return false;
    if (this.isDoAcceleration() != other.isDoAcceleration()) return false;
    if (this.isDoThreshold() != other.isDoThreshold()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getAccelerationNumerator();
    result = result * PRIME + this.getAccelerationDenominator();
    result = result * PRIME + this.getThreshold();
    result = result * PRIME + (this.isDoAcceleration() ? 79 : 97);
    result = result * PRIME + (this.isDoThreshold() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ChangePointerControl(accelerationNumerator=" + this.getAccelerationNumerator() + ", accelerationDenominator=" + this.getAccelerationDenominator() + ", threshold=" + this.getThreshold() + ", doAcceleration=" + this.isDoAcceleration() + ", doThreshold=" + this.isDoThreshold() + ")";
  }
}
