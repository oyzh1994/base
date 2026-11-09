package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class SAActionMessage implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;
  private final byte flags;
  private final ByteList message;

  public static SAActionMessage readSAActionMessage(X11Input in) throws IOException {
    SAActionMessage.SAActionMessageBuilder javaBuilder = SAActionMessage.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    ByteList message = in.readCard8(6);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.message(message.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writeCard8(message);
  }

  public boolean isFlagsEnabled(ActionMessageFlag... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ActionMessageFlag m : maskEnums) {
      if (!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 2 + 1 * message.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SAActionMessageBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte flags;
    @java.lang.SuppressWarnings("all")
    private ByteList message;

    public SAActionMessage.SAActionMessageBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SAActionMessage.SAActionMessageBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public boolean isFlagsEnabled(ActionMessageFlag... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ActionMessageFlag m : maskEnums) {
        if (!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public SAActionMessage.SAActionMessageBuilder flagsEnable(ActionMessageFlag... maskEnums) {
      for (ActionMessageFlag m : maskEnums) {
        flags((byte) m.enableFor(flags));
      }
      return this;
    }

    public SAActionMessage.SAActionMessageBuilder flagsDisable(ActionMessageFlag... maskEnums) {
      for (ActionMessageFlag m : maskEnums) {
        flags((byte) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 2 + 1 * message.size();
    }

    @java.lang.SuppressWarnings("all")
    SAActionMessageBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SAActionMessage.SAActionMessageBuilder flags(final byte flags) {
      this.flags = flags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SAActionMessage.SAActionMessageBuilder message(final ByteList message) {
      if (message == null) {
        throw new java.lang.NullPointerException("message is marked non-null but is null");
      }
      this.message = message;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SAActionMessage build() {
      return new SAActionMessage(this.type, this.flags, this.message);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SAActionMessage.SAActionMessageBuilder(type=" + this.type + ", flags=" + this.flags + ", message=" + this.message + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SAActionMessage(final byte type, final byte flags, final ByteList message) {
    if (message == null) {
      throw new java.lang.NullPointerException("message is marked non-null but is null");
    }
    this.type = type;
    this.flags = flags;
    this.message = message;
  }

  @java.lang.SuppressWarnings("all")
  public static SAActionMessage.SAActionMessageBuilder builder() {
    return new SAActionMessage.SAActionMessageBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getType() {
    return this.type;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFlags() {
    return this.flags;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getMessage() {
    return this.message;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SAActionMessage)) return false;
    final SAActionMessage other = (SAActionMessage) o;
    if (this.getType() != other.getType()) return false;
    if (this.getFlags() != other.getFlags()) return false;
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
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getFlags();
    final java.lang.Object $message = this.getMessage();
    result = result * PRIME + ($message == null ? 43 : $message.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SAActionMessage(type=" + this.getType() + ", flags=" + this.getFlags() + ", message=" + this.getMessage() + ")";
  }
}
