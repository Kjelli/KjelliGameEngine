package no.kjelli.bombline;

import java.io.IOException;

import org.newdawn.slick.Color;

import no.kjelli.bombline.gameobjects.particles.BombParticle;
import no.kjelli.bombline.levels.LevelWrapper;
import no.kjelli.bombline.menu.ConnectButton;
import no.kjelli.bombline.menu.InputConnect;
import no.kjelli.bombline.menu.HostButton;
import no.kjelli.bombline.menu.InputName;
import no.kjelli.bombline.menu.QuitButton;
import no.kjelli.bombline.network.Network;
import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gameobjects.Tagger;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.texts.TextScrolling;
import no.kjelli.generic.gfx.texts.TextStatic;
<<<<<<< HEAD
<<<<<<< HEAD
import no.kjelli.generic.gfx.textures.SpriteSheet;
import no.kjelli.generic.gamewrapper.GameWrapper;
=======
import no.kjelli.generic.main.Launcher;
>>>>>>> parent of aa02c52... Developed Pong Game at #it-dagene
=======
import no.kjelli.generic.main.GameWrapper;
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
import no.kjelli.generic.sound.SoundPlayer;

public class BombermanOnline implements Game {

	public static int tag_playfield = Tagger.uniqueTag();
	public static int block_size = 16;
<<<<<<< HEAD
	public static SpriteSheet partybombs;
=======
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong

	public static String name;

	public static enum STATE {
		INTRO, MENU, LOADING, PLAYING, WAITING_FOR_PLAYERS
	}

	public static STATE state;
	public static long ticks = 0;
	private static boolean isCloseRequested = false;

	@Override
	public void init() {
<<<<<<< HEAD
		loadSpritesheet();
=======
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
		loadSounds();
		Network.settings();
		initIntro();
	}
<<<<<<< HEAD
	
	public void loadSpritesheet(){
		partybombs = new SpriteSheet("res\\partybomb.png");
	}

