package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class SASetGroup implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;
  private final byte flags;
  private final byte group;

  public static SASetGroup readSASetGroup(X11Input in) throws IOException {
    SASetGroup.SASetGroupBuilder javaBuilder = SASetGroup.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte group = in.readInt8();
    byte[] pad3 = in.readPad(5);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.group(group);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writeInt8(group);
    out.writePad(5);
  }

  public boolean isFlagsEnabled(Sa... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Sa m : maskEnums) {
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


  public static class SASetGroupBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte flags;
    @java.lang.SuppressWarnings("all")
    private byte group;

    public SASetGroup.SASetGroupBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SASetGroup.SASetGroupBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public boolean isFlagsEnabled(Sa... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Sa m : maskEnums) {
        if (!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public SASetGroup.SASetGroupBuilder flagsEnable(Sa... maskEnums) {
      for (Sa m : maskEnums) {
        flags((byte) m.enableFor(flags));
      }
      return this;
    }

    public SASetGroup.SASetGroupBuilder flagsDisable(Sa... maskEnums) {
      for (Sa m : maskEnums) {
        flags((byte) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    SASetGroupBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SASetGroup.SASetGroupBuilder flags(final byte flags) {
      this.flags = flags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SASetGroup.SASetGroupBuilder group(final byte group) {
      this.group = group;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SASetGroup build() {
      return new SASetGroup(this.type, this.flags, this.group);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SASetGroup.SASetGroupBuilder(type=" + this.type + ", flags=" + this.flags + ", group=" + this.group + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SASetGroup(final byte type, final byte flags, final byte group) {
    this.type = type;
    this.flags = flags;
    this.group = group;
  }

  @java.lang.SuppressWarnings("all")
  public static SASetGroup.SASetGroupBuilder builder() {
    return new SASetGroup.SASetGroupBuilder();
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
  public byte getGroup() {
    return this.group;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SASetGroup)) return false;
    final SASetGroup other = (SASetGroup) o;
    if (this.getType() != other.getType()) return false;
    if (this.getFlags() != other.getFlags()) return false;
    if (this.getGroup() != other.getGroup()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getFlags();
    result = result * PRIME + this.getGroup();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SASetGroup(type=" + this.getType() + ", flags=" + this.getFlags() + ", group=" + this.getGroup() + ")";
  }
}
