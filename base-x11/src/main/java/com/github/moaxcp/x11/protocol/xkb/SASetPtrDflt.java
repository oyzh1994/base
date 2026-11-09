package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class SASetPtrDflt implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;
  private final byte flags;
  private final byte affect;
  private final byte value;

  public static SASetPtrDflt readSASetPtrDflt(X11Input in) throws IOException {
    SASetPtrDflt.SASetPtrDfltBuilder javaBuilder = SASetPtrDflt.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte affect = in.readCard8();
    byte value = in.readInt8();
    byte[] pad4 = in.readPad(4);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.affect(affect);
    javaBuilder.value(value);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writeCard8(affect);
    out.writeInt8(value);
    out.writePad(4);
  }

  public boolean isFlagsEnabled(SASetPtrDfltFlag... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (SASetPtrDfltFlag m : maskEnums) {
      if (!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectEnabled(SASetPtrDfltFlag... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (SASetPtrDfltFlag m : maskEnums) {
      if (!m.isEnabled(affect)) {
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


  public static class SASetPtrDfltBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte flags;
    @java.lang.SuppressWarnings("all")
    private byte affect;
    @java.lang.SuppressWarnings("all")
    private byte value;

    public SASetPtrDflt.SASetPtrDfltBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SASetPtrDflt.SASetPtrDfltBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public boolean isFlagsEnabled(SASetPtrDfltFlag... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (SASetPtrDfltFlag m : maskEnums) {
        if (!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public SASetPtrDflt.SASetPtrDfltBuilder flagsEnable(SASetPtrDfltFlag... maskEnums) {
      for (SASetPtrDfltFlag m : maskEnums) {
        flags((byte) m.enableFor(flags));
      }
      return this;
    }

    public SASetPtrDflt.SASetPtrDfltBuilder flagsDisable(SASetPtrDfltFlag... maskEnums) {
      for (SASetPtrDfltFlag m : maskEnums) {
        flags((byte) m.disableFor(flags));
      }
      return this;
    }

    public boolean isAffectEnabled(SASetPtrDfltFlag... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (SASetPtrDfltFlag m : maskEnums) {
        if (!m.isEnabled(affect)) {
          return false;
        }
      }
      return true;
    }

    public SASetPtrDflt.SASetPtrDfltBuilder affectEnable(SASetPtrDfltFlag... maskEnums) {
      for (SASetPtrDfltFlag m : maskEnums) {
        affect((byte) m.enableFor(affect));
      }
      return this;
    }

    public SASetPtrDflt.SASetPtrDfltBuilder affectDisable(SASetPtrDfltFlag... maskEnums) {
      for (SASetPtrDfltFlag m : maskEnums) {
        affect((byte) m.disableFor(affect));
      }
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    SASetPtrDfltBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SASetPtrDflt.SASetPtrDfltBuilder flags(final byte flags) {
      this.flags = flags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SASetPtrDflt.SASetPtrDfltBuilder affect(final byte affect) {
      this.affect = affect;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SASetPtrDflt.SASetPtrDfltBuilder value(final byte value) {
      this.value = value;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SASetPtrDflt build() {
      return new SASetPtrDflt(this.type, this.flags, this.affect, this.value);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SASetPtrDflt.SASetPtrDfltBuilder(type=" + this.type + ", flags=" + this.flags + ", affect=" + this.affect + ", value=" + this.value + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SASetPtrDflt(final byte type, final byte flags, final byte affect, final byte value) {
    this.type = type;
    this.flags = flags;
    this.affect = affect;
    this.value = value;
  }

  @java.lang.SuppressWarnings("all")
  public static SASetPtrDflt.SASetPtrDfltBuilder builder() {
    return new SASetPtrDflt.SASetPtrDfltBuilder();
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
  public byte getAffect() {
    return this.affect;
  }

  @java.lang.SuppressWarnings("all")
  public byte getValue() {
    return this.value;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SASetPtrDflt)) return false;
    final SASetPtrDflt other = (SASetPtrDflt) o;
    if (this.getType() != other.getType()) return false;
    if (this.getFlags() != other.getFlags()) return false;
    if (this.getAffect() != other.getAffect()) return false;
    if (this.getValue() != other.getValue()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getFlags();
    result = result * PRIME + this.getAffect();
    result = result * PRIME + this.getValue();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SASetPtrDflt(type=" + this.getType() + ", flags=" + this.getFlags() + ", affect=" + this.getAffect() + ", value=" + this.getValue() + ")";
  }
}
