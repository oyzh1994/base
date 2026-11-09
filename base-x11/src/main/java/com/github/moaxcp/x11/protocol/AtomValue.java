package com.github.moaxcp.x11.protocol;


/**
 * An atom. May be a predefined atom or an intern atom.
 */
public final class AtomValue {
  private final int id;
  private final String name;

  @java.lang.SuppressWarnings("all")
  public AtomValue(final int id, final String name) {
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.id = id;
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public int getId() {
    return this.id;
  }

  @java.lang.SuppressWarnings("all")
  public String getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof AtomValue)) return false;
    final AtomValue other = (AtomValue) o;
    if (this.getId() != other.getId()) return false;
    final java.lang.Object this$name = this.getName();
    final java.lang.Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getId();
    final java.lang.Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "AtomValue(id=" + this.getId() + ", name=" + this.getName() + ")";
  }
}
