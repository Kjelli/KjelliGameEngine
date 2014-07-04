package no.kjelli.towerdefense.pathfinding;

import java.util.HashSet;

import no.kjelli.towerdefense.map.Map;
import no.kjelli.towerdefense.map.Tile;

public class Pathfinder {

	public static PathElement shortestPath(Map map, Tile start, Tile end) {
		HashSet<PathElement> closed = new HashSet<>();
		HashSet<PathElement> open = new HashSet<>();
		PathElement begin;

		begin = new PathElement(start);
		open.add(begin);
		
		for (PathElement p : open) {
			if(p.tile == end)
				return begin;
			int x = p.tile.x_index, y = p.tile.y_index;
			for (int xOff = -1; xOff <= 1; xOff++) {
				for (int yOff = -1; yOff <= 1; yOff++) {
					if (xOff == 0 && yOff == 0)
						continue;
					try {
						PathElement e = new PathElement(map.getTile(x + xOff, y
								+ yOff), p);
						if(!closed.contains(e))
							open.add(e);

					} catch (IllegalArgumentException e) {
						continue;
					}
				}
			}
			
			closed.add(p);
		}
		
		return begin;

	}
}
