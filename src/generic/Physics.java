package generic;

import generic.gameobjects.AbstractObject;
import generic.gameobjects.GameObject;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import quadtrees.quadtree.QuadTree;

public class Physics {

	public static QuadTree<Collidable> quadtree;

	public static void addObjects(ArrayList<Collidable> others) {
		quadtree = new QuadTree<Collidable>(0, 0, Display.getWidth(), 0,
				Display.getHeight());
		quadtree.insert(others);
	}

	public static void clear() {
		if (quadtree != null)
			quadtree.clear();
	}

	public static void checkCollision(Collidable object) {
		ArrayList<Collidable> others = new ArrayList<>();
		quadtree.retrieve(others, object);
		for (GameObject other : others) {
			if (other.equals(object))
				continue;
			if (!(other instanceof Collidable)) {
				continue;
			}
			if (object.intersects(other)) {
				Collidable col1 = (Collidable) object;
				Collidable col2 = (Collidable) other;
				col1.onCollide(col2);
			}
		}
	}

	public static void revalidate(AbstractObject gameObject) {
	}

}
