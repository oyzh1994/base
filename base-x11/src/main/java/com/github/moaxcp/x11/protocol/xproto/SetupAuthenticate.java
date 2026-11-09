package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class SetupAuthenticate implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final byte status;
  private final ByteList reason;

  public static SetupAuthenticate readSetupAuthenticate(X11Input in) throws IOException {
    SetupAuthenticate.SetupAuthenticateBuilder javaBuilder = SetupAuthenticate.builder();
    byte status = in.readCard8();
    byte[] pad1 = in.readPad(5);
    short length = in.readCard16();
    ByteList reason = in.readChar(Short.toUnsignedInt(length) * 4);
    javaBuilder.status(status);
    javaBuilder.reason(reason.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(status);
    out.writePad(5);
    short length = (short) reason.size();
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


  public static class SetupAuthenticateBuilder {
    @java.lang.SuppressWarnings("all")
    private byte status;
    @java.lang.SuppressWarnings("all")
    private ByteList reason;

    public int getSize() {
      return 8 + 1 * reason.size();
    }

    @java.lang.SuppressWarnings("all")
    SetupAuthenticateBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetupAuthenticate.SetupAuthenticateBuilder status(final byte status) {
      this.status = status;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetupAuthenticate.SetupAuthenticateBuilder reason(final ByteList reason) {
      if (reason == null) {
        throw new java.lang.NullPointerException("reason is marked non-null but is null");
      }
      this.reason = reason;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetupAuthenticate build() {
      return new SetupAuthenticate(this.status, this.reason);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetupAuthenticate.SetupAuthenticateBuilder(status=" + this.status + ", reason=" + this.reason + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetupAuthenticate(final byte status, final ByteList reason) {
    if (reason == null) {
      throw new java.lang.NullPointerException("reason is marked non-null but is null");
    }
    this.status = status;
    this.reason = reason;
  }

  @java.lang.SuppressWarnings("all")
  public static SetupAuthenticate.SetupAuthenticateBuilder builder() {
    return new SetupAuthenticate.SetupAuthenticateBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getStatus() {
    return this.status;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getReason() {
    return this.reason;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetupAuthenticate)) return false;
    final SetupAuthenticate other = (SetupAuthenticate) o;
    if (this.getStatus() != other.getStatus()) return false;
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
    final java.lang.Object $reason = this.getReason();
    result = result * PRIME + ($reason == null ? 43 : $reason.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetupAuthenticate(status=" + this.getStatus() + ", reason=" + this.getReason() + ")";
  }
}
