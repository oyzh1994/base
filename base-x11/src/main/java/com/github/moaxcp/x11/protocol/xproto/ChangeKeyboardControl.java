package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ChangeKeyboardControl implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 102;
  private final int valueMask;
  private final int keyClickPercent;
  private final int bellPercent;
  private final int bellPitch;
  private final int bellDuration;
  private final int led;
  private final int ledMode;
  private final int key;
  private final int autoRepeatMode;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeKeyboardControl readChangeKeyboardControl(X11Input in) throws IOException {
    ChangeKeyboardControl.ChangeKeyboardControlBuilder javaBuilder = ChangeKeyboardControl.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int valueMask = in.readCard32();
    int keyClickPercent = 0;
    int bellPercent = 0;
    int bellPitch = 0;
    int bellDuration = 0;
    int led = 0;
    int ledMode = 0;
    int key = 0;
    int autoRepeatMode = 0;
    javaBuilder.valueMask(valueMask);
    if (Kb.KEY_CLICK_PERCENT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      keyClickPercent = in.readInt32();
      javaBuilder.keyClickPercent(keyClickPercent);
    }
    if (Kb.BELL_PERCENT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      bellPercent = in.readInt32();
      javaBuilder.bellPercent(bellPercent);
    }
    if (Kb.BELL_PITCH.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      bellPitch = in.readInt32();
      javaBuilder.bellPitch(bellPitch);
    }
    if (Kb.BELL_DURATION.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      bellDuration = in.readInt32();
      javaBuilder.bellDuration(bellDuration);
    }
    if (Kb.LED.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      led = in.readCard32();
      javaBuilder.led(led);
    }
    if (Kb.LED_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      ledMode = in.readCard32();
      javaBuilder.ledMode(ledMode);
    }
    if (Kb.KEY.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      key = in.readCard32();
      javaBuilder.key(key);
    }
    if (Kb.AUTO_REPEAT_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      autoRepeatMode = in.readCard32();
      javaBuilder.autoRepeatMode(autoRepeatMode);
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(valueMask);
    if (Kb.KEY_CLICK_PERCENT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(keyClickPercent);
    }
    if (Kb.BELL_PERCENT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(bellPercent);
    }
    if (Kb.BELL_PITCH.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(bellPitch);
    }
    if (Kb.BELL_DURATION.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(bellDuration);
    }
    if (Kb.LED.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(led);
    }
    if (Kb.LED_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(ledMode);
    }
    if (Kb.KEY.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(key);
    }
    if (Kb.AUTO_REPEAT_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(autoRepeatMode);
    }
    out.writePadAlign(getSize());
  }

  public boolean isValueMaskEnabled(Kb... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Kb m : maskEnums) {
      if (!m.isEnabled(valueMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 8 + (Kb.KEY_CLICK_PERCENT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.BELL_PERCENT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.BELL_PITCH.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.BELL_DURATION.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.LED.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.LED_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.KEY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.AUTO_REPEAT_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ChangeKeyboardControlBuilder {
    @java.lang.SuppressWarnings("all")
    private int valueMask;
    @java.lang.SuppressWarnings("all")
    private int keyClickPercent;
    @java.lang.SuppressWarnings("all")
    private int bellPercent;
    @java.lang.SuppressWarnings("all")
    private int bellPitch;
    @java.lang.SuppressWarnings("all")
    private int bellDuration;
    @java.lang.SuppressWarnings("all")
    private int led;
    @java.lang.SuppressWarnings("all")
    private int ledMode;
    @java.lang.SuppressWarnings("all")
    private int key;
    @java.lang.SuppressWarnings("all")
    private int autoRepeatMode;

    private ChangeKeyboardControl.ChangeKeyboardControlBuilder valueMask(int valueMask) {
      this.valueMask = valueMask;
      return this;
    }

    public boolean isValueMaskEnabled(Kb... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Kb m : maskEnums) {
        if (!m.isEnabled(valueMask)) {
          return false;
        }
      }
      return true;
    }

    private ChangeKeyboardControl.ChangeKeyboardControlBuilder valueMaskEnable(Kb... maskEnums) {
      for (Kb m : maskEnums) {
        valueMask((int) m.enableFor(valueMask));
      }
      return this;
    }

    private ChangeKeyboardControl.ChangeKeyboardControlBuilder valueMaskDisable(Kb... maskEnums) {
      for (Kb m : maskEnums) {
        valueMask((int) m.disableFor(valueMask));
      }
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder keyClickPercent(int keyClickPercent) {
      this.keyClickPercent = keyClickPercent;
      valueMaskEnable(Kb.KEY_CLICK_PERCENT);
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder bellPercent(int bellPercent) {
      this.bellPercent = bellPercent;
      valueMaskEnable(Kb.BELL_PERCENT);
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder bellPitch(int bellPitch) {
      this.bellPitch = bellPitch;
      valueMaskEnable(Kb.BELL_PITCH);
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder bellDuration(int bellDuration) {
      this.bellDuration = bellDuration;
      valueMaskEnable(Kb.BELL_DURATION);
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder led(int led) {
      this.led = led;
      valueMaskEnable(Kb.LED);
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder ledMode(int ledMode) {
      this.ledMode = ledMode;
      valueMaskEnable(Kb.LED_MODE);
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder ledMode(LedMode ledMode) {
      this.ledMode = (int) ledMode.getValue();
      valueMaskEnable(Kb.LED_MODE);
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder key(int key) {
      this.key = key;
      valueMaskEnable(Kb.KEY);
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder autoRepeatMode(int autoRepeatMode) {
      this.autoRepeatMode = autoRepeatMode;
      valueMaskEnable(Kb.AUTO_REPEAT_MODE);
      return this;
    }

    public ChangeKeyboardControl.ChangeKeyboardControlBuilder autoRepeatMode(AutoRepeatMode autoRepeatMode) {
      this.autoRepeatMode = (int) autoRepeatMode.getValue();
      valueMaskEnable(Kb.AUTO_REPEAT_MODE);
      return this;
    }

    public int getSize() {
      return 8 + (Kb.KEY_CLICK_PERCENT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.BELL_PERCENT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.BELL_PITCH.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.BELL_DURATION.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.LED.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.LED_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.KEY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Kb.AUTO_REPEAT_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
    }

    @java.lang.SuppressWarnings("all")
    ChangeKeyboardControlBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public ChangeKeyboardControl build() {
      return new ChangeKeyboardControl(this.valueMask, this.keyClickPercent, this.bellPercent, this.bellPitch, this.bellDuration, this.led, this.ledMode, this.key, this.autoRepeatMode);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ChangeKeyboardControl.ChangeKeyboardControlBuilder(valueMask=" + this.valueMask + ", keyClickPercent=" + this.keyClickPercent + ", bellPercent=" + this.bellPercent + ", bellPitch=" + this.bellPitch + ", bellDuration=" + this.bellDuration + ", led=" + this.led + ", ledMode=" + this.ledMode + ", key=" + this.key + ", autoRepeatMode=" + this.autoRepeatMode + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ChangeKeyboardControl(final int valueMask, final int keyClickPercent, final int bellPercent, final int bellPitch, final int bellDuration, final int led, final int ledMode, final int key, final int autoRepeatMode) {
    this.valueMask = valueMask;
    this.keyClickPercent = keyClickPercent;
    this.bellPercent = bellPercent;
    this.bellPitch = bellPitch;
    this.bellDuration = bellDuration;
    this.led = led;
    this.ledMode = ledMode;
    this.key = key;
    this.autoRepeatMode = autoRepeatMode;
  }

  @java.lang.SuppressWarnings("all")
  public static ChangeKeyboardControl.ChangeKeyboardControlBuilder builder() {
    return new ChangeKeyboardControl.ChangeKeyboardControlBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getValueMask() {
    return this.valueMask;
  }

  @java.lang.SuppressWarnings("all")
  public int getKeyClickPercent() {
    return this.keyClickPercent;
  }

  @java.lang.SuppressWarnings("all")
  public int getBellPercent() {
    return this.bellPercent;
  }

  @java.lang.SuppressWarnings("all")
  public int getBellPitch() {
    return this.bellPitch;
  }

  @java.lang.SuppressWarnings("all")
  public int getBellDuration() {
    return this.bellDuration;
  }

  @java.lang.SuppressWarnings("all")
  public int getLed() {
    return this.led;
  }

  @java.lang.SuppressWarnings("all")
  public int getLedMode() {
    return this.ledMode;
  }

  @java.lang.SuppressWarnings("all")
  public int getKey() {
    return this.key;
  }

  @java.lang.SuppressWarnings("all")
  public int getAutoRepeatMode() {
    return this.autoRepeatMode;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ChangeKeyboardControl)) return false;
    final ChangeKeyboardControl other = (ChangeKeyboardControl) o;
    if (this.getValueMask() != other.getValueMask()) return false;
    if (this.getKeyClickPercent() != other.getKeyClickPercent()) return false;
    if (this.getBellPercent() != other.getBellPercent()) return false;
    if (this.getBellPitch() != other.getBellPitch()) return false;
    if (this.getBellDuration() != other.getBellDuration()) return false;
    if (this.getLed() != other.getLed()) return false;
    if (this.getLedMode() != other.getLedMode()) return false;
    if (this.getKey() != other.getKey()) return false;
    if (this.getAutoRepeatMode() != other.getAutoRepeatMode()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getValueMask();
    result = result * PRIME + this.getKeyClickPercent();
    result = result * PRIME + this.getBellPercent();
    result = result * PRIME + this.getBellPitch();
    result = result * PRIME + this.getBellDuration();
    result = result * PRIME + this.getLed();
    result = result * PRIME + this.getLedMode();
    result = result * PRIME + this.getKey();
    result = result * PRIME + this.getAutoRepeatMode();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ChangeKeyboardControl(valueMask=" + this.getValueMask() + ", keyClickPercent=" + this.getKeyClickPercent() + ", bellPercent=" + this.getBellPercent() + ", bellPitch=" + this.getBellPitch() + ", bellDuration=" + this.getBellDuration() + ", led=" + this.getLed() + ", ledMode=" + this.getLedMode() + ", key=" + this.getKey() + ", autoRepeatMode=" + this.getAutoRepeatMode() + ")";
  }
}
