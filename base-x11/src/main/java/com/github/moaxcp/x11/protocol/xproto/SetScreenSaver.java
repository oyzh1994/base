package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SetScreenSaver implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 107;
  private final short timeout;
  private final short interval;
  private final byte preferBlanking;
  private final byte allowExposures;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetScreenSaver readSetScreenSaver(X11Input in) throws IOException {
    SetScreenSaver.SetScreenSaverBuilder javaBuilder = SetScreenSaver.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short timeout = in.readInt16();
    short interval = in.readInt16();
    byte preferBlanking = in.readCard8();
    byte allowExposures = in.readCard8();
    javaBuilder.timeout(timeout);
    javaBuilder.interval(interval);
    javaBuilder.preferBlanking(preferBlanking);
    javaBuilder.allowExposures(allowExposures);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeInt16(timeout);
    out.writeInt16(interval);
    out.writeCard8(preferBlanking);
    out.writeCard8(allowExposures);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 10;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetScreenSaverBuilder {
    @java.lang.SuppressWarnings("all")
    private short timeout;
    @java.lang.SuppressWarnings("all")
    private short interval;
    @java.lang.SuppressWarnings("all")
    private byte preferBlanking;
    @java.lang.SuppressWarnings("all")
    private byte allowExposures;

    public SetScreenSaver.SetScreenSaverBuilder preferBlanking(Blanking preferBlanking) {
      this.preferBlanking = (byte) preferBlanking.getValue();
      return this;
    }

    public SetScreenSaver.SetScreenSaverBuilder preferBlanking(byte preferBlanking) {
      this.preferBlanking = preferBlanking;
      return this;
    }

    public SetScreenSaver.SetScreenSaverBuilder allowExposures(Exposures allowExposures) {
      this.allowExposures = (byte) allowExposures.getValue();
      return this;
    }

    public SetScreenSaver.SetScreenSaverBuilder allowExposures(byte allowExposures) {
      this.allowExposures = allowExposures;
      return this;
    }

    public int getSize() {
      return 10;
    }

    @java.lang.SuppressWarnings("all")
    SetScreenSaverBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetScreenSaver.SetScreenSaverBuilder timeout(final short timeout) {
      this.timeout = timeout;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetScreenSaver.SetScreenSaverBuilder interval(final short interval) {
      this.interval = interval;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetScreenSaver build() {
      return new SetScreenSaver(this.timeout, this.interval, this.preferBlanking, this.allowExposures);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetScreenSaver.SetScreenSaverBuilder(timeout=" + this.timeout + ", interval=" + this.interval + ", preferBlanking=" + this.preferBlanking + ", allowExposures=" + this.allowExposures + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetScreenSaver(final short timeout, final short interval, final byte preferBlanking, final byte allowExposures) {
    this.timeout = timeout;
    this.interval = interval;
    this.preferBlanking = preferBlanking;
    this.allowExposures = allowExposures;
  }

  @java.lang.SuppressWarnings("all")
  public static SetScreenSaver.SetScreenSaverBuilder builder() {
    return new SetScreenSaver.SetScreenSaverBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getTimeout() {
    return this.timeout;
  }

  @java.lang.SuppressWarnings("all")
  public short getInterval() {
    return this.interval;
  }

  @java.lang.SuppressWarnings("all")
  public byte getPreferBlanking() {
    return this.preferBlanking;
  }

  @java.lang.SuppressWarnings("all")
  public byte getAllowExposures() {
    return this.allowExposures;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetScreenSaver)) return false;
    final SetScreenSaver other = (SetScreenSaver) o;
    if (this.getTimeout() != other.getTimeout()) return false;
    if (this.getInterval() != other.getInterval()) return false;
    if (this.getPreferBlanking() != other.getPreferBlanking()) return false;
    if (this.getAllowExposures() != other.getAllowExposures()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getTimeout();
    result = result * PRIME + this.getInterval();
    result = result * PRIME + this.getPreferBlanking();
    result = result * PRIME + this.getAllowExposures();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetScreenSaver(timeout=" + this.getTimeout() + ", interval=" + this.getInterval() + ", preferBlanking=" + this.getPreferBlanking() + ", allowExposures=" + this.getAllowExposures() + ")";
  }
}
