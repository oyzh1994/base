package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.IntList;

public final class GetKeyboardMappingReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final byte keysymsPerKeycode;
  private final short sequenceNumber;
  private final IntList keysyms;

  public static GetKeyboardMappingReply readGetKeyboardMappingReply(byte keysymsPerKeycode, short sequenceNumber, X11Input in) throws IOException {
    GetKeyboardMappingReply.GetKeyboardMappingReplyBuilder javaBuilder = GetKeyboardMappingReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(24);
    IntList keysyms = in.readCard32((int) (Integer.toUnsignedLong(length)));
    javaBuilder.keysymsPerKeycode(keysymsPerKeycode);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.keysyms(keysyms.toImmutable());
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeByte(keysymsPerKeycode);
    out.writeCard16(sequenceNumber);
    int length = keysyms.size();
    out.writeCard32(getLength());
    out.writePad(24);
    out.writeCard32(keysyms);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * keysyms.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetKeyboardMappingReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte keysymsPerKeycode;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private IntList keysyms;

    public int getSize() {
      return 32 + 4 * keysyms.size();
    }

    @java.lang.SuppressWarnings("all")
    GetKeyboardMappingReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetKeyboardMappingReply.GetKeyboardMappingReplyBuilder keysymsPerKeycode(final byte keysymsPerKeycode) {
      this.keysymsPerKeycode = keysymsPerKeycode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetKeyboardMappingReply.GetKeyboardMappingReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetKeyboardMappingReply.GetKeyboardMappingReplyBuilder keysyms(final IntList keysyms) {
      if (keysyms == null) {
        throw new java.lang.NullPointerException("keysyms is marked non-null but is null");
      }
      this.keysyms = keysyms;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetKeyboardMappingReply build() {
      return new GetKeyboardMappingReply(this.keysymsPerKeycode, this.sequenceNumber, this.keysyms);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetKeyboardMappingReply.GetKeyboardMappingReplyBuilder(keysymsPerKeycode=" + this.keysymsPerKeycode + ", sequenceNumber=" + this.sequenceNumber + ", keysyms=" + this.keysyms + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetKeyboardMappingReply(final byte keysymsPerKeycode, final short sequenceNumber, final IntList keysyms) {
    if (keysyms == null) {
      throw new java.lang.NullPointerException("keysyms is marked non-null but is null");
    }
    this.keysymsPerKeycode = keysymsPerKeycode;
    this.sequenceNumber = sequenceNumber;
    this.keysyms = keysyms;
  }

  @java.lang.SuppressWarnings("all")
  public static GetKeyboardMappingReply.GetKeyboardMappingReplyBuilder builder() {
    return new GetKeyboardMappingReply.GetKeyboardMappingReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getKeysymsPerKeycode() {
    return this.keysymsPerKeycode;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getKeysyms() {
    return this.keysyms;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetKeyboardMappingReply)) return false;
    final GetKeyboardMappingReply other = (GetKeyboardMappingReply) o;
    if (this.getKeysymsPerKeycode() != other.getKeysymsPerKeycode()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$keysyms = this.getKeysyms();
    final java.lang.Object other$keysyms = other.getKeysyms();
    if (this$keysyms == null ? other$keysyms != null : !this$keysyms.equals(other$keysyms)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getKeysymsPerKeycode();
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $keysyms = this.getKeysyms();
    result = result * PRIME + ($keysyms == null ? 43 : $keysyms.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetKeyboardMappingReply(keysymsPerKeycode=" + this.getKeysymsPerKeycode() + ", sequenceNumber=" + this.getSequenceNumber() + ", keysyms=" + this.getKeysyms() + ")";
  }
}
