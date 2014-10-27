package no.kjelli.partybombs;

import static org.lwjgl.input.Keyboard.KEY_Q;

import java.io.IOException;

import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gameobjects.Tagger;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.main.Launcher;
import no.kjelli.generic.sound.SoundPlayer;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
public class Partybombs implements Game {
	public static int tag_playfield = Tagger.uniqueTag();

	public static enum STATE {
		INTRO, MENU, LOADING, PLAYING
	}

	public static STATE state;
	public static long ticks = 0;

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
		Screen.zoom(4.0f);
		Screen.setBackgroundColor(new Color(0.2f,0.8f,0.2f));
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

	public static void main(String[] args) {
		new Launcher(new Partybombs(), "Partybombs", 640, 480, false);
	}
}
