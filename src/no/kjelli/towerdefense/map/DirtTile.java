package no.kjelli.towerdefense.map;

import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.textures.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;

public class DirtTile extends Tile {

	public DirtTile(Map map, int x, int y) {
		super(map, x, y, Tile.DIRT);
		int random = (int) (Math.random() * 4) * 32;
		sprite = new Sprite(TextureAtlas.defaultAtlas, random, 32, Tile.SIZE,
				Tile.SIZE);
	}

	public void draw() {
		Draw.sprite(this);
	}

	@Override
	public void update() {

	}

	@Override
	public void onSelect() {
		//TODO:
	}

}
