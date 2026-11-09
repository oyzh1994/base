package com.github.moaxcp.x11.protocol.xproto;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetFontPathReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final List<Str> path;

  public static GetFontPathReply readGetFontPathReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetFontPathReply.GetFontPathReplyBuilder javaBuilder = GetFontPathReply.builder();
    int length = in.readCard32();
    short pathLen = in.readCard16();
    byte[] pad5 = in.readPad(22);
    List<Str> path = new ArrayList<>(Short.toUnsignedInt(pathLen));
    for (int i = 0; i < Short.toUnsignedInt(pathLen); i++) {
      path.add(Str.readStr(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.path(List.copyOf(path));
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    short pathLen = (short) path.size();
    out.writeCard16(pathLen);
    out.writePad(22);
    for (Str t : path) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(path);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetFontPathReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private List<Str> path;

    public int getSize() {
      return 32 + XObject.sizeOf(path);
    }

    @java.lang.SuppressWarnings("all")
    GetFontPathReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetFontPathReply.GetFontPathReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetFontPathReply.GetFontPathReplyBuilder path(final List<Str> path) {
      if (path == null) {
        throw new java.lang.NullPointerException("path is marked non-null but is null");
      }
      this.path = path;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetFontPathReply build() {
      return new GetFontPathReply(this.sequenceNumber, this.path);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetFontPathReply.GetFontPathReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", path=" + this.path + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetFontPathReply(final short sequenceNumber, final List<Str> path) {
    if (path == null) {
      throw new java.lang.NullPointerException("path is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.path = path;
  }

  @java.lang.SuppressWarnings("all")
  public static GetFontPathReply.GetFontPathReplyBuilder builder() {
    return new GetFontPathReply.GetFontPathReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public List<Str> getPath() {
    return this.path;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetFontPathReply)) return false;
    final GetFontPathReply other = (GetFontPathReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$path = this.getPath();
    final java.lang.Object other$path = other.getPath();
    if (this$path == null ? other$path != null : !this$path.equals(other$path)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $path = this.getPath();
    result = result * PRIME + ($path == null ? 43 : $path.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetFontPathReply(sequenceNumber=" + this.getSequenceNumber() + ", path=" + this.getPath() + ")";
  }
}
