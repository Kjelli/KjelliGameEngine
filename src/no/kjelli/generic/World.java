package no.kjelli.generic;

import java.util.ArrayList;

import no.kjelli.generic.gameobjects.GameObject;

public class World {

	public static int width;
	public static int height;

	private static ArrayList<GameObject> removeQueue = new ArrayList<>();
	private static ArrayList<GameObject> addQueue = new ArrayList<>();
	private static ArrayList<GameObject> objects = new ArrayList<>();

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
		// Physics.quadtree.render();

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
		World.width = width;
	}

	public static void setHeight(int height) {
		World.height = height;
	}
}
