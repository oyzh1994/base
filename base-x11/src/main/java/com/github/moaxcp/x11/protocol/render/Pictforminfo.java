package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Pictforminfo implements XStruct {
  public static final String PLUGIN_NAME = "render";
  private final int id;
  private final byte type;
  private final byte depth;
  private final Directformat direct;
  private final int colormap;

  public static Pictforminfo readPictforminfo(X11Input in) throws IOException {
    Pictforminfo.PictforminfoBuilder javaBuilder = Pictforminfo.builder();
    int id = in.readCard32();
    byte type = in.readCard8();
    byte depth = in.readCard8();
    byte[] pad3 = in.readPad(2);
    Directformat direct = Directformat.readDirectformat(in);
    int colormap = in.readCard32();
    javaBuilder.id(id);
    javaBuilder.type(type);
    javaBuilder.depth(depth);
    javaBuilder.direct(direct);
    javaBuilder.colormap(colormap);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(id);
    out.writeCard8(type);
    out.writeCard8(depth);
    out.writePad(2);
    direct.write(out);
    out.writeCard32(colormap);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class PictforminfoBuilder {
    @java.lang.SuppressWarnings("all")
    private int id;
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte depth;
    @java.lang.SuppressWarnings("all")
    private Directformat direct;
    @java.lang.SuppressWarnings("all")
    private int colormap;

    public Pictforminfo.PictforminfoBuilder type(PictType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public Pictforminfo.PictforminfoBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 28;
    }

    @java.lang.SuppressWarnings("all")
    PictforminfoBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pictforminfo.PictforminfoBuilder id(final int id) {
      this.id = id;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pictforminfo.PictforminfoBuilder depth(final byte depth) {
      this.depth = depth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pictforminfo.PictforminfoBuilder direct(final Directformat direct) {
      if (direct == null) {
        throw new java.lang.NullPointerException("direct is marked non-null but is null");
      }
      this.direct = direct;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pictforminfo.PictforminfoBuilder colormap(final int colormap) {
      this.colormap = colormap;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Pictforminfo build() {
      return new Pictforminfo(this.id, this.type, this.depth, this.direct, this.colormap);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Pictforminfo.PictforminfoBuilder(id=" + this.id + ", type=" + this.type + ", depth=" + this.depth + ", direct=" + this.direct + ", colormap=" + this.colormap + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Pictforminfo(final int id, final byte type, final byte depth, final Directformat direct, final int colormap) {
    if (direct == null) {
      throw new java.lang.NullPointerException("direct is marked non-null but is null");
    }
    this.id = id;
    this.type = type;
    this.depth = depth;
    this.direct = direct;
    this.colormap = colormap;
  }

  @java.lang.SuppressWarnings("all")
  public static Pictforminfo.PictforminfoBuilder builder() {
    return new Pictforminfo.PictforminfoBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getId() {
    return this.id;
  }

  @java.lang.SuppressWarnings("all")
  public byte getType() {
    return this.type;
  }

  @java.lang.SuppressWarnings("all")
  public byte getDepth() {
    return this.depth;
  }

  @java.lang.SuppressWarnings("all")
  public Directformat getDirect() {
    return this.direct;
  }

  @java.lang.SuppressWarnings("all")
  public int getColormap() {
    return this.colormap;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Pictforminfo)) return false;
    final Pictforminfo other = (Pictforminfo) o;
    if (this.getId() != other.getId()) return false;
    if (this.getType() != other.getType()) return false;
    if (this.getDepth() != other.getDepth()) return false;
    if (this.getColormap() != other.getColormap()) return false;
    final java.lang.Object this$direct = this.getDirect();
    final java.lang.Object other$direct = other.getDirect();
    if (this$direct == null ? other$direct != null : !this$direct.equals(other$direct)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getId();
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getDepth();
    result = result * PRIME + this.getColormap();
    final java.lang.Object $direct = this.getDirect();
    result = result * PRIME + ($direct == null ? 43 : $direct.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Pictforminfo(id=" + this.getId() + ", type=" + this.getType() + ", depth=" + this.getDepth() + ", direct=" + this.getDirect() + ", colormap=" + this.getColormap() + ")";
  }
}
