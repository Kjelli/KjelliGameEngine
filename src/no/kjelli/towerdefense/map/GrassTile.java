package no.kjelli.towerdefense.map;

import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Textures;

public class GrassTile extends Tile {

	public GrassTile(Map map, int x, int y) {
		super(map, x, y, Tile.GRASS);
		int random = (int) (Math.random() * 4) + 1;

		texture = Textures.load("res\\grass_" + random + ".png");
	}

	public void draw() {
		Draw.texture(this, x * (Tile.SIZE - 1) + map.getX(), y
				* (Tile.SIZE - 1) + map.getY());
	}

	@Override
	public void update() {

	}

}
