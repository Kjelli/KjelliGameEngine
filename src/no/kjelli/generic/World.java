package no.kjelli.generic;

import java.util.*;

import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gfx.Drawable;

import org.lwjgl.util.Rectangle;

public class World {
	public static final int BACKGROUND = 0, FOREGROUND = 1;

	private static int width;
	private static int height;

	private static ArrayList<GameObject> removeQueue = new ArrayList<>();
	private static ArrayList<GameObject> addQueue = new ArrayList<>();
	private static ArrayList<GameObject> objects = new ArrayList<>();

	public static void init(int width, int height) {
		setWidth(width);
		setHeight(height);
		Physics.init();
	}

	public static ArrayList<GameObject> getObjects() {
		return objects;
	}


	public static void add(GameObject object) {
		addQueue.add(object);
	}

	public static int size() {
		return objects.size();
	}

	public static void remove(GameObject object) {
		removeQueue.add(object);
	}

	public static void clear() {
		addQueue.clear();
		removeQueue.addAll(objects);
	}

	public static void render() {
		if (width <= 0 || height <= 0) {
			System.err.println("World has an invalid dimension!");
			System.exit(0);
		}
		Collections.sort(objects);
		for (GameObject object : objects) {
			if (object instanceof Drawable && object.isVisible())
				((Drawable) object).draw();
		}

	}

	public static void update() {

		for (GameObject oldObject : removeQueue) {
			objects.remove(oldObject);
		}
		for (GameObject newObject : addQueue) {
			objects.add(newObject);
			newObject.onCreate();
		}

		addQueue.clear();
		removeQueue.clear();

		Physics.quadtree.clear();
		Physics.addObjects(objects);
		for (GameObject gameObject : objects) {
			if (!gameObject.isPaused())
				gameObject.update();
		}
	}

	public static HashSet<GameObject> retrieveCollidables(
			HashSet<GameObject> returnObjects, Rectangle bounds) {
		return Physics.quadtree.retrieve(returnObjects, bounds);
	}

	// Bad fix for retrieving game objects from the rectangle
	public static void retrieveAll(HashSet<GameObject> returnObjects,
			Rectangle bounds) {
		for (GameObject obj : objects) {
			if (obj.intersects(bounds))
				returnObjects.add(obj);
		}
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

	public static void pause(int tag, boolean pause) {
		for (GameObject o : objects)
			if (o.hasTag(tag))
				o.pause(pause);
	}

	public static void hide(int tag, boolean visible) {
		for (GameObject o : objects)
			if (o.hasTag(tag))
				o.setVisible(!visible);
	}

}
