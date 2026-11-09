package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class OpenFont implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 45;
  private final int fid;
  private final ByteList name;

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
    ByteList name = in.readChar(Short.toUnsignedInt(nameLen));
    javaBuilder.fid(fid);
    javaBuilder.name(name.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(fid);
    short nameLen = (short) name.size();
    out.writeCard16(nameLen);
    out.writePad(2);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class OpenFontBuilder {
    @java.lang.SuppressWarnings("all")
    private int fid;
    @java.lang.SuppressWarnings("all")
    private ByteList name;

    public int getSize() {
      return 12 + 1 * name.size();
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
    public OpenFont.OpenFontBuilder name(final ByteList name) {
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
      return "OpenFont.OpenFontBuilder(fid=" + this.fid + ", name=" + this.name + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  OpenFont(final int fid, final ByteList name) {
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
  public ByteList getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof OpenFont)) return false;
    final OpenFont other = (OpenFont) o;
    if (this.getFid() != other.getFid()) return false;
    final java.lang.Object this$name = this.getName();
    final java.lang.Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFid();
    final java.lang.Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "OpenFont(fid=" + this.getFid() + ", name=" + this.getName() + ")";
  }
}
