package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class SetupAuthenticate implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final byte status;
  private final byte[] reason;

  public static SetupAuthenticate readSetupAuthenticate(X11Input in) throws IOException {
    SetupAuthenticate.SetupAuthenticateBuilder javaBuilder = SetupAuthenticate.builder();
    byte status = in.readCard8();
    byte[] pad1 = in.readPad(5);
    short length = in.readCard16();
    byte[] reason = in.readChar(Short.toUnsignedInt(length) * 4);
    javaBuilder.status(status);
    javaBuilder.reason(reason);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(status);
    out.writePad(5);
    short length = (short) reason.length;
    out.writeCard16(length);
    out.writeChar(reason);
  }

  @Override
  public int getSize() {
    return 8 + 1 * reason.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetupAuthenticateBuilder {
    @java.lang.SuppressWarnings("all")
    private byte status;
    @java.lang.SuppressWarnings("all")
    private byte[] reason;

    public int getSize() {
      return 8 + 1 * reason.length;
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
    public SetupAuthenticate.SetupAuthenticateBuilder reason(final byte[] reason) {
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
      return "SetupAuthenticate.SetupAuthenticateBuilder(status=" + this.status + ", reason=" + java.util.Arrays.toString(this.reason) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetupAuthenticate(final byte status, final byte[] reason) {
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
  public byte[] getReason() {
    return this.reason;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetupAuthenticate)) return false;
    final SetupAuthenticate other = (SetupAuthenticate) o;
    if (this.getStatus() != other.getStatus()) return false;
    if (!java.util.Arrays.equals(this.getReason(), other.getReason())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getStatus();
    result = result * PRIME + java.util.Arrays.hashCode(this.getReason());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetupAuthenticate(status=" + this.getStatus() + ", reason=" + java.util.Arrays.toString(this.getReason()) + ")";
  }
}
