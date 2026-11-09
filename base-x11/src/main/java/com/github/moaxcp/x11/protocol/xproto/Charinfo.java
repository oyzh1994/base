package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Charinfo implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final short leftSideBearing;
  private final short rightSideBearing;
  private final short characterWidth;
  private final short ascent;
  private final short descent;
  private final short attributes;

  public static Charinfo readCharinfo(X11Input in) throws IOException {
    Charinfo.CharinfoBuilder javaBuilder = Charinfo.builder();
    short leftSideBearing = in.readInt16();
    short rightSideBearing = in.readInt16();
    short characterWidth = in.readInt16();
    short ascent = in.readInt16();
    short descent = in.readInt16();
    short attributes = in.readCard16();
    javaBuilder.leftSideBearing(leftSideBearing);
    javaBuilder.rightSideBearing(rightSideBearing);
    javaBuilder.characterWidth(characterWidth);
    javaBuilder.ascent(ascent);
    javaBuilder.descent(descent);
    javaBuilder.attributes(attributes);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt16(leftSideBearing);
    out.writeInt16(rightSideBearing);
    out.writeInt16(characterWidth);
    out.writeInt16(ascent);
    out.writeInt16(descent);
    out.writeCard16(attributes);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CharinfoBuilder {
    @java.lang.SuppressWarnings("all")
    private short leftSideBearing;
    @java.lang.SuppressWarnings("all")
    private short rightSideBearing;
    @java.lang.SuppressWarnings("all")
    private short characterWidth;
    @java.lang.SuppressWarnings("all")
    private short ascent;
    @java.lang.SuppressWarnings("all")
    private short descent;
    @java.lang.SuppressWarnings("all")
    private short attributes;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    CharinfoBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Charinfo.CharinfoBuilder leftSideBearing(final short leftSideBearing) {
      this.leftSideBearing = leftSideBearing;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Charinfo.CharinfoBuilder rightSideBearing(final short rightSideBearing) {
      this.rightSideBearing = rightSideBearing;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Charinfo.CharinfoBuilder characterWidth(final short characterWidth) {
      this.characterWidth = characterWidth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Charinfo.CharinfoBuilder ascent(final short ascent) {
      this.ascent = ascent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Charinfo.CharinfoBuilder descent(final short descent) {
      this.descent = descent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Charinfo.CharinfoBuilder attributes(final short attributes) {
      this.attributes = attributes;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Charinfo build() {
      return new Charinfo(this.leftSideBearing, this.rightSideBearing, this.characterWidth, this.ascent, this.descent, this.attributes);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Charinfo.CharinfoBuilder(leftSideBearing=" + this.leftSideBearing + ", rightSideBearing=" + this.rightSideBearing + ", characterWidth=" + this.characterWidth + ", ascent=" + this.ascent + ", descent=" + this.descent + ", attributes=" + this.attributes + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Charinfo(final short leftSideBearing, final short rightSideBearing, final short characterWidth, final short ascent, final short descent, final short attributes) {
    this.leftSideBearing = leftSideBearing;
    this.rightSideBearing = rightSideBearing;
    this.characterWidth = characterWidth;
    this.ascent = ascent;
    this.descent = descent;
    this.attributes = attributes;
  }

  @java.lang.SuppressWarnings("all")
  public static Charinfo.CharinfoBuilder builder() {
    return new Charinfo.CharinfoBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getLeftSideBearing() {
    return this.leftSideBearing;
  }

  @java.lang.SuppressWarnings("all")
  public short getRightSideBearing() {
    return this.rightSideBearing;
  }

  @java.lang.SuppressWarnings("all")
  public short getCharacterWidth() {
    return this.characterWidth;
  }

  @java.lang.SuppressWarnings("all")
  public short getAscent() {
    return this.ascent;
  }

  @java.lang.SuppressWarnings("all")
  public short getDescent() {
    return this.descent;
  }

  @java.lang.SuppressWarnings("all")
  public short getAttributes() {
    return this.attributes;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Charinfo)) return false;
    final Charinfo other = (Charinfo) o;
    if (this.getLeftSideBearing() != other.getLeftSideBearing()) return false;
    if (this.getRightSideBearing() != other.getRightSideBearing()) return false;
    if (this.getCharacterWidth() != other.getCharacterWidth()) return false;
    if (this.getAscent() != other.getAscent()) return false;
    if (this.getDescent() != other.getDescent()) return false;
    if (this.getAttributes() != other.getAttributes()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getLeftSideBearing();
    result = result * PRIME + this.getRightSideBearing();
    result = result * PRIME + this.getCharacterWidth();
    result = result * PRIME + this.getAscent();
    result = result * PRIME + this.getDescent();
    result = result * PRIME + this.getAttributes();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Charinfo(leftSideBearing=" + this.getLeftSideBearing() + ", rightSideBearing=" + this.getRightSideBearing() + ", characterWidth=" + this.getCharacterWidth() + ", ascent=" + this.getAscent() + ", descent=" + this.getDescent() + ", attributes=" + this.getAttributes() + ")";
  }
}
