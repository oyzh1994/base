package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class SetScreenConfig implements TwoWayRequest<SetScreenConfigReply> {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 2;
  private final int window;
  private final int timestamp;
  private final int configTimestamp;
  private final short sizeID;
  private final short rotation;
  private final short rate;

  public XReplyFunction<SetScreenConfigReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SetScreenConfigReply.readSetScreenConfigReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetScreenConfig readSetScreenConfig(X11Input in) throws IOException {
    SetScreenConfig.SetScreenConfigBuilder javaBuilder = SetScreenConfig.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int timestamp = in.readCard32();
    int configTimestamp = in.readCard32();
    short sizeID = in.readCard16();
    short rotation = in.readCard16();
    short rate = in.readCard16();
    byte[] pad9 = in.readPad(2);
    javaBuilder.window(window);
    javaBuilder.timestamp(timestamp);
    javaBuilder.configTimestamp(configTimestamp);
    javaBuilder.sizeID(sizeID);
    javaBuilder.rotation(rotation);
    javaBuilder.rate(rate);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(timestamp);
    out.writeCard32(configTimestamp);
    out.writeCard16(sizeID);
    out.writeCard16(rotation);
    out.writeCard16(rate);
    out.writePad(2);
  }

  public boolean isRotationEnabled(Rotation... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Rotation m : maskEnums) {
      if (!m.isEnabled(rotation)) {
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


  public static class SetScreenConfigBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private int configTimestamp;
    @java.lang.SuppressWarnings("all")
    private short sizeID;
    @java.lang.SuppressWarnings("all")
    private short rotation;
    @java.lang.SuppressWarnings("all")
    private short rate;

    public boolean isRotationEnabled(Rotation... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Rotation m : maskEnums) {
        if (!m.isEnabled(rotation)) {
          return false;
        }
      }
      return true;
    }

    public SetScreenConfig.SetScreenConfigBuilder rotationEnable(Rotation... maskEnums) {
      for (Rotation m : maskEnums) {
        rotation((short) m.enableFor(rotation));
      }
      return this;
    }

    public SetScreenConfig.SetScreenConfigBuilder rotationDisable(Rotation... maskEnums) {
      for (Rotation m : maskEnums) {
        rotation((short) m.disableFor(rotation));
      }
      return this;
    }

    public int getSize() {
      return 24;
    }

    @java.lang.SuppressWarnings("all")
    SetScreenConfigBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetScreenConfig.SetScreenConfigBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetScreenConfig.SetScreenConfigBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetScreenConfig.SetScreenConfigBuilder configTimestamp(final int configTimestamp) {
      this.configTimestamp = configTimestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetScreenConfig.SetScreenConfigBuilder sizeID(final short sizeID) {
      this.sizeID = sizeID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetScreenConfig.SetScreenConfigBuilder rotation(final short rotation) {
      this.rotation = rotation;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetScreenConfig.SetScreenConfigBuilder rate(final short rate) {
      this.rate = rate;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetScreenConfig build() {
      return new SetScreenConfig(this.window, this.timestamp, this.configTimestamp, this.sizeID, this.rotation, this.rate);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetScreenConfig.SetScreenConfigBuilder(window=" + this.window + ", timestamp=" + this.timestamp + ", configTimestamp=" + this.configTimestamp + ", sizeID=" + this.sizeID + ", rotation=" + this.rotation + ", rate=" + this.rate + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetScreenConfig(final int window, final int timestamp, final int configTimestamp, final short sizeID, final short rotation, final short rate) {
    this.window = window;
    this.timestamp = timestamp;
    this.configTimestamp = configTimestamp;
    this.sizeID = sizeID;
    this.rotation = rotation;
    this.rate = rate;
  }

  @java.lang.SuppressWarnings("all")
  public static SetScreenConfig.SetScreenConfigBuilder builder() {
    return new SetScreenConfig.SetScreenConfigBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getTimestamp() {
    return this.timestamp;
  }

  @java.lang.SuppressWarnings("all")
  public int getConfigTimestamp() {
    return this.configTimestamp;
  }

  @java.lang.SuppressWarnings("all")
  public short getSizeID() {
    return this.sizeID;
  }

  @java.lang.SuppressWarnings("all")
  public short getRotation() {
    return this.rotation;
  }

  @java.lang.SuppressWarnings("all")
  public short getRate() {
    return this.rate;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetScreenConfig)) return false;
    final SetScreenConfig other = (SetScreenConfig) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getConfigTimestamp() != other.getConfigTimestamp()) return false;
    if (this.getSizeID() != other.getSizeID()) return false;
    if (this.getRotation() != other.getRotation()) return false;
    if (this.getRate() != other.getRate()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getConfigTimestamp();
    result = result * PRIME + this.getSizeID();
    result = result * PRIME + this.getRotation();
    result = result * PRIME + this.getRate();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetScreenConfig(window=" + this.getWindow() + ", timestamp=" + this.getTimestamp() + ", configTimestamp=" + this.getConfigTimestamp() + ", sizeID=" + this.getSizeID() + ", rotation=" + this.getRotation() + ", rate=" + this.getRate() + ")";
  }
}
