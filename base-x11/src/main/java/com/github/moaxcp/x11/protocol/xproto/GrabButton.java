package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class GrabButton implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 28;
  private final boolean ownerEvents;
  private final int grabWindow;
  private final short eventMask;
  private final byte pointerMode;
  private final byte keyboardMode;
  private final int confineTo;
  private final int cursor;
  private final byte button;
  private final short modifiers;

  public byte getOpCode() {
    return OPCODE;
  }

  public static GrabButton readGrabButton(X11Input in) throws IOException {
    GrabButton.GrabButtonBuilder javaBuilder = GrabButton.builder();
    boolean ownerEvents = in.readBool();
    short length = in.readCard16();
    int grabWindow = in.readCard32();
    short eventMask = in.readCard16();
    byte pointerMode = in.readCard8();
    byte keyboardMode = in.readCard8();
    int confineTo = in.readCard32();
    int cursor = in.readCard32();
    byte button = in.readCard8();
    byte[] pad10 = in.readPad(1);
    short modifiers = in.readCard16();
    javaBuilder.ownerEvents(ownerEvents);
    javaBuilder.grabWindow(grabWindow);
    javaBuilder.eventMask(eventMask);
    javaBuilder.pointerMode(pointerMode);
    javaBuilder.keyboardMode(keyboardMode);
    javaBuilder.confineTo(confineTo);
    javaBuilder.cursor(cursor);
    javaBuilder.button(button);
    javaBuilder.modifiers(modifiers);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeBool(ownerEvents);
    out.writeCard16((short) getLength());
    out.writeCard32(grabWindow);
    out.writeCard16(eventMask);
    out.writeCard8(pointerMode);
    out.writeCard8(keyboardMode);
    out.writeCard32(confineTo);
    out.writeCard32(cursor);
    out.writeCard8(button);
    out.writePad(1);
    out.writeCard16(modifiers);
  }

  public boolean isEventMaskEnabled(EventMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (EventMask m : maskEnums) {
      if (!m.isEnabled(eventMask)) {
        return false;
      }
    }
    return true;
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
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GrabButtonBuilder {
    @java.lang.SuppressWarnings("all")
    private boolean ownerEvents;
    @java.lang.SuppressWarnings("all")
    private int grabWindow;
    @java.lang.SuppressWarnings("all")
    private short eventMask;
    @java.lang.SuppressWarnings("all")
    private byte pointerMode;
    @java.lang.SuppressWarnings("all")
    private byte keyboardMode;
    @java.lang.SuppressWarnings("all")
    private int confineTo;
    @java.lang.SuppressWarnings("all")
    private int cursor;
    @java.lang.SuppressWarnings("all")
    private byte button;
    @java.lang.SuppressWarnings("all")
    private short modifiers;

    public boolean isEventMaskEnabled(EventMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (EventMask m : maskEnums) {
        if (!m.isEnabled(eventMask)) {
          return false;
        }
      }
      return true;
    }

    public GrabButton.GrabButtonBuilder eventMaskEnable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        eventMask((short) m.enableFor(eventMask));
      }
      return this;
    }

    public GrabButton.GrabButtonBuilder eventMaskDisable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        eventMask((short) m.disableFor(eventMask));
      }
      return this;
    }

    public GrabButton.GrabButtonBuilder pointerMode(GrabMode pointerMode) {
      this.pointerMode = (byte) pointerMode.getValue();
      return this;
    }

    public GrabButton.GrabButtonBuilder pointerMode(byte pointerMode) {
      this.pointerMode = pointerMode;
      return this;
    }

    public GrabButton.GrabButtonBuilder keyboardMode(GrabMode keyboardMode) {
      this.keyboardMode = (byte) keyboardMode.getValue();
      return this;
    }

    public GrabButton.GrabButtonBuilder keyboardMode(byte keyboardMode) {
      this.keyboardMode = keyboardMode;
      return this;
    }

    public GrabButton.GrabButtonBuilder button(ButtonIndex button) {
      this.button = (byte) button.getValue();
      return this;
    }

    public GrabButton.GrabButtonBuilder button(byte button) {
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

    public GrabButton.GrabButtonBuilder modifiersEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        modifiers((short) m.enableFor(modifiers));
      }
      return this;
    }

    public GrabButton.GrabButtonBuilder modifiersDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        modifiers((short) m.disableFor(modifiers));
      }
      return this;
    }

    public int getSize() {
      return 24;
    }

    @java.lang.SuppressWarnings("all")
    GrabButtonBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabButton.GrabButtonBuilder ownerEvents(final boolean ownerEvents) {
      this.ownerEvents = ownerEvents;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabButton.GrabButtonBuilder grabWindow(final int grabWindow) {
      this.grabWindow = grabWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabButton.GrabButtonBuilder eventMask(final short eventMask) {
      this.eventMask = eventMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabButton.GrabButtonBuilder confineTo(final int confineTo) {
      this.confineTo = confineTo;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabButton.GrabButtonBuilder cursor(final int cursor) {
      this.cursor = cursor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabButton.GrabButtonBuilder modifiers(final short modifiers) {
      this.modifiers = modifiers;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GrabButton build() {
      return new GrabButton(this.ownerEvents, this.grabWindow, this.eventMask, this.pointerMode, this.keyboardMode, this.confineTo, this.cursor, this.button, this.modifiers);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GrabButton.GrabButtonBuilder(ownerEvents=" + this.ownerEvents + ", grabWindow=" + this.grabWindow + ", eventMask=" + this.eventMask + ", pointerMode=" + this.pointerMode + ", keyboardMode=" + this.keyboardMode + ", confineTo=" + this.confineTo + ", cursor=" + this.cursor + ", button=" + this.button + ", modifiers=" + this.modifiers + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GrabButton(final boolean ownerEvents, final int grabWindow, final short eventMask, final byte pointerMode, final byte keyboardMode, final int confineTo, final int cursor, final byte button, final short modifiers) {
    this.ownerEvents = ownerEvents;
    this.grabWindow = grabWindow;
    this.eventMask = eventMask;
    this.pointerMode = pointerMode;
    this.keyboardMode = keyboardMode;
    this.confineTo = confineTo;
    this.cursor = cursor;
    this.button = button;
    this.modifiers = modifiers;
  }

  @java.lang.SuppressWarnings("all")
  public static GrabButton.GrabButtonBuilder builder() {
    return new GrabButton.GrabButtonBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public boolean isOwnerEvents() {
    return this.ownerEvents;
  }

  @java.lang.SuppressWarnings("all")
  public int getGrabWindow() {
    return this.grabWindow;
  }

  @java.lang.SuppressWarnings("all")
  public short getEventMask() {
    return this.eventMask;
  }

  @java.lang.SuppressWarnings("all")
  public byte getPointerMode() {
    return this.pointerMode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getKeyboardMode() {
    return this.keyboardMode;
  }

  @java.lang.SuppressWarnings("all")
  public int getConfineTo() {
    return this.confineTo;
  }

  @java.lang.SuppressWarnings("all")
  public int getCursor() {
    return this.cursor;
  }

  @java.lang.SuppressWarnings("all")
  public byte getButton() {
    return this.button;
  }

  @java.lang.SuppressWarnings("all")
  public short getModifiers() {
    return this.modifiers;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GrabButton)) return false;
    final GrabButton other = (GrabButton) o;
    if (this.isOwnerEvents() != other.isOwnerEvents()) return false;
    if (this.getGrabWindow() != other.getGrabWindow()) return false;
    if (this.getEventMask() != other.getEventMask()) return false;
    if (this.getPointerMode() != other.getPointerMode()) return false;
    if (this.getKeyboardMode() != other.getKeyboardMode()) return false;
    if (this.getConfineTo() != other.getConfineTo()) return false;
    if (this.getCursor() != other.getCursor()) return false;
    if (this.getButton() != other.getButton()) return false;
    if (this.getModifiers() != other.getModifiers()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + (this.isOwnerEvents() ? 79 : 97);
    result = result * PRIME + this.getGrabWindow();
    result = result * PRIME + this.getEventMask();
    result = result * PRIME + this.getPointerMode();
    result = result * PRIME + this.getKeyboardMode();
    result = result * PRIME + this.getConfineTo();
    result = result * PRIME + this.getCursor();
    result = result * PRIME + this.getButton();
    result = result * PRIME + this.getModifiers();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GrabButton(ownerEvents=" + this.isOwnerEvents() + ", grabWindow=" + this.getGrabWindow() + ", eventMask=" + this.getEventMask() + ", pointerMode=" + this.getPointerMode() + ", keyboardMode=" + this.getKeyboardMode() + ", confineTo=" + this.getConfineTo() + ", cursor=" + this.getCursor() + ", button=" + this.getButton() + ", modifiers=" + this.getModifiers() + ")";
  }
}
