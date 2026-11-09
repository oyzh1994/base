package com.github.moaxcp.x11.protocol.present;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Notify implements XStruct {
  public static final String PLUGIN_NAME = "present";
  private final int window;
  private final int serial;

  public static Notify readNotify(X11Input in) throws IOException {
    Notify.NotifyBuilder javaBuilder = Notify.builder();
    int window = in.readCard32();
    int serial = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.serial(serial);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(window);
    out.writeCard32(serial);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class NotifyBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int serial;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    NotifyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Notify.NotifyBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Notify.NotifyBuilder serial(final int serial) {
      this.serial = serial;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Notify build() {
      return new Notify(this.window, this.serial);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Notify.NotifyBuilder(window=" + this.window + ", serial=" + this.serial + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Notify(final int window, final int serial) {
    this.window = window;
    this.serial = serial;
  }

  @java.lang.SuppressWarnings("all")
  public static Notify.NotifyBuilder builder() {
    return new Notify.NotifyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getSerial() {
    return this.serial;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Notify)) return false;
    final Notify other = (Notify) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getSerial() != other.getSerial()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getSerial();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Notify(window=" + this.getWindow() + ", serial=" + this.getSerial() + ")";
  }
}
