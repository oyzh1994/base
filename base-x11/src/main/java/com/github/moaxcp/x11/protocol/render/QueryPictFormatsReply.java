package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.IntList;

public final class QueryPictFormatsReply implements XReply {
  public static final String PLUGIN_NAME = "render";
  private final short sequenceNumber;
  private final int numDepths;
  private final int numVisuals;
  private final ImmutableList<Pictforminfo> formats;
  private final ImmutableList<Pictscreen> screens;
  private final IntList subpixels;

  public static QueryPictFormatsReply readQueryPictFormatsReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryPictFormatsReply.QueryPictFormatsReplyBuilder javaBuilder = QueryPictFormatsReply.builder();
    int length = in.readCard32();
    int numFormats = in.readCard32();
    int numScreens = in.readCard32();
    int numDepths = in.readCard32();
    int numVisuals = in.readCard32();
    int numSubpixel = in.readCard32();
    byte[] pad9 = in.readPad(4);
    MutableList<Pictforminfo> formats = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numFormats)));
    for (int i = 0; i < Integer.toUnsignedLong(numFormats); i++) {
      formats.add(Pictforminfo.readPictforminfo(in));
    }
    MutableList<Pictscreen> screens = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numScreens)));
    for (int i = 0; i < Integer.toUnsignedLong(numScreens); i++) {
      screens.add(Pictscreen.readPictscreen(in));
    }
    IntList subpixels = in.readCard32((int) (Integer.toUnsignedLong(numSubpixel)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.numDepths(numDepths);
    javaBuilder.numVisuals(numVisuals);
    javaBuilder.formats(formats.toImmutable());
    javaBuilder.screens(screens.toImmutable());
    javaBuilder.subpixels(subpixels.toImmutable());
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
    int numFormats = formats.size();
    out.writeCard32(numFormats);
    int numScreens = screens.size();
    out.writeCard32(numScreens);
    out.writeCard32(numDepths);
    out.writeCard32(numVisuals);
    int numSubpixel = subpixels.size();
    out.writeCard32(numSubpixel);
    out.writePad(4);
    for (Pictforminfo t : formats) {
      t.write(out);
    }
    for (Pictscreen t : screens) {
      t.write(out);
    }
    out.writeCard32(subpixels);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(formats) + XObject.sizeOf(screens) + 4 * subpixels.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryPictFormatsReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int numDepths;
    @java.lang.SuppressWarnings("all")
    private int numVisuals;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Pictforminfo> formats;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Pictscreen> screens;
    @java.lang.SuppressWarnings("all")
    private IntList subpixels;

    public int getSize() {
      return 32 + XObject.sizeOf(formats) + XObject.sizeOf(screens) + 4 * subpixels.size();
    }

    @java.lang.SuppressWarnings("all")
    QueryPictFormatsReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPictFormatsReply.QueryPictFormatsReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPictFormatsReply.QueryPictFormatsReplyBuilder numDepths(final int numDepths) {
      this.numDepths = numDepths;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPictFormatsReply.QueryPictFormatsReplyBuilder numVisuals(final int numVisuals) {
      this.numVisuals = numVisuals;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPictFormatsReply.QueryPictFormatsReplyBuilder formats(final ImmutableList<Pictforminfo> formats) {
      if (formats == null) {
        throw new java.lang.NullPointerException("formats is marked non-null but is null");
      }
      this.formats = formats;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPictFormatsReply.QueryPictFormatsReplyBuilder screens(final ImmutableList<Pictscreen> screens) {
      if (screens == null) {
        throw new java.lang.NullPointerException("screens is marked non-null but is null");
      }
      this.screens = screens;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPictFormatsReply.QueryPictFormatsReplyBuilder subpixels(final IntList subpixels) {
      if (subpixels == null) {
        throw new java.lang.NullPointerException("subpixels is marked non-null but is null");
      }
      this.subpixels = subpixels;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryPictFormatsReply build() {
      return new QueryPictFormatsReply(this.sequenceNumber, this.numDepths, this.numVisuals, this.formats, this.screens, this.subpixels);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryPictFormatsReply.QueryPictFormatsReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", numDepths=" + this.numDepths + ", numVisuals=" + this.numVisuals + ", formats=" + this.formats + ", screens=" + this.screens + ", subpixels=" + this.subpixels + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryPictFormatsReply(final short sequenceNumber, final int numDepths, final int numVisuals, final ImmutableList<Pictforminfo> formats, final ImmutableList<Pictscreen> screens, final IntList subpixels) {
    if (formats == null) {
      throw new java.lang.NullPointerException("formats is marked non-null but is null");
    }
    if (screens == null) {
      throw new java.lang.NullPointerException("screens is marked non-null but is null");
    }
    if (subpixels == null) {
      throw new java.lang.NullPointerException("subpixels is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.numDepths = numDepths;
    this.numVisuals = numVisuals;
    this.formats = formats;
    this.screens = screens;
    this.subpixels = subpixels;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryPictFormatsReply.QueryPictFormatsReplyBuilder builder() {
    return new QueryPictFormatsReply.QueryPictFormatsReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getNumDepths() {
    return this.numDepths;
  }

  @java.lang.SuppressWarnings("all")
  public int getNumVisuals() {
    return this.numVisuals;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Pictforminfo> getFormats() {
    return this.formats;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Pictscreen> getScreens() {
    return this.screens;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getSubpixels() {
    return this.subpixels;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryPictFormatsReply)) return false;
    final QueryPictFormatsReply other = (QueryPictFormatsReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getNumDepths() != other.getNumDepths()) return false;
    if (this.getNumVisuals() != other.getNumVisuals()) return false;
    final java.lang.Object this$formats = this.getFormats();
    final java.lang.Object other$formats = other.getFormats();
    if (this$formats == null ? other$formats != null : !this$formats.equals(other$formats)) return false;
    final java.lang.Object this$screens = this.getScreens();
    final java.lang.Object other$screens = other.getScreens();
    if (this$screens == null ? other$screens != null : !this$screens.equals(other$screens)) return false;
    final java.lang.Object this$subpixels = this.getSubpixels();
    final java.lang.Object other$subpixels = other.getSubpixels();
    if (this$subpixels == null ? other$subpixels != null : !this$subpixels.equals(other$subpixels)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getNumDepths();
    result = result * PRIME + this.getNumVisuals();
    final java.lang.Object $formats = this.getFormats();
    result = result * PRIME + ($formats == null ? 43 : $formats.hashCode());
    final java.lang.Object $screens = this.getScreens();
    result = result * PRIME + ($screens == null ? 43 : $screens.hashCode());
    final java.lang.Object $subpixels = this.getSubpixels();
    result = result * PRIME + ($subpixels == null ? 43 : $subpixels.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryPictFormatsReply(sequenceNumber=" + this.getSequenceNumber() + ", numDepths=" + this.getNumDepths() + ", numVisuals=" + this.getNumVisuals() + ", formats=" + this.getFormats() + ", screens=" + this.getScreens() + ", subpixels=" + this.getSubpixels() + ")";
  }
}
