package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.xproto.PropMode;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class ChangeOutputProperty implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 13;
  private final int output;
  private final int property;
  private final int type;
  private final byte format;
  private final byte mode;
  private final int numUnits;
  private final ByteList data;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeOutputProperty readChangeOutputProperty(X11Input in) throws IOException {
    ChangeOutputProperty.ChangeOutputPropertyBuilder javaBuilder = ChangeOutputProperty.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int output = in.readCard32();
    int property = in.readCard32();
    int type = in.readCard32();
    byte format = in.readCard8();
    byte mode = in.readCard8();
    byte[] pad8 = in.readPad(2);
    int numUnits = in.readCard32();
    ByteList data = in.readVoid((int) ((Integer.toUnsignedLong(numUnits) * Byte.toUnsignedInt(format)) / 8));
    javaBuilder.output(output);
    javaBuilder.property(property);
    javaBuilder.type(type);
    javaBuilder.format(format);
    javaBuilder.mode(mode);
    javaBuilder.numUnits(numUnits);
    javaBuilder.data(data.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(output);
    out.writeCard32(property);
    out.writeCard32(type);
    out.writeCard8(format);
    out.writeCard8(mode);
    out.writePad(2);
    out.writeCard32(numUnits);
    out.writeVoid(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 24 + 1 * data.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ChangeOutputPropertyBuilder {
    @java.lang.SuppressWarnings("all")
    private int output;
    @java.lang.SuppressWarnings("all")
    private int property;
    @java.lang.SuppressWarnings("all")
    private int type;
    @java.lang.SuppressWarnings("all")
    private byte format;
    @java.lang.SuppressWarnings("all")
    private byte mode;
    @java.lang.SuppressWarnings("all")
    private int numUnits;
    @java.lang.SuppressWarnings("all")
    private ByteList data;

    public ChangeOutputProperty.ChangeOutputPropertyBuilder mode(PropMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public ChangeOutputProperty.ChangeOutputPropertyBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 24 + 1 * data.size();
    }

    @java.lang.SuppressWarnings("all")
    ChangeOutputPropertyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeOutputProperty.ChangeOutputPropertyBuilder output(final int output) {
      this.output = output;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeOutputProperty.ChangeOutputPropertyBuilder property(final int property) {
      this.property = property;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeOutputProperty.ChangeOutputPropertyBuilder type(final int type) {
      this.type = type;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeOutputProperty.ChangeOutputPropertyBuilder format(final byte format) {
      this.format = format;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeOutputProperty.ChangeOutputPropertyBuilder numUnits(final int numUnits) {
      this.numUnits = numUnits;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeOutputProperty.ChangeOutputPropertyBuilder data(final ByteList data) {
      if (data == null) {
        throw new java.lang.NullPointerException("data is marked non-null but is null");
      }
      this.data = data;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ChangeOutputProperty build() {
      return new ChangeOutputProperty(this.output, this.property, this.type, this.format, this.mode, this.numUnits, this.data);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ChangeOutputProperty.ChangeOutputPropertyBuilder(output=" + this.output + ", property=" + this.property + ", type=" + this.type + ", format=" + this.format + ", mode=" + this.mode + ", numUnits=" + this.numUnits + ", data=" + this.data + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ChangeOutputProperty(final int output, final int property, final int type, final byte format, final byte mode, final int numUnits, final ByteList data) {
    if (data == null) {
      throw new java.lang.NullPointerException("data is marked non-null but is null");
    }
    this.output = output;
    this.property = property;
    this.type = type;
    this.format = format;
    this.mode = mode;
    this.numUnits = numUnits;
    this.data = data;
  }

  @java.lang.SuppressWarnings("all")
  public static ChangeOutputProperty.ChangeOutputPropertyBuilder builder() {
    return new ChangeOutputProperty.ChangeOutputPropertyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getOutput() {
    return this.output;
  }

  @java.lang.SuppressWarnings("all")
  public int getProperty() {
    return this.property;
  }

  @java.lang.SuppressWarnings("all")
  public int getType() {
    return this.type;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFormat() {
    return this.format;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMode() {
    return this.mode;
  }

  @java.lang.SuppressWarnings("all")
  public int getNumUnits() {
    return this.numUnits;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getData() {
    return this.data;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ChangeOutputProperty)) return false;
    final ChangeOutputProperty other = (ChangeOutputProperty) o;
    if (this.getOutput() != other.getOutput()) return false;
    if (this.getProperty() != other.getProperty()) return false;
    if (this.getType() != other.getType()) return false;
    if (this.getFormat() != other.getFormat()) return false;
    if (this.getMode() != other.getMode()) return false;
    if (this.getNumUnits() != other.getNumUnits()) return false;
    final java.lang.Object this$data = this.getData();
    final java.lang.Object other$data = other.getData();
    if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getOutput();
    result = result * PRIME + this.getProperty();
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getFormat();
    result = result * PRIME + this.getMode();
    result = result * PRIME + this.getNumUnits();
    final java.lang.Object $data = this.getData();
    result = result * PRIME + ($data == null ? 43 : $data.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ChangeOutputProperty(output=" + this.getOutput() + ", property=" + this.getProperty() + ", type=" + this.getType() + ", format=" + this.getFormat() + ", mode=" + this.getMode() + ", numUnits=" + this.getNumUnits() + ", data=" + this.getData() + ")";
  }
}
