package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class CrtcChange implements NotifyDataUnion, XStruct {
  public static final String PLUGIN_NAME = "randr";
  private final int timestamp;
  private final int window;
  private final int crtc;
  private final int mode;
  private final short rotation;
  private final short x;
  private final short y;
  private final short width;
  private final short height;

  public static CrtcChange readCrtcChange(X11Input in) throws IOException {
    CrtcChange.CrtcChangeBuilder javaBuilder = CrtcChange.builder();
    int timestamp = in.readCard32();
    int window = in.readCard32();
    int crtc = in.readCard32();
    int mode = in.readCard32();
    short rotation = in.readCard16();
    byte[] pad5 = in.readPad(2);
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    javaBuilder.timestamp(timestamp);
    javaBuilder.window(window);
    javaBuilder.crtc(crtc);
    javaBuilder.mode(mode);
    javaBuilder.rotation(rotation);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(timestamp);
    out.writeCard32(window);
    out.writeCard32(crtc);
    out.writeCard32(mode);
    out.writeCard16(rotation);
    out.writePad(2);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
  }

  public boolean isRotationEnabled(Rotation... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Rotation m : maskEnums) {
      if (!m.isEnabled(rotation)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CrtcChangeBuilder {
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int crtc;
    @java.lang.SuppressWarnings("all")
    private int mode;
    @java.lang.SuppressWarnings("all")
    private short rotation;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;

    public boolean isRotationEnabled(Rotation... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Rotation m : maskEnums) {
        if (!m.isEnabled(rotation)) {
          return false;
        }
      }
      return true;
    }

    public CrtcChange.CrtcChangeBuilder rotationEnable(Rotation... maskEnums) {
      for (Rotation m : maskEnums) {
        rotation((short) m.enableFor(rotation));
      }
      return this;
    }

    public CrtcChange.CrtcChangeBuilder rotationDisable(Rotation... maskEnums) {
      for (Rotation m : maskEnums) {
        rotation((short) m.disableFor(rotation));
      }
      return this;
    }

    public int getSize() {
      return 28;
    }

    @java.lang.SuppressWarnings("all")
    CrtcChangeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CrtcChange.CrtcChangeBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CrtcChange.CrtcChangeBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CrtcChange.CrtcChangeBuilder crtc(final int crtc) {
      this.crtc = crtc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CrtcChange.CrtcChangeBuilder mode(final int mode) {
      this.mode = mode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CrtcChange.CrtcChangeBuilder rotation(final short rotation) {
      this.rotation = rotation;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CrtcChange.CrtcChangeBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CrtcChange.CrtcChangeBuilder y(final short y) {
      this.y = y;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CrtcChange.CrtcChangeBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CrtcChange.CrtcChangeBuilder height(final short height) {
      this.height = height;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CrtcChange build() {
      return new CrtcChange(this.timestamp, this.window, this.crtc, this.mode, this.rotation, this.x, this.y, this.width, this.height);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CrtcChange.CrtcChangeBuilder(timestamp=" + this.timestamp + ", window=" + this.window + ", crtc=" + this.crtc + ", mode=" + this.mode + ", rotation=" + this.rotation + ", x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CrtcChange(final int timestamp, final int window, final int crtc, final int mode, final short rotation, final short x, final short y, final short width, final short height) {
    this.timestamp = timestamp;
    this.window = window;
    this.crtc = crtc;
    this.mode = mode;
    this.rotation = rotation;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  @java.lang.SuppressWarnings("all")
  public static CrtcChange.CrtcChangeBuilder builder() {
    return new CrtcChange.CrtcChangeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getTimestamp() {
    return this.timestamp;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getCrtc() {
    return this.crtc;
  }

  @java.lang.SuppressWarnings("all")
  public int getMode() {
    return this.mode;
  }

  @java.lang.SuppressWarnings("all")
  public short getRotation() {
    return this.rotation;
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

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CrtcChange)) return false;
    final CrtcChange other = (CrtcChange) o;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getCrtc() != other.getCrtc()) return false;
    if (this.getMode() != other.getMode()) return false;
    if (this.getRotation() != other.getRotation()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getCrtc();
    result = result * PRIME + this.getMode();
    result = result * PRIME + this.getRotation();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CrtcChange(timestamp=" + this.getTimestamp() + ", window=" + this.getWindow() + ", crtc=" + this.getCrtc() + ", mode=" + this.getMode() + ", rotation=" + this.getRotation() + ", x=" + this.getX() + ", y=" + this.getY() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ")";
  }
}
