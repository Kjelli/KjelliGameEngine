package no.kjelli.towerdefense.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import no.kjelli.generic.World;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gfx.Draw;

public class Map extends AbstractGameObject {
	public static final int EMPTY_GRASS = 0;
	public static final int EMPTY_DIRT = 1;
	public static final int RIGHT = 0, LEFT = 1, UP = 2, DOWN = 3;

	static int tiles_width, tiles_height;
	Tile[][] tiles;

	SpawnPoint[] spawns;
	GoalPoint goal;

	public Tile selection;

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
		if (x < 0 || x >= tiles_width)
			throw new IllegalArgumentException("Out of bounds (" + x + ")");
		else if (y < 0 || y >= tiles_height)
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
		if (browse_cooldown > 0)
			browse_cooldown--;
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

	public final void setVisible(boolean isVisible) {
		for (int x = 0; x < tiles_width; x++) {
			for (int y = 0; y < tiles_height; y++) {
				tiles[x][y].setVisible(isVisible);
			}
		}
		this.isVisible = isVisible;
	}

	public final void setY(float newY) {
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

	public static Map load(String name) {
		return Builder.loadFromFile(name);
	}

	public int getTilesWidth() {
		return tiles_width;
	}

	public int getTilesHeight() {
		return tiles_height;
	}

	private static class Builder {

		private static final char GRASS = 'G', DIRT = 'D';

		public static Map loadFromFile(String name) {
			Map newMap = null;

			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader("res\\" + name + ".map"));

				int width = Integer.parseInt(br.readLine());
				int height = Integer.parseInt(br.readLine());

				newMap = new Map(width, height);

				int x = 0;
				int y = 0;

				String line;
				while ((line = br.readLine()) != null) {
					x = 0;
					for (char c : line.toCharArray()) {
						if (x > width || y > height) {
							System.err
									.println("Inconsistent dimensions in map file!");
							br.close();
							return null;
						}
						switch (c) {
						case GRASS:
							newMap.setTile(x, (height - 1) - y, new GrassTile(
									newMap, x, (height - 1) - y));
							break;
						case DIRT:
							newMap.setTile(x, (height - 1) - y, new DirtTile(
									newMap, x, (height - 1) - y));
							break;
						default:
							System.err
									.println("Invalid character in map file!");
							br.close();
							return null;
						}
						x++;
					}
					y++;
				}

				return newMap;

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

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

	@Override
	public void onCreate() {
		for (int x = 0; x < tiles_width; x++) {
			for (int y = 0; y < tiles_height; y++) {
				World.add(tiles[x][y]);
			}
		}
	}

	public void select(Tile tile) {
		if (tile != null) {
			if (!tile.isBuildable)
				return;
		}
		if (selection != null)
			unselect(selection);
		selection = tile;
		tile.selected = true;
		tile.onSelect();
	}

	public void unselect(Tile tile) {
		selection = null;
		tile.selected = false;
	}

	static int browse_cooldown;

	public Tile[][] getTiles() {
		return tiles;
	}

	public Tile browse(int direction) {
		if (browse_cooldown > 0)
			return null;
		if (selection == null) {
			select(getTile(0, 0));
			return selection;
		}
		try {
			switch (direction) {
			case LEFT:
				select(getTile(selection.x_index - 1, selection.y_index));
				break;
			case RIGHT:
				select(getTile(selection.x_index + 1, selection.y_index));
				break;
			case UP:
				select(getTile(selection.x_index, selection.y_index + 1));
				break;
			case DOWN:
				select(getTile(selection.x_index, selection.y_index - 1));
				break;
			}
			browse_cooldown = 3;
		} catch (IllegalArgumentException e) {
			//
		}
		return selection;
	}
}
