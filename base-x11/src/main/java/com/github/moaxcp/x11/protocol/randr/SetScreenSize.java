package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SetScreenSize implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 7;
  private final int window;
  private final short width;
  private final short height;
  private final int mmWidth;
  private final int mmHeight;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetScreenSize readSetScreenSize(X11Input in) throws IOException {
    SetScreenSize.SetScreenSizeBuilder javaBuilder = SetScreenSize.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    int mmWidth = in.readCard32();
    int mmHeight = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.mmWidth(mmWidth);
    javaBuilder.mmHeight(mmHeight);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard32(mmWidth);
    out.writeCard32(mmHeight);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetScreenSizeBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;
    @java.lang.SuppressWarnings("all")
    private int mmWidth;
    @java.lang.SuppressWarnings("all")
    private int mmHeight;

    public int getSize() {
      return 20;
    }

    @java.lang.SuppressWarnings("all")
    SetScreenSizeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetScreenSize.SetScreenSizeBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetScreenSize.SetScreenSizeBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetScreenSize.SetScreenSizeBuilder height(final short height) {
      this.height = height;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetScreenSize.SetScreenSizeBuilder mmWidth(final int mmWidth) {
      this.mmWidth = mmWidth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetScreenSize.SetScreenSizeBuilder mmHeight(final int mmHeight) {
      this.mmHeight = mmHeight;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetScreenSize build() {
      return new SetScreenSize(this.window, this.width, this.height, this.mmWidth, this.mmHeight);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetScreenSize.SetScreenSizeBuilder(window=" + this.window + ", width=" + this.width + ", height=" + this.height + ", mmWidth=" + this.mmWidth + ", mmHeight=" + this.mmHeight + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetScreenSize(final int window, final short width, final short height, final int mmWidth, final int mmHeight) {
    this.window = window;
    this.width = width;
    this.height = height;
    this.mmWidth = mmWidth;
    this.mmHeight = mmHeight;
  }

  @java.lang.SuppressWarnings("all")
  public static SetScreenSize.SetScreenSizeBuilder builder() {
    return new SetScreenSize.SetScreenSizeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
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
  public int getMmWidth() {
    return this.mmWidth;
  }

  @java.lang.SuppressWarnings("all")
  public int getMmHeight() {
    return this.mmHeight;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetScreenSize)) return false;
    final SetScreenSize other = (SetScreenSize) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getMmWidth() != other.getMmWidth()) return false;
    if (this.getMmHeight() != other.getMmHeight()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getMmWidth();
    result = result * PRIME + this.getMmHeight();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetScreenSize(window=" + this.getWindow() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", mmWidth=" + this.getMmWidth() + ", mmHeight=" + this.getMmHeight() + ")";
  }
}
