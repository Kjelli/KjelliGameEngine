package pong;

import generic.Game;
import generic.Main;
import generic.gameobjects.GameObject;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import pong.gameobjects.Ball;
import pong.gameobjects.Bat;
import pong.gameobjects.EnemyBat;
import pong.gameobjects.PlayerBat;
import pong.gameobjects.Wall;

public class Pong implements Game {

	private ArrayList<GameObject> objects;
	
	private PlayerBat player;
	private EnemyBat enemy;
	
	//TODO: QuadTree Collision-detection

	@Override
	public void init() {
		objects = new ArrayList<GameObject>();

		Ball ball = new Ball(Display.getWidth() / 2, Display.getHeight() / 2 - Ball.SIZE / 2);
		ball.setVisible(true);
		
		player = new PlayerBat(Display.getWidth() / 8, Display.getHeight() / 2 - Bat.HEIGHT / 2, ball);
		player.setVisible(true);
		
		enemy = new EnemyBat((int)(Display.getWidth()*(1 - 1.0 / 8)), Display.getHeight() / 2 - Bat.HEIGHT / 2, ball);
		enemy.setVisible(true);
		
		Wall lowerWall = new Wall(0,0,Display.getWidth(),Wall.DEFAULT_SIZE, ball);
		lowerWall.setVisible(true);
		
		Wall upperWall = new Wall(0,Display.getHeight() - Wall.DEFAULT_SIZE,Display.getWidth(),Wall.DEFAULT_SIZE, ball);
		upperWall.setVisible(true);
		
		objects.add(ball);
		objects.add(player);
		objects.add(enemy);
		objects.add(upperWall);
		objects.add(lowerWall);
		
	}

	@Override
	public void render() {
		for (GameObject gameObject : objects)
			if (gameObject.isVisible())
				gameObject.draw();
	}

	@Override
	public void getInput() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP))
			player.move(1);
		
		else if(Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			player.move(-1);

		else
			player.move(0);
	}

	@Override
	public void update() {
		for (GameObject gameObject : objects)
			gameObject.update();
	}

	public static void main(String[] args) {
		new Main(new Pong());
	}

}
