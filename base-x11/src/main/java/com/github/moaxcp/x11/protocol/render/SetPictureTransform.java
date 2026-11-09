package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SetPictureTransform implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 28;
  private final int picture;
  private final Transform transform;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetPictureTransform readSetPictureTransform(X11Input in) throws IOException {
    SetPictureTransform.SetPictureTransformBuilder javaBuilder = SetPictureTransform.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int picture = in.readCard32();
    Transform transform = Transform.readTransform(in);
    javaBuilder.picture(picture);
    javaBuilder.transform(transform);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(picture);
    transform.write(out);
  }

  @Override
  public int getSize() {
    return 44;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetPictureTransformBuilder {
    @java.lang.SuppressWarnings("all")
    private int picture;
    @java.lang.SuppressWarnings("all")
    private Transform transform;

    public int getSize() {
      return 44;
    }

    @java.lang.SuppressWarnings("all")
    SetPictureTransformBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPictureTransform.SetPictureTransformBuilder picture(final int picture) {
      this.picture = picture;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPictureTransform.SetPictureTransformBuilder transform(final Transform transform) {
      if (transform == null) {
        throw new java.lang.NullPointerException("transform is marked non-null but is null");
      }
      this.transform = transform;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetPictureTransform build() {
      return new SetPictureTransform(this.picture, this.transform);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetPictureTransform.SetPictureTransformBuilder(picture=" + this.picture + ", transform=" + this.transform + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetPictureTransform(final int picture, final Transform transform) {
    if (transform == null) {
      throw new java.lang.NullPointerException("transform is marked non-null but is null");
    }
    this.picture = picture;
    this.transform = transform;
  }

  @java.lang.SuppressWarnings("all")
  public static SetPictureTransform.SetPictureTransformBuilder builder() {
    return new SetPictureTransform.SetPictureTransformBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getPicture() {
    return this.picture;
  }

  @java.lang.SuppressWarnings("all")
  public Transform getTransform() {
    return this.transform;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetPictureTransform)) return false;
    final SetPictureTransform other = (SetPictureTransform) o;
    if (this.getPicture() != other.getPicture()) return false;
    final java.lang.Object this$transform = this.getTransform();
    final java.lang.Object other$transform = other.getTransform();
    if (this$transform == null ? other$transform != null : !this$transform.equals(other$transform)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getPicture();
    final java.lang.Object $transform = this.getTransform();
    result = result * PRIME + ($transform == null ? 43 : $transform.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetPictureTransform(picture=" + this.getPicture() + ", transform=" + this.getTransform() + ")";
  }
}
