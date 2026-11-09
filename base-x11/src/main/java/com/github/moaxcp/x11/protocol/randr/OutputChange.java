package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.render.SubPixel;
import java.io.IOException;

public final class OutputChange implements NotifyDataUnion, XStruct {
  public static final String PLUGIN_NAME = "randr";
  private final int timestamp;
  private final int configTimestamp;
  private final int window;
  private final int output;
  private final int crtc;
  private final int mode;
  private final short rotation;
  private final byte connection;
  private final byte subpixelOrder;

  public static OutputChange readOutputChange(X11Input in) throws IOException {
    OutputChange.OutputChangeBuilder javaBuilder = OutputChange.builder();
    int timestamp = in.readCard32();
    int configTimestamp = in.readCard32();
    int window = in.readCard32();
    int output = in.readCard32();
    int crtc = in.readCard32();
    int mode = in.readCard32();
    short rotation = in.readCard16();
    byte connection = in.readCard8();
    byte subpixelOrder = in.readCard8();
    javaBuilder.timestamp(timestamp);
    javaBuilder.configTimestamp(configTimestamp);
    javaBuilder.window(window);
    javaBuilder.output(output);
    javaBuilder.crtc(crtc);
    javaBuilder.mode(mode);
    javaBuilder.rotation(rotation);
    javaBuilder.connection(connection);
    javaBuilder.subpixelOrder(subpixelOrder);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(timestamp);
    out.writeCard32(configTimestamp);
    out.writeCard32(window);
    out.writeCard32(output);
    out.writeCard32(crtc);
    out.writeCard32(mode);
    out.writeCard16(rotation);
    out.writeCard8(connection);
    out.writeCard8(subpixelOrder);
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
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class OutputChangeBuilder {
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private int configTimestamp;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int output;
    @java.lang.SuppressWarnings("all")
    private int crtc;
    @java.lang.SuppressWarnings("all")
    private int mode;
    @java.lang.SuppressWarnings("all")
    private short rotation;
    @java.lang.SuppressWarnings("all")
    private byte connection;
    @java.lang.SuppressWarnings("all")
    private byte subpixelOrder;

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

    public OutputChange.OutputChangeBuilder rotationEnable(Rotation... maskEnums) {
      for (Rotation m : maskEnums) {
        rotation((short) m.enableFor(rotation));
      }
      return this;
    }

    public OutputChange.OutputChangeBuilder rotationDisable(Rotation... maskEnums) {
      for (Rotation m : maskEnums) {
        rotation((short) m.disableFor(rotation));
      }
      return this;
    }

    public OutputChange.OutputChangeBuilder connection(Connection connection) {
      this.connection = (byte) connection.getValue();
      return this;
    }

    public OutputChange.OutputChangeBuilder connection(byte connection) {
      this.connection = connection;
      return this;
    }

    public OutputChange.OutputChangeBuilder subpixelOrder(SubPixel subpixelOrder) {
      this.subpixelOrder = (byte) subpixelOrder.getValue();
      return this;
    }

    public OutputChange.OutputChangeBuilder subpixelOrder(byte subpixelOrder) {
      this.subpixelOrder = subpixelOrder;
      return this;
    }

    public int getSize() {
      return 28;
    }

    @java.lang.SuppressWarnings("all")
    OutputChangeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OutputChange.OutputChangeBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OutputChange.OutputChangeBuilder configTimestamp(final int configTimestamp) {
      this.configTimestamp = configTimestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OutputChange.OutputChangeBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OutputChange.OutputChangeBuilder output(final int output) {
      this.output = output;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OutputChange.OutputChangeBuilder crtc(final int crtc) {
      this.crtc = crtc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OutputChange.OutputChangeBuilder mode(final int mode) {
      this.mode = mode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OutputChange.OutputChangeBuilder rotation(final short rotation) {
      this.rotation = rotation;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public OutputChange build() {
      return new OutputChange(this.timestamp, this.configTimestamp, this.window, this.output, this.crtc, this.mode, this.rotation, this.connection, this.subpixelOrder);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "OutputChange.OutputChangeBuilder(timestamp=" + this.timestamp + ", configTimestamp=" + this.configTimestamp + ", window=" + this.window + ", output=" + this.output + ", crtc=" + this.crtc + ", mode=" + this.mode + ", rotation=" + this.rotation + ", connection=" + this.connection + ", subpixelOrder=" + this.subpixelOrder + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  OutputChange(final int timestamp, final int configTimestamp, final int window, final int output, final int crtc, final int mode, final short rotation, final byte connection, final byte subpixelOrder) {
    this.timestamp = timestamp;
    this.configTimestamp = configTimestamp;
    this.window = window;
    this.output = output;
    this.crtc = crtc;
    this.mode = mode;
    this.rotation = rotation;
    this.connection = connection;
    this.subpixelOrder = subpixelOrder;
  }

  @java.lang.SuppressWarnings("all")
  public static OutputChange.OutputChangeBuilder builder() {
    return new OutputChange.OutputChangeBuilder();
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
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getOutput() {
    return this.output;
  }

  @java.lang.SuppressWarnings("all")
  public int getCrtc() {
    return this.crtc;
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
  public byte getConnection() {
    return this.connection;
  }

  @java.lang.SuppressWarnings("all")
  public byte getSubpixelOrder() {
    return this.subpixelOrder;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof OutputChange)) return false;
    final OutputChange other = (OutputChange) o;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getConfigTimestamp() != other.getConfigTimestamp()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getOutput() != other.getOutput()) return false;
    if (this.getCrtc() != other.getCrtc()) return false;
    if (this.getMode() != other.getMode()) return false;
    if (this.getRotation() != other.getRotation()) return false;
    if (this.getConnection() != other.getConnection()) return false;
    if (this.getSubpixelOrder() != other.getSubpixelOrder()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getConfigTimestamp();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getOutput();
    result = result * PRIME + this.getCrtc();
    result = result * PRIME + this.getMode();
    result = result * PRIME + this.getRotation();
    result = result * PRIME + this.getConnection();
    result = result * PRIME + this.getSubpixelOrder();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "OutputChange(timestamp=" + this.getTimestamp() + ", configTimestamp=" + this.getConfigTimestamp() + ", window=" + this.getWindow() + ", output=" + this.getOutput() + ", crtc=" + this.getCrtc() + ", mode=" + this.getMode() + ", rotation=" + this.getRotation() + ", connection=" + this.getConnection() + ", subpixelOrder=" + this.getSubpixelOrder() + ")";
  }
}
