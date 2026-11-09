package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CreateSolidFill implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 33;
  private final int picture;
  private final Color color;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateSolidFill readCreateSolidFill(X11Input in) throws IOException {
    CreateSolidFill.CreateSolidFillBuilder javaBuilder = CreateSolidFill.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int picture = in.readCard32();
    Color color = Color.readColor(in);
    javaBuilder.picture(picture);
    javaBuilder.color(color);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(picture);
    color.write(out);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateSolidFillBuilder {
    @java.lang.SuppressWarnings("all")
    private int picture;
    @java.lang.SuppressWarnings("all")
    private Color color;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    CreateSolidFillBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateSolidFill.CreateSolidFillBuilder picture(final int picture) {
      this.picture = picture;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateSolidFill.CreateSolidFillBuilder color(final Color color) {
      if (color == null) {
        throw new java.lang.NullPointerException("color is marked non-null but is null");
      }
      this.color = color;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateSolidFill build() {
      return new CreateSolidFill(this.picture, this.color);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateSolidFill.CreateSolidFillBuilder(picture=" + this.picture + ", color=" + this.color + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateSolidFill(final int picture, final Color color) {
    if (color == null) {
      throw new java.lang.NullPointerException("color is marked non-null but is null");
    }
    this.picture = picture;
    this.color = color;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateSolidFill.CreateSolidFillBuilder builder() {
    return new CreateSolidFill.CreateSolidFillBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getPicture() {
    return this.picture;
  }

  @java.lang.SuppressWarnings("all")
  public Color getColor() {
    return this.color;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateSolidFill)) return false;
    final CreateSolidFill other = (CreateSolidFill) o;
    if (this.getPicture() != other.getPicture()) return false;
    final java.lang.Object this$color = this.getColor();
    final java.lang.Object other$color = other.getColor();
    if (this$color == null ? other$color != null : !this$color.equals(other$color)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getPicture();
    final java.lang.Object $color = this.getColor();
    result = result * PRIME + ($color == null ? 43 : $color.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateSolidFill(picture=" + this.getPicture() + ", color=" + this.getColor() + ")";
  }
}
