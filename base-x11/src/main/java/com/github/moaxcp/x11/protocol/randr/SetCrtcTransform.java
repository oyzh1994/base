package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.render.Transform;
import java.io.IOException;

public final class SetCrtcTransform implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 26;
  private final int crtc;
  private final Transform transform;
  private final byte[] filterName;
  private final int[] filterParams;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetCrtcTransform readSetCrtcTransform(X11Input in) throws IOException {
    SetCrtcTransform.SetCrtcTransformBuilder javaBuilder = SetCrtcTransform.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int crtc = in.readCard32();
    javaStart += 4;
    Transform transform = Transform.readTransform(in);
    javaStart += transform.getSize();
    short filterLen = in.readCard16();
    javaStart += 2;
    byte[] pad6 = in.readPad(2);
    javaStart += 2;
    byte[] filterName = in.readChar(Short.toUnsignedInt(filterLen));
    javaStart += 1 * filterName.length;
    in.readPadAlign(Short.toUnsignedInt(filterLen));
    javaStart += XObject.getSizeForPadAlign(4, 1 * filterName.length);
    int[] filterParams = in.readInt32(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.crtc(crtc);
    javaBuilder.transform(transform);
    javaBuilder.filterName(filterName);
    javaBuilder.filterParams(filterParams);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(crtc);
    transform.write(out);
    short filterLen = (short) filterName.length;
    out.writeCard16(filterLen);
    out.writePad(2);
    out.writeChar(filterName);
    out.writePadAlign(Short.toUnsignedInt(filterLen));
    out.writeInt32(filterParams);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 48 + 1 * filterName.length + XObject.getSizeForPadAlign(4, 1 * filterName.length) + 4 * filterParams.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetCrtcTransformBuilder {
    @java.lang.SuppressWarnings("all")
    private int crtc;
    @java.lang.SuppressWarnings("all")
    private Transform transform;
    @java.lang.SuppressWarnings("all")
    private byte[] filterName;
    @java.lang.SuppressWarnings("all")
    private int[] filterParams;

    public int getSize() {
      return 48 + 1 * filterName.length + XObject.getSizeForPadAlign(4, 1 * filterName.length) + 4 * filterParams.length;
    }

    @java.lang.SuppressWarnings("all")
    SetCrtcTransformBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCrtcTransform.SetCrtcTransformBuilder crtc(final int crtc) {
      this.crtc = crtc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCrtcTransform.SetCrtcTransformBuilder transform(final Transform transform) {
      if (transform == null) {
        throw new java.lang.NullPointerException("transform is marked non-null but is null");
      }
      this.transform = transform;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCrtcTransform.SetCrtcTransformBuilder filterName(final byte[] filterName) {
      if (filterName == null) {
        throw new java.lang.NullPointerException("filterName is marked non-null but is null");
      }
      this.filterName = filterName;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCrtcTransform.SetCrtcTransformBuilder filterParams(final int[] filterParams) {
      if (filterParams == null) {
        throw new java.lang.NullPointerException("filterParams is marked non-null but is null");
      }
      this.filterParams = filterParams;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetCrtcTransform build() {
      return new SetCrtcTransform(this.crtc, this.transform, this.filterName, this.filterParams);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetCrtcTransform.SetCrtcTransformBuilder(crtc=" + this.crtc + ", transform=" + this.transform + ", filterName=" + java.util.Arrays.toString(this.filterName) + ", filterParams=" + java.util.Arrays.toString(this.filterParams) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetCrtcTransform(final int crtc, final Transform transform, final byte[] filterName, final int[] filterParams) {
    if (transform == null) {
      throw new java.lang.NullPointerException("transform is marked non-null but is null");
    }
    if (filterName == null) {
      throw new java.lang.NullPointerException("filterName is marked non-null but is null");
    }
    if (filterParams == null) {
      throw new java.lang.NullPointerException("filterParams is marked non-null but is null");
    }
    this.crtc = crtc;
    this.transform = transform;
    this.filterName = filterName;
    this.filterParams = filterParams;
  }

  @java.lang.SuppressWarnings("all")
  public static SetCrtcTransform.SetCrtcTransformBuilder builder() {
    return new SetCrtcTransform.SetCrtcTransformBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCrtc() {
    return this.crtc;
  }

  @java.lang.SuppressWarnings("all")
  public Transform getTransform() {
    return this.transform;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getFilterName() {
    return this.filterName;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getFilterParams() {
    return this.filterParams;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetCrtcTransform)) return false;
    final SetCrtcTransform other = (SetCrtcTransform) o;
    if (this.getCrtc() != other.getCrtc()) return false;
    final java.lang.Object this$transform = this.getTransform();
    final java.lang.Object other$transform = other.getTransform();
    if (this$transform == null ? other$transform != null : !this$transform.equals(other$transform)) return false;
    if (!java.util.Arrays.equals(this.getFilterName(), other.getFilterName())) return false;
    if (!java.util.Arrays.equals(this.getFilterParams(), other.getFilterParams())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCrtc();
    final java.lang.Object $transform = this.getTransform();
    result = result * PRIME + ($transform == null ? 43 : $transform.hashCode());
    result = result * PRIME + java.util.Arrays.hashCode(this.getFilterName());
    result = result * PRIME + java.util.Arrays.hashCode(this.getFilterParams());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetCrtcTransform(crtc=" + this.getCrtc() + ", transform=" + this.getTransform() + ", filterName=" + java.util.Arrays.toString(this.getFilterName()) + ", filterParams=" + java.util.Arrays.toString(this.getFilterParams()) + ")";
  }
}
