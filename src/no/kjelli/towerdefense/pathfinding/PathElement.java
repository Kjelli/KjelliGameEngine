package no.kjelli.towerdefense.pathfinding;

import no.kjelli.towerdefense.map.Tile;

public class PathElement {
	Tile tile;
	PathElement prev;
	PathElement next;
	
	int distanceFrom = 0;
	int distanceTo;
	
	public PathElement(Tile tile){
		this.tile = tile;
	}
	
	public PathElement(Tile tile, PathElement p){
		this.tile = tile;
		distanceFrom = p.distanceFrom + 1;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if(!(arg0 instanceof PathElement))
			return false;
		if(arg0 == this)
			return true;
		if(tile.equals(((PathElement)arg0).tile))
			return true;
		return false;
	}
}
