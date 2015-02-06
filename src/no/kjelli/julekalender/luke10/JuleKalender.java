package no.kjelli.julekalender.luke10;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gameobjects.Tagger;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.input.Input;
import no.kjelli.generic.input.InputListener;
import no.kjelli.generic.main.GameWrapper;
import no.kjelli.generic.sound.SoundPlayer;

public class JuleKalender implements Game {

	public static int tag_playfield = Tagger.uniqueTag();
	public static int block_size = 1;

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

	static boolean left, right, up, down;
	static int speed = 8;

	public static void initIntro() {
		state = STATE.INTRO;
		World.clear();
		Board.init();
		Screen.setX(-(float) getGameWidth() / 2);
		Screen.setY(-(float) getGameHeight() / 2);

		Input.register(new InputListener() {

			@Override
			public void keyUp(int eventKey) {
				if (eventKey == Keyboard.KEY_DOWN)
					down = false;
				if (eventKey == Keyboard.KEY_UP)
					up = false;
				if (eventKey == Keyboard.KEY_LEFT)
					left = false;
				if (eventKey == Keyboard.KEY_RIGHT)
					right = false;
			}

			@Override
			public void keyDown(int eventKey) {
				if (eventKey == Keyboard.KEY_DOWN)
					down = true;
				if (eventKey == Keyboard.KEY_UP)
					up = true;
				if (eventKey == Keyboard.KEY_LEFT)
					left = true;
				if (eventKey == Keyboard.KEY_RIGHT)
					right = true;
			}
		});
	}

	public static void initGame() {
		World.clear();
		state = STATE.PLAYING;
	}

	@Override
	public void render() {
		Screen.render();
		Board.render();
	}

	@Override
	public void update() {
		ticks++;
		switch (state) {
		case INTRO:
			if (left)
				Screen.incrementX(-speed);
			if (right)
				Screen.incrementX(speed);
			if (down)
				Screen.incrementY(-speed);
			if (up)
				Screen.incrementY(speed);
			boolean click = false;
			Mouse.poll();
			while (Mouse.next()) {
				if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
					int x = (int) (Mouse.getX()-getGameWidth()/2);
					int y = (int) (Mouse.getY()-getGameHeight()/2);
					System.out.println(x);
					Board.centerOn(x, y);
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
		Board.dispose();

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
