package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Directformat implements XStruct {
  public static final String PLUGIN_NAME = "render";
  private final short redShift;
  private final short redMask;
  private final short greenShift;
  private final short greenMask;
  private final short blueShift;
  private final short blueMask;
  private final short alphaShift;
  private final short alphaMask;

  public static Directformat readDirectformat(X11Input in) throws IOException {
    Directformat.DirectformatBuilder javaBuilder = Directformat.builder();
    short redShift = in.readCard16();
    short redMask = in.readCard16();
    short greenShift = in.readCard16();
    short greenMask = in.readCard16();
    short blueShift = in.readCard16();
    short blueMask = in.readCard16();
    short alphaShift = in.readCard16();
    short alphaMask = in.readCard16();
    javaBuilder.redShift(redShift);
    javaBuilder.redMask(redMask);
    javaBuilder.greenShift(greenShift);
    javaBuilder.greenMask(greenMask);
    javaBuilder.blueShift(blueShift);
    javaBuilder.blueMask(blueMask);
    javaBuilder.alphaShift(alphaShift);
    javaBuilder.alphaMask(alphaMask);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(redShift);
    out.writeCard16(redMask);
    out.writeCard16(greenShift);
    out.writeCard16(greenMask);
    out.writeCard16(blueShift);
    out.writeCard16(blueMask);
    out.writeCard16(alphaShift);
    out.writeCard16(alphaMask);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class DirectformatBuilder {
    @java.lang.SuppressWarnings("all")
    private short redShift;
    @java.lang.SuppressWarnings("all")
    private short redMask;
    @java.lang.SuppressWarnings("all")
    private short greenShift;
    @java.lang.SuppressWarnings("all")
    private short greenMask;
    @java.lang.SuppressWarnings("all")
    private short blueShift;
    @java.lang.SuppressWarnings("all")
    private short blueMask;
    @java.lang.SuppressWarnings("all")
    private short alphaShift;
    @java.lang.SuppressWarnings("all")
    private short alphaMask;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    DirectformatBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Directformat.DirectformatBuilder redShift(final short redShift) {
      this.redShift = redShift;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Directformat.DirectformatBuilder redMask(final short redMask) {
      this.redMask = redMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Directformat.DirectformatBuilder greenShift(final short greenShift) {
      this.greenShift = greenShift;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Directformat.DirectformatBuilder greenMask(final short greenMask) {
      this.greenMask = greenMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Directformat.DirectformatBuilder blueShift(final short blueShift) {
      this.blueShift = blueShift;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Directformat.DirectformatBuilder blueMask(final short blueMask) {
      this.blueMask = blueMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Directformat.DirectformatBuilder alphaShift(final short alphaShift) {
      this.alphaShift = alphaShift;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Directformat.DirectformatBuilder alphaMask(final short alphaMask) {
      this.alphaMask = alphaMask;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Directformat build() {
      return new Directformat(this.redShift, this.redMask, this.greenShift, this.greenMask, this.blueShift, this.blueMask, this.alphaShift, this.alphaMask);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Directformat.DirectformatBuilder(redShift=" + this.redShift + ", redMask=" + this.redMask + ", greenShift=" + this.greenShift + ", greenMask=" + this.greenMask + ", blueShift=" + this.blueShift + ", blueMask=" + this.blueMask + ", alphaShift=" + this.alphaShift + ", alphaMask=" + this.alphaMask + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Directformat(final short redShift, final short redMask, final short greenShift, final short greenMask, final short blueShift, final short blueMask, final short alphaShift, final short alphaMask) {
    this.redShift = redShift;
    this.redMask = redMask;
    this.greenShift = greenShift;
    this.greenMask = greenMask;
    this.blueShift = blueShift;
    this.blueMask = blueMask;
    this.alphaShift = alphaShift;
    this.alphaMask = alphaMask;
  }

  @java.lang.SuppressWarnings("all")
  public static Directformat.DirectformatBuilder builder() {
    return new Directformat.DirectformatBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getRedShift() {
    return this.redShift;
  }

  @java.lang.SuppressWarnings("all")
  public short getRedMask() {
    return this.redMask;
  }

  @java.lang.SuppressWarnings("all")
  public short getGreenShift() {
    return this.greenShift;
  }

  @java.lang.SuppressWarnings("all")
  public short getGreenMask() {
    return this.greenMask;
  }

  @java.lang.SuppressWarnings("all")
  public short getBlueShift() {
    return this.blueShift;
  }

  @java.lang.SuppressWarnings("all")
  public short getBlueMask() {
    return this.blueMask;
  }

  @java.lang.SuppressWarnings("all")
  public short getAlphaShift() {
    return this.alphaShift;
  }

  @java.lang.SuppressWarnings("all")
  public short getAlphaMask() {
    return this.alphaMask;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Directformat)) return false;
    final Directformat other = (Directformat) o;
    if (this.getRedShift() != other.getRedShift()) return false;
    if (this.getRedMask() != other.getRedMask()) return false;
    if (this.getGreenShift() != other.getGreenShift()) return false;
    if (this.getGreenMask() != other.getGreenMask()) return false;
    if (this.getBlueShift() != other.getBlueShift()) return false;
    if (this.getBlueMask() != other.getBlueMask()) return false;
    if (this.getAlphaShift() != other.getAlphaShift()) return false;
    if (this.getAlphaMask() != other.getAlphaMask()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getRedShift();
    result = result * PRIME + this.getRedMask();
    result = result * PRIME + this.getGreenShift();
    result = result * PRIME + this.getGreenMask();
    result = result * PRIME + this.getBlueShift();
    result = result * PRIME + this.getBlueMask();
    result = result * PRIME + this.getAlphaShift();
    result = result * PRIME + this.getAlphaMask();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Directformat(redShift=" + this.getRedShift() + ", redMask=" + this.getRedMask() + ", greenShift=" + this.getGreenShift() + ", greenMask=" + this.getGreenMask() + ", blueShift=" + this.getBlueShift() + ", blueMask=" + this.getBlueMask() + ", alphaShift=" + this.getAlphaShift() + ", alphaMask=" + this.getAlphaMask() + ")";
  }
}
