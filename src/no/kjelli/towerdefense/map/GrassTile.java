package no.kjelli.towerdefense.map;

import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.textures.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;

public class GrassTile extends Tile {

	public GrassTile(Map map, int x, int y) {
		super(map, x, y, Tile.GRASS);
		int random = (int) (Math.random() * 4) * 32;
		sprite = new Sprite(TextureAtlas.defaultAtlas, random, 0, Tile.SIZE,
				Tile.SIZE);
	}

	public void draw() {
		Draw.texture(this);
	}

	@Override
	public void update() {

	}

}
