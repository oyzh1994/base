package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Animcursorelt implements XStruct {
  public static final String PLUGIN_NAME = "render";
  private final int cursor;
  private final int delay;

  public static Animcursorelt readAnimcursorelt(X11Input in) throws IOException {
    Animcursorelt.AnimcursoreltBuilder javaBuilder = Animcursorelt.builder();
    int cursor = in.readCard32();
    int delay = in.readCard32();
    javaBuilder.cursor(cursor);
    javaBuilder.delay(delay);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(cursor);
    out.writeCard32(delay);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class AnimcursoreltBuilder {
    @java.lang.SuppressWarnings("all")
    private int cursor;
    @java.lang.SuppressWarnings("all")
    private int delay;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    AnimcursoreltBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Animcursorelt.AnimcursoreltBuilder cursor(final int cursor) {
      this.cursor = cursor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Animcursorelt.AnimcursoreltBuilder delay(final int delay) {
      this.delay = delay;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Animcursorelt build() {
      return new Animcursorelt(this.cursor, this.delay);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Animcursorelt.AnimcursoreltBuilder(cursor=" + this.cursor + ", delay=" + this.delay + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Animcursorelt(final int cursor, final int delay) {
    this.cursor = cursor;
    this.delay = delay;
  }

  @java.lang.SuppressWarnings("all")
  public static Animcursorelt.AnimcursoreltBuilder builder() {
    return new Animcursorelt.AnimcursoreltBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCursor() {
    return this.cursor;
  }

  @java.lang.SuppressWarnings("all")
  public int getDelay() {
    return this.delay;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Animcursorelt)) return false;
    final Animcursorelt other = (Animcursorelt) o;
    if (this.getCursor() != other.getCursor()) return false;
    if (this.getDelay() != other.getDelay()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCursor();
    result = result * PRIME + this.getDelay();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Animcursorelt(cursor=" + this.getCursor() + ", delay=" + this.getDelay() + ")";
  }
}
