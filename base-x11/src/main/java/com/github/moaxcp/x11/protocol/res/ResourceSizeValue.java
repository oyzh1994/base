package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class ResourceSizeValue implements XStruct {
  public static final String PLUGIN_NAME = "res";
  private final ResourceSizeSpec size;
  private final ImmutableList<ResourceSizeSpec> crossReferences;

  public static ResourceSizeValue readResourceSizeValue(X11Input in) throws IOException {
    ResourceSizeValue.ResourceSizeValueBuilder javaBuilder = ResourceSizeValue.builder();
    ResourceSizeSpec size = ResourceSizeSpec.readResourceSizeSpec(in);
    int numCrossReferences = in.readCard32();
    MutableList<ResourceSizeSpec> crossReferences = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numCrossReferences)));
    for (int i = 0; i < Integer.toUnsignedLong(numCrossReferences); i++) {
      crossReferences.add(ResourceSizeSpec.readResourceSizeSpec(in));
    }
    javaBuilder.size(size);
    javaBuilder.crossReferences(crossReferences.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    size.write(out);
    int numCrossReferences = crossReferences.size();
    out.writeCard32(numCrossReferences);
    for (ResourceSizeSpec t : crossReferences) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 24 + XObject.sizeOf(crossReferences);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ResourceSizeValueBuilder {
    @java.lang.SuppressWarnings("all")
    private ResourceSizeSpec size;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<ResourceSizeSpec> crossReferences;

    public int getSize() {
      return 24 + XObject.sizeOf(crossReferences);
    }

    @java.lang.SuppressWarnings("all")
    ResourceSizeValueBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ResourceSizeValue.ResourceSizeValueBuilder size(final ResourceSizeSpec size) {
      if (size == null) {
        throw new java.lang.NullPointerException("size is marked non-null but is null");
      }
      this.size = size;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ResourceSizeValue.ResourceSizeValueBuilder crossReferences(final ImmutableList<ResourceSizeSpec> crossReferences) {
      if (crossReferences == null) {
        throw new java.lang.NullPointerException("crossReferences is marked non-null but is null");
      }
      this.crossReferences = crossReferences;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ResourceSizeValue build() {
      return new ResourceSizeValue(this.size, this.crossReferences);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ResourceSizeValue.ResourceSizeValueBuilder(size=" + this.size + ", crossReferences=" + this.crossReferences + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ResourceSizeValue(final ResourceSizeSpec size, final ImmutableList<ResourceSizeSpec> crossReferences) {
    if (size == null) {
      throw new java.lang.NullPointerException("size is marked non-null but is null");
    }
    if (crossReferences == null) {
      throw new java.lang.NullPointerException("crossReferences is marked non-null but is null");
    }
    this.size = size;
    this.crossReferences = crossReferences;
  }

  @java.lang.SuppressWarnings("all")
  public static ResourceSizeValue.ResourceSizeValueBuilder builder() {
    return new ResourceSizeValue.ResourceSizeValueBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<ResourceSizeSpec> getCrossReferences() {
    return this.crossReferences;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ResourceSizeValue)) return false;
    final ResourceSizeValue other = (ResourceSizeValue) o;
    if (this.getSize() != other.getSize()) return false;
    final java.lang.Object this$crossReferences = this.getCrossReferences();
    final java.lang.Object other$crossReferences = other.getCrossReferences();
    if (this$crossReferences == null ? other$crossReferences != null : !this$crossReferences.equals(other$crossReferences)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSize();
    final java.lang.Object $crossReferences = this.getCrossReferences();
    result = result * PRIME + ($crossReferences == null ? 43 : $crossReferences.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ResourceSizeValue(size=" + this.getSize() + ", crossReferences=" + this.getCrossReferences() + ")";
  }
}
