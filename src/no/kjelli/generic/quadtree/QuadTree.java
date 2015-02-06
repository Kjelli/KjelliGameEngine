package no.kjelli.generic.quadtree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import no.kjelli.generic.gameobjects.Collidable;
import no.kjelli.generic.gameobjects.GameObject;
import no.kjelli.generic.gfx.Draw;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;

public class QuadTree<E extends Collidable> {
	public static final int MAX_CAPACITY = 10;
	public static final int MAX_DEPTH = 1;

	public static final int Q1 = 1;
	public static final int Q2 = 2;
	public static final int Q3 = 4;
	public static final int Q4 = 8;

	int level;
	int borderTop;
	int borderBottom;
	int borderLeft;
	int borderRight;

	int width;
	int height;

	QuadTree<E>[] nodes;
	ArrayList<E> objects;

	@SuppressWarnings("unchecked")
	public QuadTree(int level, int borderLeft, int borderRight,
			int borderBottom, int borderTop) {
		this.level = level;
		objects = new ArrayList<>();
		nodes = new QuadTree[4];

		this.borderLeft = borderLeft;
		this.borderRight = borderRight;
		this.borderTop = borderTop;
		this.borderBottom = borderBottom;

		width = borderRight - borderLeft;
		height = borderTop - borderBottom;
	}

	public void insert(E e) {
		if (!(e instanceof Collidable))
			return;
		if (nodes[0] != null) {
			int index = pickIndex(e);

			if (index != 0) {
				if ((index & Q1) > 0)
					nodes[0].insert(e);
				if ((index & Q2) > 0)
					nodes[1].insert(e);
				if ((index & Q3) > 0)
					nodes[2].insert(e);
				if ((index & Q4) > 0)
					nodes[3].insert(e);
				return;
			}
		}

		objects.add(e);

		if (objects.size() > MAX_CAPACITY && level < MAX_DEPTH) {
			if (nodes[0] == null)
				divide();

			int i = 0;
			while (i < objects.size()) {
				E ee = objects.get(i);
				int index = pickIndex(ee);
				if (index != 0) {
					objects.remove(ee);
					if ((index & Q1) > 0)
						nodes[0].insert(ee);
					if ((index & Q2) > 0)
						nodes[1].insert(ee);
					if ((index & Q3) > 0)
						nodes[2].insert(ee);
					if ((index & Q4) > 0)
						nodes[3].insert(ee);
				} else {
					i++;
				}
			}

		}
	}

	public void clear() {
		objects.clear();

		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}

	public void divide() {

		nodes[0] = new QuadTree<E>(level + 1, borderLeft, borderLeft + width
				/ 2, borderBottom + height / 2, borderBottom + height);
		nodes[1] = new QuadTree<E>(level + 1, borderLeft + width / 2,
				borderLeft + width, borderBottom + height / 2, borderBottom
						+ height);
		nodes[2] = new QuadTree<E>(level + 1, borderLeft, borderLeft + width
				/ 2, borderBottom, borderBottom + height / 2);
		nodes[3] = new QuadTree<E>(level + 1, borderLeft + width / 2,
				borderLeft + width, borderBottom, borderBottom + height / 2);
	}

	private int pickIndex(E e) {
		int index = 0;

		int yMid = borderBottom + height / 2;
		int xMid = borderLeft + width / 2;

		boolean topQuadrant = (e.getY() + e.getVelocityY() + e.getHeight() >= yMid);
		boolean bottomQuadrant = (e.getY() + e.getVelocityY() <= yMid);

		boolean leftQuadrant = (e.getX() + e.getVelocityX() <= xMid);
		boolean rightQuadrant = (e.getX() + e.getVelocityX() + e.getWidth() >= xMid);

		if (topQuadrant) {
			if (leftQuadrant)
				index += 1;
			if (rightQuadrant)
				index += 2;
		}
		if (bottomQuadrant) {
			if (leftQuadrant)
				index += 4;
			if (rightQuadrant)
				index += 8;
		}

		return index;
	}

	public boolean intersects(Rectangle bounds) {
		return (new Rectangle(borderLeft, borderBottom, borderRight
				- borderLeft, borderTop - borderBottom).intersects(bounds));
	}

	public HashSet<E> retrieve(HashSet<E> returnObjects, Rectangle bounds) {
		if (nodes[0] == null) {
			for (E e : objects)
				if (bounds.intersects(e.getBounds()))
					returnObjects.add(e);
		}
		if (nodes[0] != null) {
			for (int i = 0; i < 4; i++) {
				if (nodes[i].intersects(bounds))
					nodes[i].retrieve(returnObjects, bounds);
			}
		}
		return returnObjects;
	}

	public HashSet<E> retrieve(HashSet<E> returnObjects, E e) {
		int index = pickIndex(e);
		if (nodes[0] != null) {
			if ((index & Q1) > 0)
				nodes[0].retrieve(returnObjects, e);
			if ((index & Q2) > 0)
				nodes[1].retrieve(returnObjects, e);
			if ((index & Q3) > 0)
				nodes[2].retrieve(returnObjects, e);
			if ((index & Q4) > 0)
				nodes[3].retrieve(returnObjects, e);
		}
		returnObjects.addAll(objects);

		return returnObjects;
	}

	/*
	 * Not neccessary but used for testing
	 */

	public void render() {
		Draw.rect(borderLeft, borderBottom, 5f, width, height);
		for (E object : objects) {
			Draw.line(borderLeft + width / 2, borderBottom + height / 2, 5f,
					object.getX() + object.getWidth() / 2, object.getY()
							+ object.getHeight() / 2, Color.red, false);
			Draw.rect(object, 5f, Color.cyan);
		}
		if (nodes[0] != null) {
			for (int i = 0; i < nodes.length; i++) {
				nodes[i].render();
			}
		}
	}

	public HashSet<E> getObjects(HashSet<E> returnObjects) {
		if (nodes[0] != null)
			for (int i = 0; i < 4; i++)
				nodes[i].getObjects(returnObjects);

		returnObjects.addAll(objects);

		return returnObjects;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("");
		HashSet<E> uniques = new HashSet<>();
		getObjects(uniques);

		Iterator<E> it = uniques.iterator();

		while (it.hasNext()) {
			E e = it.next();
			if (it.hasNext())
				sb.append(e + ", ");
			else
				sb.append(e);
		}

		return sb.toString();
	}

	public void insert(ArrayList<E> others) {
		for (E e : others) {
			insert(e);
		}
	}
}
