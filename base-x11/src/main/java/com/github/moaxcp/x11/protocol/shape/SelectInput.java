package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SelectInput implements OneWayRequest {
  public static final String PLUGIN_NAME = "shape";
  public static final byte OPCODE = 6;
  private final int destinationWindow;
  private final boolean enable;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SelectInput readSelectInput(X11Input in) throws IOException {
    SelectInput.SelectInputBuilder javaBuilder = SelectInput.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int destinationWindow = in.readCard32();
    boolean enable = in.readBool();
    byte[] pad5 = in.readPad(3);
    javaBuilder.destinationWindow(destinationWindow);
    javaBuilder.enable(enable);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(destinationWindow);
    out.writeBool(enable);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SelectInputBuilder {
    @java.lang.SuppressWarnings("all")
    private int destinationWindow;
    @java.lang.SuppressWarnings("all")
    private boolean enable;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    SelectInputBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectInput.SelectInputBuilder destinationWindow(final int destinationWindow) {
      this.destinationWindow = destinationWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectInput.SelectInputBuilder enable(final boolean enable) {
      this.enable = enable;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SelectInput build() {
      return new SelectInput(this.destinationWindow, this.enable);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SelectInput.SelectInputBuilder(destinationWindow=" + this.destinationWindow + ", enable=" + this.enable + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SelectInput(final int destinationWindow, final boolean enable) {
    this.destinationWindow = destinationWindow;
    this.enable = enable;
  }

  @java.lang.SuppressWarnings("all")
  public static SelectInput.SelectInputBuilder builder() {
    return new SelectInput.SelectInputBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDestinationWindow() {
    return this.destinationWindow;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isEnable() {
    return this.enable;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SelectInput)) return false;
    final SelectInput other = (SelectInput) o;
    if (this.getDestinationWindow() != other.getDestinationWindow()) return false;
    if (this.isEnable() != other.isEnable()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDestinationWindow();
    result = result * PRIME + (this.isEnable() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SelectInput(destinationWindow=" + this.getDestinationWindow() + ", enable=" + this.isEnable() + ")";
  }
}
