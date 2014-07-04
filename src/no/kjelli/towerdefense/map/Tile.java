package no.kjelli.towerdefense.map;

import no.kjelli.generic.gameobjects.AbstractGameObject;
import no.kjelli.generic.gameobjects.Clickable;

public abstract class Tile extends AbstractGameObject implements Clickable {

	public static final int SIZE = 32;
	public static final int GRASS = 1;
	public static final int DIRT = 2;
	public final int x_index;
	public final int y_index;
	public final int type;
	protected final Map map;

	public boolean selected;

	public Tile(Map map, int x_index, int y_index, int type) {
		super(x_index, y_index, SIZE, SIZE);
		this.x_index = x_index;
		this.y_index = y_index;
		this.map = map;
		this.type = type;
	}

	@Override
	public void onMousePressed(int mouseButton) {
		if (mouseButton == 0)
			map.select(this);
		onSelect();
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

	@Override
	public void onCreate() {

	}

}
