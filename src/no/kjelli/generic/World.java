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
	private static boolean newItems = false;

	public static void init(int width, int height) {
		setWidth(width);
		setHeight(height);

	}

	public static ArrayList<GameObject> getObjects() {
		return objects;
	}

	public static void add(GameObject object) {
		add(object, FOREGROUND);
	}

	public static void add(GameObject object, int layer) {
		if (object instanceof Drawable)
			object.setLayer(layer);
		addQueue.add(object);
		newItems = true;
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
		for (GameObject object : objects) {
			if (newItems) {
				Collections.sort(objects, new Comparator<GameObject>() {
					@Override
					public int compare(GameObject arg0, GameObject arg1) {
						return arg0.compareTo(arg1);
					}
				});
				newItems = false;
			}
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
		}

		addQueue.clear();
		removeQueue.clear();

		Physics.quadtree.clear();
		Physics.addObjects(objects);
		for (GameObject gameObject : objects) {
			gameObject.update();
		}
	}

	public static HashSet<GameObject> retrieve(
			HashSet<GameObject> returnObjects, Rectangle bounds) {
		return Physics.quadtree.retrieve(returnObjects, bounds);
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
