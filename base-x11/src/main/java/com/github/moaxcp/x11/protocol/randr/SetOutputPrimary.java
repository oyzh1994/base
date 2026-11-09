package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SetOutputPrimary implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 30;
  private final int window;
  private final int output;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetOutputPrimary readSetOutputPrimary(X11Input in) throws IOException {
    SetOutputPrimary.SetOutputPrimaryBuilder javaBuilder = SetOutputPrimary.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int output = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.output(output);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(output);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetOutputPrimaryBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int output;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    SetOutputPrimaryBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetOutputPrimary.SetOutputPrimaryBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetOutputPrimary.SetOutputPrimaryBuilder output(final int output) {
      this.output = output;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetOutputPrimary build() {
      return new SetOutputPrimary(this.window, this.output);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetOutputPrimary.SetOutputPrimaryBuilder(window=" + this.window + ", output=" + this.output + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetOutputPrimary(final int window, final int output) {
    this.window = window;
    this.output = output;
  }

  @java.lang.SuppressWarnings("all")
  public static SetOutputPrimary.SetOutputPrimaryBuilder builder() {
    return new SetOutputPrimary.SetOutputPrimaryBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getOutput() {
    return this.output;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetOutputPrimary)) return false;
    final SetOutputPrimary other = (SetOutputPrimary) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getOutput() != other.getOutput()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getOutput();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetOutputPrimary(window=" + this.getWindow() + ", output=" + this.getOutput() + ")";
  }
}
