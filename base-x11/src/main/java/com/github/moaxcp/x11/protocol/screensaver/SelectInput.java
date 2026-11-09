package com.github.moaxcp.x11.protocol.screensaver;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SelectInput implements OneWayRequest {
  public static final String PLUGIN_NAME = "screensaver";
  public static final byte OPCODE = 2;
  private final int drawable;
  private final int eventMask;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SelectInput readSelectInput(X11Input in) throws IOException {
    SelectInput.SelectInputBuilder javaBuilder = SelectInput.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    int eventMask = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.eventMask(eventMask);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(eventMask);
  }

  public boolean isEventMaskEnabled(Event... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Event m : maskEnums) {
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


  public static class SelectInputBuilder {
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private int eventMask;

    public boolean isEventMaskEnabled(Event... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Event m : maskEnums) {
        if (!m.isEnabled(eventMask)) {
          return false;
        }
      }
      return true;
    }

    public SelectInput.SelectInputBuilder eventMaskEnable(Event... maskEnums) {
      for (Event m : maskEnums) {
        eventMask((int) m.enableFor(eventMask));
      }
      return this;
    }

    public SelectInput.SelectInputBuilder eventMaskDisable(Event... maskEnums) {
      for (Event m : maskEnums) {
        eventMask((int) m.disableFor(eventMask));
      }
      return this;
    }

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    SelectInputBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectInput.SelectInputBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectInput.SelectInputBuilder eventMask(final int eventMask) {
      this.eventMask = eventMask;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SelectInput build() {
      return new SelectInput(this.drawable, this.eventMask);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SelectInput.SelectInputBuilder(drawable=" + this.drawable + ", eventMask=" + this.eventMask + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SelectInput(final int drawable, final int eventMask) {
    this.drawable = drawable;
    this.eventMask = eventMask;
  }

  @java.lang.SuppressWarnings("all")
  public static SelectInput.SelectInputBuilder builder() {
    return new SelectInput.SelectInputBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDrawable() {
    return this.drawable;
  }

  @java.lang.SuppressWarnings("all")
  public int getEventMask() {
    return this.eventMask;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SelectInput)) return false;
    final SelectInput other = (SelectInput) o;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getEventMask() != other.getEventMask()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getEventMask();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SelectInput(drawable=" + this.getDrawable() + ", eventMask=" + this.getEventMask() + ")";
  }
}
