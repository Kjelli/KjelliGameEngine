package no.kjelli.towerdefense.map;

import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;

public class GrassTile extends Tile {

	public GrassTile(Map map, int x, int y) {
		super(map, x, y, true, true);
	}

	@Override
	public void update() {
	}

	

	@Override
	public void onCreate() {
		int random = (int) (Math.random() * 4) * 32;
		sprite = new Sprite(TextureAtlas.tiles, random, 0, Tile.SIZE, Tile.SIZE);
	}

}
