package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Type implements XStruct {
  public static final String PLUGIN_NAME = "res";
  private final int resourceType;
  private final int count;

  public static Type readType(X11Input in) throws IOException {
    Type.TypeBuilder javaBuilder = Type.builder();
    int resourceType = in.readCard32();
    int count = in.readCard32();
    javaBuilder.resourceType(resourceType);
    javaBuilder.count(count);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(resourceType);
    out.writeCard32(count);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class TypeBuilder {
    @java.lang.SuppressWarnings("all")
    private int resourceType;
    @java.lang.SuppressWarnings("all")
    private int count;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    TypeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Type.TypeBuilder resourceType(final int resourceType) {
      this.resourceType = resourceType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Type.TypeBuilder count(final int count) {
      this.count = count;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Type build() {
      return new Type(this.resourceType, this.count);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Type.TypeBuilder(resourceType=" + this.resourceType + ", count=" + this.count + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Type(final int resourceType, final int count) {
    this.resourceType = resourceType;
    this.count = count;
  }

  @java.lang.SuppressWarnings("all")
  public static Type.TypeBuilder builder() {
    return new Type.TypeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getResourceType() {
    return this.resourceType;
  }

  @java.lang.SuppressWarnings("all")
  public int getCount() {
    return this.count;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Type)) return false;
    final Type other = (Type) o;
    if (this.getResourceType() != other.getResourceType()) return false;
    if (this.getCount() != other.getCount()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getResourceType();
    result = result * PRIME + this.getCount();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Type(resourceType=" + this.getResourceType() + ", count=" + this.getCount() + ")";
  }
}
