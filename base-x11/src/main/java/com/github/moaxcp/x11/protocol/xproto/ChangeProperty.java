package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class ChangeProperty implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 18;
  private final byte mode;
  private final int window;
  private final int property;
  private final int type;
  private final byte format;
  private final int dataLen;
  private final ByteList data;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeProperty readChangeProperty(X11Input in) throws IOException {
    ChangeProperty.ChangePropertyBuilder javaBuilder = ChangeProperty.builder();
    byte mode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int property = in.readCard32();
    int type = in.readCard32();
    byte format = in.readCard8();
    byte[] pad7 = in.readPad(3);
    int dataLen = in.readCard32();
    ByteList data = in.readVoid((int) ((Integer.toUnsignedLong(dataLen) * Byte.toUnsignedInt(format)) / 8));
    javaBuilder.mode(mode);
    javaBuilder.window(window);
    javaBuilder.property(property);
    javaBuilder.type(type);
    javaBuilder.format(format);
    javaBuilder.dataLen(dataLen);
    javaBuilder.data(data.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard8(mode);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(property);
    out.writeCard32(type);
    out.writeCard8(format);
    out.writePad(3);
    out.writeCard32(dataLen);
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


  public static class ChangePropertyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte mode;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int property;
    @java.lang.SuppressWarnings("all")
    private int type;
    @java.lang.SuppressWarnings("all")
    private byte format;
    @java.lang.SuppressWarnings("all")
    private int dataLen;
    @java.lang.SuppressWarnings("all")
    private ByteList data;

    public ChangeProperty.ChangePropertyBuilder mode(PropMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public ChangeProperty.ChangePropertyBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 24 + 1 * data.size();
    }

    @java.lang.SuppressWarnings("all")
    ChangePropertyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeProperty.ChangePropertyBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeProperty.ChangePropertyBuilder property(final int property) {
      this.property = property;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeProperty.ChangePropertyBuilder type(final int type) {
      this.type = type;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeProperty.ChangePropertyBuilder format(final byte format) {
      this.format = format;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeProperty.ChangePropertyBuilder dataLen(final int dataLen) {
      this.dataLen = dataLen;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeProperty.ChangePropertyBuilder data(final ByteList data) {
      if (data == null) {
        throw new java.lang.NullPointerException("data is marked non-null but is null");
      }
      this.data = data;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ChangeProperty build() {
      return new ChangeProperty(this.mode, this.window, this.property, this.type, this.format, this.dataLen, this.data);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ChangeProperty.ChangePropertyBuilder(mode=" + this.mode + ", window=" + this.window + ", property=" + this.property + ", type=" + this.type + ", format=" + this.format + ", dataLen=" + this.dataLen + ", data=" + this.data + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ChangeProperty(final byte mode, final int window, final int property, final int type, final byte format, final int dataLen, final ByteList data) {
    if (data == null) {
      throw new java.lang.NullPointerException("data is marked non-null but is null");
    }
    this.mode = mode;
    this.window = window;
    this.property = property;
    this.type = type;
    this.format = format;
    this.dataLen = dataLen;
    this.data = data;
  }

  @java.lang.SuppressWarnings("all")
  public static ChangeProperty.ChangePropertyBuilder builder() {
    return new ChangeProperty.ChangePropertyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getMode() {
    return this.mode;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
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
  public int getDataLen() {
    return this.dataLen;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getData() {
    return this.data;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ChangeProperty)) return false;
    final ChangeProperty other = (ChangeProperty) o;
    if (this.getMode() != other.getMode()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getProperty() != other.getProperty()) return false;
    if (this.getType() != other.getType()) return false;
    if (this.getFormat() != other.getFormat()) return false;
    if (this.getDataLen() != other.getDataLen()) return false;
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
    result = result * PRIME + this.getMode();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getProperty();
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getFormat();
    result = result * PRIME + this.getDataLen();
    final java.lang.Object $data = this.getData();
    result = result * PRIME + ($data == null ? 43 : $data.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ChangeProperty(mode=" + this.getMode() + ", window=" + this.getWindow() + ", property=" + this.getProperty() + ", type=" + this.getType() + ", format=" + this.getFormat() + ", dataLen=" + this.getDataLen() + ", data=" + this.getData() + ")";
  }
}
