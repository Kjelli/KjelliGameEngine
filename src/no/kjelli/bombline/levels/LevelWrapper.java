package no.kjelli.bombline.levels;

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
=======
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
import java.util.ArrayList;
import java.util.HashSet;

import no.kjelli.bombline.BombermanOnline;
<<<<<<< HEAD
import no.kjelli.bombline.BombermanOnline.STATE;
import no.kjelli.bombline.gameobjects.Block;
import no.kjelli.bombline.gameobjects.Bomb;
import no.kjelli.bombline.gameobjects.Destructible;
import no.kjelli.bombline.gameobjects.Floor;
=======
import no.kjelli.bombline.gameobjects.Bomb;
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
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
<<<<<<< HEAD
import no.kjelli.generic.gameobjects.GameObject;
=======
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.texts.TextFading;
import no.kjelli.generic.gfx.texts.TextFloating;
<<<<<<< HEAD
import no.kjelli.generic.gfx.texts.TextScrolling;
=======
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
import no.kjelli.generic.gfx.texts.TextStatic;
import no.kjelli.generic.input.Input;
import no.kjelli.generic.input.InputListener;

<<<<<<< HEAD
import org.lwjgl.input.Keyboard;
=======
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;

public class LevelWrapper {
	private static final String HOST_PLAYERS_MESSAGE = "Waiting for players: ";
	private static final String HOST_MESSAGE = "Press space to play!";
	private static final String CLIENT_MESSAGE = "Waiting for host...";

	private static Level level;
<<<<<<< HEAD
	private static ArrayList<Packet> incomingPackets = new ArrayList<Packet>();

	private static boolean playing = false;
	private static boolean receiving = false;
=======
	private static final ArrayList<Packet> incomingPackets = new ArrayList<Packet>();

	private static boolean receiving = false;
	private static boolean displayingNames = false;
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong

	private static String lastLoadedLevel;
	private static TextFloating text;
	private static TextStatic playersJoinedText;
<<<<<<< HEAD
	private static InputListener inputListener = new InputListener() {

		@Override
		public void keyUp(int eventKey) {
			if (eventKey == Keyboard.KEY_F2) {
				playing = false;
				LevelWrapper.end();
				BombermanOnline.initIntro();
			} else if (eventKey == Keyboard.KEY_SPACE && !playing) {
				if (Network.isHosting()) {
					BombermanOnline.state = STATE.PLAYING;
					playing = true;
					LevelWrapper.start();
					Network.getServer()
							.sendToAllExceptTCP(Network.getClient().getID(),
									new PacketLevelStart());
				}
			}
			if (eventKey == Keyboard.KEY_V) {
				getLevel().getPlayer().displayName(false);
				for (Player p : getLevel().getPlayersMP()) {
					p.displayName(false);
				}
			}

		}

		@Override
		public void keyDown(int eventKey) {
			if (eventKey == Keyboard.KEY_V) {
				getLevel().getPlayer().displayName(true);
				for (Player p : getLevel().getPlayersMP()) {
					p.displayName(true);
				}
			}

		}
	};

	public static void init() {
		init(lastLoadedLevel);
	}

