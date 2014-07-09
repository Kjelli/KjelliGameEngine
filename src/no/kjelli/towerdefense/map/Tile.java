package no.kjelli.towerdefense.map;

import java.util.ArrayList;

import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gameobjects.Clickable;
import no.kjelli.generic.gfx.Draw;
import no.kjelli.generic.gfx.Sprite;
import no.kjelli.towerdefense.gameobjects.towers.Tower;
import no.kjelli.towerdefense.pathfinding.PathFinder;
import ai.pathfinder.Node;

public abstract class Tile extends AbstractGameObject implements Clickable {

	public static final int SIZE = 32;
	public final int x_index;
	public final int y_index;
	protected final Map map;
	public boolean selected;

	// Testing variable TODO
	protected static int selectcount;

	protected static Tile start;
	protected static Tile goal;
	protected boolean isTraversable;
	protected boolean isBuildable;

	public Tower tower;

	public int traversalCount = -1;

	public Tile(Map map, int x_index, int y_index, boolean traversable,
			boolean buildable) {
		super(x_index, y_index, SIZE, SIZE);
		this.x_index = x_index;
		this.y_index = y_index;
		this.map = map;
		isTraversable = traversable;
		isBuildable = buildable;
	}

	@Override
	public void onMousePressed(int mouseButton) {
		if (isBuildable()){
			if (mouseButton == 0){
				map.select(this);
			}
		}
		else if(tower != null){
			map.select(tower);
		}
	}

	public void draw() {
		Draw.sprite(this, color);
		if (selected)
			drawBorders();
		if (traversalCount > -1) {
			drawTraversalCount();
		}
	}

	public void drawBorders() {
		Draw.rect(this);
	}

	public void drawTraversalCount() {
		Draw.string(traversalCount + "", x, y + SIZE - Sprite.CHAR_HEIGHT);
	}

	public abstract void onSelect();

	@Override
	public void onMouseReleased(int mouseButton) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnter() {
		color.r = 0.1f;
		color.g = 0.1f;
		color.b = 0.1f;
	}

	@Override
	public void onExit() {
		color.r = 0.0f;
		color.g = 0.0f;
		color.b = 0.0f;
	}

	public boolean isTraversable() {
		return isTraversable && tower == null;
	}

	public boolean isBuildable() {
		return isBuildable && tower == null;
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
