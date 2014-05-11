package no.kjelli.balance;

import java.io.IOException;

import no.kjelli.balance.gameobjects.*;
import no.kjelli.balance.gui.StartButton;
import no.kjelli.generic.Game;
import no.kjelli.generic.Screen;
import no.kjelli.generic.World;
import no.kjelli.generic.main.Main;
import no.kjelli.generic.sound.SoundPlayer;

import org.lwjgl.input.Keyboard;

public class Balance implements Game {

	public static enum STATE {
		INTRO, MENU, LOADING, PLAYING;
	}

	public static long ticks = 0;
	public static STATE state;

	public static double gravity = 0.048;

	public static Paddle player_one;
	public static Paddle player_two;

	@Override
	public void init() {
		loadSounds();
		initIntro();
	}

	public static void initIntro() {
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

		Ball ball = new Ball(Screen.getWidth() / 2 - Ball.SIZE / 2, 4
				* Screen.getHeight() / 5 - Ball.SIZE / 2);
		ball.setVisible(true);

		player_one = new Paddle(Screen.getWidth() / 2 - Paddle.WIDTH / 3,
				Screen.getHeight() / 6, 1);
		player_one.setVisible(true);

		player_two = new Paddle(Screen.getWidth() / 2 - Paddle.WIDTH / 3,
				Screen.getHeight() / 6 - Paddle.HEIGHT - 1, 2);
		player_two.setVisible(true);

		Wall leftWall = new Wall(-Wall.SIZE, 0, Wall.SIZE, Screen.getHeight());
		leftWall.setVisible(true);

		Wall rightWall = new Wall(Screen.getWidth(), 0, Wall.SIZE,
				Screen.getHeight());
		rightWall.setVisible(true);

		World.add(player_one, World.FOREGROUND);
		World.add(player_two, World.FOREGROUND);
		World.add(ball, World.FOREGROUND);
		World.add(leftWall, World.FOREGROUND);
		World.add(rightWall, World.FOREGROUND);
	}

	private static void initBackground() {
		for (int i = 0; i < 60; i++) {
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
				player_one.accelerate(-1);
			if (Keyboard.isKeyDown(Keyboard.KEY_D))
				player_one.accelerate(1);
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
				player_two.accelerate(-1);
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				player_two.accelerate(1);
			break;
		default:
			break;

		}
	}

	@Override
	public void update() {
		ticks++;

		if (state == STATE.PLAYING) {
			if (World.size() < 600) {
				BGStar s = new BGStar(
						Wall.SIZE
								+ (float) (Math.random() * Screen.getWidth() - Wall.SIZE),
						Screen.getHeight() + 5);
				s.setVisible(true);
				World.add(s, World.BACKGROUND);
			}
		}

		gravity += 0.00002f;

		World.update();
		Screen.update();

	}

	public static void main(String[] args) {
		new Main(new Balance(), "Balance - by Kjelli", 640, 480);
	}

}