	public void loadSounds() {
		try {
			SoundPlayer.load("bounce.wav");
<<<<<<< HEAD
=======
//			SoundPlayer.load("sound1.wav");
//			SoundPlayer.load("sound2.wav");
//			SoundPlayer.load("sound3.wav");
//			SoundPlayer.load("sound4.wav");
//			SoundPlayer.load("sound5.wav");
//			SoundPlayer.load("sound6.wav");
//			SoundPlayer.load("sound7.wav");
=======

	@Override
	public void loadSounds() {
		try {
			SoundPlayer.load("bounce.wav");
			// SoundPlayer.load("sound1.wav");
			// SoundPlayer.load("sound2.wav");
			// SoundPlayer.load("sound3.wav");
			// SoundPlayer.load("sound4.wav");
			// SoundPlayer.load("sound5.wav");
			// SoundPlayer.load("sound6.wav");
			// SoundPlayer.load("sound7.wav");
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
			SoundPlayer.load("sound8.wav");
			SoundPlayer.load("sound9 lose.wav");
			SoundPlayer.load("sound10 powerup.wav");
			SoundPlayer.load("sound11 bomb.wav");
			SoundPlayer.load("sound11 bomb long.wav");
			SoundPlayer.load("sound12.wav");
			SoundPlayer.load("sound13.wav");
<<<<<<< HEAD
>>>>>>> parent of aa02c52... Developed Pong Game at #it-dagene
=======
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static TextStatic nameLabel;
	public static InputName nameInput;
	public static HostButton hostButton;
	public static ConnectButton connectButton;
	public static TextStatic hostAddressLabel;
	public static InputConnect connectInput;
	public static QuitButton quitButton;

	public static void initIntro() {
		state = STATE.INTRO;
		World.clear();

		ticks = 0;
		World.init((int) getGameWidth(), (int) getGameHeight());
<<<<<<< HEAD
=======
		Screen.centerOn(null);
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
		Screen.zoom(2.0f);
		Screen.setX(0);
		Screen.setY(0);

		hostButton = new HostButton(Screen.getWidth() / 2 - HostButton.width
				/ 2, 2 * Screen.getHeight() / 3);
		World.add(hostButton);

		nameLabel = new TextStatic("NAME", Screen.getWidth() / 2
				- "NAME".length() * Sprite.CHAR_WIDTH / 2, 5
				* Screen.getHeight() / 12 + 2 * Sprite.CHAR_HEIGHT, Color.white);
		World.add(nameLabel);

<<<<<<< HEAD
		nameInput = new InputName(Screen.getWidth() / 2 - 250 / 2,
				5 * Screen.getHeight() / 12, 250, 20, 32);
=======
		nameInput = new InputName(Screen.getWidth() / 2 - 10
				* Sprite.CHAR_WIDTH / 2, 5 * Screen.getHeight() / 12);
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
		World.add(nameInput);

		hostAddressLabel = new TextStatic("HOST ADDRESS", Screen.getWidth() / 2
				- "HOST ADDRESS".length() * Sprite.CHAR_WIDTH / 2, 7
				* Screen.getHeight() / 24 + 2 * Sprite.CHAR_HEIGHT, Color.white);
		World.add(hostAddressLabel);

<<<<<<< HEAD
		connectInput = new InputConnect(Screen.getWidth() / 2 - 250 / 2,
				7 * Screen.getHeight() / 24, 250, 20, 32);
=======
		connectInput = new InputConnect(Screen.getWidth() / 2 - 32
				* Sprite.CHAR_WIDTH / 2, 7 * Screen.getHeight() / 24);
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
		World.add(connectInput);

		connectButton = new ConnectButton(Screen.getWidth() / 2
				- HostButton.width / 2, 5 * Screen.getHeight() / 6 - 1.5f
				* HostButton.height, connectInput, nameInput);
		World.add(connectButton);

		quitButton = new QuitButton(Screen.getWidth() / 2 - HostButton.width
				/ 2, 2 * Screen.getHeight() / 3 - 3 * HostButton.height);
		World.add(quitButton);
	}

	public static void initGame(String hostAddress, String name) {
		BombermanOnline.name = name;
		if (hostAddress == null) {
			if (Network.hostServer()) {
				World.clear();
<<<<<<< HEAD
<<<<<<< HEAD
=======

				LevelWrapper.load("level2");
>>>>>>> parent of aa02c52... Developed Pong Game at #it-dagene

				LevelWrapper.init("default");

				World.add(new TextScrolling("Hosting (" + Network.TCP_PORT
						+ ")"));
=======
				LevelWrapper.load("default");

>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
			}
		} else {
			if (Network.connect(hostAddress)) {
				World.clear();
				BombermanOnline.name = name;
				state = STATE.WAITING_FOR_PLAYERS;
			}
		}
	}

	@Override
	public void render() {
		Screen.render();
	}

	static int x = 0;
	static int y = 0;
	static int cycle = 600;
	static int offset = BombermanOnline.block_size;
	static int power = 1;

	@Override
	public void update() {
		ticks++;
		switch (state) {
		case INTRO:
			if (ticks % cycle == 1) {
				x = 0;
				y = 0;
				for (; x < this.getWidth() * Screen.scale; x += offset) {
					World.add(new BombParticle(x, y, power,
							Math.random() < 0.05));
				}
				x -= offset;
				for (; y < this.getHeight() * Screen.scale; y += offset) {
					World.add(new BombParticle(x, y, power,
							Math.random() < 0.05));
				}
				y -= offset;
				for (; x >= 0; x -= offset) {
					World.add(new BombParticle(x, y, power,
							Math.random() < 0.05));
				}
				x += offset;
				for (; y >= offset; y -= offset) {
					World.add(new BombParticle(x, y, power,
							Math.random() < 0.05));
				}
			}
			if (ticks % cycle == cycle / 2 + 1) {
				x = 0;
				y = 0;
				for (; x < this.getWidth() * Screen.scale; x += offset) {
					World.add(new BombParticle(x, y, power, false));
				}
				x -= offset;
				for (; y < this.getHeight() * Screen.scale - offset; y += offset) {
					World.add(new BombParticle(x, y, power, false));
				}
				for (; x >= 0; x -= offset) {
					World.add(new BombParticle(x, y, power,
							Math.random() < 0.05));
				}
				x += offset;
				for (; y >= offset; y -= offset) {
					World.add(new BombParticle(x, y, power,
							Math.random() < 0.05));
				}
			}
			break;
		case LOADING:
			break;
		case MENU:
			break;
		case PLAYING:
			break;
		default:
			break;
		}

		LevelWrapper.parsePackets();
		World.update();
		Screen.update();

	}

	public void destroy() {
		Network.cleanup();
		for (GameObject go : World.getObjects()) {
			go.destroy();
		}

	}

	public boolean isCloseRequested() {
		return isCloseRequested;
	}

	public static void requestClose() {
		isCloseRequested = true;
	}

	public static double getGameWidth() {
		return 640;
	}

	public static double getGameHeight() {
		return 480;
	}

	@Override
	public double getWidth() {
		return getGameWidth();
	}

	@Override
	public double getHeight() {
		return getGameHeight();
	}

	@Override
	public String getTitle() {
		return "OnlineTest";
	}

	public static void main(String[] args) {
<<<<<<< HEAD
<<<<<<< HEAD
		new GameWrapper(new BombermanOnline(), false);
=======
		Game game = new BombermanOnline();
		new Launcher(game, false);
>>>>>>> parent of aa02c52... Developed Pong Game at #it-dagene
=======
		Game game = new BombermanOnline();
		new GameWrapper(game, false);
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
	}

	public static void connect(boolean host) {
		if (nameInput.getText().trim().isEmpty()) {
			World.add(new TextScrolling("Name can not be empty!",
					TextScrolling.VERTICAL, TextScrolling.DEFAULT_SPEED,
					Color.red));
			return;
		}

		if (!host && connectInput.getText().trim().isEmpty()) {
			World.add(new TextScrolling("The IP Address can not be empty!",
					TextScrolling.VERTICAL, TextScrolling.DEFAULT_SPEED,
					Color.red));
			return;
		} else if (host) {
			initGame(null, nameInput.getText());
			return;
		}
		initGame(connectInput.getText(), nameInput.getText());
	}
<<<<<<< HEAD
<<<<<<< HEAD
=======
	
	public static void reset(){
		
	}
>>>>>>> parent of aa02c52... Developed Pong Game at #it-dagene
=======

	public static void reset() {
		if (LevelWrapper.getLevel() != null)
			LevelWrapper.endLevel();
		Network.cleanup();
		initIntro();
	}
>>>>>>> parent of 1023d03... Refactor and removal of other projects unrelated to pong
}
