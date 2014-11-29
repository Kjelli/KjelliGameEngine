package no.kjelli.bombline.levels;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.bombline.gameobjects.Block;
import no.kjelli.bombline.gameobjects.Bomb;
import no.kjelli.bombline.gameobjects.Destructible;
import no.kjelli.bombline.gameobjects.Floor;
import no.kjelli.bombline.gameobjects.Player;
import no.kjelli.bombline.gameobjects.PlayerMP;
import no.kjelli.bombline.network.Network;
import no.kjelli.bombline.network.Packet;
import no.kjelli.bombline.network.PacketLevelRequest;
import no.kjelli.bombline.network.PacketLevelResponse;
import no.kjelli.bombline.network.PacketPlayerAdd;
import no.kjelli.bombline.network.PacketPlayerPlaceBomb;
import no.kjelli.bombline.network.PacketPlayerRemove;
import no.kjelli.bombline.network.PacketPlayerUpdate;
import no.kjelli.bombline.network.PacketPlayerUpdateRequest;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.ScrollingText;

import org.newdawn.slick.Color;

public class Level {
	public static final char FLOOR = '.', BLOCK = '#', DESTRUCTIBLE = 'X',
			PLAYER_ONE = '1', PLAYER_TWO = '2', PLAYER_THREE = '3',
			PLAYER_FOUR = '4';

	private static int width, height;
	private static int playerSpawnX[] = new int[4];
	private static int playerSpawnY[] = new int[4];
	private static Player player;
	private static ArrayList<GameObject> objects;

	private static ArrayList<PlayerMP> players = new ArrayList<PlayerMP>();
	private static ArrayList<Packet> incomingPackets = new ArrayList<Packet>();
	private static char[][] map;

	private static boolean receiving = false;

	public static void init(String filename) {
		loadFromFile(filename);
		World.pause(BombermanOnline.tag_playfield, true);
	}

	private static void addObjectsToWorld() {
		for (GameObject b : objects)
			World.add(b);
	}

	public static void start() {
		World.init((int) getWidth(), (int) getHeight());
		World.hide(BombermanOnline.tag_playfield, false);
		World.pause(BombermanOnline.tag_playfield, false);

		Screen.zoom((float) Screen.getWidth() / (Level.getWidth()));
		Screen.setX((Level.getWidth()) / 2 - Screen.getWidth() / 2);
		Screen.setY((Level.getHeight()) / 1.75f - Screen.getHeight() / 2);
	}

	public static void end() {
		for (GameObject b : objects)
			b.destroy();
		objects.clear();
	}

	private static void loadFromMap(char[][] map) {
		objects = new ArrayList<>();
		setWidth(map.length * BombermanOnline.block_size);
		setHeight(map[0].length * BombermanOnline.block_size);
		for (int y = 0; y < map[0].length; y++) {
			for (int x = 0; x < map.length; x++) {
				char blocktype = map[x][y];
				System.out.println("loading " + blocktype + " at " + x + " "
						+ y);
				determineBlock(blocktype, x, y);
			}
		}

		addObjectsToWorld();
	}

	private static void determineBlock(char blocktype, int x, int y) {
		switch (blocktype) {
		case DESTRUCTIBLE:
			objects.add(new Destructible(x, y));
			break;
		case BLOCK:
			objects.add(new Block(x, y));
			break;
		case PLAYER_ONE:
			addPlayer(1, x, y);
			break;
		case PLAYER_TWO:
			addPlayer(2, x, y);
			break;
		case PLAYER_THREE:
			addPlayer(3, x, y);
			break;
		case PLAYER_FOUR:
			addPlayer(4, x, y);
			break;
		case FLOOR:
			objects.add(new Floor(x, y));
			break;
		default:
			break;
		}
	}

	private static void addPlayer(int playerNo, int x, int y) {
		playerSpawnX[playerNo - 1] = x;
		playerSpawnY[playerNo - 1] = y;
		if (Network.client != null && Network.client.getID() == playerNo) {
			player = new Player(x, y);
			objects.add(player);
		} else if (Network.client == null && playerNo == 1) {
			player = new Player(x, y);
			objects.add(player);
		}
		objects.add(new Floor(x, y));
	}

	private static void loadFromFile(String name) {
		BufferedReader br = null;
		objects = new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader("res\\levels\\" + name
					+ ".bml"));
			int width = Integer.parseInt(br.readLine());
			int height = Integer.parseInt(br.readLine());
			map = new char[width][height];

			setWidth((int) (width * BombermanOnline.block_size));
			setHeight((int) (height * BombermanOnline.block_size));

			int x = 0;
			int y = 0;

