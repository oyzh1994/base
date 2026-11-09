package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class DeleteOutputMode implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 19;
  private final int output;
  private final int mode;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DeleteOutputMode readDeleteOutputMode(X11Input in) throws IOException {
    DeleteOutputMode.DeleteOutputModeBuilder javaBuilder = DeleteOutputMode.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int output = in.readCard32();
    int mode = in.readCard32();
    javaBuilder.output(output);
    javaBuilder.mode(mode);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(output);
    out.writeCard32(mode);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class DeleteOutputModeBuilder {
    @java.lang.SuppressWarnings("all")
    private int output;
    @java.lang.SuppressWarnings("all")
    private int mode;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    DeleteOutputModeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DeleteOutputMode.DeleteOutputModeBuilder output(final int output) {
      this.output = output;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DeleteOutputMode.DeleteOutputModeBuilder mode(final int mode) {
      this.mode = mode;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public DeleteOutputMode build() {
      return new DeleteOutputMode(this.output, this.mode);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "DeleteOutputMode.DeleteOutputModeBuilder(output=" + this.output + ", mode=" + this.mode + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  DeleteOutputMode(final int output, final int mode) {
    this.output = output;
    this.mode = mode;
  }

  @java.lang.SuppressWarnings("all")
  public static DeleteOutputMode.DeleteOutputModeBuilder builder() {
    return new DeleteOutputMode.DeleteOutputModeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getOutput() {
    return this.output;
  }

  @java.lang.SuppressWarnings("all")
  public int getMode() {
    return this.mode;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof DeleteOutputMode)) return false;
    final DeleteOutputMode other = (DeleteOutputMode) o;
    if (this.getOutput() != other.getOutput()) return false;
    if (this.getMode() != other.getMode()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getOutput();
    result = result * PRIME + this.getMode();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "DeleteOutputMode(output=" + this.getOutput() + ", mode=" + this.getMode() + ")";
  }
}
