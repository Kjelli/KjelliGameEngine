package no.kjelli.pong;

import java.io.IOException;

import no.kjelli.generic.Game;
import no.kjelli.generic.World;
import no.kjelli.generic.gfx.Screen;
import no.kjelli.generic.main.Main;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.pong.gameobjects.*;
import no.kjelli.pong.gui.StartButton;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Pong implements Game {

	public static enum STATE {
		INTRO, MENU, LOADING, PLAYING;
	}

	public static STATE state;

	public static PlayerBat player;
	public static EnemyBat enemy;

	public static long ticks = 0;

	private int cooldown = 0;

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
		World.clear();
		state = STATE.PLAYING;
		Ball ball = new Ball(Display.getWidth() / 2, Display.getHeight() / 2
				- Ball.SIZE / 2);
		ball.setVisible(true);

		player = new PlayerBat(Display.getWidth() / 8, Display.getHeight() / 2
				- Bat.HEIGHT / 2);
		player.setVisible(true);

		enemy = new EnemyBat((int) (Display.getWidth() * (1 - 1.0 / 8)),
				Display.getHeight() / 2 - Bat.HEIGHT / 2);
		enemy.setVisible(true);

		Wall lowerWall = new Wall(0, 0, Display.getWidth(), Wall.DEFAULT_SIZE);
		lowerWall.setVisible(true);

		Wall midWall = new Wall(0, 0, Wall.DEFAULT_SIZE, Display.getHeight());
		midWall.setVisible(true);

		Wall midWall2 = new Wall(Display.getWidth() - Wall.DEFAULT_SIZE, 0,
				Wall.DEFAULT_SIZE, Display.getHeight());
		midWall2.setVisible(true);

		Wall upperWall = new Wall(0, Display.getHeight() - Wall.DEFAULT_SIZE,
				Display.getWidth(), Wall.DEFAULT_SIZE);
		upperWall.setVisible(true);

		World.add(ball, World.FOREGROUND);
		World.add(player, World.FOREGROUND);
		World.add(enemy, World.FOREGROUND);
		World.add(upperWall, World.FOREGROUND);
		// World.add(midWall,World.FOREGROUND);
		// World.add(midWall2,World.FOREGROUND);
		World.add(lowerWall, World.FOREGROUND);
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
			if (Keyboard.isKeyDown(Keyboard.KEY_W)
					|| Keyboard.isKeyDown(Keyboard.KEY_UP))
				player.move(1);

			if (Keyboard.isKeyDown(Keyboard.KEY_S)
					|| Keyboard.isKeyDown(Keyboard.KEY_DOWN))
				player.move(-1);

			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && cooldown == 0) {
				cooldown = 10;
				Ball newBall = new Ball(Display.getWidth() / 2,
						Display.getHeight() / 2 - Ball.SIZE / 2);
				newBall.setVisible(true);
				World.add(newBall, World.FOREGROUND);
				break;
			}
		default:
			break;

		}
	}

	@Override
	public void update() {
		ticks++;

		World.update();
		Screen.update();

		if (cooldown > 0)
			cooldown--;

	}

	public static void main(String[] args) {
		new Main(new Pong(), "Pong - by Kjelli", 640, 480, false);
	}

}
