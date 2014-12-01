package no.kjelli.bombline.levels;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import no.kjelli.bombline.BombermanOnline;
import no.kjelli.bombline.BombermanOnline.STATE;
import no.kjelli.bombline.gameobjects.Block;
import no.kjelli.bombline.gameobjects.Bomb;
import no.kjelli.bombline.gameobjects.Destructible;
import no.kjelli.bombline.gameobjects.Floor;
import no.kjelli.bombline.gameobjects.Player;
import no.kjelli.bombline.gameobjects.PlayerMP;
import no.kjelli.bombline.gameobjects.powerups.Powerup;
import no.kjelli.bombline.gameobjects.powerups.PowerupBomb;
import no.kjelli.bombline.gameobjects.powerups.PowerupFire;
import no.kjelli.bombline.gameobjects.powerups.PowerupSpeed;
import no.kjelli.bombline.network.Network;
import no.kjelli.bombline.network.Packet;
import no.kjelli.bombline.network.PacketLevelRequest;
import no.kjelli.bombline.network.PacketLevelResponse;
import no.kjelli.bombline.network.PacketLevelStart;
import no.kjelli.bombline.network.PacketPlayerCredentials;
import no.kjelli.bombline.network.PacketPlayerLoseLife;
import no.kjelli.bombline.network.PacketPlayerPlaceBomb;
import no.kjelli.bombline.network.PacketPlayerRemove;
import no.kjelli.bombline.network.PacketPlayerUpdate;
import no.kjelli.bombline.network.PacketPlayerUpdateRequest;
import no.kjelli.bombline.network.PacketPowerupGain;
import no.kjelli.bombline.network.PacketPowerupSpawn;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.Collidable;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.TextScrolling;
import no.kjelli.generic.gfx.TextFloating;
import no.kjelli.generic.input.Input;
import no.kjelli.generic.input.InputListener;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;

public class Level {
	public static final char FLOOR = '.', BLOCK = '#', DESTRUCTIBLE = 'X',
			PLAYER_ONE = '1', PLAYER_TWO = '2', PLAYER_THREE = '3',
			PLAYER_FOUR = '4';
	private static final String HOST_MESSAGE = "Press space to play!";
	private static final String CLIENT_MESSAGE = "Waiting for host...";

	private static int width, height;
	private static int playerSpawnX[] = new int[4];
	private static int playerSpawnY[] = new int[4];
	private static Player player;
	private static ArrayList<GameObject> objects;

	private static ArrayList<PlayerMP> players = new ArrayList<PlayerMP>();
	private static ArrayList<Packet> incomingPackets = new ArrayList<Packet>();
	private static char[][] map;

	private static boolean playing = false;

	private static String lastLoadedLevel;
	private static TextFloating text;
	private static InputListener inputListener = new InputListener() {

		@Override
		public void keyUp(int eventKey) {
			if (eventKey == Keyboard.KEY_F2) {
				playing = false;
				Level.end();
				BombermanOnline.initIntro();
			} else if (eventKey == Keyboard.KEY_SPACE && !playing) {
				if (Network.isHosting()) {
					BombermanOnline.state = STATE.PLAYING;
					playing = true;
					Level.start();
					Network.getServer()
							.sendToAllExceptTCP(Network.getClient().getID(),
									new PacketLevelStart());
				}
			}
			if (eventKey == Keyboard.KEY_V) {
				getPlayer().displayName(false);
				for (Player p : getPlayersMP()) {
					p.displayName(false);
				}
			}

		}

		@Override
		public void keyDown(int eventKey) {
			if (eventKey == Keyboard.KEY_V) {
				getPlayer().displayName(true);
				for (Player p : getPlayersMP()) {
					p.displayName(true);
				}
			}

		}
	};

	public static void init() {
		init(lastLoadedLevel);
	}

	public static void init(String filename) {
		loadFromFile(filename);
		lastLoadedLevel = filename;
		prepareLevel();
	}

	private static void prepareLevel() {
		World.pause(BombermanOnline.tag_playfield, true);
		World.init((int) getWidth(), (int) getHeight());

		// TODO validate/improve this method
		Screen.zoom((float) Screen.getWidth() / (Level.getWidth()));
		Screen.setX((Level.getWidth()) / 2 - Screen.getWidth() / 2);
		Screen.setY((Level.getHeight()) / 1.75f - Screen.getHeight() / 2);

		Input.register(inputListener);
		text = new TextFloating(Network.isHosting() ? HOST_MESSAGE
				: CLIENT_MESSAGE,
				Screen.getWidth()
						/ 2
						- (Network.isHosting() ? HOST_MESSAGE : CLIENT_MESSAGE)
								.length() * Sprite.CHAR_WIDTH / 2,
				Screen.getHeight() / 2 - Sprite.CHAR_HEIGHT / 2, Color.white,
				true);
		World.add(text);

		getPlayer().setName(BombermanOnline.name);
	}

	private static void addObjectsToWorld() {
		for (GameObject b : objects)
			World.add(b);
	}

	public static void start() {
		World.hide(BombermanOnline.tag_playfield, false);
		World.pause(BombermanOnline.tag_playfield, false);

		getPlayer().displayName(false);
		for (Player p : players) {
			p.displayName(false);
		}
		text.destroy();
	}

	public static void end() {
		for (GameObject b : objects)
			b.destroy();
		objects.clear();
		Input.unregister(inputListener);
	}

