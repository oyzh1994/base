package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class QueryClientsReply implements XReply {
  public static final String PLUGIN_NAME = "res";
  private final short sequenceNumber;
  private final ImmutableList<Client> clients;

  public static QueryClientsReply readQueryClientsReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryClientsReply.QueryClientsReplyBuilder javaBuilder = QueryClientsReply.builder();
    int length = in.readCard32();
    int numClients = in.readCard32();
    byte[] pad5 = in.readPad(20);
    MutableList<Client> clients = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numClients)));
    for (int i = 0; i < Integer.toUnsignedLong(numClients); i++) {
      clients.add(Client.readClient(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.clients(clients.toImmutable());
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    int numClients = clients.size();
    out.writeCard32(numClients);
    out.writePad(20);
    for (Client t : clients) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(clients);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryClientsReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Client> clients;

    public int getSize() {
      return 32 + XObject.sizeOf(clients);
    }

    @java.lang.SuppressWarnings("all")
    QueryClientsReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryClientsReply.QueryClientsReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryClientsReply.QueryClientsReplyBuilder clients(final ImmutableList<Client> clients) {
      if (clients == null) {
        throw new java.lang.NullPointerException("clients is marked non-null but is null");
      }
      this.clients = clients;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryClientsReply build() {
      return new QueryClientsReply(this.sequenceNumber, this.clients);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryClientsReply.QueryClientsReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", clients=" + this.clients + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryClientsReply(final short sequenceNumber, final ImmutableList<Client> clients) {
    if (clients == null) {
      throw new java.lang.NullPointerException("clients is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.clients = clients;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryClientsReply.QueryClientsReplyBuilder builder() {
    return new QueryClientsReply.QueryClientsReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Client> getClients() {
    return this.clients;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryClientsReply)) return false;
    final QueryClientsReply other = (QueryClientsReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$clients = this.getClients();
    final java.lang.Object other$clients = other.getClients();
    if (this$clients == null ? other$clients != null : !this$clients.equals(other$clients)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $clients = this.getClients();
    result = result * PRIME + ($clients == null ? 43 : $clients.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryClientsReply(sequenceNumber=" + this.getSequenceNumber() + ", clients=" + this.getClients() + ")";
  }
}
