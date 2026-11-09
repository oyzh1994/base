package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.IntList;

public final class GetCrtcInfoReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final byte status;
  private final short sequenceNumber;
  private final int timestamp;
  private final short x;
  private final short y;
  private final short width;
  private final short height;
  private final int mode;
  private final short rotation;
  private final short rotations;
  private final IntList outputs;
  private final IntList possible;

  public static GetCrtcInfoReply readGetCrtcInfoReply(byte status, short sequenceNumber, X11Input in) throws IOException {
    GetCrtcInfoReply.GetCrtcInfoReplyBuilder javaBuilder = GetCrtcInfoReply.builder();
    int length = in.readCard32();
    int timestamp = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    int mode = in.readCard32();
    short rotation = in.readCard16();
    short rotations = in.readCard16();
    short numOutputs = in.readCard16();
    short numPossibleOutputs = in.readCard16();
    IntList outputs = in.readCard32(Short.toUnsignedInt(numOutputs));
    IntList possible = in.readCard32(Short.toUnsignedInt(numPossibleOutputs));
    javaBuilder.status(status);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.mode(mode);
    javaBuilder.rotation(rotation);
    javaBuilder.rotations(rotations);
    javaBuilder.outputs(outputs.toImmutable());
    javaBuilder.possible(possible.toImmutable());
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(status);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(timestamp);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard32(mode);
    out.writeCard16(rotation);
    out.writeCard16(rotations);
    short numOutputs = (short) outputs.size();
    out.writeCard16(numOutputs);
    short numPossibleOutputs = (short) possible.size();
    out.writeCard16(numPossibleOutputs);
    out.writeCard32(outputs);
    out.writeCard32(possible);
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

  public boolean isRotationsEnabled(Rotation... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Rotation m : maskEnums) {
      if (!m.isEnabled(rotations)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32 + 4 * outputs.size() + 4 * possible.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetCrtcInfoReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte status;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;
    @java.lang.SuppressWarnings("all")
    private int mode;
    @java.lang.SuppressWarnings("all")
    private short rotation;
    @java.lang.SuppressWarnings("all")
    private short rotations;
    @java.lang.SuppressWarnings("all")
    private IntList outputs;
    @java.lang.SuppressWarnings("all")
    private IntList possible;

    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder status(SetConfig status) {
      this.status = (byte) status.getValue();
      return this;
    }

    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder status(byte status) {
      this.status = status;
      return this;
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

    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder rotationEnable(Rotation... maskEnums) {
      for (Rotation m : maskEnums) {
        rotation((short) m.enableFor(rotation));
      }
      return this;
    }

    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder rotationDisable(Rotation... maskEnums) {
      for (Rotation m : maskEnums) {
        rotation((short) m.disableFor(rotation));
      }
      return this;
    }

    public boolean isRotationsEnabled(Rotation... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Rotation m : maskEnums) {
        if (!m.isEnabled(rotations)) {
          return false;
        }
      }
      return true;
    }

    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder rotationsEnable(Rotation... maskEnums) {
      for (Rotation m : maskEnums) {
        rotations((short) m.enableFor(rotations));
      }
      return this;
    }

    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder rotationsDisable(Rotation... maskEnums) {
      for (Rotation m : maskEnums) {
        rotations((short) m.disableFor(rotations));
      }
      return this;
    }

    public int getSize() {
      return 32 + 4 * outputs.size() + 4 * possible.size();
    }

    @java.lang.SuppressWarnings("all")
    GetCrtcInfoReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder y(final short y) {
      this.y = y;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder height(final short height) {
      this.height = height;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder mode(final int mode) {
      this.mode = mode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder rotation(final short rotation) {
      this.rotation = rotation;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder rotations(final short rotations) {
      this.rotations = rotations;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder outputs(final IntList outputs) {
      if (outputs == null) {
        throw new java.lang.NullPointerException("outputs is marked non-null but is null");
      }
      this.outputs = outputs;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder possible(final IntList possible) {
      if (possible == null) {
        throw new java.lang.NullPointerException("possible is marked non-null but is null");
      }
      this.possible = possible;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetCrtcInfoReply build() {
      return new GetCrtcInfoReply(this.status, this.sequenceNumber, this.timestamp, this.x, this.y, this.width, this.height, this.mode, this.rotation, this.rotations, this.outputs, this.possible);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetCrtcInfoReply.GetCrtcInfoReplyBuilder(status=" + this.status + ", sequenceNumber=" + this.sequenceNumber + ", timestamp=" + this.timestamp + ", x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", mode=" + this.mode + ", rotation=" + this.rotation + ", rotations=" + this.rotations + ", outputs=" + this.outputs + ", possible=" + this.possible + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetCrtcInfoReply(final byte status, final short sequenceNumber, final int timestamp, final short x, final short y, final short width, final short height, final int mode, final short rotation, final short rotations, final IntList outputs, final IntList possible) {
    if (outputs == null) {
      throw new java.lang.NullPointerException("outputs is marked non-null but is null");
    }
    if (possible == null) {
      throw new java.lang.NullPointerException("possible is marked non-null but is null");
    }
    this.status = status;
    this.sequenceNumber = sequenceNumber;
    this.timestamp = timestamp;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.mode = mode;
    this.rotation = rotation;
    this.rotations = rotations;
    this.outputs = outputs;
    this.possible = possible;
  }

  @java.lang.SuppressWarnings("all")
  public static GetCrtcInfoReply.GetCrtcInfoReplyBuilder builder() {
    return new GetCrtcInfoReply.GetCrtcInfoReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getStatus() {
    return this.status;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getTimestamp() {
    return this.timestamp;
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
  public short getWidth() {
    return this.width;
  }

  @java.lang.SuppressWarnings("all")
  public short getHeight() {
    return this.height;
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
  public short getRotations() {
    return this.rotations;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getOutputs() {
    return this.outputs;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getPossible() {
    return this.possible;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetCrtcInfoReply)) return false;
    final GetCrtcInfoReply other = (GetCrtcInfoReply) o;
    if (this.getStatus() != other.getStatus()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getMode() != other.getMode()) return false;
    if (this.getRotation() != other.getRotation()) return false;
    if (this.getRotations() != other.getRotations()) return false;
    final java.lang.Object this$outputs = this.getOutputs();
    final java.lang.Object other$outputs = other.getOutputs();
    if (this$outputs == null ? other$outputs != null : !this$outputs.equals(other$outputs)) return false;
    final java.lang.Object this$possible = this.getPossible();
    final java.lang.Object other$possible = other.getPossible();
    if (this$possible == null ? other$possible != null : !this$possible.equals(other$possible)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getStatus();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getMode();
    result = result * PRIME + this.getRotation();
    result = result * PRIME + this.getRotations();
    final java.lang.Object $outputs = this.getOutputs();
    result = result * PRIME + ($outputs == null ? 43 : $outputs.hashCode());
    final java.lang.Object $possible = this.getPossible();
    result = result * PRIME + ($possible == null ? 43 : $possible.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetCrtcInfoReply(status=" + this.getStatus() + ", sequenceNumber=" + this.getSequenceNumber() + ", timestamp=" + this.getTimestamp() + ", x=" + this.getX() + ", y=" + this.getY() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", mode=" + this.getMode() + ", rotation=" + this.getRotation() + ", rotations=" + this.getRotations() + ", outputs=" + this.getOutputs() + ", possible=" + this.getPossible() + ")";
  }
}
