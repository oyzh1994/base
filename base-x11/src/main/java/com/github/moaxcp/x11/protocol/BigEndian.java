package com.github.moaxcp.x11.protocol;

public final class BigEndian {
    public static byte[] write(int value, byte[] bytes) {
        bytes[0] = (byte) (value >> 24);
        bytes[1] = (byte) (value >> 16);
        bytes[2] = (byte) (value >> 8);
        bytes[3] = (byte) value;
        return bytes;
    }

    public static byte[] writeList(int value) {
        return new byte[]{(byte) (value >> 24), (byte) (value >> 16), (byte) (value >> 8), (byte) value};
    }

    @java.lang.SuppressWarnings("all")
    private BigEndian() {
        throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
