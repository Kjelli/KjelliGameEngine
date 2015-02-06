package no.kjelli.julekalender.luke5;

import java.io.IOException;

import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gameobjects.Tagger;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.main.Launcher;
import no.kjelli.generic.sound.SoundPlayer;

public class JuleKalender implements Game {

	public static int tag_playfield = Tagger.uniqueTag();
	public static int block_size = 32;

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
	}

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
			SoundPlayer.load("sound8.wav");
			SoundPlayer.load("sound9 lose.wav");
			SoundPlayer.load("sound10 powerup.wav");
			SoundPlayer.load("sound11 bomb.wav");
			SoundPlayer.load("sound11 bomb long.wav");
			SoundPlayer.load("sound12.wav");
			SoundPlayer.load("sound13.wav");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initIntro() {
		state = STATE.INTRO;
		World.clear();
	}

	public static void initGame() {
		World.clear();
		state = STATE.PLAYING;
	}

	@Override
	public void render() {
		Screen.render();
	}

	static final int traversalCount = 200;
	static int traversed = 0;

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

	public void destroy() {
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
		return "Julekalender";
	}

	public static void main(String[] args) {
		Game game = new JuleKalender();
		new Launcher(game, false);
	}
}