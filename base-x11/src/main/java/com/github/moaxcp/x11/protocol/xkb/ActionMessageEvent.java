package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;

public final class ActionMessageEvent implements XEvent {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte NUMBER = 9;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte xkbType;
  private final short sequenceNumber;
  private final int time;
  private final byte deviceID;
  private final byte keycode;
  private final boolean press;
  private final boolean keyEventFollows;
  private final byte mods;
  private final byte group;
  private final byte[] message;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static ActionMessageEvent readActionMessageEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    ActionMessageEvent.ActionMessageEventBuilder javaBuilder = ActionMessageEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte keycode = in.readCard8();
    boolean press = in.readBool();
    boolean keyEventFollows = in.readBool();
    byte mods = in.readCard8();
    byte group = in.readCard8();
    byte[] message = in.readChar(8);
    byte[] pad11 = in.readPad(10);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.keycode(keycode);
    javaBuilder.press(press);
    javaBuilder.keyEventFollows(keyEventFollows);
    javaBuilder.mods(mods);
    javaBuilder.group(group);
    javaBuilder.message(message);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeCard8(xkbType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard8(deviceID);
    out.writeCard8(keycode);
    out.writeBool(press);
    out.writeBool(keyEventFollows);
    out.writeCard8(mods);
    out.writeCard8(group);
    out.writeChar(message);
    out.writePad(10);
    out.writePadAlign(getSize());
  }

  public boolean isModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(mods)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 24 + 1 * message.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ActionMessageEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private byte xkbType;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int time;
    @java.lang.SuppressWarnings("all")
    private byte deviceID;
    @java.lang.SuppressWarnings("all")
    private byte keycode;
    @java.lang.SuppressWarnings("all")
    private boolean press;
    @java.lang.SuppressWarnings("all")
    private boolean keyEventFollows;
    @java.lang.SuppressWarnings("all")
    private byte mods;
    @java.lang.SuppressWarnings("all")
    private byte group;
    @java.lang.SuppressWarnings("all")
    private byte[] message;

    public boolean isModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(mods)) {
          return false;
        }
      }
      return true;
    }

    public ActionMessageEvent.ActionMessageEventBuilder modsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mods((byte) m.enableFor(mods));
      }
      return this;
    }

    public ActionMessageEvent.ActionMessageEventBuilder modsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mods((byte) m.disableFor(mods));
      }
      return this;
    }

    public ActionMessageEvent.ActionMessageEventBuilder group(Group group) {
      this.group = (byte) group.getValue();
      return this;
    }

    public ActionMessageEvent.ActionMessageEventBuilder group(byte group) {
      this.group = group;
      return this;
    }

    public int getSize() {
      return 24 + 1 * message.length;
    }

    @java.lang.SuppressWarnings("all")
    ActionMessageEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ActionMessageEvent.ActionMessageEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ActionMessageEvent.ActionMessageEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ActionMessageEvent.ActionMessageEventBuilder xkbType(final byte xkbType) {
      this.xkbType = xkbType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ActionMessageEvent.ActionMessageEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ActionMessageEvent.ActionMessageEventBuilder time(final int time) {
      this.time = time;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ActionMessageEvent.ActionMessageEventBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ActionMessageEvent.ActionMessageEventBuilder keycode(final byte keycode) {
      this.keycode = keycode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ActionMessageEvent.ActionMessageEventBuilder press(final boolean press) {
      this.press = press;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ActionMessageEvent.ActionMessageEventBuilder keyEventFollows(final boolean keyEventFollows) {
      this.keyEventFollows = keyEventFollows;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ActionMessageEvent.ActionMessageEventBuilder mods(final byte mods) {
      this.mods = mods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ActionMessageEvent.ActionMessageEventBuilder message(final byte[] message) {
      if (message == null) {
        throw new java.lang.NullPointerException("message is marked non-null but is null");
      }
      this.message = message;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ActionMessageEvent build() {
      return new ActionMessageEvent(this.firstEventOffset, this.sentEvent, this.xkbType, this.sequenceNumber, this.time, this.deviceID, this.keycode, this.press, this.keyEventFollows, this.mods, this.group, this.message);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ActionMessageEvent.ActionMessageEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", xkbType=" + this.xkbType + ", sequenceNumber=" + this.sequenceNumber + ", time=" + this.time + ", deviceID=" + this.deviceID + ", keycode=" + this.keycode + ", press=" + this.press + ", keyEventFollows=" + this.keyEventFollows + ", mods=" + this.mods + ", group=" + this.group + ", message=" + java.util.Arrays.toString(this.message) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ActionMessageEvent(final byte firstEventOffset, final boolean sentEvent, final byte xkbType, final short sequenceNumber, final int time, final byte deviceID, final byte keycode, final boolean press, final boolean keyEventFollows, final byte mods, final byte group, final byte[] message) {
    if (message == null) {
      throw new java.lang.NullPointerException("message is marked non-null but is null");
    }
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.xkbType = xkbType;
    this.sequenceNumber = sequenceNumber;
    this.time = time;
    this.deviceID = deviceID;
    this.keycode = keycode;
    this.press = press;
    this.keyEventFollows = keyEventFollows;
    this.mods = mods;
    this.group = group;
    this.message = message;
  }

  @java.lang.SuppressWarnings("all")
  public static ActionMessageEvent.ActionMessageEventBuilder builder() {
    return new ActionMessageEvent.ActionMessageEventBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstEventOffset() {
    return this.firstEventOffset;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isSentEvent() {
    return this.sentEvent;
  }

  @java.lang.SuppressWarnings("all")
  public byte getXkbType() {
    return this.xkbType;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getTime() {
    return this.time;
  }

  @java.lang.SuppressWarnings("all")
  public byte getDeviceID() {
    return this.deviceID;
  }

  @java.lang.SuppressWarnings("all")
  public byte getKeycode() {
    return this.keycode;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isPress() {
    return this.press;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isKeyEventFollows() {
    return this.keyEventFollows;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMods() {
    return this.mods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getGroup() {
    return this.group;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getMessage() {
    return this.message;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ActionMessageEvent)) return false;
    final ActionMessageEvent other = (ActionMessageEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getXkbType() != other.getXkbType()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTime() != other.getTime()) return false;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getKeycode() != other.getKeycode()) return false;
    if (this.isPress() != other.isPress()) return false;
    if (this.isKeyEventFollows() != other.isKeyEventFollows()) return false;
    if (this.getMods() != other.getMods()) return false;
    if (this.getGroup() != other.getGroup()) return false;
    if (!java.util.Arrays.equals(this.getMessage(), other.getMessage())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getXkbType();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getTime();
    result = result * PRIME + this.getDeviceID();
    result = result * PRIME + this.getKeycode();
    result = result * PRIME + (this.isPress() ? 79 : 97);
    result = result * PRIME + (this.isKeyEventFollows() ? 79 : 97);
    result = result * PRIME + this.getMods();
    result = result * PRIME + this.getGroup();
    result = result * PRIME + java.util.Arrays.hashCode(this.getMessage());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ActionMessageEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", xkbType=" + this.getXkbType() + ", sequenceNumber=" + this.getSequenceNumber() + ", time=" + this.getTime() + ", deviceID=" + this.getDeviceID() + ", keycode=" + this.getKeycode() + ", press=" + this.isPress() + ", keyEventFollows=" + this.isKeyEventFollows() + ", mods=" + this.getMods() + ", group=" + this.getGroup() + ", message=" + java.util.Arrays.toString(this.getMessage()) + ")";
  }
}
