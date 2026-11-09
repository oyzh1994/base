package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class RotateProperties implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 114;
  private final int window;
  private final short delta;
  private final int[] atoms;

  public byte getOpCode() {
    return OPCODE;
  }

  public static RotateProperties readRotateProperties(X11Input in) throws IOException {
    RotateProperties.RotatePropertiesBuilder javaBuilder = RotateProperties.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    short atomsLen = in.readCard16();
    short delta = in.readInt16();
    int[] atoms = in.readCard32(Short.toUnsignedInt(atomsLen));
    javaBuilder.window(window);
    javaBuilder.delta(delta);
    javaBuilder.atoms(atoms);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    short atomsLen = (short) atoms.length;
    out.writeCard16(atomsLen);
    out.writeInt16(delta);
    out.writeCard32(atoms);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 4 * atoms.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class RotatePropertiesBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private short delta;
    @java.lang.SuppressWarnings("all")
    private int[] atoms;

    public int getSize() {
      return 12 + 4 * atoms.length;
    }

    @java.lang.SuppressWarnings("all")
    RotatePropertiesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RotateProperties.RotatePropertiesBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RotateProperties.RotatePropertiesBuilder delta(final short delta) {
      this.delta = delta;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RotateProperties.RotatePropertiesBuilder atoms(final int[] atoms) {
      if (atoms == null) {
        throw new java.lang.NullPointerException("atoms is marked non-null but is null");
      }
      this.atoms = atoms;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public RotateProperties build() {
      return new RotateProperties(this.window, this.delta, this.atoms);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "RotateProperties.RotatePropertiesBuilder(window=" + this.window + ", delta=" + this.delta + ", atoms=" + java.util.Arrays.toString(this.atoms) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  RotateProperties(final int window, final short delta, final int[] atoms) {
    if (atoms == null) {
      throw new java.lang.NullPointerException("atoms is marked non-null but is null");
    }
    this.window = window;
    this.delta = delta;
    this.atoms = atoms;
  }

  @java.lang.SuppressWarnings("all")
  public static RotateProperties.RotatePropertiesBuilder builder() {
    return new RotateProperties.RotatePropertiesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public short getDelta() {
    return this.delta;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getAtoms() {
    return this.atoms;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof RotateProperties)) return false;
    final RotateProperties other = (RotateProperties) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getDelta() != other.getDelta()) return false;
    if (!java.util.Arrays.equals(this.getAtoms(), other.getAtoms())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getDelta();
    result = result * PRIME + java.util.Arrays.hashCode(this.getAtoms());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "RotateProperties(window=" + this.getWindow() + ", delta=" + this.getDelta() + ", atoms=" + java.util.Arrays.toString(this.getAtoms()) + ")";
  }
}
