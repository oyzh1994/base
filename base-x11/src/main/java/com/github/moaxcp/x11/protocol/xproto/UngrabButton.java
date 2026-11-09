package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class UngrabButton implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 29;
  private final byte button;
  private final int grabWindow;
  private final short modifiers;

  public byte getOpCode() {
    return OPCODE;
  }

  public static UngrabButton readUngrabButton(X11Input in) throws IOException {
    UngrabButton.UngrabButtonBuilder javaBuilder = UngrabButton.builder();
    byte button = in.readCard8();
    short length = in.readCard16();
    int grabWindow = in.readCard32();
    short modifiers = in.readCard16();
    byte[] pad5 = in.readPad(2);
    javaBuilder.button(button);
    javaBuilder.grabWindow(grabWindow);
    javaBuilder.modifiers(modifiers);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard8(button);
    out.writeCard16((short) getLength());
    out.writeCard32(grabWindow);
    out.writeCard16(modifiers);
    out.writePad(2);
  }

  public boolean isModifiersEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(modifiers)) {
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


  public static class UngrabButtonBuilder {
    @java.lang.SuppressWarnings("all")
    private byte button;
    @java.lang.SuppressWarnings("all")
    private int grabWindow;
    @java.lang.SuppressWarnings("all")
    private short modifiers;

    public UngrabButton.UngrabButtonBuilder button(ButtonIndex button) {
      this.button = (byte) button.getValue();
      return this;
    }

    public UngrabButton.UngrabButtonBuilder button(byte button) {
      this.button = button;
      return this;
    }

    public boolean isModifiersEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(modifiers)) {
          return false;
        }
      }
      return true;
    }

    public UngrabButton.UngrabButtonBuilder modifiersEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        modifiers((short) m.enableFor(modifiers));
      }
      return this;
    }

    public UngrabButton.UngrabButtonBuilder modifiersDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        modifiers((short) m.disableFor(modifiers));
      }
      return this;
    }

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    UngrabButtonBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UngrabButton.UngrabButtonBuilder grabWindow(final int grabWindow) {
      this.grabWindow = grabWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UngrabButton.UngrabButtonBuilder modifiers(final short modifiers) {
      this.modifiers = modifiers;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public UngrabButton build() {
      return new UngrabButton(this.button, this.grabWindow, this.modifiers);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "UngrabButton.UngrabButtonBuilder(button=" + this.button + ", grabWindow=" + this.grabWindow + ", modifiers=" + this.modifiers + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  UngrabButton(final byte button, final int grabWindow, final short modifiers) {
    this.button = button;
    this.grabWindow = grabWindow;
    this.modifiers = modifiers;
  }

  @java.lang.SuppressWarnings("all")
  public static UngrabButton.UngrabButtonBuilder builder() {
    return new UngrabButton.UngrabButtonBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getButton() {
    return this.button;
  }

  @java.lang.SuppressWarnings("all")
  public int getGrabWindow() {
    return this.grabWindow;
  }

  @java.lang.SuppressWarnings("all")
  public short getModifiers() {
    return this.modifiers;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof UngrabButton)) return false;
    final UngrabButton other = (UngrabButton) o;
    if (this.getButton() != other.getButton()) return false;
    if (this.getGrabWindow() != other.getGrabWindow()) return false;
    if (this.getModifiers() != other.getModifiers()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getButton();
    result = result * PRIME + this.getGrabWindow();
    result = result * PRIME + this.getModifiers();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "UngrabButton(button=" + this.getButton() + ", grabWindow=" + this.getGrabWindow() + ", modifiers=" + this.getModifiers() + ")";
  }
}
