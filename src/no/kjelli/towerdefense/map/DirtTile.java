package no.kjelli.towerdefense.map;

import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;

public class DirtTile extends Tile {

	public DirtTile(Map map, int x, int y) {
		super(map, x, y, Tile.DIRT, false, false);
	}

	public void draw() {
		Draw.sprite(this);
		if (selected)
			drawBorders();
		if (traversalCount > -1)
			drawTraversalCount();

	}

	@Override
	public void update() {

	}

	@Override
	public void onSelect() {
		testPathfinding();
	}

	@Override
	public void onCreate() {
		randomizeGrassTexture();
	}
	
	public void randomizeGrassTexture(){
		int random = (int) (Math.random() * 4) * 32;
		sprite = new Sprite(TextureAtlas.tiles, random, 32, Tile.SIZE,
				Tile.SIZE);
	}

}
