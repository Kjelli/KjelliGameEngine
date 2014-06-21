package no.kjelli.towerdefense.map;

import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;

public class Map extends AbstractGameObject {

	int tiles_width, tiles_height;
	Tile[][] tiles;

	public Map(int tiles_width, int tiles_height) {
		this(0, 0, tiles_width, tiles_height);
	}

	public Map(int x, int y, int tiles_width, int tiles_height) {
		this.x = x;
		this.y = y;
		this.tiles_width = tiles_width;
		this.tiles_height = tiles_height;
		this.width = tiles_width * Tile.SIZE;
		this.height = tiles_height * Tile.SIZE;

		tiles = new Tile[tiles_width][tiles_height];
	}

	@Override
	public void draw() {
		if (tiles[0][0] == null) {
			Draw.rect(this);
		} else {
			for (int x = 0; x < tiles_width; x++) {
				for (int y = 0; y < tiles_height; y++) {
					tiles[x][y].draw();
				}
			}
		}
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x > tiles_width)
			throw new IllegalArgumentException("Out of bounds (" + x + ")");
		else if (y < 0 || x > tiles_height)
			throw new IllegalArgumentException("Out of bounds (" + y + ")");
		else
			return tiles[x][y];
	}

	@Override
	public void update() {
	}

	public void setTile(int x, int y, Tile tile) {
		tiles[x][y] = tile;
	}
}
