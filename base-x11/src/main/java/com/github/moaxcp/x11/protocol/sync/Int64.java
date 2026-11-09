package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Int64 implements XStruct {
  public static final String PLUGIN_NAME = "sync";
  private final int hi;
  private final int lo;

  public static Int64 readInt64(X11Input in) throws IOException {
    Int64.Int64Builder javaBuilder = Int64.builder();
    int hi = in.readInt32();
    int lo = in.readCard32();
    javaBuilder.hi(hi);
    javaBuilder.lo(lo);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt32(hi);
    out.writeCard32(lo);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class Int64Builder {
    @java.lang.SuppressWarnings("all")
    private int hi;
    @java.lang.SuppressWarnings("all")
    private int lo;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    Int64Builder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Int64.Int64Builder hi(final int hi) {
      this.hi = hi;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Int64.Int64Builder lo(final int lo) {
      this.lo = lo;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Int64 build() {
      return new Int64(this.hi, this.lo);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Int64.Int64Builder(hi=" + this.hi + ", lo=" + this.lo + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Int64(final int hi, final int lo) {
    this.hi = hi;
    this.lo = lo;
  }

  @java.lang.SuppressWarnings("all")
  public static Int64.Int64Builder builder() {
    return new Int64.Int64Builder();
  }

  @java.lang.SuppressWarnings("all")
  public int getHi() {
    return this.hi;
  }

  @java.lang.SuppressWarnings("all")
  public int getLo() {
    return this.lo;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Int64)) return false;
    final Int64 other = (Int64) o;
    if (this.getHi() != other.getHi()) return false;
    if (this.getLo() != other.getLo()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getHi();
    result = result * PRIME + this.getLo();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Int64(hi=" + this.getHi() + ", lo=" + this.getLo() + ")";
  }
}
