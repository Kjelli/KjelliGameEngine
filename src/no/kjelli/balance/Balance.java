package no.kjelli.balance;

import java.io.IOException;

import no.kjelli.balance.gameobjects.*;
import no.kjelli.balance.gui.StartButton;
import no.kjelli.balance.level.Level;
import no.kjelli.balance.level.LevelEnum;
import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.main.Main;
import no.kjelli.generic.sound.SoundPlayer;

import org.lwjgl.input.Keyboard;

public class Balance implements Game {

	public static enum STATE {
		INTRO, MENU, LOADING, PLAYING;
	}

	public static long ticks = 0;
	public static STATE state;

	public static final float GRAVITY = 0.048f;

	public static Level level;
	public static int sublevel_index;

	public static Paddle player_two;
	public static Paddle player_one;

	public static int hoopsLeft;

	@Override
	public void init() {
		loadSounds();
		initIntro();
	}

	public static void initIntro() {
		Screen.clearGUI();
		World.clear();
		state = STATE.INTRO;
		StartButton startButton = new StartButton(Screen.getWidth() / 2
				- StartButton.WIDTH / 2, Screen.getHeight() / 2
				- StartButton.HEIGHT / 2);
		startButton.setVisible(true);
		Screen.add(startButton);
	}

	public static void initGame() {
		Screen.clearGUI();
		World.clear();
		initBackground();
		state = STATE.PLAYING;

		Ball ball = new Ball(Screen.getWidth() / 2 - Ball.SIZE / 2, 5
				* Screen.getHeight() / 6 - Ball.SIZE / 2);
		ball.setVisible(true);

		player_two = new Paddle(Screen.getWidth() / 3 - Paddle.WIDTH / 3,
				Screen.getHeight() / 6, 1);
		player_two.setVisible(true);

		player_one = new Paddle(Screen.getWidth() / 2 - Paddle.WIDTH / 2,
				Screen.getHeight() / 6, Paddle.SINGLE_PLAYER);
		player_one.setVisible(true);

		Wall leftWall = new Wall(-Wall.SIZE, 0, Wall.SIZE, Screen.getHeight());
		leftWall.setVisible(true);

		Wall rightWall = new Wall(Screen.getWidth(), 0, Wall.SIZE,
				Screen.getHeight());
		rightWall.setVisible(true);

		World.add(player_one, World.FOREGROUND);
		// World.add(player_two, World.FOREGROUND);
		World.add(ball, World.FOREGROUND);
		World.add(leftWall, World.FOREGROUND);
		World.add(rightWall, World.FOREGROUND);

		loadLevel(LevelEnum.easy);
		initSubLevel(sublevel_index);
	}

	private static void initSubLevel(int sublevel_index) {
		hoopsLeft = 0;
		Hoop[] hoops = level.getSublevel(sublevel_index).getHoops();
		for (Hoop hoop : hoops) {
			hoop.setVisible(true);
			World.add(hoop);
			hoopsLeft++;
		}
	}

	private static void loadLevel(LevelEnum level) {
		Balance.level = new Level(level);
	}

	private static void initBackground() {
		for (int i = 0; i < 500; i++) {
			float newX = (float) (Math.random() * Screen.getWidth());
			float newY = (float) (Math.random() * Screen.getHeight());
			BGStar s = new BGStar(newX, newY);
			s.setVisible(true);
			World.add(s, World.BACKGROUND);
		}
	}

	@Override
	public void loadSounds() {
		try {
			SoundPlayer.load("res\\bounce.wav");
			SoundPlayer.load("res\\win.wav");
			SoundPlayer.load("res\\lose.wav");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render() {
		Screen.render();
	}

	@Override
	public void getInput() {
		switch (state) {
		case INTRO:
			break;
		case LOADING:
			break;
		case MENU:
			break;
		case PLAYING:
			if (Keyboard.isKeyDown(Keyboard.KEY_A))
				player_two.accelerate(-1.6f);
			if (Keyboard.isKeyDown(Keyboard.KEY_D))
				player_two.accelerate(1.6f);
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
				player_one.accelerate(-1.6f);
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				player_one.accelerate(1.6f);
			if (Keyboard.isKeyDown(Keyboard.KEY_Q))
				Screen.toggleDebugDraw();
			break;
		default:
			break;

		}
	}

	@Override
	public void update() {
		ticks++;

		switch (state) {
		case PLAYING:

			if (hoopsLeft <= 0) {
				sublevel_index++;
				if (sublevel_index < level.getSublevels()) {
					initSubLevel(sublevel_index);
				} else {
					// TODO: Level Complete
					sublevel_index = 0;
					initIntro();
				}
			}

			if (World.size() < 500) {
				BGStar s = new BGStar(
						Wall.SIZE
								+ (float) (Math.random() * Screen.getWidth() - 2 * Wall.SIZE),
						Screen.getHeight() + 5);
				s.setVisible(true);
				World.add(s, World.BACKGROUND);
			}
			World.update();
			break;
		default:
			break;
		}
		Screen.update();
	}

	public static void lose() {
		ticks = 0;
		loseLife();
		initGame();
	}

	private static void loseLife() {
		// TODO
	}

	public static void main(String[] args) {
		// System.setProperty("org.lwjgl.librarypath",
		// new File("natives").getAbsolutePath());
		new Main(new Balance(), "Balance - by Kjelli", 640, 480, false);
	}

}
