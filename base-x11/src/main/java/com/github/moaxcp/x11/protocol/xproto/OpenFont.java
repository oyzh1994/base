package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class OpenFont implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 45;
  private final int fid;
  private final byte[] name;

  public byte getOpCode() {
    return OPCODE;
  }

  public static OpenFont readOpenFont(X11Input in) throws IOException {
    OpenFont.OpenFontBuilder javaBuilder = OpenFont.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int fid = in.readCard32();
    short nameLen = in.readCard16();
    byte[] pad5 = in.readPad(2);
    byte[] name = in.readChar(Short.toUnsignedInt(nameLen));
    javaBuilder.fid(fid);
    javaBuilder.name(name);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(fid);
    short nameLen = (short) name.length;
    out.writeCard16(nameLen);
    out.writePad(2);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 1 * name.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class OpenFontBuilder {
    @java.lang.SuppressWarnings("all")
    private int fid;
    @java.lang.SuppressWarnings("all")
    private byte[] name;

    public int getSize() {
      return 12 + 1 * name.length;
    }

    @java.lang.SuppressWarnings("all")
    OpenFontBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OpenFont.OpenFontBuilder fid(final int fid) {
      this.fid = fid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OpenFont.OpenFontBuilder name(final byte[] name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public OpenFont build() {
      return new OpenFont(this.fid, this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "OpenFont.OpenFontBuilder(fid=" + this.fid + ", name=" + java.util.Arrays.toString(this.name) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  OpenFont(final int fid, final byte[] name) {
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.fid = fid;
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static OpenFont.OpenFontBuilder builder() {
    return new OpenFont.OpenFontBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getFid() {
    return this.fid;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof OpenFont)) return false;
    final OpenFont other = (OpenFont) o;
    if (this.getFid() != other.getFid()) return false;
    if (!java.util.Arrays.equals(this.getName(), other.getName())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFid();
    result = result * PRIME + java.util.Arrays.hashCode(this.getName());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "OpenFont(fid=" + this.getFid() + ", name=" + java.util.Arrays.toString(this.getName()) + ")";
  }
}
