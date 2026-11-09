package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SelectSelectionInput implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 2;
  private final int window;
  private final int selection;
  private final int eventMask;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SelectSelectionInput readSelectSelectionInput(X11Input in) throws IOException {
    SelectSelectionInput.SelectSelectionInputBuilder javaBuilder = SelectSelectionInput.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int selection = in.readCard32();
    int eventMask = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.selection(selection);
    javaBuilder.eventMask(eventMask);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(selection);
    out.writeCard32(eventMask);
  }

  public boolean isEventMaskEnabled(SelectionEventMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (SelectionEventMask m : maskEnums) {
      if (!m.isEnabled(eventMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SelectSelectionInputBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int selection;
    @java.lang.SuppressWarnings("all")
    private int eventMask;

    public boolean isEventMaskEnabled(SelectionEventMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (SelectionEventMask m : maskEnums) {
        if (!m.isEnabled(eventMask)) {
          return false;
        }
      }
      return true;
    }

    public SelectSelectionInput.SelectSelectionInputBuilder eventMaskEnable(SelectionEventMask... maskEnums) {
      for (SelectionEventMask m : maskEnums) {
        eventMask((int) m.enableFor(eventMask));
      }
      return this;
    }

    public SelectSelectionInput.SelectSelectionInputBuilder eventMaskDisable(SelectionEventMask... maskEnums) {
      for (SelectionEventMask m : maskEnums) {
        eventMask((int) m.disableFor(eventMask));
      }
      return this;
    }

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    SelectSelectionInputBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectSelectionInput.SelectSelectionInputBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectSelectionInput.SelectSelectionInputBuilder selection(final int selection) {
      this.selection = selection;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectSelectionInput.SelectSelectionInputBuilder eventMask(final int eventMask) {
      this.eventMask = eventMask;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SelectSelectionInput build() {
      return new SelectSelectionInput(this.window, this.selection, this.eventMask);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SelectSelectionInput.SelectSelectionInputBuilder(window=" + this.window + ", selection=" + this.selection + ", eventMask=" + this.eventMask + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SelectSelectionInput(final int window, final int selection, final int eventMask) {
    this.window = window;
    this.selection = selection;
    this.eventMask = eventMask;
  }

  @java.lang.SuppressWarnings("all")
  public static SelectSelectionInput.SelectSelectionInputBuilder builder() {
    return new SelectSelectionInput.SelectSelectionInputBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getSelection() {
    return this.selection;
  }

  @java.lang.SuppressWarnings("all")
  public int getEventMask() {
    return this.eventMask;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SelectSelectionInput)) return false;
    final SelectSelectionInput other = (SelectSelectionInput) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getSelection() != other.getSelection()) return false;
    if (this.getEventMask() != other.getEventMask()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getSelection();
    result = result * PRIME + this.getEventMask();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SelectSelectionInput(window=" + this.getWindow() + ", selection=" + this.getSelection() + ", eventMask=" + this.getEventMask() + ")";
  }
}
