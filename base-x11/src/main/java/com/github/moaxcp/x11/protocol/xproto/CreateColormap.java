package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CreateColormap implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 78;
  private final byte alloc;
  private final int mid;
  private final int window;
  private final int visual;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateColormap readCreateColormap(X11Input in) throws IOException {
    CreateColormap.CreateColormapBuilder javaBuilder = CreateColormap.builder();
    byte alloc = in.readByte();
    short length = in.readCard16();
    int mid = in.readCard32();
    int window = in.readCard32();
    int visual = in.readCard32();
    javaBuilder.alloc(alloc);
    javaBuilder.mid(mid);
    javaBuilder.window(window);
    javaBuilder.visual(visual);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeByte(alloc);
    out.writeCard16((short) getLength());
    out.writeCard32(mid);
    out.writeCard32(window);
    out.writeCard32(visual);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateColormapBuilder {
    @java.lang.SuppressWarnings("all")
    private byte alloc;
    @java.lang.SuppressWarnings("all")
    private int mid;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int visual;

    public CreateColormap.CreateColormapBuilder alloc(ColormapAlloc alloc) {
      this.alloc = (byte) alloc.getValue();
      return this;
    }

    public CreateColormap.CreateColormapBuilder alloc(byte alloc) {
      this.alloc = alloc;
      return this;
    }

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    CreateColormapBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateColormap.CreateColormapBuilder mid(final int mid) {
      this.mid = mid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateColormap.CreateColormapBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateColormap.CreateColormapBuilder visual(final int visual) {
      this.visual = visual;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateColormap build() {
      return new CreateColormap(this.alloc, this.mid, this.window, this.visual);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateColormap.CreateColormapBuilder(alloc=" + this.alloc + ", mid=" + this.mid + ", window=" + this.window + ", visual=" + this.visual + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateColormap(final byte alloc, final int mid, final int window, final int visual) {
    this.alloc = alloc;
    this.mid = mid;
    this.window = window;
    this.visual = visual;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateColormap.CreateColormapBuilder builder() {
    return new CreateColormap.CreateColormapBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getAlloc() {
    return this.alloc;
  }

  @java.lang.SuppressWarnings("all")
  public int getMid() {
    return this.mid;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getVisual() {
    return this.visual;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateColormap)) return false;
    final CreateColormap other = (CreateColormap) o;
    if (this.getAlloc() != other.getAlloc()) return false;
    if (this.getMid() != other.getMid()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getVisual() != other.getVisual()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getAlloc();
    result = result * PRIME + this.getMid();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getVisual();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateColormap(alloc=" + this.getAlloc() + ", mid=" + this.getMid() + ", window=" + this.getWindow() + ", visual=" + this.getVisual() + ")";
  }
}
