package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CreateAlarm implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";
  public static final byte OPCODE = 8;
  private final int id;
  private final int valueMask;
  private final int counter;
  private final int valueType;
  private final Int64 value;
  private final int testType;
  private final Int64 delta;
  private final int events;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateAlarm readCreateAlarm(X11Input in) throws IOException {
    CreateAlarm.CreateAlarmBuilder javaBuilder = CreateAlarm.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int id = in.readCard32();
    int valueMask = in.readCard32();
    int counter = 0;
    int valueType = 0;
    Int64 value = null;
    int testType = 0;
    Int64 delta = null;
    int events = 0;
    javaBuilder.id(id);
    javaBuilder.valueMask(valueMask);
    if (Ca.COUNTER.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      counter = in.readCard32();
      javaBuilder.counter(counter);
    }
    if (Ca.VALUE_TYPE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      valueType = in.readCard32();
      javaBuilder.valueType(valueType);
    }
    if (Ca.VALUE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      value = Int64.readInt64(in);
      javaBuilder.value(value);
    }
    if (Ca.TEST_TYPE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      testType = in.readCard32();
      javaBuilder.testType(testType);
    }
    if (Ca.DELTA.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      delta = Int64.readInt64(in);
      javaBuilder.delta(delta);
    }
    if (Ca.EVENTS.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      events = in.readCard32();
      javaBuilder.events(events);
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(id);
    out.writeCard32(valueMask);
    if (Ca.COUNTER.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(counter);
    }
    if (Ca.VALUE_TYPE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(valueType);
    }
    if (Ca.VALUE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      value.write(out);
    }
    if (Ca.TEST_TYPE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(testType);
    }
    if (Ca.DELTA.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      delta.write(out);
    }
    if (Ca.EVENTS.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(events);
    }
    out.writePadAlign(getSize());
  }

  public boolean isValueMaskEnabled(Ca... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Ca m : maskEnums) {
      if (!m.isEnabled(valueMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 28 + (Ca.COUNTER.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Ca.VALUE_TYPE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Ca.TEST_TYPE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Ca.EVENTS.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateAlarmBuilder {
    @java.lang.SuppressWarnings("all")
    private int id;
    @java.lang.SuppressWarnings("all")
    private int valueMask;
    @java.lang.SuppressWarnings("all")
    private int counter;
    @java.lang.SuppressWarnings("all")
    private int valueType;
    @java.lang.SuppressWarnings("all")
    private Int64 value;
    @java.lang.SuppressWarnings("all")
    private int testType;
    @java.lang.SuppressWarnings("all")
    private Int64 delta;
    @java.lang.SuppressWarnings("all")
    private int events;

    private CreateAlarm.CreateAlarmBuilder valueMask(int valueMask) {
      this.valueMask = valueMask;
      return this;
    }

    public boolean isValueMaskEnabled(Ca... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Ca m : maskEnums) {
        if (!m.isEnabled(valueMask)) {
          return false;
        }
      }
      return true;
    }

    private CreateAlarm.CreateAlarmBuilder valueMaskEnable(Ca... maskEnums) {
      for (Ca m : maskEnums) {
        valueMask((int) m.enableFor(valueMask));
      }
      return this;
    }

    private CreateAlarm.CreateAlarmBuilder valueMaskDisable(Ca... maskEnums) {
      for (Ca m : maskEnums) {
        valueMask((int) m.disableFor(valueMask));
      }
      return this;
    }

    public CreateAlarm.CreateAlarmBuilder counter(int counter) {
      this.counter = counter;
      valueMaskEnable(Ca.COUNTER);
      return this;
    }

    public CreateAlarm.CreateAlarmBuilder valueType(int valueType) {
      this.valueType = valueType;
      valueMaskEnable(Ca.VALUE_TYPE);
      return this;
    }

    public CreateAlarm.CreateAlarmBuilder valueType(Valuetype valueType) {
      this.valueType = (int) valueType.getValue();
      valueMaskEnable(Ca.VALUE_TYPE);
      return this;
    }

    public CreateAlarm.CreateAlarmBuilder value(Int64 value) {
      this.value = value;
      valueMaskEnable(Ca.VALUE);
      return this;
    }

    public CreateAlarm.CreateAlarmBuilder testType(int testType) {
      this.testType = testType;
      valueMaskEnable(Ca.TEST_TYPE);
      return this;
    }

    public CreateAlarm.CreateAlarmBuilder testType(Testtype testType) {
      this.testType = (int) testType.getValue();
      valueMaskEnable(Ca.TEST_TYPE);
      return this;
    }

    public CreateAlarm.CreateAlarmBuilder delta(Int64 delta) {
      this.delta = delta;
      valueMaskEnable(Ca.DELTA);
      return this;
    }

    public CreateAlarm.CreateAlarmBuilder events(int events) {
      this.events = events;
      valueMaskEnable(Ca.EVENTS);
      return this;
    }

    public int getSize() {
      return 28 + (Ca.COUNTER.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Ca.VALUE_TYPE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Ca.TEST_TYPE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Ca.EVENTS.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
    }

    @java.lang.SuppressWarnings("all")
    CreateAlarmBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateAlarm.CreateAlarmBuilder id(final int id) {
      this.id = id;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateAlarm build() {
      return new CreateAlarm(this.id, this.valueMask, this.counter, this.valueType, this.value, this.testType, this.delta, this.events);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateAlarm.CreateAlarmBuilder(id=" + this.id + ", valueMask=" + this.valueMask + ", counter=" + this.counter + ", valueType=" + this.valueType + ", value=" + this.value + ", testType=" + this.testType + ", delta=" + this.delta + ", events=" + this.events + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateAlarm(final int id, final int valueMask, final int counter, final int valueType, final Int64 value, final int testType, final Int64 delta, final int events) {
    this.id = id;
    this.valueMask = valueMask;
    this.counter = counter;
    this.valueType = valueType;
    this.value = value;
    this.testType = testType;
    this.delta = delta;
    this.events = events;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateAlarm.CreateAlarmBuilder builder() {
    return new CreateAlarm.CreateAlarmBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getId() {
    return this.id;
  }

  @java.lang.SuppressWarnings("all")
  public int getValueMask() {
    return this.valueMask;
  }

  @java.lang.SuppressWarnings("all")
  public int getCounter() {
    return this.counter;
  }

  @java.lang.SuppressWarnings("all")
  public int getValueType() {
    return this.valueType;
  }

  @java.lang.SuppressWarnings("all")
  public Int64 getValue() {
    return this.value;
  }

  @java.lang.SuppressWarnings("all")
  public int getTestType() {
    return this.testType;
  }

  @java.lang.SuppressWarnings("all")
  public Int64 getDelta() {
    return this.delta;
  }

  @java.lang.SuppressWarnings("all")
  public int getEvents() {
    return this.events;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateAlarm)) return false;
    final CreateAlarm other = (CreateAlarm) o;
    if (this.getId() != other.getId()) return false;
    if (this.getValueMask() != other.getValueMask()) return false;
    if (this.getCounter() != other.getCounter()) return false;
    if (this.getValueType() != other.getValueType()) return false;
    if (this.getTestType() != other.getTestType()) return false;
    if (this.getEvents() != other.getEvents()) return false;
    final java.lang.Object this$value = this.getValue();
    final java.lang.Object other$value = other.getValue();
    if (this$value == null ? other$value != null : !this$value.equals(other$value)) return false;
    final java.lang.Object this$delta = this.getDelta();
    final java.lang.Object other$delta = other.getDelta();
    if (this$delta == null ? other$delta != null : !this$delta.equals(other$delta)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getId();
    result = result * PRIME + this.getValueMask();
    result = result * PRIME + this.getCounter();
    result = result * PRIME + this.getValueType();
    result = result * PRIME + this.getTestType();
    result = result * PRIME + this.getEvents();
    final java.lang.Object $value = this.getValue();
    result = result * PRIME + ($value == null ? 43 : $value.hashCode());
    final java.lang.Object $delta = this.getDelta();
    result = result * PRIME + ($delta == null ? 43 : $delta.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateAlarm(id=" + this.getId() + ", valueMask=" + this.getValueMask() + ", counter=" + this.getCounter() + ", valueType=" + this.getValueType() + ", value=" + this.getValue() + ", testType=" + this.getTestType() + ", delta=" + this.getDelta() + ", events=" + this.getEvents() + ")";
  }
}
