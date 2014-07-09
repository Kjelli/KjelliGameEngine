package no.kjelli.towerdefense.map;

import java.util.ArrayList;

import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.towerdefense.pathfinding.PathFinder;
import ai.pathfinder.Node;

public class GrassTile extends Tile {

	public GrassTile(Map map, int x, int y) {
		super(map, x, y, Tile.GRASS, true, true);
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
		int random = (int) (Math.random() * 4) * 32;
		sprite = new Sprite(TextureAtlas.tiles, random, 0, Tile.SIZE,
				Tile.SIZE);
	}

}
