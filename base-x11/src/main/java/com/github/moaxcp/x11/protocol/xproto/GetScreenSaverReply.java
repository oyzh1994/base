package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetScreenSaverReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final short timeout;
  private final short interval;
  private final byte preferBlanking;
  private final byte allowExposures;

  public static GetScreenSaverReply readGetScreenSaverReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetScreenSaverReply.GetScreenSaverReplyBuilder javaBuilder = GetScreenSaverReply.builder();
    int length = in.readCard32();
    short timeout = in.readCard16();
    short interval = in.readCard16();
    byte preferBlanking = in.readByte();
    byte allowExposures = in.readByte();
    byte[] pad8 = in.readPad(18);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timeout(timeout);
    javaBuilder.interval(interval);
    javaBuilder.preferBlanking(preferBlanking);
    javaBuilder.allowExposures(allowExposures);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(timeout);
    out.writeCard16(interval);
    out.writeByte(preferBlanking);
    out.writeByte(allowExposures);
    out.writePad(18);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetScreenSaverReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short timeout;
    @java.lang.SuppressWarnings("all")
    private short interval;
    @java.lang.SuppressWarnings("all")
    private byte preferBlanking;
    @java.lang.SuppressWarnings("all")
    private byte allowExposures;

    public GetScreenSaverReply.GetScreenSaverReplyBuilder preferBlanking(Blanking preferBlanking) {
      this.preferBlanking = (byte) preferBlanking.getValue();
      return this;
    }

    public GetScreenSaverReply.GetScreenSaverReplyBuilder preferBlanking(byte preferBlanking) {
      this.preferBlanking = preferBlanking;
      return this;
    }

    public GetScreenSaverReply.GetScreenSaverReplyBuilder allowExposures(Exposures allowExposures) {
      this.allowExposures = (byte) allowExposures.getValue();
      return this;
    }

    public GetScreenSaverReply.GetScreenSaverReplyBuilder allowExposures(byte allowExposures) {
      this.allowExposures = allowExposures;
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    GetScreenSaverReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenSaverReply.GetScreenSaverReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenSaverReply.GetScreenSaverReplyBuilder timeout(final short timeout) {
      this.timeout = timeout;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenSaverReply.GetScreenSaverReplyBuilder interval(final short interval) {
      this.interval = interval;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetScreenSaverReply build() {
      return new GetScreenSaverReply(this.sequenceNumber, this.timeout, this.interval, this.preferBlanking, this.allowExposures);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetScreenSaverReply.GetScreenSaverReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", timeout=" + this.timeout + ", interval=" + this.interval + ", preferBlanking=" + this.preferBlanking + ", allowExposures=" + this.allowExposures + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetScreenSaverReply(final short sequenceNumber, final short timeout, final short interval, final byte preferBlanking, final byte allowExposures) {
    this.sequenceNumber = sequenceNumber;
    this.timeout = timeout;
    this.interval = interval;
    this.preferBlanking = preferBlanking;
    this.allowExposures = allowExposures;
  }

  @java.lang.SuppressWarnings("all")
  public static GetScreenSaverReply.GetScreenSaverReplyBuilder builder() {
    return new GetScreenSaverReply.GetScreenSaverReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
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
    if (!(o instanceof GetScreenSaverReply)) return false;
    final GetScreenSaverReply other = (GetScreenSaverReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
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
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getTimeout();
    result = result * PRIME + this.getInterval();
    result = result * PRIME + this.getPreferBlanking();
    result = result * PRIME + this.getAllowExposures();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetScreenSaverReply(sequenceNumber=" + this.getSequenceNumber() + ", timeout=" + this.getTimeout() + ", interval=" + this.getInterval() + ", preferBlanking=" + this.getPreferBlanking() + ", allowExposures=" + this.getAllowExposures() + ")";
  }
}
