package no.kjelli.pong;

import java.io.IOException;

import no.kjelli.generic.*;
import no.kjelli.generic.main.Main;
import no.kjelli.generic.sound.SoundPlayer;
import no.kjelli.pong.gameobjects.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Pong implements Game {

	public static Screen screen;

	public static PlayerBat player;
	public static EnemyBat enemy;

	public static long ticks = 0;

	private int cooldown = 0;

	@Override
	public void init() {
		loadSounds();
		Screen.init(0, 0, Display.getWidth(), Display.getHeight());
		World.init(Display.getWidth(), Display.getHeight());
		Physics.init();
		initGameObjects();
	}

	private void initGameObjects() {
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

		World.add(ball);
		World.add(player);
		World.add(enemy);
		World.add(upperWall);
		// World.add(midWall);
		// World.add(midWall2);
		World.add(lowerWall);
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
		if (Keyboard.isKeyDown(Keyboard.KEY_W)
				|| Keyboard.isKeyDown(Keyboard.KEY_UP))
			player.move(1);

		if (Keyboard.isKeyDown(Keyboard.KEY_S)
				|| Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			player.move(-1);

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && cooldown == 0) {
			cooldown = 10;
			Ball newBall = new Ball(Display.getWidth() / 2, Display.getHeight()
					/ 2 - Ball.SIZE / 2);
			newBall.setVisible(true);
			World.add(newBall);
		}
	}

	@Override
	public void update() {
		ticks++;
		if (cooldown > 0)
			cooldown--;
		World.update();
		Screen.update();
	}

	public static void main(String[] args) {
		new Main(new Pong());
	}

}
