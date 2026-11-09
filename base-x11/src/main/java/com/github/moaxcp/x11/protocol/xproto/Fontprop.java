package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Fontprop implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final int name;
  private final int value;

  public static Fontprop readFontprop(X11Input in) throws IOException {
    Fontprop.FontpropBuilder javaBuilder = Fontprop.builder();
    int name = in.readCard32();
    int value = in.readCard32();
    javaBuilder.name(name);
    javaBuilder.value(value);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(name);
    out.writeCard32(value);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class FontpropBuilder {
    @java.lang.SuppressWarnings("all")
    private int name;
    @java.lang.SuppressWarnings("all")
    private int value;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    FontpropBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Fontprop.FontpropBuilder name(final int name) {
      this.name = name;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Fontprop.FontpropBuilder value(final int value) {
      this.value = value;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Fontprop build() {
      return new Fontprop(this.name, this.value);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Fontprop.FontpropBuilder(name=" + this.name + ", value=" + this.value + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Fontprop(final int name, final int value) {
    this.name = name;
    this.value = value;
  }

  @java.lang.SuppressWarnings("all")
  public static Fontprop.FontpropBuilder builder() {
    return new Fontprop.FontpropBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getName() {
    return this.name;
  }

  @java.lang.SuppressWarnings("all")
  public int getValue() {
    return this.value;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Fontprop)) return false;
    final Fontprop other = (Fontprop) o;
    if (this.getName() != other.getName()) return false;
    if (this.getValue() != other.getValue()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getName();
    result = result * PRIME + this.getValue();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Fontprop(name=" + this.getName() + ", value=" + this.getValue() + ")";
  }
}
