package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Trap implements XStruct {
  public static final String PLUGIN_NAME = "render";
  private final Spanfix top;
  private final Spanfix bot;

  public static Trap readTrap(X11Input in) throws IOException {
    Trap.TrapBuilder javaBuilder = Trap.builder();
    Spanfix top = Spanfix.readSpanfix(in);
    Spanfix bot = Spanfix.readSpanfix(in);
    javaBuilder.top(top);
    javaBuilder.bot(bot);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    top.write(out);
    bot.write(out);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class TrapBuilder {
    @java.lang.SuppressWarnings("all")
    private Spanfix top;
    @java.lang.SuppressWarnings("all")
    private Spanfix bot;

    public int getSize() {
      return 24;
    }

    @java.lang.SuppressWarnings("all")
    TrapBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Trap.TrapBuilder top(final Spanfix top) {
      if (top == null) {
        throw new java.lang.NullPointerException("top is marked non-null but is null");
      }
      this.top = top;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Trap.TrapBuilder bot(final Spanfix bot) {
      if (bot == null) {
        throw new java.lang.NullPointerException("bot is marked non-null but is null");
      }
      this.bot = bot;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Trap build() {
      return new Trap(this.top, this.bot);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Trap.TrapBuilder(top=" + this.top + ", bot=" + this.bot + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Trap(final Spanfix top, final Spanfix bot) {
    if (top == null) {
      throw new java.lang.NullPointerException("top is marked non-null but is null");
    }
    if (bot == null) {
      throw new java.lang.NullPointerException("bot is marked non-null but is null");
    }
    this.top = top;
    this.bot = bot;
  }

  @java.lang.SuppressWarnings("all")
  public static Trap.TrapBuilder builder() {
    return new Trap.TrapBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public Spanfix getTop() {
    return this.top;
  }

  @java.lang.SuppressWarnings("all")
  public Spanfix getBot() {
    return this.bot;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Trap)) return false;
    final Trap other = (Trap) o;
    final java.lang.Object this$top = this.getTop();
    final java.lang.Object other$top = other.getTop();
    if (this$top == null ? other$top != null : !this$top.equals(other$top)) return false;
    final java.lang.Object this$bot = this.getBot();
    final java.lang.Object other$bot = other.getBot();
    if (this$bot == null ? other$bot != null : !this$bot.equals(other$bot)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $top = this.getTop();
    result = result * PRIME + ($top == null ? 43 : $top.hashCode());
    final java.lang.Object $bot = this.getBot();
    result = result * PRIME + ($bot == null ? 43 : $bot.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Trap(top=" + this.getTop() + ", bot=" + this.getBot() + ")";
  }
}
