package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class SetupFailed implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final byte status;
  private final short protocolMajorVersion;
  private final short protocolMinorVersion;
  private final short length;
  private final ByteList reason;

  public static SetupFailed readSetupFailed(X11Input in) throws IOException {
    SetupFailed.SetupFailedBuilder javaBuilder = SetupFailed.builder();
    byte status = in.readCard8();
    byte reasonLen = in.readCard8();
    short protocolMajorVersion = in.readCard16();
    short protocolMinorVersion = in.readCard16();
    short length = in.readCard16();
    ByteList reason = in.readChar(Byte.toUnsignedInt(reasonLen));
    javaBuilder.status(status);
    javaBuilder.protocolMajorVersion(protocolMajorVersion);
    javaBuilder.protocolMinorVersion(protocolMinorVersion);
    javaBuilder.length(length);
    javaBuilder.reason(reason.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(status);
    byte reasonLen = (byte) reason.size();
    out.writeCard8(reasonLen);
    out.writeCard16(protocolMajorVersion);
    out.writeCard16(protocolMinorVersion);
    out.writeCard16(length);
    out.writeChar(reason);
  }

  @Override
  public int getSize() {
    return 8 + 1 * reason.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetupFailedBuilder {
    @java.lang.SuppressWarnings("all")
    private byte status;
    @java.lang.SuppressWarnings("all")
    private short protocolMajorVersion;
    @java.lang.SuppressWarnings("all")
    private short protocolMinorVersion;
    @java.lang.SuppressWarnings("all")
    private short length;
    @java.lang.SuppressWarnings("all")
    private ByteList reason;

    public int getSize() {
      return 8 + 1 * reason.size();
    }

    @java.lang.SuppressWarnings("all")
    SetupFailedBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetupFailed.SetupFailedBuilder status(final byte status) {
      this.status = status;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetupFailed.SetupFailedBuilder protocolMajorVersion(final short protocolMajorVersion) {
      this.protocolMajorVersion = protocolMajorVersion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetupFailed.SetupFailedBuilder protocolMinorVersion(final short protocolMinorVersion) {
      this.protocolMinorVersion = protocolMinorVersion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetupFailed.SetupFailedBuilder length(final short length) {
      this.length = length;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetupFailed.SetupFailedBuilder reason(final ByteList reason) {
      if (reason == null) {
        throw new java.lang.NullPointerException("reason is marked non-null but is null");
      }
      this.reason = reason;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetupFailed build() {
      return new SetupFailed(this.status, this.protocolMajorVersion, this.protocolMinorVersion, this.length, this.reason);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetupFailed.SetupFailedBuilder(status=" + this.status + ", protocolMajorVersion=" + this.protocolMajorVersion + ", protocolMinorVersion=" + this.protocolMinorVersion + ", length=" + this.length + ", reason=" + this.reason + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetupFailed(final byte status, final short protocolMajorVersion, final short protocolMinorVersion, final short length, final ByteList reason) {
    if (reason == null) {
      throw new java.lang.NullPointerException("reason is marked non-null but is null");
    }
    this.status = status;
    this.protocolMajorVersion = protocolMajorVersion;
    this.protocolMinorVersion = protocolMinorVersion;
    this.length = length;
    this.reason = reason;
  }

  @java.lang.SuppressWarnings("all")
  public static SetupFailed.SetupFailedBuilder builder() {
    return new SetupFailed.SetupFailedBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getStatus() {
    return this.status;
  }

  @java.lang.SuppressWarnings("all")
  public short getProtocolMajorVersion() {
    return this.protocolMajorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public short getProtocolMinorVersion() {
    return this.protocolMinorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public short getLength() {
    return this.length;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getReason() {
    return this.reason;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetupFailed)) return false;
    final SetupFailed other = (SetupFailed) o;
    if (this.getStatus() != other.getStatus()) return false;
    if (this.getProtocolMajorVersion() != other.getProtocolMajorVersion()) return false;
    if (this.getProtocolMinorVersion() != other.getProtocolMinorVersion()) return false;
    if (this.getLength() != other.getLength()) return false;
    final java.lang.Object this$reason = this.getReason();
    final java.lang.Object other$reason = other.getReason();
    if (this$reason == null ? other$reason != null : !this$reason.equals(other$reason)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getStatus();
    result = result * PRIME + this.getProtocolMajorVersion();
    result = result * PRIME + this.getProtocolMinorVersion();
    result = result * PRIME + this.getLength();
    final java.lang.Object $reason = this.getReason();
    result = result * PRIME + ($reason == null ? 43 : $reason.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetupFailed(status=" + this.getStatus() + ", protocolMajorVersion=" + this.getProtocolMajorVersion() + ", protocolMinorVersion=" + this.getProtocolMinorVersion() + ", length=" + this.getLength() + ", reason=" + this.getReason() + ")";
  }
}
