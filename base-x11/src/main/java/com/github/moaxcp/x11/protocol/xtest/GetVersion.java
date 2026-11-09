package com.github.moaxcp.x11.protocol.xtest;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetVersion implements TwoWayRequest<GetVersionReply> {
  public static final String PLUGIN_NAME = "xtest";
  public static final byte OPCODE = 0;
  private final byte majorVersion;
  private final short minorVersion;

  public XReplyFunction<GetVersionReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetVersionReply.readGetVersionReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetVersion readGetVersion(X11Input in) throws IOException {
    GetVersion.GetVersionBuilder javaBuilder = GetVersion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte majorVersion = in.readCard8();
    byte[] pad4 = in.readPad(1);
    short minorVersion = in.readCard16();
    javaBuilder.majorVersion(majorVersion);
    javaBuilder.minorVersion(minorVersion);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(majorVersion);
    out.writePad(1);
    out.writeCard16(minorVersion);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetVersionBuilder {
    @java.lang.SuppressWarnings("all")
    private byte majorVersion;
    @java.lang.SuppressWarnings("all")
    private short minorVersion;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    GetVersionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetVersion.GetVersionBuilder majorVersion(final byte majorVersion) {
      this.majorVersion = majorVersion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetVersion.GetVersionBuilder minorVersion(final short minorVersion) {
      this.minorVersion = minorVersion;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetVersion build() {
      return new GetVersion(this.majorVersion, this.minorVersion);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetVersion.GetVersionBuilder(majorVersion=" + this.majorVersion + ", minorVersion=" + this.minorVersion + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetVersion(final byte majorVersion, final short minorVersion) {
    this.majorVersion = majorVersion;
    this.minorVersion = minorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public static GetVersion.GetVersionBuilder builder() {
    return new GetVersion.GetVersionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getMajorVersion() {
    return this.majorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public short getMinorVersion() {
    return this.minorVersion;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetVersion)) return false;
    final GetVersion other = (GetVersion) o;
    if (this.getMajorVersion() != other.getMajorVersion()) return false;
    if (this.getMinorVersion() != other.getMinorVersion()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getMajorVersion();
    result = result * PRIME + this.getMinorVersion();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetVersion(majorVersion=" + this.getMajorVersion() + ", minorVersion=" + this.getMinorVersion() + ")";
  }
}
