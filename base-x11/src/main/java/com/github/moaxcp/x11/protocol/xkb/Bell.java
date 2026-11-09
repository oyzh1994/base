package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class Bell implements OneWayRequest {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 3;
  private final short deviceSpec;
  private final short bellClass;
  private final short bellID;
  private final byte percent;
  private final boolean forceSound;
  private final boolean eventOnly;
  private final short pitch;
  private final short duration;
  private final int name;
  private final int window;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Bell readBell(X11Input in) throws IOException {
    Bell.BellBuilder javaBuilder = Bell.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short bellClass = in.readCard16();
    short bellID = in.readCard16();
    byte percent = in.readInt8();
    boolean forceSound = in.readBool();
    boolean eventOnly = in.readBool();
    byte[] pad9 = in.readPad(1);
    short pitch = in.readInt16();
    short duration = in.readInt16();
    byte[] pad12 = in.readPad(2);
    int name = in.readCard32();
    int window = in.readCard32();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.bellClass(bellClass);
    javaBuilder.bellID(bellID);
    javaBuilder.percent(percent);
    javaBuilder.forceSound(forceSound);
    javaBuilder.eventOnly(eventOnly);
    javaBuilder.pitch(pitch);
    javaBuilder.duration(duration);
    javaBuilder.name(name);
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard16(bellClass);
    out.writeCard16(bellID);
    out.writeInt8(percent);
    out.writeBool(forceSound);
    out.writeBool(eventOnly);
    out.writePad(1);
    out.writeInt16(pitch);
    out.writeInt16(duration);
    out.writePad(2);
    out.writeCard32(name);
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class BellBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;
    @java.lang.SuppressWarnings("all")
    private short bellClass;
    @java.lang.SuppressWarnings("all")
    private short bellID;
    @java.lang.SuppressWarnings("all")
    private byte percent;
    @java.lang.SuppressWarnings("all")
    private boolean forceSound;
    @java.lang.SuppressWarnings("all")
    private boolean eventOnly;
    @java.lang.SuppressWarnings("all")
    private short pitch;
    @java.lang.SuppressWarnings("all")
    private short duration;
    @java.lang.SuppressWarnings("all")
    private int name;
    @java.lang.SuppressWarnings("all")
    private int window;

    public int getSize() {
      return 28;
    }

    @java.lang.SuppressWarnings("all")
    BellBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Bell.BellBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Bell.BellBuilder bellClass(final short bellClass) {
      this.bellClass = bellClass;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Bell.BellBuilder bellID(final short bellID) {
      this.bellID = bellID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Bell.BellBuilder percent(final byte percent) {
      this.percent = percent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Bell.BellBuilder forceSound(final boolean forceSound) {
      this.forceSound = forceSound;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Bell.BellBuilder eventOnly(final boolean eventOnly) {
      this.eventOnly = eventOnly;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Bell.BellBuilder pitch(final short pitch) {
      this.pitch = pitch;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Bell.BellBuilder duration(final short duration) {
      this.duration = duration;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Bell.BellBuilder name(final int name) {
      this.name = name;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Bell.BellBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Bell build() {
      return new Bell(this.deviceSpec, this.bellClass, this.bellID, this.percent, this.forceSound, this.eventOnly, this.pitch, this.duration, this.name, this.window);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Bell.BellBuilder(deviceSpec=" + this.deviceSpec + ", bellClass=" + this.bellClass + ", bellID=" + this.bellID + ", percent=" + this.percent + ", forceSound=" + this.forceSound + ", eventOnly=" + this.eventOnly + ", pitch=" + this.pitch + ", duration=" + this.duration + ", name=" + this.name + ", window=" + this.window + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Bell(final short deviceSpec, final short bellClass, final short bellID, final byte percent, final boolean forceSound, final boolean eventOnly, final short pitch, final short duration, final int name, final int window) {
    this.deviceSpec = deviceSpec;
    this.bellClass = bellClass;
    this.bellID = bellID;
    this.percent = percent;
    this.forceSound = forceSound;
    this.eventOnly = eventOnly;
    this.pitch = pitch;
    this.duration = duration;
    this.name = name;
    this.window = window;
  }

  @java.lang.SuppressWarnings("all")
  public static Bell.BellBuilder builder() {
    return new Bell.BellBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public short getBellClass() {
    return this.bellClass;
  }

  @java.lang.SuppressWarnings("all")
  public short getBellID() {
    return this.bellID;
  }

  @java.lang.SuppressWarnings("all")
  public byte getPercent() {
    return this.percent;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isForceSound() {
    return this.forceSound;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isEventOnly() {
    return this.eventOnly;
  }

  @java.lang.SuppressWarnings("all")
  public short getPitch() {
    return this.pitch;
  }

  @java.lang.SuppressWarnings("all")
  public short getDuration() {
    return this.duration;
  }

  @java.lang.SuppressWarnings("all")
  public int getName() {
    return this.name;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Bell)) return false;
    final Bell other = (Bell) o;
    if (this.getDeviceSpec() != other.getDeviceSpec()) return false;
    if (this.getBellClass() != other.getBellClass()) return false;
    if (this.getBellID() != other.getBellID()) return false;
    if (this.getPercent() != other.getPercent()) return false;
    if (this.isForceSound() != other.isForceSound()) return false;
    if (this.isEventOnly() != other.isEventOnly()) return false;
    if (this.getPitch() != other.getPitch()) return false;
    if (this.getDuration() != other.getDuration()) return false;
    if (this.getName() != other.getName()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceSpec();
    result = result * PRIME + this.getBellClass();
    result = result * PRIME + this.getBellID();
    result = result * PRIME + this.getPercent();
    result = result * PRIME + (this.isForceSound() ? 79 : 97);
    result = result * PRIME + (this.isEventOnly() ? 79 : 97);
    result = result * PRIME + this.getPitch();
    result = result * PRIME + this.getDuration();
    result = result * PRIME + this.getName();
    result = result * PRIME + this.getWindow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Bell(deviceSpec=" + this.getDeviceSpec() + ", bellClass=" + this.getBellClass() + ", bellID=" + this.getBellID() + ", percent=" + this.getPercent() + ", forceSound=" + this.isForceSound() + ", eventOnly=" + this.isEventOnly() + ", pitch=" + this.getPitch() + ", duration=" + this.getDuration() + ", name=" + this.getName() + ", window=" + this.getWindow() + ")";
  }
}
