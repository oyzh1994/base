package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Waitcondition implements XStruct {
  public static final String PLUGIN_NAME = "sync";
  private final Trigger trigger;
  private final Int64 eventThreshold;

  public static Waitcondition readWaitcondition(X11Input in) throws IOException {
    Waitcondition.WaitconditionBuilder javaBuilder = Waitcondition.builder();
    Trigger trigger = Trigger.readTrigger(in);
    Int64 eventThreshold = Int64.readInt64(in);
    javaBuilder.trigger(trigger);
    javaBuilder.eventThreshold(eventThreshold);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    trigger.write(out);
    eventThreshold.write(out);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class WaitconditionBuilder {
    @java.lang.SuppressWarnings("all")
    private Trigger trigger;
    @java.lang.SuppressWarnings("all")
    private Int64 eventThreshold;

    public int getSize() {
      return 28;
    }

    @java.lang.SuppressWarnings("all")
    WaitconditionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Waitcondition.WaitconditionBuilder trigger(final Trigger trigger) {
      if (trigger == null) {
        throw new java.lang.NullPointerException("trigger is marked non-null but is null");
      }
      this.trigger = trigger;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Waitcondition.WaitconditionBuilder eventThreshold(final Int64 eventThreshold) {
      if (eventThreshold == null) {
        throw new java.lang.NullPointerException("eventThreshold is marked non-null but is null");
      }
      this.eventThreshold = eventThreshold;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Waitcondition build() {
      return new Waitcondition(this.trigger, this.eventThreshold);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Waitcondition.WaitconditionBuilder(trigger=" + this.trigger + ", eventThreshold=" + this.eventThreshold + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Waitcondition(final Trigger trigger, final Int64 eventThreshold) {
    if (trigger == null) {
      throw new java.lang.NullPointerException("trigger is marked non-null but is null");
    }
    if (eventThreshold == null) {
      throw new java.lang.NullPointerException("eventThreshold is marked non-null but is null");
    }
    this.trigger = trigger;
    this.eventThreshold = eventThreshold;
  }

  @java.lang.SuppressWarnings("all")
  public static Waitcondition.WaitconditionBuilder builder() {
    return new Waitcondition.WaitconditionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public Trigger getTrigger() {
    return this.trigger;
  }

  @java.lang.SuppressWarnings("all")
  public Int64 getEventThreshold() {
    return this.eventThreshold;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Waitcondition)) return false;
    final Waitcondition other = (Waitcondition) o;
    final java.lang.Object this$trigger = this.getTrigger();
    final java.lang.Object other$trigger = other.getTrigger();
    if (this$trigger == null ? other$trigger != null : !this$trigger.equals(other$trigger)) return false;
    final java.lang.Object this$eventThreshold = this.getEventThreshold();
    final java.lang.Object other$eventThreshold = other.getEventThreshold();
    if (this$eventThreshold == null ? other$eventThreshold != null : !this$eventThreshold.equals(other$eventThreshold)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $trigger = this.getTrigger();
    result = result * PRIME + ($trigger == null ? 43 : $trigger.hashCode());
    final java.lang.Object $eventThreshold = this.getEventThreshold();
    result = result * PRIME + ($eventThreshold == null ? 43 : $eventThreshold.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Waitcondition(trigger=" + this.getTrigger() + ", eventThreshold=" + this.getEventThreshold() + ")";
  }
}
