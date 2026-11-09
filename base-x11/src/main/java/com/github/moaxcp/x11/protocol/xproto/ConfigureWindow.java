package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ConfigureWindow implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 12;
  private final int window;
  private final short valueMask;
  private final int x;
  private final int y;
  private final int width;
  private final int height;
  private final int borderWidth;
  private final int sibling;
  private final int stackMode;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ConfigureWindow readConfigureWindow(X11Input in) throws IOException {
    ConfigureWindow.ConfigureWindowBuilder javaBuilder = ConfigureWindow.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    short valueMask = in.readCard16();
    byte[] pad5 = in.readPad(2);
    int x = 0;
    int y = 0;
    int width = 0;
    int height = 0;
    int borderWidth = 0;
    int sibling = 0;
    int stackMode = 0;
    javaBuilder.window(window);
    javaBuilder.valueMask(valueMask);
    if (ConfigWindow.X.isEnabled(Short.toUnsignedInt(valueMask))) {
      x = in.readInt32();
      javaBuilder.x(x);
    }
    if (ConfigWindow.Y.isEnabled(Short.toUnsignedInt(valueMask))) {
      y = in.readInt32();
      javaBuilder.y(y);
    }
    if (ConfigWindow.WIDTH.isEnabled(Short.toUnsignedInt(valueMask))) {
      width = in.readCard32();
      javaBuilder.width(width);
    }
    if (ConfigWindow.HEIGHT.isEnabled(Short.toUnsignedInt(valueMask))) {
      height = in.readCard32();
      javaBuilder.height(height);
    }
    if (ConfigWindow.BORDER_WIDTH.isEnabled(Short.toUnsignedInt(valueMask))) {
      borderWidth = in.readCard32();
      javaBuilder.borderWidth(borderWidth);
    }
    if (ConfigWindow.SIBLING.isEnabled(Short.toUnsignedInt(valueMask))) {
      sibling = in.readCard32();
      javaBuilder.sibling(sibling);
    }
    if (ConfigWindow.STACK_MODE.isEnabled(Short.toUnsignedInt(valueMask))) {
      stackMode = in.readCard32();
      javaBuilder.stackMode(stackMode);
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard16(valueMask);
    out.writePad(2);
    if (ConfigWindow.X.isEnabled(Short.toUnsignedInt(valueMask))) {
      out.writeInt32(x);
    }
    if (ConfigWindow.Y.isEnabled(Short.toUnsignedInt(valueMask))) {
      out.writeInt32(y);
    }
    if (ConfigWindow.WIDTH.isEnabled(Short.toUnsignedInt(valueMask))) {
      out.writeCard32(width);
    }
    if (ConfigWindow.HEIGHT.isEnabled(Short.toUnsignedInt(valueMask))) {
      out.writeCard32(height);
    }
    if (ConfigWindow.BORDER_WIDTH.isEnabled(Short.toUnsignedInt(valueMask))) {
      out.writeCard32(borderWidth);
    }
    if (ConfigWindow.SIBLING.isEnabled(Short.toUnsignedInt(valueMask))) {
      out.writeCard32(sibling);
    }
    if (ConfigWindow.STACK_MODE.isEnabled(Short.toUnsignedInt(valueMask))) {
      out.writeCard32(stackMode);
    }
    out.writePadAlign(getSize());
  }

  public boolean isValueMaskEnabled(ConfigWindow... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ConfigWindow m : maskEnums) {
      if (!m.isEnabled(valueMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 12 + (ConfigWindow.X.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.Y.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.WIDTH.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.HEIGHT.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.BORDER_WIDTH.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.SIBLING.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.STACK_MODE.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ConfigureWindowBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private short valueMask;
    @java.lang.SuppressWarnings("all")
    private int x;
    @java.lang.SuppressWarnings("all")
    private int y;
    @java.lang.SuppressWarnings("all")
    private int width;
    @java.lang.SuppressWarnings("all")
    private int height;
    @java.lang.SuppressWarnings("all")
    private int borderWidth;
    @java.lang.SuppressWarnings("all")
    private int sibling;
    @java.lang.SuppressWarnings("all")
    private int stackMode;

    private ConfigureWindow.ConfigureWindowBuilder valueMask(short valueMask) {
      this.valueMask = valueMask;
      return this;
    }

    public boolean isValueMaskEnabled(ConfigWindow... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ConfigWindow m : maskEnums) {
        if (!m.isEnabled(valueMask)) {
          return false;
        }
      }
      return true;
    }

    private ConfigureWindow.ConfigureWindowBuilder valueMaskEnable(ConfigWindow... maskEnums) {
      for (ConfigWindow m : maskEnums) {
        valueMask((short) m.enableFor(valueMask));
      }
      return this;
    }

    private ConfigureWindow.ConfigureWindowBuilder valueMaskDisable(ConfigWindow... maskEnums) {
      for (ConfigWindow m : maskEnums) {
        valueMask((short) m.disableFor(valueMask));
      }
      return this;
    }

    public ConfigureWindow.ConfigureWindowBuilder x(int x) {
      this.x = x;
      valueMaskEnable(ConfigWindow.X);
      return this;
    }

    public ConfigureWindow.ConfigureWindowBuilder y(int y) {
      this.y = y;
      valueMaskEnable(ConfigWindow.Y);
      return this;
    }

    public ConfigureWindow.ConfigureWindowBuilder width(int width) {
      this.width = width;
      valueMaskEnable(ConfigWindow.WIDTH);
      return this;
    }

    public ConfigureWindow.ConfigureWindowBuilder height(int height) {
      this.height = height;
      valueMaskEnable(ConfigWindow.HEIGHT);
      return this;
    }

    public ConfigureWindow.ConfigureWindowBuilder borderWidth(int borderWidth) {
      this.borderWidth = borderWidth;
      valueMaskEnable(ConfigWindow.BORDER_WIDTH);
      return this;
    }

    public ConfigureWindow.ConfigureWindowBuilder sibling(int sibling) {
      this.sibling = sibling;
      valueMaskEnable(ConfigWindow.SIBLING);
      return this;
    }

    public ConfigureWindow.ConfigureWindowBuilder stackMode(int stackMode) {
      this.stackMode = stackMode;
      valueMaskEnable(ConfigWindow.STACK_MODE);
      return this;
    }

    public ConfigureWindow.ConfigureWindowBuilder stackMode(StackMode stackMode) {
      this.stackMode = (int) stackMode.getValue();
      valueMaskEnable(ConfigWindow.STACK_MODE);
      return this;
    }

    public int getSize() {
      return 12 + (ConfigWindow.X.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.Y.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.WIDTH.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.HEIGHT.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.BORDER_WIDTH.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.SIBLING.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0) + (ConfigWindow.STACK_MODE.isEnabled(Short.toUnsignedInt(valueMask)) ? 4 : 0);
    }

    @java.lang.SuppressWarnings("all")
    ConfigureWindowBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureWindow.ConfigureWindowBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ConfigureWindow build() {
      return new ConfigureWindow(this.window, this.valueMask, this.x, this.y, this.width, this.height, this.borderWidth, this.sibling, this.stackMode);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ConfigureWindow.ConfigureWindowBuilder(window=" + this.window + ", valueMask=" + this.valueMask + ", x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", borderWidth=" + this.borderWidth + ", sibling=" + this.sibling + ", stackMode=" + this.stackMode + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ConfigureWindow(final int window, final short valueMask, final int x, final int y, final int width, final int height, final int borderWidth, final int sibling, final int stackMode) {
    this.window = window;
    this.valueMask = valueMask;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.borderWidth = borderWidth;
    this.sibling = sibling;
    this.stackMode = stackMode;
  }

  @java.lang.SuppressWarnings("all")
  public static ConfigureWindow.ConfigureWindowBuilder builder() {
    return new ConfigureWindow.ConfigureWindowBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public short getValueMask() {
    return this.valueMask;
  }

  @java.lang.SuppressWarnings("all")
  public int getX() {
    return this.x;
  }

  @java.lang.SuppressWarnings("all")
  public int getY() {
    return this.y;
  }

  @java.lang.SuppressWarnings("all")
  public int getWidth() {
    return this.width;
  }

  @java.lang.SuppressWarnings("all")
  public int getHeight() {
    return this.height;
  }

  @java.lang.SuppressWarnings("all")
  public int getBorderWidth() {
    return this.borderWidth;
  }

  @java.lang.SuppressWarnings("all")
  public int getSibling() {
    return this.sibling;
  }

  @java.lang.SuppressWarnings("all")
  public int getStackMode() {
    return this.stackMode;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ConfigureWindow)) return false;
    final ConfigureWindow other = (ConfigureWindow) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getValueMask() != other.getValueMask()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getBorderWidth() != other.getBorderWidth()) return false;
    if (this.getSibling() != other.getSibling()) return false;
    if (this.getStackMode() != other.getStackMode()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getValueMask();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getBorderWidth();
    result = result * PRIME + this.getSibling();
    result = result * PRIME + this.getStackMode();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ConfigureWindow(window=" + this.getWindow() + ", valueMask=" + this.getValueMask() + ", x=" + this.getX() + ", y=" + this.getY() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", borderWidth=" + this.getBorderWidth() + ", sibling=" + this.getSibling() + ", stackMode=" + this.getStackMode() + ")";
  }
}
