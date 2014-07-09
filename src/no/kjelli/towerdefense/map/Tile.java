package no.kjelli.towerdefense.map;

import java.util.ArrayList;

import ai.pathfinder.Node;
import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gameobjects.Clickable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.towerdefense.pathfinding.PathFinder;

public abstract class Tile extends AbstractGameObject implements Clickable {

	public static final int SIZE = 32;
	public static final int GRASS = 1;
	public static final int DIRT = 2;
	public final int x_index;
	public final int y_index;
	public final int type;
	protected final Map map;
	public boolean selected;

	// Testing variable TODO
	protected static int selectcount;

	protected static Tile start;
	protected static Tile goal;
	protected boolean isTraversable;
	protected boolean isBuildable;

	public int traversalCount = -1;

	public Tile(Map map, int x_index, int y_index, int type,
			boolean traversable, boolean buildable) {
		super(x_index, y_index, SIZE, SIZE);
		this.x_index = x_index;
		this.y_index = y_index;
		this.map = map;
		this.type = type;
		isTraversable = traversable;
		isBuildable = buildable;
	}

	@Override
	public void onMousePressed(int mouseButton) {
		if (mouseButton == 0)
			map.select(this);
	}

	public void drawBorders() {
		Draw.rect(this);
	}

	public void drawTraversalCount() {
		Draw.string(traversalCount + "", x,
				y + SIZE - Sprite.CHAR_HEIGHT);
	}

	public abstract void onSelect();

	@Override
	public void onMouseReleased(int mouseButton) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnter() {

	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub

	}

	public boolean isTraversable() {
		return isTraversable;
	}

	public boolean isBuildable() {
		return isBuildable;
	}

	public void testPathfinding() {
		if (selectcount % 2 == 0) {
			for (int x = 0; x < map.getTilesWidth(); x++) {
				for (int y = 0; y < map.getTilesHeight(); y++) {
					map.getTile(x, y).traversalCount = -1;
				}
			}
			start = this;
		} else if (selectcount % 2 == 1) {

			goal = this;
			ArrayList<Node> result = PathFinder.findPath(map, start, goal);
			int i = result.size() - 1;
			for (Node n : result) {
				map.getTile((int) n.x, (int) n.y).traversalCount = i;
				i--;
			}
		}

		selectcount++;
	}

}
