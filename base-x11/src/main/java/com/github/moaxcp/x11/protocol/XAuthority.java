package com.github.moaxcp.x11.protocol;

import org.eclipse.collections.api.list.primitive.ByteList;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.github.moaxcp.x11.protocol.ParametersCheck.requireNonEmpty;
import static com.github.moaxcp.x11.protocol.Utilities.toList;

/**
 * An X11 Authority defines a secret key used when authenticating with the x11 server. family, address and displayNumber
 * are used to find the correct authority. protocolName and protocolDate is used to authenticate with x11.
 */
public final class XAuthority {
  private final Family family;
  private final ByteList address;
  private final int displayNumber;
  private final ByteList protocolName;
  private final ByteList protocolData;


  /**
   * Represents a Family or connection type used in authentication.
   */
  public enum Family {
    INTERNET(0), DEC_NET(1), CHAOS(2), SERVER_INTERPRETED(5), INTERNET6(6), LOCAL(256), WILD(65535), NETNAME(254), KRB5PRINCIPAL(253), LOCALHOST(252);
    private final int code;

    Family(int code) {
      this.code = code;
    }

    /**
     * Returns the family code
     * @return
     */
    public int getCode() {
      return this.code;
    }

    /**
     * Returns the family with this code
     * @param code
     * @return
     * @throws IllegalArgumentException if code is unsupported.
     */
    public static Family getByCode(int code) {
      for (Family family : Family.values()) {
        if (family.code == code) {
          return family;
        }
      }
      throw new IllegalArgumentException("Unsupported code \"" + code + "\"");
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "XAuthority.Family." + this.name() + "(code=" + this.getCode() + ")";
    }
  }

  /**
   * Creates a new XAuthority.
   * @param family
   * @param address
   * @param displayNumber
   * @param protocolName
   * @param protocolData
   * @throws NullPointerException if any parameter is null.
   * @throws IllegalArgumentException if displayNumber is less than 0 or protocolName is empty.
   */
  public XAuthority(Family family, ByteList address, int displayNumber, ByteList protocolName, ByteList protocolData) {
    if (family == null) {
      throw new java.lang.NullPointerException("family is marked non-null but is null");
    }
    if (address == null) {
      throw new java.lang.NullPointerException("address is marked non-null but is null");
    }
    if (protocolName == null) {
      throw new java.lang.NullPointerException("protocolName is marked non-null but is null");
    }
    if (protocolData == null) {
      throw new java.lang.NullPointerException("protocolData is marked non-null but is null");
    }
    this.family = family;
    this.address = address;
    if (displayNumber < 0) {
      throw new IllegalArgumentException("displayNumber was \"" + displayNumber + "\" expected >= 0.");
    }
    this.displayNumber = displayNumber;
    this.protocolName = requireNonEmpty("protocolName", protocolName);
    this.protocolData = requireNonEmpty("protocolData", protocolData);
  }

  public static Optional<XAuthority> read(DataInput in) {
    try {
      Family family = Family.getByCode(in.readUnsignedShort());
      int length = in.readUnsignedShort();
      var address = readBytesList(in, length);
      length = in.readUnsignedShort();
      int number = 0;
      if (length != 0) {
        number = Integer.parseInt(new String(readBytes(in, length)));
      }
      length = in.readUnsignedShort();
      var name = readBytesList(in, length);
      length = in.readUnsignedShort();
      var data = readBytesList(in, length);
      return Optional.of(new XAuthority(family, address, number, name, data));
    } catch (IOException ex) {
      return Optional.empty();
    }
  }

  private static byte[] readBytes(DataInput in, int length) throws IOException {
    byte[] bytes = new byte[length];
    in.readFully(bytes);
    return bytes;
  }

  private static ByteList readBytesList(DataInput in, int length) throws IOException {
    byte[] bytes = new byte[length];
    in.readFully(bytes);
    return toList(bytes);
  }

