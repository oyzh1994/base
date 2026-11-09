package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class InputSelected implements TwoWayRequest<InputSelectedReply> {
  public static final String PLUGIN_NAME = "shape";
  public static final byte OPCODE = 7;
  private final int destinationWindow;

  public XReplyFunction<InputSelectedReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> InputSelectedReply.readInputSelectedReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static InputSelected readInputSelected(X11Input in) throws IOException {
    InputSelected.InputSelectedBuilder javaBuilder = InputSelected.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int destinationWindow = in.readCard32();
    javaBuilder.destinationWindow(destinationWindow);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(destinationWindow);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class InputSelectedBuilder {
    @java.lang.SuppressWarnings("all")
    private int destinationWindow;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    InputSelectedBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public InputSelected.InputSelectedBuilder destinationWindow(final int destinationWindow) {
      this.destinationWindow = destinationWindow;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public InputSelected build() {
      return new InputSelected(this.destinationWindow);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "InputSelected.InputSelectedBuilder(destinationWindow=" + this.destinationWindow + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  InputSelected(final int destinationWindow) {
    this.destinationWindow = destinationWindow;
  }

  @java.lang.SuppressWarnings("all")
  public static InputSelected.InputSelectedBuilder builder() {
    return new InputSelected.InputSelectedBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDestinationWindow() {
    return this.destinationWindow;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof InputSelected)) return false;
    final InputSelected other = (InputSelected) o;
    if (this.getDestinationWindow() != other.getDestinationWindow()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDestinationWindow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "InputSelected(destinationWindow=" + this.getDestinationWindow() + ")";
  }
}
