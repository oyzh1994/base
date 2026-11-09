package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class ResourceSizeSpec implements XStruct {
  public static final String PLUGIN_NAME = "res";
  private final ResourceIdSpec spec;
  private final int bytes;
  private final int refCount;
  private final int useCount;

  public static ResourceSizeSpec readResourceSizeSpec(X11Input in) throws IOException {
    ResourceSizeSpec.ResourceSizeSpecBuilder javaBuilder = ResourceSizeSpec.builder();
    ResourceIdSpec spec = ResourceIdSpec.readResourceIdSpec(in);
    int bytes = in.readCard32();
    int refCount = in.readCard32();
    int useCount = in.readCard32();
    javaBuilder.spec(spec);
    javaBuilder.bytes(bytes);
    javaBuilder.refCount(refCount);
    javaBuilder.useCount(useCount);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    spec.write(out);
    out.writeCard32(bytes);
    out.writeCard32(refCount);
    out.writeCard32(useCount);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ResourceSizeSpecBuilder {
    @java.lang.SuppressWarnings("all")
    private ResourceIdSpec spec;
    @java.lang.SuppressWarnings("all")
    private int bytes;
    @java.lang.SuppressWarnings("all")
    private int refCount;
    @java.lang.SuppressWarnings("all")
    private int useCount;

    public int getSize() {
      return 20;
    }

    @java.lang.SuppressWarnings("all")
    ResourceSizeSpecBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ResourceSizeSpec.ResourceSizeSpecBuilder spec(final ResourceIdSpec spec) {
      if (spec == null) {
        throw new java.lang.NullPointerException("spec is marked non-null but is null");
      }
      this.spec = spec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ResourceSizeSpec.ResourceSizeSpecBuilder bytes(final int bytes) {
      this.bytes = bytes;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ResourceSizeSpec.ResourceSizeSpecBuilder refCount(final int refCount) {
      this.refCount = refCount;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ResourceSizeSpec.ResourceSizeSpecBuilder useCount(final int useCount) {
      this.useCount = useCount;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ResourceSizeSpec build() {
      return new ResourceSizeSpec(this.spec, this.bytes, this.refCount, this.useCount);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ResourceSizeSpec.ResourceSizeSpecBuilder(spec=" + this.spec + ", bytes=" + this.bytes + ", refCount=" + this.refCount + ", useCount=" + this.useCount + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ResourceSizeSpec(final ResourceIdSpec spec, final int bytes, final int refCount, final int useCount) {
    if (spec == null) {
      throw new java.lang.NullPointerException("spec is marked non-null but is null");
    }
    this.spec = spec;
    this.bytes = bytes;
    this.refCount = refCount;
    this.useCount = useCount;
  }

  @java.lang.SuppressWarnings("all")
  public static ResourceSizeSpec.ResourceSizeSpecBuilder builder() {
    return new ResourceSizeSpec.ResourceSizeSpecBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public ResourceIdSpec getSpec() {
    return this.spec;
  }

  @java.lang.SuppressWarnings("all")
  public int getBytes() {
    return this.bytes;
  }

  @java.lang.SuppressWarnings("all")
  public int getRefCount() {
    return this.refCount;
  }

  @java.lang.SuppressWarnings("all")
  public int getUseCount() {
    return this.useCount;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ResourceSizeSpec)) return false;
    final ResourceSizeSpec other = (ResourceSizeSpec) o;
    if (this.getBytes() != other.getBytes()) return false;
    if (this.getRefCount() != other.getRefCount()) return false;
    if (this.getUseCount() != other.getUseCount()) return false;
    final java.lang.Object this$spec = this.getSpec();
    final java.lang.Object other$spec = other.getSpec();
    if (this$spec == null ? other$spec != null : !this$spec.equals(other$spec)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getBytes();
    result = result * PRIME + this.getRefCount();
    result = result * PRIME + this.getUseCount();
    final java.lang.Object $spec = this.getSpec();
    result = result * PRIME + ($spec == null ? 43 : $spec.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ResourceSizeSpec(spec=" + this.getSpec() + ", bytes=" + this.getBytes() + ", refCount=" + this.getRefCount() + ", useCount=" + this.getUseCount() + ")";
  }
}
