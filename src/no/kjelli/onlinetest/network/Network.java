package no.kjelli.onlinetest.network;

import java.io.IOException;

import no.kjelli.generic.World;
import no.kjelli.generic.gfx.ScrollingText;
import no.kjelli.onlinetest.gameobjects.PlayerMP;
import no.kjelli.onlinetest.levels.Level;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;

public class Network {
	public static Client client;
	public static Server server;

	public static final int TCP_PORT = 4567;
	public static final int UDP_PORT = 4568;
	private static String ADDRESS = "localhost";
	private static int TIMEOUT = 5000;

	public static void register(Kryo kryo) {
		kryo.register(PlayerMP.class);
		kryo.register(PacketPlayerAdd.class);
		kryo.register(PacketPlayerRemove.class);
		kryo.register(PacketPlayerPlaceBomb.class);
		kryo.register(PacketPlayerUpdate.class);
		kryo.register(PacketLevelRequest.class);
		kryo.register(PacketLevelResponse.class);
		kryo.register(char[].class);
		kryo.register(char[][].class);
	}

	public static void hostServer() {
		if (server != null)
			return;

		server = new Server();
		server.start();
		register(server.getKryo());
		server.addListener(new ServerListener(server));
		try {
			server.bind(TCP_PORT, UDP_PORT);
			World.add(new ScrollingText("Hosting on port " + TCP_PORT));
			connect(TIMEOUT, "localhost", TCP_PORT, UDP_PORT);
		} catch (IOException e) {
			World.add(new ScrollingText("Port " + TCP_PORT + " already in use?"));
			server.stop();
			server = null;
			e.printStackTrace();
		}
	}

	public static void cleanup() {
		if (server != null) {
			server.close();
			server.stop();
		}

		if (client != null) {
			client.close();
			client.stop();
		}

	}

	public static void connect() {
		if (server == null)
			Level.end();
		connect(TIMEOUT, ADDRESS, TCP_PORT, UDP_PORT);
	}

	public static void connect(int timeout, String address, int tcp, int udp) {
		if (client != null)
			return;
		client = new Client();
		client.start();
		register(client.getKryo());
		client.addListener(new ClientListener(client));
		try {
			client.connect(timeout, address, tcp, udp);
		} catch (IOException e) {
			client.stop();
			client = null;
			World.add(new ScrollingText("Timed out!"));
			e.printStackTrace();
		}
	}
}
