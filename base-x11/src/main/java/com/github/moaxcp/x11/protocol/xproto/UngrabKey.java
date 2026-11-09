package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class UngrabKey implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 34;
  private final byte key;
  private final int grabWindow;
  private final short modifiers;

  public byte getOpCode() {
    return OPCODE;
  }

  public static UngrabKey readUngrabKey(X11Input in) throws IOException {
    UngrabKey.UngrabKeyBuilder javaBuilder = UngrabKey.builder();
    byte key = in.readCard8();
    short length = in.readCard16();
    int grabWindow = in.readCard32();
    short modifiers = in.readCard16();
    byte[] pad5 = in.readPad(2);
    javaBuilder.key(key);
    javaBuilder.grabWindow(grabWindow);
    javaBuilder.modifiers(modifiers);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard8(key);
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


  public static class UngrabKeyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte key;
    @java.lang.SuppressWarnings("all")
    private int grabWindow;
    @java.lang.SuppressWarnings("all")
    private short modifiers;

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

    public UngrabKey.UngrabKeyBuilder modifiersEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        modifiers((short) m.enableFor(modifiers));
      }
      return this;
    }

    public UngrabKey.UngrabKeyBuilder modifiersDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        modifiers((short) m.disableFor(modifiers));
      }
      return this;
    }

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    UngrabKeyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UngrabKey.UngrabKeyBuilder key(final byte key) {
      this.key = key;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UngrabKey.UngrabKeyBuilder grabWindow(final int grabWindow) {
      this.grabWindow = grabWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UngrabKey.UngrabKeyBuilder modifiers(final short modifiers) {
      this.modifiers = modifiers;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public UngrabKey build() {
      return new UngrabKey(this.key, this.grabWindow, this.modifiers);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "UngrabKey.UngrabKeyBuilder(key=" + this.key + ", grabWindow=" + this.grabWindow + ", modifiers=" + this.modifiers + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  UngrabKey(final byte key, final int grabWindow, final short modifiers) {
    this.key = key;
    this.grabWindow = grabWindow;
    this.modifiers = modifiers;
  }

  @java.lang.SuppressWarnings("all")
  public static UngrabKey.UngrabKeyBuilder builder() {
    return new UngrabKey.UngrabKeyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getKey() {
    return this.key;
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
    if (!(o instanceof UngrabKey)) return false;
    final UngrabKey other = (UngrabKey) o;
    if (this.getKey() != other.getKey()) return false;
    if (this.getGrabWindow() != other.getGrabWindow()) return false;
    if (this.getModifiers() != other.getModifiers()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getKey();
    result = result * PRIME + this.getGrabWindow();
    result = result * PRIME + this.getModifiers();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "UngrabKey(key=" + this.getKey() + ", grabWindow=" + this.getGrabWindow() + ", modifiers=" + this.getModifiers() + ")";
  }
}
