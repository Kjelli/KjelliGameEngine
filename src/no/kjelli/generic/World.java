package no.kjelli.generic;

import java.util.ArrayList;

import no.kjelli.generic.gameobjects.GameObject;

public class World {

	private static int width;
	private static int height;

	private static ArrayList<GameObject> removeQueue = new ArrayList<>();
	private static ArrayList<GameObject> addQueue = new ArrayList<>();
	private static ArrayList<GameObject> objects = new ArrayList<>();

	public static void init(int width, int height) {
		setWidth(width);
		setHeight(height);

	}

	public static ArrayList<GameObject> getObjects() {
		return objects;
	}

	public static void add(GameObject object) {
		addQueue.add(object);
	}

	public static void remove(GameObject object) {
		removeQueue.add(object);
	}

	public static void render() {
		if (width <= 0 || height <= 0) {
			System.err.println("World has an invalid dimension!");
			System.exit(0);
		}
		for (GameObject object : objects) {
			if (object.isVisible())
				object.draw();
		}

	}               

	public static void update() {

		for (GameObject oldObject : removeQueue) {
			objects.remove(oldObject);
		}
		for (GameObject newObject : addQueue) {
			objects.add(newObject);
		}

		addQueue.clear();
		removeQueue.clear();

		Physics.quadtree.clear();
		Physics.addObjects(getCollidables());
		for (GameObject gameObject : objects) {
			gameObject.update();
		}
	}

	private static ArrayList<Collidable> getCollidables() {
		ArrayList<Collidable> collidables = new ArrayList<>();
		for (GameObject e : objects) {
			if (e instanceof Collidable) {
				collidables.add((Collidable) e);
			}
		}
		return collidables;
	}

	public static void setWidth(int width) {
		if (width <= 0)
			throw new IllegalArgumentException("Width cannot be <= 0!");
		World.width = width;
	}

	public static void setHeight(int height) {
		if (height <= 0)
			throw new IllegalArgumentException("Height cannot be <= 0!");
		World.height = height;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

}
