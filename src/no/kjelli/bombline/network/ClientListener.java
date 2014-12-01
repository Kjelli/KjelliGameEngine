package no.kjelli.bombline.network;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.bombline.levels.Level;
import no.kjelli.generic.World;
import no.kjelli.generic.gfx.TextScrolling;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {
	Client client;

	public ClientListener(Client client) {
		this.client = client;
	}

	@Override
	public void connected(Connection connection) {
		if (Network.getServer() == null) {
			client.sendTCP(new PacketPlayerJoinRequest(client.getID()));
		}
	}

	@Override
	public void received(Connection connection, Object object) {
		// System.out.println("Received a " + object.getClass().getName());
		if (object instanceof Packet) {
			Packet packet = (Packet) object;
			if (packet instanceof PacketPlayerJoinResponse) {
				PacketPlayerJoinResponse p = (PacketPlayerJoinResponse) packet;
				if (p.accepted) {
					client.sendTCP(new PacketPlayerCredentials(client.getID(),
							BombermanOnline.name, true));
					client.sendTCP(new PacketLevelRequest(client.getID()));
				} else {
					BombermanOnline.initIntro();

					// TODO spectate? Gameroom? Longterm projects
					World.add(new TextScrolling("Game already started!"));
				}
			} else {
				Level.addPacket((Packet) object);
			}
		}
	}
}
