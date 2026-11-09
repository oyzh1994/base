package com.github.moaxcp.x11.protocol.xtest;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class FakeInput implements OneWayRequest {
  public static final String PLUGIN_NAME = "xtest";
  public static final byte OPCODE = 2;
  private final byte type;
  private final byte detail;
  private final int time;
  private final int root;
  private final short rootX;
  private final short rootY;
  private final byte deviceid;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FakeInput readFakeInput(X11Input in) throws IOException {
    FakeInput.FakeInputBuilder javaBuilder = FakeInput.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte type = in.readByte();
    byte detail = in.readByte();
    byte[] pad5 = in.readPad(2);
    int time = in.readCard32();
    int root = in.readCard32();
    byte[] pad8 = in.readPad(8);
    short rootX = in.readInt16();
    short rootY = in.readInt16();
    byte[] pad11 = in.readPad(7);
    byte deviceid = in.readCard8();
    javaBuilder.type(type);
    javaBuilder.detail(detail);
    javaBuilder.time(time);
    javaBuilder.root(root);
    javaBuilder.rootX(rootX);
    javaBuilder.rootY(rootY);
    javaBuilder.deviceid(deviceid);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeByte(type);
    out.writeByte(detail);
    out.writePad(2);
    out.writeCard32(time);
    out.writeCard32(root);
    out.writePad(8);
    out.writeInt16(rootX);
    out.writeInt16(rootY);
    out.writePad(7);
    out.writeCard8(deviceid);
  }

  @Override
  public int getSize() {
    return 36;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class FakeInputBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte detail;
    @java.lang.SuppressWarnings("all")
    private int time;
    @java.lang.SuppressWarnings("all")
    private int root;
    @java.lang.SuppressWarnings("all")
    private short rootX;
    @java.lang.SuppressWarnings("all")
    private short rootY;
    @java.lang.SuppressWarnings("all")
    private byte deviceid;

    public int getSize() {
      return 36;
    }

    @java.lang.SuppressWarnings("all")
    FakeInputBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FakeInput.FakeInputBuilder type(final byte type) {
      this.type = type;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FakeInput.FakeInputBuilder detail(final byte detail) {
      this.detail = detail;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FakeInput.FakeInputBuilder time(final int time) {
      this.time = time;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FakeInput.FakeInputBuilder root(final int root) {
      this.root = root;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FakeInput.FakeInputBuilder rootX(final short rootX) {
      this.rootX = rootX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FakeInput.FakeInputBuilder rootY(final short rootY) {
      this.rootY = rootY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FakeInput.FakeInputBuilder deviceid(final byte deviceid) {
      this.deviceid = deviceid;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public FakeInput build() {
      return new FakeInput(this.type, this.detail, this.time, this.root, this.rootX, this.rootY, this.deviceid);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "FakeInput.FakeInputBuilder(type=" + this.type + ", detail=" + this.detail + ", time=" + this.time + ", root=" + this.root + ", rootX=" + this.rootX + ", rootY=" + this.rootY + ", deviceid=" + this.deviceid + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  FakeInput(final byte type, final byte detail, final int time, final int root, final short rootX, final short rootY, final byte deviceid) {
    this.type = type;
    this.detail = detail;
    this.time = time;
    this.root = root;
    this.rootX = rootX;
    this.rootY = rootY;
    this.deviceid = deviceid;
  }

  @java.lang.SuppressWarnings("all")
  public static FakeInput.FakeInputBuilder builder() {
    return new FakeInput.FakeInputBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getType() {
    return this.type;
  }

  @java.lang.SuppressWarnings("all")
  public byte getDetail() {
    return this.detail;
  }

  @java.lang.SuppressWarnings("all")
  public int getTime() {
    return this.time;
  }

  @java.lang.SuppressWarnings("all")
  public int getRoot() {
    return this.root;
  }

  @java.lang.SuppressWarnings("all")
  public short getRootX() {
    return this.rootX;
  }

  @java.lang.SuppressWarnings("all")
  public short getRootY() {
    return this.rootY;
  }

  @java.lang.SuppressWarnings("all")
  public byte getDeviceid() {
    return this.deviceid;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof FakeInput)) return false;
    final FakeInput other = (FakeInput) o;
    if (this.getType() != other.getType()) return false;
    if (this.getDetail() != other.getDetail()) return false;
    if (this.getTime() != other.getTime()) return false;
    if (this.getRoot() != other.getRoot()) return false;
    if (this.getRootX() != other.getRootX()) return false;
    if (this.getRootY() != other.getRootY()) return false;
    if (this.getDeviceid() != other.getDeviceid()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getDetail();
    result = result * PRIME + this.getTime();
    result = result * PRIME + this.getRoot();
    result = result * PRIME + this.getRootX();
    result = result * PRIME + this.getRootY();
    result = result * PRIME + this.getDeviceid();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "FakeInput(type=" + this.getType() + ", detail=" + this.getDetail() + ", time=" + this.getTime() + ", root=" + this.getRoot() + ", rootX=" + this.getRootX() + ", rootY=" + this.getRootY() + ", deviceid=" + this.getDeviceid() + ")";
  }
}
