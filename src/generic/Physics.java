package generic;

import generic.gameobjects.GameObject;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import quadtrees.quadtree.QuadTree;

public class Physics {

	private static QuadTree<GameObject> quadtree;

	public static void addObjects(ArrayList<GameObject> others) {
		quadtree = new QuadTree(0,0,Display.getWidth(),0,Display.getHeight());
		quadtree.insert(others);
	}

	public static void clear() {
		if (quadtree != null)
			quadtree.clear();
	}

	public static void checkCollisions(GameObject object) {
		ArrayList<GameObject> others = new ArrayList<>();
		quadtree.retrieve(others, object);
		for (GameObject other : others) {
			if (other.equals(object))
				continue;
			if (object.intersects(other)) {
				object.onCollision(other);
				other.onCollision(object);
			}
		}
	}

	public static void revalidate(GameObject gameObject) {
	}

}
