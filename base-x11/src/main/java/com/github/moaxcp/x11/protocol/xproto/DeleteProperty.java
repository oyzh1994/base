package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class DeleteProperty implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 19;
  private final int window;
  private final int property;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DeleteProperty readDeleteProperty(X11Input in) throws IOException {
    DeleteProperty.DeletePropertyBuilder javaBuilder = DeleteProperty.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    int property = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.property(property);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(property);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class DeletePropertyBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int property;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    DeletePropertyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DeleteProperty.DeletePropertyBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DeleteProperty.DeletePropertyBuilder property(final int property) {
      this.property = property;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public DeleteProperty build() {
      return new DeleteProperty(this.window, this.property);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "DeleteProperty.DeletePropertyBuilder(window=" + this.window + ", property=" + this.property + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  DeleteProperty(final int window, final int property) {
    this.window = window;
    this.property = property;
  }

  @java.lang.SuppressWarnings("all")
  public static DeleteProperty.DeletePropertyBuilder builder() {
    return new DeleteProperty.DeletePropertyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getProperty() {
    return this.property;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof DeleteProperty)) return false;
    final DeleteProperty other = (DeleteProperty) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getProperty() != other.getProperty()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getProperty();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "DeleteProperty(window=" + this.getWindow() + ", property=" + this.getProperty() + ")";
  }
}
