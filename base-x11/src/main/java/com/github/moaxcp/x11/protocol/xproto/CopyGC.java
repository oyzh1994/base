package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CopyGC implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 57;
  private final int srcGc;
  private final int dstGc;
  private final int valueMask;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CopyGC readCopyGC(X11Input in) throws IOException {
    CopyGC.CopyGCBuilder javaBuilder = CopyGC.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int srcGc = in.readCard32();
    int dstGc = in.readCard32();
    int valueMask = in.readCard32();
    javaBuilder.srcGc(srcGc);
    javaBuilder.dstGc(dstGc);
    javaBuilder.valueMask(valueMask);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(srcGc);
    out.writeCard32(dstGc);
    out.writeCard32(valueMask);
  }

  public boolean isValueMaskEnabled(Gc... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Gc m : maskEnums) {
      if (!m.isEnabled(valueMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CopyGCBuilder {
    @java.lang.SuppressWarnings("all")
    private int srcGc;
    @java.lang.SuppressWarnings("all")
    private int dstGc;
    @java.lang.SuppressWarnings("all")
    private int valueMask;

    private CopyGC.CopyGCBuilder valueMask(int valueMask) {
      this.valueMask = valueMask;
      return this;
    }

    public boolean isValueMaskEnabled(Gc... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Gc m : maskEnums) {
        if (!m.isEnabled(valueMask)) {
          return false;
        }
      }
      return true;
    }

    private CopyGC.CopyGCBuilder valueMaskEnable(Gc... maskEnums) {
      for (Gc m : maskEnums) {
        valueMask((int) m.enableFor(valueMask));
      }
      return this;
    }

    private CopyGC.CopyGCBuilder valueMaskDisable(Gc... maskEnums) {
      for (Gc m : maskEnums) {
        valueMask((int) m.disableFor(valueMask));
      }
      return this;
    }

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    CopyGCBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CopyGC.CopyGCBuilder srcGc(final int srcGc) {
      this.srcGc = srcGc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CopyGC.CopyGCBuilder dstGc(final int dstGc) {
      this.dstGc = dstGc;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CopyGC build() {
      return new CopyGC(this.srcGc, this.dstGc, this.valueMask);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CopyGC.CopyGCBuilder(srcGc=" + this.srcGc + ", dstGc=" + this.dstGc + ", valueMask=" + this.valueMask + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CopyGC(final int srcGc, final int dstGc, final int valueMask) {
    this.srcGc = srcGc;
    this.dstGc = dstGc;
    this.valueMask = valueMask;
  }

  @java.lang.SuppressWarnings("all")
  public static CopyGC.CopyGCBuilder builder() {
    return new CopyGC.CopyGCBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getSrcGc() {
    return this.srcGc;
  }

  @java.lang.SuppressWarnings("all")
  public int getDstGc() {
    return this.dstGc;
  }

  @java.lang.SuppressWarnings("all")
  public int getValueMask() {
    return this.valueMask;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CopyGC)) return false;
    final CopyGC other = (CopyGC) o;
    if (this.getSrcGc() != other.getSrcGc()) return false;
    if (this.getDstGc() != other.getDstGc()) return false;
    if (this.getValueMask() != other.getValueMask()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSrcGc();
    result = result * PRIME + this.getDstGc();
    result = result * PRIME + this.getValueMask();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CopyGC(srcGc=" + this.getSrcGc() + ", dstGc=" + this.getDstGc() + ", valueMask=" + this.getValueMask() + ")";
  }
}
