package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class SetDebuggingFlags implements TwoWayRequest<SetDebuggingFlagsReply> {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 101;
  private final int affectFlags;
  private final int flags;
  private final int affectCtrls;
  private final int ctrls;
  private final ByteList message;

  public XReplyFunction<SetDebuggingFlagsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SetDebuggingFlagsReply.readSetDebuggingFlagsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetDebuggingFlags readSetDebuggingFlags(X11Input in) throws IOException {
    SetDebuggingFlags.SetDebuggingFlagsBuilder javaBuilder = SetDebuggingFlags.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short msgLength = in.readCard16();
    byte[] pad4 = in.readPad(2);
    int affectFlags = in.readCard32();
    int flags = in.readCard32();
    int affectCtrls = in.readCard32();
    int ctrls = in.readCard32();
    ByteList message = in.readChar(Short.toUnsignedInt(msgLength));
    javaBuilder.affectFlags(affectFlags);
    javaBuilder.flags(flags);
    javaBuilder.affectCtrls(affectCtrls);
    javaBuilder.ctrls(ctrls);
    javaBuilder.message(message.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    short msgLength = (short) message.size();
    out.writeCard16(msgLength);
    out.writePad(2);
    out.writeCard32(affectFlags);
    out.writeCard32(flags);
    out.writeCard32(affectCtrls);
    out.writeCard32(ctrls);
    out.writeChar(message);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 24 + 1 * message.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetDebuggingFlagsBuilder {
    @java.lang.SuppressWarnings("all")
    private int affectFlags;
    @java.lang.SuppressWarnings("all")
    private int flags;
    @java.lang.SuppressWarnings("all")
    private int affectCtrls;
    @java.lang.SuppressWarnings("all")
    private int ctrls;
    @java.lang.SuppressWarnings("all")
    private ByteList message;

    public int getSize() {
      return 24 + 1 * message.size();
    }

    @java.lang.SuppressWarnings("all")
    SetDebuggingFlagsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetDebuggingFlags.SetDebuggingFlagsBuilder affectFlags(final int affectFlags) {
      this.affectFlags = affectFlags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetDebuggingFlags.SetDebuggingFlagsBuilder flags(final int flags) {
      this.flags = flags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetDebuggingFlags.SetDebuggingFlagsBuilder affectCtrls(final int affectCtrls) {
      this.affectCtrls = affectCtrls;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetDebuggingFlags.SetDebuggingFlagsBuilder ctrls(final int ctrls) {
      this.ctrls = ctrls;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetDebuggingFlags.SetDebuggingFlagsBuilder message(final ByteList message) {
      if (message == null) {
        throw new java.lang.NullPointerException("message is marked non-null but is null");
      }
      this.message = message;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetDebuggingFlags build() {
      return new SetDebuggingFlags(this.affectFlags, this.flags, this.affectCtrls, this.ctrls, this.message);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetDebuggingFlags.SetDebuggingFlagsBuilder(affectFlags=" + this.affectFlags + ", flags=" + this.flags + ", affectCtrls=" + this.affectCtrls + ", ctrls=" + this.ctrls + ", message=" + this.message + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetDebuggingFlags(final int affectFlags, final int flags, final int affectCtrls, final int ctrls, final ByteList message) {
    if (message == null) {
      throw new java.lang.NullPointerException("message is marked non-null but is null");
    }
    this.affectFlags = affectFlags;
    this.flags = flags;
    this.affectCtrls = affectCtrls;
    this.ctrls = ctrls;
    this.message = message;
  }

  @java.lang.SuppressWarnings("all")
  public static SetDebuggingFlags.SetDebuggingFlagsBuilder builder() {
    return new SetDebuggingFlags.SetDebuggingFlagsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getAffectFlags() {
    return this.affectFlags;
  }

  @java.lang.SuppressWarnings("all")
  public int getFlags() {
    return this.flags;
  }

  @java.lang.SuppressWarnings("all")
  public int getAffectCtrls() {
    return this.affectCtrls;
  }

  @java.lang.SuppressWarnings("all")
  public int getCtrls() {
    return this.ctrls;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getMessage() {
    return this.message;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetDebuggingFlags)) return false;
    final SetDebuggingFlags other = (SetDebuggingFlags) o;
    if (this.getAffectFlags() != other.getAffectFlags()) return false;
    if (this.getFlags() != other.getFlags()) return false;
    if (this.getAffectCtrls() != other.getAffectCtrls()) return false;
    if (this.getCtrls() != other.getCtrls()) return false;
    final java.lang.Object this$message = this.getMessage();
    final java.lang.Object other$message = other.getMessage();
    if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getAffectFlags();
    result = result * PRIME + this.getFlags();
    result = result * PRIME + this.getAffectCtrls();
    result = result * PRIME + this.getCtrls();
    final java.lang.Object $message = this.getMessage();
    result = result * PRIME + ($message == null ? 43 : $message.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetDebuggingFlags(affectFlags=" + this.getAffectFlags() + ", flags=" + this.getFlags() + ", affectCtrls=" + this.getAffectCtrls() + ", ctrls=" + this.getCtrls() + ", message=" + this.getMessage() + ")";
  }
}
