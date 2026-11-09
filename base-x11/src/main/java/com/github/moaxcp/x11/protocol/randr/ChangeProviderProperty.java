package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ChangeProviderProperty implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 39;
  private final int provider;
  private final int property;
  private final int type;
  private final byte format;
  private final byte mode;
  private final int numItems;
  private final byte[] data;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeProviderProperty readChangeProviderProperty(X11Input in) throws IOException {
    ChangeProviderProperty.ChangeProviderPropertyBuilder javaBuilder = ChangeProviderProperty.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int provider = in.readCard32();
    int property = in.readCard32();
    int type = in.readCard32();
    byte format = in.readCard8();
    byte mode = in.readCard8();
    byte[] pad8 = in.readPad(2);
    int numItems = in.readCard32();
    byte[] data = in.readVoid((int) (Integer.toUnsignedLong(numItems) * (Byte.toUnsignedInt(format) / 8)));
    javaBuilder.provider(provider);
    javaBuilder.property(property);
    javaBuilder.type(type);
    javaBuilder.format(format);
    javaBuilder.mode(mode);
    javaBuilder.numItems(numItems);
    javaBuilder.data(data);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(provider);
    out.writeCard32(property);
    out.writeCard32(type);
    out.writeCard8(format);
    out.writeCard8(mode);
    out.writePad(2);
    out.writeCard32(numItems);
    out.writeVoid(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 24 + 1 * data.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ChangeProviderPropertyBuilder {
    @java.lang.SuppressWarnings("all")
    private int provider;
    @java.lang.SuppressWarnings("all")
    private int property;
    @java.lang.SuppressWarnings("all")
    private int type;
    @java.lang.SuppressWarnings("all")
    private byte format;
    @java.lang.SuppressWarnings("all")
    private byte mode;
    @java.lang.SuppressWarnings("all")
    private int numItems;
    @java.lang.SuppressWarnings("all")
    private byte[] data;

    public int getSize() {
      return 24 + 1 * data.length;
    }

    @java.lang.SuppressWarnings("all")
    ChangeProviderPropertyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeProviderProperty.ChangeProviderPropertyBuilder provider(final int provider) {
      this.provider = provider;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeProviderProperty.ChangeProviderPropertyBuilder property(final int property) {
      this.property = property;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeProviderProperty.ChangeProviderPropertyBuilder type(final int type) {
      this.type = type;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeProviderProperty.ChangeProviderPropertyBuilder format(final byte format) {
      this.format = format;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeProviderProperty.ChangeProviderPropertyBuilder mode(final byte mode) {
      this.mode = mode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeProviderProperty.ChangeProviderPropertyBuilder numItems(final int numItems) {
      this.numItems = numItems;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeProviderProperty.ChangeProviderPropertyBuilder data(final byte[] data) {
      if (data == null) {
        throw new java.lang.NullPointerException("data is marked non-null but is null");
      }
      this.data = data;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ChangeProviderProperty build() {
      return new ChangeProviderProperty(this.provider, this.property, this.type, this.format, this.mode, this.numItems, this.data);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ChangeProviderProperty.ChangeProviderPropertyBuilder(provider=" + this.provider + ", property=" + this.property + ", type=" + this.type + ", format=" + this.format + ", mode=" + this.mode + ", numItems=" + this.numItems + ", data=" + java.util.Arrays.toString(this.data) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ChangeProviderProperty(final int provider, final int property, final int type, final byte format, final byte mode, final int numItems, final byte[] data) {
    if (data == null) {
      throw new java.lang.NullPointerException("data is marked non-null but is null");
    }
    this.provider = provider;
    this.property = property;
    this.type = type;
    this.format = format;
    this.mode = mode;
    this.numItems = numItems;
    this.data = data;
  }

  @java.lang.SuppressWarnings("all")
  public static ChangeProviderProperty.ChangeProviderPropertyBuilder builder() {
    return new ChangeProviderProperty.ChangeProviderPropertyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getProvider() {
    return this.provider;
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
  public int getNumItems() {
    return this.numItems;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getData() {
    return this.data;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ChangeProviderProperty)) return false;
    final ChangeProviderProperty other = (ChangeProviderProperty) o;
    if (this.getProvider() != other.getProvider()) return false;
    if (this.getProperty() != other.getProperty()) return false;
    if (this.getType() != other.getType()) return false;
    if (this.getFormat() != other.getFormat()) return false;
    if (this.getMode() != other.getMode()) return false;
    if (this.getNumItems() != other.getNumItems()) return false;
    if (!java.util.Arrays.equals(this.getData(), other.getData())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getProvider();
    result = result * PRIME + this.getProperty();
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getFormat();
    result = result * PRIME + this.getMode();
    result = result * PRIME + this.getNumItems();
    result = result * PRIME + java.util.Arrays.hashCode(this.getData());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ChangeProviderProperty(provider=" + this.getProvider() + ", property=" + this.getProperty() + ", type=" + this.getType() + ", format=" + this.getFormat() + ", mode=" + this.getMode() + ", numItems=" + this.getNumItems() + ", data=" + java.util.Arrays.toString(this.getData()) + ")";
  }
}
