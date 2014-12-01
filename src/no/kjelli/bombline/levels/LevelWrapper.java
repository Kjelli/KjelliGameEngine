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
import no.kjelli.generic.gfx.texts.TextFading;
import no.kjelli.generic.gfx.texts.TextFloating;
import no.kjelli.generic.gfx.texts.TextScrolling;
import no.kjelli.generic.gfx.texts.TextStatic;
import no.kjelli.generic.input.Input;
import no.kjelli.generic.input.InputListener;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;

public class LevelWrapper {
	private static final String HOST_PLAYERS_MESSAGE = "Waiting for players: ";
	private static final String HOST_MESSAGE = "Press space to play!";
	private static final String CLIENT_MESSAGE = "Waiting for host...";

	private static Level level;
	private static ArrayList<Packet> incomingPackets = new ArrayList<Packet>();

	private static boolean playing = false;
	private static boolean receiving = false;

	private static String lastLoadedLevel;
	private static TextFloating text;
	private static TextStatic playersJoinedText;
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
		LevelImports.loadFromFile(filename);
		lastLoadedLevel = filename;
		prepareLevel();
	}

	private static void prepareLevel() {

		World.pause(BombermanOnline.tag_playfield, true);
		World.init((int) level.getWidth(), (int) level.getHeight());
		level.addObjectsToWorld();

		// TODO validate/improve this zooming method
		Screen.zoom((float) Screen.getWidth() / (level.getWidth()));
		Screen.setX((level.getWidth()) / 2 - Screen.getWidth() / 2);
		Screen.setY((level.getHeight()) / 1.75f - Screen.getHeight() / 2);

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
		String pcs = getPlayersConnectedString();
		playersJoinedText = new TextStatic(pcs, Screen.getWidth() / 2
				- pcs.length() * Sprite.CHAR_WIDTH / 2, Screen.getHeight() / 5
				- 3 * Sprite.CHAR_HEIGHT, Color.white, true);

		World.add(playersJoinedText);

		level.getPlayer().setName(BombermanOnline.name);
	}

	public static void start() {
		World.hide(BombermanOnline.tag_playfield, false);
		World.pause(BombermanOnline.tag_playfield, false);

		level.getPlayer().displayName(false);
		for (Player p : level.getPlayersMP()) {
			p.displayName(false);
		}
		text.destroy();
		playersJoinedText.destroy();
	}

	public static void end() {
		level.end();
		Input.unregister(inputListener);
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
				if (level.getPlayer() != null) {
					level.getPlayer().sendInfo();
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
		if (level.getPlayerMP(ppc.id) != null)
			return;
		addPlayerMP(ppc);
		Network.getClient().sendTCP(new PacketPlayerUpdateRequest(ppc.id));

		updatePlayersConnected();

		// If the sender is a newly connected player, send hello back
		if (ppc.firstPacket) {
			Network.getClient().sendTCP(
					new PacketPlayerCredentials(Network.getClient().getID(),
							level.getPlayer().getName(), false));
		}
	}

	private static void updatePlayersConnected() {
		playersJoinedText.setText(getPlayersConnectedString());
	}

	private static String getPlayersConnectedString() {
		return HOST_PLAYERS_MESSAGE
				+ String.format("%d/%d", (level.getPlayersMP().size() + 1),
						level.getMaxPlayers());
	}

	private static void loseLife(PacketPlayerLoseLife packet) {
		if (packet.id == Network.getClient().getID()) {
			level.getPlayer().loseLife();
		} else {
			level.getPlayerMP(packet.id).loseLife();
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
			powerup.powerUp(level.getPlayer());
			// TODO Highlighting player when aquiring a powerup. Change to sfx
			// or more appealing gfx
			level.getPlayer().debug_highlight();

			powerup.destroy();
		} else {
			powerup.powerUp(level.getPlayerMP(packet.id));
			// TODO Highlighting player when aquiring a powerup. Change to sfx
			// or more appealing gfx
			level.getPlayerMP(packet.id).debug_highlight();
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
		LevelImports.loadFromMap(packet);
		setReceiving(false);
		prepareLevel();
	}

	private static void sendLevel(PacketLevelRequest packet) {
		Network.getClient().sendTCP(
				new PacketLevelResponse(packet.receiverID, level.getMap(),
						level.getMaxPlayers()));
	}

	private static void placeBomb(PacketPlayerPlaceBomb packet) {
		Player source = level.getPlayerMP(packet.id);
		World.add(new Bomb(packet.x, packet.y, source, source.getPower(),
				source.hasSuperBomb()));
	}

	private static void updatePlayerMP(PacketPlayerUpdate ppu) {
		PlayerMP player = level.getPlayerMP(ppu.id);
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
		level.newPlayer(newPlayer);
		World.add(newPlayer);
	}

	public static void removePlayer(PacketPlayerRemove packet) {
		PlayerMP oldPlayer = level.getPlayerMP(packet.id);

		World.add(new TextFading(oldPlayer.getName() + " left!", Screen
				.getCenterX()
				- (oldPlayer.getName() + " left!").length()
				* Sprite.CHAR_WIDTH / 2, Screen.getHeight() * 3 / 4
				- Sprite.CHAR_HEIGHT / 2, Color.red, 100));
		updatePlayersConnected();

		level.removePlayer(oldPlayer);
		oldPlayer.destroy();
	}

	public static boolean isReceiving() {
		return receiving;
	}

	public static void setReceiving(boolean receiving) {
		LevelWrapper.receiving = receiving;
	}

}
