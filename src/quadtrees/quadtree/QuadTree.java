package quadtrees.quadtree;

import generic.Collidable;
import generic.Draw;

import java.util.ArrayList;

public class QuadTree<E extends Collidable> {
	public static final int MAX_CAPACITY = 5;
	public static final int MAX_DEPTH = 5;

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
		if (nodes[0] != null) {
			int index = pickIndex(e);

			if (index != -1) {
				nodes[index].insert(e);
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
				if (index != -1) {
					objects.remove(ee);
					nodes[index].insert(ee);
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

	@SuppressWarnings("unchecked")
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

	/*
	 * (0,0) is upper left corner using awt.Rectangle, lower left using OpenGL
	 * fucking confusing
	 */

	private int pickIndex(E e) {
		int index = -1;

		int yMid = borderBottom + height / 2;
		int xMid = borderLeft + width / 2;

		boolean topQuadrant = (e.getY() > yMid);
		boolean bottomQuadrant = (e.getY() + e.getHeight() < yMid);

		boolean leftQuadrant = (e.getX() + e.getWidth() < xMid);
		boolean rightQuadrant = (e.getX() > xMid);

		if (topQuadrant) {
			if (leftQuadrant)
				index = 0;
			else if (rightQuadrant)
				index = 1;
		}
		if (bottomQuadrant) {
			if (leftQuadrant)
				index = 2;
			else if (rightQuadrant)
				index = 3;
		}

		return index;
	}

	public ArrayList<E> retrieve(ArrayList<E> returnObjects, E e) {
		int index = pickIndex(e);
		if (index != -1 && nodes[0] != null)
			nodes[index].retrieve(returnObjects, e);

		returnObjects.addAll(objects);

		return returnObjects;
	}

	/*
	 * Not neccessary but used for testing
	 */

	public void render() {
		Draw.rect(borderLeft, borderBottom, width, height);
		for (E object : objects)
			Draw.line(borderLeft + width / 2, borderBottom + height / 2,
					object.getX() + object.getWidth() / 2, object.getY()
							+ object.getHeight() / 2);
		if (nodes[0] != null) {
			for (int i = 0; i < nodes.length; i++) {
				nodes[i].render();
			}
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("");
		if (!objects.isEmpty()) {
			for (int i = 0; i < objects.size() - 1; i++) {
				sb.append(objects.get(i).toString() + ", ");
			}
			sb.append(objects.get(objects.size() - 1).toString());
		}
		return sb.toString();
	}

	public void insert(ArrayList<E> others) {
		for (E e : others) {
			insert(e);
		}
	}
}
