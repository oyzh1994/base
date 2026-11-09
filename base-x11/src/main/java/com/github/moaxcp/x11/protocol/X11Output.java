package com.github.moaxcp.x11.protocol;

import java.io.IOException;

public interface X11Output {
  void writeBool(boolean bool) throws IOException;

  default void writeBool(boolean[] bool) throws IOException {
    for (boolean value : bool) {
      writeBool(value);
    }
  }

  void writeByte(byte b) throws IOException;

  void writeInt8(byte int8) throws IOException;

  void writeInt16(short int16) throws IOException;

  void writeInt32(int int32) throws IOException;

  default void writeInt32(int[] int32) throws IOException {
    for (int value : int32) {
      writeInt32(value);
    }
  }

  void writeInt64(long int64) throws IOException;

  void writeCard8(byte card8) throws IOException;

  default void writeCard8(byte[] card8) throws IOException {
    for (byte value : card8) {
      writeCard8(value);
    }
  }

  void writeCard16(short card16) throws IOException;

  default void writeCard16(short[] card16) throws IOException {
    for (short value : card16) {
      writeCard16(value);
    }
  }

  void writeCard32(int card32) throws IOException;

  default void writeCard32(int[] card32) throws IOException {
    for (int value : card32) {
      writeCard32(value);
    }
  }

  void writeCard64(long card64) throws IOException;

  default void writeCard64(long[] card64) throws IOException {
    for (long value : card64) {
      writeCard64(value);
    }
  }

  void writeFloat(float f) throws IOException;

  default void writeFloat(float[] f) throws IOException {
    for (float value : f) {
      writeFloat(value);
    }
  }

  void writeDouble(double d) throws IOException;

  default void writeDouble(double[] d) throws IOException {
    for (double value : d) {
      writeDouble(value);
    }
  }

  default void writeChar(byte[] string) throws IOException {
    writeByte(string);
  }

  void writeString8(String string) throws IOException;

  default void writeByte(byte[] bytes) throws IOException {
    for (byte value : bytes) {
      writeByte(value);
    }
  }

  default void writeVoid(byte[] bytes) throws IOException {
    writeByte(bytes);
  }

  void writeFd(int fd) throws IOException;

  default void writeFd(int[] fd) throws IOException {
    writeInt32(fd);
  }

  default void writePad(int length) throws IOException {
    writeByte(new byte[length]);
  }

  default void writePadAlign(int forLength) throws IOException {
    writePad(XObject.getSizeForPadAlign(forLength));
  }

  default void writePadAlign(int pad, int forLength) throws IOException {
    writePad(XObject.getSizeForPadAlign(pad, forLength));
  }
}