	private static void loadFromMap(char[][] map) {
		objects = new ArrayList<>();
		setWidth(map.length * BombermanOnline.block_size);
		setHeight(map[0].length * BombermanOnline.block_size);
		for (int y = 0; y < map[0].length; y++) {
			for (int x = 0; x < map.length; x++) {
				char blocktype = map[x][y];
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
			addPlayerSpawn(1, x, y);
			break;
		case PLAYER_TWO:
			addPlayerSpawn(2, x, y);
			break;
		case PLAYER_THREE:
			addPlayerSpawn(3, x, y);
			break;
		case PLAYER_FOUR:
			addPlayerSpawn(4, x, y);
			break;
		case FLOOR:
			objects.add(new Floor(x, y));
			break;
		default:
			break;
		}
	}

	private static void addPlayerSpawn(int playerNo, int x, int y) {
		playerSpawnX[playerNo - 1] = x;
		playerSpawnY[playerNo - 1] = y;
		if (Network.isHosting() && playerNo == 1) {
			player = new Player(x, y);
			objects.add(player);
		} else if (Network.isOnline()
				&& playerNo == Network.getClient().getID()) {
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
			// System.out.println(packet.getClass().getSimpleName());

			if (packet instanceof PacketPlayerCredentials) {
				PacketPlayerCredentials ppc = (PacketPlayerCredentials) packet;
				addPlayerMP(ppc);
				Network.getClient().sendTCP(
						new PacketPlayerUpdateRequest(ppc.id));

				// If the sender is a newly connected player, send information
				// about self
				if (ppc.firstPacket) {
					Network.getClient().sendTCP(
							new PacketPlayerCredentials(Network.getClient()
									.getID(), getPlayer().getName(), false));
				}
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
			} else if (packet instanceof PacketPowerupSpawn) {
				spawnPowerup((PacketPowerupSpawn) packet);
			} else if (packet instanceof PacketPowerupGain) {
				handlePowerup((PacketPowerupGain) packet);
			} else if (packet instanceof PacketPlayerLoseLife) {
				loseLife((PacketPlayerLoseLife) packet);
			} else if (packet instanceof PacketLevelStart) {
				start();
			} else {
				System.err.println("Unknown packet: " + packet);
			}
			incomingPackets.remove(0);
		}
	}

	private static void loseLife(PacketPlayerLoseLife packet) {
		if (packet.id == Network.getClient().getID()) {
			getPlayer().loseLife();
		} else {
			getPlayer(packet.id).loseLife();
		}
	}

	// TODO easier and cheaper way to do this? ._. (KryoSerial, powerup array)
	// TODO well works fine anyway...
	private static void handlePowerup(PacketPowerupGain packet) {
		HashSet<Collidable> returnObjects = new HashSet<Collidable>();
		World.retrieveCollidables(returnObjects, new Rectangle(packet.x_index
				* BombermanOnline.block_size - 1 + Powerup.SPRITE_WIDTH / 2,
				packet.y_index * BombermanOnline.block_size - 1
						+ Powerup.SPRITE_WIDTH / 2, 2, 2));
		Powerup powerup = null;
		for (Collidable c : returnObjects) {
			if (c instanceof Powerup) {
				if (((Powerup) c).getType() == packet.type)
					powerup = (Powerup) c;
			}
		}
		if (powerup == null) {
			System.err.println("Could not handle the powerup?");
			return;
		}

		if (packet.id == Network.getClient().getID()) {
			powerup.powerUp(Level.getPlayer());
			getPlayer().debug_highlight();

			powerup.destroy();
		} else {
			powerup.powerUp(Level.getPlayer(packet.id));
			getPlayer(packet.id).debug_highlight();
			powerup.destroy();
		}
	}

	private static void spawnPowerup(PacketPowerupSpawn packet) {
		switch (packet.type) {
		case Powerup.BOMB:
			World.add(new PowerupBomb(packet.x, packet.y));
			break;
		case Powerup.FIRE:
			World.add(new PowerupFire(packet.x, packet.y));
			break;
		case Powerup.SPEED:
			World.add(new PowerupSpeed(packet.x, packet.y));
			break;
		}

	}

	private static void receiveLevel(PacketLevelResponse packet) {
		char[][] map = packet.levelMap;
		loadFromMap(map);
		prepareLevel();
	}

	private static void sendLevel(PacketLevelRequest packet) {
		Network.getClient().sendTCP(
				new PacketLevelResponse(packet.receiverID, map));
	}

	private static void placeBomb(PacketPlayerPlaceBomb packet) {
		Player source = getPlayer(packet.id);
		World.add(new Bomb(packet.x, packet.y, source, source.getPower(),
				source.hasSuperBomb()));
	}

	private static void updatePlayerMP(PacketPlayerUpdate ppu) {
		PlayerMP player = getPlayer(ppu.id);
		player.updateMP(ppu.x, ppu.y, ppu.directionID, ppu.animationID);
	}

	private static void addPlayerMP(PacketPlayerCredentials ppa) {
		World.add(new TextScrolling(ppa.name + " joined!",
				TextScrolling.VERTICAL, -1f, Color.green));

		PlayerMP newPlayer = new PlayerMP(ppa.id, ppa.name);
		System.out.println(ppa.name + " was added");
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
		World.add(new TextScrolling("Player left!", TextScrolling.VERTICAL,
				-1f, Color.red));
		PlayerMP oldPlayer = getPlayer(packet.id);
		players.remove(oldPlayer);
		World.remove(oldPlayer);
	}

}
