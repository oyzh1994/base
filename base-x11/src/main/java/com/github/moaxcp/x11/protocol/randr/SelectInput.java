package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SelectInput implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 4;
  private final int window;
  private final short enable;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SelectInput readSelectInput(X11Input in) throws IOException {
    SelectInput.SelectInputBuilder javaBuilder = SelectInput.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    short enable = in.readCard16();
    byte[] pad5 = in.readPad(2);
    javaBuilder.window(window);
    javaBuilder.enable(enable);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard16(enable);
    out.writePad(2);
  }

  public boolean isEnableEnabled(NotifyMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (NotifyMask m : maskEnums) {
      if (!m.isEnabled(enable)) {
        return false;
      }
    }
    return true;
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
    private int window;
    @java.lang.SuppressWarnings("all")
    private short enable;

    public boolean isEnableEnabled(NotifyMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (NotifyMask m : maskEnums) {
        if (!m.isEnabled(enable)) {
          return false;
        }
      }
      return true;
    }

    public SelectInput.SelectInputBuilder enableEnable(NotifyMask... maskEnums) {
      for (NotifyMask m : maskEnums) {
        enable((short) m.enableFor(enable));
      }
      return this;
    }

    public SelectInput.SelectInputBuilder enableDisable(NotifyMask... maskEnums) {
      for (NotifyMask m : maskEnums) {
        enable((short) m.disableFor(enable));
      }
      return this;
    }

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
    public SelectInput.SelectInputBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectInput.SelectInputBuilder enable(final short enable) {
      this.enable = enable;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SelectInput build() {
      return new SelectInput(this.window, this.enable);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SelectInput.SelectInputBuilder(window=" + this.window + ", enable=" + this.enable + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SelectInput(final int window, final short enable) {
    this.window = window;
    this.enable = enable;
  }

  @java.lang.SuppressWarnings("all")
  public static SelectInput.SelectInputBuilder builder() {
    return new SelectInput.SelectInputBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public short getEnable() {
    return this.enable;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SelectInput)) return false;
    final SelectInput other = (SelectInput) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getEnable() != other.getEnable()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getEnable();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SelectInput(window=" + this.getWindow() + ", enable=" + this.getEnable() + ")";
  }
}
