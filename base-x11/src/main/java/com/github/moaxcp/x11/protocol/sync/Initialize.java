package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class Initialize implements TwoWayRequest<InitializeReply> {
  public static final String PLUGIN_NAME = "sync";
  public static final byte OPCODE = 0;
  private final byte desiredMajorVersion;
  private final byte desiredMinorVersion;

  public XReplyFunction<InitializeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> InitializeReply.readInitializeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static Initialize readInitialize(X11Input in) throws IOException {
    Initialize.InitializeBuilder javaBuilder = Initialize.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte desiredMajorVersion = in.readCard8();
    byte desiredMinorVersion = in.readCard8();
    javaBuilder.desiredMajorVersion(desiredMajorVersion);
    javaBuilder.desiredMinorVersion(desiredMinorVersion);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(desiredMajorVersion);
    out.writeCard8(desiredMinorVersion);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 6;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class InitializeBuilder {
    @java.lang.SuppressWarnings("all")
    private byte desiredMajorVersion;
    @java.lang.SuppressWarnings("all")
    private byte desiredMinorVersion;

    public int getSize() {
      return 6;
    }

    @java.lang.SuppressWarnings("all")
    InitializeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Initialize.InitializeBuilder desiredMajorVersion(final byte desiredMajorVersion) {
      this.desiredMajorVersion = desiredMajorVersion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Initialize.InitializeBuilder desiredMinorVersion(final byte desiredMinorVersion) {
      this.desiredMinorVersion = desiredMinorVersion;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Initialize build() {
      return new Initialize(this.desiredMajorVersion, this.desiredMinorVersion);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Initialize.InitializeBuilder(desiredMajorVersion=" + this.desiredMajorVersion + ", desiredMinorVersion=" + this.desiredMinorVersion + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Initialize(final byte desiredMajorVersion, final byte desiredMinorVersion) {
    this.desiredMajorVersion = desiredMajorVersion;
    this.desiredMinorVersion = desiredMinorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public static Initialize.InitializeBuilder builder() {
    return new Initialize.InitializeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getDesiredMajorVersion() {
    return this.desiredMajorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public byte getDesiredMinorVersion() {
    return this.desiredMinorVersion;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Initialize)) return false;
    final Initialize other = (Initialize) o;
    if (this.getDesiredMajorVersion() != other.getDesiredMajorVersion()) return false;
    if (this.getDesiredMinorVersion() != other.getDesiredMinorVersion()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDesiredMajorVersion();
    result = result * PRIME + this.getDesiredMinorVersion();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Initialize(desiredMajorVersion=" + this.getDesiredMajorVersion() + ", desiredMinorVersion=" + this.getDesiredMinorVersion() + ")";
  }
}
