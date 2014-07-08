package no.kjelli.towerdefense.map;

import java.util.ArrayList;

import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.textures.Sprite;
import no.kjelli.generic.gfx.textures.TextureAtlas;
import no.kjelli.towerdefense.pathfinding.PathFinder;
import ai.pathfinder.Node;

public class GrassTile extends Tile {

	public GrassTile(Map map, int x, int y) {
		super(map, x, y, Tile.GRASS, true, true);
		int random = (int) (Math.random() * 4) * 32;
		sprite = new Sprite(TextureAtlas.defaultAtlas, random, 0, Tile.SIZE,
				Tile.SIZE);
	}

	public void draw() {
		Draw.sprite(this);
		if (selected)
			drawBorders();
		if (traversalCount > -1)
			Draw.string(traversalCount + "", x + SIZE / 2-Sprite.CHAR_WIDTH/2, y + SIZE / 2 - Sprite.CHAR_HEIGHT/2);
	}

	@Override
	public void update() {
	}

	@Override
	public void onSelect() {
		testPathfinding();
	}

}
