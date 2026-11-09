package com.github.moaxcp.x11.protocol;

import org.eclipse.collections.api.list.primitive.ByteList;
import org.eclipse.collections.impl.factory.primitive.ByteLists;

public final class LittleEndian {
    public static short toShort(byte[] bytes) {
        int value = (bytes[1] & 255);
        value = (value << 8) + (bytes[0] & 255);
        return (short) value;
    }

    public static void write(short value, byte[] bytes) {
        bytes[0] = (byte) (value & 255);
        value >>= 8;
        bytes[1] = (byte) (value & 255);
    }

    public static int toInt(byte[] bytes) {
        int value = (bytes[3] & 255);
        value = (value << 8) + (bytes[2] & 255);
        value = (value << 8) + (bytes[1] & 255);
        value = (value << 8) + (bytes[0] & 255);
        return value;
    }

    public static byte[] write(int value, byte[] bytes) {
        bytes[0] = (byte) (value & 255);
        value >>= 8;
        bytes[1] = (byte) (value & 255);
        value >>= 8;
        bytes[2] = (byte) (value & 255);
        value >>= 8;
        bytes[3] = (byte) (value & 255);
        return bytes;
    }

    public static ByteList writeList(int value) {
        var first = (byte) (value & 255);
        value >>= 8;
        var second = (byte) (value & 255);
        value >>= 8;
        var third = (byte) (value & 255);
        value >>= 8;
        var fourth = (byte) (value & 255);
        return ByteLists.immutable.of(first, second, third, fourth);
    }

    public static Long toLong(byte[] bytes) {
        long value = (bytes[7] & 255);
        value = (value << 8) + (bytes[6] & 255);
        value = (value << 8) + (bytes[5] & 255);
        value = (value << 8) + (bytes[4] & 255);
        value = (value << 8) + (bytes[3] & 255);
        value = (value << 8) + (bytes[2] & 255);
        value = (value << 8) + (bytes[1] & 255);
        value = (value << 8) + (bytes[0] & 255);
        return value;
    }

    public static void write(long value, byte[] bytes) {
        bytes[0] = (byte) (value & 255);
        value >>= 8;
        bytes[1] = (byte) (value & 255);
        value >>= 8;
        bytes[2] = (byte) (value & 255);
        value >>= 8;
        bytes[3] = (byte) (value & 255);
        value >>= 8;
        bytes[4] = (byte) (value & 255);
        value >>= 8;
        bytes[5] = (byte) (value & 255);
        value >>= 8;
        bytes[6] = (byte) (value & 255);
        value >>= 8;
        bytes[7] = (byte) (value & 255);
    }

    public static float toFloat(byte[] bytes) {
        int value = toInt(bytes);
        return Float.intBitsToFloat(value);
    }

    public static void write(float value, byte[] bytes) {
        int intValue = Float.floatToRawIntBits(value);
        write(intValue, bytes);
    }

    public static double toDouble(byte[] bytes) {
        long value = toLong(bytes);
        return Double.longBitsToDouble(value);
    }

    public static void write(double value, byte[] bytes) {
        long longValue = Double.doubleToLongBits(value);
        write(longValue, bytes);
    }

    @java.lang.SuppressWarnings("all")
    private LittleEndian() {
        throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
