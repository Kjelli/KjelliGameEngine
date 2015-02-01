package no.kjelli.julekalender.luke20;

import java.io.IOException;

import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gameobjects.Tagger;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.main.GameWrapper;
import no.kjelli.generic.sound.SoundPlayer;

public class JuleKalender implements Game {

	public static int tag_playfield = Tagger.uniqueTag();
	public static int block_size = 32;

	public static ChristmasDinner cd;

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

		cd = new ChristmasDinner(1500);
	}

	public static void initGame() {
		World.clear();
		state = STATE.PLAYING;
	}

	@Override
	public void render() {
		Screen.render();
		cd.draw();
	}

	@Override
	public void update() {
		ticks++;
		switch (state) {
		case INTRO:
			if (!cd.allDrunk) {
				if (ticks % 1 == 0) {
					if (ticks % 2 == 0) {
						cd.bottleHolder.drink();
					} else {
						cd.bottleHolder.pass();
					}
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
		return 1024;
	}

	public static double getGameHeight() {
		return 768;
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
		new GameWrapper(game, false);
	}
}
