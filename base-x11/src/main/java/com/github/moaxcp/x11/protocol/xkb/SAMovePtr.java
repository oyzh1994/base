package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class SAMovePtr implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;
  private final byte flags;
  private final byte xHigh;
  private final byte xLow;
  private final byte yHigh;
  private final byte yLow;

  public static SAMovePtr readSAMovePtr(X11Input in) throws IOException {
    SAMovePtr.SAMovePtrBuilder javaBuilder = SAMovePtr.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte xHigh = in.readInt8();
    byte xLow = in.readCard8();
    byte yHigh = in.readInt8();
    byte yLow = in.readCard8();
    byte[] pad6 = in.readPad(2);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.xHigh(xHigh);
    javaBuilder.xLow(xLow);
    javaBuilder.yHigh(yHigh);
    javaBuilder.yLow(yLow);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writeInt8(xHigh);
    out.writeCard8(xLow);
    out.writeInt8(yHigh);
    out.writeCard8(yLow);
    out.writePad(2);
  }

  public boolean isFlagsEnabled(SAMovePtrFlag... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (SAMovePtrFlag m : maskEnums) {
      if (!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SAMovePtrBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte flags;
    @java.lang.SuppressWarnings("all")
    private byte xHigh;
    @java.lang.SuppressWarnings("all")
    private byte xLow;
    @java.lang.SuppressWarnings("all")
    private byte yHigh;
    @java.lang.SuppressWarnings("all")
    private byte yLow;

    public SAMovePtr.SAMovePtrBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SAMovePtr.SAMovePtrBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public boolean isFlagsEnabled(SAMovePtrFlag... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (SAMovePtrFlag m : maskEnums) {
        if (!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public SAMovePtr.SAMovePtrBuilder flagsEnable(SAMovePtrFlag... maskEnums) {
      for (SAMovePtrFlag m : maskEnums) {
        flags((byte) m.enableFor(flags));
      }
      return this;
    }

    public SAMovePtr.SAMovePtrBuilder flagsDisable(SAMovePtrFlag... maskEnums) {
      for (SAMovePtrFlag m : maskEnums) {
        flags((byte) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    SAMovePtrBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SAMovePtr.SAMovePtrBuilder flags(final byte flags) {
      this.flags = flags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SAMovePtr.SAMovePtrBuilder xHigh(final byte xHigh) {
      this.xHigh = xHigh;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SAMovePtr.SAMovePtrBuilder xLow(final byte xLow) {
      this.xLow = xLow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SAMovePtr.SAMovePtrBuilder yHigh(final byte yHigh) {
      this.yHigh = yHigh;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SAMovePtr.SAMovePtrBuilder yLow(final byte yLow) {
      this.yLow = yLow;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SAMovePtr build() {
      return new SAMovePtr(this.type, this.flags, this.xHigh, this.xLow, this.yHigh, this.yLow);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SAMovePtr.SAMovePtrBuilder(type=" + this.type + ", flags=" + this.flags + ", xHigh=" + this.xHigh + ", xLow=" + this.xLow + ", yHigh=" + this.yHigh + ", yLow=" + this.yLow + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SAMovePtr(final byte type, final byte flags, final byte xHigh, final byte xLow, final byte yHigh, final byte yLow) {
    this.type = type;
    this.flags = flags;
    this.xHigh = xHigh;
    this.xLow = xLow;
    this.yHigh = yHigh;
    this.yLow = yLow;
  }

  @java.lang.SuppressWarnings("all")
  public static SAMovePtr.SAMovePtrBuilder builder() {
    return new SAMovePtr.SAMovePtrBuilder();
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
  public byte getXHigh() {
    return this.xHigh;
  }

  @java.lang.SuppressWarnings("all")
  public byte getXLow() {
    return this.xLow;
  }

  @java.lang.SuppressWarnings("all")
  public byte getYHigh() {
    return this.yHigh;
  }

  @java.lang.SuppressWarnings("all")
  public byte getYLow() {
    return this.yLow;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SAMovePtr)) return false;
    final SAMovePtr other = (SAMovePtr) o;
    if (this.getType() != other.getType()) return false;
    if (this.getFlags() != other.getFlags()) return false;
    if (this.getXHigh() != other.getXHigh()) return false;
    if (this.getXLow() != other.getXLow()) return false;
    if (this.getYHigh() != other.getYHigh()) return false;
    if (this.getYLow() != other.getYLow()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getFlags();
    result = result * PRIME + this.getXHigh();
    result = result * PRIME + this.getXLow();
    result = result * PRIME + this.getYHigh();
    result = result * PRIME + this.getYLow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SAMovePtr(type=" + this.getType() + ", flags=" + this.getFlags() + ", xHigh=" + this.getXHigh() + ", xLow=" + this.getXLow() + ", yHigh=" + this.getYHigh() + ", yLow=" + this.getYLow() + ")";
  }
}