  public static Optional<XAuthority> getAuthority(List<XAuthority> authorities, DisplayName displayName) throws UnknownHostException {
    for (int i = 0; i < authorities.size(); i++) {
      XAuthority auth = authorities.get(i);
      switch (auth.getFamily()) {
      case WILD: 
        return Optional.of(auth);
      default: 
        InetAddress hostNameAddress;
        if (displayName.getHostName() == null || displayName.getHostName().equals("localhost")) {
          hostNameAddress = InetAddress.getLocalHost();
        } else {
          hostNameAddress = InetAddress.getByName(displayName.getHostName());
        }
        try {
          InetAddress authAddress = InetAddress.getByName(Utilities.toString(auth.getAddress(), StandardCharsets.UTF_8));
          if (authAddress.equals(hostNameAddress)) {
            return Optional.of(auth);
          }
        } catch (UnknownHostException e) {
        }
        //go to next authority
        break;
      }
    }
    return Optional.empty();
  }

  /**
   * Returns the XAuthority file for the current environment. First the XAUTHORITY environment variable is checked. If
   * XAUTHORITY is not set or empty the .XAuthority file in the user's home is returned.
   * @return XAuthority file to use for the current environment
   */
  public static File getXAuthorityFile() {
    String authFilename = System.getenv("XAUTHORITY");
    if (authFilename == null || authFilename.equals("")) {
      authFilename = System.getProperty("user.home") + File.separatorChar + ".Xauthority";
    }
    return new File(authFilename);
  }

  public static List<XAuthority> getAuthorities(File file) {
    List<XAuthority> authorities = new ArrayList<>();
    try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
      Optional<XAuthority> read = read(in);
      while (read.isPresent()) {
        XAuthority current = read.get();
        authorities.add(current);
        read = read(in);
      }
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
    return authorities;
  }

  @java.lang.SuppressWarnings("all")
  public Family getFamily() {
    return this.family;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getAddress() {
    return this.address;
  }

  @java.lang.SuppressWarnings("all")
  public int getDisplayNumber() {
    return this.displayNumber;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getProtocolName() {
    return this.protocolName;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getProtocolData() {
    return this.protocolData;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof XAuthority)) return false;
    final XAuthority other = (XAuthority) o;
    if (this.getDisplayNumber() != other.getDisplayNumber()) return false;
    final java.lang.Object this$family = this.getFamily();
    final java.lang.Object other$family = other.getFamily();
    if (this$family == null ? other$family != null : !this$family.equals(other$family)) return false;
    final java.lang.Object this$address = this.getAddress();
    final java.lang.Object other$address = other.getAddress();
    if (this$address == null ? other$address != null : !this$address.equals(other$address)) return false;
    final java.lang.Object this$protocolName = this.getProtocolName();
    final java.lang.Object other$protocolName = other.getProtocolName();
    if (this$protocolName == null ? other$protocolName != null : !this$protocolName.equals(other$protocolName)) return false;
    final java.lang.Object this$protocolData = this.getProtocolData();
    final java.lang.Object other$protocolData = other.getProtocolData();
    if (this$protocolData == null ? other$protocolData != null : !this$protocolData.equals(other$protocolData)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDisplayNumber();
    final java.lang.Object $family = this.getFamily();
    result = result * PRIME + ($family == null ? 43 : $family.hashCode());
    final java.lang.Object $address = this.getAddress();
    result = result * PRIME + ($address == null ? 43 : $address.hashCode());
    final java.lang.Object $protocolName = this.getProtocolName();
    result = result * PRIME + ($protocolName == null ? 43 : $protocolName.hashCode());
    final java.lang.Object $protocolData = this.getProtocolData();
    result = result * PRIME + ($protocolData == null ? 43 : $protocolData.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "XAuthority(family=" + this.getFamily() + ", address=" + this.getAddress() + ", displayNumber=" + this.getDisplayNumber() + ", protocolName=" + this.getProtocolName() + ", protocolData=" + this.getProtocolData() + ")";
  }
}
