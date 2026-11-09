package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class ScreenSize implements XStruct {
  public static final String PLUGIN_NAME = "randr";
  private final short width;
  private final short height;
  private final short mwidth;
  private final short mheight;

  public static ScreenSize readScreenSize(X11Input in) throws IOException {
    ScreenSize.ScreenSizeBuilder javaBuilder = ScreenSize.builder();
    short width = in.readCard16();
    short height = in.readCard16();
    short mwidth = in.readCard16();
    short mheight = in.readCard16();
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.mwidth(mwidth);
    javaBuilder.mheight(mheight);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(mwidth);
    out.writeCard16(mheight);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ScreenSizeBuilder {
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;
    @java.lang.SuppressWarnings("all")
    private short mwidth;
    @java.lang.SuppressWarnings("all")
    private short mheight;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    ScreenSizeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenSize.ScreenSizeBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenSize.ScreenSizeBuilder height(final short height) {
      this.height = height;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenSize.ScreenSizeBuilder mwidth(final short mwidth) {
      this.mwidth = mwidth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenSize.ScreenSizeBuilder mheight(final short mheight) {
      this.mheight = mheight;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ScreenSize build() {
      return new ScreenSize(this.width, this.height, this.mwidth, this.mheight);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ScreenSize.ScreenSizeBuilder(width=" + this.width + ", height=" + this.height + ", mwidth=" + this.mwidth + ", mheight=" + this.mheight + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ScreenSize(final short width, final short height, final short mwidth, final short mheight) {
    this.width = width;
    this.height = height;
    this.mwidth = mwidth;
    this.mheight = mheight;
  }

  @java.lang.SuppressWarnings("all")
  public static ScreenSize.ScreenSizeBuilder builder() {
    return new ScreenSize.ScreenSizeBuilder();
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
  public short getMwidth() {
    return this.mwidth;
  }

  @java.lang.SuppressWarnings("all")
  public short getMheight() {
    return this.mheight;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ScreenSize)) return false;
    final ScreenSize other = (ScreenSize) o;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getMwidth() != other.getMwidth()) return false;
    if (this.getMheight() != other.getMheight()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getMwidth();
    result = result * PRIME + this.getMheight();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ScreenSize(width=" + this.getWidth() + ", height=" + this.getHeight() + ", mwidth=" + this.getMwidth() + ", mheight=" + this.getMheight() + ")";
  }
}
