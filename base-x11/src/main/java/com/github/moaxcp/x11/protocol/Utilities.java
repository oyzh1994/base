package com.github.moaxcp.x11.protocol;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

public final class Utilities {
  public static byte[] toList(byte[] bytes) {
    if (bytes == null) {
      throw new java.lang.NullPointerException("bytes is marked non-null but is null");
    }
    return bytes.clone();
  }

  public static X11Input toX11Input(byte[] bytes) {
    if (bytes == null) {
      throw new java.lang.NullPointerException("bytes is marked non-null but is null");
    }
    return toX11Input(false, bytes);
  }

  public static X11Input toX11Input(boolean bigEndian, byte[] bytes) {
    if (bytes == null) {
      throw new java.lang.NullPointerException("bytes is marked non-null but is null");
    }
    InputStream input = new BufferedInputStream(new ByteArrayInputStream(bytes));
    if (bigEndian) {
      return new X11BigEndianInputStream(input);
    } else {
      return new X11LittleEndianInputStream(input);
    }
  }

  public static String toString(byte[] byteList) {
    if (byteList == null) {
      throw new java.lang.NullPointerException("byteList is marked non-null but is null");
    }
    return new String(byteList);
  }

  public static String toString(byte[] byteList, Charset charset) {
    if (byteList == null) {
      throw new java.lang.NullPointerException("byteList is marked non-null but is null");
    }
    if (charset == null) {
      throw new java.lang.NullPointerException("charset is marked non-null but is null");
    }
    return new String(byteList, charset);
  }

  public static String toString(List<Byte> byteList, Charset charset) {
    if (byteList == null) {
      throw new java.lang.NullPointerException("byteList is marked non-null but is null");
    }
    if (charset == null) {
      throw new java.lang.NullPointerException("charset is marked non-null but is null");
    }
    byte[] bytes = new byte[byteList.size()];
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = byteList.get(i);
    }
    return new String(bytes, charset);
  }

  public static byte[] toByteList(String string) {
    if (string == null) {
      throw new java.lang.NullPointerException("string is marked non-null but is null");
    }
    return toList(string.getBytes());
  }

  public static byte[] toByteList(String string, Charset charset) {
    if (string == null) {
      throw new java.lang.NullPointerException("string is marked non-null but is null");
    }
    if (charset == null) {
      throw new java.lang.NullPointerException("charset is marked non-null but is null");
    }
    return toList(string.getBytes(charset));
  }

  public static byte[] toByteList(XEvent event) {
    ByteArrayOutputStream saved = new ByteArrayOutputStream();
    X11Output out = new X11BigEndianOutputStream(saved);
    try {
      event.write(out);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
    return saved.toByteArray();
  }

  public static byte[] toByteList(byte majorOpcode, XRequest request) {
    ByteArrayOutputStream saved = new ByteArrayOutputStream();
    X11Output out = new X11BigEndianOutputStream(saved);
    try {
      request.write(majorOpcode, out);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
    return saved.toByteArray();
  }

  public static int[] toIntegers(byte[] bytes) {
    if (bytes == null) {
      throw new java.lang.NullPointerException("bytes is marked non-null but is null");
    }
    if (bytes.length % 4 != 0) {
      throw new IllegalArgumentException("bytes must have size divisible by 4 to be converted to integers got: " + bytes.length);
    }
    int[] result = new int[bytes.length / 4];
    for (int i = 0; i < bytes.length; i += 4) {
      int first = bytes[i] << 24;
      int second = bytes[i + 1] << 24 >>> 8;
      int third = bytes[i + 2] << 24 >>> 16;
      int fourth = bytes[i + 3] << 24 >>> 24;
      result[i / 4] = first | second | third | fourth;
    }
    return result;
  }

  public static int sum(byte[] values) {
    int result = 0;
    for (byte value : values) {
      result += value;
    }
    return result;
  }

  public static int sum(short[] values) {
    int result = 0;
    for (short value : values) {
      result += value;
    }
    return result;
  }

  @java.lang.SuppressWarnings("all")
  private Utilities() {
    throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }
}
