package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Systemcounter implements XStruct {
  public static final String PLUGIN_NAME = "sync";
  private final int counter;
  private final Int64 resolution;
  private final byte[] name;

  public static Systemcounter readSystemcounter(X11Input in) throws IOException {
    Systemcounter.SystemcounterBuilder javaBuilder = Systemcounter.builder();
    int counter = in.readCard32();
    Int64 resolution = Int64.readInt64(in);
    short nameLen = in.readCard16();
    byte[] name = in.readChar(Short.toUnsignedInt(nameLen));
    in.readPadAlign(Short.toUnsignedInt(nameLen));
    javaBuilder.counter(counter);
    javaBuilder.resolution(resolution);
    javaBuilder.name(name);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(counter);
    resolution.write(out);
    short nameLen = (short) name.length;
    out.writeCard16(nameLen);
    out.writeChar(name);
    out.writePadAlign(Short.toUnsignedInt(nameLen));
  }

  @Override
  public int getSize() {
    return 14 + 1 * name.length + XObject.getSizeForPadAlign(4, 1 * name.length);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SystemcounterBuilder {
    @java.lang.SuppressWarnings("all")
    private int counter;
    @java.lang.SuppressWarnings("all")
    private Int64 resolution;
    @java.lang.SuppressWarnings("all")
    private byte[] name;

    public int getSize() {
      return 14 + 1 * name.length + XObject.getSizeForPadAlign(4, 1 * name.length);
    }

    @java.lang.SuppressWarnings("all")
    SystemcounterBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Systemcounter.SystemcounterBuilder counter(final int counter) {
      this.counter = counter;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Systemcounter.SystemcounterBuilder resolution(final Int64 resolution) {
      if (resolution == null) {
        throw new java.lang.NullPointerException("resolution is marked non-null but is null");
      }
      this.resolution = resolution;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Systemcounter.SystemcounterBuilder name(final byte[] name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Systemcounter build() {
      return new Systemcounter(this.counter, this.resolution, this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Systemcounter.SystemcounterBuilder(counter=" + this.counter + ", resolution=" + this.resolution + ", name=" + java.util.Arrays.toString(this.name) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Systemcounter(final int counter, final Int64 resolution, final byte[] name) {
    if (resolution == null) {
      throw new java.lang.NullPointerException("resolution is marked non-null but is null");
    }
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.counter = counter;
    this.resolution = resolution;
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static Systemcounter.SystemcounterBuilder builder() {
    return new Systemcounter.SystemcounterBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCounter() {
    return this.counter;
  }

  @java.lang.SuppressWarnings("all")
  public Int64 getResolution() {
    return this.resolution;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Systemcounter)) return false;
    final Systemcounter other = (Systemcounter) o;
    if (this.getCounter() != other.getCounter()) return false;
    final java.lang.Object this$resolution = this.getResolution();
    final java.lang.Object other$resolution = other.getResolution();
    if (this$resolution == null ? other$resolution != null : !this$resolution.equals(other$resolution)) return false;
    if (!java.util.Arrays.equals(this.getName(), other.getName())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCounter();
    final java.lang.Object $resolution = this.getResolution();
    result = result * PRIME + ($resolution == null ? 43 : $resolution.hashCode());
    result = result * PRIME + java.util.Arrays.hashCode(this.getName());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Systemcounter(counter=" + this.getCounter() + ", resolution=" + this.getResolution() + ", name=" + java.util.Arrays.toString(this.getName()) + ")";
  }
}
