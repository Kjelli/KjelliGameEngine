package pong;

import generic.Game;
import generic.Main;
import generic.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import pong.gameobjects.*;

public class Pong implements Game {

	public static World world;
	// public static Screen screen;

	public static PlayerBat player;
	public static EnemyBat enemy;
	private int cooldown = 0;

	@Override
	public void init() {
		world = new World();

		Ball ball = new Ball(Display.getWidth() / 2, Display.getHeight() / 2
				- Ball.SIZE / 2);
		ball.setVisible(true);

		player = new PlayerBat(Display.getWidth() / 8, Display.getHeight() / 2
				- Bat.HEIGHT / 2);
		player.setVisible(true);

		enemy = new EnemyBat((int) (Display.getWidth() * (1 - 1.0 / 8)),
				Display.getHeight() / 2 - Bat.HEIGHT / 2);
		enemy.setVisible(true);

		Wall lowerWall = new Wall(0, 0, Display.getWidth(), Wall.DEFAULT_SIZE,
				ball);
		lowerWall.setVisible(true);

		Wall upperWall = new Wall(0, Display.getHeight() - Wall.DEFAULT_SIZE,
				Display.getWidth(), Wall.DEFAULT_SIZE, ball);
		upperWall.setVisible(true);

		world.add(ball);
		world.add(player);
		world.add(enemy);
		world.add(upperWall);
		world.add(lowerWall);

	}

	@Override
	public void render() {
		world.render();
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
			cooldown = 100;
			Ball newBall = new Ball(Display.getWidth() / 2, Display.getHeight()
					/ 2 - Ball.SIZE / 2);
			newBall.setVisible(true);
			world.add(newBall);
		}
	}

	@Override
	public void update() {
		if (cooldown > 0)
			cooldown--;
		world.update();
	}

	public static void main(String[] args) {
		new Main(new Pong());
	}

}
