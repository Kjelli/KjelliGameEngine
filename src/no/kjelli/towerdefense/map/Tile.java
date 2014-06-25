package no.kjelli.towerdefense.map;

import no.kjelli.generic.gameobjects.AbstractGameObject;

public abstract class Tile extends AbstractGameObject {

	public static final int SIZE = 32;
	public static final int GRASS = 1;
	public static final int DIRT = 2;
	public final int type;
	protected final Map map;

	public Tile(Map map, int x, int y, int type) {
		super(x, y, SIZE, SIZE);
		this.map = map;
		this.type = type;
	}

}
