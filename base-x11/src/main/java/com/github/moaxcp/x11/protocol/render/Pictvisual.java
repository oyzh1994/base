package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Pictvisual implements XStruct {
  public static final String PLUGIN_NAME = "render";
  private final int visual;
  private final int format;

  public static Pictvisual readPictvisual(X11Input in) throws IOException {
    Pictvisual.PictvisualBuilder javaBuilder = Pictvisual.builder();
    int visual = in.readCard32();
    int format = in.readCard32();
    javaBuilder.visual(visual);
    javaBuilder.format(format);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(visual);
    out.writeCard32(format);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class PictvisualBuilder {
    @java.lang.SuppressWarnings("all")
    private int visual;
    @java.lang.SuppressWarnings("all")
    private int format;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    PictvisualBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pictvisual.PictvisualBuilder visual(final int visual) {
      this.visual = visual;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pictvisual.PictvisualBuilder format(final int format) {
      this.format = format;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Pictvisual build() {
      return new Pictvisual(this.visual, this.format);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Pictvisual.PictvisualBuilder(visual=" + this.visual + ", format=" + this.format + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Pictvisual(final int visual, final int format) {
    this.visual = visual;
    this.format = format;
  }

  @java.lang.SuppressWarnings("all")
  public static Pictvisual.PictvisualBuilder builder() {
    return new Pictvisual.PictvisualBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getVisual() {
    return this.visual;
  }

  @java.lang.SuppressWarnings("all")
  public int getFormat() {
    return this.format;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Pictvisual)) return false;
    final Pictvisual other = (Pictvisual) o;
    if (this.getVisual() != other.getVisual()) return false;
    if (this.getFormat() != other.getFormat()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getVisual();
    result = result * PRIME + this.getFormat();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Pictvisual(visual=" + this.getVisual() + ", format=" + this.getFormat() + ")";
  }
}
