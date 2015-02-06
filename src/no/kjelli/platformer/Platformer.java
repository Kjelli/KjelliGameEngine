package no.kjelli.platformer;

import static org.lwjgl.input.Keyboard.*;

import java.io.IOException;

import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gameobjects.Tagger;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.main.GameWrapper;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.platformer.gameobjects.MovingOnewayPlatform;
import no.kjelli.platformer.gameobjects.OnewayPlatform;
import no.kjelli.platformer.gameobjects.Player;

import org.lwjgl.input.Keyboard;

public class Platformer implements Game {
	public static int tag_playfield = Tagger.uniqueTag();

	public static enum STATE {
		INTRO, MENU, LOADING, PLAYING
	}

	public static STATE state;
	public static long ticks = 0;
	private static boolean isCloseRequested = false;

	@Override
	public void init() {
		loadSounds();
		initIntro();
		initGame();
	}

	@Override
	public void loadSounds() {
		try {
			SoundPlayer.load("bounce.wav");
			SoundPlayer.load("win.wav");
			SoundPlayer.load("coin.wav");
			SoundPlayer.load("combo.wav");
			SoundPlayer.load("wrong.wav");
			SoundPlayer.load("right.wav");
			SoundPlayer.load("lose.wav");
			SoundPlayer.load("music.wav");
			SoundPlayer.load("comboexpire.wav");
			SoundPlayer.load("musicadd.wav");
			SoundPlayer.load("musicbeat.wav");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initIntro() {
		World.clear();
		state = STATE.INTRO;
	}

	public void initGame() {
		World.clear();
		state = STATE.PLAYING;

		World.add(new Player(Screen.getCenterX(), Screen.getCenterY()));
		World.add(new OnewayPlatform(Screen.getWidth() / 6,
				Screen.getHeight() / 4, 2 * Screen.getWidth() / 3, 20));
		World.add(new OnewayPlatform(0, Screen.getHeight() / 3, Screen
				.getWidth() / 3, 80));
		World.add(new OnewayPlatform(Screen.getCenterX() - 120, Screen
				.getHeight() / 5 + 100, 60, 10));
		World.add(new MovingOnewayPlatform(Screen.getCenterX() - 120, Screen
				.getHeight() / 5 + 130, 60, 10));
		Screen.zoom(4.0f);
	}

	@Override
	public void render() {
		Screen.render();
	}

	@Override
	public void update() {
		ticks++;
		switch (state) {
		case INTRO:
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

		World.update();
		Screen.update();

	}

	@Override
	public void destroy() {
		for (GameObject go : World.getObjects()) {
			go.destroy();
		}
	}

	@Override
	public boolean isCloseRequested() {
		return isCloseRequested;
	}

	@Override
	public double getWidth() {
		return 640;
	}

	@Override
	public double getHeight() {
		return 480;
	}

	@Override
	public String getTitle() {
		return "Platformer";
	}

	public static void main(String[] args) {
		new GameWrapper(new Platformer(), false);
	}
}
