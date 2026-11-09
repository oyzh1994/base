package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CreatePointerBarrier implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 31;
  private final int barrier;
  private final int window;
  private final short x1;
  private final short y1;
  private final short x2;
  private final short y2;
  private final int directions;
  private final short[] devices;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreatePointerBarrier readCreatePointerBarrier(X11Input in) throws IOException {
    CreatePointerBarrier.CreatePointerBarrierBuilder javaBuilder = CreatePointerBarrier.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int barrier = in.readCard32();
    int window = in.readCard32();
    short x1 = in.readCard16();
    short y1 = in.readCard16();
    short x2 = in.readCard16();
    short y2 = in.readCard16();
    int directions = in.readCard32();
    byte[] pad10 = in.readPad(2);
    short numDevices = in.readCard16();
    short[] devices = in.readCard16(Short.toUnsignedInt(numDevices));
    javaBuilder.barrier(barrier);
    javaBuilder.window(window);
    javaBuilder.x1(x1);
    javaBuilder.y1(y1);
    javaBuilder.x2(x2);
    javaBuilder.y2(y2);
    javaBuilder.directions(directions);
    javaBuilder.devices(devices);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(barrier);
    out.writeCard32(window);
    out.writeCard16(x1);
    out.writeCard16(y1);
    out.writeCard16(x2);
    out.writeCard16(y2);
    out.writeCard32(directions);
    out.writePad(2);
    short numDevices = (short) devices.length;
    out.writeCard16(numDevices);
    out.writeCard16(devices);
    out.writePadAlign(getSize());
  }

  public boolean isDirectionsEnabled(BarrierDirections... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (BarrierDirections m : maskEnums) {
      if (!m.isEnabled(directions)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 28 + 2 * devices.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreatePointerBarrierBuilder {
    @java.lang.SuppressWarnings("all")
    private int barrier;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private short x1;
    @java.lang.SuppressWarnings("all")
    private short y1;
    @java.lang.SuppressWarnings("all")
    private short x2;
    @java.lang.SuppressWarnings("all")
    private short y2;
    @java.lang.SuppressWarnings("all")
    private int directions;
    @java.lang.SuppressWarnings("all")
    private short[] devices;

    public boolean isDirectionsEnabled(BarrierDirections... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (BarrierDirections m : maskEnums) {
        if (!m.isEnabled(directions)) {
          return false;
        }
      }
      return true;
    }

    public CreatePointerBarrier.CreatePointerBarrierBuilder directionsEnable(BarrierDirections... maskEnums) {
      for (BarrierDirections m : maskEnums) {
        directions((int) m.enableFor(directions));
      }
      return this;
    }

    public CreatePointerBarrier.CreatePointerBarrierBuilder directionsDisable(BarrierDirections... maskEnums) {
      for (BarrierDirections m : maskEnums) {
        directions((int) m.disableFor(directions));
      }
      return this;
    }

    public int getSize() {
      return 28 + 2 * devices.length;
    }

    @java.lang.SuppressWarnings("all")
    CreatePointerBarrierBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreatePointerBarrier.CreatePointerBarrierBuilder barrier(final int barrier) {
      this.barrier = barrier;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreatePointerBarrier.CreatePointerBarrierBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreatePointerBarrier.CreatePointerBarrierBuilder x1(final short x1) {
      this.x1 = x1;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreatePointerBarrier.CreatePointerBarrierBuilder y1(final short y1) {
      this.y1 = y1;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreatePointerBarrier.CreatePointerBarrierBuilder x2(final short x2) {
      this.x2 = x2;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreatePointerBarrier.CreatePointerBarrierBuilder y2(final short y2) {
      this.y2 = y2;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreatePointerBarrier.CreatePointerBarrierBuilder directions(final int directions) {
      this.directions = directions;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreatePointerBarrier.CreatePointerBarrierBuilder devices(final short[] devices) {
      if (devices == null) {
        throw new java.lang.NullPointerException("devices is marked non-null but is null");
      }
      this.devices = devices;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreatePointerBarrier build() {
      return new CreatePointerBarrier(this.barrier, this.window, this.x1, this.y1, this.x2, this.y2, this.directions, this.devices);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreatePointerBarrier.CreatePointerBarrierBuilder(barrier=" + this.barrier + ", window=" + this.window + ", x1=" + this.x1 + ", y1=" + this.y1 + ", x2=" + this.x2 + ", y2=" + this.y2 + ", directions=" + this.directions + ", devices=" + java.util.Arrays.toString(this.devices) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreatePointerBarrier(final int barrier, final int window, final short x1, final short y1, final short x2, final short y2, final int directions, final short[] devices) {
    if (devices == null) {
      throw new java.lang.NullPointerException("devices is marked non-null but is null");
    }
    this.barrier = barrier;
    this.window = window;
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.directions = directions;
    this.devices = devices;
  }

  @java.lang.SuppressWarnings("all")
  public static CreatePointerBarrier.CreatePointerBarrierBuilder builder() {
    return new CreatePointerBarrier.CreatePointerBarrierBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getBarrier() {
    return this.barrier;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public short getX1() {
    return this.x1;
  }

  @java.lang.SuppressWarnings("all")
  public short getY1() {
    return this.y1;
  }

  @java.lang.SuppressWarnings("all")
  public short getX2() {
    return this.x2;
  }

  @java.lang.SuppressWarnings("all")
  public short getY2() {
    return this.y2;
  }

  @java.lang.SuppressWarnings("all")
  public int getDirections() {
    return this.directions;
  }

  @java.lang.SuppressWarnings("all")
  public short[] getDevices() {
    return this.devices;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreatePointerBarrier)) return false;
    final CreatePointerBarrier other = (CreatePointerBarrier) o;
    if (this.getBarrier() != other.getBarrier()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getX1() != other.getX1()) return false;
    if (this.getY1() != other.getY1()) return false;
    if (this.getX2() != other.getX2()) return false;
    if (this.getY2() != other.getY2()) return false;
    if (this.getDirections() != other.getDirections()) return false;
    if (!java.util.Arrays.equals(this.getDevices(), other.getDevices())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getBarrier();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getX1();
    result = result * PRIME + this.getY1();
    result = result * PRIME + this.getX2();
    result = result * PRIME + this.getY2();
    result = result * PRIME + this.getDirections();
    result = result * PRIME + java.util.Arrays.hashCode(this.getDevices());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreatePointerBarrier(barrier=" + this.getBarrier() + ", window=" + this.getWindow() + ", x1=" + this.getX1() + ", y1=" + this.getY1() + ", x2=" + this.getX2() + ", y2=" + this.getY2() + ", directions=" + this.getDirections() + ", devices=" + java.util.Arrays.toString(this.getDevices()) + ")";
  }
}
