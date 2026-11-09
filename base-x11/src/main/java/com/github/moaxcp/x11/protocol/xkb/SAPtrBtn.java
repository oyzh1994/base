package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class SAPtrBtn implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;
  private final byte flags;
  private final byte count;
  private final byte button;

  public static SAPtrBtn readSAPtrBtn(X11Input in) throws IOException {
    SAPtrBtn.SAPtrBtnBuilder javaBuilder = SAPtrBtn.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte count = in.readCard8();
    byte button = in.readCard8();
    byte[] pad4 = in.readPad(4);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.count(count);
    javaBuilder.button(button);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writeCard8(count);
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


  public static class SAPtrBtnBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte flags;
    @java.lang.SuppressWarnings("all")
    private byte count;
    @java.lang.SuppressWarnings("all")
    private byte button;

    public SAPtrBtn.SAPtrBtnBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SAPtrBtn.SAPtrBtnBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    SAPtrBtnBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SAPtrBtn.SAPtrBtnBuilder flags(final byte flags) {
      this.flags = flags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SAPtrBtn.SAPtrBtnBuilder count(final byte count) {
      this.count = count;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SAPtrBtn.SAPtrBtnBuilder button(final byte button) {
      this.button = button;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SAPtrBtn build() {
      return new SAPtrBtn(this.type, this.flags, this.count, this.button);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SAPtrBtn.SAPtrBtnBuilder(type=" + this.type + ", flags=" + this.flags + ", count=" + this.count + ", button=" + this.button + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SAPtrBtn(final byte type, final byte flags, final byte count, final byte button) {
    this.type = type;
    this.flags = flags;
    this.count = count;
    this.button = button;
  }

  @java.lang.SuppressWarnings("all")
  public static SAPtrBtn.SAPtrBtnBuilder builder() {
    return new SAPtrBtn.SAPtrBtnBuilder();
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
  public byte getCount() {
    return this.count;
  }

  @java.lang.SuppressWarnings("all")
  public byte getButton() {
    return this.button;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SAPtrBtn)) return false;
    final SAPtrBtn other = (SAPtrBtn) o;
    if (this.getType() != other.getType()) return false;
    if (this.getFlags() != other.getFlags()) return false;
    if (this.getCount() != other.getCount()) return false;
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
    result = result * PRIME + this.getCount();
    result = result * PRIME + this.getButton();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SAPtrBtn(type=" + this.getType() + ", flags=" + this.getFlags() + ", count=" + this.getCount() + ", button=" + this.getButton() + ")";
  }
}
