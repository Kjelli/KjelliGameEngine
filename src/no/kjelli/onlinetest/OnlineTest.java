package no.kjelli.onlinetest;

import static org.lwjgl.input.Keyboard.KEY_Q;

import java.io.IOException;

import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gameobjects.Tagger;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.main.Launcher;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.onlinetest.gameobjects.particles.BombParticle;
import no.kjelli.onlinetest.levels.Level;
import no.kjelli.onlinetest.menu.ConnectButton;
import no.kjelli.onlinetest.menu.HostButton;
import no.kjelli.onlinetest.menu.QuitButton;
import no.kjelli.onlinetest.network.Network;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

public class OnlineTest implements Game {

	public static int tag_playfield = Tagger.uniqueTag();
	public static int block_size = 16;

	public static enum STATE {
		INTRO, MENU, LOADING, PLAYING
	}

	public static STATE state;
	public static long ticks = 0;

	@Override
	public void init() {
		loadSounds();
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
		Screen.zoom(2.0f);
		World.add(new HostButton(Screen.getWidth() / 2 - HostButton.width / 2,
				2 * Screen.getHeight() / 3));
		World.add(new ConnectButton(Screen.getWidth() / 2 - HostButton.width
				/ 2, 2 * Screen.getHeight() / 3 - 1.5f * HostButton.height));
		World.add(new QuitButton(Screen.getWidth() / 2 - HostButton.width / 2,
				2 * Screen.getHeight() / 3 - 3 * HostButton.height));
	}

	public static void initGame(boolean hosting) {
		state = STATE.PLAYING;
		World.clear();
		Screen.setBackgroundColor(new Color(0.0f, 0.0f, 0.0f));
		Level.init("level");
		Level.start();
		Screen.zoom((float) Screen.getWidth() / (Level.getWidth()));
		Screen.setX((Level.getWidth()) / 2 - Screen.getWidth() / 2);
		Screen.setY((Level.getHeight()) / 1.75f - Screen.getHeight() / 2);
		if (hosting) {
			Network.hostServer();
		} else {
			// TODO join a specified server;
			Network.connect();
		}
	}

	@Override
	public void render() {
		Screen.render();
	}

	@Override
	public void getInput() {
		Keyboard.poll();

		while (Keyboard.next()) {
			int key = Keyboard.getEventKey();
			boolean state = Keyboard.getEventKeyState();
			switch (key) {
			case KEY_Q:
				if (state)
					Screen.toggleDebugDraw();
				break;
			default:
				break;
			}
		}
	}

	static int x = 0;
	static int y = 0;
	static int cycle = 300;
	static int offset = OnlineTest.block_size;
	static int power = 5;

	@Override
	public void update() {
		ticks++;
		switch (state) {
		case INTRO:
			if (ticks % cycle == 1) {
				for (; x < Screen.getWidth(); x += offset) {
					World.add(new BombParticle(x, y, power, x == 0));
				}
				x -= offset;
				for (; y < Screen.getHeight(); y += offset) {
					World.add(new BombParticle(x, y, power, false));
				}
				y -= offset;
				for (; x >= 0; x -= offset) {
					World.add(new BombParticle(x, y, power, false));
				}
				x += offset;
				for (; y >= offset; y -= offset) {
					World.add(new BombParticle(x, y, power, false));
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

	@Override
	public void destroy() {
		Network.cleanup();
		for (GameObject go : World.getObjects()) {
			go.destroy();
		}

	}

	public static void main(String[] args) {
		new Launcher(new OnlineTest(), "OnlineTest", 640, 480, false);
	}

	public static void onReceivedLevel() {
		state = STATE.PLAYING;
		Level.start();
	}
}
