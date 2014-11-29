package no.kjelli.bombline;

import java.io.IOException;

import org.newdawn.slick.Color;

import no.kjelli.bombline.gameobjects.particles.BombParticle;
import no.kjelli.bombline.levels.Level;
import no.kjelli.bombline.menu.ConnectButton;
import no.kjelli.bombline.menu.ConnectInput;
import no.kjelli.bombline.menu.HostButton;
import no.kjelli.bombline.menu.QuitButton;
import no.kjelli.bombline.network.Network;
import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gameobjects.Tagger;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.gfx.ScrollingText;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.StaticText;
import no.kjelli.generic.input.Input;
import no.kjelli.generic.main.Launcher;
import no.kjelli.generic.sound.SoundPlayer;

public class BombermanOnline implements Game {

	public static int tag_playfield = Tagger.uniqueTag();
	public static int block_size = 16;

	public static enum STATE {
		INTRO, MENU, LOADING, PLAYING
	}

	public static STATE state;
	public static long ticks = 0;
	private static boolean isCloseRequested = false;

	@Override
	public void init() {
		loadSounds();
		Network.settings();
		initIntro();
	}

	@Override
	public void loadSounds() {
		try {
			SoundPlayer.load("bounce.wav");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initIntro() {
		state = STATE.INTRO;
		World.clear();
		Input.clear();

		Screen.zoom(2.0f);

		World.add(new HostButton(Screen.getWidth() / 2 - HostButton.width / 2,
				2 * Screen.getHeight() / 3));
		World.add(new StaticText("HOST ADDRESS", Screen.getWidth() / 2
				- "HOST ADDRESS".length() * Sprite.CHAR_WIDTH / 2, 2
				* Screen.getHeight() / 6 + 2 * Sprite.CHAR_HEIGHT, Color.white));
		ConnectInput connectInput = new ConnectInput(
				Screen.getWidth() / 2 - 250 / 2, 2 * Screen.getHeight() / 6,
				250, 20, 32);
		World.add(connectInput);
		World.add(new ConnectButton(Screen.getWidth() / 2 - HostButton.width
				/ 2, 5 * Screen.getHeight() / 6 - 1.5f * HostButton.height,
				connectInput));
		World.add(new QuitButton(Screen.getWidth() / 2 - HostButton.width / 2,
				2 * Screen.getHeight() / 3 - 3 * HostButton.height));
	}

	public static void initGame(String hostAddress) {
		if (hostAddress != null && hostAddress.trim().isEmpty()) {
			World.add(new ScrollingText("The IP Address can not be empty!",
					ScrollingText.VERTICAL, ScrollingText.DEFAULT_SPEED,
					Color.red));
			return;
		}
		if (hostAddress == null) {
			if (Network.hostServer()) {
				World.clear();
				Input.clear();

				Level.init("level");
				Level.start();
				state = STATE.PLAYING;
				World.add(new ScrollingText("Hosting on port "
						+ Network.TCP_PORT));
			}
		} else {
			if (Network.connect(hostAddress)) {
				World.clear();
				Input.clear();
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

		Level.parsePackets();
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

	public static void onReceivedLevel() {
		state = STATE.PLAYING;
		Level.start();
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
		Game game = new BombermanOnline();
		new Launcher(game, false);
	}
}
