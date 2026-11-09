package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class ListFontsWithInfoReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final Charinfo minBounds;
  private final Charinfo maxBounds;
  private final short minCharOrByte2;
  private final short maxCharOrByte2;
  private final short defaultChar;
  private final byte drawDirection;
  private final byte minByte1;
  private final byte maxByte1;
  private final boolean allCharsExist;
  private final short fontAscent;
  private final short fontDescent;
  private final int repliesHint;
  private final ImmutableList<Fontprop> properties;
  private final ByteList name;

  public static ListFontsWithInfoReply readListFontsWithInfoReply(byte nameLen, short sequenceNumber, X11Input in) throws IOException {
    ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder javaBuilder = ListFontsWithInfoReply.builder();
    int length = in.readCard32();
    Charinfo minBounds = Charinfo.readCharinfo(in);
    byte[] pad5 = in.readPad(4);
    Charinfo maxBounds = Charinfo.readCharinfo(in);
    byte[] pad7 = in.readPad(4);
    short minCharOrByte2 = in.readCard16();
    short maxCharOrByte2 = in.readCard16();
    short defaultChar = in.readCard16();
    short propertiesLen = in.readCard16();
    byte drawDirection = in.readByte();
    byte minByte1 = in.readCard8();
    byte maxByte1 = in.readCard8();
    boolean allCharsExist = in.readBool();
    short fontAscent = in.readInt16();
    short fontDescent = in.readInt16();
    int repliesHint = in.readCard32();
    MutableList<Fontprop> properties = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(propertiesLen));
    for (int i = 0; i < Short.toUnsignedInt(propertiesLen); i++) {
      properties.add(Fontprop.readFontprop(in));
    }
    ByteList name = in.readChar(Byte.toUnsignedInt(nameLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.minBounds(minBounds);
    javaBuilder.maxBounds(maxBounds);
    javaBuilder.minCharOrByte2(minCharOrByte2);
    javaBuilder.maxCharOrByte2(maxCharOrByte2);
    javaBuilder.defaultChar(defaultChar);
    javaBuilder.drawDirection(drawDirection);
    javaBuilder.minByte1(minByte1);
    javaBuilder.maxByte1(maxByte1);
    javaBuilder.allCharsExist(allCharsExist);
    javaBuilder.fontAscent(fontAscent);
    javaBuilder.fontDescent(fontDescent);
    javaBuilder.repliesHint(repliesHint);
    javaBuilder.properties(properties.toImmutable());
    javaBuilder.name(name.toImmutable());
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    byte nameLen = (byte) name.size();
    out.writeCard8(nameLen);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    minBounds.write(out);
    out.writePad(4);
    maxBounds.write(out);
    out.writePad(4);
    out.writeCard16(minCharOrByte2);
    out.writeCard16(maxCharOrByte2);
    out.writeCard16(defaultChar);
    short propertiesLen = (short) properties.size();
    out.writeCard16(propertiesLen);
    out.writeByte(drawDirection);
    out.writeCard8(minByte1);
    out.writeCard8(maxByte1);
    out.writeBool(allCharsExist);
    out.writeInt16(fontAscent);
    out.writeInt16(fontDescent);
    out.writeCard32(repliesHint);
    for (Fontprop t : properties) {
      t.write(out);
    }
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 60 + XObject.sizeOf(properties) + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ListFontsWithInfoReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private Charinfo minBounds;
    @java.lang.SuppressWarnings("all")
    private Charinfo maxBounds;
    @java.lang.SuppressWarnings("all")
    private short minCharOrByte2;
    @java.lang.SuppressWarnings("all")
    private short maxCharOrByte2;
    @java.lang.SuppressWarnings("all")
    private short defaultChar;
    @java.lang.SuppressWarnings("all")
    private byte drawDirection;
    @java.lang.SuppressWarnings("all")
    private byte minByte1;
    @java.lang.SuppressWarnings("all")
    private byte maxByte1;
    @java.lang.SuppressWarnings("all")
    private boolean allCharsExist;
    @java.lang.SuppressWarnings("all")
    private short fontAscent;
    @java.lang.SuppressWarnings("all")
    private short fontDescent;
    @java.lang.SuppressWarnings("all")
    private int repliesHint;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Fontprop> properties;
    @java.lang.SuppressWarnings("all")
    private ByteList name;

    public ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder drawDirection(FontDraw drawDirection) {
      this.drawDirection = (byte) drawDirection.getValue();
      return this;
    }

    public ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder drawDirection(byte drawDirection) {
      this.drawDirection = drawDirection;
      return this;
    }

    public int getSize() {
      return 60 + XObject.sizeOf(properties) + 1 * name.size();
    }

    @java.lang.SuppressWarnings("all")
    ListFontsWithInfoReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder minBounds(final Charinfo minBounds) {
      if (minBounds == null) {
        throw new java.lang.NullPointerException("minBounds is marked non-null but is null");
      }
      this.minBounds = minBounds;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder maxBounds(final Charinfo maxBounds) {
      if (maxBounds == null) {
        throw new java.lang.NullPointerException("maxBounds is marked non-null but is null");
      }
      this.maxBounds = maxBounds;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder minCharOrByte2(final short minCharOrByte2) {
      this.minCharOrByte2 = minCharOrByte2;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder maxCharOrByte2(final short maxCharOrByte2) {
      this.maxCharOrByte2 = maxCharOrByte2;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder defaultChar(final short defaultChar) {
      this.defaultChar = defaultChar;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder minByte1(final byte minByte1) {
      this.minByte1 = minByte1;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder maxByte1(final byte maxByte1) {
      this.maxByte1 = maxByte1;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder allCharsExist(final boolean allCharsExist) {
      this.allCharsExist = allCharsExist;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder fontAscent(final short fontAscent) {
      this.fontAscent = fontAscent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder fontDescent(final short fontDescent) {
      this.fontDescent = fontDescent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder repliesHint(final int repliesHint) {
      this.repliesHint = repliesHint;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder properties(final ImmutableList<Fontprop> properties) {
      if (properties == null) {
        throw new java.lang.NullPointerException("properties is marked non-null but is null");
      }
      this.properties = properties;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder name(final ByteList name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ListFontsWithInfoReply build() {
      return new ListFontsWithInfoReply(this.sequenceNumber, this.minBounds, this.maxBounds, this.minCharOrByte2, this.maxCharOrByte2, this.defaultChar, this.drawDirection, this.minByte1, this.maxByte1, this.allCharsExist, this.fontAscent, this.fontDescent, this.repliesHint, this.properties, this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", minBounds=" + this.minBounds + ", maxBounds=" + this.maxBounds + ", minCharOrByte2=" + this.minCharOrByte2 + ", maxCharOrByte2=" + this.maxCharOrByte2 + ", defaultChar=" + this.defaultChar + ", drawDirection=" + this.drawDirection + ", minByte1=" + this.minByte1 + ", maxByte1=" + this.maxByte1 + ", allCharsExist=" + this.allCharsExist + ", fontAscent=" + this.fontAscent + ", fontDescent=" + this.fontDescent + ", repliesHint=" + this.repliesHint + ", properties=" + this.properties + ", name=" + this.name + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ListFontsWithInfoReply(final short sequenceNumber, final Charinfo minBounds, final Charinfo maxBounds, final short minCharOrByte2, final short maxCharOrByte2, final short defaultChar, final byte drawDirection, final byte minByte1, final byte maxByte1, final boolean allCharsExist, final short fontAscent, final short fontDescent, final int repliesHint, final ImmutableList<Fontprop> properties, final ByteList name) {
    if (minBounds == null) {
      throw new java.lang.NullPointerException("minBounds is marked non-null but is null");
    }
    if (maxBounds == null) {
      throw new java.lang.NullPointerException("maxBounds is marked non-null but is null");
    }
    if (properties == null) {
      throw new java.lang.NullPointerException("properties is marked non-null but is null");
    }
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.minBounds = minBounds;
    this.maxBounds = maxBounds;
    this.minCharOrByte2 = minCharOrByte2;
    this.maxCharOrByte2 = maxCharOrByte2;
    this.defaultChar = defaultChar;
    this.drawDirection = drawDirection;
    this.minByte1 = minByte1;
    this.maxByte1 = maxByte1;
    this.allCharsExist = allCharsExist;
    this.fontAscent = fontAscent;
    this.fontDescent = fontDescent;
    this.repliesHint = repliesHint;
    this.properties = properties;
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder builder() {
    return new ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public Charinfo getMinBounds() {
    return this.minBounds;
  }

  @java.lang.SuppressWarnings("all")
  public Charinfo getMaxBounds() {
    return this.maxBounds;
  }

  @java.lang.SuppressWarnings("all")
  public short getMinCharOrByte2() {
    return this.minCharOrByte2;
  }

  @java.lang.SuppressWarnings("all")
  public short getMaxCharOrByte2() {
    return this.maxCharOrByte2;
  }

  @java.lang.SuppressWarnings("all")
  public short getDefaultChar() {
    return this.defaultChar;
  }

  @java.lang.SuppressWarnings("all")
  public byte getDrawDirection() {
    return this.drawDirection;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMinByte1() {
    return this.minByte1;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMaxByte1() {
    return this.maxByte1;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isAllCharsExist() {
    return this.allCharsExist;
  }

  @java.lang.SuppressWarnings("all")
  public short getFontAscent() {
    return this.fontAscent;
  }

  @java.lang.SuppressWarnings("all")
  public short getFontDescent() {
    return this.fontDescent;
  }

  @java.lang.SuppressWarnings("all")
  public int getRepliesHint() {
    return this.repliesHint;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Fontprop> getProperties() {
    return this.properties;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ListFontsWithInfoReply)) return false;
    final ListFontsWithInfoReply other = (ListFontsWithInfoReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getMinCharOrByte2() != other.getMinCharOrByte2()) return false;
    if (this.getMaxCharOrByte2() != other.getMaxCharOrByte2()) return false;
    if (this.getDefaultChar() != other.getDefaultChar()) return false;
    if (this.getDrawDirection() != other.getDrawDirection()) return false;
    if (this.getMinByte1() != other.getMinByte1()) return false;
    if (this.getMaxByte1() != other.getMaxByte1()) return false;
    if (this.isAllCharsExist() != other.isAllCharsExist()) return false;
    if (this.getFontAscent() != other.getFontAscent()) return false;
    if (this.getFontDescent() != other.getFontDescent()) return false;
    if (this.getRepliesHint() != other.getRepliesHint()) return false;
    final java.lang.Object this$minBounds = this.getMinBounds();
    final java.lang.Object other$minBounds = other.getMinBounds();
    if (this$minBounds == null ? other$minBounds != null : !this$minBounds.equals(other$minBounds)) return false;
    final java.lang.Object this$maxBounds = this.getMaxBounds();
    final java.lang.Object other$maxBounds = other.getMaxBounds();
    if (this$maxBounds == null ? other$maxBounds != null : !this$maxBounds.equals(other$maxBounds)) return false;
    final java.lang.Object this$properties = this.getProperties();
    final java.lang.Object other$properties = other.getProperties();
    if (this$properties == null ? other$properties != null : !this$properties.equals(other$properties)) return false;
    final java.lang.Object this$name = this.getName();
    final java.lang.Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getMinCharOrByte2();
    result = result * PRIME + this.getMaxCharOrByte2();
    result = result * PRIME + this.getDefaultChar();
    result = result * PRIME + this.getDrawDirection();
    result = result * PRIME + this.getMinByte1();
    result = result * PRIME + this.getMaxByte1();
    result = result * PRIME + (this.isAllCharsExist() ? 79 : 97);
    result = result * PRIME + this.getFontAscent();
    result = result * PRIME + this.getFontDescent();
    result = result * PRIME + this.getRepliesHint();
    final java.lang.Object $minBounds = this.getMinBounds();
    result = result * PRIME + ($minBounds == null ? 43 : $minBounds.hashCode());
    final java.lang.Object $maxBounds = this.getMaxBounds();
    result = result * PRIME + ($maxBounds == null ? 43 : $maxBounds.hashCode());
    final java.lang.Object $properties = this.getProperties();
    result = result * PRIME + ($properties == null ? 43 : $properties.hashCode());
    final java.lang.Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ListFontsWithInfoReply(sequenceNumber=" + this.getSequenceNumber() + ", minBounds=" + this.getMinBounds() + ", maxBounds=" + this.getMaxBounds() + ", minCharOrByte2=" + this.getMinCharOrByte2() + ", maxCharOrByte2=" + this.getMaxCharOrByte2() + ", defaultChar=" + this.getDefaultChar() + ", drawDirection=" + this.getDrawDirection() + ", minByte1=" + this.getMinByte1() + ", maxByte1=" + this.getMaxByte1() + ", allCharsExist=" + this.isAllCharsExist() + ", fontAscent=" + this.getFontAscent() + ", fontDescent=" + this.getFontDescent() + ", repliesHint=" + this.getRepliesHint() + ", properties=" + this.getProperties() + ", name=" + this.getName() + ")";
  }
}
