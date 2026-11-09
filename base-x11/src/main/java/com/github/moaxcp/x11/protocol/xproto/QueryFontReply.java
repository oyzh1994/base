package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class QueryFontReply implements XReply {
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
  private final ImmutableList<Fontprop> properties;
  private final ImmutableList<Charinfo> charInfos;

  public static QueryFontReply readQueryFontReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryFontReply.QueryFontReplyBuilder javaBuilder = QueryFontReply.builder();
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
    int charInfosLen = in.readCard32();
    MutableList<Fontprop> properties = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(propertiesLen));
    for (int i = 0; i < Short.toUnsignedInt(propertiesLen); i++) {
      properties.add(Fontprop.readFontprop(in));
    }
    MutableList<Charinfo> charInfos = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(charInfosLen)));
    for (int i = 0; i < Integer.toUnsignedLong(charInfosLen); i++) {
      charInfos.add(Charinfo.readCharinfo(in));
    }
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
    javaBuilder.properties(properties.toImmutable());
    javaBuilder.charInfos(charInfos.toImmutable());
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
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
    int charInfosLen = charInfos.size();
    out.writeCard32(charInfosLen);
    for (Fontprop t : properties) {
      t.write(out);
    }
    for (Charinfo t : charInfos) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 60 + XObject.sizeOf(properties) + XObject.sizeOf(charInfos);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryFontReplyBuilder {
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
    private ImmutableList<Fontprop> properties;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Charinfo> charInfos;

    public QueryFontReply.QueryFontReplyBuilder drawDirection(FontDraw drawDirection) {
      this.drawDirection = (byte) drawDirection.getValue();
      return this;
    }

    public QueryFontReply.QueryFontReplyBuilder drawDirection(byte drawDirection) {
      this.drawDirection = drawDirection;
      return this;
    }

    public int getSize() {
      return 60 + XObject.sizeOf(properties) + XObject.sizeOf(charInfos);
    }

    @java.lang.SuppressWarnings("all")
    QueryFontReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryFontReply.QueryFontReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryFontReply.QueryFontReplyBuilder minBounds(final Charinfo minBounds) {
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
    public QueryFontReply.QueryFontReplyBuilder maxBounds(final Charinfo maxBounds) {
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
    public QueryFontReply.QueryFontReplyBuilder minCharOrByte2(final short minCharOrByte2) {
      this.minCharOrByte2 = minCharOrByte2;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryFontReply.QueryFontReplyBuilder maxCharOrByte2(final short maxCharOrByte2) {
      this.maxCharOrByte2 = maxCharOrByte2;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryFontReply.QueryFontReplyBuilder defaultChar(final short defaultChar) {
      this.defaultChar = defaultChar;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryFontReply.QueryFontReplyBuilder minByte1(final byte minByte1) {
      this.minByte1 = minByte1;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryFontReply.QueryFontReplyBuilder maxByte1(final byte maxByte1) {
      this.maxByte1 = maxByte1;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryFontReply.QueryFontReplyBuilder allCharsExist(final boolean allCharsExist) {
      this.allCharsExist = allCharsExist;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryFontReply.QueryFontReplyBuilder fontAscent(final short fontAscent) {
      this.fontAscent = fontAscent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryFontReply.QueryFontReplyBuilder fontDescent(final short fontDescent) {
      this.fontDescent = fontDescent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryFontReply.QueryFontReplyBuilder properties(final ImmutableList<Fontprop> properties) {
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
    public QueryFontReply.QueryFontReplyBuilder charInfos(final ImmutableList<Charinfo> charInfos) {
      if (charInfos == null) {
        throw new java.lang.NullPointerException("charInfos is marked non-null but is null");
      }
      this.charInfos = charInfos;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryFontReply build() {
      return new QueryFontReply(this.sequenceNumber, this.minBounds, this.maxBounds, this.minCharOrByte2, this.maxCharOrByte2, this.defaultChar, this.drawDirection, this.minByte1, this.maxByte1, this.allCharsExist, this.fontAscent, this.fontDescent, this.properties, this.charInfos);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryFontReply.QueryFontReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", minBounds=" + this.minBounds + ", maxBounds=" + this.maxBounds + ", minCharOrByte2=" + this.minCharOrByte2 + ", maxCharOrByte2=" + this.maxCharOrByte2 + ", defaultChar=" + this.defaultChar + ", drawDirection=" + this.drawDirection + ", minByte1=" + this.minByte1 + ", maxByte1=" + this.maxByte1 + ", allCharsExist=" + this.allCharsExist + ", fontAscent=" + this.fontAscent + ", fontDescent=" + this.fontDescent + ", properties=" + this.properties + ", charInfos=" + this.charInfos + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryFontReply(final short sequenceNumber, final Charinfo minBounds, final Charinfo maxBounds, final short minCharOrByte2, final short maxCharOrByte2, final short defaultChar, final byte drawDirection, final byte minByte1, final byte maxByte1, final boolean allCharsExist, final short fontAscent, final short fontDescent, final ImmutableList<Fontprop> properties, final ImmutableList<Charinfo> charInfos) {
    if (minBounds == null) {
      throw new java.lang.NullPointerException("minBounds is marked non-null but is null");
    }
    if (maxBounds == null) {
      throw new java.lang.NullPointerException("maxBounds is marked non-null but is null");
    }
    if (properties == null) {
      throw new java.lang.NullPointerException("properties is marked non-null but is null");
    }
    if (charInfos == null) {
      throw new java.lang.NullPointerException("charInfos is marked non-null but is null");
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
    this.properties = properties;
    this.charInfos = charInfos;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryFontReply.QueryFontReplyBuilder builder() {
    return new QueryFontReply.QueryFontReplyBuilder();
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
  public ImmutableList<Fontprop> getProperties() {
    return this.properties;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Charinfo> getCharInfos() {
    return this.charInfos;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryFontReply)) return false;
    final QueryFontReply other = (QueryFontReply) o;
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
    final java.lang.Object this$minBounds = this.getMinBounds();
    final java.lang.Object other$minBounds = other.getMinBounds();
    if (this$minBounds == null ? other$minBounds != null : !this$minBounds.equals(other$minBounds)) return false;
    final java.lang.Object this$maxBounds = this.getMaxBounds();
    final java.lang.Object other$maxBounds = other.getMaxBounds();
    if (this$maxBounds == null ? other$maxBounds != null : !this$maxBounds.equals(other$maxBounds)) return false;
    final java.lang.Object this$properties = this.getProperties();
    final java.lang.Object other$properties = other.getProperties();
    if (this$properties == null ? other$properties != null : !this$properties.equals(other$properties)) return false;
    final java.lang.Object this$charInfos = this.getCharInfos();
    final java.lang.Object other$charInfos = other.getCharInfos();
    if (this$charInfos == null ? other$charInfos != null : !this$charInfos.equals(other$charInfos)) return false;
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
    final java.lang.Object $minBounds = this.getMinBounds();
    result = result * PRIME + ($minBounds == null ? 43 : $minBounds.hashCode());
    final java.lang.Object $maxBounds = this.getMaxBounds();
    result = result * PRIME + ($maxBounds == null ? 43 : $maxBounds.hashCode());
    final java.lang.Object $properties = this.getProperties();
    result = result * PRIME + ($properties == null ? 43 : $properties.hashCode());
    final java.lang.Object $charInfos = this.getCharInfos();
    result = result * PRIME + ($charInfos == null ? 43 : $charInfos.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryFontReply(sequenceNumber=" + this.getSequenceNumber() + ", minBounds=" + this.getMinBounds() + ", maxBounds=" + this.getMaxBounds() + ", minCharOrByte2=" + this.getMinCharOrByte2() + ", maxCharOrByte2=" + this.getMaxCharOrByte2() + ", defaultChar=" + this.getDefaultChar() + ", drawDirection=" + this.getDrawDirection() + ", minByte1=" + this.getMinByte1() + ", maxByte1=" + this.getMaxByte1() + ", allCharsExist=" + this.isAllCharsExist() + ", fontAscent=" + this.getFontAscent() + ", fontDescent=" + this.getFontDescent() + ", properties=" + this.getProperties() + ", charInfos=" + this.getCharInfos() + ")";
  }
}
