package quadtrees;

import generic.*;
import generic.gameobjects.GameObject;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import quadtrees.gameobjects.Ball;

public class Quadtrees implements Game {

	static final boolean DEBUG_RENDER_QUADTREE = true;
	static long ticks = 0;

	private ArrayList<GameObject> objects;

	@Override
	public void init() {
		objects = new ArrayList<GameObject>();

		Ball ball = new Ball(Display.getWidth() / 2, Display.getHeight() / 2
				- Ball.SIZE / 2);
		ball.setVisible(true);

		objects.add(ball);
	}

	@Override
	public void render() {
		for (GameObject gameObject : objects)
			if (gameObject.isVisible())
				gameObject.draw();

	}

	@Override
	public void getInput() {
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && ticks % 1 == 0) {
			Ball tempBall = new Ball(
					(int) (Math.random() * (Display.getWidth() - Ball.SIZE)),
					(int) (Math.random() * (Display.getHeight() - Ball.SIZE)));
			tempBall.setVisible(true);
			objects.add(tempBall);
		}
	}

	@Override
	public void update() {
		ticks++;
		Physics.clear();
		Physics.addObjects(objects);
		for (GameObject gameObject : objects) {
			Physics.checkCollisions(gameObject);
			gameObject.update();
		}
	}

	public static void main(String[] args) {
		new Main(new Quadtrees());
	}

}
