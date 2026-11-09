package com.github.moaxcp.x11.protocol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface X11Input {
  boolean readBool() throws IOException;

  default boolean[] readBool(int length) throws IOException {
    boolean[] list = new boolean[length];
    for (int i = 0; i < length; i++) {
      list[i] = readBool();
    }
    return list;
  }

  byte readByte() throws IOException;

  byte peekByte() throws IOException;

  byte readInt8() throws IOException;

  short readInt16() throws IOException;

  int readInt32() throws IOException;

  default int[] readInt32(int length) throws IOException {
    int[] list = new int[length];
    for (int i = 0; i < length; i++) {
      list[i] = readInt32();
    }
    return list;
  }

  long readInt64() throws IOException;

  byte readCard8() throws IOException;

  default byte[] readCard8(int length) throws IOException {
    byte[] list = new byte[length];
    for (int i = 0; i < length; i++) {
      list[i] = readCard8();
    }
    return list;
  }

  short readCard16() throws IOException;

  default short[] readCard16(int length) throws IOException {
    short[] list = new short[length];
    for (int i = 0; i < length; i++) {
      list[i] = readCard16();
    }
    return list;
  }

  int readCard32() throws IOException;

  default int[] readCard32(int length) throws IOException {
    int[] list = new int[length];
    for (int i = 0; i < length; i++) {
      list[i] = readCard32();
    }
    return list;
  }

  long readCard64() throws IOException;

  default long[] readCard64(int length) throws IOException {
    long[] list = new long[length];
    for (int i = 0; i < length; i++) {
      list[i] = readCard64();
    }
    return list;
  }

  float readFloat() throws IOException;

  default float[] readFloat(int length) throws IOException {
    float[] list = new float[length];
    for (int i = 0; i < length; i++) {
      list[i] = readFloat();
    }
    return list;
  }

  double readDouble() throws IOException;

  default double[] readDouble(int length) throws IOException {
    double[] list = new double[length];
    for (int i = 0; i < length; i++) {
      list[i] = readDouble();
    }
    return list;
  }

  default byte[] readChar(int length) throws IOException {
    byte[] list = new byte[length];
    for (int i = 0; i < length; i++) {
      list[i] = readByte();
    }
    return list;
  }

  String readString8(int length) throws IOException;

  default byte[] readByte(int length) throws IOException {
    byte[] list = new byte[length];
    for (int i = 0; i < length; i++) {
      list[i] = readCard8();
    }
    return list;
  }

  default byte[] readVoid(int length) throws IOException {
    return readByte(length);
  }

  int readFd() throws IOException;

  default int[] readFd(int length) throws IOException {
    return readInt32(length);
  }

  byte[] readPad(int length) throws IOException;

  default void readPadAlign(int forLength) throws IOException {
    readPad(XObject.getSizeForPadAlign(forLength));
  }

  default void readPadAlign(int align, int forLength) throws IOException {
    readPad(XObject.getSizeForPadAlign(align, forLength));
  }

  void peekWith(X11InputConsumer consumer) throws IOException;

  int available() throws IOException;

  default <T> List<T> readObject(XReadFunction<T> reader, int length) throws IOException {
    List<T> result = new ArrayList<>(length);
    for (int i = 0; i < length; i++) {
      T object = reader.read(this);
      result.add(object);
    }
    return List.copyOf(result);
  }
}
