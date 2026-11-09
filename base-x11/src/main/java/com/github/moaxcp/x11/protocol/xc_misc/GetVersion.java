package com.github.moaxcp.x11.protocol.xc_misc;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetVersion implements TwoWayRequest<GetVersionReply> {
  public static final String PLUGIN_NAME = "xc_misc";
  public static final byte OPCODE = 0;
  private final short clientMajorVersion;
  private final short clientMinorVersion;

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
    short clientMajorVersion = in.readCard16();
    short clientMinorVersion = in.readCard16();
    javaBuilder.clientMajorVersion(clientMajorVersion);
    javaBuilder.clientMinorVersion(clientMinorVersion);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(clientMajorVersion);
    out.writeCard16(clientMinorVersion);
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
    private short clientMajorVersion;
    @java.lang.SuppressWarnings("all")
    private short clientMinorVersion;

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
    public GetVersion.GetVersionBuilder clientMajorVersion(final short clientMajorVersion) {
      this.clientMajorVersion = clientMajorVersion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetVersion.GetVersionBuilder clientMinorVersion(final short clientMinorVersion) {
      this.clientMinorVersion = clientMinorVersion;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetVersion build() {
      return new GetVersion(this.clientMajorVersion, this.clientMinorVersion);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetVersion.GetVersionBuilder(clientMajorVersion=" + this.clientMajorVersion + ", clientMinorVersion=" + this.clientMinorVersion + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetVersion(final short clientMajorVersion, final short clientMinorVersion) {
    this.clientMajorVersion = clientMajorVersion;
    this.clientMinorVersion = clientMinorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public static GetVersion.GetVersionBuilder builder() {
    return new GetVersion.GetVersionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getClientMajorVersion() {
    return this.clientMajorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public short getClientMinorVersion() {
    return this.clientMinorVersion;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetVersion)) return false;
    final GetVersion other = (GetVersion) o;
    if (this.getClientMajorVersion() != other.getClientMajorVersion()) return false;
    if (this.getClientMinorVersion() != other.getClientMinorVersion()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getClientMajorVersion();
    result = result * PRIME + this.getClientMinorVersion();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetVersion(clientMajorVersion=" + this.getClientMajorVersion() + ", clientMinorVersion=" + this.getClientMinorVersion() + ")";
  }
}
