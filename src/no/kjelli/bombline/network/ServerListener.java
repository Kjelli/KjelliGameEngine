package no.kjelli.bombline.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerListener extends Listener {
	Server server;

	public ServerListener(Server server) {
		this.server = server;
	}

	@Override
	public void connected(Connection connection) {
		PacketPlayerAdd ppa = new PacketPlayerAdd(connection.getID());
		server.sendToAllExceptTCP(connection.getID(), ppa);
		for (Connection c : server.getConnections()) {
			if (c.getID() == connection.getID())
				continue;
			server.sendToTCP(connection.getID(), new PacketPlayerAdd(c.getID()));
		}
	}

	@Override
	public void disconnected(Connection connection) {
		server.sendToAllExceptTCP(connection.getID(), new PacketPlayerRemove(
				connection.getID()));
	}

	@Override
	public void received(Connection connection, Object object) {
		if (object instanceof PacketPlayerUpdate
				|| object instanceof PacketPlayerPlaceBomb) {
			server.sendToAllExceptUDP(connection.getID(), object);
		} else if (object instanceof PacketLevelRequest) {
			server.sendToTCP(1, object);
		} else if (object instanceof PacketPlayerUpdateRequest) {
			server.sendToTCP(((PacketPlayerUpdateRequest) object).id, object);
		} else if (object instanceof PacketLevelResponse) {
			PacketLevelResponse packet = (PacketLevelResponse) object;
			server.sendToTCP(packet.receiverID, packet);
		}
	}
}
