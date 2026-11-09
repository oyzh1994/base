package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class SALockPtrBtn implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;
  private final byte flags;
  private final byte button;

  public static SALockPtrBtn readSALockPtrBtn(X11Input in) throws IOException {
    SALockPtrBtn.SALockPtrBtnBuilder javaBuilder = SALockPtrBtn.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte[] pad2 = in.readPad(1);
    byte button = in.readCard8();
    byte[] pad4 = in.readPad(4);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.button(button);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writePad(1);
    out.writeCard8(button);
    out.writePad(4);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SALockPtrBtnBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte flags;
    @java.lang.SuppressWarnings("all")
    private byte button;

    public SALockPtrBtn.SALockPtrBtnBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SALockPtrBtn.SALockPtrBtnBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    SALockPtrBtnBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SALockPtrBtn.SALockPtrBtnBuilder flags(final byte flags) {
      this.flags = flags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SALockPtrBtn.SALockPtrBtnBuilder button(final byte button) {
      this.button = button;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SALockPtrBtn build() {
      return new SALockPtrBtn(this.type, this.flags, this.button);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SALockPtrBtn.SALockPtrBtnBuilder(type=" + this.type + ", flags=" + this.flags + ", button=" + this.button + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SALockPtrBtn(final byte type, final byte flags, final byte button) {
    this.type = type;
    this.flags = flags;
    this.button = button;
  }

  @java.lang.SuppressWarnings("all")
  public static SALockPtrBtn.SALockPtrBtnBuilder builder() {
    return new SALockPtrBtn.SALockPtrBtnBuilder();
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
  public byte getButton() {
    return this.button;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SALockPtrBtn)) return false;
    final SALockPtrBtn other = (SALockPtrBtn) o;
    if (this.getType() != other.getType()) return false;
    if (this.getFlags() != other.getFlags()) return false;
    if (this.getButton() != other.getButton()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getFlags();
    result = result * PRIME + this.getButton();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SALockPtrBtn(type=" + this.getType() + ", flags=" + this.getFlags() + ", button=" + this.getButton() + ")";
  }
}
