package com.github.moaxcp.x11.protocol;


/**
 * An exception thrown when the x11 server returns an {@link XError} which has not been recovered from.
 *
 * Could use xlib code to get error message https://github.com/mirror/libX11/blob/caa71668af7fd3ebdd56353c8f0ab90824773969/src/ErrDes.c
 */
public class X11ErrorException extends RuntimeException {
  /**
   * The error returned from the server
   */
  private XError error;

  /**
   * Creates an X11Error exception.
   * @param error from server. non-null
   */
  public X11ErrorException(XError error) {
    super(error.toString());
    if (error == null) {
      throw new java.lang.NullPointerException("error is marked non-null but is null");
    }
    this.error = error;
  }

  /**
   * The error returned from the server
   */
  @java.lang.SuppressWarnings("all")
  public XError getError() {
    return this.error;
  }
}
