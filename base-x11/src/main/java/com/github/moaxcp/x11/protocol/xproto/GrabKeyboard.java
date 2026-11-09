package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GrabKeyboard implements TwoWayRequest<GrabKeyboardReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 31;
  private final boolean ownerEvents;
  private final int grabWindow;
  private final int time;
  private final byte pointerMode;
  private final byte keyboardMode;

  public XReplyFunction<GrabKeyboardReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GrabKeyboardReply.readGrabKeyboardReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GrabKeyboard readGrabKeyboard(X11Input in) throws IOException {
    GrabKeyboard.GrabKeyboardBuilder javaBuilder = GrabKeyboard.builder();
    boolean ownerEvents = in.readBool();
    short length = in.readCard16();
    int grabWindow = in.readCard32();
    int time = in.readCard32();
    byte pointerMode = in.readByte();
    byte keyboardMode = in.readByte();
    byte[] pad7 = in.readPad(2);
    javaBuilder.ownerEvents(ownerEvents);
    javaBuilder.grabWindow(grabWindow);
    javaBuilder.time(time);
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
    out.writeCard32(time);
    out.writeByte(pointerMode);
    out.writeByte(keyboardMode);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GrabKeyboardBuilder {
    @java.lang.SuppressWarnings("all")
    private boolean ownerEvents;
    @java.lang.SuppressWarnings("all")
    private int grabWindow;
    @java.lang.SuppressWarnings("all")
    private int time;
    @java.lang.SuppressWarnings("all")
    private byte pointerMode;
    @java.lang.SuppressWarnings("all")
    private byte keyboardMode;

    public GrabKeyboard.GrabKeyboardBuilder pointerMode(GrabMode pointerMode) {
      this.pointerMode = (byte) pointerMode.getValue();
      return this;
    }

    public GrabKeyboard.GrabKeyboardBuilder pointerMode(byte pointerMode) {
      this.pointerMode = pointerMode;
      return this;
    }

    public GrabKeyboard.GrabKeyboardBuilder keyboardMode(GrabMode keyboardMode) {
      this.keyboardMode = (byte) keyboardMode.getValue();
      return this;
    }

    public GrabKeyboard.GrabKeyboardBuilder keyboardMode(byte keyboardMode) {
      this.keyboardMode = keyboardMode;
      return this;
    }

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    GrabKeyboardBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabKeyboard.GrabKeyboardBuilder ownerEvents(final boolean ownerEvents) {
      this.ownerEvents = ownerEvents;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabKeyboard.GrabKeyboardBuilder grabWindow(final int grabWindow) {
      this.grabWindow = grabWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabKeyboard.GrabKeyboardBuilder time(final int time) {
      this.time = time;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GrabKeyboard build() {
      return new GrabKeyboard(this.ownerEvents, this.grabWindow, this.time, this.pointerMode, this.keyboardMode);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GrabKeyboard.GrabKeyboardBuilder(ownerEvents=" + this.ownerEvents + ", grabWindow=" + this.grabWindow + ", time=" + this.time + ", pointerMode=" + this.pointerMode + ", keyboardMode=" + this.keyboardMode + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GrabKeyboard(final boolean ownerEvents, final int grabWindow, final int time, final byte pointerMode, final byte keyboardMode) {
    this.ownerEvents = ownerEvents;
    this.grabWindow = grabWindow;
    this.time = time;
    this.pointerMode = pointerMode;
    this.keyboardMode = keyboardMode;
  }

  @java.lang.SuppressWarnings("all")
  public static GrabKeyboard.GrabKeyboardBuilder builder() {
    return new GrabKeyboard.GrabKeyboardBuilder();
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
  public int getTime() {
    return this.time;
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
    if (!(o instanceof GrabKeyboard)) return false;
    final GrabKeyboard other = (GrabKeyboard) o;
    if (this.isOwnerEvents() != other.isOwnerEvents()) return false;
    if (this.getGrabWindow() != other.getGrabWindow()) return false;
    if (this.getTime() != other.getTime()) return false;
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
    result = result * PRIME + this.getTime();
    result = result * PRIME + this.getPointerMode();
    result = result * PRIME + this.getKeyboardMode();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GrabKeyboard(ownerEvents=" + this.isOwnerEvents() + ", grabWindow=" + this.getGrabWindow() + ", time=" + this.getTime() + ", pointerMode=" + this.getPointerMode() + ", keyboardMode=" + this.getKeyboardMode() + ")";
  }
}
