package no.kjelli.bombline.network;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.bombline.levels.Level;
import no.kjelli.generic.World;
import no.kjelli.generic.gfx.ScrollingText;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {
	Client client;
	
	public ClientListener(Client client){
		this.client = client;
	}
	
	@Override
	public void connected(Connection connection) {
		if(Network.server == null){
			World.add(new ScrollingText("Connected!"));
			client.sendTCP(new PacketLevelRequest(client.getID()));
		}
	}

	@Override
	public void received(Connection connection, Object object) {
		//System.out.println("Received a " + object.getClass().getName());
		if (object instanceof Packet)
			Level.addPacket((Packet) object);
	}

}
