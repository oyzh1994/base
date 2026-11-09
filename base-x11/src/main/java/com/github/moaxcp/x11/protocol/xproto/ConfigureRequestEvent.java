package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class ConfigureRequestEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte NUMBER = 23;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte stackMode;
  private final short sequenceNumber;
  private final int parent;
  private final int window;
  private final int sibling;
  private final short x;
  private final short y;
  private final short width;
  private final short height;
  private final short borderWidth;
  private final short valueMask;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static ConfigureRequestEvent readConfigureRequestEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    ConfigureRequestEvent.ConfigureRequestEventBuilder javaBuilder = ConfigureRequestEvent.builder();
    byte stackMode = in.readByte();
    short sequenceNumber = in.readCard16();
    int parent = in.readCard32();
    int window = in.readCard32();
    int sibling = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    short borderWidth = in.readCard16();
    short valueMask = in.readCard16();
    byte[] pad12 = in.readPad(4);
    javaBuilder.stackMode(stackMode);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.parent(parent);
    javaBuilder.window(window);
    javaBuilder.sibling(sibling);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.borderWidth(borderWidth);
    javaBuilder.valueMask(valueMask);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeByte(stackMode);
    out.writeCard16(sequenceNumber);
    out.writeCard32(parent);
    out.writeCard32(window);
    out.writeCard32(sibling);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(borderWidth);
    out.writeCard16(valueMask);
    out.writePad(4);
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
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ConfigureRequestEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private byte stackMode;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int parent;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int sibling;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;
    @java.lang.SuppressWarnings("all")
    private short borderWidth;
    @java.lang.SuppressWarnings("all")
    private short valueMask;

    public ConfigureRequestEvent.ConfigureRequestEventBuilder stackMode(StackMode stackMode) {
      this.stackMode = (byte) stackMode.getValue();
      return this;
    }

    public ConfigureRequestEvent.ConfigureRequestEventBuilder stackMode(byte stackMode) {
      this.stackMode = stackMode;
      return this;
    }

    private ConfigureRequestEvent.ConfigureRequestEventBuilder valueMask(short valueMask) {
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

    private ConfigureRequestEvent.ConfigureRequestEventBuilder valueMaskEnable(ConfigWindow... maskEnums) {
      for (ConfigWindow m : maskEnums) {
        valueMask((short) m.enableFor(valueMask));
      }
      return this;
    }

    private ConfigureRequestEvent.ConfigureRequestEventBuilder valueMaskDisable(ConfigWindow... maskEnums) {
      for (ConfigWindow m : maskEnums) {
        valueMask((short) m.disableFor(valueMask));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    ConfigureRequestEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureRequestEvent.ConfigureRequestEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureRequestEvent.ConfigureRequestEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureRequestEvent.ConfigureRequestEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureRequestEvent.ConfigureRequestEventBuilder parent(final int parent) {
      this.parent = parent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureRequestEvent.ConfigureRequestEventBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureRequestEvent.ConfigureRequestEventBuilder sibling(final int sibling) {
      this.sibling = sibling;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureRequestEvent.ConfigureRequestEventBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureRequestEvent.ConfigureRequestEventBuilder y(final short y) {
      this.y = y;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureRequestEvent.ConfigureRequestEventBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureRequestEvent.ConfigureRequestEventBuilder height(final short height) {
      this.height = height;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureRequestEvent.ConfigureRequestEventBuilder borderWidth(final short borderWidth) {
      this.borderWidth = borderWidth;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ConfigureRequestEvent build() {
      return new ConfigureRequestEvent(this.firstEventOffset, this.sentEvent, this.stackMode, this.sequenceNumber, this.parent, this.window, this.sibling, this.x, this.y, this.width, this.height, this.borderWidth, this.valueMask);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ConfigureRequestEvent.ConfigureRequestEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", stackMode=" + this.stackMode + ", sequenceNumber=" + this.sequenceNumber + ", parent=" + this.parent + ", window=" + this.window + ", sibling=" + this.sibling + ", x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", borderWidth=" + this.borderWidth + ", valueMask=" + this.valueMask + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ConfigureRequestEvent(final byte firstEventOffset, final boolean sentEvent, final byte stackMode, final short sequenceNumber, final int parent, final int window, final int sibling, final short x, final short y, final short width, final short height, final short borderWidth, final short valueMask) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.stackMode = stackMode;
    this.sequenceNumber = sequenceNumber;
    this.parent = parent;
    this.window = window;
    this.sibling = sibling;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.borderWidth = borderWidth;
    this.valueMask = valueMask;
  }

  @java.lang.SuppressWarnings("all")
  public static ConfigureRequestEvent.ConfigureRequestEventBuilder builder() {
    return new ConfigureRequestEvent.ConfigureRequestEventBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstEventOffset() {
    return this.firstEventOffset;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isSentEvent() {
    return this.sentEvent;
  }

  @java.lang.SuppressWarnings("all")
  public byte getStackMode() {
    return this.stackMode;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getParent() {
    return this.parent;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getSibling() {
    return this.sibling;
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
  public short getBorderWidth() {
    return this.borderWidth;
  }

  @java.lang.SuppressWarnings("all")
  public short getValueMask() {
    return this.valueMask;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ConfigureRequestEvent)) return false;
    final ConfigureRequestEvent other = (ConfigureRequestEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getStackMode() != other.getStackMode()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getParent() != other.getParent()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getSibling() != other.getSibling()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getBorderWidth() != other.getBorderWidth()) return false;
    if (this.getValueMask() != other.getValueMask()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getStackMode();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getParent();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getSibling();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getBorderWidth();
    result = result * PRIME + this.getValueMask();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ConfigureRequestEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", stackMode=" + this.getStackMode() + ", sequenceNumber=" + this.getSequenceNumber() + ", parent=" + this.getParent() + ", window=" + this.getWindow() + ", sibling=" + this.getSibling() + ", x=" + this.getX() + ", y=" + this.getY() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", borderWidth=" + this.getBorderWidth() + ", valueMask=" + this.getValueMask() + ")";
  }
}
