package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class DeleteOutputProperty implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 14;
  private final int output;
  private final int property;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DeleteOutputProperty readDeleteOutputProperty(X11Input in) throws IOException {
    DeleteOutputProperty.DeleteOutputPropertyBuilder javaBuilder = DeleteOutputProperty.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int output = in.readCard32();
    int property = in.readCard32();
    javaBuilder.output(output);
    javaBuilder.property(property);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(output);
    out.writeCard32(property);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class DeleteOutputPropertyBuilder {
    @java.lang.SuppressWarnings("all")
    private int output;
    @java.lang.SuppressWarnings("all")
    private int property;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    DeleteOutputPropertyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DeleteOutputProperty.DeleteOutputPropertyBuilder output(final int output) {
      this.output = output;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DeleteOutputProperty.DeleteOutputPropertyBuilder property(final int property) {
      this.property = property;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public DeleteOutputProperty build() {
      return new DeleteOutputProperty(this.output, this.property);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "DeleteOutputProperty.DeleteOutputPropertyBuilder(output=" + this.output + ", property=" + this.property + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  DeleteOutputProperty(final int output, final int property) {
    this.output = output;
    this.property = property;
  }

  @java.lang.SuppressWarnings("all")
  public static DeleteOutputProperty.DeleteOutputPropertyBuilder builder() {
    return new DeleteOutputProperty.DeleteOutputPropertyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getOutput() {
    return this.output;
  }

  @java.lang.SuppressWarnings("all")
  public int getProperty() {
    return this.property;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof DeleteOutputProperty)) return false;
    final DeleteOutputProperty other = (DeleteOutputProperty) o;
    if (this.getOutput() != other.getOutput()) return false;
    if (this.getProperty() != other.getProperty()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getOutput();
    result = result * PRIME + this.getProperty();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "DeleteOutputProperty(output=" + this.getOutput() + ", property=" + this.getProperty() + ")";
  }
}
