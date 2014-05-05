package no.kjelli.generic;

import java.util.ArrayList;
import java.util.HashSet;

import no.kjelli.generic.gameobjects.AbstractObject;
import no.kjelli.quadtree.QuadTree;

public class Physics {

	public static QuadTree<Collidable> quadtree;

	public static void init() {
		quadtree = new QuadTree<Collidable>(0, 0, World.getWidth(), 0,
				World.getHeight());
	}

	public static void addObjects(ArrayList<Collidable> others) {
		quadtree.insert(others);
	}

	public static Collision checkCollision(Collidable object) {
		HashSet<Collidable> others = new HashSet<>();
		quadtree.retrieve(others, object);
		for (Collidable other : others) {
			if (other.equals(object))
				continue;

			if (object.intersects(other)) {
				Collidable col1 = (Collidable) object;
				Collidable col2 = (Collidable) other;

				Collision collision = new Collision(col1, col2);
				return collision;
			}
		}
		return null;
	}

	public static void revalidate(AbstractObject gameObject) {
	}

}
