package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ChangeSaveSet implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 6;
  private final byte mode;
  private final int window;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeSaveSet readChangeSaveSet(X11Input in) throws IOException {
    ChangeSaveSet.ChangeSaveSetBuilder javaBuilder = ChangeSaveSet.builder();
    byte mode = in.readByte();
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.mode(mode);
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeByte(mode);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ChangeSaveSetBuilder {
    @java.lang.SuppressWarnings("all")
    private byte mode;
    @java.lang.SuppressWarnings("all")
    private int window;

    public ChangeSaveSet.ChangeSaveSetBuilder mode(SetMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public ChangeSaveSet.ChangeSaveSetBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    ChangeSaveSetBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeSaveSet.ChangeSaveSetBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ChangeSaveSet build() {
      return new ChangeSaveSet(this.mode, this.window);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ChangeSaveSet.ChangeSaveSetBuilder(mode=" + this.mode + ", window=" + this.window + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ChangeSaveSet(final byte mode, final int window) {
    this.mode = mode;
    this.window = window;
  }

  @java.lang.SuppressWarnings("all")
  public static ChangeSaveSet.ChangeSaveSetBuilder builder() {
    return new ChangeSaveSet.ChangeSaveSetBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getMode() {
    return this.mode;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ChangeSaveSet)) return false;
    final ChangeSaveSet other = (ChangeSaveSet) o;
    if (this.getMode() != other.getMode()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getMode();
    result = result * PRIME + this.getWindow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ChangeSaveSet(mode=" + this.getMode() + ", window=" + this.getWindow() + ")";
  }
}
