package com.github.moaxcp.x11.protocol;

import org.eclipse.collections.api.iterator.ByteIterator;
import org.eclipse.collections.api.list.primitive.ByteList;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.impl.factory.primitive.ByteLists;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

public final class Utilities {
  public static ByteList toList(byte[] bytes) {
    if (bytes == null) {
      throw new java.lang.NullPointerException("bytes is marked non-null but is null");
    }
    return ByteLists.immutable.of(bytes);
  }

  public static X11Input toX11Input(ByteList bytes) {
    if (bytes == null) {
      throw new java.lang.NullPointerException("bytes is marked non-null but is null");
    }
    return toX11Input(false, bytes);
  }

  public static X11Input toX11Input(boolean bigEndian, ByteList bytes) {
    if (bytes == null) {
      throw new java.lang.NullPointerException("bytes is marked non-null but is null");
    }
    if (bigEndian) {
      return new X11BigEndianInputStream(new BufferedInputStream(new InputStream() {
        private ByteIterator iterator = bytes.byteIterator();
        @Override
        public int read() throws IOException {
          if (iterator.hasNext()) {
            return Byte.toUnsignedInt(iterator.next());
          }
          return -1;
        }
      }));
    } else {
      return new X11LittleEndianInputStream(new BufferedInputStream(new InputStream() {
        private ByteIterator iterator = bytes.byteIterator();
        @Override
        public int read() throws IOException {
          if (iterator.hasNext()) {
            return Byte.toUnsignedInt(iterator.next());
          }
          return -1;
        }
      }));
    }
  }

  public static String toString(ByteList byteList) {
    if (byteList == null) {
      throw new java.lang.NullPointerException("byteList is marked non-null but is null");
    }
    byte[] bytes = new byte[byteList.size()];
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = byteList.get(i);
    }
    return new String(bytes);
  }

  public static String toString(ByteList byteList, Charset charset) {
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

  public static ByteList toByteList(String string) {
    if (string == null) {
      throw new java.lang.NullPointerException("string is marked non-null but is null");
    }
    return toList(string.getBytes());
  }

  public static ByteList toByteList(String string, Charset charset) {
    if (string == null) {
      throw new java.lang.NullPointerException("string is marked non-null but is null");
    }
    if (charset == null) {
      throw new java.lang.NullPointerException("charset is marked non-null but is null");
    }
    return toList(string.getBytes(charset));
  }

  public static ByteList toByteList(XEvent event) {
    ByteArrayOutputStream saved = new ByteArrayOutputStream();
    X11Output out = new X11BigEndianOutputStream(saved);
    try {
      event.write(out);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
    return toList(saved.toByteArray());
  }

  public static ByteList toByteList(byte majorOpcode, XRequest request) {
    ByteArrayOutputStream saved = new ByteArrayOutputStream();
    X11Output out = new X11BigEndianOutputStream(saved);
    try {
      request.write(majorOpcode, out);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
    return toList(saved.toByteArray());
  }

  public static IntList toIntegers(ByteList bytes) {
    if (bytes == null) {
      throw new java.lang.NullPointerException("bytes is marked non-null but is null");
    }
    if (bytes.size() % 4 != 0) {
      throw new IllegalArgumentException("bytes must have size divisible by 4 to be converted to integers got: " + bytes.size());
    }
    var result = IntLists.mutable.empty();
    for (int i = 0; i < bytes.size(); i += 4) {
      int first = bytes.get(i) << 24;
      int second = bytes.get(i + 1) << 24 >>> 8;
      int third = bytes.get(i + 2) << 24 >>> 16;
      int fourth = bytes.get(i + 3) << 24 >>> 24;
      result.add(first | second | third | fourth);
    }
    return result.toImmutable();
  }

  @java.lang.SuppressWarnings("all")
  private Utilities() {
    throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }
}
