package com.github.moaxcp.x11.protocol.xinerama;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class ScreenInfo implements XStruct {
  public static final String PLUGIN_NAME = "xinerama";
  private final short xOrg;
  private final short yOrg;
  private final short width;
  private final short height;

  public static ScreenInfo readScreenInfo(X11Input in) throws IOException {
    ScreenInfo.ScreenInfoBuilder javaBuilder = ScreenInfo.builder();
    short xOrg = in.readInt16();
    short yOrg = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    javaBuilder.xOrg(xOrg);
    javaBuilder.yOrg(yOrg);
    javaBuilder.width(width);
    javaBuilder.height(height);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt16(xOrg);
    out.writeInt16(yOrg);
    out.writeCard16(width);
    out.writeCard16(height);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ScreenInfoBuilder {
    @java.lang.SuppressWarnings("all")
    private short xOrg;
    @java.lang.SuppressWarnings("all")
    private short yOrg;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    ScreenInfoBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenInfo.ScreenInfoBuilder xOrg(final short xOrg) {
      this.xOrg = xOrg;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenInfo.ScreenInfoBuilder yOrg(final short yOrg) {
      this.yOrg = yOrg;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenInfo.ScreenInfoBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenInfo.ScreenInfoBuilder height(final short height) {
      this.height = height;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ScreenInfo build() {
      return new ScreenInfo(this.xOrg, this.yOrg, this.width, this.height);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ScreenInfo.ScreenInfoBuilder(xOrg=" + this.xOrg + ", yOrg=" + this.yOrg + ", width=" + this.width + ", height=" + this.height + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ScreenInfo(final short xOrg, final short yOrg, final short width, final short height) {
    this.xOrg = xOrg;
    this.yOrg = yOrg;
    this.width = width;
    this.height = height;
  }

  @java.lang.SuppressWarnings("all")
  public static ScreenInfo.ScreenInfoBuilder builder() {
    return new ScreenInfo.ScreenInfoBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getXOrg() {
    return this.xOrg;
  }

  @java.lang.SuppressWarnings("all")
  public short getYOrg() {
    return this.yOrg;
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
    if (!(o instanceof ScreenInfo)) return false;
    final ScreenInfo other = (ScreenInfo) o;
    if (this.getXOrg() != other.getXOrg()) return false;
    if (this.getYOrg() != other.getYOrg()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getXOrg();
    result = result * PRIME + this.getYOrg();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ScreenInfo(xOrg=" + this.getXOrg() + ", yOrg=" + this.getYOrg() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ")";
  }
}
