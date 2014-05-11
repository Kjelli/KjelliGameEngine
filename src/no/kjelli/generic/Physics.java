package no.kjelli.generic;

import java.util.ArrayList;
import java.util.HashSet;

import no.kjelli.generic.gameobjects.Collidable;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.quadtree.QuadTree;

public class Physics {

	public static QuadTree<GameObject> quadtree;

	public static void init() {
		quadtree = new QuadTree<GameObject>(0, 0, World.getWidth(), 0,
				World.getHeight());
	}

	public static void addObjects(ArrayList<GameObject> others) {
		quadtree.insert(others);
	}

	public static Collision getCollisions(Collidable object) {
		HashSet<GameObject> others = new HashSet<>();
		quadtree.retrieve(others, object);
		for (GameObject other : others) {
			if (!(other instanceof Collidable))
				continue;
			if (other.equals(object))
				continue;

			if (object.intersects(other)) {
				Collidable tgt = (Collidable) other;
				Collision collision1 = new Collision(object, tgt);
				Collision collision2 = new Collision(tgt, object);
				object.onCollision(collision1);
				tgt.onCollision(collision2);
			}
		}
		return null;
	}
}
