package no.kjelli.generic;

import java.util.ArrayList;
import java.util.HashSet;

import no.kjelli.generic.gameobjects.Collidable;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.quadtree.QuadTree;

public class Physics {

	public static QuadTree<Collidable> quadtree;

	public static void init() {
		quadtree = new QuadTree<Collidable>(0, 0, World.getWidth(), 0,
				World.getHeight());
	}

	public static void addObjects(ArrayList<Collidable> others) {
		quadtree.insert(others);
	}

	public static void getCollisions(Collidable object) {
		HashSet<Collidable> others = new HashSet<>();
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
	}
}
