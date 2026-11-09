package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SelectCursorInput implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 3;
  private final int window;
  private final int eventMask;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SelectCursorInput readSelectCursorInput(X11Input in) throws IOException {
    SelectCursorInput.SelectCursorInputBuilder javaBuilder = SelectCursorInput.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int eventMask = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.eventMask(eventMask);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(eventMask);
  }

  public boolean isEventMaskEnabled(CursorNotifyMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (CursorNotifyMask m : maskEnums) {
      if (!m.isEnabled(eventMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SelectCursorInputBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int eventMask;

    public boolean isEventMaskEnabled(CursorNotifyMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (CursorNotifyMask m : maskEnums) {
        if (!m.isEnabled(eventMask)) {
          return false;
        }
      }
      return true;
    }

    public SelectCursorInput.SelectCursorInputBuilder eventMaskEnable(CursorNotifyMask... maskEnums) {
      for (CursorNotifyMask m : maskEnums) {
        eventMask((int) m.enableFor(eventMask));
      }
      return this;
    }

    public SelectCursorInput.SelectCursorInputBuilder eventMaskDisable(CursorNotifyMask... maskEnums) {
      for (CursorNotifyMask m : maskEnums) {
        eventMask((int) m.disableFor(eventMask));
      }
      return this;
    }

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    SelectCursorInputBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectCursorInput.SelectCursorInputBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectCursorInput.SelectCursorInputBuilder eventMask(final int eventMask) {
      this.eventMask = eventMask;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SelectCursorInput build() {
      return new SelectCursorInput(this.window, this.eventMask);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SelectCursorInput.SelectCursorInputBuilder(window=" + this.window + ", eventMask=" + this.eventMask + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SelectCursorInput(final int window, final int eventMask) {
    this.window = window;
    this.eventMask = eventMask;
  }

  @java.lang.SuppressWarnings("all")
  public static SelectCursorInput.SelectCursorInputBuilder builder() {
    return new SelectCursorInput.SelectCursorInputBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getEventMask() {
    return this.eventMask;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SelectCursorInput)) return false;
    final SelectCursorInput other = (SelectCursorInput) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getEventMask() != other.getEventMask()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getEventMask();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SelectCursorInput(window=" + this.getWindow() + ", eventMask=" + this.getEventMask() + ")";
  }
}
