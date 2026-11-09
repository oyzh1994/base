package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SetInputFocus implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 42;
  private final byte revertTo;
  private final int focus;
  private final int time;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetInputFocus readSetInputFocus(X11Input in) throws IOException {
    SetInputFocus.SetInputFocusBuilder javaBuilder = SetInputFocus.builder();
    byte revertTo = in.readCard8();
    short length = in.readCard16();
    int focus = in.readCard32();
    int time = in.readCard32();
    javaBuilder.revertTo(revertTo);
    javaBuilder.focus(focus);
    javaBuilder.time(time);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard8(revertTo);
    out.writeCard16((short) getLength());
    out.writeCard32(focus);
    out.writeCard32(time);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetInputFocusBuilder {
    @java.lang.SuppressWarnings("all")
    private byte revertTo;
    @java.lang.SuppressWarnings("all")
    private int focus;
    @java.lang.SuppressWarnings("all")
    private int time;

    public SetInputFocus.SetInputFocusBuilder revertTo(InputFocus revertTo) {
      this.revertTo = (byte) revertTo.getValue();
      return this;
    }

    public SetInputFocus.SetInputFocusBuilder revertTo(byte revertTo) {
      this.revertTo = revertTo;
      return this;
    }

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    SetInputFocusBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetInputFocus.SetInputFocusBuilder focus(final int focus) {
      this.focus = focus;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetInputFocus.SetInputFocusBuilder time(final int time) {
      this.time = time;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetInputFocus build() {
      return new SetInputFocus(this.revertTo, this.focus, this.time);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetInputFocus.SetInputFocusBuilder(revertTo=" + this.revertTo + ", focus=" + this.focus + ", time=" + this.time + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetInputFocus(final byte revertTo, final int focus, final int time) {
    this.revertTo = revertTo;
    this.focus = focus;
    this.time = time;
  }

  @java.lang.SuppressWarnings("all")
  public static SetInputFocus.SetInputFocusBuilder builder() {
    return new SetInputFocus.SetInputFocusBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getRevertTo() {
    return this.revertTo;
  }

  @java.lang.SuppressWarnings("all")
  public int getFocus() {
    return this.focus;
  }

  @java.lang.SuppressWarnings("all")
  public int getTime() {
    return this.time;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetInputFocus)) return false;
    final SetInputFocus other = (SetInputFocus) o;
    if (this.getRevertTo() != other.getRevertTo()) return false;
    if (this.getFocus() != other.getFocus()) return false;
    if (this.getTime() != other.getTime()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getRevertTo();
    result = result * PRIME + this.getFocus();
    result = result * PRIME + this.getTime();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetInputFocus(revertTo=" + this.getRevertTo() + ", focus=" + this.getFocus() + ", time=" + this.getTime() + ")";
  }
}
