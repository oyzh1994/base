package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class ResourceIdSpec implements XStruct {
  public static final String PLUGIN_NAME = "res";
  private final int resource;
  private final int type;

  public static ResourceIdSpec readResourceIdSpec(X11Input in) throws IOException {
    ResourceIdSpec.ResourceIdSpecBuilder javaBuilder = ResourceIdSpec.builder();
    int resource = in.readCard32();
    int type = in.readCard32();
    javaBuilder.resource(resource);
    javaBuilder.type(type);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(resource);
    out.writeCard32(type);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ResourceIdSpecBuilder {
    @java.lang.SuppressWarnings("all")
    private int resource;
    @java.lang.SuppressWarnings("all")
    private int type;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    ResourceIdSpecBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ResourceIdSpec.ResourceIdSpecBuilder resource(final int resource) {
      this.resource = resource;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ResourceIdSpec.ResourceIdSpecBuilder type(final int type) {
      this.type = type;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ResourceIdSpec build() {
      return new ResourceIdSpec(this.resource, this.type);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ResourceIdSpec.ResourceIdSpecBuilder(resource=" + this.resource + ", type=" + this.type + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ResourceIdSpec(final int resource, final int type) {
    this.resource = resource;
    this.type = type;
  }

  @java.lang.SuppressWarnings("all")
  public static ResourceIdSpec.ResourceIdSpecBuilder builder() {
    return new ResourceIdSpec.ResourceIdSpecBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getResource() {
    return this.resource;
  }

  @java.lang.SuppressWarnings("all")
  public int getType() {
    return this.type;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ResourceIdSpec)) return false;
    final ResourceIdSpec other = (ResourceIdSpec) o;
    if (this.getResource() != other.getResource()) return false;
    if (this.getType() != other.getType()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getResource();
    result = result * PRIME + this.getType();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ResourceIdSpec(resource=" + this.getResource() + ", type=" + this.getType() + ")";
  }
}
