package com.github.moaxcp.x11.protocol.present;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SelectInput implements OneWayRequest {
  public static final String PLUGIN_NAME = "present";
  public static final byte OPCODE = 3;
  private final int eid;
  private final int window;
  private final int eventMask;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SelectInput readSelectInput(X11Input in) throws IOException {
    SelectInput.SelectInputBuilder javaBuilder = SelectInput.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int eid = in.readCard32();
    int window = in.readCard32();
    int eventMask = in.readCard32();
    javaBuilder.eid(eid);
    javaBuilder.window(window);
    javaBuilder.eventMask(eventMask);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(eid);
    out.writeCard32(window);
    out.writeCard32(eventMask);
  }

  public boolean isEventMaskEnabled(EventMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (EventMask m : maskEnums) {
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


  public static class SelectInputBuilder {
    @java.lang.SuppressWarnings("all")
    private int eid;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int eventMask;

    public boolean isEventMaskEnabled(EventMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (EventMask m : maskEnums) {
        if (!m.isEnabled(eventMask)) {
          return false;
        }
      }
      return true;
    }

    public SelectInput.SelectInputBuilder eventMaskEnable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        eventMask((int) m.enableFor(eventMask));
      }
      return this;
    }

    public SelectInput.SelectInputBuilder eventMaskDisable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        eventMask((int) m.disableFor(eventMask));
      }
      return this;
    }

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    SelectInputBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectInput.SelectInputBuilder eid(final int eid) {
      this.eid = eid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectInput.SelectInputBuilder window(final int window) {
      this.window = window;
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
      return new SelectInput(this.eid, this.window, this.eventMask);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SelectInput.SelectInputBuilder(eid=" + this.eid + ", window=" + this.window + ", eventMask=" + this.eventMask + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SelectInput(final int eid, final int window, final int eventMask) {
    this.eid = eid;
    this.window = window;
    this.eventMask = eventMask;
  }

  @java.lang.SuppressWarnings("all")
  public static SelectInput.SelectInputBuilder builder() {
    return new SelectInput.SelectInputBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getEid() {
    return this.eid;
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
    if (!(o instanceof SelectInput)) return false;
    final SelectInput other = (SelectInput) o;
    if (this.getEid() != other.getEid()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getEventMask() != other.getEventMask()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getEid();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getEventMask();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SelectInput(eid=" + this.getEid() + ", window=" + this.getWindow() + ", eventMask=" + this.getEventMask() + ")";
  }
}
