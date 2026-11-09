package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class ModeInfo implements XStruct {
  public static final String PLUGIN_NAME = "randr";
  private final int id;
  private final short width;
  private final short height;
  private final int dotClock;
  private final short hsyncStart;
  private final short hsyncEnd;
  private final short htotal;
  private final short hskew;
  private final short vsyncStart;
  private final short vsyncEnd;
  private final short vtotal;
  private final short nameLen;
  private final int modeFlags;

  public static ModeInfo readModeInfo(X11Input in) throws IOException {
    ModeInfo.ModeInfoBuilder javaBuilder = ModeInfo.builder();
    int id = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    int dotClock = in.readCard32();
    short hsyncStart = in.readCard16();
    short hsyncEnd = in.readCard16();
    short htotal = in.readCard16();
    short hskew = in.readCard16();
    short vsyncStart = in.readCard16();
    short vsyncEnd = in.readCard16();
    short vtotal = in.readCard16();
    short nameLen = in.readCard16();
    int modeFlags = in.readCard32();
    javaBuilder.id(id);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.dotClock(dotClock);
    javaBuilder.hsyncStart(hsyncStart);
    javaBuilder.hsyncEnd(hsyncEnd);
    javaBuilder.htotal(htotal);
    javaBuilder.hskew(hskew);
    javaBuilder.vsyncStart(vsyncStart);
    javaBuilder.vsyncEnd(vsyncEnd);
    javaBuilder.vtotal(vtotal);
    javaBuilder.nameLen(nameLen);
    javaBuilder.modeFlags(modeFlags);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(id);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard32(dotClock);
    out.writeCard16(hsyncStart);
    out.writeCard16(hsyncEnd);
    out.writeCard16(htotal);
    out.writeCard16(hskew);
    out.writeCard16(vsyncStart);
    out.writeCard16(vsyncEnd);
    out.writeCard16(vtotal);
    out.writeCard16(nameLen);
    out.writeCard32(modeFlags);
  }

  public boolean isModeFlagsEnabled(ModeFlag... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModeFlag m : maskEnums) {
      if (!m.isEnabled(modeFlags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ModeInfoBuilder {
    @java.lang.SuppressWarnings("all")
    private int id;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;
    @java.lang.SuppressWarnings("all")
    private int dotClock;
    @java.lang.SuppressWarnings("all")
    private short hsyncStart;
    @java.lang.SuppressWarnings("all")
    private short hsyncEnd;
    @java.lang.SuppressWarnings("all")
    private short htotal;
    @java.lang.SuppressWarnings("all")
    private short hskew;
    @java.lang.SuppressWarnings("all")
    private short vsyncStart;
    @java.lang.SuppressWarnings("all")
    private short vsyncEnd;
    @java.lang.SuppressWarnings("all")
    private short vtotal;
    @java.lang.SuppressWarnings("all")
    private short nameLen;
    @java.lang.SuppressWarnings("all")
    private int modeFlags;

    public boolean isModeFlagsEnabled(ModeFlag... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModeFlag m : maskEnums) {
        if (!m.isEnabled(modeFlags)) {
          return false;
        }
      }
      return true;
    }

    public ModeInfo.ModeInfoBuilder modeFlagsEnable(ModeFlag... maskEnums) {
      for (ModeFlag m : maskEnums) {
        modeFlags((int) m.enableFor(modeFlags));
      }
      return this;
    }

    public ModeInfo.ModeInfoBuilder modeFlagsDisable(ModeFlag... maskEnums) {
      for (ModeFlag m : maskEnums) {
        modeFlags((int) m.disableFor(modeFlags));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    ModeInfoBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ModeInfo.ModeInfoBuilder id(final int id) {
      this.id = id;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ModeInfo.ModeInfoBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ModeInfo.ModeInfoBuilder height(final short height) {
      this.height = height;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ModeInfo.ModeInfoBuilder dotClock(final int dotClock) {
      this.dotClock = dotClock;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ModeInfo.ModeInfoBuilder hsyncStart(final short hsyncStart) {
      this.hsyncStart = hsyncStart;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ModeInfo.ModeInfoBuilder hsyncEnd(final short hsyncEnd) {
      this.hsyncEnd = hsyncEnd;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ModeInfo.ModeInfoBuilder htotal(final short htotal) {
      this.htotal = htotal;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ModeInfo.ModeInfoBuilder hskew(final short hskew) {
      this.hskew = hskew;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ModeInfo.ModeInfoBuilder vsyncStart(final short vsyncStart) {
      this.vsyncStart = vsyncStart;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ModeInfo.ModeInfoBuilder vsyncEnd(final short vsyncEnd) {
      this.vsyncEnd = vsyncEnd;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ModeInfo.ModeInfoBuilder vtotal(final short vtotal) {
      this.vtotal = vtotal;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ModeInfo.ModeInfoBuilder nameLen(final short nameLen) {
      this.nameLen = nameLen;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ModeInfo.ModeInfoBuilder modeFlags(final int modeFlags) {
      this.modeFlags = modeFlags;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ModeInfo build() {
      return new ModeInfo(this.id, this.width, this.height, this.dotClock, this.hsyncStart, this.hsyncEnd, this.htotal, this.hskew, this.vsyncStart, this.vsyncEnd, this.vtotal, this.nameLen, this.modeFlags);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ModeInfo.ModeInfoBuilder(id=" + this.id + ", width=" + this.width + ", height=" + this.height + ", dotClock=" + this.dotClock + ", hsyncStart=" + this.hsyncStart + ", hsyncEnd=" + this.hsyncEnd + ", htotal=" + this.htotal + ", hskew=" + this.hskew + ", vsyncStart=" + this.vsyncStart + ", vsyncEnd=" + this.vsyncEnd + ", vtotal=" + this.vtotal + ", nameLen=" + this.nameLen + ", modeFlags=" + this.modeFlags + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ModeInfo(final int id, final short width, final short height, final int dotClock, final short hsyncStart, final short hsyncEnd, final short htotal, final short hskew, final short vsyncStart, final short vsyncEnd, final short vtotal, final short nameLen, final int modeFlags) {
    this.id = id;
    this.width = width;
    this.height = height;
    this.dotClock = dotClock;
    this.hsyncStart = hsyncStart;
    this.hsyncEnd = hsyncEnd;
    this.htotal = htotal;
    this.hskew = hskew;
    this.vsyncStart = vsyncStart;
    this.vsyncEnd = vsyncEnd;
    this.vtotal = vtotal;
    this.nameLen = nameLen;
    this.modeFlags = modeFlags;
  }

  @java.lang.SuppressWarnings("all")
  public static ModeInfo.ModeInfoBuilder builder() {
    return new ModeInfo.ModeInfoBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getId() {
    return this.id;
  }

  @java.lang.SuppressWarnings("all")
  public short getWidth() {
    return this.width;
  }

  @java.lang.SuppressWarnings("all")
  public short getHeight() {
    return this.height;
  }

  @java.lang.SuppressWarnings("all")
  public int getDotClock() {
    return this.dotClock;
  }

  @java.lang.SuppressWarnings("all")
  public short getHsyncStart() {
    return this.hsyncStart;
  }

  @java.lang.SuppressWarnings("all")
  public short getHsyncEnd() {
    return this.hsyncEnd;
  }

  @java.lang.SuppressWarnings("all")
  public short getHtotal() {
    return this.htotal;
  }

  @java.lang.SuppressWarnings("all")
  public short getHskew() {
    return this.hskew;
  }

  @java.lang.SuppressWarnings("all")
  public short getVsyncStart() {
    return this.vsyncStart;
  }

  @java.lang.SuppressWarnings("all")
  public short getVsyncEnd() {
    return this.vsyncEnd;
  }

  @java.lang.SuppressWarnings("all")
  public short getVtotal() {
    return this.vtotal;
  }

  @java.lang.SuppressWarnings("all")
  public short getNameLen() {
    return this.nameLen;
  }

  @java.lang.SuppressWarnings("all")
  public int getModeFlags() {
    return this.modeFlags;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ModeInfo)) return false;
    final ModeInfo other = (ModeInfo) o;
    if (this.getId() != other.getId()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getDotClock() != other.getDotClock()) return false;
    if (this.getHsyncStart() != other.getHsyncStart()) return false;
    if (this.getHsyncEnd() != other.getHsyncEnd()) return false;
    if (this.getHtotal() != other.getHtotal()) return false;
    if (this.getHskew() != other.getHskew()) return false;
    if (this.getVsyncStart() != other.getVsyncStart()) return false;
    if (this.getVsyncEnd() != other.getVsyncEnd()) return false;
    if (this.getVtotal() != other.getVtotal()) return false;
    if (this.getNameLen() != other.getNameLen()) return false;
    if (this.getModeFlags() != other.getModeFlags()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getId();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getDotClock();
    result = result * PRIME + this.getHsyncStart();
    result = result * PRIME + this.getHsyncEnd();
    result = result * PRIME + this.getHtotal();
    result = result * PRIME + this.getHskew();
    result = result * PRIME + this.getVsyncStart();
    result = result * PRIME + this.getVsyncEnd();
    result = result * PRIME + this.getVtotal();
    result = result * PRIME + this.getNameLen();
    result = result * PRIME + this.getModeFlags();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ModeInfo(id=" + this.getId() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", dotClock=" + this.getDotClock() + ", hsyncStart=" + this.getHsyncStart() + ", hsyncEnd=" + this.getHsyncEnd() + ", htotal=" + this.getHtotal() + ", hskew=" + this.getHskew() + ", vsyncStart=" + this.getVsyncStart() + ", vsyncEnd=" + this.getVsyncEnd() + ", vtotal=" + this.getVtotal() + ", nameLen=" + this.getNameLen() + ", modeFlags=" + this.getModeFlags() + ")";
  }
}
