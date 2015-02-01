package no.kjelli.pong;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import no.kjelli.generic.Callback;
import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gameobjects.Tagger;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.main.GameWrapper;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.pong.config.PlayerConfig;
import no.kjelli.pong.gameobjects.Ball;
import no.kjelli.pong.gameobjects.Bat;
import no.kjelli.pong.menu.ControlInput;

public class Pong implements Game {

	public static int tag_playfield = Tagger.uniqueTag();
	public static int block_size = 32;
	public static long ticks = 0;
	PlayerConfig[] config = new PlayerConfig[2];

	public static Bat bat1, bat2;
	public static Ball ball;

	private static boolean isCloseRequested = false;
	public static STATE state;

	public static enum STATE {
		INTRO, MENU, LOADING, PLAYING
	}

	@Override
	public void init() {
		loadSounds();
		initIntro();
	}

	@Override
	public void loadSounds() {
		try {
			SoundPlayer.load("bounce.wav");
			SoundPlayer.load("laser1.wav");
			SoundPlayer.load("hit1.wav");
			SoundPlayer.load("speedup.wav");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initIntro() {
		World.clear();
		state = STATE.INTRO;

		config[0] = new PlayerConfig("devguy", Keyboard.KEY_W, Keyboard.KEY_S,
				Keyboard.KEY_SPACE, 0);
		config[1] = new PlayerConfig("Newguy", Keyboard.KEY_UP,
				Keyboard.KEY_DOWN, Keyboard.KEY_LEFT, 1);
		fetchControlInput(config[0], new Callback() {
			@Override
			public void run() {
				World.clear();
				fetchControlInput(config[1], new Callback() {
					@Override
					public void run() {
						initGame();
					}
				});
			}
		});
	}

	private void fetchControlInput(PlayerConfig pc, Callback callback) {
		new ControlInput(Screen.getCenterX(), Screen.getCenterY(), pc, callback);
	}

	public void initGame() {
		World.clear();
		state = STATE.PLAYING;

		bat1 = new Bat(100, Screen.getCenterY() - Bat.HEIGHT / 2, config[0]);
		World.add(bat1);

		bat2 = new Bat(Screen.getWidth() - 100, Screen.getCenterY()
				- Bat.HEIGHT / 2, config[1]);
		World.add(bat2);

		ball = new Ball(Screen.getCenterX() - Ball.WIDTH / 2,
				Screen.getCenterY() - Ball.HEIGHT / 2);

		World.add(ball);
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
	
	public static void reset() {
		bat1.reset();
		bat2.reset();
		ball.reset();
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
		return 800;
	}

	@Override
	public double getHeight() {
		return 600;
	}

	@Override
	public String getTitle() {
		return "Pong";
	}

	public static void main(String[] args) {
		new GameWrapper(new Pong(), true);
	}

	
}
