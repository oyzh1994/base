package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetNamedIndicator implements TwoWayRequest<GetNamedIndicatorReply> {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 15;
  private final short deviceSpec;
  private final short ledClass;
  private final short ledID;
  private final int indicator;

  public XReplyFunction<GetNamedIndicatorReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetNamedIndicatorReply.readGetNamedIndicatorReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetNamedIndicator readGetNamedIndicator(X11Input in) throws IOException {
    GetNamedIndicator.GetNamedIndicatorBuilder javaBuilder = GetNamedIndicator.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short ledClass = in.readCard16();
    short ledID = in.readCard16();
    byte[] pad6 = in.readPad(2);
    int indicator = in.readCard32();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.ledClass(ledClass);
    javaBuilder.ledID(ledID);
    javaBuilder.indicator(indicator);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard16(ledClass);
    out.writeCard16(ledID);
    out.writePad(2);
    out.writeCard32(indicator);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetNamedIndicatorBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;
    @java.lang.SuppressWarnings("all")
    private short ledClass;
    @java.lang.SuppressWarnings("all")
    private short ledID;
    @java.lang.SuppressWarnings("all")
    private int indicator;

    public GetNamedIndicator.GetNamedIndicatorBuilder ledClass(LedClass ledClass) {
      this.ledClass = (short) ledClass.getValue();
      return this;
    }

    public GetNamedIndicator.GetNamedIndicatorBuilder ledClass(short ledClass) {
      this.ledClass = ledClass;
      return this;
    }

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    GetNamedIndicatorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicator.GetNamedIndicatorBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicator.GetNamedIndicatorBuilder ledID(final short ledID) {
      this.ledID = ledID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicator.GetNamedIndicatorBuilder indicator(final int indicator) {
      this.indicator = indicator;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetNamedIndicator build() {
      return new GetNamedIndicator(this.deviceSpec, this.ledClass, this.ledID, this.indicator);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetNamedIndicator.GetNamedIndicatorBuilder(deviceSpec=" + this.deviceSpec + ", ledClass=" + this.ledClass + ", ledID=" + this.ledID + ", indicator=" + this.indicator + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetNamedIndicator(final short deviceSpec, final short ledClass, final short ledID, final int indicator) {
    this.deviceSpec = deviceSpec;
    this.ledClass = ledClass;
    this.ledID = ledID;
    this.indicator = indicator;
  }

  @java.lang.SuppressWarnings("all")
  public static GetNamedIndicator.GetNamedIndicatorBuilder builder() {
    return new GetNamedIndicator.GetNamedIndicatorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public short getLedClass() {
    return this.ledClass;
  }

  @java.lang.SuppressWarnings("all")
  public short getLedID() {
    return this.ledID;
  }

  @java.lang.SuppressWarnings("all")
  public int getIndicator() {
    return this.indicator;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetNamedIndicator)) return false;
    final GetNamedIndicator other = (GetNamedIndicator) o;
    if (this.getDeviceSpec() != other.getDeviceSpec()) return false;
    if (this.getLedClass() != other.getLedClass()) return false;
    if (this.getLedID() != other.getLedID()) return false;
    if (this.getIndicator() != other.getIndicator()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceSpec();
    result = result * PRIME + this.getLedClass();
    result = result * PRIME + this.getLedID();
    result = result * PRIME + this.getIndicator();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetNamedIndicator(deviceSpec=" + this.getDeviceSpec() + ", ledClass=" + this.getLedClass() + ", ledID=" + this.getLedID() + ", indicator=" + this.getIndicator() + ")";
  }
}
