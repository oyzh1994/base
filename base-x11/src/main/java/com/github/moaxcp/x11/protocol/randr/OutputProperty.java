package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.xproto.Property;
import java.io.IOException;

public final class OutputProperty implements NotifyDataUnion, XStruct {
  public static final String PLUGIN_NAME = "randr";
  private final int window;
  private final int output;
  private final int atom;
  private final int timestamp;
  private final byte status;

  public static OutputProperty readOutputProperty(X11Input in) throws IOException {
    OutputProperty.OutputPropertyBuilder javaBuilder = OutputProperty.builder();
    int window = in.readCard32();
    int output = in.readCard32();
    int atom = in.readCard32();
    int timestamp = in.readCard32();
    byte status = in.readCard8();
    byte[] pad5 = in.readPad(11);
    javaBuilder.window(window);
    javaBuilder.output(output);
    javaBuilder.atom(atom);
    javaBuilder.timestamp(timestamp);
    javaBuilder.status(status);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(window);
    out.writeCard32(output);
    out.writeCard32(atom);
    out.writeCard32(timestamp);
    out.writeCard8(status);
    out.writePad(11);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class OutputPropertyBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int output;
    @java.lang.SuppressWarnings("all")
    private int atom;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private byte status;

    public OutputProperty.OutputPropertyBuilder status(Property status) {
      this.status = (byte) status.getValue();
      return this;
    }

    public OutputProperty.OutputPropertyBuilder status(byte status) {
      this.status = status;
      return this;
    }

    public int getSize() {
      return 28;
    }

    @java.lang.SuppressWarnings("all")
    OutputPropertyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OutputProperty.OutputPropertyBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OutputProperty.OutputPropertyBuilder output(final int output) {
      this.output = output;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OutputProperty.OutputPropertyBuilder atom(final int atom) {
      this.atom = atom;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OutputProperty.OutputPropertyBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public OutputProperty build() {
      return new OutputProperty(this.window, this.output, this.atom, this.timestamp, this.status);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "OutputProperty.OutputPropertyBuilder(window=" + this.window + ", output=" + this.output + ", atom=" + this.atom + ", timestamp=" + this.timestamp + ", status=" + this.status + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  OutputProperty(final int window, final int output, final int atom, final int timestamp, final byte status) {
    this.window = window;
    this.output = output;
    this.atom = atom;
    this.timestamp = timestamp;
    this.status = status;
  }

  @java.lang.SuppressWarnings("all")
  public static OutputProperty.OutputPropertyBuilder builder() {
    return new OutputProperty.OutputPropertyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getOutput() {
    return this.output;
  }

  @java.lang.SuppressWarnings("all")
  public int getAtom() {
    return this.atom;
  }

  @java.lang.SuppressWarnings("all")
  public int getTimestamp() {
    return this.timestamp;
  }

  @java.lang.SuppressWarnings("all")
  public byte getStatus() {
    return this.status;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof OutputProperty)) return false;
    final OutputProperty other = (OutputProperty) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getOutput() != other.getOutput()) return false;
    if (this.getAtom() != other.getAtom()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getStatus() != other.getStatus()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getOutput();
    result = result * PRIME + this.getAtom();
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getStatus();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "OutputProperty(window=" + this.getWindow() + ", output=" + this.getOutput() + ", atom=" + this.getAtom() + ", timestamp=" + this.getTimestamp() + ", status=" + this.getStatus() + ")";
  }
}
