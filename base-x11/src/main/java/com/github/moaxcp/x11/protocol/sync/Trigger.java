package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Trigger implements XStruct {
  public static final String PLUGIN_NAME = "sync";
  private final int counter;
  private final int waitType;
  private final Int64 waitValue;
  private final int testType;

  public static Trigger readTrigger(X11Input in) throws IOException {
    Trigger.TriggerBuilder javaBuilder = Trigger.builder();
    int counter = in.readCard32();
    int waitType = in.readCard32();
    Int64 waitValue = Int64.readInt64(in);
    int testType = in.readCard32();
    javaBuilder.counter(counter);
    javaBuilder.waitType(waitType);
    javaBuilder.waitValue(waitValue);
    javaBuilder.testType(testType);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(counter);
    out.writeCard32(waitType);
    waitValue.write(out);
    out.writeCard32(testType);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class TriggerBuilder {
    @java.lang.SuppressWarnings("all")
    private int counter;
    @java.lang.SuppressWarnings("all")
    private int waitType;
    @java.lang.SuppressWarnings("all")
    private Int64 waitValue;
    @java.lang.SuppressWarnings("all")
    private int testType;

    public Trigger.TriggerBuilder waitType(Valuetype waitType) {
      this.waitType = (int) waitType.getValue();
      return this;
    }

    public Trigger.TriggerBuilder waitType(int waitType) {
      this.waitType = waitType;
      return this;
    }

    public Trigger.TriggerBuilder testType(Testtype testType) {
      this.testType = (int) testType.getValue();
      return this;
    }

    public Trigger.TriggerBuilder testType(int testType) {
      this.testType = testType;
      return this;
    }

    public int getSize() {
      return 20;
    }

    @java.lang.SuppressWarnings("all")
    TriggerBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Trigger.TriggerBuilder counter(final int counter) {
      this.counter = counter;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Trigger.TriggerBuilder waitValue(final Int64 waitValue) {
      if (waitValue == null) {
        throw new java.lang.NullPointerException("waitValue is marked non-null but is null");
      }
      this.waitValue = waitValue;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Trigger build() {
      return new Trigger(this.counter, this.waitType, this.waitValue, this.testType);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Trigger.TriggerBuilder(counter=" + this.counter + ", waitType=" + this.waitType + ", waitValue=" + this.waitValue + ", testType=" + this.testType + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Trigger(final int counter, final int waitType, final Int64 waitValue, final int testType) {
    if (waitValue == null) {
      throw new java.lang.NullPointerException("waitValue is marked non-null but is null");
    }
    this.counter = counter;
    this.waitType = waitType;
    this.waitValue = waitValue;
    this.testType = testType;
  }

  @java.lang.SuppressWarnings("all")
  public static Trigger.TriggerBuilder builder() {
    return new Trigger.TriggerBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCounter() {
    return this.counter;
  }

  @java.lang.SuppressWarnings("all")
  public int getWaitType() {
    return this.waitType;
  }

  @java.lang.SuppressWarnings("all")
  public Int64 getWaitValue() {
    return this.waitValue;
  }

  @java.lang.SuppressWarnings("all")
  public int getTestType() {
    return this.testType;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Trigger)) return false;
    final Trigger other = (Trigger) o;
    if (this.getCounter() != other.getCounter()) return false;
    if (this.getWaitType() != other.getWaitType()) return false;
    if (this.getTestType() != other.getTestType()) return false;
    final java.lang.Object this$waitValue = this.getWaitValue();
    final java.lang.Object other$waitValue = other.getWaitValue();
    if (this$waitValue == null ? other$waitValue != null : !this$waitValue.equals(other$waitValue)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCounter();
    result = result * PRIME + this.getWaitType();
    result = result * PRIME + this.getTestType();
    final java.lang.Object $waitValue = this.getWaitValue();
    result = result * PRIME + ($waitValue == null ? 43 : $waitValue.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Trigger(counter=" + this.getCounter() + ", waitType=" + this.getWaitType() + ", waitValue=" + this.getWaitValue() + ", testType=" + this.getTestType() + ")";
  }
}
