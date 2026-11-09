package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetKeyboardControlReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final byte globalAutoRepeat;
  private final short sequenceNumber;
  private final int ledMask;
  private final byte keyClickPercent;
  private final byte bellPercent;
  private final short bellPitch;
  private final short bellDuration;
  private final byte[] autoRepeats;

  public static GetKeyboardControlReply readGetKeyboardControlReply(byte globalAutoRepeat, short sequenceNumber, X11Input in) throws IOException {
    GetKeyboardControlReply.GetKeyboardControlReplyBuilder javaBuilder = GetKeyboardControlReply.builder();
    int length = in.readCard32();
    int ledMask = in.readCard32();
    byte keyClickPercent = in.readCard8();
    byte bellPercent = in.readCard8();
    short bellPitch = in.readCard16();
    short bellDuration = in.readCard16();
    byte[] pad9 = in.readPad(2);
    byte[] autoRepeats = in.readCard8(32);
    javaBuilder.globalAutoRepeat(globalAutoRepeat);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.ledMask(ledMask);
    javaBuilder.keyClickPercent(keyClickPercent);
    javaBuilder.bellPercent(bellPercent);
    javaBuilder.bellPitch(bellPitch);
    javaBuilder.bellDuration(bellDuration);
    javaBuilder.autoRepeats(autoRepeats);
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeByte(globalAutoRepeat);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(ledMask);
    out.writeCard8(keyClickPercent);
    out.writeCard8(bellPercent);
    out.writeCard16(bellPitch);
    out.writeCard16(bellDuration);
    out.writePad(2);
    out.writeCard8(autoRepeats);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 20 + 1 * autoRepeats.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetKeyboardControlReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte globalAutoRepeat;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int ledMask;
    @java.lang.SuppressWarnings("all")
    private byte keyClickPercent;
    @java.lang.SuppressWarnings("all")
    private byte bellPercent;
    @java.lang.SuppressWarnings("all")
    private short bellPitch;
    @java.lang.SuppressWarnings("all")
    private short bellDuration;
    @java.lang.SuppressWarnings("all")
    private byte[] autoRepeats;

    public GetKeyboardControlReply.GetKeyboardControlReplyBuilder globalAutoRepeat(AutoRepeatMode globalAutoRepeat) {
      this.globalAutoRepeat = (byte) globalAutoRepeat.getValue();
      return this;
    }

    public GetKeyboardControlReply.GetKeyboardControlReplyBuilder globalAutoRepeat(byte globalAutoRepeat) {
      this.globalAutoRepeat = globalAutoRepeat;
      return this;
    }

    public int getSize() {
      return 20 + 1 * autoRepeats.length;
    }

    @java.lang.SuppressWarnings("all")
    GetKeyboardControlReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetKeyboardControlReply.GetKeyboardControlReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetKeyboardControlReply.GetKeyboardControlReplyBuilder ledMask(final int ledMask) {
      this.ledMask = ledMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetKeyboardControlReply.GetKeyboardControlReplyBuilder keyClickPercent(final byte keyClickPercent) {
      this.keyClickPercent = keyClickPercent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetKeyboardControlReply.GetKeyboardControlReplyBuilder bellPercent(final byte bellPercent) {
      this.bellPercent = bellPercent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetKeyboardControlReply.GetKeyboardControlReplyBuilder bellPitch(final short bellPitch) {
      this.bellPitch = bellPitch;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetKeyboardControlReply.GetKeyboardControlReplyBuilder bellDuration(final short bellDuration) {
      this.bellDuration = bellDuration;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetKeyboardControlReply.GetKeyboardControlReplyBuilder autoRepeats(final byte[] autoRepeats) {
      if (autoRepeats == null) {
        throw new java.lang.NullPointerException("autoRepeats is marked non-null but is null");
      }
      this.autoRepeats = autoRepeats;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetKeyboardControlReply build() {
      return new GetKeyboardControlReply(this.globalAutoRepeat, this.sequenceNumber, this.ledMask, this.keyClickPercent, this.bellPercent, this.bellPitch, this.bellDuration, this.autoRepeats);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetKeyboardControlReply.GetKeyboardControlReplyBuilder(globalAutoRepeat=" + this.globalAutoRepeat + ", sequenceNumber=" + this.sequenceNumber + ", ledMask=" + this.ledMask + ", keyClickPercent=" + this.keyClickPercent + ", bellPercent=" + this.bellPercent + ", bellPitch=" + this.bellPitch + ", bellDuration=" + this.bellDuration + ", autoRepeats=" + java.util.Arrays.toString(this.autoRepeats) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetKeyboardControlReply(final byte globalAutoRepeat, final short sequenceNumber, final int ledMask, final byte keyClickPercent, final byte bellPercent, final short bellPitch, final short bellDuration, final byte[] autoRepeats) {
    if (autoRepeats == null) {
      throw new java.lang.NullPointerException("autoRepeats is marked non-null but is null");
    }
    this.globalAutoRepeat = globalAutoRepeat;
    this.sequenceNumber = sequenceNumber;
    this.ledMask = ledMask;
    this.keyClickPercent = keyClickPercent;
    this.bellPercent = bellPercent;
    this.bellPitch = bellPitch;
    this.bellDuration = bellDuration;
    this.autoRepeats = autoRepeats;
  }

  @java.lang.SuppressWarnings("all")
  public static GetKeyboardControlReply.GetKeyboardControlReplyBuilder builder() {
    return new GetKeyboardControlReply.GetKeyboardControlReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getGlobalAutoRepeat() {
    return this.globalAutoRepeat;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getLedMask() {
    return this.ledMask;
  }

  @java.lang.SuppressWarnings("all")
  public byte getKeyClickPercent() {
    return this.keyClickPercent;
  }

  @java.lang.SuppressWarnings("all")
  public byte getBellPercent() {
    return this.bellPercent;
  }

  @java.lang.SuppressWarnings("all")
  public short getBellPitch() {
    return this.bellPitch;
  }

  @java.lang.SuppressWarnings("all")
  public short getBellDuration() {
    return this.bellDuration;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getAutoRepeats() {
    return this.autoRepeats;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetKeyboardControlReply)) return false;
    final GetKeyboardControlReply other = (GetKeyboardControlReply) o;
    if (this.getGlobalAutoRepeat() != other.getGlobalAutoRepeat()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getLedMask() != other.getLedMask()) return false;
    if (this.getKeyClickPercent() != other.getKeyClickPercent()) return false;
    if (this.getBellPercent() != other.getBellPercent()) return false;
    if (this.getBellPitch() != other.getBellPitch()) return false;
    if (this.getBellDuration() != other.getBellDuration()) return false;
    if (!java.util.Arrays.equals(this.getAutoRepeats(), other.getAutoRepeats())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getGlobalAutoRepeat();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getLedMask();
    result = result * PRIME + this.getKeyClickPercent();
    result = result * PRIME + this.getBellPercent();
    result = result * PRIME + this.getBellPitch();
    result = result * PRIME + this.getBellDuration();
    result = result * PRIME + java.util.Arrays.hashCode(this.getAutoRepeats());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetKeyboardControlReply(globalAutoRepeat=" + this.getGlobalAutoRepeat() + ", sequenceNumber=" + this.getSequenceNumber() + ", ledMask=" + this.getLedMask() + ", keyClickPercent=" + this.getKeyClickPercent() + ", bellPercent=" + this.getBellPercent() + ", bellPitch=" + this.getBellPitch() + ", bellDuration=" + this.getBellDuration() + ", autoRepeats=" + java.util.Arrays.toString(this.getAutoRepeats()) + ")";
  }
}
