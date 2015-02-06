package no.kjelli.bombline.network;

import java.io.IOException;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.bombline.BombermanOnline.STATE;
import no.kjelli.bombline.gameobjects.PlayerMP;
import no.kjelli.generic.World;
import no.kjelli.generic.gfx.texts.TextScrolling;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class Network {
	private static Client client;
	private static Server server;

	public static final int TCP_PORT = 4567;
	public static final int UDP_PORT = 4568;
	private static String ADDRESS = "localhost";
	private static int TIMEOUT = 5000;

	public static void settings() {
		Log.set(Log.LEVEL_NONE);
	}

	public static void register(Kryo kryo) {
		kryo.register(PlayerMP.class);
		kryo.register(PacketPlayerRemove.class);
		kryo.register(PacketPlayerPlaceBomb.class);
		kryo.register(PacketPlayerLoseLife.class);
		kryo.register(PacketPlayerUpdate.class);
		kryo.register(PacketLevelStart.class);
		kryo.register(PacketPlayerCredentials.class);
		kryo.register(PacketPlayerJoinRequest.class);
		kryo.register(PacketPlayerJoinResponse.class);
		kryo.register(PacketPlayerUpdateRequest.class);
		kryo.register(PacketLevelRequest.class);
		kryo.register(PacketLevelResponse.class);
		kryo.register(PacketPowerupSpawn.class);
		kryo.register(PacketPowerupGain.class);
		kryo.register(char[].class);
		kryo.register(char[][].class);
	}

	public static boolean hostServer() {
		if (server != null)
			return true;
		server = new Server();
		server.start();
		register(server.getKryo());
		server.addListener(new ServerListener(server));
		try {
			BombermanOnline.state = STATE.WAITING_FOR_PLAYERS;
			server.bind(TCP_PORT, UDP_PORT);
			connect(TIMEOUT, "localhost", TCP_PORT, UDP_PORT);
			return true;
		} catch (IOException e) {
			server.stop();
			server = null;
			World.add(new TextScrolling(e.getClass().getSimpleName()));
			e.printStackTrace();
			BombermanOnline.state = STATE.INTRO;
			return false;
		}
	}

	public static void cleanup() {
		if (server != null) {
			server.close();
			server.stop();
<<<<<<< HEAD
=======
			server = null;
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
		}

		if (client != null) {
			client.close();
			client.stop();
<<<<<<< HEAD
=======
			client = null;
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
		}

	}

	public static boolean connect(String hostAddress) {
		return connect(TIMEOUT, hostAddress, TCP_PORT, UDP_PORT);
	}

	public static boolean connect() {
		return connect(TIMEOUT, ADDRESS, TCP_PORT, UDP_PORT);
	}

	public static boolean connect(int timeout, String address, int tcp, int udp) {
		if (client != null)
			return true;
		client = new Client();
		client.start();
		register(client.getKryo());
		client.addListener(new ClientListener(client));
		try {
			client.connect(timeout, address, tcp, udp);
			return true;
		} catch (IOException e) {
			client.stop();
			client = null;
			World.add(new TextScrolling(e.getClass().getSimpleName()));
			e.printStackTrace();
			return false;
		}
	}

	public static boolean isOnline() {
		return Network.client != null && Network.client.isConnected();
	}

	public static boolean isHosting() {
		return server != null && isOnline();
	}
	
	public static Server getServer(){
		return server;
	}
	
	public static Client getClient(){
		return client;
	}

}
