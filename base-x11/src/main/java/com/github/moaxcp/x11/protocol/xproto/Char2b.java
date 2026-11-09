package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Char2b implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final byte byte1;
  private final byte byte2;

  public static Char2b readChar2b(X11Input in) throws IOException {
    Char2b.Char2bBuilder javaBuilder = Char2b.builder();
    byte byte1 = in.readCard8();
    byte byte2 = in.readCard8();
    javaBuilder.byte1(byte1);
    javaBuilder.byte2(byte2);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(byte1);
    out.writeCard8(byte2);
  }

  @Override
  public int getSize() {
    return 2;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class Char2bBuilder {
    @java.lang.SuppressWarnings("all")
    private byte byte1;
    @java.lang.SuppressWarnings("all")
    private byte byte2;

    public int getSize() {
      return 2;
    }

    @java.lang.SuppressWarnings("all")
    Char2bBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Char2b.Char2bBuilder byte1(final byte byte1) {
      this.byte1 = byte1;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Char2b.Char2bBuilder byte2(final byte byte2) {
      this.byte2 = byte2;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Char2b build() {
      return new Char2b(this.byte1, this.byte2);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Char2b.Char2bBuilder(byte1=" + this.byte1 + ", byte2=" + this.byte2 + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Char2b(final byte byte1, final byte byte2) {
    this.byte1 = byte1;
    this.byte2 = byte2;
  }

  @java.lang.SuppressWarnings("all")
  public static Char2b.Char2bBuilder builder() {
    return new Char2b.Char2bBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getByte1() {
    return this.byte1;
  }

  @java.lang.SuppressWarnings("all")
  public byte getByte2() {
    return this.byte2;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Char2b)) return false;
    final Char2b other = (Char2b) o;
    if (this.getByte1() != other.getByte1()) return false;
    if (this.getByte2() != other.getByte2()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getByte1();
    result = result * PRIME + this.getByte2();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Char2b(byte1=" + this.getByte1() + ", byte2=" + this.getByte2() + ")";
  }
}
