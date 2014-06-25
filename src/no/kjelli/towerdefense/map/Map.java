package no.kjelli.towerdefense.map;

import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;

public class Map extends AbstractGameObject {
	public static final int EMPTY_GRASS = 0;
	public static final int EMPTY_DIRT = 1;

	int tiles_width, tiles_height;
	Tile[][] tiles;

	private Map(int tiles_width, int tiles_height) {
		this(0, 0, tiles_width, tiles_height);
	}

	private Map(int x, int y, int tiles_width, int tiles_height) {
		super(x, y, tiles_width * Tile.SIZE, tiles_height * Tile.SIZE);
		this.tiles_width = tiles_width;
		this.tiles_height = tiles_height;

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
		for (int x = 0; x < tiles_width; x++) {
			for (int y = 0; y < tiles_height; y++) {
				tiles[x][y].update();
			}
		}
	}

	public void setTile(int x, int y, Tile tile) {
		tiles[x][y] = tile;
	}

	public void destroy() {
		for (int x = 0; x < tiles_width; x++) {
			for (int y = 0; y < tiles_height; y++) {
				tiles[x][y].destroy();
			}
		}
		super.destroy();
	}

	public void setX(float newX) {
		for (int x = 0; x < tiles_width; x++) {
			for (int y = 0; y < tiles_height; y++) {
				tiles[x][y].setX(x * Tile.SIZE + newX);
			}
		}
		this.x = newX;
	}

	public void setVisible(boolean isVisible) {
		for (int x = 0; x < tiles_width; x++) {
			for (int y = 0; y < tiles_height; y++) {
				tiles[x][y].setVisible(isVisible);
			}
		}
		this.isVisible = isVisible;
	}

	public void setY(float newY) {
		for (int x = 0; x < tiles_width; x++) {
			for (int y = 0; y < tiles_height; y++) {
				tiles[x][y].setY(y * Tile.SIZE + newY);
			}
		}
		this.y = newY;
	}

	public static Map build(int width, int height) {
		return Builder.generate(width, height);
	}

	public static Map build(int width, int height, int template) {
		return Builder.generate(width, height, template);
	}

	private static class Builder {

		public static Map generate(int width, int height) {
			return generate(width, height, EMPTY_GRASS);
		}

		public static Map generate(int width, int height, int template) {
			Map newMap;
			switch (template) {
			case EMPTY_DIRT:
				newMap = emptyDirtMap(width, height);
				break;
			default:
			case EMPTY_GRASS:
				newMap = emptyGrassMap(width, height);
				break;
			}
			return newMap;
		}

		private static Map emptyGrassMap(int width, int height) {
			Map newMap = new Map(width, height);
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					newMap.setTile(x, y, new GrassTile(newMap, x, y));
				}
			}
			return newMap;
		}

		private static Map emptyDirtMap(int width, int height) {
			Map newMap = new Map(width, height);
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					newMap.setTile(x, y, new DirtTile(newMap, x, y));
				}
			}
			return newMap;
		}
	}
}
