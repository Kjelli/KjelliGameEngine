package generic;

import generic.gameobjects.AbstractObject;
import generic.gameobjects.GameObject;

import java.util.ArrayList;
import java.util.HashSet;

import org.lwjgl.opengl.Display;

import quadtree.QuadTree;

public class Physics {

	public static QuadTree<Collidable> quadtree = new QuadTree<Collidable>(0,
			0, Display.getWidth(), 0, Display.getHeight());;

	public static void addObjects(ArrayList<Collidable> others) {
		quadtree.insert(others);
	}

	public static void checkCollisions(ArrayList<Collidable> objects) {
		HashSet<Collidable> others = new HashSet<>();
		for (Collidable object : objects) {
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
					col2.onCollide(col1);
				}
			}
		}
	}

	public static void checkCollision(Collidable object) {
		HashSet<Collidable> others = new HashSet<>();
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
