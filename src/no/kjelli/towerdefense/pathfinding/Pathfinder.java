package no.kjelli.towerdefense.pathfinding;

import java.util.ArrayList;

import no.kjelli.towerdefense.map.Map;
import no.kjelli.towerdefense.map.Tile;
import ai.pathfinder.Node;
import ai.pathfinder.Pathfinder;

public class PathFinder {

	private static final ArrayList<Node> nodes = new ArrayList<Node>();
	private static Node s, t;

	public static ArrayList<Node> findPath(Map map, Tile start, Tile goal) {
		if (start == null || goal == null) {
			System.err.println("Source or target was null");
			return null;
		}
		if (!start.isTraversable() || !goal.isTraversable()) {
			System.err.println("Source or target was not traversable");
			return null;
		}

		nodes.clear();

		s = new Node(start.x_index, start.y_index);
		s.walkable = start.isTraversable();
		t = new Node(goal.x_index, goal.y_index);
		t.walkable = goal.isTraversable();

		insertNodes(map);
		connect(map);

		Pathfinder pf = new Pathfinder(nodes);
		ArrayList<Node> result = pf.aStar(s, t);
		return result;

	}

	private static void insertNodes(Map map) {
		boolean walkable = false;
		for (int x = 0; x < map.getTilesWidth(); x++) {
			for (int y = 0; y < map.getTilesHeight(); y++) {
				if ((x == s.x && y == s.y)) {
					nodes.add(s);
					continue;
				}

				if ((x == t.x && y == t.y)) {
					nodes.add(t);
					continue;
				}
				walkable = map.getTile(x, y).isTraversable();
				Node n = new Node(x, y);
				n.walkable = walkable;
				nodes.add(n);

			}
		}
	}

	private static void connect(Map map) {
		for (int x = 0; x < map.getTilesWidth(); x++) {
			for (int y = 0; y < map.getTilesHeight(); y++) {
				int index = x + y * map.getTilesWidth();
				Node current = retrieveNode(map, x, y);
				Node next;
				if ((next = retrieveNode(map, x - 1, y)) != null) {
					if (next.walkable)
						current.connect(next);
				}
				if ((next = retrieveNode(map, x + 1, y)) != null) {
					if (next.walkable)
						current.connect(next);
				}
				if ((next = retrieveNode(map, x, y + 1)) != null) {
					if (next.walkable)
						current.connect(next);
				}
				if ((next = retrieveNode(map, x, y - 1)) != null) {
					if (next.walkable)
						current.connect(next);
				}
			}
		}
	}

	private static Node retrieveNode(Map map, int x, int y) {
		if (x < 0 || x >= map.getTilesWidth() || y < 0
				|| y >= map.getTilesHeight())
			return null;
		else
			return nodes.get(x + y * map.getTilesWidth());
	}
}
