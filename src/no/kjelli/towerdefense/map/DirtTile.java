package no.kjelli.towerdefense.map;

import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;

public class DirtTile extends Tile {

	public DirtTile(Map map, int x, int y) {
		super(map, x, y, false, false);
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
