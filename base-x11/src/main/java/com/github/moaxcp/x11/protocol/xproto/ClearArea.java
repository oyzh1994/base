package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ClearArea implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 61;
  private final boolean exposures;
  private final int window;
  private final short x;
  private final short y;
  private final short width;
  private final short height;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ClearArea readClearArea(X11Input in) throws IOException {
    ClearArea.ClearAreaBuilder javaBuilder = ClearArea.builder();
    boolean exposures = in.readBool();
    short length = in.readCard16();
    int window = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    javaBuilder.exposures(exposures);
    javaBuilder.window(window);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeBool(exposures);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ClearAreaBuilder {
    @java.lang.SuppressWarnings("all")
    private boolean exposures;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    ClearAreaBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ClearArea.ClearAreaBuilder exposures(final boolean exposures) {
      this.exposures = exposures;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ClearArea.ClearAreaBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ClearArea.ClearAreaBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ClearArea.ClearAreaBuilder y(final short y) {
      this.y = y;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ClearArea.ClearAreaBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ClearArea.ClearAreaBuilder height(final short height) {
      this.height = height;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ClearArea build() {
      return new ClearArea(this.exposures, this.window, this.x, this.y, this.width, this.height);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ClearArea.ClearAreaBuilder(exposures=" + this.exposures + ", window=" + this.window + ", x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ClearArea(final boolean exposures, final int window, final short x, final short y, final short width, final short height) {
    this.exposures = exposures;
    this.window = window;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  @java.lang.SuppressWarnings("all")
  public static ClearArea.ClearAreaBuilder builder() {
    return new ClearArea.ClearAreaBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public boolean isExposures() {
    return this.exposures;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
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

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ClearArea)) return false;
    final ClearArea other = (ClearArea) o;
    if (this.isExposures() != other.isExposures()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + (this.isExposures() ? 79 : 97);
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ClearArea(exposures=" + this.isExposures() + ", window=" + this.getWindow() + ", x=" + this.getX() + ", y=" + this.getY() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ")";
  }
}