			String line;
			while ((line = br.readLine()) != null) {
				x = 0;
				for (String block : line.split(" ")) {
					if (x > width || y > height) {
						System.err
								.println("Inconsistent dimensions in lev file!");
						br.close();
						return;
					}
					char blocktype = ' ';
					if (block.length() > 1) {
						System.out.println("parsing " + block);
						String[] properties = block.split(",");
						for (int num = 0; num < properties.length; num++) {
							if (num == 0) {
								blocktype = properties[0].charAt(0);
								continue;
							}
							System.out.println("parsing " + properties[num]);
							String[] elements = properties[num].split("=");
							String key = elements[0];
							String val = elements[1];
							switch (key) {
							default:
								System.out.println("Undefined key: " + key
										+ ", val: " + val);
							}
						}
					} else {
						blocktype = block.charAt(0);
					}
					determineBlock(blocktype, x, (height - y - 1));
					// TODO if ever adding properties to blocks, change this
					map[x][height - y - 1] = blocktype;
					x++;
				}
				y++;
			}

			addObjectsToWorld();
			return;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static void setHeight(int height) {
		if (height <= 0)
			throw new IllegalArgumentException();
		Level.height = height;
	}

	private static void setWidth(int width) {
		if (width <= 0)
			throw new IllegalArgumentException();
		Level.width = width;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

	public static Player getPlayer() {
		return player;
	}

	public static int getPlayerSpawnX(int id) {
		return playerSpawnX[id - 1];
	}

	public static int getPlayerSpawnY(int id) {
		return playerSpawnY[id - 1];
	}

	public static ArrayList<PlayerMP> getPlayersMP() {
		return players;
	}

	public static void addPacket(Packet p) {
		incomingPackets.add(p);
	}

	public static void addGameObject(GameObject go) {
		objects.add(go);
	}

	public static void removeGameObject(GameObject go) {
		objects.remove(go);
	}

	public static void parsePackets() {
		while (incomingPackets.size() > 0) {
			Packet packet = incomingPackets.get(0);

			if (packet instanceof PacketPlayerAdd) {
				addPlayerMP((PacketPlayerAdd) packet);
				Network.client.sendTCP(new PacketPlayerUpdateRequest(
						((PacketPlayerAdd) packet).id));
			} else if (packet instanceof PacketPlayerUpdate) {
				updatePlayerMP((PacketPlayerUpdate) packet);
			} else if (packet instanceof PacketPlayerUpdateRequest) {
				if (Level.getPlayer() != null) {
					Level.getPlayer().sendInfo();
				} else {
					incomingPackets.add(incomingPackets.remove(0));
					return;
				}
			} else if (packet instanceof PacketPlayerPlaceBomb) {
				placeBomb((PacketPlayerPlaceBomb) packet);
			} else if (packet instanceof PacketPlayerRemove) {
				removePlayer((PacketPlayerRemove) packet);
			} else if (packet instanceof PacketLevelRequest) {
				sendLevel((PacketLevelRequest) packet);
			} else if (packet instanceof PacketLevelResponse) {
				receiveLevel((PacketLevelResponse) packet);
			} else {
				System.err.println("Unknown packet: " + packet);
			}
			incomingPackets.remove(0);
		}
	}

	private static void receiveLevel(PacketLevelResponse packet) {
		char[][] map = packet.levelMap;
		loadFromMap(map);
		receiving = false;
		BombermanOnline.onReceivedLevel();
	}

	private static void sendLevel(PacketLevelRequest packet) {
		Network.client.sendTCP(new PacketLevelResponse(packet.receiverID, map));
		System.out.println("Sending level to " + packet.receiverID);
	}

	private static void placeBomb(PacketPlayerPlaceBomb packet) {
		Player source = getPlayer(packet.id);
		World.add(new Bomb(packet.x, packet.y, source, source.power, source.superBomb));
	}

	private static void updatePlayerMP(PacketPlayerUpdate ppu) {
		PlayerMP player = getPlayer(ppu.id);
		player.setX(ppu.x);
		player.setY(ppu.y);
		player.setDirection(ppu.directionID);
		player.setAnimation(ppu.animationID);
		player.step();
	}

	private static void addPlayerMP(PacketPlayerAdd ppa) {
		World.add(new ScrollingText("Player joined!", ScrollingText.VERTICAL,
				-1f, Color.green));
		PlayerMP newPlayer = new PlayerMP(ppa.id);
		players.add(newPlayer);
		World.add(newPlayer);
	}

	public static PlayerMP getPlayer(int id) {
		for (PlayerMP p : getPlayersMP()) {
			if (id == p.getID())
				return p;
		}
		return null;
	}

	public static void removePlayer(PacketPlayerRemove packet) {
		World.add(new ScrollingText("Player left!", ScrollingText.VERTICAL,
				-1f, Color.red));
		PlayerMP oldPlayer = getPlayer(packet.id);
		players.remove(oldPlayer);
		World.remove(oldPlayer);
	}

	public static void waitForLevelPacket() {
		receiving = true;
	}
}