	public static void init(String filename) {
		LevelImports.loadFromFile(filename);
=======
	private static final InputListener inputListener = new LevelInputListener();

	public static void load() {
		load(lastLoadedLevel);
	}

	public static void load(String filename) {
		if (level != null)
			level.end();
		level = LevelImports.loadFromFile(filename);
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
		lastLoadedLevel = filename;
		prepareLevel();
	}

	private static void prepareLevel() {
<<<<<<< HEAD
		World.pause(BombermanOnline.tag_playfield, true);
		World.init((int) getLevel().getWidth(), (int) getLevel().getHeight());
		getLevel().addObjectsToWorld();

		// TODO validate/improve this zooming method
		Screen.zoom((float) Screen.getWidth() / (getLevel().getWidth()));
		Screen.setX((getLevel().getWidth()) / 2 - Screen.getWidth() / 2);
		Screen.setY((getLevel().getHeight()) / 1.75f - Screen.getHeight() / 2);

		Input.register(inputListener);
=======

		World.pause(BombermanOnline.tag_playfield, true);
		World.init((int) level.getWidth(), (int) level.getHeight());

		level.addObjectsToWorld();

		screenSettings();
		textSettings();
		Input.register(inputListener);

		level.getPlayer().setName(BombermanOnline.name);
	}

	private static void textSettings() {
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
		text = new TextFloating(Network.isHosting() ? HOST_MESSAGE
				: CLIENT_MESSAGE,
				Screen.getWidth()
						/ 2
						- (Network.isHosting() ? HOST_MESSAGE : CLIENT_MESSAGE)
								.length() * Sprite.CHAR_WIDTH / 2,
				Screen.getHeight() / 2 - Sprite.CHAR_HEIGHT / 2, Color.white,
				true);
		World.add(text);
		String pcs = getPlayersConnectedString();
		playersJoinedText = new TextStatic(pcs, Screen.getWidth() / 2
				- pcs.length() * Sprite.CHAR_WIDTH / 2, Screen.getHeight() / 5
				- 3 * Sprite.CHAR_HEIGHT, Color.white, true);

		World.add(playersJoinedText);
<<<<<<< HEAD

		getLevel().getPlayer().setName(BombermanOnline.name);
=======
	}

	private static void screenSettings() {
		if (level.getWidth() > Screen.getWidth()
				|| level.getHeight() > Screen.getHeight()) {
			Screen.centerOn(level.getPlayer(), 1);
		} else {
			Screen.setX((level.getWidth()) / 2 - Screen.getWidth() / 2);
			Screen.setY((level.getHeight()) / 1.75f - Screen.getHeight() / 2);
		}
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
	}

	public static void start() {
		World.hide(BombermanOnline.tag_playfield, false);
		World.pause(BombermanOnline.tag_playfield, false);

<<<<<<< HEAD
		getLevel().getPlayer().displayName(false);
		for (Player p : getLevel().getPlayersMP()) {
=======
		level.getPlayer().displayName(false);
		for (Player p : level.getPlayersMP()) {
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
			p.displayName(false);
		}
		text.destroy();
		playersJoinedText.destroy();
	}

<<<<<<< HEAD
	public static void end() {
<<<<<<< HEAD
		getLevel().end();
=======
		level.end();
>>>>>>> parent of aa02c52... Developed Pong Game at #it-dagene
		Input.unregister(inputListener);
=======
	public static void endLevel() {
		level.end();
		level = null;
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
	}

	public static void addPacket(Packet p) {
		incomingPackets.add(p);
	}

	public static void parsePackets() {
		while (incomingPackets.size() > 0) {
			Packet packet = incomingPackets.get(0);
			// System.out.println(packet.getClass().getSimpleName());

			if (packet instanceof PacketPlayerCredentials) {
				if (isReceiving()) {
					// If client is yet to receive level, postpone the handling
					// of this packet
					incomingPackets.add(incomingPackets.remove(0));
					return;
				} else {
					addPlayer(((PacketPlayerCredentials) packet));
				}
			} else if (packet instanceof PacketPlayerUpdate) {
				updatePlayerMP((PacketPlayerUpdate) packet);
			} else if (packet instanceof PacketPlayerUpdateRequest) {
<<<<<<< HEAD
				if (getLevel().getPlayer() != null) {
					getLevel().getPlayer().sendInfo();
=======
				if (level != null && level.getPlayer() != null) {
					level.getPlayer().sendInfo();
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
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

	private static void addPlayer(PacketPlayerCredentials ppc) {
		// Player already exists in our game, no need to add
<<<<<<< HEAD
		if (getLevel().getPlayerMP(ppc.id) != null)
=======
		if (level.getPlayerMP(ppc.id) != null)
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
			return;
		addPlayerMP(ppc);
		Network.getClient().sendTCP(new PacketPlayerUpdateRequest(ppc.id));

		updatePlayersConnected();

		// If the sender is a newly connected player, send hello back
		if (ppc.firstPacket) {
			Network.getClient().sendTCP(
					new PacketPlayerCredentials(Network.getClient().getID(),
<<<<<<< HEAD
							getLevel().getPlayer().getName(), false));
=======
							level.getPlayer().getName(), false));
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
		}
	}

	private static void updatePlayersConnected() {
		playersJoinedText.setText(getPlayersConnectedString());
	}

	private static String getPlayersConnectedString() {
		return HOST_PLAYERS_MESSAGE
<<<<<<< HEAD
				+ String.format("%d/%d", (getLevel().getPlayersMP().size() + 1),
						getLevel().getMaxPlayers());
=======
				+ String.format("%d/%d", (level.getPlayersMP().size() + 1),
						level.getMaxPlayers());
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
	}

	private static void loseLife(PacketPlayerLoseLife packet) {
		if (packet.id == Network.getClient().getID()) {
<<<<<<< HEAD
			getLevel().getPlayer().loseLife();
		} else {
			getLevel().getPlayerMP(packet.id).loseLife();
=======
			level.getPlayer().loseLife();
		} else {
			level.getPlayerMP(packet.id).loseLife();
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
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
<<<<<<< HEAD
			powerup.powerUp(getLevel().getPlayer());
			// TODO Highlighting player when aquiring a powerup. Change to sfx
			// or more appealing gfx

			powerup.destroy();
		} else {
			powerup.powerUp(getLevel().getPlayerMP(packet.id));
			// TODO Highlighting player when aquiring a powerup. Change to sfx
			// or more appealing gfx
			powerup.destroy();
=======
			powerup.collect(level.getPlayer());

		} else {
			powerup.collect(level.getPlayerMP(packet.id));
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
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
<<<<<<< HEAD
		LevelImports.loadFromMap(packet);
=======
		if (level != null)
			level.end();

		level = LevelImports.loadFromMap(packet);
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
		setReceiving(false);
		prepareLevel();
	}

	private static void sendLevel(PacketLevelRequest packet) {
		Network.getClient().sendTCP(
<<<<<<< HEAD
				new PacketLevelResponse(packet.receiverID, getLevel().getMap(),
						getLevel().getMaxPlayers()));
	}

	private static void placeBomb(PacketPlayerPlaceBomb packet) {
		Player source = getLevel().getPlayerMP(packet.id);
=======
				new PacketLevelResponse(packet.receiverID, level.getMap(),
						level.getMaxPlayers()));
	}

	private static void placeBomb(PacketPlayerPlaceBomb packet) {
		Player source = level.getPlayerMP(packet.id);
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
		World.add(new Bomb(packet.x, packet.y, source, source.getPower(),
				source.hasSuperBomb()));
	}

	private static void updatePlayerMP(PacketPlayerUpdate ppu) {
<<<<<<< HEAD
		PlayerMP player = getLevel().getPlayerMP(ppu.id);
=======
		PlayerMP player = level.getPlayerMP(ppu.id);
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
		player.updateMP(ppu.x, ppu.y, ppu.directionID, ppu.animationID);
	}

	private static void addPlayerMP(PacketPlayerCredentials ppa) {
		System.out.println(ppa.name + " joined");
		World.add(new TextFading(ppa.name + " joined!", Screen.getCenterX()
				- (ppa.name + " joined!").length() * Sprite.CHAR_WIDTH / 2,
				Screen.getHeight() * 3 / 4 - Sprite.CHAR_HEIGHT / 2,
				Color.green, 100));

		PlayerMP newPlayer = new PlayerMP(ppa.id, ppa.name);
		System.out.println(ppa.name + " was added");
<<<<<<< HEAD
		getLevel().newPlayer(newPlayer);
=======
		level.newPlayer(newPlayer);
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
		World.add(newPlayer);
	}

	public static void removePlayer(PacketPlayerRemove packet) {
<<<<<<< HEAD
		PlayerMP oldPlayer = getLevel().getPlayerMP(packet.id);
=======
		PlayerMP oldPlayer = level.getPlayerMP(packet.id);
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong

		World.add(new TextFading(oldPlayer.getName() + " left!", Screen
				.getCenterX()
				- (oldPlayer.getName() + " left!").length()
				* Sprite.CHAR_WIDTH / 2, Screen.getHeight() * 3 / 4
				- Sprite.CHAR_HEIGHT / 2, Color.red, 100));
		updatePlayersConnected();

<<<<<<< HEAD
		getLevel().removePlayer(oldPlayer);
=======
		level.removePlayer(oldPlayer);
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
		oldPlayer.destroy();
	}

	public static boolean isReceiving() {
		return receiving;
	}

	public static void setReceiving(boolean receiving) {
		LevelWrapper.receiving = receiving;
	}

	public static Level getLevel() {
		return level;
	}
<<<<<<< HEAD
<<<<<<< HEAD

	public static void setLevel(Level level) {
		LevelWrapper.level = level;
	}
=======
>>>>>>> parent of aa02c52... Developed Pong Game at #it-dagene
=======
	
	public static void toggleNameDisplay() {
		displayingNames = !displayingNames;
		getLevel().getPlayer().displayName(displayingNames);
		for (Player p : getLevel().getPlayersMP()) {
			p.displayName(displayingNames);
		}
	}
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong

}
