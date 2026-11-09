package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.IntList;

public final class MonitorInfo implements XStruct {
  public static final String PLUGIN_NAME = "randr";
  private final int name;
  private final boolean primary;
  private final boolean automatic;
  private final short x;
  private final short y;
  private final short width;
  private final short height;
  private final int widthInMillimeters;
  private final int heightInMillimeters;
  private final IntList outputs;

  public static MonitorInfo readMonitorInfo(X11Input in) throws IOException {
    MonitorInfo.MonitorInfoBuilder javaBuilder = MonitorInfo.builder();
    int name = in.readCard32();
    boolean primary = in.readBool();
    boolean automatic = in.readBool();
    short nOutput = in.readCard16();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    int widthInMillimeters = in.readCard32();
    int heightInMillimeters = in.readCard32();
    IntList outputs = in.readCard32(Short.toUnsignedInt(nOutput));
    javaBuilder.name(name);
    javaBuilder.primary(primary);
    javaBuilder.automatic(automatic);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.widthInMillimeters(widthInMillimeters);
    javaBuilder.heightInMillimeters(heightInMillimeters);
    javaBuilder.outputs(outputs.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(name);
    out.writeBool(primary);
    out.writeBool(automatic);
    short nOutput = (short) outputs.size();
    out.writeCard16(nOutput);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard32(widthInMillimeters);
    out.writeCard32(heightInMillimeters);
    out.writeCard32(outputs);
  }

  @Override
  public int getSize() {
    return 24 + 4 * outputs.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class MonitorInfoBuilder {
    @java.lang.SuppressWarnings("all")
    private int name;
    @java.lang.SuppressWarnings("all")
    private boolean primary;
    @java.lang.SuppressWarnings("all")
    private boolean automatic;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;
    @java.lang.SuppressWarnings("all")
    private int widthInMillimeters;
    @java.lang.SuppressWarnings("all")
    private int heightInMillimeters;
    @java.lang.SuppressWarnings("all")
    private IntList outputs;

    public int getSize() {
      return 24 + 4 * outputs.size();
    }

    @java.lang.SuppressWarnings("all")
    MonitorInfoBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MonitorInfo.MonitorInfoBuilder name(final int name) {
      this.name = name;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MonitorInfo.MonitorInfoBuilder primary(final boolean primary) {
      this.primary = primary;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MonitorInfo.MonitorInfoBuilder automatic(final boolean automatic) {
      this.automatic = automatic;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MonitorInfo.MonitorInfoBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MonitorInfo.MonitorInfoBuilder y(final short y) {
      this.y = y;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MonitorInfo.MonitorInfoBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MonitorInfo.MonitorInfoBuilder height(final short height) {
      this.height = height;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MonitorInfo.MonitorInfoBuilder widthInMillimeters(final int widthInMillimeters) {
      this.widthInMillimeters = widthInMillimeters;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MonitorInfo.MonitorInfoBuilder heightInMillimeters(final int heightInMillimeters) {
      this.heightInMillimeters = heightInMillimeters;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MonitorInfo.MonitorInfoBuilder outputs(final IntList outputs) {
      if (outputs == null) {
        throw new java.lang.NullPointerException("outputs is marked non-null but is null");
      }
      this.outputs = outputs;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public MonitorInfo build() {
      return new MonitorInfo(this.name, this.primary, this.automatic, this.x, this.y, this.width, this.height, this.widthInMillimeters, this.heightInMillimeters, this.outputs);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "MonitorInfo.MonitorInfoBuilder(name=" + this.name + ", primary=" + this.primary + ", automatic=" + this.automatic + ", x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", widthInMillimeters=" + this.widthInMillimeters + ", heightInMillimeters=" + this.heightInMillimeters + ", outputs=" + this.outputs + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  MonitorInfo(final int name, final boolean primary, final boolean automatic, final short x, final short y, final short width, final short height, final int widthInMillimeters, final int heightInMillimeters, final IntList outputs) {
    if (outputs == null) {
      throw new java.lang.NullPointerException("outputs is marked non-null but is null");
    }
    this.name = name;
    this.primary = primary;
    this.automatic = automatic;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.widthInMillimeters = widthInMillimeters;
    this.heightInMillimeters = heightInMillimeters;
    this.outputs = outputs;
  }

  @java.lang.SuppressWarnings("all")
  public static MonitorInfo.MonitorInfoBuilder builder() {
    return new MonitorInfo.MonitorInfoBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getName() {
    return this.name;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isPrimary() {
    return this.primary;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isAutomatic() {
    return this.automatic;
  }

  @java.lang.SuppressWarnings("all")
  public short getX() {
    return this.x;
  }

  @java.lang.SuppressWarnings("all")
  public short getY() {
    return this.y;
  }

  @java.lang.SuppressWarnings("all")
  public short getWidth() {
    return this.width;
  }

  @java.lang.SuppressWarnings("all")
  public short getHeight() {
    return this.height;
  }

  @java.lang.SuppressWarnings("all")
  public int getWidthInMillimeters() {
    return this.widthInMillimeters;
  }

  @java.lang.SuppressWarnings("all")
  public int getHeightInMillimeters() {
    return this.heightInMillimeters;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getOutputs() {
    return this.outputs;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof MonitorInfo)) return false;
    final MonitorInfo other = (MonitorInfo) o;
    if (this.getName() != other.getName()) return false;
    if (this.isPrimary() != other.isPrimary()) return false;
    if (this.isAutomatic() != other.isAutomatic()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getWidthInMillimeters() != other.getWidthInMillimeters()) return false;
    if (this.getHeightInMillimeters() != other.getHeightInMillimeters()) return false;
    final java.lang.Object this$outputs = this.getOutputs();
    final java.lang.Object other$outputs = other.getOutputs();
    if (this$outputs == null ? other$outputs != null : !this$outputs.equals(other$outputs)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getName();
    result = result * PRIME + (this.isPrimary() ? 79 : 97);
    result = result * PRIME + (this.isAutomatic() ? 79 : 97);
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getWidthInMillimeters();
    result = result * PRIME + this.getHeightInMillimeters();
    final java.lang.Object $outputs = this.getOutputs();
    result = result * PRIME + ($outputs == null ? 43 : $outputs.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "MonitorInfo(name=" + this.getName() + ", primary=" + this.isPrimary() + ", automatic=" + this.isAutomatic() + ", x=" + this.getX() + ", y=" + this.getY() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", widthInMillimeters=" + this.getWidthInMillimeters() + ", heightInMillimeters=" + this.getHeightInMillimeters() + ", outputs=" + this.getOutputs() + ")";
  }
}
