package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class GrabKey implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 33;
  private final boolean ownerEvents;
  private final int grabWindow;
  private final short modifiers;
  private final byte key;
  private final byte pointerMode;
  private final byte keyboardMode;

  public byte getOpCode() {
    return OPCODE;
  }

  public static GrabKey readGrabKey(X11Input in) throws IOException {
    GrabKey.GrabKeyBuilder javaBuilder = GrabKey.builder();
    boolean ownerEvents = in.readBool();
    short length = in.readCard16();
    int grabWindow = in.readCard32();
    short modifiers = in.readCard16();
    byte key = in.readCard8();
    byte pointerMode = in.readCard8();
    byte keyboardMode = in.readCard8();
    byte[] pad8 = in.readPad(3);
    javaBuilder.ownerEvents(ownerEvents);
    javaBuilder.grabWindow(grabWindow);
    javaBuilder.modifiers(modifiers);
    javaBuilder.key(key);
    javaBuilder.pointerMode(pointerMode);
    javaBuilder.keyboardMode(keyboardMode);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeBool(ownerEvents);
    out.writeCard16((short) getLength());
    out.writeCard32(grabWindow);
    out.writeCard16(modifiers);
    out.writeCard8(key);
    out.writeCard8(pointerMode);
    out.writeCard8(keyboardMode);
    out.writePad(3);
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
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GrabKeyBuilder {
    @java.lang.SuppressWarnings("all")
    private boolean ownerEvents;
    @java.lang.SuppressWarnings("all")
    private int grabWindow;
    @java.lang.SuppressWarnings("all")
    private short modifiers;
    @java.lang.SuppressWarnings("all")
    private byte key;
    @java.lang.SuppressWarnings("all")
    private byte pointerMode;
    @java.lang.SuppressWarnings("all")
    private byte keyboardMode;

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

    public GrabKey.GrabKeyBuilder modifiersEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        modifiers((short) m.enableFor(modifiers));
      }
      return this;
    }

    public GrabKey.GrabKeyBuilder modifiersDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        modifiers((short) m.disableFor(modifiers));
      }
      return this;
    }

    public GrabKey.GrabKeyBuilder pointerMode(GrabMode pointerMode) {
      this.pointerMode = (byte) pointerMode.getValue();
      return this;
    }

    public GrabKey.GrabKeyBuilder pointerMode(byte pointerMode) {
      this.pointerMode = pointerMode;
      return this;
    }

    public GrabKey.GrabKeyBuilder keyboardMode(GrabMode keyboardMode) {
      this.keyboardMode = (byte) keyboardMode.getValue();
      return this;
    }

    public GrabKey.GrabKeyBuilder keyboardMode(byte keyboardMode) {
      this.keyboardMode = keyboardMode;
      return this;
    }

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    GrabKeyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabKey.GrabKeyBuilder ownerEvents(final boolean ownerEvents) {
      this.ownerEvents = ownerEvents;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabKey.GrabKeyBuilder grabWindow(final int grabWindow) {
      this.grabWindow = grabWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabKey.GrabKeyBuilder modifiers(final short modifiers) {
      this.modifiers = modifiers;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabKey.GrabKeyBuilder key(final byte key) {
      this.key = key;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GrabKey build() {
      return new GrabKey(this.ownerEvents, this.grabWindow, this.modifiers, this.key, this.pointerMode, this.keyboardMode);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GrabKey.GrabKeyBuilder(ownerEvents=" + this.ownerEvents + ", grabWindow=" + this.grabWindow + ", modifiers=" + this.modifiers + ", key=" + this.key + ", pointerMode=" + this.pointerMode + ", keyboardMode=" + this.keyboardMode + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GrabKey(final boolean ownerEvents, final int grabWindow, final short modifiers, final byte key, final byte pointerMode, final byte keyboardMode) {
    this.ownerEvents = ownerEvents;
    this.grabWindow = grabWindow;
    this.modifiers = modifiers;
    this.key = key;
    this.pointerMode = pointerMode;
    this.keyboardMode = keyboardMode;
  }

  @java.lang.SuppressWarnings("all")
  public static GrabKey.GrabKeyBuilder builder() {
    return new GrabKey.GrabKeyBuilder();
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
  public short getModifiers() {
    return this.modifiers;
  }

  @java.lang.SuppressWarnings("all")
  public byte getKey() {
    return this.key;
  }

  @java.lang.SuppressWarnings("all")
  public byte getPointerMode() {
    return this.pointerMode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getKeyboardMode() {
    return this.keyboardMode;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GrabKey)) return false;
    final GrabKey other = (GrabKey) o;
    if (this.isOwnerEvents() != other.isOwnerEvents()) return false;
    if (this.getGrabWindow() != other.getGrabWindow()) return false;
    if (this.getModifiers() != other.getModifiers()) return false;
    if (this.getKey() != other.getKey()) return false;
    if (this.getPointerMode() != other.getPointerMode()) return false;
    if (this.getKeyboardMode() != other.getKeyboardMode()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + (this.isOwnerEvents() ? 79 : 97);
    result = result * PRIME + this.getGrabWindow();
    result = result * PRIME + this.getModifiers();
    result = result * PRIME + this.getKey();
    result = result * PRIME + this.getPointerMode();
    result = result * PRIME + this.getKeyboardMode();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GrabKey(ownerEvents=" + this.isOwnerEvents() + ", grabWindow=" + this.getGrabWindow() + ", modifiers=" + this.getModifiers() + ", key=" + this.getKey() + ", pointerMode=" + this.getPointerMode() + ", keyboardMode=" + this.getKeyboardMode() + ")";
  }
}
