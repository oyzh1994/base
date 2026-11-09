package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.render.Transform;
import java.io.IOException;

public final class GetCrtcTransformReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final short sequenceNumber;
  private final Transform pendingTransform;
  private final boolean hasTransforms;
  private final Transform currentTransform;
  private final byte[] pendingFilterName;
  private final int[] pendingParams;
  private final byte[] currentFilterName;
  private final int[] currentParams;

  public static GetCrtcTransformReply readGetCrtcTransformReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetCrtcTransformReply.GetCrtcTransformReplyBuilder javaBuilder = GetCrtcTransformReply.builder();
    int length = in.readCard32();
    Transform pendingTransform = Transform.readTransform(in);
    boolean hasTransforms = in.readBool();
    byte[] pad6 = in.readPad(3);
    Transform currentTransform = Transform.readTransform(in);
    byte[] pad8 = in.readPad(4);
    short pendingLen = in.readCard16();
    short pendingNparams = in.readCard16();
    short currentLen = in.readCard16();
    short currentNparams = in.readCard16();
    byte[] pendingFilterName = in.readChar(Short.toUnsignedInt(pendingLen));
    in.readPadAlign(Short.toUnsignedInt(pendingLen));
    int[] pendingParams = in.readInt32(Short.toUnsignedInt(pendingNparams));
    byte[] currentFilterName = in.readChar(Short.toUnsignedInt(currentLen));
    in.readPadAlign(Short.toUnsignedInt(currentLen));
    int[] currentParams = in.readInt32(Short.toUnsignedInt(currentNparams));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.pendingTransform(pendingTransform);
    javaBuilder.hasTransforms(hasTransforms);
    javaBuilder.currentTransform(currentTransform);
    javaBuilder.pendingFilterName(pendingFilterName);
    javaBuilder.pendingParams(pendingParams);
    javaBuilder.currentFilterName(currentFilterName);
    javaBuilder.currentParams(currentParams);
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    pendingTransform.write(out);
    out.writeBool(hasTransforms);
    out.writePad(3);
    currentTransform.write(out);
    out.writePad(4);
    short pendingLen = (short) pendingFilterName.length;
    out.writeCard16(pendingLen);
    short pendingNparams = (short) pendingParams.length;
    out.writeCard16(pendingNparams);
    short currentLen = (short) currentFilterName.length;
    out.writeCard16(currentLen);
    short currentNparams = (short) currentParams.length;
    out.writeCard16(currentNparams);
    out.writeChar(pendingFilterName);
    out.writePadAlign(Short.toUnsignedInt(pendingLen));
    out.writeInt32(pendingParams);
    out.writeChar(currentFilterName);
    out.writePadAlign(Short.toUnsignedInt(currentLen));
    out.writeInt32(currentParams);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 96 + 1 * pendingFilterName.length + XObject.getSizeForPadAlign(4, 1 * pendingFilterName.length) + 4 * pendingParams.length + 1 * currentFilterName.length + XObject.getSizeForPadAlign(4, 1 * currentFilterName.length) + 4 * currentParams.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetCrtcTransformReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private Transform pendingTransform;
    @java.lang.SuppressWarnings("all")
    private boolean hasTransforms;
    @java.lang.SuppressWarnings("all")
    private Transform currentTransform;
    @java.lang.SuppressWarnings("all")
    private byte[] pendingFilterName;
    @java.lang.SuppressWarnings("all")
    private int[] pendingParams;
    @java.lang.SuppressWarnings("all")
    private byte[] currentFilterName;
    @java.lang.SuppressWarnings("all")
    private int[] currentParams;

    public int getSize() {
      return 96 + 1 * pendingFilterName.length + XObject.getSizeForPadAlign(4, 1 * pendingFilterName.length) + 4 * pendingParams.length + 1 * currentFilterName.length + XObject.getSizeForPadAlign(4, 1 * currentFilterName.length) + 4 * currentParams.length;
    }

    @java.lang.SuppressWarnings("all")
    GetCrtcTransformReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcTransformReply.GetCrtcTransformReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcTransformReply.GetCrtcTransformReplyBuilder pendingTransform(final Transform pendingTransform) {
      if (pendingTransform == null) {
        throw new java.lang.NullPointerException("pendingTransform is marked non-null but is null");
      }
      this.pendingTransform = pendingTransform;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcTransformReply.GetCrtcTransformReplyBuilder hasTransforms(final boolean hasTransforms) {
      this.hasTransforms = hasTransforms;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcTransformReply.GetCrtcTransformReplyBuilder currentTransform(final Transform currentTransform) {
      if (currentTransform == null) {
        throw new java.lang.NullPointerException("currentTransform is marked non-null but is null");
      }
      this.currentTransform = currentTransform;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcTransformReply.GetCrtcTransformReplyBuilder pendingFilterName(final byte[] pendingFilterName) {
      if (pendingFilterName == null) {
        throw new java.lang.NullPointerException("pendingFilterName is marked non-null but is null");
      }
      this.pendingFilterName = pendingFilterName;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcTransformReply.GetCrtcTransformReplyBuilder pendingParams(final int[] pendingParams) {
      if (pendingParams == null) {
        throw new java.lang.NullPointerException("pendingParams is marked non-null but is null");
      }
      this.pendingParams = pendingParams;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcTransformReply.GetCrtcTransformReplyBuilder currentFilterName(final byte[] currentFilterName) {
      if (currentFilterName == null) {
        throw new java.lang.NullPointerException("currentFilterName is marked non-null but is null");
      }
      this.currentFilterName = currentFilterName;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcTransformReply.GetCrtcTransformReplyBuilder currentParams(final int[] currentParams) {
      if (currentParams == null) {
        throw new java.lang.NullPointerException("currentParams is marked non-null but is null");
      }
      this.currentParams = currentParams;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetCrtcTransformReply build() {
      return new GetCrtcTransformReply(this.sequenceNumber, this.pendingTransform, this.hasTransforms, this.currentTransform, this.pendingFilterName, this.pendingParams, this.currentFilterName, this.currentParams);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetCrtcTransformReply.GetCrtcTransformReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", pendingTransform=" + this.pendingTransform + ", hasTransforms=" + this.hasTransforms + ", currentTransform=" + this.currentTransform + ", pendingFilterName=" + java.util.Arrays.toString(this.pendingFilterName) + ", pendingParams=" + java.util.Arrays.toString(this.pendingParams) + ", currentFilterName=" + java.util.Arrays.toString(this.currentFilterName) + ", currentParams=" + java.util.Arrays.toString(this.currentParams) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetCrtcTransformReply(final short sequenceNumber, final Transform pendingTransform, final boolean hasTransforms, final Transform currentTransform, final byte[] pendingFilterName, final int[] pendingParams, final byte[] currentFilterName, final int[] currentParams) {
    if (pendingTransform == null) {
      throw new java.lang.NullPointerException("pendingTransform is marked non-null but is null");
    }
    if (currentTransform == null) {
      throw new java.lang.NullPointerException("currentTransform is marked non-null but is null");
    }
    if (pendingFilterName == null) {
      throw new java.lang.NullPointerException("pendingFilterName is marked non-null but is null");
    }
    if (pendingParams == null) {
      throw new java.lang.NullPointerException("pendingParams is marked non-null but is null");
    }
    if (currentFilterName == null) {
      throw new java.lang.NullPointerException("currentFilterName is marked non-null but is null");
    }
    if (currentParams == null) {
      throw new java.lang.NullPointerException("currentParams is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.pendingTransform = pendingTransform;
    this.hasTransforms = hasTransforms;
    this.currentTransform = currentTransform;
    this.pendingFilterName = pendingFilterName;
    this.pendingParams = pendingParams;
    this.currentFilterName = currentFilterName;
    this.currentParams = currentParams;
  }

  @java.lang.SuppressWarnings("all")
  public static GetCrtcTransformReply.GetCrtcTransformReplyBuilder builder() {
    return new GetCrtcTransformReply.GetCrtcTransformReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public Transform getPendingTransform() {
    return this.pendingTransform;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isHasTransforms() {
    return this.hasTransforms;
  }

  @java.lang.SuppressWarnings("all")
  public Transform getCurrentTransform() {
    return this.currentTransform;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getPendingFilterName() {
    return this.pendingFilterName;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getPendingParams() {
    return this.pendingParams;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getCurrentFilterName() {
    return this.currentFilterName;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getCurrentParams() {
    return this.currentParams;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetCrtcTransformReply)) return false;
    final GetCrtcTransformReply other = (GetCrtcTransformReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.isHasTransforms() != other.isHasTransforms()) return false;
    final java.lang.Object this$pendingTransform = this.getPendingTransform();
    final java.lang.Object other$pendingTransform = other.getPendingTransform();
    if (this$pendingTransform == null ? other$pendingTransform != null : !this$pendingTransform.equals(other$pendingTransform)) return false;
    final java.lang.Object this$currentTransform = this.getCurrentTransform();
    final java.lang.Object other$currentTransform = other.getCurrentTransform();
    if (this$currentTransform == null ? other$currentTransform != null : !this$currentTransform.equals(other$currentTransform)) return false;
    if (!java.util.Arrays.equals(this.getPendingFilterName(), other.getPendingFilterName())) return false;
    if (!java.util.Arrays.equals(this.getPendingParams(), other.getPendingParams())) return false;
    if (!java.util.Arrays.equals(this.getCurrentFilterName(), other.getCurrentFilterName())) return false;
    if (!java.util.Arrays.equals(this.getCurrentParams(), other.getCurrentParams())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + (this.isHasTransforms() ? 79 : 97);
    final java.lang.Object $pendingTransform = this.getPendingTransform();
    result = result * PRIME + ($pendingTransform == null ? 43 : $pendingTransform.hashCode());
    final java.lang.Object $currentTransform = this.getCurrentTransform();
    result = result * PRIME + ($currentTransform == null ? 43 : $currentTransform.hashCode());
    result = result * PRIME + java.util.Arrays.hashCode(this.getPendingFilterName());
    result = result * PRIME + java.util.Arrays.hashCode(this.getPendingParams());
    result = result * PRIME + java.util.Arrays.hashCode(this.getCurrentFilterName());
    result = result * PRIME + java.util.Arrays.hashCode(this.getCurrentParams());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetCrtcTransformReply(sequenceNumber=" + this.getSequenceNumber() + ", pendingTransform=" + this.getPendingTransform() + ", hasTransforms=" + this.isHasTransforms() + ", currentTransform=" + this.getCurrentTransform() + ", pendingFilterName=" + java.util.Arrays.toString(this.getPendingFilterName()) + ", pendingParams=" + java.util.Arrays.toString(this.getPendingParams()) + ", currentFilterName=" + java.util.Arrays.toString(this.getCurrentFilterName()) + ", currentParams=" + java.util.Arrays.toString(this.getCurrentParams()) + ")";
  }
}
