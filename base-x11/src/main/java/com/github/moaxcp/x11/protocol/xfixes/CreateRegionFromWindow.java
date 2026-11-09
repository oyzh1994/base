package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.shape.Sk;
import java.io.IOException;

public final class CreateRegionFromWindow implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 7;
  private final int region;
  private final int window;
  private final byte kind;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateRegionFromWindow readCreateRegionFromWindow(X11Input in) throws IOException {
    CreateRegionFromWindow.CreateRegionFromWindowBuilder javaBuilder = CreateRegionFromWindow.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int region = in.readCard32();
    int window = in.readCard32();
    byte kind = in.readCard8();
    byte[] pad6 = in.readPad(3);
    javaBuilder.region(region);
    javaBuilder.window(window);
    javaBuilder.kind(kind);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(region);
    out.writeCard32(window);
    out.writeCard8(kind);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateRegionFromWindowBuilder {
    @java.lang.SuppressWarnings("all")
    private int region;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private byte kind;

    public CreateRegionFromWindow.CreateRegionFromWindowBuilder kind(Sk kind) {
      this.kind = (byte) kind.getValue();
      return this;
    }

    public CreateRegionFromWindow.CreateRegionFromWindowBuilder kind(byte kind) {
      this.kind = kind;
      return this;
    }

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    CreateRegionFromWindowBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRegionFromWindow.CreateRegionFromWindowBuilder region(final int region) {
      this.region = region;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRegionFromWindow.CreateRegionFromWindowBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateRegionFromWindow build() {
      return new CreateRegionFromWindow(this.region, this.window, this.kind);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateRegionFromWindow.CreateRegionFromWindowBuilder(region=" + this.region + ", window=" + this.window + ", kind=" + this.kind + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateRegionFromWindow(final int region, final int window, final byte kind) {
    this.region = region;
    this.window = window;
    this.kind = kind;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateRegionFromWindow.CreateRegionFromWindowBuilder builder() {
    return new CreateRegionFromWindow.CreateRegionFromWindowBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getRegion() {
    return this.region;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public byte getKind() {
    return this.kind;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateRegionFromWindow)) return false;
    final CreateRegionFromWindow other = (CreateRegionFromWindow) o;
    if (this.getRegion() != other.getRegion()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getKind() != other.getKind()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getRegion();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getKind();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateRegionFromWindow(region=" + this.getRegion() + ", window=" + this.getWindow() + ", kind=" + this.getKind() + ")";
  }
}
