package no.kjelli.towerdefense.map;

import no.kjelli.generic.gameobjects.AbstractGameObject;

public abstract class Tile extends AbstractGameObject {

	public static final int SIZE = 40;
	public static final int GRASS = 1;
	public final int type;
	protected final Map map;

	public Tile(Map map, int x, int y, int type) {
		this.map = map;
		this.type = type;

		this.x = x;
		this.y = y;
		width = SIZE;
		height = SIZE;
	}

}
