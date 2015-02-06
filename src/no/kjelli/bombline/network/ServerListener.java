package no.kjelli.bombline.network;

import no.kjelli.bombline.BombermanOnline;

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

	}

	@Override
	public void disconnected(Connection connection) {
		server.sendToAllExceptTCP(connection.getID(), new PacketPlayerRemove(
				connection.getID()));
	}

	@Override
	public void received(Connection connection, Object object) {
		if (object instanceof Packet) {
			Packet packet = (Packet) object;
			if (packet instanceof PacketPlayerJoinRequest) {
				handleJoinRequest((PacketPlayerJoinRequest) packet);
			} else if (packet instanceof PacketPlayerUpdate
					|| packet instanceof PacketPlayerPlaceBomb) {
				server.sendToAllExceptUDP(connection.getID(), packet);
			} else if (packet instanceof PacketLevelRequest) {
				server.sendToTCP(1, packet);
			} else if (packet instanceof PacketPlayerUpdateRequest) {
				server.sendToTCP(((PacketPlayerUpdateRequest) packet).id,
						packet);
			} else if (packet instanceof PacketLevelResponse) {
				PacketLevelResponse plr = (PacketLevelResponse) packet;
				server.sendToTCP(plr.receiverID, packet);
			} else if (packet instanceof PacketPlayerCredentials) {
				server.sendToAllExceptTCP(connection.getID(), packet);
			}
		} else {
			System.err.println("Unknown packet: " + object);
		}
	}

	private void handleJoinRequest(PacketPlayerJoinRequest packet) {
		// TODO Auto-generated method stub
		if (BombermanOnline.state == BombermanOnline.STATE.WAITING_FOR_PLAYERS) {
			server.sendToTCP(packet.id, new PacketPlayerJoinResponse(true));
		} else {
			server.sendToTCP(packet.id, new PacketPlayerJoinResponse(false));
		}
	}
}
