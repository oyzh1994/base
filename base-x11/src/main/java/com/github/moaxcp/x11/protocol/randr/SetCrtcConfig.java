package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class SetCrtcConfig implements TwoWayRequest<SetCrtcConfigReply> {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 21;
  private final int crtc;
  private final int timestamp;
  private final int configTimestamp;
  private final short x;
  private final short y;
  private final int mode;
  private final short rotation;
  private final int[] outputs;

  public XReplyFunction<SetCrtcConfigReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SetCrtcConfigReply.readSetCrtcConfigReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetCrtcConfig readSetCrtcConfig(X11Input in) throws IOException {
    SetCrtcConfig.SetCrtcConfigBuilder javaBuilder = SetCrtcConfig.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int crtc = in.readCard32();
    javaStart += 4;
    int timestamp = in.readCard32();
    javaStart += 4;
    int configTimestamp = in.readCard32();
    javaStart += 4;
    short x = in.readInt16();
    javaStart += 2;
    short y = in.readInt16();
    javaStart += 2;
    int mode = in.readCard32();
    javaStart += 4;
    short rotation = in.readCard16();
    javaStart += 2;
    byte[] pad10 = in.readPad(2);
    javaStart += 2;
    int[] outputs = in.readCard32(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.crtc(crtc);
    javaBuilder.timestamp(timestamp);
    javaBuilder.configTimestamp(configTimestamp);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.mode(mode);
    javaBuilder.rotation(rotation);
    javaBuilder.outputs(outputs);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(crtc);
    out.writeCard32(timestamp);
    out.writeCard32(configTimestamp);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard32(mode);
    out.writeCard16(rotation);
    out.writePad(2);
    out.writeCard32(outputs);
    out.writePadAlign(getSize());
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
    return 28 + 4 * outputs.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetCrtcConfigBuilder {
    @java.lang.SuppressWarnings("all")
    private int crtc;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private int configTimestamp;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;
    @java.lang.SuppressWarnings("all")
    private int mode;
    @java.lang.SuppressWarnings("all")
    private short rotation;
    @java.lang.SuppressWarnings("all")
    private int[] outputs;

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

    public SetCrtcConfig.SetCrtcConfigBuilder rotationEnable(Rotation... maskEnums) {
      for (Rotation m : maskEnums) {
        rotation((short) m.enableFor(rotation));
      }
      return this;
    }

    public SetCrtcConfig.SetCrtcConfigBuilder rotationDisable(Rotation... maskEnums) {
      for (Rotation m : maskEnums) {
        rotation((short) m.disableFor(rotation));
      }
      return this;
    }

    public int getSize() {
      return 28 + 4 * outputs.length;
    }

    @java.lang.SuppressWarnings("all")
    SetCrtcConfigBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCrtcConfig.SetCrtcConfigBuilder crtc(final int crtc) {
      this.crtc = crtc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCrtcConfig.SetCrtcConfigBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCrtcConfig.SetCrtcConfigBuilder configTimestamp(final int configTimestamp) {
      this.configTimestamp = configTimestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCrtcConfig.SetCrtcConfigBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCrtcConfig.SetCrtcConfigBuilder y(final short y) {
      this.y = y;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCrtcConfig.SetCrtcConfigBuilder mode(final int mode) {
      this.mode = mode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCrtcConfig.SetCrtcConfigBuilder rotation(final short rotation) {
      this.rotation = rotation;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCrtcConfig.SetCrtcConfigBuilder outputs(final int[] outputs) {
      if (outputs == null) {
        throw new java.lang.NullPointerException("outputs is marked non-null but is null");
      }
      this.outputs = outputs;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetCrtcConfig build() {
      return new SetCrtcConfig(this.crtc, this.timestamp, this.configTimestamp, this.x, this.y, this.mode, this.rotation, this.outputs);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetCrtcConfig.SetCrtcConfigBuilder(crtc=" + this.crtc + ", timestamp=" + this.timestamp + ", configTimestamp=" + this.configTimestamp + ", x=" + this.x + ", y=" + this.y + ", mode=" + this.mode + ", rotation=" + this.rotation + ", outputs=" + java.util.Arrays.toString(this.outputs) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetCrtcConfig(final int crtc, final int timestamp, final int configTimestamp, final short x, final short y, final int mode, final short rotation, final int[] outputs) {
    if (outputs == null) {
      throw new java.lang.NullPointerException("outputs is marked non-null but is null");
    }
    this.crtc = crtc;
    this.timestamp = timestamp;
    this.configTimestamp = configTimestamp;
    this.x = x;
    this.y = y;
    this.mode = mode;
    this.rotation = rotation;
    this.outputs = outputs;
  }

  @java.lang.SuppressWarnings("all")
  public static SetCrtcConfig.SetCrtcConfigBuilder builder() {
    return new SetCrtcConfig.SetCrtcConfigBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCrtc() {
    return this.crtc;
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
  public short getX() {
    return this.x;
  }

  @java.lang.SuppressWarnings("all")
  public short getY() {
    return this.y;
  }

  @java.lang.SuppressWarnings("all")
  public int getMode() {
    return this.mode;
  }

  @java.lang.SuppressWarnings("all")
  public short getRotation() {
    return this.rotation;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getOutputs() {
    return this.outputs;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetCrtcConfig)) return false;
    final SetCrtcConfig other = (SetCrtcConfig) o;
    if (this.getCrtc() != other.getCrtc()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getConfigTimestamp() != other.getConfigTimestamp()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    if (this.getMode() != other.getMode()) return false;
    if (this.getRotation() != other.getRotation()) return false;
    if (!java.util.Arrays.equals(this.getOutputs(), other.getOutputs())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCrtc();
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getConfigTimestamp();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    result = result * PRIME + this.getMode();
    result = result * PRIME + this.getRotation();
    result = result * PRIME + java.util.Arrays.hashCode(this.getOutputs());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetCrtcConfig(crtc=" + this.getCrtc() + ", timestamp=" + this.getTimestamp() + ", configTimestamp=" + this.getConfigTimestamp() + ", x=" + this.getX() + ", y=" + this.getY() + ", mode=" + this.getMode() + ", rotation=" + this.getRotation() + ", outputs=" + java.util.Arrays.toString(this.getOutputs()) + ")";
  }
}
