package no.kjelli.generic;

import java.util.ArrayList;
import java.util.HashSet;

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

	public static Collision getCollisions(Collidable object) {
		HashSet<Collidable> others = new HashSet<>();
		quadtree.retrieve(others, object);
		for (Collidable other : others) {
			if (other.equals(object))
				continue;

			if (object.intersects(other)) {
				Collision collision1 = new Collision(object, other);
				Collision collision2 = new Collision(other, object);
				object.onCollision(collision1);
				other.onCollision(collision2);
			}
		}
		return null;
	}
}
